import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardController implements ActionListener {

    private Board boardModel;

    public BoardController(Board boardModel){
        this.boardModel = boardModel;
    }
    private Coordinate getCoordinates(String[] input){
        Coordinate.Row row = Coordinate.Row.values()[Integer.valueOf(input[1])];
        Coordinate.Column column = Coordinate.Column.values()[Integer.valueOf(input[0])];
        return new Coordinate(column, row);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String[] input = e.getActionCommand().split(" ");
        Coordinate c = getCoordinates(input);
        Tile t = boardModel.getTileToPlace();
        if (t != null) {
            if (boardModel.placeTile(c, t)) { // if placing the tile on board is successfull
                button.setText(t.letter); //update button text on board tile
                boardModel.resetTiletoPlace(); //resets selected tile from rack to null
                specificrackModel.removeTileFromRack(t); //remove tile from rack
            }
            else{

            }
        }
        else {
            return;
        }
    }

}
