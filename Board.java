import java.util.*;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 */
public class Board {
    private static Square[][] squares; // [row][column]

    private Direction direction; // keeps track of the direction of the tiles that were placed, set in alignment check
    private enum Direction {HORIZONTAL, VERTICAL, UNKNOWN}

    private class Node {
        public Tile tile;
        public Square.Type type;


        public Node(Tile tile, Square.Type type) {
            this.tile = tile;
            this.type = type;
        }
    }

    public Board() {
        squares = new Square[Coordinate.Row.values().length][Coordinate.Column.values().length];
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                squares[r.ordinal()][c.ordinal()] = new Square();
            }
        }
        direction = Direction.UNKNOWN;
    }

    private Square getSquare(Coordinate coordinate) {return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];}


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
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is attempting to place this turn
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
     * @param tilesPlaced the tiles the player is attempting to place this turn
     * @return -1 if any validation fails (player tries again), otherwise returns the score for the turn
     */
    public int submit(List<Coordinate> tilesPlaced) {
        if(isValidTileAlignment(tilesPlaced) == null) return -1;
        // at this point tilesPlaced is now sorted and direction is set

        List<LinkedList> words = getWordsCreated(tilesPlaced); // each node has a Tile and Square type

        if(!areValidWords(words)) return -1;
        // at this point words are all valid

        int score = scoreWords(words);

        direction = Direction.UNKNOWN; // reset for next turn
        return score;
    }

    /**
     * Places tile in square if available
     * @param coordinate of the tile being placed
     * @return true if letter was placed, false otherwise
     */
    public boolean placeTile(Coordinate coordinate, Tile tile) {
        Square square = squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
        if (square.isEmpty()) {
            square.placeTile(tile);
            return true;
        }
        return false;
    }

    /**
     * Removes and returns the Tile in a square if available
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


    /**
     * checks if the square has a tile in it already
     * @param coordinate of the square being checked
     * @return true if the square has no Tile yet, false otherwise
     */
    private boolean isSquareEmpty(Coordinate coordinate) {
        return getSquare(coordinate).isEmpty();
    }

    /**
     * Gets the tile on a square without removing it
     * @param coordinate of the square being checked
     * @return the Tile or null
     */

    public Tile getSquareTile(Coordinate coordinate) {
        return getSquare(coordinate).getTile();
    }

    public Square.Type getSquareType(Coordinate coordinate) {return getSquare(coordinate).getType();}

    /**
     * finds all the words that were created this turn
     * @param tilesPlayed a sorted list of the tiles played this turn
     * @return list of a words stored in a double linked list. nodes store Tiles and Square type
     */
    private List<LinkedList> getWordsCreated(List<Coordinate> tilesPlayed) {
        List<LinkedList> words = new ArrayList<>();

        if (direction == Direction.HORIZONTAL) {
            // get word played, possibly extending previously played word
            LinkedList word = getHorizontalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word);

            // get any newly formed vertical words
            for (Coordinate c: tilesPlayed) {
                word = getVerticalWord(c);
                if (word.size() > 1) words.add(word);
            }

        }
        else if (direction == Direction.VERTICAL) {
            // get word played, possibly extending previously played word
            LinkedList word = getVerticalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word);

            // get any newly formed horizontal words
            for (Coordinate c: tilesPlayed) {
                word = getHorizontalWord(c);
                if (word.size() > 1) words.add(word);
            }
        }
        return words;
    }

    /**
     * gets any horizontal word that is longer than one letter
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private LinkedList getHorizontalWord(Coordinate startSearch) {
        LinkedList word = new LinkedList();
        Coordinate currentCoordinate = startSearch;
        Coordinate coordinateToLeft = new Coordinate(currentCoordinate.column.previous(), currentCoordinate.row);
        Coordinate temp = coordinateToLeft;

        // adds letter to the front of the word
        while (!getSquare(coordinateToLeft).isEmpty()) {
            word.add(new Node(getSquareTile(coordinateToLeft), getSquareType(coordinateToLeft)));
        }

        currentCoordinate = temp;
        Coordinate coordinateToRight = new Coordinate(currentCoordinate.column.next(), currentCoordinate.row);
        //adds letters to the end of the word
        while (!getSquare(coordinateToRight).isEmpty()) {
            word.add(new Node(getSquareTile(coordinateToRight), getSquareType(coordinateToRight)));
        }

        return word;
    }

    /**
     * gets any vertical word that is longer than one letter
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private LinkedList getVerticalWord(Coordinate startSearch) {
        LinkedList word = new LinkedList();
        Coordinate currentCoordinate = startSearch;
        Coordinate coordinateAbove = new Coordinate(currentCoordinate.column, currentCoordinate.row.previous());
        Coordinate temp = coordinateAbove;

        // adds letter to the front of the word
        while (!getSquare(coordinateAbove).isEmpty()) {
            word.add(new Node(getSquareTile(coordinateAbove), getSquareType(coordinateAbove)));
        }

        currentCoordinate = temp;
        Coordinate coordinateBelow = new Coordinate(currentCoordinate.column, currentCoordinate.row.next());
        //adds letters to the end of the word
        while (!getSquare(coordinateBelow).isEmpty()) {
            word.add(new Node(getSquareTile(coordinateBelow), getSquareType(coordinateBelow)));
        }

        return word;
    }

    /**
     * Calculates the score for all the words created this turn
     * @param words a list of LinkList where each node has the Tile, and square type
     * @return total score
     */
    private int scoreWords(List<LinkedList> words) {
        int score = 0;
        for (LinkedList w : words) {
            score += scoreWord(w);
        }
        return score;
    }

    /**
     * Calculates the score of a single word
     * @return word score
     */
    private int scoreWord(LinkedList word) {
        return -1; // TODO:  
    }

    /**
     * gets the letter from each node in the linked list
     * @param llWord linkedList form of a word
     * @return the word as a String
     */
    private String llToString(LinkedList llWord) {
        String sWord = "";
        llWord.forEach(node -> sWord += node.tile));
        return sWord;
    }
        

    public String toString(){
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

    /**
     * checks if the words are in wordBank
     * @param words to check
     * @return true if all are valid, false otherwise
     */
    private boolean areValidWords(List<LinkedList> words) {
        for (LinkedList w : words) {
            if(!isValidWord(llToString(w))) return false;
        }
        return true;
    }


    /**
     * checks if the word is in wordBank
     * @param word to check
     * @return true if valid, false otherwise
     */
    private boolean isValidWord(String word) {return false;} // TODO

}
