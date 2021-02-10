from coord import Coord
import pygetwindow as gw
import pyautogui as gui
from gui_navigation_helper import move_and_click, enter_credentials, sign_out
import time

gui.FAILSAFE = False

def set_logged_in(value):
  global logged_in
  logged_in = value

def get_logged_in():
    return logged_in
  
def wait_for_gui_load():
  while (not gui.pixelMatchesColor(*(Coord.MOJANG_LOGIN.value), (255, 255, 255)) and
        not gui.pixelMatchesColor(*(Coord.MAIN_PLAY_BUTTON.value), (0, 138, 68)) and
        not gui.pixelMatchesColor(*(Coord.CREDENTIALS_LOGIN.value), (0, 140, 68))): 
        time.sleep(0.1)
        continue
  print('Launcher successfully loaded')

async def wait_for_launcher_load():
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
  
# Returns true if signed out, false otherwise
def mojang_sign_in(username, password):
  wait_for_gui_load()
  # if on original sign in page, click sign in and enter credentials
  if gui.pixelMatchesColor(*(Coord.MOJANG_LOGIN.value), (255, 255, 255)):
    print('Clicking \'Mojang Login\' button.')
    move_and_click(*(Coord.MOJANG_LOGIN_BUTTON.value)) # click mojang login
    enter_credentials(username, password)
  elif gui.pixelMatchesColor(*(Coord.CREDENTIALS_LOGIN.value), (0, 140, 68)):
    print('Entering credentials...')
    enter_credentials(username, password)
  else:
    print("signing out because we're already signed in")
    time.sleep(4)
    sign_out() 
    return True # MAKE SURE TO NOTE THAT SIGN OUT OCCURRED, THIS WILL ONLY EVER HAPPEN ON FIRST ITERATION
  return False