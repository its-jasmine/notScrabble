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
        button.setBorder(BorderFactory.createLineBorder(Color.green, 5));
        String input = e.getActionCommand();
        getTile(input);


    }
    private void getTile(String input){
        Tile t = Tile.valueOf(input);
        //System.out.println(t+ " in rack controller");
        //boardModel.tileToPlace(t);
        rackModel.removeTileFromRack(t);
        boardModel.tileToPlace(t);
/*
        if (rackModel.isTileinRack(t)){//just in case;

        }
*/
    }

}
