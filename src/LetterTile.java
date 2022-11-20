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
    A(1, 9, "A_tile.jpg"),
    B(3, 2, "B_tile.jpg"),
    C(3, 2, "C_tile.jpg"),
    D(2, 4, "D_tile.jpg"),
    E(1, 12, "E_tile.jpg"),
    F(4, 2, "F_tile.jpg"),
    G(2, 3, "G_tile.jpg"),
    H(4, 2, "H_tile.jpg"),
    I(1, 9, "I_tile.jpg"),
    J(8, 1, "J_tile.jpg"),
    K(5, 1, "K_tile.jpg"),
    L(1, 4, "L_tile.jpg"),
    M(3, 2, "M_tile.jpg"),
    N(1, 6, "N_tile.jpg"),
    O(1, 8, "O_tile.jpg"),
    P(3, 2, "P_tile.jpg"),
    Q(10, 1, "Q_tile.jpg"),
    R(1, 6, "R_tile.jpg"),
    S(1, 4, "S_tile.jpg"),
    T(1, 6, "T_tile.jpg"),
    U(1, 4, "U_tile.jpg"),
    V(4, 2, "V_tile.jpg"),
    W(4, 2, "W_tile.jpg"),
    X(8, 1, "X_tile.jpg"),
    Y(4, 2, "Y_tile.jpg"),
    Z(10, 1, "Z_tile.jpg");

    /** The score value of the tile */
    public final int value; // set to public so it can be accessed directly. Since it's final it can't be changed
    /** The total quantity of that tile in the bag */
    private final int totalNum; // total number of this letter in the game
    private final String path;
    public final ImageIcon image;

    /**
     * Creates a new tile with the specified letter, value and quantity.
     *
     * @param value         of the tile
     * @param totalNum      the quantity of that tile in the bag
     * @param path
     */
    LetterTile(int value, int totalNum, String path) {
        this.value = value;
        this.totalNum = totalNum;
        this.path = path;
        this.image = new ImageIcon(path);
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
    public ImageIcon getImage(){ return image;}

    /**
     * Converts Letter to a String.
     * @return the letter as a String
     */
    @Override
    public String toString() {
        return this.name();
    }
    public String getName(){return this.name();}

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("images/A_tile.jpg"));
        }catch (IOException e){
        }
        contentPane.add(new JLabel(LetterTile.B.getImage()));
        //contentPane.add(new JLabel(new ImageIcon("images/A_tile.jpg")));



        frame.setVisible(true);
        frame.setSize(50,50);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
