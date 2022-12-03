import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Represents the rack in the GUI.
 */

public class RackView extends JTable {
    private final Rack rack;

    public RackView(Rack rack) {
        super();
        this.rack = rack;

        setDefaultRenderer(Tile.class,new RackRenderer());
        //DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) this.getDefaultRenderer(Object.class);
        //renderer.setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(48);
        setOpaque(true);
        setFillsViewportHeight(true);
        setBackground(new Color(137,66,0));
        setShowGrid(false);
        //setGridColor(Color.BLACK);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new RackTransferHelper());
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setModel();
    }

    /**
     * gets a rack
     * @return a rack
     */
    public Rack getRack() {
        return rack;
    }
    public void setModel(){
        setModel(rack.getModel());
    }

}
