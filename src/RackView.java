import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

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
        setRowHeight(48);
        setOpaque(true);
        setFillsViewportHeight(true);
        setBackground(new Color(226,187,123));
        setGridColor(Color.BLACK);
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
