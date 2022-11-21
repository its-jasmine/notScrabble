import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoardRenderer  extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //if (((Square) value).getType() == Square.Type.START)
        if (((Square) value).getTile() == null){
            ((JLabel) c).setIcon(null);
            ((JLabel) c).setText(null);
            return c;
        }
        else {
            ImageIcon image = new ImageIcon("images/"+((Square) value).getTile().getName()+"_tile.jpg");
            ((JLabel) c).setIcon(image);
            ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
            ((JLabel) c).setText(null);
        }
        return c;
    }
}
