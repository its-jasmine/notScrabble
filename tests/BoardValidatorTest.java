import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.*;

public class BoardValidatorTest {
    private Board board;
    private BoardValidator boardValidator;
    private ArrayList<Coordinate> coordinateList;
    private ArrayList<Tile> tileList;

    /**
     * Initiates a new Board object and an ArrayList that will hold Coordinate objects
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        Stack<Move> moves = new Stack<>();
        board = new Board(moves);
        coordinateList = new ArrayList<>();
    }

    /**
     * Tests that the first tiles placed on the start square return the correct Direction
     */
    @Test
    public void testFirstTilesOnStart() {
        boardValidator = new BoardValidator(board);
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that the first tiles placed NOT on the start square return Direction.UNKNOWN
     */
    @Test
    public void testFirstTilesNotOnStart() {
        boardValidator = new BoardValidator(board);
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.UNKNOWN,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can append horizontally
     */
    @Test
    public void testTilesExtendHorizontally() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can append vertically
     */
    @Test
    public void testTilesExtendVertically() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.ELEVEN));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can prepend horizontally
     */
    @Test
    public void testTilesPrependHorizontally() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList.add(new Coordinate(Coordinate.Column.E, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can prepend vertically
     */
    @Test
    public void testTilesPrependVertically() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.FIVE));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SIX));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can cross horizontally
     */
    @Test
    public void testTilesCrossingHorizontally() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can cross vertically
     */
    @Test
    public void testTilesCrossingVertically() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Tests that tiles can cross vertically
     */
    @Test
    public void testDisjointTiles() {
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList.add(new Coordinate(Coordinate.Column.B, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.UNKNOWN,boardValidator.isValidTileAlignment(coordinateList));
    }

    /**
     * Helper method that returns a board with a horizontal word placed on the start square
     */
    private Board setBoardWithOneWordOnStartH() {
        Stack<Move> moves = new Stack<>();
        Board b = new Board(moves);
        ArrayList<Coordinate> coordinateList2 = new ArrayList<>();
        coordinateList2.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList2.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList2.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        tileList = new ArrayList<>();
        tileList.add(LetterTile.E);
        tileList.add(LetterTile.N);
        tileList.add(LetterTile.D);
        b.placeTiles(coordinateList2, tileList);
        return b;
    }

    /**
     * Helper method that returns a board with a vertical word placed on the start square
     */
    private Board setBoardWithOneWordOnStartV() {
        Stack<Move> moves = new Stack<>();
        Board b = new Board(moves);
        ArrayList<Coordinate> coordinateList3 = new ArrayList<>();
        coordinateList3.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coordinateList3.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList3.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        tileList = new ArrayList<>();
        tileList.add(LetterTile.E);
        tileList.add(LetterTile.N);
        tileList.add(LetterTile.D);
        b.placeTiles(coordinateList3, tileList);
        return b;
    }

}
