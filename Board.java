import java.util.*;

/**
 * Represents a 15x15-grid board where the tiles of the game are played.
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 * @author Victoria Malouf
 * @version Milestone2
 */
public class Board {
    /** The list of squares on the board */
    private static Square[][] squares; // [row][column]
    /** The direction of the word currently be validated */
    private final BoardValidator boardValidator = new BoardValidator(this);

    private final WordExtractor wordExtractor = new WordExtractor(this);
    private Tile tileBeingPlaced;
    private List<Coordinate> placedTilesCoordinates;
    private List<BoardView> views;


    /**
     * Creates a new board with plain squares.
     */
    public Board() {
        placedTilesCoordinates = new ArrayList<>();
        views = new ArrayList<>();
        squares = new Square[Coordinate.Row.values().length][Coordinate.Column.values().length];
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                squares[r.ordinal()][c.ordinal()] = new Square();
            }
        }
    }

    /**
     * Gets the square at the specified coordinate on the board.
     * @param coordinate of the Square to retrieve
     * @return Square at specified coordinate
     */
    public Square getSquare(Coordinate coordinate) {
        return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
    }


    /**
     * Calls all the methods needed to validated and score words created this turn.
     * @param tilesPlaced the tiles the player has placed this turn
     * @return -1 if any validation fails (player should try again), otherwise returns the score for the turn
     */
    public int submit(List<Coordinate> tilesPlaced) {
        BoardValidator.Direction d = boardValidator.isValidTileAlignment(tilesPlaced);
        if ( d == BoardValidator.Direction.UNKNOWN) return -1;
        // at this point tilesPlaced is now sorted and direction is HORIZONTAL or VERTICAL

        List<Word> words = wordExtractor.getWordsCreated(tilesPlaced, d);
        if (words.size() == 0) { // no words, at least two letters long, were created
            System.out.println("Words must be at least two letters long."); // only possible at game start
            return -1;
        }

        if (!Word.areValidWords(words)) {
            System.out.println("One or more of the words created was invalid.");
            return -1;
        }

        // at this point words are all valid
        placedTilesCoordinates.removeAll(tilesPlaced);
        int score = Word.scoreWords(words);
        if (tilesPlaced.size() == 7) score += 50;
        return score;
    }
    public void tileToPlace(Tile t){
        tileBeingPlaced = t;
        System.out.println(tileBeingPlaced+ " in board class");
    }
    public Tile getTileToPlace() {
        return tileBeingPlaced;
    }
    public void resetTiletoPlace() {
        tileBeingPlaced = null;
    }
    public Tile giveTileBackToRack(Tile t){
        resetTiletoPlace();
        return t;

    }
    /**
     * Places tile in square if available.
     * @param coordinate of the tile being placed
     * @param tile to be placed
     * @return true if letter was placed, false otherwise
     */
    public boolean placeTile(Coordinate coordinate, Tile tile) {
        Square square = getSquare(coordinate);
        if (square.isEmpty()) {
            square.placeTile(tile);
            placedTilesCoordinates.add(coordinate);
            for (BoardView view : views){
                view.addTileToBoardView(new BoardEvent(this,tile,coordinate));
            }
            return true;
        }
        return false;
    }


    /**
     * Places all the tiles at the specified coordinates.
     * @param coordinates of the tiles to be placed.
     * @param tiles to be placed on the board.
     * @return true if all tiles are successfully placed on the board, false otherwise
     */
    public boolean placeTiles(ArrayList<Coordinate> coordinates, ArrayList<Tile> tiles){
        for (int i = 0; i < coordinates.size(); i++){
            if (!placeTile(coordinates.get(i),tiles.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Removes and returns the Tile in a square if available.
     * @param coordinate of the Tile being removed
     * @return the tile if it was removed, null otherwise
     */
    private Tile removeTile(Coordinate coordinate) {
        for (BoardView view : views){
            view.removeTileFromBoardView(new BoardEvent(this,coordinate));
        }
        placedTilesCoordinates.remove(coordinate);
        return  getSquare(coordinate).removeTile();
    }

    /**
     * removes tiles from the board
     * @param tileCoordinates : a list of coordinates for the tiles to be removed
     * @return a list a removed tiles;
     */
    public ArrayList<Tile> removeTiles(ArrayList<Coordinate> tileCoordinates){
        ArrayList<Tile> tilesLst = new ArrayList<>();
        for (Coordinate c : tileCoordinates){
            tilesLst.add(removeTile(c));
        }
        return tilesLst;
    }

    /**
     * Checks if the square has a tile in it already.
     * @param coordinate of the square being checked
     * @return true if the square has no Tile yet, false otherwise
     */
    public boolean isSquareEmpty(Coordinate coordinate) {
        return getSquare(coordinate).isEmpty();
    }

    /**
     * Gets the tile on a square without removing it.
     * @param coordinate of the square being checked
     * @return the Tile or null if square has no tile
     */
    public Tile getSquareTile(Coordinate coordinate) {
        return getSquare(coordinate).getTile();
    }

    /**
     * Gets the type of square at a give coordinate.
     * @param coordinate of the square being checked
     * @return the squares type
     */
    public Square.Type getSquareType(Coordinate coordinate) {
        return getSquare(coordinate).getType();
    }



    /**
     * Returns string representation of board.
     * @return string of current board state
    */
    public String toString() {
        String s = "   ";
        for (Coordinate.Column c : Coordinate.Column.values()) {
            s += c + "  ";
        }
        s += "\n";
        for (Coordinate.Row r : Coordinate.Row.values()) {
            int row = r.ordinal() + 1;
            if(row < 10) s += row + "  "; else s += row + " "; // keeps columns straight
            for (Coordinate.Column c : Coordinate.Column.values()) {
                s += squares[r.ordinal()][c.ordinal()] + "  "; // Each square will be separated by two spaces.
            }
            s += "\n";
        }
        return s;
    }


    public List<Coordinate> getCoordinateList() {
        return placedTilesCoordinates;
    }
}
