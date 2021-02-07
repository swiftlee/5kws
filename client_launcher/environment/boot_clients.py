import pyautogui as gui
import pygetwindow as gw
import time

launcher = gw.getWindowsWithTitle('Minecraft Launcher')[0]
launcher.activate()


def mojang_sign_in(username, password):
    launcher.maximize()
    # if on original sign in page, click sign in and enter credentials
    # print(gui.pixel(1035, 562))
    # print(gui.pixel(1052, 623))
    if gui.pixelMatchesColor(1035, 562, (255, 255, 255)) or gui.pixelMatchesColor(1035, 562, (0, 140, 68)):
      move_and_click(960, 556) # click mojang login
      enter_credentials(username, password)
    elif gui.pixelMatchesColor(1052, 623, (255, 255, 255)) or gui.pixelMatchesColor(1052, 623, (0, 140, 68)):
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
  move_and_click(953, 448, 3)
  gui.write(username, interval=0.01)

  move_and_click(949, 522, 2)
  gui.write(password, interval=0.01)

  move_and_click(959, 621)

  time.sleep(2)
  # IF LOGIN FAILS
  if gui.pixelMatchesColor(1882, 57, (217, 54, 54)): 
    return

def play_game():
  # FIND MOD AND CLICK PLAY
  # installations 298 87
  # search 807 133
  #   gui.click()
  # move_and_click(298, 87)
  
  # # IF INSTANCE ALREADY RUNNING DIALOGUE
  # if gui.pixelMatchesColor(1140, 633, (231, 100, 60)
  #   # gui.moveTo(1140, 633)
  #   # gui.click()
  move_and_click(298, 87)   # click "installations"
  move_and_click(807, 133)  # click "search"
  gui.write('1.12.2')       # search for the installation 
  move_and_click(1370, 197)   # click play
  time.sleep(1)
  # if multiple client dialogue, click play anyways
  # if gui.pixelMatchesColor(1140, 633, (231, 100, 60)):
  move_and_click(1140, 633)
  
  wait_for_load()
    
    
def wait_for_load():
    while True:
        if "Minecraft Launcher" not in gw.getAllTitles():
          print('Rebooting Minecraft Launcher...')
          break
        time.sleep(0.5)

def move_and_click(x, y, c=1):
  gui.moveTo(x, y)
  gui.click(clicks=c)
  
def sign_out():
  move_and_click(140, 46)
  move_and_click(96, 160)
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
    mojang_sign_in(username, loginDictionary[username])
    play_game()
    break

# next(iter(my_dict))


## click search
## type in whatever name is


# installations 298 87
# search 807 133
# play position 1366 200