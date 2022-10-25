import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a rack that contains the tiles that a player can use during their turn.
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Rack {
    /** The list of tiles in the rack */
    private List<Tile> tileList;
    /** The maximum number of tiles in the rack */
    private final static int MAXTILES = 7;
    /** The bag that the tiles will be drawn from */
    private final Bag bag;

    /**
     * Creates a new full rack (has 7 tiles, drawn from given bag).
     * @param bag where the tiles are drawn from
     */
    public Rack(Bag bag){
        tileList = new ArrayList<>();
        this.bag = bag;
        drawTiles(); // draw tiles at beginning of game
    }

    /**
     * Return the number of tiles currently in the rack.
     * @return number of tiles in rack
     */
    public int getTilesAmount() {
        return tileList.size();
    } // I don't think anything outside of Rack needs this so it could be removed

    /**
     * Returns the list of tiles in the rack.
     * @return list of tiles in the rack
     */
    public List<Tile> getTilesList() {
        return tileList;
    }

    /**
     * Draws missing tiles from the bag up to 7 and refills the rack.
     * @return OVER if the rack has no Tiles after trying to draw, RUNNING otherwise
     */
    public Game.Status drawTiles(){
        tileList.addAll(bag.drawTiles(MAXTILES - tileList.size()));
        if (tileList.size() == 0) return Game.Status.OVER; // no Tiles left, signal for Game that the game is over
        return Game.Status.RUNNING;
    }

    /**
     * gets the score of the Tiles currently on the rack
     * @return int of the score.
     */
    public int getRackScore(){
        int rackScore = 0;
        for (Tile tile : tileList){
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
        return tileList.stream().map(tile -> tile + " ").collect(Collectors.joining());
    }

    /**
     * Verifies if the tile is in the rack.
     * @param t : the tile to check if it is in the rack.
     * @return true if the tile is in the rack, false otherwise.
     */
    public boolean isTileinRack(Tile t){
        for (Tile tile: tileList){
            if (tile == t){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a specific tile from the rack
     * @param t : the tile to be removed from the rack.
     */
    private void removeTileFromRack(Tile t){
        tileList.remove(t);
    }
    /**
     * Removes tiles from the rack
     * @param tiles : a list of tiles to be removed from the rack.
     */
    public void removeTiles(ArrayList<Tile> tiles){
        for (Tile t: tiles){
            removeTileFromRack(t);
        }
    }
}
