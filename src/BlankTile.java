/**
 * Models the blank tiles in the game.
 * @author Victoria Malouf
 * @version Milestone3
 */
public class BlankTile implements Tile {

    /** The letter on the tile */
    public String letter; // set to public so it can be accessed directly. Since it's final it can't be changed

    /** The score value of the blank tile */
    public static final int VALUE = 0; // set to public so it can be accessed directly. Since it's final it can't be changed

    /** The total quantity of blank tiles in the bag */
    public static final int TOTAL_NUM = 2; // total number of blank tiles in the game

    public BlankTile(){
        this.letter = "'";
    }

    /**
     * Sets the blank tile letter
     * @param tile the blank tile will be set to
     */
    public void setLetter(LetterTile tile){
        this.letter = tile.toString();
    }

    /**
     * Resets the blank tile letter
     */
    public void resetLetter(){
        this.letter = "'";
    }

    /**
     * Returns the letter of the blank tile
     * @return letter of blank tile
     */
    public String getLetter(){
        return letter;
    }

    /**
     * Gets the score number of the blank tile
     * @return how many points this blank tile is worth
     */
    public int getValue(){
        return VALUE;
    }

    /**
     * Gets the total number of blank tiles to be included in the game.
     * @return how many of blank tiles are in the game
     */
    public int getTotalNum(){
        return TOTAL_NUM;
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
