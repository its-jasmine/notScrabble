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
        Coordinate c = new Coordinate(column, row);
        return c;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String[] input = e.getActionCommand().split(" ");
        Coordinate c = getCoordinates(input);
        Tile t = boardModel.getTileToPlace();
        if (t != null){
            System.out.println(t);
            button.setText(t.letter);
            boardModel.placeTile(c,t);
        }
        else {
            System.out.println("tileToPlace in board is null?");
        }

        //boardModel.resetTiletoPlace();

        //e.setText(t.letter); // trying to make the button text change

    }

}
