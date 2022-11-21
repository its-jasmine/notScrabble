import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    Square square;

    @Test
    public void placeTile() {
        square = new Square();
        square.setTile(LetterTile.A);
        assertEquals(LetterTile.A,square.getTile());
        assertNotEquals(LetterTile.B,square.getTile());
    }

    @Test
    public void isEmpty() {
        square = new Square();
        assertTrue(square.isEmpty()); // empty square
        square.setTile(LetterTile.A);
        assertFalse(square.isEmpty()); // non-empty square
    }

    @Test
    public void removeTile() {
        square = new Square();
        //remove from square with no tile
        assertNull(square.removeTile());
        //remove from square with tile A
        square.setTile(LetterTile.A);
        assertEquals(square.removeTile(),LetterTile.A);
        assertNull(square.getTile());
    }
}