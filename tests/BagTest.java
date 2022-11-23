import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for Bags.
 * @author Rebecca Elliott
 * @version Milestone2
 */

public class BagTest {
    Bag b;

    @Test
    public void drawTiles() {
        b = new Bag();
        assertEquals(7, b.drawTiles(7).size());
        assertEquals(0, b.drawTiles(0).size());
        b.drawTiles(93); // bag now empty
        assertEquals(0, b.drawTiles(19).size());
    }

    @Test
    public void returnTiles(){
        b = new Bag();
        ArrayList<Tile> drawn = b.drawTiles(1);
        b.returnTiles(drawn);
        assertEquals(Bag.MAX_TILES, b.getNumTilesLeft());
    }

    @Test
    public void getNumTilesLeft() {
        b = new Bag();
        assertEquals(Bag.MAX_TILES, b.getNumTilesLeft());
        b.drawTiles(7);
        assertEquals(Bag.MAX_TILES - 7, b.getNumTilesLeft());
    }
}