import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardValidatorTest {
    private BoardValidator boardValidator;
    private ArrayList<Coordinate> coordinateList;

    private ArrayList<Tile> tileList;


    @Test
    public void verifyNoGaps() {
    coordinateList = new ArrayList<>();
    coordinateList.add(new Coordinate(Coordinate.Column.A, Coordinate.Row.ONE));
    coordinateList.add(new Coordinate(Coordinate.Column.A, Coordinate.Row.THREE));
    }

    @Test
    public void verifyWordAttachment() {
    }

    @Test
    public void getDirection() {
    }

    @Test
    public void isOnStart() {
    }

    @Test
    public void isValidTileAlignment() {
        // This method returns UNKNOWN if the alignment is NOT valid

        /* test first tiles */
        // on start square
        boardValidator = new BoardValidator(new Board());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));

        // not on start
        boardValidator = new BoardValidator(new Board());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.UNKNOWN,boardValidator.isValidTileAlignment(coordinateList));


        /* test tiles extend tiles horizontally */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));

        /* test tiles extend tiles vertically */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.ELEVEN));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));


        /* test tiles prepend tiles horizontally */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.E, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));

        /* test tiles prepend tiles vertically */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.FIVE));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SIX));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));

        /* test tiles cross horizontal tiles */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartH());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        assertEquals(BoardValidator.Direction.VERTICAL,boardValidator.isValidTileAlignment(coordinateList));

        /* test tiles cross vertical tiles */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.HORIZONTAL,boardValidator.isValidTileAlignment(coordinateList));

        /* test disjoint tiles */
        boardValidator = new BoardValidator(setBoardWithOneWordOnStartV());
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.B, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        assertEquals(BoardValidator.Direction.UNKNOWN,boardValidator.isValidTileAlignment(coordinateList));

    }

    private Board setBoardWithOneWordOnStartH() {
        Board b = new Board();
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        tileList = new ArrayList<>();
        tileList.add(Tile.E);
        tileList.add(Tile.N);
        tileList.add(Tile.D);
        b.placeTiles(coordinateList, tileList);
        return b;
    }

    private Board setBoardWithOneWordOnStartV() {
        Board b = new Board();
        coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        tileList = new ArrayList<>();
        tileList.add(Tile.E);
        tileList.add(Tile.N);
        tileList.add(Tile.D);
        b.placeTiles(coordinateList, tileList);
        return b;
    }
}