import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Represents the rack in the GUI.
 * @author Rebecca Elliot
 * @version Milestone4
 */

public class RackView extends JTable {
    private final Rack rack;

    public RackView(Rack rack) {
        super();
        this.rack = rack;

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
