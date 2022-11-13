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

        // Can only import into another JTable
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable table = (JTable) comp;
            // get location where the drop might occur
            DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            // Get the column at the drop point
            int dragColumn = table.columnAtPoint(dp);
            try {
                // Get the Transferable, we need to check
                // the constraints
                Transferable t = support.getTransferable();
                BoardCellData cd = null;
                RackCellData rackCellData = null;
                JTable source;
                try {
                    cd = (BoardCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = cd.getTable();
                } catch (Exception e) {
                    rackCellData = (RackCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = rackCellData.getTable();
                }

                if (cd == null && rackCellData == null) {
                    System.out.println("something went wrong with source data\n");
                    return false;
                }
                canImport = true;
//                // Make sure we're not dropping onto ourselves...
//                if (cd.getTable() != table) {
//                    // Do the columns match...?
//                    if (dragColumn == cd.getColumn()) {
//                        canImport = true;
//                    }
//                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return canImport;
    }

    @Override
    public boolean importData(TransferSupport support) {
        // Import failed for some reason...
        boolean imported = false;

        // Only import into JTables...
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable target = (JTable) comp;
            // Need to know where we are importing to...
            DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            int dropCol = target.columnAtPoint(dp);
            int dropRow = target.rowAtPoint(dp);
            try {

                // Get the Transferable at the heart of it all
                Transferable t = support.getTransferable();

                BoardCellData cd = null;
                RackCellData rackCellData = null;
                JTable source;
                try {
                    cd = (BoardCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = cd.getTable();
                } catch (Exception e) {
                    rackCellData = (RackCellData) t.getTransferData(BoardDataTransferable.CELL_DATA_FLAVOR);
                    source = rackCellData.getTable();
                }

                if (cd == null && rackCellData == null) {
                    System.out.println("something went wrong with source data\n");
                    return false;
                }

                boolean sourceIsBoard = source instanceof BoardJTable;
                int row = source.getSelectedRow();
                int col = source.getSelectedColumn();
                BoardJTable.Location sourceLocation = new BoardJTable.Location(row, col);
                Tile importValue;


                if(sourceIsBoard) {
                    HashSet disabled = ((BoardJTable)source).getPreviouslyPlayed();
                    if (disabled.contains(sourceLocation)) return false; // tried to move a previously played tile
                    importValue = cd.getValue().removeTile();

                } else {
                    importValue = rackCellData.getValue();
                }


                Tile exportValue = (Tile) target.getValueAt(dropRow, dropCol);


                // This is where we swap the values...
                // Set the target/dropped tables value
                //((SquareTrial) target.getValueAt(dropRow, dropCol)).setTile(importValue);
                target.setValueAt(importValue, dropRow, dropCol);
                //target.setTileAt(importValue, dropRow, dropCol);

                // Set the source/dragged tables values

                //((SquareTrial) source.getValueAt(row, col)).setTile(exportValue);
                if (sourceIsBoard) {
                    SquareTrial es = new SquareTrial();
                    es.setTile(exportValue);
                    source.setValueAt(es, row, col);

                    ((BoardJTable)source).addLocation(sourceLocation);
                    //source.setTileAt(exportValue, row, col);
                } else source.setValueAt(exportValue,row, col);


                imported = true;

                /*
                if (cd.getTable() != target) {
                    // Make sure the columns match
                    if (dropCol == cd.getColumn()) {
                        // Get the data from the "dropped" table
                        ImageIcon exportValue = (ImageIcon) target.getValueAt(dropRow, dropCol);
                        // Get the data from the "dragged" table
                        ImageIcon importValue = cd.getValue();
                        // This is where we swap the values...
                        // Set the target/dropped tables value
                        target.setValueAt(importValue, dropRow, dropCol);

                        // Set the source/dragged tables values
                        JTable source = cd.getTable();
                        int row = source.getSelectedRow();
                        int col = source.getSelectedColumn();
                        source.setValueAt(exportValue, row, col);

                        imported = true;
                    }
                }*/
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }
}
