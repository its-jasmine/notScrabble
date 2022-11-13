import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class RackController implements ActionListener {

    private Rack rackModel;
    private Board boardModel;

    public RackController(Rack rackModel, Board boardModel){
        this.rackModel = rackModel;
        this.boardModel = boardModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.setBorder(BorderFactory.createLineBorder(Color.green, 5)); // highlights clicked button
        String input = e.getActionCommand();
        getTile(input); //give tile to board and remove it from rack


    }
    private void getTile(String input){
        Tile t = rackModel.removeTileFromRack(Integer.valueOf(input));
        boardModel.tileToPlace(t);
    }

}
