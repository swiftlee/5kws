import asyncio
import aiofiles
import aiohttp
import subprocess

base_url = 'https://authserver.mojang.com'
login_dictionary = {}
player_data = {}

async def get_user_access_token(username, password):
    global player_data

    endpoint = '/authenticate'
    url = f'{base_url}{endpoint}'
    print(f'Attempting to authenticate {username} via POST to {url}...')
    payload = {
        'agent': {
            'name': 'Minecraft',
            'version': 1
         },
         'username': username,
         'password': password
    }

    async with aiohttp.ClientSession() as session:
        async with session.post(url, json=payload) as resp:
            if resp.status == 200:
                data = await resp.json()
                user = {
                    'accessToken': data['accessToken'],
                    'name': data['selectedProfile']['name'],
                    'id': data['selectedProfile']['id']
                }
                print(f'Successfully authenticated {username} with in-game name: {user["name"]}.')
                player_data[username] = user
            else:
                print(f'[!] Failed authentication for {username}!')
                return False
    return True

async def init_game(username):
    accessToken, name, id = player_data[username].values()
    process = subprocess.Popen(['TEST.bat', name, id, accessToken], shell=True, stdout=subprocess.PIPE)

    load_complete = False

    for line in process.stdout:
        print(line)
        if line is not None and 'minecraft:textures/atlas/shulker_boxes' in str(line):
            return

def boot_game_for_users():
  global login_dictionary

  i = 0
  for username in player_data:
    if i < 2:
      # booting the game should act synchronously; sign one user in at a time due to processing limitations
      loop.run_until_complete(init_game(username))
      i+=1

######################### ENTRYPOINT ######################### 

file = open('accounts.txt', 'r')

while True:
    username = file.readline().strip()
    password = file.readline().strip()
    if not username or not password or username in login_dictionary: break
    login_dictionary[username] = password
    
loop = asyncio.ProactorEventLoop()
asyncio.set_event_loop(loop)

# we can run this concurrently since it's not an intensive process
loop.run_until_complete(asyncio.gather(
        *(get_user_access_token(*credentials) for credentials in login_dictionary.items())
))

boot_game_for_users()

#TODO: copy the required files (the jar and json file for the versions directory) to minecraft directory, also copy binaries to .minecraft/bin/1.16.5-Baritone/

