import javax.swing.*;

public class CellData {
    private BoardJTable table;

    public CellData(BoardJTable table) {
        this.table = table;
    }

    public int getColumn() {
        return table.getSelectedColumn();
    }

    public SquareTrial getValue() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        return (SquareTrial) table.getValueAt(row, col);
    }

    public BoardJTable getTable() {
        return table;
    }

}