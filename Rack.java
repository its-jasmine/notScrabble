import java.util.*;

import java.util.stream.Collectors;

/**
 * @author Arthur Atangana
 */

public class Rack {

    private List<Tile> tileList;
    private final static int MAXTILES = 7;
    private Bag bag;

    public Rack(Bag bag){
        List tileList = new ArrayList<Tile>();
        this.bag = bag;
    }
    public int getTilesAmount() {
        return tileList.size();
    } // I don't think anything outside of Rack needs this so it could be removed
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

    public boolean isTileinRack(Tile t){
        for (Tile tile: getTilesList()){
            if (tile == t){
                return true;
            }
        }
        return false;
    }



}
