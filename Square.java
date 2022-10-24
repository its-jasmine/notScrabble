/**
 * Represents a square on the game board.
 * @author Jasmine Gad El Hak
 * @author Rebecca Elliott
 * @version Milestone1
 */

public class Square {
    /** Acceptable square type on game board */
    public enum Type {
        START, PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD
    }
    /** The tile currently placed on the square, or null */
    private Tile tile;
    /** The square type */
    private Type type;

    /**
     * Creates a new empty square (i.e., with no tile placed on it) of the given type.
     * @param type the type of the new square
     */
    public Square(Type type){
        this.tile = null;
        this.type = type;
    }

    /**
     * Creates a new plain-type empty square (i.e., with no tile placed on it).
     */
    public Square(){
        this(Type.PLAIN);
    }

    /**
     * Returns the tile on the square (if present).
     * @return tile on square, or null (if empty).
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
        return tile == null;
    }

    /**
     * Places given tile on the square.
     * @param tile the tile to be placed on the square
     */
    public void placeTile(Tile tile) {
        this.tile = tile;
    }
    /**
     * Removes the tile on the square.
     * @return the tile that was on the square, or null if tile was empty.
     */
    public Tile removeTile() {
        Tile temp = this.tile;
        this.tile = null;
        return temp;
    }

    /**
     * Returns string representation of square.
     * @return if square is empty, string shortform of square type, otherwise letter of tile on square
     */
    public String toString(){
        return "_";
    }
}
