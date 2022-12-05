import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class PlayerTest {


    Player player;
    Bag bag;
    Board board;
    ArrayList<Tile> tiles;
    ArrayList<Coordinate> coordinates;
    HashSet<Coordinate> coordinateHashSet;

    @Before
    public void setUp() throws Exception {
        bag = new Bag();
        board = new Board();
        player = new Player(board, bag);
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        coordinateHashSet = new HashSet<>();
    }

    @Test
    public void addToScore() {
        player.addToScore(0);
        assertEquals(0,player.getScore());
        player.addToScore(1);
        assertEquals(1, player.getScore());
        player.addToScore(999);
        assertEquals(1000, player.getScore());
        player.addToScore(-1);
        assertEquals(999, player.getScore());
        player.addToScore(-1000);
        assertEquals( -1, player.getScore());
    }

    @Test
    public void endTurn() {
        //is actually private
    }


    public void submitValidStartWord() {
        //place AND on the starting tile
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates, tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertSame(player.submit(), Game.Status.RUNNING);
    }
    @Test
    public void submitUnconnectedValidWord() {
        //place AND on the starting tile
        tiles.add(LetterTile.A);
        coordinateHashSet.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates, tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        tiles.clear();
        coordinates.clear();
        coordinateHashSet.clear();
        //add unconnected valid word
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.B, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.C, Coordinate.Row.EIGHT));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates, tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertSame(Game.Status.RETRY,player.submit());
    }
    @Test
    public void submitInvalidWord() {
        // place invalid word
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        tiles.add(LetterTile.S);
        coordinates.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates, tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertSame(Game.Status.RETRY,player.submit());
    }
    @Test
    public void submitNoMoreTiles(){
        // place a valid word
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        tiles.clear();
        coordinates.clear();
        coordinateHashSet.clear();
        // place a valid word with no more tiles in bag
        tiles.add(LetterTile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.SEVEN));
        tiles.add(LetterTile.P);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.NINE));
        tiles.add(LetterTile.S);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.TEN));
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        for (int i=0; i<15; i++) {
            player.getRack().removeTiles(player.getRack().getTilesList());
            player.getRack().drawTiles();
        }
        assertEquals(Game.Status.OVER, player.submit());


    }

    @Test
    public void resetTurn() {
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        player.resetTurn();

        assertEquals( 7,player.getRack().getNumTiles());
    }
    @Test
    public void exchangeOneTile(){
        int numTilesInBag = bag.getNumTilesLeft();
        int playerScore = player.getScore();

        Rack r = player.getRack();
        ArrayList<Tile> tiles = r.getTilesList();
        ArrayList<Tile> firstTile = new ArrayList<>();
        firstTile.add(tiles.get(0));
        r.removeTiles(firstTile);
        player.getRack().getExchangeModel().setValueAt(tiles.get(0),0,0);

        assertTrue(player.exchangeTiles());

        ArrayList<Tile> tilesActual = r.getTilesList();
        assertEquals(7, tilesActual.size()); // should have a full rack
        assertNotEquals(tiles.get(0), tilesActual.get(0)); // first tile should be different (exchanged)
        for (int i = 1; i < tilesActual.size(); i++){
            assertEquals(tiles.get(i), tilesActual.get(i)); // rest of tiles should be the same
        }
        // exchange rack should not have the tile anymore
        assertNull(r.getExchangeModel().getValueAt(0,0));
        // after an exchange, the number of tiles in the bag should be the same
        assertEquals(numTilesInBag,bag.getNumTilesLeft());

        assertEquals(playerScore,player.getScore()); // player should not gain any points
    }
    @Test
    public void exchangeAllTiles(){
        int numTilesInBag = bag.getNumTilesLeft();
        int playerScore = player.getScore();

        Rack r = player.getRack();
        ArrayList<Tile> tiles = r.getTilesList();

        r.removeTiles(tiles);

        for (int i = 0; i < tiles.size(); i++) {
            player.getRack().getExchangeModel().setValueAt(tiles.get(i), 0, i);
        }

        assertTrue(player.exchangeTiles());

        ArrayList<Tile> tilesActual = r.getTilesList();
        assertEquals(7, tilesActual.size()); // should have a full rack
        for (int i = 0; i < tiles.size(); i++){
            assertNotEquals(tiles.get(i), tilesActual.get(i)); // all tiles should be different
            assertNull(r.getExchangeModel().getValueAt(0,i)); // exchange rack should not have the tiles anymore
        }

        // after an exchange, the number of tiles in the bag should be the same
        assertEquals(numTilesInBag,bag.getNumTilesLeft());

        assertEquals(playerScore,player.getScore()); // player should not gain any points
    }
    @Test
    public void exchangeUnsuccessful(){
        bag.drawTiles(100);
        int numTilesInBag = bag.getNumTilesLeft();
        assertEquals(0, numTilesInBag);// bag is empty
        int playerScore = player.getScore();

        Rack r = player.getRack();
        ArrayList<Tile> tiles = r.getTilesList();
        ArrayList<Tile> firstTile = new ArrayList<>();
        firstTile.add(tiles.get(0));
        r.removeTiles(firstTile);
        player.getRack().getExchangeModel().setValueAt(tiles.get(0),0,0);

        assertFalse(player.exchangeTiles());

        ArrayList<Tile> tilesActual = r.getTilesList();
        assertEquals(7, tilesActual.size()); // tile should be returned
        for (int i = 0; i < tilesActual.size(); i++){
            assertTrue(tiles.contains(tilesActual.get(i))); // rest of tiles should be the same
        }

        // the number of tiles in the bag should be the same
        assertEquals(numTilesInBag,bag.getNumTilesLeft());

        assertEquals(playerScore,player.getScore()); // player should not gain any points
    }
}