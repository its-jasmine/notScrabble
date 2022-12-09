import javax.swing.*;
import java.io.Serializable;

public interface Tile extends Serializable {
    /**
     * Gets the total number of that letter to be included in the game.
     * @return how many of that letter are in the game
     */
    int getTotalNum();

    /**
     * Gets the score number of that letter
     * @return how many points this letter is worth
     */
    int getValue();

    /**
     * Converts Letter to a String.
     * @return the letter as a String
     */
    String toString();

    /**
     * Gets the original name of the tile
     * @return the name of the tile
     */
    String getName();
}
