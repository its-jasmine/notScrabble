/**
 * Represents a square on the game board.
 * @author Jasmine Gad El Hak
 * @author Rebecca Elliott
 * @version Milestone1
 */

public class Square {
    /** The allowable square type on game board */
    public enum Type {
        START("*", 1,1),
        PLAIN("", 1,1),
        DOUBLE_LETTER("DL", 2,1),
        TRIPLE_LETTER("TL", 3,1),
        DOUBLE_WORD("DW",1,2),
        TRIPLE_WORD("TW",1,3);

        public final String stringDisplay;
        public final int letterMultiplier;
        public final int wordMultiplier;

        // public final Image squareImage... //TODO
        Type(String stringDisplay, int letterMultiplier, int wordMultiplier) {
            this.stringDisplay = stringDisplay;
            this.letterMultiplier = letterMultiplier;
            this.wordMultiplier = wordMultiplier;
        }
        public String toString(){
            return stringDisplay;
        }
    }
    /** The tile currently placed on the square, or null */
    private Tile tile;
    /** The square type */
    private final Type type;

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
    public void setTile(Tile tile) {
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
        if (isEmpty()) return type.toString();
        return tile.toString();
    }
}
