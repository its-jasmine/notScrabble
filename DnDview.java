import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DnDview extends JFrame{
    public final int TILESIZE = 50;
    public DnDview() {
        super("DnD");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container contentpane = this.getContentPane();
        contentpane.setLayout(new BorderLayout());

        ImageIcon a = new ImageIcon("Letter-A.jpg");
        a.setImage(a.getImage().getScaledInstance(TILESIZE, TILESIZE, Image.SCALE_FAST));


        DefaultTableModel rdm = new DefaultTableModel()
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        rdm.setDataVector(new Object[][]{{a, a, a, new ImageIcon("blank"), a, a, a}}, new Object[]{"a", "a", "a","a", "a", "a","a"});
        JTable rack = new JTable(rdm);
        rack.setRowHeight(TILESIZE);

        rack.setDragEnabled(true);
        rack.setDropMode(DropMode.ON_OR_INSERT);
        rack.setTransferHandler(new TransferHandler("tile"));

        contentpane.add(rack, BorderLayout.SOUTH);





        DefaultTableModel dm = new DefaultTableModel()
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        dm.setDataVector(new Object[][]{{a, a, a}, {a, a, a},{a, a, a}}, new Object[]{"a", "a", "a"});
        JTable board = new JTable(dm);

        // setting board size
        board.setRowHeight(TILESIZE);
        TableColumnModel cm = board.getColumnModel();
        for (int i = 0; i < 3; i++) {
            cm.getColumn(i).setPreferredWidth(TILESIZE);
        }




        contentpane.add(board, BorderLayout.CENTER);
        board.setPreferredScrollableViewportSize(board.getPreferredSize());


        setLocationByPlatform( true );




        this.setSize(800,800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        DnDview d = new DnDview();
    }

    class TileRenderer extends DefaultTableCellRenderer {
        public TileRenderer() {
            super();
            setOpaque(true);
        }

        public void setValue(ImageIcon value) {
            setIcon(value);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                //JOptionPane.showMessageDialog(button, label + ": Ouch!");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
