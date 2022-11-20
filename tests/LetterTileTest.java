import static org.junit.Assert.*;

/**
 * Test class for Tiles.
 * @author Rebecca Elliott
 * @version Milestone2
 */
public class LetterTileTest {

    @org.junit.Test
    public void getTotalNum() {
        assertEquals(9, LetterTile.A.getTotalNum());
        assertEquals(1, LetterTile.Z.getTotalNum());
    }

    @org.junit.Test
    public void testGetValue() {
        assertEquals(3, LetterTile.B.getValue());
        assertEquals(1, LetterTile.U.getValue());
    }

    @org.junit.Test
    public void testToString() {
        assertEquals("B", LetterTile.B.toString());
        assertEquals("U", LetterTile.U.toString());
    }
}
