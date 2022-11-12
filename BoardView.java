import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardView extends JPanel {

    private JButton boardGrid[][];
    private Board boardModel;

    private BoardController boardController;

    public BoardView(Board board){
        super();
        boardModel = board;
        boardController = new BoardController(boardModel);
        this.setLayout(new GridLayout(15,15));
        this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        boardGrid = new JButton[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton();
                button.setActionCommand(i+" "+j);
                button.setOpaque(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardGrid[i][j] = button;
                button.addActionListener(boardController);
                //button.addActionListener(rackController);
                this.add(button);
            }
        }

    }
    public void addTileToBoardView(BoardEvent e){
        getGridButton(e).setText(e.getTile().letter);
    }

    public void removeTileFromBoardView(BoardEvent e){
        getGridButton(e).setText(null);
    }
    private JButton getGridButton(BoardEvent e){
        Coordinate c = e.getCoordinate();
        int column = c.column.ordinal();
        int row = c.row.ordinal();
        JButton button = boardGrid[row][column];
        return button;
    }
}
