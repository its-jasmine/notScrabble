import javax.swing.*;

public class RackCellData {
    private JTable table;

    public RackCellData(JTable table) {
        this.table = table;
    }

    public int getColumn() {
        return table.getSelectedColumn();
    }

    public Tile getValue() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        return (Tile) table.getValueAt(row, col);
    }

    public JTable getTable() {
        return table;
    }

}