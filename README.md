![notScrabble_logo](https://user-images.githubusercontent.com/83664112/201569071-5c00745d-51ec-4077-be1e-e8401601f39c.png)


notScrabble is a simplified version of the classic board game Scrabble! 

This project is being produced for SYSC 3110 taught by Dr. Babak Esfandiari. The TA assigned to this group is Mr. Mohamed Zalat. 

This project is divided into 4 milestones with this README file corresponding to milestone 4.

**Milestone 4:** Final touches on the notScrabble Game
- User plays the game through the User Interface
- User may place tiles on the board from their rack, see their score, pass their turn, exchange their tiles and submit their placed tiles
- premium squares and blank tiles are supported, as well as custom board layouts, save and loading a game.
- UML class diagram and sequence diagram are provided
- Design choices such as data structure and relevant operations are discussed


## Table of contents

<!--ts-->
   * [Authors](#authors)
   * [Version](#version)
   * [Usage](#usage)
   * [Rest of deliverables](#rest-of-deliverables)
   * [Known issues](#known-issues)
   * [Roadmap ahead](#roadmap-ahead)
<!--te-->

### Authors   

Arthur Atangana ArthurAtangana@cmail.carleton.ca

Rebecca Elliott RebeccaElliott3@cmail.carleton.ca

Jasmine Gad El Hak JasmineGadElHak@cmail.carleton.ca

Victoria Malouf VictoriaMalouf@cmail.carleton.ca

### Version 

12/8/2022 - Milestone 4

### Usage 

To start the game, the number of players need to be specified with a default minimum of 2 and a default maximum of 4. The user will be prompted.
The game Welcome View is initiated, which prompts the user to select either instructions, load Game, 1 VS Computer or New Game.
load Game requests the name of the save file that the player wishes to play.
1 VS computer starts a game against an AI opponent and requests the type of board that the player wants to play on.
New game requests the amount of players wanted and the type of board that the players want to play on.

Players will take turns until a player runs out of tiles AND there are no tiles in the bag. The winner is then determined.
When prompted, a player may pass or play their turn by clicking the submit or pass button.
The player needs to drag and drop their tiles from their rack to the board.
The player can swap the order of tiles in their rack by dragging and dropping to reorder their rack.
The player can place unsubmited tiles from the board back to their rack.
The player can exchange their tiles for tiles in the bag. This passes their turn.
The player can place blank tiles, and will be prompted as to which letter the blank tile represents
The player can save their game at any point by clicking on the drop-down save menu, and inputting the name of their save.
When submitting their word after placement, if it is determined to be invalid, the player's tiles are returned to the rack.


    
 **First turn** reminder: The player who places the first word onto the board must place a tile on the start square. 

<img width="639" alt="Screen Shot 2022-10-25 at 10 04 24 AM" src="https://user-images.githubusercontent.com/84146479/197795120-00438956-c3fc-4f35-a596-05640f70e335.png">

### Known issues

The AI is too good, beware.

