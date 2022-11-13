/**
 * Represents a square on the game board.
 * @author Jasmine Gad El Hak
 * @author Rebecca Elliott
 * @version Milestone1
 */

public class SquareTrial {
    /** The allowable square type on game board */
    public enum Type {
        START, PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD, RACK
    }
    /** The tile currently placed on the square, or null */
    private Tile tile;
    /** The square type */
    private final Type type;

    /**
     * Creates a new empty square (i.e., with no tile placed on it) of the given type.
     * @param type the type of the new square
     */
    public SquareTrial(Type type){
        this.tile = Tile.EMPTY;
        this.type = type;
    }

    /**
     * Creates a new plain-type empty square (i.e., with no tile placed on it).
     */
    public SquareTrial(){
        this(Type.PLAIN);
    }

    /**
     * Returns the tile on the square without removing it.
     * @return tile on square.
     */
    public Tile getTile() {
        return tile;
    }


    /**
     * Returns the type of the square.
     * @return square type
     */
    public Type getType() {
        return type;
    }

    /**
     * Checks whether square is empty.
     * @return true if square does not have a tile placed on it, false otherwise.
     */
    public boolean isEmpty() {
        return tile.equals(Tile.EMPTY);
    }

    /**
     * Places given tile on the square.
     * @param tile the tile to be placed on the square
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    /**
     * Removes the tile on the square.
     * @return the tile that was on the square, or null if tile was empty.
     */
    public Tile removeTile() {
        Tile temp = this.tile;
        this.tile = Tile.EMPTY;
        return temp;
    }

    /**
     * Removes the tile on the square.
     * @return the tile that was on the square, or null if tile was empty.
     */
    public Tile swapTile(Tile t) {
        Tile temp = this.tile;
        this.tile = t;
        return temp;
    }

    /**
     * Returns string representation of square.
     * @return if square is empty, string shortform of square type, otherwise letter of tile on square
     */
    public String toString(){
        if (isEmpty()) return " ";
        return tile.toString();
    }
}
