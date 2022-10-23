import java.util.*;
import java.util.stream.Collectors;

public class Rack {

    private List<Tile> tileList;
    private final static int MAXTILES = 7;
    private static Bag bag;

    public Rack(){
        List tileList = new ArrayList<Tile>();
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
    /**
     * Returns string representation of the rack
     * @return String of all the tile letters.
     */
    public String toString(){
        // Each tile letter is separated by a space and is collected into a single string
        return tileList.stream().map(tile -> tile + " ").collect(Collectors.joining());;
    }



}
