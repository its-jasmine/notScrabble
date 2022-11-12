import java.util.List;
import java.util.Comparator;

/**
 * This class represents valid coordinates on the game board.
 * @author Rebecca Elliott
 * @version Milestone1
 */
public class Coordinate {
    /** The allowable columns on the board */
    public enum Column {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O;

        /**
         * Gets the next sequential Column enum.
         * @return the next enum or null if there isn't another one
         */
        public Column next() {
            Column c;
            try {
                c = values()[ordinal() + 1];
            } catch (IndexOutOfBoundsException err) {return null;}
            return c;
        }

        /**
         * Gets the previous sequential Column enum.
         * @return the previous enum or null if there isn't another one
         */
        public Column previous() {
            Column c;
            try {
                c = values()[ordinal() - 1];
            } catch (IndexOutOfBoundsException err) {return null;}
            return c;
        }
    }
    /** The allowable rows on the board */
    public enum Row {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRT, FOURT, FIFT;

        /**
         * Gets the next sequential Row enum.
         * @return the next enum or null if there isn't another one
         */
        public Row next() {
            Row r;
            try {
                r = values()[ordinal() + 1];
            } catch (IndexOutOfBoundsException err) {return null;}
            return r;
        }

        /**
         * Gets the previous sequential Row enum.
         * @return the previous enum or null if there isn't another one
         */
        public Row previous() {
            Row r;
            try {
                r = values()[ordinal() - 1];
            } catch (IndexOutOfBoundsException err) {return null;}
            return r;}
    }

    /** The allowable adjacency directions of a coordinate */
    public enum Adjacent{ // used as directional inputs for finding adjacent coordinates
        LEFT, RIGHT, ABOVE, BELOW
    }

    /**
     * Sorts a list by each element's row field.
     * @param list to be sorted
     */
    public static void sortByRow(List<Coordinate> list) {
        list.sort(Comparator.comparing(o -> o.row));
    }

    /**
     * Sorts a list by each element's column field.
     * @param list to be sorted
     */
    public static void sortByColumn(List<Coordinate> list) {
        list.sort(Comparator.comparing(o -> o.column));
    }

    /** The column of the coordinate */
    public final Column column;
    /** The row of the coordinate */
    public final Row row;

    /**
     * Creates a new coordinate.
     * @param column of the coordinate
     * @param row of the coordinate
     */
    public Coordinate(Column column, Row row){
        this.column = column;
        this.row = row;
    }

    /**
     * Returns the index of the row.
     * @return index of the row
     */
    public int getRowIndex() {
        return row.ordinal();
    }

    /**
     * Gets the index related to the column.
     * @return index in Column
     */
    public int getColumnIndex() {
        return column.ordinal();
    }

    /**
     * Gets the coordinate one space over in the direction given if possible.
     * @param direction to check
     * @return the adjacent coordinate or null if at edge of board
     */
    public Coordinate getAdjacentCoordinate(Adjacent direction) {
        switch (direction) {
            case LEFT:
                Column cL = column.previous();
                if (cL == null) return null;
                return new Coordinate(cL, row);
            case RIGHT:
                Column cR = column.next();
                if (cR == null) return null;
                return new Coordinate(cR, row);
            case ABOVE:
                Row rA = row.previous();
                if (rA == null) return null;
                return new Coordinate(column, rA);
            case BELOW:
                Row rB = row.next();
                if (rB == null) return null;
                return new Coordinate(column, rB);
            default:
                return null;
        }
    }
}
