/**
 * @author Rebecca Elliott
 */
public class Board {
    private static List<List<Square>> squares;

    public Board() {
        squares = new ArrayList<List<Square>>();
        int i = 0;
        for (Column c : Column.values()) {
            squares.add(i, new ArrayList<Square>());
            int j = 0;
            for (Row r : Row.values()) {
                squares.get(i).add(j, new Square());
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
    public boolean placeTile(Column colum, Row row) {
        return false; // needs logic
    }

    /**
     * checks if the square has a letter in it already
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return true if the square has no letter yet, false otherwise
     */
    private boolean isSquareEmpty(Column colum, Row row) { // Column Row enum will need associated int value for indexing
        return squares.get(colum.int).get(row.int).isEmpty();
    }

    /**
     * Gets the letter on a square
     * @param colum of the square being checked
     * @param row of the square being checked
     * @return the enum letter or null
     */
    public Letters getSquareLetter(Column colum, Row row) {
        return squares.get(colum.int).get(row.int).getLetter();
    }

    /**
     * Calculates the score for all the words created this turn
     * @param playedThisTurn a list with the location of all the letters played this turn
     * @return total score
     */
    public int scoreWords(List playedThisTurn) {
        return -1;// needs logic - Rebecca will do
    }

    /**
     * Calculates the score of a single word
     * @return word score
     */
    private int scoreWord(head, tail) {
        return -1; //  needs logic - Rebecca will do
    }

}
