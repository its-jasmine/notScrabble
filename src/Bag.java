import java.io.Serializable;
import java.util.*;
import static java.util.Collections.shuffle;

/**
 * This class represents the bag full of tiles that the players draw from.
 * @author Rebecca Elliott
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Bag implements Serializable {
    public static final int MAX_TILES = 100; // will be 100 when blanks are added
    /** The list of tiles in the bag */
    private List<Tile> tiles;

    /**
     * Creates a new bag with the appropriate amount of tiles (according to Scrabble specifications). 100 when blanks are included.
     */
    public Bag() {
        this.tiles = new ArrayList<>();
        for (LetterTile t : LetterTile.values()) {
            for (int i = t.getTotalNum();i > 0; i--) {
                tiles.add(t);
            }
        }
        for (int i = 0; i < BlankTile.TOTAL_NUM; i++){
                tiles.add(new BlankTile());
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

    /**
     * Gets the number of tiles still in the bag.
     * @return Number of tiles in bag
     */
    public int getNumTilesLeft() {
        return tiles.size();
    }

    /**
     * Puts given tiles back into bag.
     * @param rTiles tiles to be returned
     */
    public void returnTiles(ArrayList<Tile> rTiles) {
        tiles.addAll(rTiles);
        shuffle(tiles);
    }

    /**
     * Attempts to exchange the tiles provided with the same number of new tiles from the bag.
     * @param tilesToExchange the tiles to exchange
     * @return list of new tiles from exchange if there are sufficient nnumber of tiles left in the bag, otherwise, empty list.
     */
    public ArrayList<Tile> exchangeTiles(ArrayList<Tile> tilesToExchange) {
        ArrayList<Tile> newTiles = new ArrayList<>();
        int numTilesNeeded = tilesToExchange.size();
        if (getNumTilesLeft() < numTilesNeeded) return newTiles; // not enough tiles left in the bag
        newTiles.addAll(drawTiles(numTilesNeeded));
        returnTiles(tilesToExchange); // tiles are returned to the bag after the new tiles are drawn
        return newTiles;
    }
}
