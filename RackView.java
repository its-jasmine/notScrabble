import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RackView extends JPanel {

    private JButton rackGrid[];
    private Rack rackModel;
    private RackController rackController;
    private Board boardModel;
    public RackView(Board board, Rack rack){
        super();
        this.boardModel = board;
        this.rackModel = rack;
        rackController = new RackController(rackModel, boardModel);
        this.setLayout(new GridLayout(1,19));
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        JButton passButton = new JButton("Pass");
        passButton.setBackground(Color.RED);
        this.add(passButton);

        rackGrid = new JButton[7];
        for (int i = 0; i < 7; i++) {
            JButton button = new JButton();
            //button.setActionCommand();
            button.setBackground(Color.ORANGE);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            rackGrid[i] = button;
            button.addActionListener(rackController);
            this.add(button);
        }
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.WHITE);
        this.add(submitButton);
    }


    public void updateRackView(RackEvent e) {
        for (JButton button : rackGrid){
            if (button.getText() != null){
                continue;
            }
            else{
                button.setText(e.getTile().letter);
            }
        }
    }
}
