import java.util.Stack;

public class MovesStack extends Stack<Move> {

    private Board board;

    private Rack rack;

    public MovesStack(Board board, Rack rack) {
        super();
        this.board = board;
        this.rack = rack;
    }


    public Move undo() {
        Move lastMove = this.pop();
        // returns tile to where it was played from
        if (lastMove.isPlayedFromWasBoard) {
            board.getSquare(lastMove.playedFrom).setTile(lastMove.playedToTile);
            board.getPlayedThisTurn().add(lastMove.playedFrom);
        }
        else {
            rack.removeTileFromRack(lastMove.playedFromTile);
            rack.putTileOnRack(lastMove.playedToTile);
        }


        if (lastMove.playedToWasBoard) {
            board.getSquare(lastMove.playedTo).setTile(lastMove.playedFromTile);
            board.getPlayedThisTurn().remove(lastMove.playedTo);
        }
        else  {
            rack.removeTileFromRack(lastMove.playedToTile);
            rack.putTileOnRack(lastMove.playedFromTile);
        }
        return lastMove;
    }

    public Move redo() {
        return this.undo();
    }
}
