import pyautogui as gui
import time
from coord import Coord

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