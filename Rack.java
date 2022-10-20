import java.util.*;
public class Rack {

    private List<Letter> tileList;
    private final static int MAXTILES = 7;
    private static Bag bag;

    public Rack(){
        List lettersList = new ArrayList<Letter>();
    }
    public int getTilesAmount() {
        return tileList.size();
    }
    public List<Letter> getTilesList() {
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
     * gets the score of the letters currently on the rack
     * @return int of the score.
     */
    public int getRackScore(){
        int rackScore;
        for (Letter l : tileList){
            rackScore += l.value;
        }
        return rackScore;
    }


}
