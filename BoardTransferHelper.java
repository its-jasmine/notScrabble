import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;

public class BoardTransferHelper extends TransferHandler {

    public BoardTransferHelper() {
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        // Create the transferable
        // Because I'm hacking a little, I've included the source table...
        BoardView table = (BoardView) source;
        return new BoardDataTransferable(new BoardCellData(table));
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // Reject the import by default
        boolean canImport = false;

        Component comp = support.getComponent();
        BoardView board = (BoardView) comp;
        // get location where the drop might occur
        DropLocation dropLocation = support.getDropLocation();
        Point dropPoint = dropLocation.getDropPoint();
        int dropRow = board.rowAtPoint(dropPoint);
        int dropColumn = board.columnAtPoint(dropPoint);
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
            BoardView.Location sourceLocation = new BoardView.Location(draggedFromRow, draggedFromCol);
            BoardView.Location targetLocation = new BoardView.Location(dropRow, dropColumn);


            if(sourceIsBoard) {
                BoardView sourceBoard = (BoardView) source;
                HashSet disabled = sourceBoard.getPreviouslyPlayed();
                if (disabled.contains(sourceLocation)) return false; // tried to move a previously played tile
                Tile sourceTile = sourceBoard.getValueAt(draggedFromRow, draggedFromCol).getTile();
                if (sourceTile == null || sourceTile.equals(Tile.EMPTY)) return false; // can't drag from empty squares
            } else {
                Tile sourceTile = (Tile) source.getValueAt(draggedFromRow, draggedFromCol);
                if (sourceTile == null) return false; // can't drag null tile from rack
            }
            if (board.getPreviouslyPlayed().contains(targetLocation)) return false; // tried to play on a previously played tile
            canImport = true;

        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
        return canImport;
    }

    @Override
    public boolean importData(TransferSupport support) {
        boolean imported = false;

        Component comp = support.getComponent();
        if (comp instanceof BoardView) {
            BoardView target = (BoardView) comp; // is always the board
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
                BoardView.Location targetLocation = new BoardView.Location(dropRow, dropCol);
                BoardView.Location sourceLocation = new BoardView.Location(draggedFromRow, draggedFromCol);

                Tile importValue;

                if(sourceIsBoard) {
                    importValue = boardCellData.getValue().removeTile();

                } else {
                    importValue = rackCellData.getValue();
                }

                Tile exportValue = target.getValueAt(dropRow, dropCol).removeTile();

                // swap the values
                SquareTrial ts = new SquareTrial();
                ts.setTile(importValue);
                target.setValueAt(ts, dropRow, dropCol);

                if (sourceIsBoard) {
                    SquareTrial es = new SquareTrial();
                    es.setTile(exportValue);
                    source.setValueAt(es, draggedFromRow, draggedFromCol);
                } else {
                    if (exportValue.equals(Tile.EMPTY)) source.setValueAt(null, draggedFromRow, draggedFromCol); //dropped value is set
                    else source.setValueAt(exportValue, draggedFromRow, draggedFromCol); //dropped value is set
                }

                target.addLocationPlayedThisTurn(targetLocation);
                if (exportValue == null || exportValue.equals(Tile.EMPTY)) target.removeLocationPlayedThisTurn(sourceLocation);// took a tile off board and returned it to rack

                imported = true;

            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }
}
