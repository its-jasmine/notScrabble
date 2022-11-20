import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashSet;

public class BoardView extends JTable {

    private final Board board;
    public BoardView(Board board) {
        super();
        this.board = board;
        setModel(board.getModel());
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) this.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(48);
        setOpaque(true);
        setFillsViewportHeight(true);
        setBackground(new Color(91,159,115));
        setGridColor(Color.BLACK);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new BoardTransferHelper());
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
    }

    @Override
    public Square getValueAt(int row, int col) {
        return (Square) getModel().getValueAt(row, col);
    }

    public HashSet<Coordinate> getPreviouslyPlayed() {
        return board.getPreviouslyPlayed();
    }

    public void addCoordinatePlayedThisTurn(Coordinate coordinate) {
        board.getPlayedThisTurn().add(coordinate);
    }


    public void removeCoordinatePlayedThisTurn(Coordinate coordinate) {
        board.getPlayedThisTurn().remove(coordinate);
    }


    public void submit() { // for testing
        System.out.println(board.submit());
        addThisTurnToPreviously();
    }

    public void addThisTurnToPreviously() { // for testing
        board.addThisTurnToPreviously();
    }

    public void setTileAt(Tile tile, int row, int col) {
        Square squareTrial = (Square) dataModel.getValueAt(row, col);
        squareTrial.setTile(tile);
    }

    public Tile removeTileAt(int row, int col) {
        Square square = (Square) dataModel.getValueAt(row, col);
        return square.removeTile();
    }

    public Board getBoard() {
        return board;
    }
}
