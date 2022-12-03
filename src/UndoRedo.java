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
            lastMove.invertMove();
            redoMoves.push(lastMove);
        }
    }

    public void redo(Rack rack) {
        if (redoMoves.size() != 0) {
            Move redoMove = redoMoves.pop();
            moveTiles(redoMove, rack);
            redoMove.invertMove();
            moves.push(redoMove);
        }
    }

    private void moveTiles(Move move, Rack rack) {
        //revert To
        if (move.sentToWasBoard) {
            board.getSquare(move.playedTo).setTile(move.playedFromTile);
            if (move.playedToTile == null) board.getPlayedThisTurn().remove(move.playedTo);
        }
        else  {
            rack.getModel().setValueAt(move.playedFromTile, 0, move.playedTo.getColumnIndex());
        }

        //revert From
        if (move.cameFromWasBoard) {
            board.getSquare(move.playedFrom).setTile(move.playedToTile);
            if (move.playedFromTile != null) board.getPlayedThisTurn().add(move.playedFrom);
        }
        else {
            rack.getModel().setValueAt(move.playedToTile, 0, move.playedFrom.getColumnIndex());
        }
    }

    public void clearMoves() {
        moves.clear();
        redoMoves.clear();
    }


}
