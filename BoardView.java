import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.HashSet;

public class BoardView extends JTable {


    private HashSet<Location> playedThisTurn;

    private HashSet<Location> previouslyPlayed;
    public BoardView(TableModel dm) {
        super(dm);
        this.playedThisTurn = new HashSet<>();
        this.previouslyPlayed = new HashSet<>();
    }

    @Override
    public SquareTrial getValueAt(int row, int col) {
        return (SquareTrial) getModel().getValueAt(row, col);
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
        SquareTrial squareTrial = (SquareTrial) dataModel.getValueAt(row, col);
        squareTrial.setTile(tile);
    }

    public Tile removeTileAt(int row, int col) {
        SquareTrial squareTrial = (SquareTrial) dataModel.getValueAt(row, col);
        return squareTrial.removeTile();
    }

    public Tile swapTileAt(Tile t, int row, int col) {
        SquareTrial squareTrial = (SquareTrial) dataModel.getValueAt(row, col);
        return squareTrial.swapTile(t);
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
