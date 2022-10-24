import java.util.*;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 */
public class Board {
    private static Square[][] squares; // [row][column]
    private Direction direction; // keeps track of the direction of the tiles that were placed, set in alignment check

    private enum Direction {HORIZONTAL, VERTICAL, UNKNOWN}


    public Board() {
        squares = new Square[Coordinate.Row.values().length][Coordinate.Column.values().length];
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                squares[r.ordinal()][c.ordinal()] = new Square();
            }
        }
        direction = Direction.UNKNOWN;
    }

    private Square getSquare(Coordinate coordinate) {
        return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
    }


    /**
    * Determine the direction field for this turn if tiles are straight
    * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
    * @return Direction of the tiles (HORIZONTAL or VERTICAL), UNKNOWN otherwise
    */
    private Direction getDirection(List<Coordinate> tilesPlacedCoordinates){
        // Get the sorted rows
        Set<Integer> rowSet = new HashSet<>();
        for (Coordinate c : tilesPlacedCoordinates) rowSet.add(c.getRowIndex());
        if (rowSet.size()==1) return Direction.HORIZONTAL;
        else {
            // Get the sorted columns
            Set<Integer> columnSet = new HashSet<>();
            for (Coordinate c : tilesPlacedCoordinates) columnSet.add(c.getColumnIndex());
            if (columnSet.size()==1) return Direction.VERTICAL;
            else return Direction.UNKNOWN;
        }
    }
    /**
     * Determine if the tiles the player is attempting to place are connected to an existing tile
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is attempting to place this turn
     * @return true if the sorted tiles are attached to another tile, false otherwise
     */
    private boolean verifyWordAttachment(List<Coordinate> tilesPlacedCoordinates){
        if (direction == Direction.HORIZONTAL) {
            // Is there a tile to the left of the first tile played?
            Coordinate c = new Coordinate(tilesPlacedCoordinates.get(0).getColumnIndex() - 1, tilesPlacedCoordinates.get(0).getRowIndex());
            if (!isSquareEmpty(c)) {return true;}
            // For each tile played is there a letter above or below?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate above = new Coordinate(c.getColumnIndex(), c.getRowIndex() - 1);
                Coordinate below = new Coordinate(c.getColumnIndex(), c.getRowIndex() + 1);
                if (!isSquareEmpty(above)) {return true;}
                if (!isSquareEmpty(below)) {return true;}
            }
            // Is there a tile to the right of the last tile played?
            Coordinate c = new Coordinate(tilesPlacedCoordinates.get(-1).getColumnIndex() + 1, tilesPlacedCoordinates.get(-1).getRowIndex());
            if (!isSquareEmpty(c)) {return true;}
        }
        else {
            // Is there a tile above the first tile played?
            Coordinate c = new Coordinate(tilesPlacedCoordinates.get(0).getColumnIndex(), tilesPlacedCoordinates.get(0).getRowIndex() - 1);
            if (!isSquareEmpty(c)) {return true;}
            // For each tile played is there a letter right or left?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate left = new Coordinate(c.getColumnIndex() - 1, c.getRowIndex());
                Coordinate right = new Coordinate(c.getColumnIndex() + 1, c.getRowIndex());
                if (!isSquareEmpty(left)) {return true;}
                if (!isSquareEmpty(right)) {return true;}
            }
            // Is there a tile to below the last tile played?
            Coordinate c = new Coordinate(tilesPlacedCoordinates.get(-1).getColumnIndex(), tilesPlacedCoordinates.get(-1).getRowIndex() + 1);
            if (!isSquareEmpty(c)) {return true;}
        }
        return false;
    }

    /**
     * Determine if is each square between the first and last tile played is NOT empty
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is attempting to place this turn which are confirmed to be in a straight line
     * @return true if the sorted tiles placement do no have gaps, false otherwise
     */
    private boolean verifyNoGaps(List<Coordinate> tilesPlacedCoordinates){
        if (direction == Direction.HORIZONTAL){
            int rowIndex =  tilesPlacedCoordinates.get(0).getRowIndex();
            for (int i = tilesPlacedCoordinates.get(0).getColumnIndex(); i <= tilesPlacedCoordinates.get(-1).getColumnIndex(); i++){
                if (isSquareEmpty(new Coordinate(i, rowIndex))) return false;
            }
        }
        else {
            int columnIndex = tilesPlacedCoordinates.get(0).getColumnIndex();
            for (int i = tilesPlacedCoordinates.get(0).getRowIndex(); i <= tilesPlacedCoordinates.get(-1).getRowIndex(); i++){
                if (isSquareEmpty(new Coordinate(columnIndex, i))) return false;
            }
        }
        return true;
    }

    /**
     * Determine if one of the coordinates attempting to be placed is the start square coordinate
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is attempting to place this turn which are confirmed to be in a straight line
     * @return true if one of the tilesPlacedCoordinates land on the start square, false otherwise
     */
    private boolean isOnStart(List<Coordinate> tilesPlacedCoordinates){
        for (Coordinate c: tilesPlacedCoordinates){
            // Start square is at Coordinate(8,8)
            if ((c.getRowIndex() == 8) && (c.getColumnIndex() == 8)) return true;
        }
        return false;
    }

    /**
     * Checks if the tiles placed this turn are straight, leave no gaps, and touch a word that was already played
     * sets the direction field for this turn if tiles are straight
     * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
     * @return a sorted list of tiles played if the alignment is valid, null otherwise
     */
    private List<Coordinate> isValidTileAlignment(List<Coordinate> tilesPlacedCoordinates) {
        // Determine direction
        Direction d = getDirection(tilesPlacedCoordinates);
        if (d == Direction.UNKNOWN) return null;
        else direction = d;

        // Sort tiles
        if (direction == Direction.HORIZONTAL) Coordinate.sortByRow(tilesPlacedCoordinates);
        else Coordinate.sortByColumn(tilesPlacedCoordinates);

        // Check if there are any gaps between tiles placed
        // We have confirmed that the tiles placed are straight, therefore the sorted tiles can be horizontal OR vertical
        if ((tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(-1).getColumnIndex() - tilesPlacedCoordinates.get(0).getColumnIndex() + 1))
                || (tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(-1).getRowIndex() - tilesPlacedCoordinates.get(0).getRowIndex() + 1))) {
            // verify word attachment
            if (verifyWordAttachment(tilesPlacedCoordinates)) return tilesPlacedCoordinates;
        }

        // If there are gaps between tiles, check if each square between the first and last tile played NOT empty
        else if (verifyNoGaps(tilesPlacedCoordinates)) {
            return tilesPlacedCoordinates;
        }

        // Check if this is the first word being played
        else {
            if (isOnStart(tilesPlacedCoordinates)) return tilesPlacedCoordinates;
        }

        // At this point, the Coordinate placements are invalid
        return null;
    }

    /**
     * calls all the functions needed to validated and score words created this turn
     *
     * @param tilesPlaced the tiles the player is attempting to place this turn
     * @return -1 if any validation fails (player tries again), otherwise returns the score for the turn
     */
    public int submit(List<Coordinate> tilesPlaced) {
        if (isValidTileAlignment(tilesPlaced) == null) return -1;
        // at this point tilesPlaced is now sorted and direction is set

        List<Word> words = getWordsCreated(tilesPlaced); // each node has a Tile and Square type

        if (!Word.areValidWords(words)) return -1;
        // at this point words are all valid

        int score = Word.scoreWords(words);

        direction = Direction.UNKNOWN; // reset for next turn
        return score;
    }

    /**
     * Places tile in square if available
     *
     * @param coordinate of the tile being placed
     * @return true if letter was placed, false otherwise
     */
    private boolean placeTile(Coordinate coordinate, Tile tile) {
        Square square = squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
        if (square.isEmpty()) {
            square.placeTile(tile);
            return true;
        }
        return false;
    }
    public boolean placeTiles(ArrayList<Coordinate> coordinates, ArrayList<Tile> tiles){
        for (int i = 0; i < coordinates.size(); i++){
            if (!placeTile(coordinates.get(i),tiles.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Removes and returns the Tile in a square if available
     *
     * @param coordinate of the Tile being removed
     * @return the tile if it was removed, null otherwise
     */
    public Tile removeTile(Coordinate coordinate) {
        Square square = squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
        if (!square.isEmpty()) {
            return square.removeTile();
        }
        return null;
    }

    public ArrayList<Tile> removeTiles(ArrayList<Coordinate> tiles){
        ArrayList<Tile> tilesLst = new ArrayList<>();
        for (Coordinate c : tiles){
            tilesLst.add(removeTile(c));
        }
        return tilesLst;
    }

    /**
     * checks if the square has a tile in it already
     *
     * @param coordinate of the square being checked
     * @return true if the square has no Tile yet, false otherwise
     */
    public boolean isSquareEmpty(Coordinate coordinate) {
        return getSquare(coordinate).isEmpty();
    }

    /**
     * Gets the tile on a square without removing it
     *
     * @param coordinate of the square being checked
     * @return the Tile or null
     */

    public Tile getSquareTile(Coordinate coordinate) {
        return getSquare(coordinate).getTile();
    }

    public Square.Type getSquareType(Coordinate coordinate) {
        return getSquare(coordinate).getType();
    }

    /**
     * finds all the words that were created this turn
     *
     * @param tilesPlayed a sorted list of the tiles played this turn
     * @return list of a words stored in a double linked list. nodes store Tiles and Square type
     */
    private List<Word> getWordsCreated(List<Coordinate> tilesPlayed) {
        List<Word> words = new ArrayList<>();

        if (direction == Direction.HORIZONTAL) {
            // get word played, possibly extending previously played word
            Word word = getHorizontalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word); // to be counted as a word it must be at least two letters long

            // get any newly formed vertical words
            for (Coordinate c : tilesPlayed) {
                word = getVerticalWord(c);
                if (word.size() > 1) words.add(word);
            }

        } else if (direction == Direction.VERTICAL) {
            // get word played, possibly extending previously played word
            Word word = getVerticalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word);

            // get any newly formed horizontal words
            for (Coordinate c : tilesPlayed) {
                word = getHorizontalWord(c);
                if (word.size() > 1) words.add(word);
            }
        }
        return words;
    }

    /**
     * gets any horizontal word, can be one letter long at this point
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private Word getHorizontalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateToLeft = startSearch.getAdjacentCoordinate("L");

        // adds letter to the front of the word
        while (coordinateToLeft != null && !getSquare(coordinateToLeft).isEmpty()) { // if coordinateToLeft is null we are at the left edge of the board
            word.addNode(getSquareTile(coordinateToLeft), getSquareType(coordinateToLeft));
            coordinateToLeft = coordinateToLeft.getAdjacentCoordinate("L");
        }

        Coordinate coordinateToRight = startSearch;
        //adds letters to the end of the word
        while (coordinateToRight != null && !getSquare(coordinateToRight).isEmpty()) {  // if coordinateToRight is null we are at the Right edge of the board
            word.addNode(getSquareTile(coordinateToRight), getSquareType(coordinateToRight));
            coordinateToRight = coordinateToRight.getAdjacentCoordinate("R");
        }

        return word;
    }

    /**
     * gets any vertical word, can be one letter long at this point
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private Word getVerticalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateAbove = startSearch.getAdjacentCoordinate("A");

        // adds letter to the front of the word
        while (coordinateAbove != null && !getSquare(coordinateAbove).isEmpty()) { // if coordinateAbove is null we are at the top of the board
            word.addNode(getSquareTile(coordinateAbove), getSquareType(coordinateAbove));
            coordinateAbove = coordinateAbove.getAdjacentCoordinate("A");
        }

        Coordinate coordinateBelow = startSearch;
        //adds letters to the end of the word
        while (coordinateBelow != null && !getSquare(coordinateBelow).isEmpty()) { // if coordinateBelow is null we are at the bottom of the board
            word.addNode(getSquareTile(coordinateBelow), getSquareType(coordinateBelow));
            coordinateBelow = coordinateBelow.getAdjacentCoordinate("B");
        }

        return word;
    }


    public String toString() {
        String s = "";
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                s += squares[r.ordinal()][c.ordinal()] + " ";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        Board b = new Board();
        System.out.print(b);
    }
}
