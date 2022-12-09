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
        setModel(rack.getModel());
        setDefaultRenderer(Tile.class,new RackRenderer());
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setRowHeight(48);
        setOpaque(true);
        setFillsViewportHeight(true);
        setBackground(new Color(137,66,0));
        setShowGrid(false);
        setDragEnabled(true);
        setDropMode(DropMode.ON);
        setTransferHandler(new RackTransferHelper(rack.moves));
        setRowSelectionAllowed(false);
        setCellSelectionEnabled(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    /**
     * gets a rack
     * @return a rack
     */
    public Rack getRack() {
        return rack;
    }


}
