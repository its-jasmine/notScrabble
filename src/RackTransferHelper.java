import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

/**
 * This class dictates where things can be dragged to on the rack and what to do when something is dropped on the rack.
 * @author Rebecca Elliott
 */
public class RackTransferHelper extends TransferHandler {
    private final Stack<Move> moves; // pointer to the stack that is used for undoing and redoing moves

    public RackTransferHelper(Stack<Move> moves) {
        this.moves = moves;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        return new RackDataTransferable(new RackCellData((JTable) source));
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {

    }

    /**
     * Checks if the cell that the mouse is over, while dragging something, can be imported to
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return true if it is possible to import to this cell, false otherwise
     */
    @Override
    public boolean canImport(TransferSupport support) {
        // Reject the import by default
        boolean canImport = false;
        try {
            // Get the Transferable
            Transferable t = support.getTransferable();

            BoardCellData boardCellData = null;
            RackCellData rackCellData = null;
            JTable source;
            try {
                boardCellData = (BoardCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                source = boardCellData.getTable();
            } catch (Exception e) {
                rackCellData = (RackCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                source = rackCellData.getTable();
            }

            if (boardCellData == null && rackCellData == null) {
                System.out.println("something went wrong with source data\n");
                return false;
            }

            boolean sourceIsBoard = source instanceof BoardView;
            int draggedFromRow = source.getSelectedRow();
            int draggedFromCol = source.getSelectedColumn();
            Coordinate sourceLocation = new Coordinate(Coordinate.Column.values()[draggedFromCol], Coordinate.Row.values()[draggedFromRow]);


            if(sourceIsBoard) {
                BoardView sourceBoard = (BoardView) source;
                HashSet<Coordinate> disabled = sourceBoard.getPreviouslyPlayed();
                if (disabled.contains(sourceLocation)) return false; // tried to move a previously played tile
                Tile sourceTile = sourceBoard.getValueAt(draggedFromRow, draggedFromCol).getTile();
                if (sourceTile == null) return false; // can't drag from empty squares
            }else {
                Tile sourceTile = (Tile) source.getValueAt(draggedFromRow, draggedFromCol);
                if (sourceTile == null) return false; // can't drag null tile from rack
            }

            canImport = true;

        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
        return canImport;
    }

    /**
     * Swaps the data in the cell with the data in the cell that was dragged from
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return true if the data was successfully imported, false otherwise
     */
    @Override
    public boolean importData(TransferSupport support) {
        boolean imported = false;

        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable target = (JTable) comp;
            // get import location
            DropLocation dropLocation = support.getDropLocation();
            Point dropPoint = dropLocation.getDropPoint();
            int dropCol = target.columnAtPoint(dropPoint);
            int dropRow = target.rowAtPoint(dropPoint);
            try {
                // Get the Transferable
                Transferable t = support.getTransferable();

                BoardCellData boardCellData = null;
                RackCellData rackCellData = null;
                JTable source;
                try {
                    boardCellData = (BoardCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = boardCellData.getTable();
                } catch (Exception e) {
                    rackCellData = (RackCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = rackCellData.getTable();
                }

                if (boardCellData == null && rackCellData == null) {
                    System.out.println("something went wrong with source data\n");
                    return false;
                }

                boolean sourceIsBoard = source instanceof BoardView;
                int draggedFromRow = source.getSelectedRow();
                int draggedFromCol = source.getSelectedColumn();
                Coordinate sourceLocation = new Coordinate(Coordinate.Column.values()[draggedFromCol], Coordinate.Row.values()[draggedFromRow]);

                Tile exportValue = (Tile) target.getValueAt(dropRow, dropCol); // export is from rack
                Tile importValue; // could be from rack or board
                if(sourceIsBoard) {
                    importValue = boardCellData.getValue().removeTile();
                    System.out.println("BOARD to RACK");
                    if (importValue instanceof BlankTile){
                        ((BlankTile) importValue).resetLetter();
                    }
                } else {
                    importValue = rackCellData.getValue();
                }
                target.setValueAt(importValue, dropRow, dropCol); //dropped value is set

                // set the new value at the source where we got the import data from
                if (sourceIsBoard) {
                    BoardView boardView = (BoardView) source;
                    boardView.setTileAt(exportValue, draggedFromRow, draggedFromCol);

                    boardView.addCoordinatePlayedThisTurn(sourceLocation);
                    if (exportValue == null) boardView.removeCoordinatePlayedThisTurn(sourceLocation);// took a tile off board and returned it to rack

                } else {
                    source.setValueAt(exportValue, draggedFromRow, draggedFromCol);
                }

                // stores the move that just occurred
                Coordinate targetCoordinate = new Coordinate(Coordinate.Column.values()[dropCol], Coordinate.Row.values()[dropRow]);
                Move move = new Move(targetCoordinate, sourceLocation, importValue, exportValue, sourceIsBoard, false);
                moves.push(move);

                imported = true;

            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }
}
