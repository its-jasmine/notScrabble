import java.util.*;
import static java.util.Collections.shuffle;

/**
 * This class represents the bag full of tiles that the players draw from.
 * @author Rebecca Elliott
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Bag {
    /** The list of tiles in the bag */
    private List<Tile> tiles;

    /**
     * Creates a new bag with the appropriate amount of tiles (according to Scrabble specifications).
     */
    public Bag() {
        this.tiles = new ArrayList<>();
        for (Tile t : Tile.values()) {
            for (int i = t.getTotalNum();i > 0; i--) {
                tiles.add(t);
            }
        }
        shuffle(tiles); // randomizes the order tiles will be drawn in
    }

    /**
     * Allows player to draw multiple tiles at random.
     * @param numToDraw number of tiles to draw
     * @return list of drawn tiles removed from the bag
     */
    public ArrayList<Tile> drawTiles(int numToDraw) {
        ArrayList<Tile> returnList = new ArrayList<>();
        for (int i = numToDraw; i > 0; i--){
            Tile drawnLetter = drawTile();
            if (drawnLetter != null) returnList.add(drawnLetter);
        }
        return returnList;
    }

    /**
     * Allows player to draw one tile at random.
     * @return tile removed from the bag
     */
    private Tile drawTile() {
        if (tiles.size() == 0) return null;
        return tiles.remove(tiles.size()-1);
    }

}
