public class BoardCellData {
    private BoardView table;

    public BoardCellData(BoardView table) {
        this.table = table;
    }

    public int getColumn() {
        return table.getSelectedColumn();
    }

    /**
     * gets a square at the specific position in the table
     * @return the square at a position in the table
     */
    public Square getValue() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        return (Square) table.getValueAt(row, col);
    }

    /**
     * @return the BoardView
     */
    public BoardView getTable() {
        return table;
    }

}