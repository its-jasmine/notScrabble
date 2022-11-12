import static org.junit.Assert.*;

public class TileTest {

    @org.junit.Test
    public void getTotalNum() {
        assertEquals(9, Tile.A.getTotalNum());
        assertEquals(1, Tile.Z.getTotalNum());
    }

    @org.junit.Test
    public void testToString() {
        assertEquals("B", Tile.B.toString());
        assertEquals("U", Tile.U.toString());
    }
}