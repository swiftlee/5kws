import pyautogui as gui
import pygetwindow as gw
import time
import subprocess
import asyncio, sys
from coord import Coord
from login_helper import set_logged_in, wait_for_gui_load, wait_for_launcher_load, get_logged_in as logged_in, mojang_sign_in
from gui_navigation_helper import move_and_click
import server_connect

gui.FAILSAFE = False
mc_install_location = 'C:\\Program Files (x86)\\Minecraft\\MinecraftLauncher.exe'
game_windows = {}
login_dictionary = {}
failed_signin = []
filter = '1.12.2'

# FUNCTION DEFINITIONS

async def boot_launcher(username, password):
  proc = subprocess.Popen(['MinecraftLauncher.exe'],
    executable=mc_install_location,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE, shell=False)

  for line in proc.stdout.readlines():
    print(line)

  for line in proc.stderr.readlines():
    print(line)

  await wait_for_launcher_load()
  
  attempt_sign_in(username, password, proc)

  time.sleep(2)
  print(f'[{mc_install_location!r} exited with {proc.returncode}]')


def assign_game_window(username, proc):
  while True:
    game_window = [window for window in gw.getAllWindows() if filter in window.title.lower() and window not in list(game_windows.values())]
    
    if len(game_window) == 0:
      time.sleep(0.5)
      continue
      
    game_window = next(iter(game_window))
    game_windows[username] = game_window
    print(f'Setting window with name \'{game_window.title}\' for user {username}.')               
    proc.terminate()
    break

  # while username not in game_windows:
  #     mc_game_window = gw.getActiveWindow()
  #     if mc_game_window not None and 'minecraft' in  mc_game_window.title.lower():
  #       print(f'Setting window with name \'{gw.getActiveWindow().title}\' for user {username}.')
  #       game_windows[username] = mc_game_window
  #       proc.terminate()
  #       break
  #     time.sleep(0.5)


def attempt_sign_in(username, password, proc):
  
  print('Attempting sign in...')
  
  user_signed_out = mojang_sign_in(username, password)
  
  if user_signed_out:
    print('User was signed out; moving to login page to sign in...')
    attempt_sign_in(username, password, proc)
    return

  if logged_in():
    print('Launching Minecraft...')
    play_game(proc)
    print('Waiting on process to complete')

    # assign game window to user
    assign_game_window(username, proc)

    proc.wait() # wait for process to complete

    set_logged_in(False)
  else:
    print('Player was not logged in, selecting next user.')
    failed_signin.append(username)
    try:
      next_user_id = list(login_dictionary.keys()).index(username) + 1
      _username, _password = list(login_dictionary.items())[next_user_id]
      attempt_sign_in(_username, _password, proc)
    except IndexError:
      pass


def play_game(launcher_process):
  print('Starting game')
  move_and_click(*(Coord.INSTALLATIONS_TAB.value))   # click "installations"
  move_and_click(*(Coord.SEARCH_INPUT.value))  # click "search"
  gui.write(filter)       # search for the installation 
  move_and_click(*(Coord.PRIMARY_PLAY_BUTTON.value))   # click play
  time.sleep(1)
  # if multiple client dialogue, click play anyways
  # if gui.pixelMatchesColor(1140, 633, (231, 100, 60)):
  move_and_click(*(Coord.SECONDARY_PLAY_BUTTON.value))
  print('Finished play_game')
  # launcher_process.terminate()
  
async def create_clients_per_user():
  global login_dictionary
  file = open('accounts.txt', 'r')

  while True:
    username = file.readline().strip()
    password = file.readline().strip()
    if not username or not password or username in login_dictionary: break
    login_dictionary[username] = password

  i = 0
  for username in login_dictionary:
    if username not in game_windows and username not in failed_signin and i < 1:
      await boot_launcher(username, login_dictionary[username])
      i+=1

def minimize_windows():
  print('Minimizing windows...')
  for window in gw.getAllWindows():
    window.minimize()
  print('Minimizing complete!')
# entrypoint

if sys.platform == 'win32':
  loop = asyncio.ProactorEventLoop()
  asyncio.set_event_loop(loop)

set_logged_in(False)
loop.run_until_complete(create_clients_per_user())
loop.close()
time.sleep(5)
print('Giving time for game to load...')
minimize_windows()

server_connect.join_server(game_windows)

  
