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


### Authors   

Arthur Atangana ArthurAtangana@cmail.carleton.ca

Rebecca Elliott RebeccaElliott3@cmail.carleton.ca

Jasmine Gad El Hak JasmineGadElHak@cmail.carleton.ca

Victoria Malouf VictoriaMalouf@cmail.carleton.ca

### Version 

10/25/2022 - Milestone 1 

### Usage 

To start the game the number of players need to be specified.

When prompted, a player may pass or play their turn by entering a string of letters and coordinates into the terminal. 

Columns: A-F, Rows: ONE-FIFT 

    "A H EIGHT,N I EIGHT,D J EIGHT"
    
NOTE: All tiles the user is attempting to place during their turn must be included.  Correct formatting is required: 
> Letter Column Row,noSpace

### Rest of Deliverables

| Milestone | Due Date | Main Deliverables|
|:-------:|----------|-------------------------------------------------------|
| 2 | 11/11/2022 | **GUI-based version:** The display will be in a JFrame where user input occurs via the mouse. Unit tests for the Model with failing unit tests for incomplete functionality implementations will be provided. Changes to UML and data structures from Milestone 1 will be documented.|
| 3 | 11/21/2022 | **Fully functioning game with AI capabilities:** Tile placement validation, scoring, blank tile and premium square functionalities are included. AI players with the ability to play the highest scoring word will be implemented. Changes from Milestone 2 will be documented. (The code will smell like fresh flowers) 🌻 
| 4 | 12/05/2022 | **Undo, saving and customization features:** Ability to undo or redo moves at multiple levels. Ability to save and load the game using Java Serialization. Ability to customize boards by alternating the placement of premium squares. |

### Known issues


### Roadmap ahead


### Design decisions
| Data Structure | Class | Reasoning |
|:-------------:|------------|------------|
| Enums | Tile | Scrabble has a fixed amount of letters each with a constant value  |
| Multidimensional Arrays | Board | The grid is a two-dimensional 15 by 15 fixed array|




