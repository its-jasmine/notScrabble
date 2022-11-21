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
    @Test
    public void BlankSquareToString() {
        square = new Square();
        assertEquals("", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("", square.toString());
    }
    @Test
    public void StartSquareToString() {
        square = new Square(Square.Type.START);
        assertEquals("*", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("*", square.toString());
    }
    @Test
    public void DLSquareToString() {
        square = new Square(Square.Type.DOUBLE_LETTER);
        assertEquals("DL", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("DL", square.toString());

    }
    @Test
    public void TLSquareToString() {
        square = new Square(Square.Type.TRIPLE_LETTER);
        assertEquals("TL", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("TL", square.toString());

    }
    @Test
    public void DWSquareToString() {
        square = new Square(Square.Type.DOUBLE_WORD);
        assertEquals("DW", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("DW", square.toString());


    }
    @Test
    public void TWSquareToString() {
        square = new Square(Square.Type.TRIPLE_WORD);
        assertEquals("TW", square.toString());
        square.setTile(LetterTile.A);
        assertEquals("A",square.toString());
        square.removeTile();
        assertEquals("TW", square.toString());
    }

}