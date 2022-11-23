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

    @Before
    public void setUp() throws Exception {
        bag = new Bag();
        board = new Board();
        player = new Player(board, bag);
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    @Test
    public void addToScore() {
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
        //place AND on the starting tile
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
        for (int i=0; i<15; i++) {
            //System.out.println(player.getRack());
            player.getRack().removeTiles(player.getRack().getTilesList());
            player.getRack().drawTiles();
        }
        //System.out.println(player.submit());
        assertTrue(player.submit()== Game.Status.OVER);
    }

    @Test
    public void resetTurn() {
        //place AND on the starting tile
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
}