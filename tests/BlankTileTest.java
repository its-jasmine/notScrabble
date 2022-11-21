import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlankTileTest {
    BlankTile b;

    /**
     * Initiates a new BlankTile object
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        b = new BlankTile("'");
    }

    /**
     * Tests that the totalNum of a Blank tile is 2
     */
    @Test
    public void getTotalNum() {
        assertEquals(2, b.getTotalNum());
    }

    /**
     * Tests the String format of a Blank tile is '
     */
    @Test
    public void testToString() {
        assertEquals("'", b.toString());
    }

    /**
     * Tests that the value of a Blank tile is 0
     */
    @Test
    public void testGetValue() {
        assertEquals(0, b.getValue());
    }

    /**
     * Tests that the setLetter method works as expected
     */
    @Test
    public void testSetLetter() {
        b.setLetter(LetterTile.A);
        assertEquals("A", b.toString());
        assertEquals("A", b.getLetter());
        assertEquals(0, b.getValue());

    }

    /**
     * Tests that the resetLetter method works as expected
     */
    @Test
    public void testResetLetter() {
        b.setLetter(LetterTile.A);
        b.resetLetter();
        assertEquals("'", b.toString());
        assertEquals("'", b.getLetter());
        assertEquals(0, b.getValue());
    }

    /**
     * Tests that equals method returns true if both blank tiles have the same letter
     */
    @Test
    public void testEquals() {
        b.setLetter(LetterTile.A);
        BlankTile b2 = new BlankTile("'");
        assertFalse(b.equals(b2));
        b2.setLetter(LetterTile.A);
        assertTrue(b.equals(b2));
    }

}