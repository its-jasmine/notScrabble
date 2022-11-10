import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardController implements ActionListener {

    private Board boardModel;

    public BoardController(Board boardModel){
        this.boardModel = boardModel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] input = e.getActionCommand().split(" ");
        Coordinate c = getCoordinates(input);
        Tile t = boardModel.getTileToPlace();
        boardModel.placeTile(c,t);
        boardModel.resetTiletoPlace();
        //e.setText(t.letter); // trying to make the button text change

    }
    private Coordinate getCoordinates(String[] input){
        Coordinate.Row row = Coordinate.Row.values()[Integer.valueOf(input[1])];
        Coordinate.Column column = Coordinate.Column.values()[Integer.valueOf(input[0])];
        Coordinate c = new Coordinate(column, row);
        return c;
    }

}
