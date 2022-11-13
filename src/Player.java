//import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Represents a player in the game.
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Player {
    /** The board of the game the player is participating in */
    private Board board;
    /** The player's rack */
    private Rack rack;
    /** The player's current score */
    private int score;
    
    private String name;
    private List<PlayerView> views;


    /**
     * Creates a new player with a specified board and bag.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     */
    public Player(Board board, Bag bag){
        views = new ArrayList<>();
        this.board = board;
        this.name = "";
        this.score = 0;
        this.rack = new Rack(bag);
    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param playerNumber the index of the player being created
     */
    public Player(Board board, Bag bag, int playerNumber) {
        this(board,bag);
        this.name = "Player " + playerNumber;
    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param name of the player
     */
    public Player(Board board, Bag bag,String name) {
        this(board, bag);
        this.name = name;
    }

    /**
     * Returns the name of the player.
     * @return name of the player
     */
    public String getName() {return name;}

    /**
     * Returns the player's rack.
     * @return the player's rack
     */
    public Rack getRack() {
        return rack;
    }
    /**
     * Returns the player's score.
     * @return the player's score
     */
    public int getScore() {
        return score;
    }
    /**
     * Adds to the player's score.
     * @param score The score to add.
     */
    public void addToScore(int score) {
        score += score;
    }
    /**
     * Ends the player's turn.
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status endTurn(){
        return rack.drawTiles();// draws tiles from bag onto the rack
    }

    /**
     * gets the rack score from rack
     * @return int
     */
    public int getRackScore() {
        return rack.getRackScore();
    }

    /**
     * checks if board squares from user input are empty
     * @param columns  list of coordinate columns
     * @param rows list of coordinate rows.
     * @return true if squares are empty, false if any square is occupied.
     */
    private boolean isBoardSquareEmpty(ArrayList<Coordinate.Row> rows, ArrayList<Coordinate.Column> columns){
        boolean areAllEmpty = false;
        for (int i = 0; i< rows.size(); i++){
            Coordinate coordinates = new Coordinate(columns.get(i),rows.get(i));
            areAllEmpty = board.isSquareEmpty(coordinates);
        }
        return areAllEmpty;
    }
    public void addView(PlayerView view){
        views.add(view);
    }
    public boolean submit(){
        board.submit(); // we will update board to have internal list of tiles, no need for arg
    }

    /**
     * Checks if the tiles in the user input are in the player's rack
     * @param lettersList : list of tiles to check if in rack.
     * @return true if the tiles are in the rack, false if not.
     */
    private boolean isTileInRack(ArrayList<Tile> lettersList){//gets the tileString "A A2"
        boolean isInRack = false;
        for (Tile letter: lettersList){
            isInRack = rack.isTileinRack(letter);
        }
        return isInRack;
    }

    /**
     * Transforms the lists of rows and columns into coordinates
     * @param rows : a list of rows enum.
     * @param columns : a list of columns enums.
     * @return an arrayList of coordinate objects
     */
    private ArrayList<Coordinate> coordinatesList(ArrayList rows, ArrayList columns){
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++){
            Coordinate c = new Coordinate((Coordinate.Column)columns.get(i), (Coordinate.Row) rows.get(i));
            coordinates.add(c);
        }
        return coordinates;
    }

}
