import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a rack that contains the tiles that a player can use during their turn.
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Rack implements Iterable<Tile>{
    /** The list of tiles in the rack */
    private DefaultTableModel rackModel;
    /** The maximum number of tiles in the rack */
    private final static int MAXTILES = 7;
    /** The bag that the tiles will be drawn from */
    private final Bag bag;

    /**
     * Creates a new full rack (has 7 tiles, drawn from given bag).
     * @param bag where the tiles are drawn from
     */
    public Rack(Bag bag){
        rackModel = new DefaultTableModel(1, 7){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        this.bag = bag;
        drawTiles(); // draw tiles at beginning of game
    }

    /**
     * Return the number of tiles currently in the rack.
     * @return number of tiles in rack
     */
    public int getNumTiles() {
        int numTiles = 0;
        for (Tile t : this) {
            if (t == null ||  t.equals(Tile.EMPTY) ) continue;
            numTiles++;
        }

        //for (int i = 0; i < MAXTILES; i++) {
        //    if (!rackModel.getValueAt(0, i).equals(Tile.EMPTY)) numTiles++;
        //}
        return numTiles;
    } // I don't think anything outside of Rack needs this so it could be removed


    /**
     * Returns the list of tiles in the rack.
     * @return list of tiles in the rack
     */
    public ArrayList<Tile> getTilesList() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile t : this) {
            tiles.add(t);
        }
        return tiles;
    }

    public TableModel getModel() {
        return rackModel;
    }

    /**
     * Draws missing tiles from the bag up to 7 and refills the rack.
     * @return OVER if the rack has no Tiles after trying to draw, RUNNING otherwise
     */
    public Game.Status drawTiles(){
        ArrayList<Tile> newTiles = new ArrayList<>();
        newTiles.addAll(bag.drawTiles(MAXTILES - getNumTiles()));
        for (Tile t: newTiles) {
            for (int i = 0; i < MAXTILES; i++) {
                Tile r = (Tile) rackModel.getValueAt(0, i);
                if (r == null || r.equals(Tile.EMPTY)) {
                    rackModel.setValueAt(t, 0, i);
                    break;
                }
            }
        }
        if (newTiles.size() !=0 ) System.out.println("drawTiles had leftover tiles"); //error
        if (getNumTiles() == 0) return Game.Status.OVER; // no Tiles left, signal for Game that the game is over
        return Game.Status.RUNNING;
    }

    /**
     * gets the score of the Tiles currently on the rack
     * @return int of the score.
     */
    public int getRackScore(){
        int rackScore = 0;
        for (Tile tile : this){
            rackScore += tile.value;
        }
        return rackScore;
    }

    /**
     * Returns string representation of the rack
     * @return String of all the tile letters.
     */
    public String toString(){
        // Each tile letter is separated by a space and is collected into a single string
        ArrayList<Tile> tileList = getTilesList();
        return tileList.stream().map(tile -> tile + " ").collect(Collectors.joining());
    }

    /**
     * Verifies if the tile is in the rack.
     * @param t : the tile to check if it is in the rack.
     * @return true if the tile is in the rack, false otherwise.
     */
    public boolean isTileinRack(Tile t){
        for (Tile tile: this){
            if (tile == t){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a specific tile from the rack
     * @param tile : the tile to be removed from the rack.
     */
    private Tile removeTileFromRack(Tile tile){
        for (int i = 0; i < MAXTILES; i++) {
            Tile r = (Tile) rackModel.getValueAt(0, i);
            if (r == tile) {
                rackModel.setValueAt(Tile.EMPTY, 0, i);
                return r;
            }
        }
        return Tile.EMPTY;
    }
    /**
     * Removes tiles from the rack
     * @param tiles : a list of tiles to be removed from the rack.
     */
    public ArrayList<Tile> removeTiles(ArrayList<Tile> tiles){
        ArrayList<Tile> removedTiles = new ArrayList<>();
        for (Tile t: tiles){
            Tile r = removeTileFromRack(t);
            if (r == null || r.equals(Tile.EMPTY)) removedTiles.add(r);
        }
        return removedTiles;
    }

    @Override
    public Iterator<Tile> iterator() {
        Iterator<Tile> it = new Iterator<Tile>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                if (currentIndex >= MAXTILES) return false;
                Tile tile = (Tile) rackModel.getValueAt(0, currentIndex);
                while ((tile == null || tile.equals(Tile.EMPTY)) && currentIndex < MAXTILES - 1) {
                    currentIndex++;
                    tile = (Tile) rackModel.getValueAt(0, currentIndex);
                }

                return tile == null || tile.equals(Tile.EMPTY);
            }

            @Override
            public Tile next() {
                Tile tile = (Tile) rackModel.getValueAt(0, currentIndex);
                currentIndex++;
                return tile;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }


}
