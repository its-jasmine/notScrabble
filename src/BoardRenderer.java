import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;

public class BoardRenderer  extends DefaultTableCellRenderer {
    private static final HashMap<Square.Type, Color> typeToColor = new HashMap<Square.Type, Color>(){{
        put(Square.Type.PLAIN, new Color(216,213,194));
        put(Square.Type.START, new Color(142, 177, 37));
        put(Square.Type.DOUBLE_LETTER, new Color(196,230,245));
        put(Square.Type.TRIPLE_LETTER,new Color(43,160,220));
        put(Square.Type.DOUBLE_WORD, new Color(255,168,178));
        put(Square.Type.TRIPLE_WORD, new Color(244,37,36));
    }};
    private String getSquareTypeRenderString(Square.Type t){
        if (t == Square.Type.PLAIN) {
            return "";
        }else if (t == Square.Type.START){
            return t.name();
        } else {
            String[] splitName = t.name().split("_");
            return "<html><p style='text-align:center'>" + splitName[0] + "<br>" + splitName[1] + "</p></html>";
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Square.Type type = ((Square)value).getType();
        ((JLabel)c).setText(getSquareTypeRenderString(type));
        c.setBackground(typeToColor.get(type));
        ((JLabel)c).setHorizontalAlignment(JLabel.CENTER);
        Tile t = ((Square) value).getTile();
        if (t == null){
            ((JLabel) c).setIcon(null);
            //((JLabel) c).setText(null);
            return c;
        }
        else {
            ImageIcon image = new ImageIcon("images/"+t.getName()+"_tile.jpg");
            ((JLabel) c).setIcon(image);
            ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
            ((JLabel) c).setText(null);
            if (t instanceof BlankTile) {
                ((JLabel) c).setToolTipText(((BlankTile) t).getLetter());
                UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 30));
            } else {
                ((JLabel) c).setToolTipText(null);
            }
        }
        return c;
    }
}
