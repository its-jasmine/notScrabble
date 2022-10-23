import java.util.*;

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
    * Determine the direction field for this turn if tiles are straight
    * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
    * @return Direction of the tiles (HORIZONTAL or VERTICAL), null otherwise
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
            else return null;
        }
    }
    /**
     * Determine if the tiles the player is attempting to place are connected to an existing tile
     * @param sortedTiles the sorted coordinates of the tiles the player is attempting to place this turn
     * @return true if the sorted tiles are attached to another tile, false otherwise
     */
    private boolean verifyWordAttachment(List<Coordinate> sortedTiles){
        if (direction == Direction.HORIZONTAL) {
            // Is there a tile to the left of the first tile played?
            Coordinate c = new Coordinate(sortedTiles.get(0).getColumnIndex() - 1, sortedTiles.get(0).getRowIndex());
            if (!isSquareEmpty(c)) {return true;}
            // For each tile played is there a letter above or below?
            for (Coordinate c: sortedTiles){
                Coordinate above = new Coordinate(c.getColumnIndex(), c.getRowIndex() - 1);
                Coordinate below = new Coordinate(c.getColumnIndex(), c.getRowIndex() + 1);
                if (!isSquareEmpty(above)) {return true;}
                if (!isSquareEmpty(below)) {return true;}
            }
            // Is there a tile to the right of the last tile played?
            Coordinate c = new Coordinate(sortedTiles.get(-1).getColumnIndex() + 1, sortedTiles.get(-1).getRowIndex());
            if (!isSquareEmpty(c)) {return true;}
        }
        else {
            // Is there a tile above the first tile played?
            Coordinate c = new Coordinate(sortedTiles.get(0).getColumnIndex(), sortedTiles.get(0).getRowIndex() - 1);
            if (!isSquareEmpty(c)) {return true;}
            // For each tile played is there a letter right or left?
            for (Coordinate c: sortedTiles){
                Coordinate left = new Coordinate(c.getColumnIndex() - 1, c.getRowIndex());
                Coordinate right = new Coordinate(c.getColumnIndex() + 1, c.getRowIndex());
                if (!isSquareEmpty(left)) {return true;}
                if (!isSquareEmpty(right)) {return true;}
            }
            // Is there a tile to below the last tile played?
            Coordinate c = new Coordinate(sortedTiles.get(-1).getColumnIndex(), sortedTiles.get(-1).getRowIndex() + 1);
            if (!isSquareEmpty(c)) {return true;}
        }
        return false;
    }

    /**
     * Determine if is each square between the first and last tile played is NOT empty
     * @param sortedTiles the sorted coordinates of the tiles the player is attempting to place this turn
     * @return true if the sorted tiles placement do no have gaps, false otherwise
     */
    private boolean verifyNoGaps(List<Coordinate> sortedTiles){
        if (direction == Direction.HORIZONTAL){
            Row ro = sortedTiles.get(0).row;
            for (int i = sortedTiles.get(0).getColumnIndex(); i <= sortedTiles.get(-1).getColumnIndex(); i++){
                if (isSquareEmpty(new Coordinate(i, ro))) return false;
            }
        }
        else {
            Column col = sortedTiles.get(0).column;
            for (int i = sortedTiles.get(0).getRowIndex(); i <= sortedTiles.get(-1).getRowIndex(); i++){
                if (isSquareEmpty(new Coordinate(col, i))) return false;
            }
        }
        return true;
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
        if (d == null) return null;
        else direction = d;

        // Sort tiles
        List<Coordinate> sortedTiles = new ArrayList<Coordinate>();
        if (direction == Direction.HORIZONTAL){
            Collections.sort(sortedTiles, Comparator.comparing(Coordinate::getRowIndex));
        }
        else {
            Collections.sort(sortedTiles, Comparator.comparing(Coordinate::getColumnIndex));
        }
        // Check if there are any gaps between tiles placed
        if ((sortedTiles.size() == (sortedTiles.get(-1).getColumnIndex() - sortedTiles.get(0).getColumnIndex() + 1))
                || (sortedTiles.size() == (sortedTiles.get(-1).getRowIndex() - sortedTiles.get(0).getRowIndex() + 1))) {
                // verify word attachment
                if (verifyWordAttachment(sortedTiles)) return sortedTiles;
        }
        // If there are gaps between tiles, is each square between the first and last tile played NOT empty?
        else {
            if (verifyNoGaps(sortedTiles)) return sortedTiles;
        }
        return null;
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
