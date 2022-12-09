import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class UndoRedoTest {
    private Stack<Move> undo = new Stack<>();
    private Board board = new Board(undo);
    private Rack rack = new Rack(new Bag(), undo);
    private UndoRedo undoRedo = new UndoRedo(board, undo);
    private Move move1 = new Move(new Coordinate(Coordinate.Column.A, Coordinate.Row.EIGHT), new Coordinate(Coordinate.Column.B, Coordinate.Row.NINE), LetterTile.I, LetterTile.A, true, false);
    private Move move2 = new Move(new Coordinate(Coordinate.Column.C, Coordinate.Row.TEN), new Coordinate(Coordinate.Column.D, Coordinate.Row.ELEVEN), LetterTile.E, LetterTile.B, true, true);
    private Move move3 = new Move(new Coordinate(Coordinate.Column.E, Coordinate.Row.TWELVE), new Coordinate(Coordinate.Column.F, Coordinate.Row.THIRT), LetterTile.A, LetterTile.C, false, false);
    private Move move4 = new Move(new Coordinate(Coordinate.Column.G, Coordinate.Row.FOURT), new Coordinate(Coordinate.Column.H, Coordinate.Row.FIFT), LetterTile.O, LetterTile.D, false, true);

    @Before
    public void setUp() {
        undoRedo.clearMoves();
    }

    @Test
    public void undoRedo() {
        undo.push(move1);
        undoRedo.undo(rack);
        undoRedo.redo(rack);

        assertEquals(move1, undo.pop());
    }

    @Test
    public void redo() {
    }
}