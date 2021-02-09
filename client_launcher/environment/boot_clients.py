import pyautogui as gui
import pygetwindow as gw
import time
import subprocess
import asyncio, sys
from enum import Enum
import os
import signal

mc_install_location = 'C:\\Program Files (x86)\\Minecraft\\MinecraftLauncher.exe'
gameWindows = {}

class Coord(Enum):
  MOJANG_LOGIN = [1035, 562]
  MOJANG_LOGIN_BUTTON = [960, 556]
  CREDENTIALS_LOGIN = [1052, 623]
  CREDENTIALS_LOGIN_BUTTON = [959, 621]
  USERNAME_FIELD = [953, 448]
  PASSWORD_FIELD = [949, 522]
  LOGIN_ERROR_BANNER = [1882, 57]
  INSTALLATIONS_TAB = [298, 87]
  SEARCH_INPUT = [807, 133]
  PRIMARY_PLAY_BUTTON = [1370, 197]
  SECONDARY_PLAY_BUTTON = [1140, 633]
  ACCOUNT_MENU_DROP_DOWN = [140, 46]
  LOGOUT_MENU_ITEM = [96, 160]
  MAIN_PLAY_BUTTON = [1131, 752]


if sys.platform == 'win32':
  loop = asyncio.ProactorEventLoop()
  asyncio.set_event_loop(loop)

def wait_for_gui_load():
  while (not gui.pixelMatchesColor(*(Coord.MOJANG_LOGIN.value), (255, 255, 255)) and
        not gui.pixelMatchesColor(*(Coord.MAIN_PLAY_BUTTON.value), (0, 138, 68)) and
        not gui.pixelMatchesColor(*(Coord.CREDENTIALS_LOGIN.value), (0, 140, 68))): 
        time.sleep(0.1)
        continue
  print('Something worked')

async def wait_for_launcher_load(username, password, launcher_process):
  while True:
    time.sleep(0.25)
    try:
      # Maximize launcher
      launcher = gw.getWindowsWithTitle('Minecraft Launcher')[0]
      launcher.activate()
      launcher.maximize()
      print('Maximizing window...\n')
      break
    except(IndexError):
      pass

  time.sleep(2)
  print('Attempting sign in...')
  mojang_sign_in(username, password)
  play_game(launcher_process)

async def boot_launcher(username, password):
  print(mc_install_location)
  proc = subprocess.Popen(['MinecraftLauncher.exe'],
    executable=mc_install_location,
    stdout=subprocess.PIPE,
    stderr=subprocess.PIPE, shell=False)

  for line in proc.stdout.readlines():
    print(line)

  for line in proc.stderr.readlines():
    print(line)

  await wait_for_launcher_load(username, password, proc)

  proc.wait() # wait for process to complete

 #TODO: VERIFY THIS CODE
  if username not in gameWindows:
    mc_game_window = gw.getActiveWindow()
    if mc_game_window.title().includes('Minecraft'):
      gameWindows[username] = mc_game_window

  time.sleep(2)
  print(f'[{mc_install_location!r} exited with {proc.returncode}]')



def mojang_sign_in(username, password):
  wait_for_gui_load()
  # if on original sign in page, click sign in and enter credentials
  # print('MOJANG LOGIN COLOR:' + gui.pixel(*(Coord.MOJANG_LOGIN.value)))
  # print('CREDENTIALS LOGIN COLOR:' + gui.pixel(*(Coord.CREDENTIALS_LOGIN.value)))
  print('at sign in cases')
  print('COORDINATE VALUES' + str(Coord.MOJANG_LOGIN.value) + '\nCOLOR: ' + str(gui.pixel(*(Coord.MOJANG_LOGIN.value))))
  if gui.pixelMatchesColor(*(Coord.MOJANG_LOGIN.value), (255, 255, 255)):
    move_and_click(*(Coord.MOJANG_LOGIN_BUTTON.value)) # click mojang login
    enter_credentials(username, password)
  elif gui.pixelMatchesColor(*(Coord.CREDENTIALS_LOGIN.value), (0, 140, 68)):
    enter_credentials(username, password)
  else:
    print("signing out because we're already signed in")
    time.sleep(4)
    sign_out()
  
    

# def clear_login():
#   print("in clear login")
  
#   move_and_click(953, 448, 3)
#   gui.press('backspace')

#   move_and_click(949, 522)
#   gui.press('backspace')


def enter_credentials(username, password):
  move_and_click(*(Coord.USERNAME_FIELD.value), 3)
  gui.write(username, interval=0.01)

  move_and_click(*(Coord.PASSWORD_FIELD.value), 2)
  gui.write(password, interval=0.01)

  move_and_click(*(Coord.CREDENTIALS_LOGIN_BUTTON.value))

  time.sleep(2)
  # IF LOGIN FAILS
  if gui.pixelMatchesColor(*(Coord.LOGIN_ERROR_BANNER.value), (217, 54, 54)): 
    return

def play_game(launcher_process):
  # FIND MOD AND CLICK PLAY
  # installations 298 87
  # search 807 133
  #   gui.click()
  # move_and_click(298, 87)
  
  # # IF INSTANCE ALREADY RUNNING DIALOGUE
  # if gui.pixelMatchesColor(1140, 633, (231, 100, 60)
  #   # gui.moveTo(1140, 633)
  #   # gui.click()
  move_and_click(*(Coord.INSTALLATIONS_TAB.value))   # click "installations"
  move_and_click(*(Coord.SEARCH_INPUT.value))  # click "search"
  gui.write('1.12.2')       # search for the installation 
  move_and_click(*(Coord.PRIMARY_PLAY_BUTTON.value))   # click play
  time.sleep(1)
  # if multiple client dialogue, click play anyways
  # if gui.pixelMatchesColor(1140, 633, (231, 100, 60)):
  move_and_click(*(Coord.SECONDARY_PLAY_BUTTON.value))
  launcher_process.terminate()

def move_and_click(x, y, c=1):
  gui.moveTo(x, y)
  gui.click(clicks=c)
  
def sign_out():
  move_and_click(*(Coord.ACCOUNT_MENU_DROP_DOWN.value))
  move_and_click(*(Coord.LOGOUT_MENU_ITEM.value))
  time.sleep(5)


file = open('accounts.txt', 'r')
loginDictionary = {}

while True:
  username = file.readline().strip()
  password = file.readline().strip()
  if not username or not password or username in loginDictionary: break
  loginDictionary[username] = password
# mojang_sign_in('conceicaoinacio2011@hotmail.com', 'c02051988') # we need to read from file, but make sure we are not signing in the same account twice
# print(loginDictionary.items())
async def create_clients_per_user():
  for username in loginDictionary:
    await boot_launcher(username, loginDictionary[username])

loop.run_until_complete(create_clients_per_user())
loop.close()
# next(iter(my_dict))


## click search
## type in whatever name is


# installations 298 87
# search 807 133
# play position 1366 200