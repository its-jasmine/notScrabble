import java.util.*;
public class Rack {

    private List<Tile> tileList;
    private final static int MAXTILES = 7;
    private static Bag bag;

    public Rack(){
        List tileList = new ArrayList<Tile>();
    }
    public int getTilesAmount() {
        return tileList.size();
    } // I don't think anything outside of Rack needs this so it could be removed
    public List<Tile> getTilesList() {
        return tileList;
    }

    /**
     * Draws missing tiles from the bag up to 7 and refills the rack.
     * Also updates the tileAmount
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


}
