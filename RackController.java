import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RackController implements ActionListener {

    private Rack rackModel;
    private Board boardModel;

    public RackController(Rack rackModel, Board BoardModel){
        this.rackModel = rackModel;
        this.boardModel = boardModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        getTile(input);

    }
    private void getTile(String input){
        Tile t = Tile.valueOf(input);
        if (rackModel.isTileinRack(t)){//just in case;
            rackModel.removeTileFromRack(t);
            boardModel.tileToPlace(t);
        }
    }

}
