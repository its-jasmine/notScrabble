/**
 * This class contains all the information needed to know what happen in a move.
 * @author Rebecca Elliott
 */

public class Move {
    public Coordinate playedTo;
    public Coordinate playedFrom;
    public Tile playedToTile;
    public Tile playedFromTile;
    public boolean cameFromWasBoard;
    public boolean sentToWasBoard;


    public Move(Coordinate playedTo, Coordinate playedFrom, Tile playedToTile, Tile playedFromTile, Boolean cameFromWasBoard, Boolean sentToWasBoard) {
        this.playedTo = playedTo;
        this.playedFrom = playedFrom;
        this.playedToTile = (playedToTile instanceof BlankTile) ? new BlankTile(((BlankTile) playedToTile).getLetter()) : playedToTile;
        this.playedFromTile = (playedFromTile instanceof BlankTile) ? new BlankTile(((BlankTile) playedFromTile).getLetter()) : playedFromTile;
        this.cameFromWasBoard = cameFromWasBoard;
        this.sentToWasBoard = sentToWasBoard;
    }

    /**
     * Swaps where a move came form with where it was played to
     */
    public void invertMove() {
        Coordinate temp = playedTo;
        playedTo = playedFrom;
        playedFrom = temp;
        cameFromWasBoard = !cameFromWasBoard;
        sentToWasBoard = !sentToWasBoard;
    }
}
