import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RackRenderer  extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value == null){
            ((JLabel) c).setIcon(null);
            ((JLabel) c).setText(null);
            return c;
        }
        else {
            ImageIcon image = new ImageIcon("images/"+((Tile) value).getName()+"_tile.jpg");
            ((JLabel) c).setIcon(image);
            ((JLabel) c).setText(null);
            ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
        }
        return c;
    }
}
