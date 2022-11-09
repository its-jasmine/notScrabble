import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;
    ArrayList<Coordinate> coordinates;
    ArrayList<Tile> tiles;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        coordinates = new ArrayList<>();
        tiles = new ArrayList<>();
    }

    /**
     * Tests that getSquare returns a PLAIN square as expected
     */
    @Test
    public void testGetPlainSquare(){
        Coordinate c = new Coordinate(Coordinate.Column.A, Coordinate.Row.NINE);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.PLAIN, s.getType());
    }

    /**
     * Tests that the getSquare method for the START square returns the correct type
     * FAIL: Expected: START, Actual: PLAIN
     */
    @Test
    public void testGetStartSquare(){
        // Coordinate (H8)
        Coordinate c = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.START, s.getType());
    }

    /**
     * Tests that the getSquare method for a DOUBLE_LETTER square returns the correct type
     * FAIL: Expected: DOUBLE_LETTER, Actual: PLAIN
     */
    @Test
    public void testGetDoubleLetterSquare(){
        // Coordinate (D1)
        Coordinate c = new Coordinate(Coordinate.Column.D, Coordinate.Row.ONE);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.DOUBLE_LETTER, s.getType());
    }

    /**
     * Tests that the getSquare method for a TRIPLE_LETTER square returns the correct type
     * FAIL: Expected: TRIPLE_LETTER, Actual: PLAIN
     */
    @Test
    public void testGetTripleLetterSquare(){
        // Coordinate (F2)
        Coordinate c = new Coordinate(Coordinate.Column.F, Coordinate.Row.TWO);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.TRIPLE_LETTER, s.getType());
    }

    /**
     * Tests that the getSquare method for a DOUBLE_WORD square returns the correct type
     * FAIL: Expected: DOUBLE_WORD, Actual: PLAIN
     */
    @Test
    public void testGetDoubleWordSquare(){
        // Coordinate (B2)
        Coordinate c = new Coordinate(Coordinate.Column.B, Coordinate.Row.TWO);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.TRIPLE_WORD, s.getType());
    }

    /**
     * Tests that the getSquare method for a TRIPLE_WORD square returns the correct type
     * FAIL: Expected: TRIPLE_WORD, Actual: PLAIN
     */
    @Test
    public void testGetTripleWordSquare(){
        // Coordinate (A1)
        Coordinate c = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        Square s = board.getSquare(c);
        assertEquals(Square.Type.TRIPLE_WORD, s.getType());
    }

    /**
     * Tests that the placeTiles method works as expected on empty squares
     */
    @Test
    public void testPlaceTiles(){
        Coordinate c1 = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT);
        Coordinate c2 = new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT);
        Coordinate c3 = new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT);
        tiles.add(Tile.A);
        tiles.add(Tile.N);
        tiles.add(Tile.D);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        assertTrue(board.placeTiles(coordinates, tiles));
    }

    /**
     * Tests that the placeTiles method works as expected on non-empty squares
     */
    @Test
    public void testPlaceTilesOnNonEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT);
        tiles.add(Tile.O);
        tiles.add(Tile.R);
        coordinates.add(c1);
        coordinates.add(c1);
        assertFalse(board.placeTiles(coordinates, tiles));
    }

    /**
     * Tests that the removeTiles method works as expected on non-empty squares
     */
    @Test
    public void testRemoveTiles(){
        Coordinate c1 = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        Coordinate c2 = new Coordinate(Coordinate.Column.B, Coordinate.Row.TWO);
        Coordinate c3 = new Coordinate(Coordinate.Column.C, Coordinate.Row.THREE);
        tiles.add(Tile.P);
        tiles.add(Tile.O);
        tiles.add(Tile.P);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        board.placeTiles(coordinates, tiles);
        assertEquals(tiles,board.removeTiles(coordinates));
    }

    /**
     * Tests that the removeTiles method works as expected on empty squares
     */
    @Test
    public void testRemoveTilesFromEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.C, Coordinate.Row.FOUR);
        coordinates.add(c1);
        assertNull(board.removeTiles(coordinates).get(0));
    }
}