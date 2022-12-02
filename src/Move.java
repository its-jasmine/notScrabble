public class Move {
    public Coordinate playedTo;
    public Coordinate playedFrom;
    public Tile playedToTile;
    public Tile playedFromTile;
    public boolean cameFromWasBoard;


    public Move(Coordinate playedTo, Coordinate playedFrom, Tile playedToTile, Tile playedFromTile, Boolean cameFromWasBoard) {
        this.playedTo = playedTo;
        this.playedFrom = playedFrom;
        this.playedToTile = (playedToTile instanceof BlankTile) ? new BlankTile(((BlankTile) playedToTile).getLetter()) : playedToTile;
        this.playedFromTile = (playedFromTile instanceof BlankTile) ? new BlankTile(((BlankTile) playedFromTile).getLetter()) : playedFromTile;
        this.cameFromWasBoard = cameFromWasBoard;
    }
}
