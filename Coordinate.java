import java.util.List;
import java.util.Comparator;

public class Coordinate {
    public enum Column {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O;
        public Column next() {return values()[ordinal() + 1];}
        public Column previous() {return values()[ordinal() - 1];}
    }

    public enum Row {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRT, FOURT, FIFT;
        public Row next() {return values()[ordinal() + 1];}
        public Row previous() {return values()[ordinal() - 1];}
    }

    public static void sortByRow(List<Coordinate> list) {
        list.sort(Comparator.comparing(o -> o.row));
    }

    public static void sortByColumn(List<Coordinate> list) {
        list.sort(Comparator.comparing(o -> o.column));
    }

    public final Column column;
    public final Row row;

    public Coordinate(Column column, Row row){
        this.column = column;
        this.row = row;
    }

    public Coordinate(int column, int row){
        this.column = Column.values()[column];
        this.row = Row.values()[row];
    }

    public int getRowIndex() {
        return row.ordinal();
    }

    public int getColumnIndex() {
        return column.ordinal();
    }

}
