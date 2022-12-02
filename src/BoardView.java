import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.HashSet;

public class BoardView extends JTable {

    private final Board board;
    public BoardView(Board board) {
        super();
        this.board = board;
        setModel(board.getModel());
        setDefaultRenderer(Square.class,new BoardRenderer());
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(48);
        setOpaque(true);
        setFillsViewportHeight(true);
        setBackground(new Color(91,159,115));
        setGridColor(Color.BLACK);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new BoardTransferHelper(board.moves));
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
    }

    @Override
    public Square getValueAt(int row, int col) {
        return (Square) getModel().getValueAt(row, col);
    }

    /**
     * gets the previously played tiles on the board
     * @return A hashset of coordinate objects
     */
    public HashSet<Coordinate> getPreviouslyPlayed() {
        return board.getPreviouslyPlayed();
    }

    /**
     * adds the coordinate of the tile played this turn to the list of tiles played
     * @param coordinate a coordinate played this turn
     */
    public void addCoordinatePlayedThisTurn(Coordinate coordinate) {
        board.getPlayedThisTurn().add(coordinate);
    }


    /**
     * removes the coordinate of the tile played this turn to the list of tiles played
     * @param coordinate a coordinate played this turn
     */
    public void removeCoordinatePlayedThisTurn(Coordinate coordinate) {
        board.getPlayedThisTurn().remove(coordinate);
    }


    public void submit() { // for testing
        System.out.println(board.submit());
        addThisTurnToPreviously();
    }

    /**
     * adds the tiles played this turn to the tiles played previously
     */
    public void addThisTurnToPreviously() { // for testing
        board.addThisTurnToPreviously();
    }

    /**
     * adds a tile to a square
     * @param tile the tile to be placed in the square
     * @param row the row coordinate of the square
     * @param col the column coordinate of the square
     */
    public void setTileAt(Tile tile, int row, int col) {
        Square squareTrial = (Square) dataModel.getValueAt(row, col);
        squareTrial.setTile(tile);
    }

    /**
     * removes a tile from the square
     * @param row the row in which the tile is
     * @param col the column in which the tile is
     * @return the tile in the square
     */
    public Tile removeTileAt(int row, int col) {
        Square square = (Square) dataModel.getValueAt(row, col);
        return square.removeTile();
    }

    /**
     * @return a board
     */
    public Board getBoard() {
        return board;
    }
}
