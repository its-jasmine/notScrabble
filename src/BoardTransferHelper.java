import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

public class BoardTransferHelper extends TransferHandler {

    private final Stack<Move> moves;

    public BoardTransferHelper(Stack<Move> moves) {
        this.moves = moves;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        // Create the transferable
        BoardView table = (BoardView) source;
        return new BoardDataTransferable(new BoardCellData(table));
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        JTable sourceTable = (JTable) source;
        sourceTable.updateUI();
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
        if (dropColumn == -1 || dropRow == -1) return false;
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
            Coordinate targetLocation = new Coordinate(Coordinate.Column.values()[dropColumn], Coordinate.Row.values()[dropRow]);


            if(sourceIsBoard) {
                BoardView sourceBoard = (BoardView) source;
                HashSet<Coordinate> disabled = sourceBoard.getPreviouslyPlayed();
                if (disabled.contains(sourceLocation)) return false; // tried to move a previously played tile
                Tile sourceTile = sourceBoard.getValueAt(draggedFromRow, draggedFromCol).getTile();
                if (sourceTile == null) return false; // can't drag from empty squares
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
                Coordinate targetLocation = new Coordinate(Coordinate.Column.values()[dropCol], Coordinate.Row.values()[dropRow]);
                Coordinate sourceLocation = new Coordinate(Coordinate.Column.values()[draggedFromCol], Coordinate.Row.values()[draggedFromRow]);

                Tile exportValue = target.removeTileAt(dropRow, dropCol);
                Tile importValue;


                if(sourceIsBoard) {
                    importValue = boardCellData.getValue().removeTile();
                    if (importValue instanceof BlankTile){
                        System.out.println("BOARD TO BOARD");
                        String letter = JOptionPane.showInputDialog("Provide letter the blank tile will represent:" );
                        ((BlankTile) importValue).setLetter(LetterTile.valueOf(letter.toUpperCase()));
                    }

                } else {
                    importValue = rackCellData.getValue();
                    if (importValue instanceof BlankTile){
                        System.out.println("RACK TO BOARD");
                        String letter = JOptionPane.showInputDialog("Provide letter the blank tile will represent:" );
                        ((BlankTile) importValue).setLetter(LetterTile.valueOf(letter.toUpperCase()));
                    }
                }

                // swap the values
                target.setTileAt(importValue, dropRow, dropCol);
                target.addCoordinatePlayedThisTurn(targetLocation);
                if (sourceIsBoard) {
                    BoardView boardView = (BoardView) source;
                    boardView.setTileAt(exportValue, draggedFromRow, draggedFromCol);
                    if (exportValue == null) {
                        target.removeCoordinatePlayedThisTurn(sourceLocation);// moved a tile on the board to an empty space on the board
                    }
                } else {
                    source.setValueAt(exportValue, draggedFromRow, draggedFromCol); //dropped value is set
                }

                Move move = new Move(targetLocation, sourceLocation, importValue, exportValue, sourceIsBoard, true);
                moves.push(move);


                imported = true;

            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }
}
