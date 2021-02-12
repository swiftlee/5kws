import asyncio
from aiofiles.threadpool import open as aioopen
import aiohttp
import subprocess
import nest_asyncio

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
        #TODO maybe find a different way to tell when minecraft loaded
        if line is not None and 'minecraft:textures/atlas/shulker_boxes' in str(line):
            print('[!] Finished loading Minecraft!')
            return

def boot_game_for_users():
  global login_dictionary

  # booting the game should act synchronously; sign one user in at a time due to processing and RAM limitations
  for i, username in enumerate(player_data, start=0):
    if i < 1:
      loop.run_until_complete(init_game(username))

def fetch_users_and_boot_game():
    # we can run this in parallel since it's not an intensive process
    loop.run_until_complete(asyncio.gather(
            *(get_user_access_token(*credentials) for credentials in login_dictionary.items())
    ))

    boot_game_for_users()

async def main():
    global login_dictionary

    filename = 'accounts.txt'
    
    async with aioopen(filename, 'r') as file:
        lines = await file.readlines()
        it = iter([line.strip() for line in lines])
        login_dictionary = dict(zip(it, it))
    
    fetch_users_and_boot_game()
    
    
######################### ENTRYPOINT ######################### 

# DRIVER CODE
loop = asyncio.ProactorEventLoop()
nest_asyncio.apply(loop)
asyncio.set_event_loop(loop)
loop.run_until_complete(main())

#TODO: copy the required files (the jar and json file for the versions directory) to minecraft directory, also copy binaries to .minecraft/bin/1.16.5-Baritone/

