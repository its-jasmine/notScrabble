import java.util.Stack;

public class UndoRedo {
    private final Stack<Move> moves;
    private final Stack<Move> redoMoves;

    private final Board board;

    public UndoRedo(Board board, Stack<Move> moves) {
        this.board = board;
        this.moves = moves;
        redoMoves = new Stack<Move>();
    }

    public void undo(Rack rack) {
        if (moves.size() != 0) {
            Move lastMove = moves.pop();
            moveTiles(lastMove, rack);
            redoMoves.push(lastMove);
        }
    }

    public void redo(Rack rack) {
        if (redoMoves.size() != 0) {
            Move redoMove = redoMoves.pop();
            moveTiles(redoMove, rack);
            moves.push(redoMove);
        }
    }

    private void moveTiles(Move move, Rack rack) {
        //revert To
        if (move.playedTo != null) {
            board.getSquare(move.playedTo).setTile(move.playedFromTile);
            board.getPlayedThisTurn().remove(move.playedTo);
        }
        else  {
            if (!move.cameFromWasBoard) rack.removeTileFromRack(move.playedToTile);
            rack.putTileOnRack(move.playedFromTile);
        }

        //revert From
        if (move.cameFromWasBoard) {
            board.getSquare(move.playedFrom).setTile(move.playedToTile);
            board.getPlayedThisTurn().add(move.playedFrom);
        }
        else {
            if (move.playedTo == null) rack.removeTileFromRack(move.playedFromTile);
            rack.putTileOnRack(move.playedToTile);
        }
    }


}
