import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.ArrayList;
import java.util.HashSet;

public class BoardView extends JTable {

    private final Board board;
    public BoardView(Board board) {
        super();
        this.board = board;
        setModel(board.getModel());

        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(50);
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

    public HashSet<Location> getPreviouslyPlayed() {
        return board.getPreviouslyPlayed();
    }

    public void addLocationPlayedThisTurn(Location location) {
        board.getPlayedThisTurn().add(location);
    }

    //TODO
    public void removeLocationPlayedThisTurn(Location location) {
        board.getPlayedThisTurn().remove(location);
    }

    public HashSet<Location> getPlayedThisTurn() {
        return board.getPlayedThisTurn();
    }

    private ArrayList<Coordinate> playedHashToList() {
        ArrayList<Coordinate> played = new ArrayList<>();
        for (Location l : board.getPlayedThisTurn()) {
            played.add(new Coordinate(Coordinate.Column.values()[l.col], Coordinate.Row.values()[l.row])); //this is shit I need to remove Location class
        }
        return played;
    }

    public void submit() { // for testing
        System.out.println(board.submit(playedHashToList()));
        addThisTurnToPreviously();
    }

    public void addThisTurnToPreviously() {
        for (Location l: board.getPlayedThisTurn()) {
            board.getPreviouslyPlayed().add(l);
        }
        resetPlayedThisTurn();
    }

    public void resetPlayedThisTurn() {
        board.resetPlayedThisTurn();
    }

    public void setTileAt(Tile tile, int row, int col) {
        Square squareTrial = (Square) dataModel.getValueAt(row, col);
        squareTrial.setTile(tile);
    }

    public Tile removeTileAt(int row, int col) {
        Square square = (Square) dataModel.getValueAt(row, col);
        return square.removeTile();
    }

    public Tile swapTileAt(Tile t, int row, int col) {
        Square square = (Square) dataModel.getValueAt(row, col);
        return square.swapTile(t);
    }

    public Board getBoard() {
        return board;
    }


    public static class Location {
        public final int row;
        public final int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Location)) return false;
            if (obj == this) return true;
            Location l = (Location) obj;
            return (this.row == l.row) && (this.col == l.col);
        }

        @Override
        public int hashCode() {
            return (37 * row) + col;
        }
    }
}
