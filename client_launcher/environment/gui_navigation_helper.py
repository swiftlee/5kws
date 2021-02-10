import pyautogui as gui
import time
from coord import Coord

gui.FAILSAFE = False

def move_and_click(x, y, c=1):
  gui.moveTo(x, y)
  gui.click(clicks=c)
  
def sign_out():
  move_and_click(*(Coord.ACCOUNT_MENU_DROP_DOWN.value))
  move_and_click(*(Coord.LOGOUT_MENU_ITEM.value))
  from login_helper import set_logged_in
  set_logged_in(False)
  time.sleep(5)

def enter_credentials(username, password):
  move_and_click(*(Coord.USERNAME_FIELD.value), 3)
  gui.write(username, interval=0.01)

  move_and_click(*(Coord.PASSWORD_FIELD.value), 2)
  gui.write(password, interval=0.01)

  move_and_click(*(Coord.CREDENTIALS_LOGIN_BUTTON.value))

  time.sleep(2)
  
  from login_helper import set_logged_in, get_logged_in as logged_in

  # True if login succeeds
  set_logged_in(not gui.pixelMatchesColor(*(Coord.LOGIN_ERROR_BANNER.value), (217, 54, 54)))
  if not logged_in():
    # print(f'Credentials invalid, sleeping, status was {logged_in()}')
    time.sleep(4)
    #set_logged_in(not gui.pixelMatchesColor(*(Coord.LOGIN_ERROR_BANNER.value), (217, 54, 54)))

  print(f'Login status: {logged_in()}')


def enter_hypixel(window):
  connection_success = False
  navigate_main_menu(window)
  while not connection_success:
    connect_to_server(window) 
    connection_success = check_connection(window)
    time.sleep(1)
# TODO: Currently we are joining the server successfully or unsuccessfully,
# but somehow we are not making it to the "check_connection" function. 
# connect_to_server finishes as it should, and then we don't know what happens.

def navigate_main_menu(window):
  print('Moving to default position...')
  gui.moveTo(*(Coord.DEFAULT_MENU_POSITION.value))
  while not gui.pixelMatchesColor(*(Coord.MULTIPLAYER_BUTTON.value), (224, 224, 224)):
    time.sleep(0.5)
  print('Moving to multiplayer button...')
  move_and_click(*(Coord.MULTIPLAYER_BUTTON.value))
  connect_to_server(window)
  
def connect_to_server(window):
  while not gui.pixelMatchesColor(*(Coord.DIRECT_CONNECT_BUTTON.value), (56, 56, 56)):
    time.sleep(0.5)
  print('Moving to direct connect button...')
  move_and_click(*(Coord.DIRECT_CONNECT_BUTTON.value))
  while not gui.pixelMatchesColor(*(Coord.SERVER_ADDRESS_BAR.value), (0, 0, 0)):
    time.sleep(0.5)
  print('Moving to type in server address and join...')
  gui.hotkey('ctrl', 'a', interval=0.2)
  gui.write('hypixel.net', interval=0.1)
  move_and_click(*(Coord.JOIN_SERVER.value))


def check_connection(window):
  print('Checking connection...')
  gui.press('esc')
  while not gui.pixelMatchesColor(*(Coord.PAUSED_SCREEN_BUTTON.value), (110, 110, 110)):
    if gui.pixelMatchesColor(*(Coord.TIMED_OUT_RETRY.value), (111, 111, 111)):
      print('Moving to back to server list...')
      move_and_click(*(Coord.TIMED_OUT_RETRY.value))
      return False
    else:
      print('Shit aint workin')
      time.sleep(3)
      gui.press('esc')
  return True
  
