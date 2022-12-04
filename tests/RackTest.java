import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

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
    public void setUp() {
        Stack<Move> moves = new Stack<>();
        rack = new Rack(new Bag(), moves);
        rackTiles = rack.getTilesList();
        randomTiles = new ArrayList<>();
        randomTiles.add(LetterTile.A);
        randomTiles.add(LetterTile.H);
        randomTiles.add(LetterTile.K);
        randomTiles.add(LetterTile.Z);
        randomTiles.add(LetterTile.Q);
        randomTiles.add(LetterTile.X);
        randomTiles.add(LetterTile.M);
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
        assertFalse(rack.putTileOnRack(LetterTile.B));
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(rack.getTilesList().get(0));
        rack.removeTiles(tile);
        assertTrue(rack.putTileOnRack(LetterTile.A));
        assertFalse(rack.putTileOnRack(LetterTile.Z));
    }

    /**
     * Tests that drawTiles returns RUNNING if there are tiles leftover and OVER otherwise
     */
    @Test
    public void testDrawAllTiles(){
        for (int i=0; i<13; i++) {
            rack.removeTiles(rack.getTilesList());
            assertEquals(0, rack.getNumTiles());
            assertEquals(Game.Status.RUNNING, rack.drawTiles());
            assertEquals(7, rack.getNumTiles());
        }
        rack.removeTiles(rack.getTilesList());
        assertEquals(0, rack.getNumTiles());
        assertEquals(Game.Status.RUNNING, rack.drawTiles());
        assertEquals(2, rack.getNumTiles());
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
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.E);
        tiles.add(LetterTile.I);
        tiles.add(LetterTile.L);
        tiles.add(LetterTile.N);
        tiles.add(LetterTile.O);
        tiles.add(LetterTile.R);
        rack.putTilesOnRack(tiles);
        assertEquals(7, rack.tallyRackScore());
        //assertEquals(Tile.A+" "+Tile.E+" "+Tile.I+" "+Tile.L+" "+Tile.N+" "+Tile.O+" "+Tile.R+" ", rack);

        rack.removeTiles(rack.getTilesList());
        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(LetterTile.D);
        tiles2.add(LetterTile.G);
        tiles2.add(LetterTile.Z);
        tiles2.add(LetterTile.V);
        tiles2.add(LetterTile.W);
        tiles2.add(LetterTile.Q);
        tiles2.add(LetterTile.J);
        rack.putTilesOnRack(tiles2);
        assertEquals(40, rack.tallyRackScore());

        rack.removeTiles(rack.getTilesList());
        ArrayList<Tile> tiles3 = new ArrayList<>();
        tiles3.add(LetterTile.J);
        tiles3.add(LetterTile.K);
        tiles3.add(LetterTile.L);
        tiles3.add(LetterTile.M);
        tiles3.add(LetterTile.N);
        tiles3.add(LetterTile.O);
        tiles3.add(LetterTile.P);
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
        String s = LetterTile.A+" "+LetterTile.H+" "+LetterTile.K+" "+LetterTile.Z+" "+LetterTile.Q+" "+LetterTile.X+" "+LetterTile.M+" ";
        assertEquals(s,rack.toString());
    }

    /**
     * Tests that the isTileInRack method returns true if the tile is present, and false otherwise
     */
    @Test
    public void testIsTileInRack(){
        rack.removeTiles(rackTiles);
        rack.putTilesOnRack(randomTiles);
        assertTrue(rack.isTileInRack(LetterTile.A));
        assertTrue(rack.isTileInRack(LetterTile.H));
        assertTrue(rack.isTileInRack(LetterTile.K));
        assertFalse(rack.isTileInRack(LetterTile.B));
        assertFalse(rack.isTileInRack(LetterTile.J));
        assertFalse(rack.isTileInRack(LetterTile.L));
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
        rack.putTileOnRack(LetterTile.A);
        rack.putTileOnRack(LetterTile.B);
        Iterator<Tile> it2 = rack.iterator();
        for (int i=0;i<2; i++){
            assertTrue(it2.hasNext());
            it2.next();
        }
        assertFalse(it2.hasNext());
    }

}