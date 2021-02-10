import pyautogui as gui
from gui_navigation_helper import enter_hypixel
import time

gui.FAILSAFE = False

def join_server(game_windows):
  for window in list(game_windows.values()):
    print('Maximizing and focusing window...')
    window.maximize()
    time.sleep(2)
    #gui.click(window.center)
    if not window.isActive:
      print('Window was not focused properly')
      break
    print('Window is active!')
    enter_hypixel(window)
    time.sleep(10)
    #do what we need to in that client`
    window.minimize()