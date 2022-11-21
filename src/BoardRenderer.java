import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoardRenderer  extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (((Square) value).getType() == Square.Type.PLAIN){
            //c.setBackground(new Color(227,207,170)); // a bit too close to tile color.
            c.setBackground(new Color(216,213,194));
        }
        else if (((Square) value).getType() == Square.Type.START){
            c.setBackground(new Color(142, 177, 37));
            ((JLabel)c).setText("START");
            ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        }
        else if (((Square) value).getType() == Square.Type.DOUBLE_LETTER){
            c.setBackground(new Color(196,230,245));
            ((JLabel)c).setText("<html><p style='text-align:center'>DOUBLE<br>LETTER</p></html>");
            ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        }
        else if (((Square) value).getType() == Square.Type.TRIPLE_LETTER){
            c.setBackground(new Color(43,160,220));
            ((JLabel)c).setText("<html><p style='text-align:center'>TRIPLE<br>LETTER</p></html>");
            ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        }
        else if (((Square) value).getType() == Square.Type.DOUBLE_WORD){
            c.setBackground(new Color(255,168,178));
            ((JLabel)c).setText("<html><p style='text-align:center'>DOUBLE<br>WORD</p></html>");
            ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        }
        else if (((Square) value).getType() == Square.Type.TRIPLE_WORD){
            c.setBackground(new Color(244,37,36));
            ((JLabel)c).setText("<html><p style='text-align:center'>TRIPLE<br>WORD</p></html>");
            ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        }
        if (((Square) value).getTile() == null){
            ((JLabel) c).setIcon(null);
            //((JLabel) c).setText(null);
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
