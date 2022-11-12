import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableIcon extends JPanel
{
    public TableIcon()
    {
        ImageIcon a = new ImageIcon("Letter-A.jpg");
        a.setImage(a.getImage().getScaledInstance(100, 100, Image.SCALE_FAST));

        String[] columnNames = {"Picture", "Description"};
        Object[][] data =
                {
                        {a, a},
                        {a, a},
                        {a, a},
                };

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable( model );
        table.setRowHeight(100);
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < 2; i++) {
            cm.getColumn(i).setPreferredWidth(100);
        }
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane( table );
        add( scrollPane );
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("Table Icon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TableIcon());
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

}