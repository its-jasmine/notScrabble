import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    Square square;

    @Test
    public void placeTile() {
        square = new Square();
        square.placeTile(Tile.A);
        assertEquals(Tile.A,square.getTile());
        assertNotEquals(Tile.B,square.getTile());
    }

    @Test
    public void isEmpty() {
        square = new Square();
        assertTrue(square.isEmpty()); // empty square
        square.placeTile(Tile.A);
        assertFalse(square.isEmpty()); // non-empty square
    }

    @Test
    public void removeTile() {
        square = new Square();
        //remove from square with no tile
        assertNull(square.removeTile());
        //remove from square with tile A
        square.placeTile(Tile.A);
        assertEquals(square.removeTile(),Tile.A);
        assertNull(square.getTile());
    }
}