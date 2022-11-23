import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AIPlayerTest {
    AIPlayer aiPlayer;
    Bag bag;
    Board board;
    ArrayList<Tile> tiles;
    ArrayList<Coordinate> coordinates;
    @Before
    public void setUp() throws Exception {
        bag = new Bag();
        board = new Board();
        aiPlayer = new AIPlayer(board, bag, 1);
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    @Test
    public void submit() {
        ArrayList<Tile> aiTilesBefore = aiPlayer.getRack().getTilesList();
        // at least two tiles should be different
        assertEquals(0, aiPlayer.getScore());
        assertEquals(Game.Status.RUNNING, aiPlayer.submit());
        assertNotEquals(0, aiPlayer.getScore());
        ArrayList<Tile> aiTilesAfter = aiPlayer.getRack().getTilesList();
        assertEquals(aiTilesBefore.size(), aiTilesAfter.size());
        int differentTileCount = 0;
        for (int i = 0; i < aiTilesBefore.size(); i++){
            if (aiTilesBefore.get(i) != aiTilesAfter.get(i)) differentTileCount++;
        }
        assertTrue(differentTileCount > 1);

        bag.drawTiles(Bag.MAX_TILES - Rack.MAXTILES);
        assertEquals(0, bag.getNumTilesLeft());
        ArrayList<Tile> aiRackTiles = aiPlayer.getRack().getTilesList();
        aiPlayer.getRack().removeTiles(aiRackTiles);
        assertEquals(0, aiPlayer.getRack().getNumTiles());

        assertEquals(Game.Status.OVER, aiPlayer.submit());
    }

    // visual test
    public static void main(String[] args) {
        GameView gameView = new GameView(1, 1);
        Game game = gameView.getGame();
        ArrayList<Player> players = game.getPlayers();
        for (Player p: players) {
            if (p instanceof AIPlayer) {
                Rack rack = p.getRack();
                ArrayList<Tile> rackTiles = rack.getTilesList();
                for (Tile t: rackTiles){
                    rack.removeTileFromRack(t);
                }
                assertEquals(0, rack.getNumTiles());

                BlankTile tile = new BlankTile();
                rack.putTileOnRack(tile);
            }

        }
    }

}