import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 */
public class Board {
    private static Square[][] squares;

    private Direction direction; // keeps track of the direction of the tiles that were placed, set in alignment check
    private static enum Direction {HORIZONTAL, VERTICAL, UNKNOWN};

    public Board() {
        squares = new Square[Row.values().length][Column.values().length];
        for (Row r : Row.values()) {
            for (Column c : Column.values()) {
                squares[r.ordinal()][c.ordinal()] = new Square();
            }
        }
        direction = Direction.UNKNOWN;
    }

    /**
     * // probably won't use this, will validate all tiles placed at once
     * Checks if the letter being placed is inline with the word being placed
     * @param colum of the letter being placed
     * @param row of the letter being placed
     * @return true if inline, false otherwise
     */
    private boolean isValidTilePlacement(Column colum, Row row) {
        return false; // needs logic
    }

    /**
     * calls all the functions needed to validated and score words created this turn
     * @param tilesPlaced the tiles the player is attempting to place this turn
     * @return -1 if any validation fails (player tries again), otherwise returns the score for the turn
     */
    public int submit(List<Tile> tilesPlaced) {
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
     * checks if the tiles placed this turn are straight, leave no gaps, and touch a word that was already played
     * sets the direction field for this turn if tiles are straight
     * @param tilesPlaced the tiles the player is attempting to place this turn
     * @return a sorted list of tiles played if the alignment is valid, null otherwise
     */
    private List<Tile> isValidTileAlignment(List<Tile> tilesPlaced) {return null;} // TODO

    /**
     * Places letter in square if available
     * @param colum of the letter being placed
     * @param row of the letter being placed
     * @return true if letter was placed, false otherwise
     */
    public boolean placeTile(Column colum, Row row) {
        return false; // TODO
    }

    /**
     * checks if the square has a letter in it already
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return true if the square has no letter yet, false otherwise
     */
    private boolean isSquareEmpty(Column colum, Row row) { // Column Row enum will need associated int value for indexing
        return squares[row.ordinal()][colum.ordinal()].isEmpty();
    }

    /**
     * Gets the letter on a square
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return the enum letter or null
     */
    public Letters getSquareLetter(Column colum, Row row) {
        return squares[row.ordinal()][colum.ordinal()].getLetter();
    }

    /**
     * finds all the words that were created this turn
     * @param tilesPlayed a sorted list of the tiles played this turn
     * @return list of a words stored in a double linked list. nodes store Tiles so we have letter and value.
     */
    private List<LinkedList> getWordsCreated(List<Tile> tilesPlayed) {
        List<LinkedList> words = new ArrayList<LinkedList>();

        if (direction == Direction.HORIZONTAL) {} // TODO : Rebecca
        else if (direction == Direction.VERTICAL) {} // TODO : Rebecca
        return words;
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
        return -1; // TODO:  Rebecca
    }

    /**
     * gets the letter from each node in the linked list
     * @param llWord linkedList form of a word
     * @return the word as a String
     */
    private String llToString(LinkedList llWord) {
        String sWord = "";
        // TODO: Rebecca
        return sWord;
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
        return true;}

    /**
     * checks if the word is in wordBank
     * @param word to check
     * @return true if valid, false otherwise
     */
    private boolean isValidWord(String word) {return false;} // TODO

}
