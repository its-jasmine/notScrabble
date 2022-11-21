import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Test class for Rack.
 * @author Victoria Malouf
 * @version Milestone2
 */

public class RackTest {
    Rack rack;
    ArrayList<Tile> rackTiles;
    ArrayList<Tile> randomTiles;

    @Before
    public void setUp() throws Exception {
        rack = new Rack(new Bag());
        rackTiles = rack.getTilesList();
        randomTiles = new ArrayList<>();
        randomTiles.add(Tile.A);
        randomTiles.add(Tile.H);
        randomTiles.add(Tile.K);
        randomTiles.add(Tile.Z);
        randomTiles.add(Tile.Q);
        randomTiles.add(Tile.X);
        randomTiles.add(Tile.M);
    }

    /**
     * Tests that a new rack contains 7 tiles
     */
    @Test
    public void testInitialRackSize(){
        assertEquals(7, rack.getNumTiles());
    }

    /**
     * Tests that removeTiles removes the correct Tile
     */
    @Test
    public void testRemovePresentTiles(){
        for (Tile t: rackTiles){
            ArrayList<Tile> tile = new ArrayList<>();
            tile.add(t);
            assertEquals(tile, rack.removeTiles(tile));
        }
        assertEquals(0, rack.getNumTiles());
    }

    /**
     * Tests that removeTiles removes Blank Tile
     */
    @Test
    public void testRemoveBlankTiles(){
        for (Tile t: rackTiles){
            rack.removeTileFromRack(t);
        }
        assertEquals(0, rack.getNumTiles());

        BlankTile tile = new BlankTile();
        rack.putTileOnRack(tile);
        assertEquals(tile, rack.removeTileFromRack(new BlankTile()));
        assertEquals(0, rack.getNumTiles());
    }

    /**
     * Tests that removeTiles does not remove a Tile if it is not present in the rack
     */
    @Test
    public void testRemoveNonPresentTiles(){
        for (Tile t: randomTiles) {
            ArrayList<Tile> tile = new ArrayList<>();
            tile.add(t);
            if (rackTiles.contains(t)) {
                assertEquals(tile, rack.removeTiles(tile));
            }
            else {
                assertNotEquals(tile, rack.removeTiles(tile));
            }
        }
    }

    /**
     * Tests that putTiles works as expected
     */
    @Test
    public void testPutTileOnRack(){
        assertFalse(rack.putTileOnRack(Tile.B));
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(rack.getTilesList().get(0));
        rack.removeTiles(tile);
        assertTrue(rack.putTileOnRack(Tile.A));
        assertFalse(rack.putTileOnRack(Tile.Z));
    }

    /**
     * Tests that drawTiles returns RUNNING if there are tiles leftover and OVER otherwise
     */
    @Test
    public void testDrawTiles(){
        for (int i=0; i<13; i++) {
            rack.removeTiles(rack.getTilesList());
            assertEquals(0, rack.getNumTiles());
            assertEquals(Game.Status.RUNNING, rack.drawTiles());
            assertEquals(7, rack.getNumTiles());
        }
        rack.removeTiles(rack.getTilesList());
        assertEquals(Game.Status.OVER, rack.drawTiles());
    }

    /**
     * Tests that tallyRackScore returns the correct score
     */
    @Test
    public void testTallyRackScore() {
        rack.removeTiles(rackTiles);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.A);
        tiles.add(Tile.E);
        tiles.add(Tile.I);
        tiles.add(Tile.L);
        tiles.add(Tile.N);
        tiles.add(Tile.O);
        tiles.add(Tile.R);
        rack.putTilesOnRack(tiles);
        assertEquals(7, rack.tallyRackScore());
        //assertEquals(Tile.A+" "+Tile.E+" "+Tile.I+" "+Tile.L+" "+Tile.N+" "+Tile.O+" "+Tile.R+" ", rack);

        rack.removeTiles(rack.getTilesList());
        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(Tile.D);
        tiles2.add(Tile.G);
        tiles2.add(Tile.Z);
        tiles2.add(Tile.V);
        tiles2.add(Tile.W);
        tiles2.add(Tile.Q);
        tiles2.add(Tile.J);
        rack.putTilesOnRack(tiles2);
        assertEquals(40, rack.tallyRackScore());

        rack.removeTiles(rack.getTilesList());
        ArrayList<Tile> tiles3 = new ArrayList<>();
        tiles3.add(Tile.J);
        tiles3.add(Tile.K);
        tiles3.add(Tile.L);
        tiles3.add(Tile.M);
        tiles3.add(Tile.N);
        tiles3.add(Tile.O);
        tiles3.add(Tile.P);
        rack.putTilesOnRack(tiles3);
        assertEquals(22, rack.tallyRackScore());
    }

    /**
     * Tests that the toString method returns the correct representation of the rack and its tiles
     */
    @Test
    public void testToString(){
        rack.removeTiles(rackTiles);
        rack.putTilesOnRack(randomTiles);
        String s = Tile.A+" "+Tile.H+" "+Tile.K+" "+ Tile.Z+" "+ Tile.Q+" "+ Tile.X+" "+ Tile.M+" ";
        assertEquals(s,rack.toString());
    }

    /**
     * Tests that the isTileInRack method returns true if the tile is present, and false otherwise
     */
    @Test
    public void testIsTileInRack(){
        rack.removeTiles(rackTiles);
        rack.putTilesOnRack(randomTiles);
        assertTrue(rack.isTileInRack(Tile.A));
        assertTrue(rack.isTileInRack(Tile.H));
        assertTrue(rack.isTileInRack(Tile.K));
        assertFalse(rack.isTileInRack(Tile.B));
        assertFalse(rack.isTileInRack(Tile.J));
        assertFalse(rack.isTileInRack(Tile.L));
    }

    /**
     * Tests that the iterator method works as expected
     */
    @Test
    public void testIterator(){
        Iterator<Tile> it = rack.iterator();
        for (int i=0;i<rack.getModel().getColumnCount(); i++){
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());

        rack.removeTiles(rack.getTilesList());
        rack.putTileOnRack(Tile.A);
        rack.putTileOnRack(Tile.B);
        Iterator<Tile> it2 = rack.iterator();
        for (int i=0;i<2; i++){
            assertTrue(it2.hasNext());
            it2.next();
        }
        assertFalse(it2.hasNext());
    }

}