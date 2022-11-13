import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RackView extends JPanel {

    private JTable rackJTable;
    private Rack rackModel;

    public RackView(Rack rack){
        super();
        rackJTable = new JTable(rack.getDefaultTableModel());
        this.add(rackJTable);
    }


    public void updateRackView(RackEvent e) {
        for (JButton button : rackGrid){
            if (button.getText() != null){
                continue;
            }
            else{
                button.setText(e.getTile().letter);
            }
        }
    }
}
