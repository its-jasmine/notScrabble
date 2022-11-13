import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;

public class RackTransferHelper extends TransferHandler {

    public RackTransferHelper() {
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
            BoardView.Location sourceLocation = new BoardView.Location(draggedFromRow, draggedFromCol);


            if(sourceIsBoard) {
                BoardView sourceBoard = (BoardView) source;
                HashSet disabled = sourceBoard.getPreviouslyPlayed();
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
                BoardView.Location sourceLocation = new BoardView.Location(draggedFromRow, draggedFromCol);

                Tile exportValue = (Tile) target.getValueAt(dropRow, dropCol); // export is from rack
                Tile importValue; // could be from rack or board
                if(sourceIsBoard) {
                    importValue = boardCellData.getValue().removeTile();
                } else {
                    importValue = rackCellData.getValue();
                }
                target.setValueAt(importValue, dropRow, dropCol); //dropped value is set

                // set the new value at the source where we got the import data from
                if (sourceIsBoard) {
                    BoardView boardView = (BoardView) source;
                    boardView.setTileAt(exportValue, draggedFromRow, draggedFromCol);
//                    Square es = new Square();
//                    es.setTile(exportValue);
//                    boardView.setValueAt(es, draggedFromRow, draggedFromCol);

                    boardView.addLocationPlayedThisTurn(sourceLocation);
                    if (exportValue == null) boardView.removeLocationPlayedThisTurn(sourceLocation);// took a tile off board and returned it to rack

                } else source.setValueAt(exportValue,draggedFromRow, draggedFromCol);

                imported = true;

            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }
}
