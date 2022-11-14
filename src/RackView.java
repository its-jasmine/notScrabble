import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Represents the rack in the GUI.
 */

public class RackView extends JTable {
    private final Rack rack;

    public RackView(Rack rack) {
        super();
        this.rack = rack;
        setModel(rack.getModel());

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

    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Rack r = new Rack(new Bag());
        frame.add(panel);
        frame.add(new RackView(r));
        frame.setVisible(true);
        frame.setSize(100,10);
    }

}
