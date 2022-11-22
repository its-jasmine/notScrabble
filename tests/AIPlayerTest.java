import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AIPlayerTest {



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