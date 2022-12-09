import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    private Move move1 = new Move(new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT), new Coordinate(Coordinate.Column.B, Coordinate.Row.NINE), LetterTile.I, LetterTile.A, true, false);
    private Move move1inverted = new Move(new Coordinate(Coordinate.Column.B, Coordinate.Row.NINE), new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT), LetterTile.I, LetterTile.A, false, true);
    private Move move2 = new Move(new Coordinate(Coordinate.Column.C, Coordinate.Row.TEN), new Coordinate(Coordinate.Column.D, Coordinate.Row.ELEVEN), LetterTile.E, LetterTile.B, true, true);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void invertMove() {
        Move move1copy = new Move(new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT), new Coordinate(Coordinate.Column.B, Coordinate.Row.NINE), LetterTile.I, LetterTile.A, true, false);

        move1copy.invertMove();
        assertEquals(move1inverted, move1copy);
        move1copy.invertMove();
        assertEquals(move1, move1copy);
    }

    @Test
    public void testEquals() {
        Move move1copy = new Move(new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT), new Coordinate(Coordinate.Column.B, Coordinate.Row.NINE), LetterTile.I, LetterTile.A, true, false);

        assertEquals(move1, move1copy);
        assertNotEquals(move1, move2);
    }
}