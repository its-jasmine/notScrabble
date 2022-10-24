//import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Arthur Atangana
 */
public class Player {
    private Board board;
    private Rack rack;
    private int score;
    private int turn;
    private String name;
    

    public Player(Board board, Bag bag){
        this.board = board;
        this.name = "";
        this.score = 0;
        this.turn = 0;
        this.rack = new Rack(bag);
    }

    public Player(Board board, Bag bag, int playerNumber) {
        this(Board board, Bag bag);
        this.name = "Player " + playerNumber;
    }

    public Player(Board board, Bag bag,String name) {
        this(Board board, Bag bag);
        this.name = name;
    }

    public String getName() {return name;}

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
     * adds the score of the placed word to score
     */
    public void addToScore(int leftOverLetterScore) {
    }

    /**
     *
     * @return
     * Not sure what this needs
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status takeTurn() {
        System.out.println(board);//print board
        System.out.println("It is your turn to play.");
        boolean validInput = false;
        boolean running = true;
        boolean pass = false;
        ArrayList inputTiles = new ArrayList<>();
        ArrayList inputRows = new ArrayList<>();
        ArrayList inputColumns = new ArrayList<>();
        Scanner sc = new Scanner(System.in);



        do {
            System.out.println("Enter your word and coordinates (e.g C A THIRT,A A FOURT,T A FIFT): ");
            String s = sc.nextLine();
            switch (s) {
                case "pass":
                    System.out.println("You passed your turn");
                    pass = true;
                    running = false;
                    break;
                default:
                    inputTiles = string2Enums(s, "letters");
                    //System.out.println(inputTiles.toString());
                    inputColumns = string2Enums(s, "columns");
                    //System.out.println(inputColumns.toString());
                    inputRows = string2Enums(s, "rows");
                    //System.out.println(inputRows.toString());

                    if (inputColumns.contains(false) | inputRows.contains(false) | inputTiles.contains(false)) {
                        System.out.println("invalid input");
                        validInput = false;
                        break;
                    }
                    if (!isTileInRack(inputTiles)){
                        System.out.println("Some of the input tiles are not in the rack");
                        validInput = false;
                        break;
                    }
                    if (!isBoardSquareEmpty(inputRows,inputColumns)){
                        System.out.println("The coordinates are not available to place tiles");
                        validInput = false;
                        break;
                    }
                    else{
                        validInput = true;
                    }
            }
            if (validInput) {
                running = false;
            }
        }while(running);
        System.out.println("this got the right input");
        if (pass){
            System.out.println("passing turn");
            //endTurn();
            return endTurn();
        }
        for (int i = 0; i < inputTiles.size(); i++){
            Coordinate coordinates = new Coordinate((Coordinate.Column)inputColumns.get(i), (Coordinate.Row) inputRows.get(i));
            board.placeTile(coordinates,(Tile) inputTiles.get(i));
        }
        //could remove tiles from rack only when word was approved in endTurn()
        //rack.placeTiles(inputTiles);
        //for now this code assumes that placing will not result in an error since we checked if the squares are empty.
        // the code right now is not taking into account invalid words, or invalid placements, only invalid inputs and non-empty squares.
        //endTurn()

        return endTurn();
    }

    /**
     * gets a string to a list containing enums of either letters, rows or columns.
     * @param s the string to be transformed.
     * @param option picks letters, rows or columns enum
     * @return ArrayList [C, A, T]
     */
    private ArrayList<Object> string2Enums(String s, String option){
        ArrayList<String> letters = new ArrayList<String>();
        ArrayList<String> rows = new ArrayList<String>();
        ArrayList<String> columns = new ArrayList<String>();
        String[] tileArray;
        String[] tileEnums;
        tileArray = s.toUpperCase().split(",");
        for (String tileString : tileArray){
            tileEnums = tileString.split(" ");
            letters.add(tileEnums[0]);
            columns.add(tileEnums[1]);
            rows.add(tileEnums[2]);
            /*System.out.println(tileString);
            System.out.println(tileEnums[0]);
            System.out.println(tileEnums[1]);
            System.out.println(tileEnums[2]);*/

        }
        /*System.out.println(letters.toString());
        System.out.println(rows.toString());
        System.out.println(columns.toString());*/
        ArrayList temp = new ArrayList<>();
        ArrayList invalid = new ArrayList();
        invalid.add(false);
        //System.out.println(invalid.toString());
        switch (option){
            case "letters":
                for (String l: letters){
                    try{
                        temp.add(Tile.valueOf(l));
                    }
                    catch(Exception e){
                        return invalid;
                    }
                }
                return temp;
            case "rows":
                for (String r: rows){
                    try{
                        temp.add(Coordinate.Row.valueOf(r));
                    }
                    catch(Exception e){
                        return invalid;
                    }
                }
                return temp;
            default:
                for (String c: columns){
                    try{
                        temp.add(Coordinate.Column.valueOf(c));
                    }
                    catch(Exception e){
                        return invalid;
                    }
                }
                return temp;
        }
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

    public static void main(String[] args) {
        Player p = new Player();
        p.takeTurn();
        //p.string2Lists("C A ONE,A A TWO,A A THREE", "rows");
    }
}
