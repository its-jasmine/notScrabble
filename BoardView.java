import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;
import java.util.HashSet;

public class BoardView extends JTable {


    private final Board board;
    private HashSet<Location> playedThisTurn;

    private HashSet<Location> previouslyPlayed;
    public BoardView() {
        super();
        this.playedThisTurn = new HashSet<>();
        this.previouslyPlayed = new HashSet<>();
        this.board = new Board();
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
        return previouslyPlayed;
    }

    public void addLocationPlayedThisTurn(Location location) {
        playedThisTurn.add(location);
    }

    //TODO
    public void removeLocationPlayedThisTurn(Location location) {
        playedThisTurn.remove(location);
    }

    public HashSet<Location> getPlayedThisTurn() {
        return playedThisTurn;
    }

    public void resetPlayedThisTurn() {
        for (Location l: playedThisTurn) {
            previouslyPlayed.add(l);
        }
        playedThisTurn = new HashSet<>();
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

    public static class Location {
        final int row;
        final int col;

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
