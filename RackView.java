import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Represents the rack in the GUI.
 */

public class RackView extends JTable {

    public RackView() {
        super();
        Rack rack = new Rack(new Bag());
        setModel(rack.getModel());

        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(50);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new RackTransferHelper());
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
    }
}
