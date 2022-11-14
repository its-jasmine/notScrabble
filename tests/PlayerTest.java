import org.junit.Test;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
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
        tiles.add(Tile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(Tile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(Tile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        HashSet<Coordinate> coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertTrue(player.submit()== Game.Status.RUNNING);

        // place AND but not connected to another word
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(Tile.A);
        coordinates.add(new Coordinate(Coordinate.Column.A,Coordinate.Row.EIGHT));
        tiles.add(Tile.N);
        coordinates.add(new Coordinate(Coordinate.Column.B,Coordinate.Row.EIGHT));
        tiles.add(Tile.D);
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
        tiles.add(Tile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.NINE));
        tiles.add(Tile.D);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.TEN));
        tiles.add(Tile.S);
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
        tiles.add(Tile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(Tile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(Tile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        board.setPlayedThisTurn(coordinateHashSet);

        assertTrue(player.submit()== Game.Status.RUNNING);
        tiles = new ArrayList<>();
        coordinates = new ArrayList<>();
        tiles.add(Tile.L);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.SEVEN));
        tiles.add(Tile.P);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.NINE));
        tiles.add(Tile.S);
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
        assertTrue(player.submit()== Game.Status.OVER);


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
        tiles.add(Tile.A);
        coordinates.add(new Coordinate(Coordinate.Column.F,Coordinate.Row.EIGHT));
        tiles.add(Tile.N);
        coordinates.add(new Coordinate(Coordinate.Column.G,Coordinate.Row.EIGHT));
        tiles.add(Tile.D);
        coordinates.add(new Coordinate(Coordinate.Column.H,Coordinate.Row.EIGHT));
        HashSet<Coordinate> coordinateHashSet = new HashSet<>();
        coordinateHashSet.addAll(coordinates);
        board.placeTiles(coordinates,tiles);
        player.resetTurn();

        assertTrue(player.getRack().getNumTiles() == 7);
    }
}