import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardController implements ActionListener {

    private Board boardModel;
    private Rack rackModel;

    public BoardController(Board boardModel){
        this.boardModel = boardModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String[] input = e.getActionCommand().split(" ");
        Coordinate c = getCoordinates(input);
        Tile t = boardModel.getTileToPlace();
        if (t != null) {
            if (boardModel.placeTile(c, t)) { // if placing the tile on board is successfull
                //button.setText(t.letter); //update button text on board tile, not needed since board updates view
                boardModel.resetTiletoPlace(); //resets selected tile from rack to null
                rackModel.removeTileFromRack(t); //remove tile from rack
            }
            else{
                return;
            }
        }
        else {
            return;
        }
    }
    private Coordinate getCoordinates(String[] input){
        Coordinate.Row row = Coordinate.Row.values()[Integer.valueOf(input[1])];
        Coordinate.Column column = Coordinate.Column.values()[Integer.valueOf(input[0])];
        return new Coordinate(column, row);
    }

}
