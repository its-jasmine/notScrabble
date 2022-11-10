import javax.swing.*;
import java.awt.*;

public class RackView extends JPanel {

    private JButton rackGrid[][];
    private Rack rackModel;
    private RackController rackController;
    private Board boardModel;
    public RackView(Board board){
        super();
        this.boardModel = board;
        this.setLayout(new GridLayout(1,19));
        this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        rackGrid = new JButton[1][9];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton("A");
                //button.setActionCommand();
                button.setOpaque(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                rackGrid[i][j] = button;
                this.add(button);
            }
        }
        JButton submitButton = new JButton("Submit");
        this.add(submitButton);
    }
}
