import static org.junit.Assert.*;
import org.junit.*;

import java.util.*;
import java.util.stream.Collectors;

public class CoordinateTest {

    @Test
    public void sortByRow() {
        // (A,ONE), (A,TWO), (A, THREE), ... (A, FIFT)

        ArrayList<Coordinate> coordsToSort = (ArrayList<Coordinate>) Arrays.stream(
                Coordinate.Row.values()).map(row -> new Coordinate(Coordinate.Column.A, row)).collect(Collectors.toList());
        ArrayList<Coordinate> expected = (ArrayList<Coordinate>) Arrays.stream(
                Coordinate.Row.values()).map(row -> new Coordinate(Coordinate.Column.A, row)).collect(Collectors.toList());

        Collections.shuffle(coordsToSort);
        Coordinate.sortByRow(coordsToSort);
        for (int i = 0; i < coordsToSort.size(); i++){
            assertEquals(expected.get(i), coordsToSort.get(i));
        }


    }

    @Test
    public void sortByColumn() {
        Coordinate.Column[] columnValues = Coordinate.Column.values();
        ArrayList<Coordinate> expected = (ArrayList<Coordinate>) Arrays.asList(columnValues).stream().map(col -> new Coordinate(col, Coordinate.Row.ONE)).collect(Collectors.toList());


        /*Test 1: Sorting (A,ONE), (B,ONE), (C, ONE), ... (O, ONE) from reverse*/
        List<Coordinate.Column> reversedColumnValues = Arrays.asList(columnValues.clone());
        Collections.reverse(reversedColumnValues);

        ArrayList<Coordinate> coordsToSort = (ArrayList<Coordinate>) reversedColumnValues.stream().map(col -> new Coordinate(col, Coordinate.Row.ONE)).collect(Collectors.toList());

        Coordinate.sortByColumn(coordsToSort);
        for (int i = 0; i < coordsToSort.size(); i++){
            assertEquals(expected.get(i), coordsToSort.get(i));
        }

        /*Test 2: Sorting (A,ONE), (B,ONE), (C, ONE), ... (O, ONE) from shuffle*/
        List<Coordinate.Column> shuffledColumnValues = Arrays.asList(columnValues.clone());
        Collections.shuffle(shuffledColumnValues);
        coordsToSort = (ArrayList<Coordinate>) shuffledColumnValues.stream().map(col -> new Coordinate(col, Coordinate.Row.ONE)).collect(Collectors.toList());

        Coordinate.sortByColumn(coordsToSort);
        for (int i = 0; i < coordsToSort.size(); i++){
            assertEquals(expected.get(i), coordsToSort.get(i));
        }
    }

    @Test
    public void getRowIndex() {
        Coordinate c = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        assertEquals(0, c.getRowIndex());

        c = new Coordinate(Coordinate.Column.A, Coordinate.Row.FIFT);
        assertEquals(14, c.getRowIndex());

        c = new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT);
        assertEquals(7, c.getRowIndex());
    }

    @Test
    public void getColumnIndex() {
        Coordinate c = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        assertEquals(0, c.getColumnIndex());

        c = new Coordinate(Coordinate.Column.O, Coordinate.Row.ONE);
        assertEquals(14, c.getColumnIndex());

        c = new Coordinate(Coordinate.Column.H, Coordinate.Row.ONE);
        assertEquals(7, c.getColumnIndex());
    }

    @Test
    public void getAdjacentCoordinate() {
        /* Test 1: Top left corner */
        Coordinate c = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        assertEquals(new Coordinate(Coordinate.Column.B, Coordinate.Row.ONE), c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT));
        assertEquals(new Coordinate(Coordinate.Column.A, Coordinate.Row.TWO), c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE));

        /* Test 2: Top right corner */
        c = new Coordinate(Coordinate.Column.O, Coordinate.Row.ONE);
        assertEquals(new Coordinate(Coordinate.Column.N, Coordinate.Row.ONE), c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT));
        assertEquals(new Coordinate(Coordinate.Column.O, Coordinate.Row.TWO), c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE));

        /* Test 3: Bottom left corner */
        c = new Coordinate(Coordinate.Column.A, Coordinate.Row.FIFT);
        assertEquals(new Coordinate(Coordinate.Column.B, Coordinate.Row.FIFT), c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT));
        assertEquals(new Coordinate(Coordinate.Column.A, Coordinate.Row.FOURT), c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW));

        /* Test 4: Bottom right corner */
        c = new Coordinate(Coordinate.Column.O, Coordinate.Row.FIFT);
        assertEquals(new Coordinate(Coordinate.Column.N, Coordinate.Row.FIFT), c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT));
        assertEquals(new Coordinate(Coordinate.Column.O, Coordinate.Row.FOURT), c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT));
        assertEquals(null, c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW));
    }
}