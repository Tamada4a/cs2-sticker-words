# Words by CS2 stickers
This service allows you to create words from CS2 stickers. The idea of creating the service appeared after the update, where the ability to glue stickers in custom places was added.

# How it works

## User guide
1. Enter the word or phrase you want to generate.
2. Choose whether to generate the word using Holo/Gold/Glitter/Foil stickers ("Use unusual stickers" checkbox) - by default, generating without unusual stickers.
3. Choose from how many stickers to generate the word - by default, "5" is selected.
4. Press "Generate" and wait for the generation to finish.
5. Choose the stickers that you like the most, go to the market to buy them (just click on the sticker), and customize your skins!

## Demo
### Start page
![Start page](https://github.com/Tamada4a/cs2-sticker-words/blob/main/assets/Main.png)

### Result of generation
![Result](https://github.com/Tamada4a/cs2-sticker-words/blob/main/assets/Result.png)

# How to launch?
1. There are two options:
   1. Using docker from the root folder: `docker-compose up --build -d`.
   2. Everything is separate:
      1. Download and install <a href="https://www.mysql.com/">MySQL</a>.
      2. Download the repository.
      3. Go to the `backend` folder and launch the project in any way convenient for you.
      4. Go to the `frontend` folder, open the console in this folder and write `npm install` and then `npm run dev`.
2. Open in the browser `http://127.0.0.1:5173/`.
3. The project has been launched!

# Afterword
The API authored by <a href="https://github.com/ByMykel/CSGO-API">ByMykel</a> is used as a sticker database.  
