//import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Player {
    private Board board;
    private Rack rack;
    private int score;
    private String name;


    public Player(Board board, Bag bag){
        this.board = board;
        this.name = "";
        this.score = 0;
        this.rack = new Rack(bag);
    }

    public Player(Board board, Bag bag, int playerNumber) {
        this(board,bag);
        this.name = "Player " + playerNumber;
    }

    public Player(Board board, Bag bag,String name) {
        this(board, bag);
        this.name = name;
    }

    public String getName() {return name;}

    public Rack getRack() {
        return rack;
    }
    public int getScore() {
        return score;
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
     * Processes the turn of a player.
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status takeTurn() {
        System.out.println(board);//print board
        System.out.println(rack);
        System.out.printf("%s's turn to play.\n", name);
        boolean validInput = false;
        boolean running = true;
        boolean pass = false;
        ArrayList inputTiles = new ArrayList<>();
        ArrayList inputRows = new ArrayList<>();
        ArrayList inputColumns = new ArrayList<>();
        Scanner sc = new Scanner(System.in);



        do {
            System.out.println("Enter your word and coordinates (e.g C A THIRT,A A FOURT,T A FIFT) or enter 'pass': ");
            String s = sc.nextLine();

            switch (s.toUpperCase()) {
                case "PASS":
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
                        ArrayList<Coordinate> coordinates = new ArrayList<>();
                        coordinates = coordinatesList(inputRows,inputColumns); //creates list of coordinates
                        if (!board.placeTiles(coordinates,inputTiles)){ //if placeTiles fails, input again.
                            board.removeTiles(coordinates);
                            System.out.println("invalid input, try again");
                            validInput = false;
                            break;
                        }
                        int turnScore = board.submit(coordinates);
                        if (turnScore == -1){
                            System.out.println("Invalid placement, try again.");
                            board.removeTiles(coordinates);
                            System.out.println(board);//print board
                            System.out.println(rack);
                            validInput = false;
                            break;
                        }
                        else{
                            rack.removeTiles(inputTiles);
                            System.out.printf("Your word scored %d points!\n", turnScore);
                            score += turnScore;
                            System.out.printf("Your total score is now %d\n", score);
                            validInput = true;
                        }
                        //validInput = true;
                    }
            }
            if (validInput) {
                running = false;
            }
        }while(running);
        System.out.println("this is a valid input");
        if (pass){
            System.out.println("passing turn");
        }
        return endTurn();
    }

    /**
     * transforms the user input into a list of enums or returns "invalid"
     * @param s : the user input.
     * @param option : whether string2Enums should process tile, row, columns
     * @return a list of tile or row or column enums, or invalid if the input is wrong.
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
        //Player p = new Player();
        //p.takeTurn();
        //p.string2Lists("C A ONE,A A TWO,A A THREE", "rows");
    }
}
