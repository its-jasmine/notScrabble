/**
 * Models the letters in the game. Each value has an associated character, letter point value, and the total number of that
 * letter in the game.
 * @author Rebecca Elliott
 * @version Milestone1
 */

public enum Tile {
    A("A", 1, 9),
    B("B", 3, 2),
    C("C", 3, 2),
    D("D", 2, 4),
    E("E", 1, 12),
    F("F", 4, 2),
    G("G", 2, 3),
    H("H", 4, 2),
    I("I", 1, 9),
    J("J", 8, 1),
    K("K", 5, 1),
    L("L", 1, 4),
    M("M", 3, 2),
    N("N", 1, 6),
    O("O", 1, 8),
    P("P", 3, 2),
    Q("Q", 10, 1),
    R("R", 1, 6),
    S("S", 1, 4),
    T("T", 1, 6),
    U("U", 1, 4),
    V("V", 4, 2),
    W("W", 4, 2),
    X("X", 8, 1),
    Y("Y", 4, 2),
    Z("Z", 10, 1),
    BLANK(" ", 0, 2);

    /** The letter on the tile */
    public final String letter; // set to public so it can be accessed directly. Since it's final it can't be changed
    /** The score value of the tile */
    public final int value; // set to public so it can be accessed directly. Since it's final it can't be changed
    /** The total quantity of that tile in the bag */
    private final int totalNum; // total number of this letter in the game

    /**
     * Creates a new tile with the specified letter, value and quantity.
     * @param letter of the tile
     * @param value of the tile
     * @param totalNum the quantity of that tile in the bag
     */
    Tile(String letter, int value, int totalNum) {
        this.letter = letter;
        this.value = value;
        this.totalNum = totalNum;
    }

    /**
     * Gets the total number of that letter to be included in the game.
     * @return how many of that letter are in the game
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * Converts Letter to a String.
     * @return the letter as a String
     */
    @Override
    public String toString() {
        return letter;
    }
}
