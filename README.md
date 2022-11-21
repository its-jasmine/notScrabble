![notScrabble_logo](https://user-images.githubusercontent.com/83664112/201569071-5c00745d-51ec-4077-be1e-e8401601f39c.png)


notScrabble is a simplified version of the classic board game Scrabble! 

This project is being produced for SYSC 3110 taught by Dr. Babak Esfandiari. The TA assigned to this group is Mr. Mohamed Zalat. 

This project is divided into 4 milestones with this README file corresponding to milestone 2.

**Milestone 3:** More complex implementation of notScrabble
- User plays the game through the User Interface
- User may place tiles on the board from their rack, see their score, pass their turn and submit their placed tiles
- premium squares and blank tiles are supported
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

11/21/2022 - Milestone 3

### Usage 

To start the game, the number of players need to be specified with a default minimum of 2 and a default maximum of 4. The user will be prompted.
The game Welcome View is initiated, which prompts the user to select either instructions or New Game, which starts a notScrabble game of two players.

Players will take turns until a player runs out of tiles AND there are no tiles in the bag. The winner is then determined. 

When prompted, a player may pass or play their turn by clicking the submit or pass button.
The player needs to drag and drop their tiles from their rack to the board.
The player can swap the order of tiles in their rack by dragging and dropping to reorder their rack.
The player can place unsubmited tiles from the board back to their rack.
The player can place blank tiles, and will be prompted as to which letter the blank tile represents
when submitting their word after placement, if it is determined to be invalid, the player's tiles are returned to the rack.

    
 **First turn** reminder: The player who places the first word onto the board must place a tile on the start square. 

<img width="639" alt="Screen Shot 2022-10-25 at 10 04 24 AM" src="https://user-images.githubusercontent.com/84146479/197795120-00438956-c3fc-4f35-a596-05640f70e335.png">

### Rest of Deliverables

| Milestone | Due Date   | Main Deliverables                                                                                                                                                                                                                                                                                                           |
|:---------:|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|     3     | 11/21/2022 | **Fully functioning game with AI capabilities:** Tile placement validation, scoring, blank tile and premium square functionalities are included. AI players with the ability to play the highest scoring word will be implemented. Changes from Milestone 2 will be documented. (The code will smell like fresh flowers) 🌻 |
|     4     | 12/05/2022 | **Undo, saving and customization features:** Ability to undo or redo moves at multiple levels. Ability to save and load the game using Java Serialization. Ability to customize boards by alternating the placement of premium squares.                                                                                     |

### Known issues

The AI can only place words in a downward manner. This will later be fixed to place words in all directions
Currently, to see the AI in action, GameView.java must be run instead of the default welcomeFrame.

### Roadmap ahead

For milestone 4 and onwards, the team will try to use JIRA, GitHub's Projects. The team will continue to use Lucidchart and Discord as brainstorming and communication channels. 

The roadmap ahead is concerned with the refinement of the AI, UI, and added features like saving and loading games as well as new premium squares patterns

**Goal** for milestone 4:
- Refine the AI
- Add saving and loading functionalities.

