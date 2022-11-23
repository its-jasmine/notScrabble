import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.SysexMessage;
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
        assertEquals(player.getScore(), 0);
        player.addToScore(1);
        assertEquals(player.getScore(), 1);
        player.addToScore(999);
        assertEquals(player.getScore(), 1000);
        player.addToScore(-1);
        assertEquals(player.getScore(), 999);
        player.addToScore(-1000);
        assertEquals(player.getScore(), -1);
    }

    @Test
    public void endTurn() {
        //is actually private
    }

    @Test
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

        assertSame(player.submit(), Game.Status.RETRY);
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

        assertSame(player.submit(), Game.Status.RETRY);
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

        assertSame(player.submit(), Game.Status.OVER);
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

        assertEquals(player.getRack().getNumTiles(), 7);
    }
}