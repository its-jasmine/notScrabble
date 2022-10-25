import java.util.List;
import java.util.Comparator;

/**
 * This class represents valid coordinates on the game board.
 * @author: Rebecca Elliott
 * @version Milestone1
 */
public class Coordinate {
    public enum Column {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O;
        public Column next() {return values()[ordinal() + 1];}  // if used outside getAdjacentCoordinate it can cause index out of bounds error
        public Column previous() {return values()[ordinal() - 1];}
    }

    public enum Row {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRT, FOURT, FIFT;
        public Row next() {return values()[ordinal() + 1];}
        public Row previous() {return values()[ordinal() - 1];}
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

    public final Column column;
    public final Row row;

    public Coordinate(Column column, Row row){
        this.column = column;
        this.row = row;
    }

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
     * @throws IllegalArgumentException
     */
    public Coordinate getAdjacentCoordinate(String direction) throws IllegalArgumentException {
        direction = direction.toUpperCase();
        boolean outOfBounds = false;
        switch (direction) {
            case "L":
                try {
                    column.previous();
                } catch (IndexOutOfBoundsException err) { outOfBounds = true;}
                break;
            case "R":
                try {
                    column.next();
                } catch (IndexOutOfBoundsException err) { outOfBounds = true;}
                break;
            case "A":
                try {
                    row.previous();
                } catch (IndexOutOfBoundsException err) { outOfBounds = true;}
                break;
            case "B":
                try {
                    row.next();
                } catch (IndexOutOfBoundsException err) { outOfBounds = true;}
                break;
            default:
                throw new IllegalArgumentException("direction must be one of L, R, A, B");
        }
        if (!outOfBounds) {
            switch (direction){
                case "L":
                    return new Coordinate(column.previous(), row);
                case "R":
                    return new Coordinate(column.next(), row);
                case "A":
                    return new Coordinate(column, row.previous());
                case "B":
                    return new Coordinate(column, row.next());
            }
        }
        return null;
    }

}
