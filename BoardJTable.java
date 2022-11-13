import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;

public class BoardJTable extends JTable {


    private HashSet<Location> playedThisTurn;

    private HashSet<Location> previouslyPlayed;
    public BoardJTable(TableModel dm) {
        super(dm);
        this.previouslyPlayed = new HashSet<>();
    }

    public HashSet<Location> getPreviouslyPlayed() {
        return previouslyPlayed;
    }

    public void addLocation(Location location) {
        previouslyPlayed.add(location);
    }

    public HashSet<Location> getPlayedThisTurn() {
        return playedThisTurn;
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
