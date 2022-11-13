import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.Transferable;
import java.io.IOException;

public class BoardDataTransferable implements Transferable{
    public static final DataFlavor CELL_DATA_FLAVOR = new DataFlavor(BoardCellData.class, "Board Tiles");
    private BoardCellData boardCellData;

    public BoardDataTransferable(BoardCellData boardCellData) {
        this.boardCellData = boardCellData;
    }


    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{CELL_DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return getTransferDataFlavors().equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return boardCellData;
    }

}

