import org.junit.Test;

import javax.swing.table.DefaultTableModel;

import java.io.IOException;

import static org.junit.Assert.*;

public class BoardConfigurationTest {

    @Test
    public void generateDefaultTableModel() {
        String[][] squarePlacement =
                {{"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"},
                        {"PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN"},
                        {"PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN"},
                        {"DOUBLE_LETTER","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER"},
                        {"PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN"},
                        {"PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER", "PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN"},
                        {"PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN"},
                        {"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","START","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"},
                        {"PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN"},
                        {"PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER", "PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN"},
                        {"PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN"},
                        {"DOUBLE_LETTER","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER"},
                        {"PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN"},
                        {"PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN"},
                        {"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"}};

        DefaultTableModel mExpected = new DefaultTableModel(Coordinate.Row.values().length, Coordinate.Column.values().length){
            public Class<Square> getColumnClass(int column)
            {
                return Square.class;
            }
        };
        Square.Type type;
        for (int r = 0; r < squarePlacement.length; r++){
            for (int c = 0; c < squarePlacement[r].length; c++){
                type = Square.Type.valueOf(squarePlacement[r][c]);
                mExpected.setValueAt(new Square(type), r, c);
            }
        }
        try{
            DefaultTableModel mActual = new BoardConfiguration(BoardConfiguration.Type.Basic).generateDefaultTableModel();

            assertEquals(mExpected.getRowCount(), mActual.getRowCount());
            assertEquals(mExpected.getColumnCount(), mActual.getColumnCount());
            for (int i = 0; i < mExpected.getRowCount(); i++){
                for (int j = 0; j < mExpected.getColumnCount(); j++){
                    assertEquals(mExpected.getValueAt(i, j), mActual.getValueAt(i, j));
                }
            }
        } catch (IOException e){
            throw new AssertionError("BoardConfiguration could not generate DefaultTableModel");
        }

    }
}