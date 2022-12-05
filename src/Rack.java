import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Represents a rack that contains the tiles that a player can use during their turn.
 * @author Arthur Atangana
 * @author Jasmine Gad El Hak
 * @version Milestone4
 */
public class Rack implements Iterable<Tile>{

    /** The list of tiles in the rack */
    private DefaultTableModel rackModel;
    /** The maximum number of tiles in the rack */
    public final static int MAXTILES = 7;
    /** The bag that the tiles will be drawn from */
    private final Bag bag;
    private DefaultTableModel tilesToExchange;

    /**
     * Creates a new full rack (has 7 tiles, drawn from given bag).
     * @param bag where the tiles are drawn from
     */
    public Rack(Bag bag){
        tilesToExchange = new DefaultTableModel(1, 7){
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return Tile.class;
            }
        };
        rackModel = new DefaultTableModel(1, 7) {
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                //return ImageIcon.class;
                return Tile.class;
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
            if (t == null) continue;
            numTiles++;
        }
        return numTiles;
    }


    /**
     * Returns an ArrayList of tiles in the rack.
     * @return list of tiles in the rack
     */
    public ArrayList<Tile> getTilesList() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile t : this) {
            tiles.add(t);
        }
        return tiles;
    }

    /**
     * Gets the DefaultJTableModel that the tiles are stored in
     * @return rack tiles
     */
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
        int leftOverTiles = newTiles.size();
        for (Tile t: newTiles) {
            for (int i = 0; i < MAXTILES; i++) {
                Tile r = (Tile) rackModel.getValueAt(0, i);
                if (r == null) {
                    rackModel.setValueAt(t, 0, i);
                    leftOverTiles--;
                    break;
                }
            }
        }
        if (leftOverTiles !=0 ) System.out.println("drawTiles had leftover tiles"); //TODO error
        if (getNumTiles() == 0) return Game.Status.OVER; // no Tiles left, signal for Game that the game is over
        return Game.Status.RUNNING;
    }

    /**
     * gets the score of the Tiles currently on the rack
     * @return int of the score.
     */
    public int tallyRackScore(){
        int rackScore = 0;
        for (Tile tile : this){
            rackScore += tile.getValue();
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
    public boolean isTileInRack(Tile t){
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
    public Tile removeTileFromRack(Tile tile){
        for (int i = 0; i < MAXTILES; i++) {
            Tile r = (Tile) rackModel.getValueAt(0, i);
            if (r == null) continue;

            // needed if trying to remove a blank, using a set blank as input tile
            if (r instanceof BlankTile && tile instanceof BlankTile) {
                rackModel.setValueAt(null, 0, i);
                return r;
            }

            else if (r.equals(tile)) {
                rackModel.setValueAt(null, 0, i);
                return r;
            }
        }
        return null;
    }
    /**
     * Removes tiles from the rack
     * @param tiles : a list of tiles to be removed from the rack.
     */
    public ArrayList<Tile> removeTiles(ArrayList<Tile> tiles){
        ArrayList<Tile> removedTiles = new ArrayList<>();
        for (Tile t: tiles){
            Tile r = removeTileFromRack(t);
            if (r != null) removedTiles.add(r);
        }
        return removedTiles;
    }


    /**
     * Puts tiles on the rack
     * @param tiles : a list of tiles to be removed from the rack.
     */
    public void putTilesOnRack(ArrayList<Tile> tiles){
        for (Tile t: tiles){
            if (!putTileOnRack(t)) ; //System.out.println("couldn't add tile");
        }
    }
    /**
     * Put tile on the rack
     * @param tile : a list of tiles to be removed from the rack.
     */
    public boolean putTileOnRack(Tile tile){

        for (int i = 0; i < MAXTILES; i++) {
            Tile r = (Tile) rackModel.getValueAt(0, i);
            if (r == null) {
                rackModel.setValueAt(tile, 0, i);
                return true;
            }
        }
        return false;
    }


    public ArrayList<Tile> getTilesToExchange() {
        ArrayList<Tile> tileList = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Tile tileToExchange = (Tile)tilesToExchange.getValueAt(0,i);
            if (tileToExchange != null) {
                tileList.add(tileToExchange);
            }
        }
        return tileList;
    }


    @Override
    public Iterator<Tile> iterator() {
        Iterator<Tile> it = new Iterator<Tile>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                if (currentIndex >= MAXTILES) return false;
                Tile tile = (Tile) rackModel.getValueAt(0, currentIndex);
                while (tile == null && currentIndex < MAXTILES - 1) { //skip over empty tiles
                    currentIndex++;
                    tile = (Tile) rackModel.getValueAt(0, currentIndex);
                }

                return !(tile == null);
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

    public TableModel getExchangeModel() {
        return tilesToExchange;
    }

    public void resetTilesToExchange() {
        if (getTilesToExchange().size() > 0) putTilesOnRack(getTilesToExchange());
        for (int i = 0; i < MAXTILES; i++) {
            tilesToExchange.setValueAt(null,0,i);
        }
    }
}
