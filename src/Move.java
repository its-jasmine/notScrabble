public class Move {
    public Coordinate playedTo;
    public Coordinate playedFrom;
    public Tile playedToTile;
    public Tile playedFromTile;

    public boolean playedToWasBoard;

    public boolean isPlayedFromWasBoard;


    public Move(Coordinate playedTo, Coordinate playedFrom, Tile playedToTile, Tile playedFromTile, Boolean isPlayedFromWasBoard) {
        this.playedTo = playedTo;
        this.playedFrom = playedFrom;
        this.playedToTile = playedToTile;
        this.playedFromTile = playedFromTile;
        this.isPlayedFromWasBoard = isPlayedFromWasBoard;
    }
}
