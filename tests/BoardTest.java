import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;
    ArrayList<Coordinate> coordinates;
    HashSet<Coordinate> thisTurnHashSet;

    HashSet<Coordinate> previouslyHashSet;
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
        assertEquals(Square.Type.DOUBLE_WORD, s.getType());
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
    public void testPlaceTilesOnEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT);
        Coordinate c2 = new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT);
        Coordinate c3 = new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT);
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.N);
        tiles.add(LetterTile.D);
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
        tiles.add(LetterTile.O);
        tiles.add(LetterTile.R);
        coordinates.add(c1);
        coordinates.add(c1);
        assertFalse(board.placeTiles(coordinates, tiles));
    }

    /**
     * Tests that the removeTiles method works as expected on non-empty squares
     */
    @Test
    public void testRemoveTilesFromNonEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE);
        Coordinate c2 = new Coordinate(Coordinate.Column.B, Coordinate.Row.TWO);
        Coordinate c3 = new Coordinate(Coordinate.Column.C, Coordinate.Row.THREE);
        tiles.add(LetterTile.P);
        tiles.add(LetterTile.O);
        tiles.add(LetterTile.P);
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

    /**
     * Tests that the isSquareEmpty method works as expected on empty squares
     */
    @Test
    public void testIsSquareEmptyOnEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.A, Coordinate.Row.FIFT);
        Coordinate c2 = new Coordinate(Coordinate.Column.F, Coordinate.Row.SEVEN);
        Coordinate c3 = new Coordinate(Coordinate.Column.L, Coordinate.Row.THREE);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        for (Coordinate c: coordinates) {
            assertTrue(board.isSquareEmpty(c));
        }
    }

    /**
     * Tests that the isSquareEmpty method works as expected on non-empty squares
     */
    @Test
    public void testIsSquareEmptyOnNonEmptySquares(){
        Coordinate c1 = new Coordinate(Coordinate.Column.G, Coordinate.Row.FOURT);
        Coordinate c2 = new Coordinate(Coordinate.Column.K, Coordinate.Row.ELEVEN);
        Coordinate c3 = new Coordinate(Coordinate.Column.C, Coordinate.Row.TWELVE);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        tiles.add(LetterTile.H);
        tiles.add(LetterTile.E);
        tiles.add(LetterTile.Y);
        board.placeTiles(coordinates, tiles);
        for (Coordinate c: coordinates) {
            assertFalse(board.isSquareEmpty(c));
        }
    }

    /**
     * Tests that getSquareTile returns the correct tile
     */
    @Test
    public void testGetSquareTile(){
        Coordinate c1 = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT);
        Coordinate c2 = new Coordinate(Coordinate.Column.D, Coordinate.Row.NINE);
        Coordinate c3 = new Coordinate(Coordinate.Column.I, Coordinate.Row.TEN);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.B);
        tiles.add(LetterTile.C);
        board.placeTiles(coordinates, tiles);
        assertEquals(LetterTile.A, board.getSquareTile(c1));
        assertEquals(LetterTile.B, board.getSquareTile(c2));
        assertEquals(LetterTile.C, board.getSquareTile(c3));
    }

    /**
     * Tests that getSquareType returns the correct square type
     * FAIL: Expected: START,PLAIN,DOUBLE_LETTER,TRIPLE_LETTER,DOUBLE_WORD,TRIPLE_WORD
     *       Actual: PLAIN,PLAIN,PLAIN,PLAIN,PLAIN,PLAIN
     */
    @Test
    public void testGetSquareType(){
        Coordinate c1 = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT); // START
        Coordinate c2 = new Coordinate(Coordinate.Column.E, Coordinate.Row.THREE); // PLAIN
        Coordinate c3 = new Coordinate(Coordinate.Column.H, Coordinate.Row.FOUR);  // DOUBLE_LETTER
        Coordinate c4 = new Coordinate(Coordinate.Column.F, Coordinate.Row.TWO);   // TRIPLE_LETTER
        Coordinate c5 = new Coordinate(Coordinate.Column.E, Coordinate.Row.FIVE);  // DOUBLE_WORD
        Coordinate c6 = new Coordinate(Coordinate.Column.O, Coordinate.Row.FIFT);  // TRIPLE_WORD
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c4);
        coordinates.add(c5);
        coordinates.add(c6);
        tiles.add(LetterTile.H);
        tiles.add(LetterTile.I);
        tiles.add(LetterTile.J);
        tiles.add(LetterTile.K);
        tiles.add(LetterTile.L);
        tiles.add(LetterTile.M);
        board.placeTiles(coordinates, tiles);
        assertEquals(Square.Type.START, board.getSquareType(c1));
        assertEquals(Square.Type.PLAIN, board.getSquareType(c2));
        assertEquals(Square.Type.DOUBLE_LETTER, board.getSquareType(c3));
        assertEquals(Square.Type.TRIPLE_LETTER, board.getSquareType(c4));
        assertEquals(Square.Type.DOUBLE_WORD, board.getSquareType(c5));
        assertEquals(Square.Type.TRIPLE_WORD, board.getSquareType(c6));
    }

    /**
     * Tests that the submit method works as expected
     */
    @Test
    public void testSubmit(){
        thisTurnHashSet = new HashSet<>();
        previouslyHashSet = new HashSet<>();

        // Placement 1: HORN
        Coordinate c1 = new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT);
        Coordinate c2 = new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT);
        Coordinate c3 = new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT);
        Coordinate c4 = new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT);
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c4);
        tiles.add(LetterTile.H);
        tiles.add(LetterTile.O);
        tiles.add(LetterTile.R);
        tiles.add(LetterTile.N);
        board.placeTiles(coordinates, tiles);
        thisTurnHashSet.addAll(coordinates);
        board.setPlayedThisTurn(thisTurnHashSet);
        assertEquals(14,board.submit());
        previouslyHashSet.addAll(thisTurnHashSet);
        thisTurnHashSet = new HashSet<>();

        // Placement 2: FAM
        ArrayList<Coordinate> coordinates2 = new ArrayList<>();
        ArrayList<Tile> tiles2 = new ArrayList<>();
        Coordinate c5 = new Coordinate(Coordinate.Column.H, Coordinate.Row.SIX);
        Coordinate c6 = new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN);
        Coordinate c7 = new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE);
        coordinates2.add(c5);
        coordinates2.add(c6);
        coordinates2.add(c7);
        tiles2.add(LetterTile.F);
        tiles2.add(LetterTile.A);
        tiles2.add(LetterTile.M);
        board.placeTiles(coordinates2, tiles2);
        thisTurnHashSet.addAll(coordinates2);
        board.setPlayedThisTurn(thisTurnHashSet);
        board.setPreviouslyPlayed(previouslyHashSet);
        assertEquals(18,board.submit());
        previouslyHashSet.addAll(thisTurnHashSet);
        thisTurnHashSet = new HashSet<>();

        // Placement 3: PASTE
        ArrayList<Coordinate> coordinates3 = new ArrayList<>();
        ArrayList<Tile> tiles3 = new ArrayList<>();
        Coordinate c8 = new Coordinate(Coordinate.Column.F, Coordinate.Row.TEN);
        Coordinate c9 = new Coordinate(Coordinate.Column.G, Coordinate.Row.TEN);
        Coordinate c10 = new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN);
        Coordinate c11 = new Coordinate(Coordinate.Column.I, Coordinate.Row.TEN);
        Coordinate c12 = new Coordinate(Coordinate.Column.J, Coordinate.Row.TEN);
        coordinates3.add(c8);
        coordinates3.add(c9);
        coordinates3.add(c10);
        coordinates3.add(c11);
        coordinates3.add(c12);
        tiles3.add(LetterTile.P);
        tiles3.add(LetterTile.A);
        tiles3.add(LetterTile.S);
        tiles3.add(LetterTile.T);
        tiles3.add(LetterTile.E);
        board.placeTiles(coordinates3, tiles3);
        thisTurnHashSet.addAll(coordinates3);
        board.setPlayedThisTurn(thisTurnHashSet);
        board.setPreviouslyPlayed(previouslyHashSet);
        assertEquals(35,board.submit());
        previouslyHashSet.addAll(thisTurnHashSet);
        thisTurnHashSet = new HashSet<>();

        // Placement 4: BIT
        ArrayList<Coordinate> coordinates4 = new ArrayList<>();
        ArrayList<Tile> tiles4 = new ArrayList<>();
        Coordinate c13 = new Coordinate(Coordinate.Column.E, Coordinate.Row.ELEVEN);
        Coordinate c14 = new Coordinate(Coordinate.Column.F, Coordinate.Row.ELEVEN);
        Coordinate c15 = new Coordinate(Coordinate.Column.G, Coordinate.Row.ELEVEN);
        coordinates4.add(c13);
        coordinates4.add(c14);
        coordinates4.add(c15);
        tiles4.add(LetterTile.B);
        tiles4.add(LetterTile.I);
        tiles4.add(LetterTile.T);
        board.placeTiles(coordinates4, tiles4);
        thisTurnHashSet.addAll(coordinates4);
        board.setPlayedThisTurn(thisTurnHashSet);
        board.setPreviouslyPlayed(previouslyHashSet);
        assertEquals(22,board.submit());
    }

}