import java.util.*;

/**
 * BLURB ABOUT BOARD GOES HERE
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 * @author Victoria Malouf
 * @version Milestone1
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
     * Calls all the functions needed to validated and score words created this turn.
     * @param tilesPlaced the tiles the player has placed this turn
     * @return -1 if any validation fails (player tries again), otherwise returns the score for the turn
     */
    public int submit(List<Coordinate> tilesPlaced) {
        if (isValidTileAlignment(tilesPlaced) == null) return -1;
        // at this point tilesPlaced is now sorted and direction is set

        List<Word> words = getWordsCreated(tilesPlaced);

        if (!Word.areValidWords(words)) {
            System.out.println("One or more of the words created was invalid.");
            return -1;
        }

        // at this point words are all valid
        int score = Word.scoreWords(words);

        direction = Direction.UNKNOWN; // reset for next turn
        return score;
    }


    /**
    * Determine the direction for this turn if tiles the player is attempting to place are straight.
    * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
    * @return direction of the tiles (HORIZONTAL or VERTICAL), UNKNOWN otherwise
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
     * Determine if the tiles the player is attempting to place are connected to an existing tile.
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if the sorted tiles are attached to another tile, false otherwise
     */
    private boolean verifyWordAttachment(List<Coordinate> tilesPlacedCoordinates){
        Coordinate firstTileCoordinate = tilesPlacedCoordinates.get(0);
        Coordinate lastTileCoordinate = tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1);
        if (direction == Direction.HORIZONTAL) {
            // Is there a tile to the left of the first tile played?
            Coordinate toLeft = firstTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
            if (toLeft != null && !isSquareEmpty(toLeft)) {return true;}
            // For each tile played is there a letter above or below?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate above = c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
                if (above != null && !isSquareEmpty(above)) {return true;}
                Coordinate below = c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                if (below != null && !isSquareEmpty(below)) {return true;}
            }
            // Is there a tile to the right of the last tile played?
            Coordinate toRight = lastTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
            return toRight != null && !isSquareEmpty(toRight);
        }
        else {
            // Is there a tile above the first tile played?
            Coordinate above = firstTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
            if (above != null && !isSquareEmpty(above)) {return true;}
            // For each tile played is there a letter right or left?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate left = c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
                if (left != null && !isSquareEmpty(left)) {return true;}
                Coordinate right = c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
                if (right != null && !isSquareEmpty(right)) {return true;}
            }
            // Is there a tile to below the last tile played?
            Coordinate below = lastTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
            return below != null && !isSquareEmpty(below);
        }
    }

    /**
     * Determine if each square between the first and last tile played is NOT empty.
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if the sorted tiles placement do no have gaps, false otherwise
     */
    private boolean verifyNoGaps(List<Coordinate> tilesPlacedCoordinates){
        Coordinate coordinate = tilesPlacedCoordinates.get(0); // first coordinate at this point
        Coordinate lastCoordinate = tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1);
        if (direction == Direction.HORIZONTAL){
            for (int i = coordinate.getColumnIndex(); i < lastCoordinate.getColumnIndex(); i++){ // first and last coordinates are not checked because we know they are not empty
                coordinate = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT); // we are only checking square that we know are on the board so null check is not required
                if (isSquareEmpty(coordinate)) return false;
            }
        }
        else {
            for (int i = coordinate.getRowIndex(); i < lastCoordinate.getRowIndex(); i++){
                coordinate = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                if (isSquareEmpty(coordinate)) return false;
            }
        }
        return true;
    }

    /**
     * Determine if one of the coordinates attempting to be placed is the start square coordinate.
     * @param tileCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if one of the tilesPlacedCoordinates land on the start square, false otherwise
     */
    private boolean isOnStart(List<Coordinate> tileCoordinates){
        for (Coordinate c: tileCoordinates){
            // Start square is at Coordinate(7,7)
            if ((c.getRowIndex() == 7) && (c.getColumnIndex() == 7)) return true; // for milestone2 "if (getSquare(c).getType() == Square.Type.START)"
        }
        return false;
    }

    /**
     * Checks if the tiles placed this turn are straight, leave no gaps, and touch a word that was already played
     * sets the direction field for this turn if tiles are straight.
     * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
     * @return a sorted list of tiles played if the alignment is valid, null otherwise
     */
    private List<Coordinate> isValidTileAlignment(List<Coordinate> tilesPlacedCoordinates) {
        // Determine direction
        Direction d = getDirection(tilesPlacedCoordinates);
        if (d == Direction.UNKNOWN) return null;
        else direction = d;

        // Sort tiles
        if (direction == Direction.HORIZONTAL) Coordinate.sortByColumn(tilesPlacedCoordinates);
        else Coordinate.sortByRow(tilesPlacedCoordinates);

        // Check if there are any gaps between tiles placed
        // We have confirmed that the tiles placed are straight, therefore the sorted tiles can be horizontal OR vertical
        if ((tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1).getColumnIndex() - tilesPlacedCoordinates.get(0).getColumnIndex() + 1))
                || (tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1).getRowIndex() - tilesPlacedCoordinates.get(0).getRowIndex() + 1))) {
            // verify word attachment
            if (verifyWordAttachment(tilesPlacedCoordinates)) return tilesPlacedCoordinates;
        }

        // If there are gaps between tiles, check if each square between the first and last tile played NOT empty
        else if (verifyNoGaps(tilesPlacedCoordinates)) {
            return tilesPlacedCoordinates;
        }

        // Check if this is the first word being played
        if (isOnStart(tilesPlacedCoordinates)) return tilesPlacedCoordinates;

        // At this point, the Coordinate placements are invalid
        return null;
    }


    /**
     * Places tile in square if available.
     * @param coordinate of the tile being placed
     * @return true if letter was placed, false otherwise
     */
    private boolean placeTile(Coordinate coordinate, Tile tile) {
        Square square = getSquare(coordinate);
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
     * Removes and returns the Tile in a square if available.
     * @param coordinate of the Tile being removed
     * @return the tile if it was removed, null otherwise
     */
    private Tile removeTile(Coordinate coordinate) {
        return  getSquare(coordinate).removeTile();
    }

    /**
     * removes tiles from the board
     * @param tiles : a list of coordinates for the tiles to be removed
     * @return a list a removed tiles;
     */
    public ArrayList<Tile> removeTiles(ArrayList<Coordinate> tiles){
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
    private Tile getSquareTile(Coordinate coordinate) {
        return getSquare(coordinate).getTile();
    }

    /**
     * Gets the type of square at a give coordinate.
     * @param coordinate of the square being checked
     * @return the squares type
     */
    private Square.Type getSquareType(Coordinate coordinate) {
        return getSquare(coordinate).getType();
    }

    /**
     * Gets all the words that were created this turn.
     * @param tilesPlayed a sorted list of the tile coordinates played this turn, coordinates are confirmed in a line
     * @return list of words created this turn
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
        return words; // this can be an empty ArrayList in the scenario a player plays one Tile to start the game
    }

    /**
     * Gets any horizontal word, can be one letter long at this point.
     * @param startSearch the location on the board to search from
     * @return word that was created
     */
    private Word getHorizontalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateToLeft = startSearch.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);

        // adds letter to the front of the word
        while (coordinateToLeft != null && !getSquare(coordinateToLeft).isEmpty()) { // if coordinateToLeft is null we are at the left edge of the board
            word.addNode(getSquareTile(coordinateToLeft), getSquareType(coordinateToLeft));
            coordinateToLeft = coordinateToLeft.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
        }

        Coordinate coordinateToRight = startSearch;
        //adds letters to the end of the word
        while (coordinateToRight != null && !getSquare(coordinateToRight).isEmpty()) {  // if coordinateToRight is null we are at the Right edge of the board
            word.addNode(getSquareTile(coordinateToRight), getSquareType(coordinateToRight));
            coordinateToRight = coordinateToRight.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
        }

        return word;
    }

    /**
     * Gets any vertical word, can be one letter long at this point.
     * @param startSearch the location on the board to search from
     * @return word that was created
     */
    private Word getVerticalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateAbove = startSearch.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);

        // adds letter to the front of the word
        while (coordinateAbove != null && !getSquare(coordinateAbove).isEmpty()) { // if coordinateAbove is null we are at the top of the board
            word.addNode(getSquareTile(coordinateAbove), getSquareType(coordinateAbove));
            coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        }

        Coordinate coordinateBelow = startSearch;
        //adds letters to the end of the word
        while (coordinateBelow != null && !getSquare(coordinateBelow).isEmpty()) { // if coordinateBelow is null we are at the bottom of the board
            word.addNode(getSquareTile(coordinateBelow), getSquareType(coordinateBelow));
            coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        }

        return word;
    }

    /**
     * Returns string representation of board.
     * @return string of current board state
    */
    public String toString() {
        String s = "";
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                s += squares[r.ordinal()][c.ordinal()] + " "; // Each square will be separated by a space.
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
