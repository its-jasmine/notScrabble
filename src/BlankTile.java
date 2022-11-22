import javax.swing.*;

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

    public BlankTile(String letter){
        if (letter.length() == 1 && Character.isLetter(letter.charAt(0)) && Character.isUpperCase(letter.charAt(0))) {
            this.letter = letter;
        } else {
            System.out.println("invalid character");
            this.letter = "'";
        }
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
     * Determines if this instance of BlankTile has the same letter as obj
     * @param obj
     * @return true if obj is equal to this BlankTile
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BlankTile)) return false;
        BlankTile c = (BlankTile) obj;
        return c.letter == this.letter;
    }

    /**
     * Converts Letter to a String.
     * @return the letter as a String
     */
    @Override
    public String toString() {
        return letter;
    }

    /**
     * returns the original name of a blank tile
     * @return BLANK
     */
    @Override
    public String getName() {return "BLANK";}

}
