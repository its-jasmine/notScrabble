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
        Game game = new Game(1,1);
        ArrayList<Player> players = game.getPlayers();
        aiPlayer = (AIPlayer) players.get(1);
        if (game.getPlayerTurn() == 1){ // AI is the first player
            game.submit();
            assertEquals(0, aiPlayer.getScore()); // AI currently cannot play first, it should pass
        }
        game.submit();

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