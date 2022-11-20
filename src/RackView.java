import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Arrays;

/**
 * Represents the rack in the GUI.
 */

public class RackView extends JTable {
    private final Rack rack;
    private class RackController implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JTable table = RackView.this;
            int[] rows = table.getSelectedRows();
            System.out.println(Arrays.toString(rows));
            int[] cols = table.getSelectedColumns();
            System.out.println(Arrays.toString(cols));
        }
    }

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
        getSelectionModel().addListSelectionListener(new RackController());
    }

    public Rack getRack() {
        return rack;
    }


}
