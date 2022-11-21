import org.junit.Test;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class PlayerTest {


    Player player;
    Bag bag;
    ArrayList<Tile> tiles;
    ArrayList<Coordinate> coordinates;

    @Test
    public void addToScore() {
        player = new Player(new Board(),new Bag());
        player.addToScore(0);
        assertTrue(player.getScore() == 0);
        player.addToScore(1);
        assertTrue(player.getScore() == 1);
        player.addToScore(999);
        assertTrue(player.getScore() == 1000);
        player.addToScore(-1);
        assertTrue(player.getScore() == 999);
        player.addToScore(-1000);
        assertTrue(player.getScore() == -1);
    }

    @Test
    public void endTurn() {
        //is actually private
    }

    @Test
    public void submit() {
        Board board = new Board();
        Bag bag = new Bag();
        player = new Player(board,bag);

        //place AND on the starting tile
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        HashSet<Coordinate> coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertTrue(player.submit()== Game.Status.RUNNING);

        // place AND but not connected to another word
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.A,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.B,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.C,Coordinate.Row.EIGHT));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        //player = new Player(board, bag);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        //System.out.println(board.getPlayedThisTurn());

        assertTrue(player.submit()== Game.Status.RETRY);

        // place invalid word
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.NINE));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.TEN));
        tiles.add(LetterTile.S);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.ELEVEN));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        //player = new Player(board,new Bag());
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        //System.out.println(board.getPlayedThisTurn());
        //System.out.println(player.submit());
        assertTrue(player.submit()== Game.Status.RETRY);
        board.resetPlayedThisTurn();

        board = new Board();
        bag = new Bag();
        player = new Player(board,bag);

        // no more tiles in bag
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
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

        assertTrue(player.submit()== Game.Status.RUNNING);
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.SEVEN));
        tiles.add(LetterTile.P);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.NINE));
        tiles.add(LetterTile.S);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.TEN));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        //player = new Player(board,new Bag());
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);
        //System.out.println(board.getPlayedThisTurn());
        for (int i=0; i<14; i++) {
            //System.out.println(player.getRack());
            player.getRack().removeTiles(player.getRack().getTilesList());
            player.getRack().drawTiles();
        }
        //System.out.println(player.submit());
        assertEquals(player.submit(), Game.Status.OVER);


    }

    @Test
    public void resetTurn() {
        Board board = new Board();
        Bag bag = new Bag();
        player = new Player(board,bag);

        player = new Player(new Board(),new Bag());
        //place AND on the starting tile
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(LetterTile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(LetterTile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        HashSet<Coordinate> coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        player.resetTurn();

        assertTrue(player.getRack().getNumTiles() == 7);
    }
    @Test
    public void exchangeOneTile(){
        Bag bag = new Bag();
        player = new Player(new Board(),bag);
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
        Bag bag = new Bag();
        player = new Player(new Board(),bag);
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
        Bag bag = new Bag();
        player = new Player(new Board(),bag);
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
        assertEquals(6, tilesActual.size()); // should still have one removed tile
        for (int i = 0; i < tilesActual.size(); i++){
            assertEquals(tiles.get(i+1), tilesActual.get(i)); // rest of tiles should be the same
        }
        // exchange rack should still have the tile
        assertEquals(firstTile.get(0), r.getExchangeModel().getValueAt(0,0));

        // the number of tiles in the bag should be the same
        assertEquals(numTilesInBag,bag.getNumTilesLeft());

        assertEquals(playerScore,player.getScore()); // player should not gain any points
    }
}