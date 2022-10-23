import java.util.*;

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
    }
    public List<Tile> getTilesList() {
        return tileList;
    }

    /**
     * Draws missing tiles from the bag up to 7 and refills the rack.
     * Also updates the tileAmount
     *
     */
    public void drawTiles(){
        tileList.addAll(bag.drawTiles(MAXTILES - tileList.size()));
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
    public boolean isTileinRack(Tile t){
        for (Tile tile: getTilesList()){
            if (tile == t){
                return true;
            }
        }
        return false;
    }


}
