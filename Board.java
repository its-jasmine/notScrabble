import java.util.Collections;

/**
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 */
public class Board {
    private static Square[][] squares; // [row][column]

    public Board() {
        squares = new Square[Coordinate.Row.values().length][Coordinate.Column.values().length];
        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
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
     * @return true if the square has no tile yet, false otherwise
     */
    public boolean isSquareEmpty(Coordinate coordinate) {
        return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()].isEmpty();
    }

    /**
     * Gets the tile on a square without removing it
     * @param coordinate of the square being checked
     * @return the tile on the square or null
     */

    public Tile getSquareLetter(Coordinate coordinate) {
        return squares[coordinate.getRowIndex()][coordinate.getColumnIndex()].getTile();
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

}
