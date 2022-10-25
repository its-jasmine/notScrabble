# notScrabble

notScrabble is a simplified version of the classic board game Scrabble! 

This project is being produced for SYSC 3110 taught by Dr. Babak Esfandiari. The TA assigned to this group is Mr. Mohamed Zalat. 

This project is divided into 4 milestones with this README file corresponding to milestone 1.

**Milestone 1:** Text-based version of the game
- Users play the game via the console
- Users may place a word, pass their turn, see their rack, and see the resulting state of the board
- Word placements are validated and scores are calculated accordingly
- There is no support for blank tiles and premium squares at this time
- UML class diagrams and sequence diagrams are provided
- Design choices such as data structures and relevant operations are discussed

## Table of contents

<!--ts-->
   * [Authors](#authors)
   * [Version](#version)
   * [Usage](#usage)
   * [Rest of deliverables](#rest-of-deliverables)
   * [Known issues](#known-issues)
   * [Roadmap ahead](#roadmap-ahead)
   * [Design decisions](#design-decisions)
<!--te-->

### Authors   

Arthur Atangana ArthurAtangana@cmail.carleton.ca

Rebecca Elliott RebeccaElliott3@cmail.carleton.ca

Jasmine Gad El Hak JasmineGadElHak@cmail.carleton.ca

Victoria Malouf VictoriaMalouf@cmail.carleton.ca

### Version 

10/25/2022 - Milestone 1 

### Usage 

To start the game, the number of players need to be specified with a default minimum of 2 and a default maximum of 4.
After a Game is created, the playGame method is invoked. Players will take turns until a player runs out of tiles AND there are no tiles in the bag. The winner is then determined. 

When prompted, a player may pass or play their turn by entering a string of letters and coordinates into the terminal. Entering "pass" will pass the turn.

The order and formatting of user input is **important**. Letters must be capitalized, columns must be labeled A-O, and rows must be labeled ONE-FIFT. With coordinate "A ONE" corresponding to the top left square and coordinate "O FIFT" corresponding to the bottom right square. 

NOTE: All tiles the user is attempting to place during their turn must be included in the same input, so be careful!.

The input formatting is as follows: 
> LETTER COLUMN ROW,LETTER COLUMN ROW,...                           

    "A H EIGHT,N I EIGHT,D J EIGHT"
    
 **First turn** reminder: The player who places the first word onto the board must place a tile on the start square. (coordinate H EIGHT) 

<img width="639" alt="Screen Shot 2022-10-25 at 10 04 24 AM" src="https://user-images.githubusercontent.com/84146479/197795120-00438956-c3fc-4f35-a596-05640f70e335.png">

### Rest of Deliverables

| Milestone | Due Date | Main Deliverables |
|:-------:|----------|-------------------------------------------------------|
| 2 | 11/11/2022 | **GUI-based version:** The display will be in a JFrame where user input occurs via the mouse. Unit tests for the Model with failing unit tests for incomplete functionality implementations will be provided. Changes to UML and data structures from Milestone 1 will be documented.|
| 3 | 11/21/2022 | **Fully functioning game with AI capabilities:** Tile placement validation, scoring, blank tile and premium square functionalities are included. AI players with the ability to play the highest scoring word will be implemented. Changes from Milestone 2 will be documented. (The code will smell like fresh flowers) 🌻 
| 4 | 12/05/2022 | **Undo, saving and customization features:** Ability to undo or redo moves at multiple levels. Ability to save and load the game using Java Serialization. Ability to customize boards by alternating the placement of premium squares. |

### Known issues

The UI is hard to use. 
Since this UI will be almost completely replaced in Milestone 2 we made the decision not to fix it.

The first word played can be one letter long and return a score of 0.

Extra points for special squares and Scrabbles are not implemented at this time.

### Roadmap ahead

For milestone 2 and onwards, the team will try to use JIRA, GitHub's Projects, or GitHub's ZenHub extension for task management. The team will continue to use Lucidchart and Discord as brainstorming and communication channels. 

Goals for milestone 2:
- Implement logic so that the first word played must be at least two tiles long. (Currently a player may legally place only one tile to start)
- Implement logic so that a scrabble can be scored (7 tiles placed at once)

Milestone 1 provides an initial design and implementation of the Model part of the **M**VC pattern. The roadmap ahead is concerned with the design and implementation of the View and Controller part of the M**VC** pattern. Unit tests of Model logic will be implemented with all parts of the missing or incomplete implementation of the placement and scoring of words having corresponding failing unit tests. 


### Design decisions
| Data Structure/Relevant Operation | Class | Reasoning |
|:-------:|----------|-------------------------------------------------------|
| Enums | Tile | Scrabble has a fixed amount of letters each with a constant value |
| Multidimensional Arrays | Board | The grid is a two-dimensional 15 by 15 fixed array|
| Collections.shuffle | Bag | The shuffle function randomizes the order tiles will be drawn in |
| random.nextInt | Game | Randomizes the player that goes first |




