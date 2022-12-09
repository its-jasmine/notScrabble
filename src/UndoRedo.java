import java.io.Serializable;
import java.util.Stack;

/**
 * This class stores moves and supports undoing and redoing moves.
 * @author Rebecca Elliott
 */
public class UndoRedo implements Serializable {
    private final Stack<Move> moves;
    private final Stack<Move> redoMoves;
    private final Board board;

    public UndoRedo(Board board, Stack<Move> moves) {
        this.board = board;
        this.moves = moves;
        redoMoves = new Stack<>();
    }

    /**
     * If there is a move stored, it is reverted
     * @param rack the rack of the player whose turn it is
     */
    public void undo(Rack rack) {
        if (moves.size() != 0) {
            Move lastMove = moves.pop();
            moveTiles(lastMove, rack);
            lastMove.invertMove();
            redoMoves.push(lastMove);
        }
    }

    /**
     * If there is an undone move that is stored, it is reverted
     * @param rack the rack of the player whose turn it is
     */
    public void redo(Rack rack) {
        if (redoMoves.size() != 0) {
            Move redoMove = redoMoves.pop();
            moveTiles(redoMove, rack);
            redoMove.invertMove();
            moves.push(redoMove);
        }
    }

    /**
     * Places the give move
     * @param move to place
     * @param rack the rack of the player whose turn it is
     */
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

    /**
     * Clears the stacks storing moves
     */
    public void clearMoves() {
        moves.clear();
        redoMoves.clear();
    }


}
