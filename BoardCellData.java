public class BoardCellData {
    private BoardView table;

    public BoardCellData(BoardView table) {
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

    public BoardView getTable() {
        return table;
    }

}