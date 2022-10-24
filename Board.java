import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
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

    private Square getSquare(Coordinate coordinate) {
        return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()];
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
    public boolean isSquareEmpty(Coordinate coordinate) {
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
            if (word.size() > 1) words.add(word); // to be counted as a word it must be at least two letters long

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
     * gets any horizontal word, can be one letter long at this point
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private LinkedList getHorizontalWord(Coordinate startSearch) {
        LinkedList word = new LinkedList();
        Coordinate coordinateToLeft = startSearch.getAdjacentCoordinate("L");

        // adds letter to the front of the word
        while (coordinateToLeft != null && !getSquare(coordinateToLeft).isEmpty()) { // if coordinateToLeft is null we are at the left edge of the board
            word.add(new Node(getSquareTile(coordinateToLeft), getSquareType(coordinateToLeft)));
            coordinateToLeft = coordinateToLeft.getAdjacentCoordinate("L");
        }

        Coordinate coordinateToRight = startSearch;
        //adds letters to the end of the word
        while (coordinateToRight != null && !getSquare(coordinateToRight).isEmpty()) {  // if coordinateToRight is null we are at the Right edge of the board
            word.add(new Node(getSquareTile(coordinateToRight), getSquareType(coordinateToRight)));
            coordinateToRight = coordinateToRight.getAdjacentCoordinate("R");
        }

        return word;
    }

    /**
     * gets any vertical word, can be one letter long at this point
     * @param startSearch the place on the board to search from
     * @return word that was created
     */
    private LinkedList getVerticalWord(Coordinate startSearch) {
        LinkedList word = new LinkedList();
        Coordinate coordinateAbove = startSearch.getAdjacentCoordinate("A");

        // adds letter to the front of the word
        while (coordinateAbove != null && !getSquare(coordinateAbove).isEmpty()) { // if coordinateAbove is null we are at the top of the board
            word.add(new Node(getSquareTile(coordinateAbove), getSquareType(coordinateAbove)));
            coordinateAbove = coordinateAbove.getAdjacentCoordinate("A");
        }

        Coordinate coordinateBelow = startSearch;
        //adds letters to the end of the word
        while (coordinateBelow != null && !getSquare(coordinateBelow).isEmpty()) { // if coordinateBelow is null we are at the bottom of the board
            word.add(new Node(getSquareTile(coordinateBelow), getSquareType(coordinateBelow)));
            coordinateBelow = coordinateBelow.getAdjacentCoordinate("B");
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
