import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 */
public class Board {
    private static Square[][] squares;

    public Board() {
        squares = new Square[Row.values().length][Column.values().length];
        for (Row r : Row.values()) {
            for (Column c : Column.values()) {
                squares[r.ordinal()][c.ordinal()] = new Square();
            }
        }
    }

    /**
     * Checks if the letter being placed is inline with the word being placed
     * @param colum of the letter being placed
     * @param row of the letter being placed
     * @return true if inline, false otherwise
     */
    private boolean isValidTilePlacement(Column colum, Row row) {
        return false; // needs logic
    }

    /**
     * Places letter in square if available
     * @param colum of the letter being placed
     * @param row of the letter being placed
     * @return true if letter was placed, false otherwise
     */
    public boolean placeTile(Column colum, Row row, Letter letter) {
        if (squares[row.ordinal()][colum.ordinal()].isEmpty()) {
            squares[row.ordinal()][colum.ordinal()].placeTile(letter);
            return true;
        }
        return false;
    }

    /**
     * Removes letter in square if available
     * @param colum of the letter being removed
     * @param row of the letter being removed
     * @return true if letter was removed, false otherwise
     */
    public boolean removeTile(Column colum, Row row) {
        if (!squares[row.ordinal()][colum.ordinal()].isEmpty()) {
            squares[row.ordinal()][colum.ordinal()].removeTile();
            return true;
        }
        return false;
    }


    /**
     * checks if the square has a letter in it already
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return true if the square has no letter yet, false otherwise
     */
    private boolean isSquareEmpty(Column colum, Row row) {
        return squares[row.ordinal()][colum.ordinal()].isEmpty();
    }

    /**
     * Gets the letter on a square
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return the enum letter or null
     */

    public Tile getSquareLetter(Column colum, Row row) {
        return squares[row.ordinal()][colum.ordinal()].getTile();
    }

    /**
     * Calculates the score for all the words created this turn
     * @param playedThisTurn a list with the location of all the letters played this turn
     * @return total score
     */
    //public int scoreWords(List playedThisTurn) { return -1;// needs logic - Rebecca will do}

    /**
     * Calculates the score of a single word
     * @return word score
     */
     
    //private int scoreWord(head, tail) {return -1; //  needs logic - Rebecca will do}
    /**
     * take a word in a LinkedList and converts it to a String
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
        for (Row r : Row.values()) {
            for (Column c : Column.values()) {
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
