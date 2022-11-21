import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;

/**
 * Models the non-blank letters in the game. Each value has an associated character, letter point value, and the total number of that
 * letter in the game.
 * @author Rebecca Elliott
 * @version Milestone1
 */

public enum LetterTile implements Tile{
    A(1, 9),
    B(3, 2),
    C(3, 2),
    D(2, 4),
    E(1, 12),
    F(4, 2),
    G(2, 3),
    H(4, 2),
    I(1, 9),
    J(8, 1),
    K(5, 1),
    L(1, 4),
    M(3, 2),
    N(1, 6),
    O(1, 8),
    P(3, 2),
    Q(10, 1),
    R(1, 6),
    S(1, 4),
    T(1, 6),
    U(1, 4),
    V(4, 2),
    W(4, 2),
    X(8, 1),
    Y(4, 2),
    Z(10, 1);

    /** The score value of the tile */
    public final int value; // set to public so it can be accessed directly. Since it's final it can't be changed
    /** The total quantity of that tile in the bag */
    private final int totalNum; // total number of this letter in the game

    /**
     * Creates a new tile with the specified letter, value and quantity.
     *
     * @param value         of the tile
     * @param totalNum      the quantity of that tile in the bag
     */
    LetterTile(int value, int totalNum) {
        this.value = value;
        this.totalNum = totalNum;

    }

    /**
     * Gets the score number of that letter
     * @return how many points this letter is worth
     */
    public int getValue() {
        return value;
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
        return this.name();
    }
    public String getName(){return this.name();}

}
