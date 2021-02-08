import pyautogui as gui
import pygetwindow as gw
import time
import subprocess
import asyncio, sys
from enum import Enum
import os
import signal

mc_install_location = 'C:/"Program Files (x86)"/Minecraft/MinecraftLauncher.exe'

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

if sys.platform == 'win32':
  loop = asyncio.ProactorEventLoop()
  asyncio.set_event_loop(loop)

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
  cmd = f'{mc_install_location}'
  proc = await asyncio.create_subprocess_shell(
    cmd,
    stdout=asyncio.subprocess.PIPE,
    stderr=asyncio.subprocess.PIPE)

  await wait_for_launcher_load(username, password, proc)

  await proc.wait() # wait for process to complete

  print(f'[{cmd!r} exited with {proc.returncode}]')



def mojang_sign_in(username, password):
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
  
  os.kill(launcher_process.pid, signal.CTRL_C_EVENT)
    
    
def wait_for_game_load():
    while True:
        if "Minecraft Launcher" not in gw.getAllTitles():
          print('Rebooting Minecraft Launcher...')
          break
        time.sleep(0.5)

def move_and_click(x, y, c=1):
  gui.moveTo(x, y)
  gui.click(clicks=c)
  
def sign_out():
  move_and_click(*(Coord.ACCOUNT_MENU_DROP_DOWN.value))
  move_and_click(*(Coord.LOGOUT_MENU_ITEM.value))
  time.sleep(3)


file = open('accounts.txt', 'r')
loginDictionary = {}

while True:
  username = file.readline().strip()
  password = file.readline().strip()
  if not username or not password or username in loginDictionary: break
  loginDictionary[username] = password
# mojang_sign_in('conceicaoinacio2011@hotmail.com', 'c02051988') # we need to read from file, but make sure we are not signing in the same account twice
# print(loginDictionary.items())


for username in loginDictionary:
  loop.run_until_complete(boot_launcher(username, loginDictionary[username]))
  break

loop.close()
# next(iter(my_dict))


## click search
## type in whatever name is


# installations 298 87
# search 807 133
# play position 1366 200