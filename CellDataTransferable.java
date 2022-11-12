import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.Transferable;
import java.io.IOException;

public class CellDataTransferable implements Transferable{
    public static final DataFlavor CELL_DATA_FLAVOR = new DataFlavor(CellData.class, "tile");
    private CellData cellData;

    public CellDataTransferable(CellData cellData) {
        this.cellData = cellData;
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
        return cellData;
    }

}

