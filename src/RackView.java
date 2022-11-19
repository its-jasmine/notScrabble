import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Represents the rack in the GUI.
 */

public class RackView extends JTable {
    private final Rack rack;

    public RackView(Rack rack) {
        super();
        this.rack = rack;
        setModel(rack.getModel());
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) this.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(50);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new RackTransferHelper());
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
    }

    public Rack getRack() {
        return rack;
    }


}
