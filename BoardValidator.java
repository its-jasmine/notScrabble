import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for methods that validate the state of the board.
 * @author Victoria Malouf
 * @author Rebecca Elliott
 */
public class BoardValidator {

    /** The allowable word alignment directions */
    public enum Direction {HORIZONTAL, VERTICAL, UNKNOWN}

    private Direction direction;
    private final Board board;

    public BoardValidator(Board board) {
        this.board = board;
        direction = Direction.UNKNOWN;
    }

    /**
     * Checks if the tiles placed this turn are straight, leave no gaps, and touch a word that was already played.
     * Sets the direction field for this turn if tiles are straight and sorts tilesPlacedCoordinates by that direction.
     * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
     * @return the direction the tiles were played in, direction is UNKNOWN if alignment is invalid
     */
    public Direction isValidTileAlignment(List<Coordinate> tilesPlacedCoordinates) {
        restDirection();
        // Determine direction
        direction = getDirection(tilesPlacedCoordinates);
        if (direction == Direction.UNKNOWN) return null;

        // Sort tiles
        if (direction == Direction.HORIZONTAL) Coordinate.sortByColumn(tilesPlacedCoordinates);
        else Coordinate.sortByRow(tilesPlacedCoordinates);

        // Check if there are any gaps between tiles placed
        // We have confirmed that the tiles placed are straight, therefore the sorted tiles can be horizontal OR vertical
        if ((tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1).getColumnIndex() - tilesPlacedCoordinates.get(0).getColumnIndex() + 1))
                || (tilesPlacedCoordinates.size() == (tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1).getRowIndex() - tilesPlacedCoordinates.get(0).getRowIndex() + 1))) {
            // verify word attachment
            if (verifyWordAttachment(tilesPlacedCoordinates)) return direction;
        }

        // If there are gaps between tiles, check if each square between the first and last tile played NOT empty
        else if (verifyNoGaps(tilesPlacedCoordinates)) {
            return direction;
        }

        // Check if this is the first word being played
        if (isOnStart(tilesPlacedCoordinates)) return direction;

        // At this point, the Coordinate placements are invalid
        return Direction.UNKNOWN;
    }

    /**
     * Determine if one of the coordinates attempting to be placed is the start square coordinate.
     * @param tileCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if one of the tilesPlacedCoordinates land on the start square, false otherwise
     */
    private boolean isOnStart(List<Coordinate> tileCoordinates){
        for (Coordinate c: tileCoordinates){
            // Start square is at Coordinate(7,7)
            if ((c.getRowIndex() == 7) && (c.getColumnIndex() == 7)) return true; // for milestone2 "if (getSquare(c).getType() == Square.Type.START)"
        }
        return false;
    }

    /**
     * Determine the direction for this turn if tiles the player is attempting to place are straight.
     * @param tilesPlacedCoordinates the coordinates of the tiles the player is attempting to place this turn
     * @return direction of the tiles (HORIZONTAL or VERTICAL), UNKNOWN otherwise
     */
    private Direction getDirection(List<Coordinate> tilesPlacedCoordinates){
        // Get the sorted rows
        Set<Integer> rowSet = new HashSet<>();
        for (Coordinate c : tilesPlacedCoordinates) rowSet.add(c.getRowIndex());
        if (rowSet.size()==1) return Direction.HORIZONTAL;
        else {
            // Get the sorted columns
            Set<Integer> columnSet = new HashSet<>();
            for (Coordinate c : tilesPlacedCoordinates) columnSet.add(c.getColumnIndex());
            if (columnSet.size()==1) return Direction.VERTICAL;
            else return Direction.UNKNOWN;
        }
    }
    /**
     * Determine if the tiles the player is attempting to place are connected to an existing tile.
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if the sorted tiles are attached to another tile, false otherwise
     */
    private boolean verifyWordAttachment(List<Coordinate> tilesPlacedCoordinates){
        Coordinate firstTileCoordinate = tilesPlacedCoordinates.get(0);
        Coordinate lastTileCoordinate = tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1);
        if (direction == Direction.HORIZONTAL) {
            // Is there a tile to the left of the first tile played?
            Coordinate toLeft = firstTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
            if (toLeft != null && !board.isSquareEmpty(toLeft)) {return true;}
            // For each tile played is there a letter above or below?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate above = c.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
                if (above != null && !board.isSquareEmpty(above)) {return true;}
                Coordinate below = c.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                if (below != null && !board.isSquareEmpty(below)) {return true;}
            }
            // Is there a tile to the right of the last tile played?
            Coordinate toRight = lastTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
            return toRight != null && !board.isSquareEmpty(toRight);
        }
        else {
            // Is there a tile above the first tile played?
            Coordinate above = firstTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
            if (above != null && !board.isSquareEmpty(above)) {return true;}
            // For each tile played is there a letter right or left?
            for (Coordinate c: tilesPlacedCoordinates){
                Coordinate left = c.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
                if (left != null && !board.isSquareEmpty(left)) {return true;}
                Coordinate right = c.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
                if (right != null && !board.isSquareEmpty(right)) {return true;}
            }
            // Is there a tile to below the last tile played?
            Coordinate below = lastTileCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
            return below != null && !board.isSquareEmpty(below);
        }
    }

    /**
     * Determine if each square between the first and last tile played is NOT empty.
     * @param tilesPlacedCoordinates the sorted coordinates of the tiles the player is
     * attempting to place this turn, which are confirmed to be in a straight line
     * @return true if the sorted tiles placement do no have gaps, false otherwise
     */
    private boolean verifyNoGaps(List<Coordinate> tilesPlacedCoordinates){
        Coordinate coordinate = tilesPlacedCoordinates.get(0); // first coordinate at this point
        Coordinate lastCoordinate = tilesPlacedCoordinates.get(tilesPlacedCoordinates.size() - 1);
        if (direction == Direction.HORIZONTAL){
            for (int i = coordinate.getColumnIndex(); i < lastCoordinate.getColumnIndex(); i++){ // first and last coordinates are not checked because we know they are not empty
                coordinate = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT); // we are only checking square that we know are on the board so null check is not required
                if (board.isSquareEmpty(coordinate)) return false;
            }
        }
        else {
            for (int i = coordinate.getRowIndex(); i < lastCoordinate.getRowIndex(); i++){
                coordinate = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                if (board.isSquareEmpty(coordinate)) return false;
            }
        }
        return true;
    }

    /**
     * Reset Direction to UNKNOWN.
     */
    private void resetDirection() {
        direction = Direction.UNKNOWN;
    }
}
