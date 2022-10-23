//import java.util.Scanner;
import java.util.*;

/**
 * @author Arthur Atangana
 */
public class Player {
    private Board board;
    private Rack rack;
    private int score;
    private int turn;



    public Player(Board board, Bag bag){
        this.board = board;
        this.score = 0;
        this.turn = 0;
        this.rack = new Rack(bag);
    }

    public Rack getRack() {
        return rack;
    }
    public int getScore() {
        return score;
    }
    public int getTurn() {
        return turn;
    }
    public void placeLetter(String letter){
    }
    /**
     * Ends the player's turn.
     */
    public void endTurn(){
        rack.drawTiles();// draws tiles from bag onto the rack
        turn++; // keeps track of turns

    }

    /**
     * gets the rack score from rack
     * @return int
     */
    public int getRackScore() {
        return rack.getRackScore();
    }
    /**
     * adds the score of the placed word to score
     */
    public void addToScore(int leftOverLetterScore) {
    }

    /**
     *
     * @return
     */
    public boolean takeTurn() {
        //print board to be added
        System.out.println("It is your turn to play.");
        boolean validInput = false;
        boolean running = true;
        boolean pass = false;
        Scanner sc = new Scanner(System.in);
        String[] tileArray;

        do {
            System.out.println("Enter your word and coordinates (e.g C A2,A A3,T A4): ");
            String s = sc.nextLine();
            tileArray = s.toUpperCase().split(",");
            /*for (String tileString : tileArray){
                System.out.println(tileString);
            }*/
            switch (s) {
                case "pass":
                    System.out.println("You passed your turn");
                    pass = true;
                    running = false;
                    break;
                default:
                    for (String tileString : tileArray){
                        if (isInputValid(tileString)){
                            System.out.println("input is valid for "+ tileString);
                            if (!isTileInRack(tileString)){ //if tile is not in rack,
                                validInput = false;
                                break;
                            }
                            if (!isBoardSquareEmpty(tileString)){
                                validInput = false;
                                break;
                            }
                            validInput = true;
                        }
                        else{
                            System.out.println("invalid input, try again");
                            validInput = false;
                            break;
                        }
                    }
                    if (validInput) {
                        running = false;
                    }
                    else {
                        break;
                    }
            }
        } while (running);

        System.out.println("this got the right input");
        if (pass){
            System.out.println("passing turn");
            //endTurn();
            return true;
        }

        rack.placeTiles(tileArray);

        return true;
    }


    /**
     * checks if the input from the user was formatted properly where each tile is set as "A A1"
     * @param s: a string taken from the user's input on command line
     * @return true if the format is correct, false if not.
     */
    private boolean isInputValid(String s){
        System.out.println(s);
        if (s.length() ==5){
            return s.matches("[A-Z] [A-O]1[0-5]"); // handles inputs where coordinate is 10-15
        }
        return s.matches("[A-Z] [A-O][1-9][0-5]?");
    }

    /**
     * Checks if the tiles in the user input are in the player's rack
     * @param s: the tile to check if it is in the rack
     * @return true if the tile is in the rack, false if not.
     */
    private boolean isTileInRack(String s){//gets the tileString "A A2"
        for (Tile t : rack.getTilesList()){
            if (t.letter.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if board squares from user input are empty
     * @param s String from user input
     * @return true if squares are empty, false if any square is occupied.
     */
    private boolean isBoardSquareEmpty(String s){
        String x = Character.toString(s.charAt(2));
        String y = Character.toString(s.charAt(3));
        if (s.length() ==5){
            y = Character.toString(s.charAt(3))+s.charAt(4);
        }
        return board.isSquareEmpty(Column.valueOf(y),Row.valueOf(x));
    }

    /**
     * converts the player's input to a list of tiles and coordinates.
     * @param s: the string from the player's input
     * @return an array list of tiles and coordinates
     */
    private ArrayList<Tile> stringToTile(String s){
        //implementation with jasmine's row and columns


    }
    public static void main(String[] args) {
        //Player p = new Player();
        //p.takeTurn();
    }
}
