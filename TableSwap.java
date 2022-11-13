import javax.swing.*;

import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TableSwap {

    public static void main(String[] args) {
        new TableSwap();
    }

    public TableSwap() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                BoardJTable board = createBoard();
                JTable rack = createRack();

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Container contentpane = frame.getContentPane();
                contentpane.setLayout(new BorderLayout());
                contentpane.add(board, BorderLayout.CENTER);
                contentpane.add(rack, BorderLayout.SOUTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    protected BoardJTable createBoard() {


        DefaultTableModel model = new DefaultTableModel(0, 3){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        SquareTrial e = new SquareTrial(SquareTrial.Type.PLAIN);
        for (int index = 0; index < 3; index++) {
            ArrayList test = new ArrayList();
            test.add(e);
            test.add(e);
            test.add(e);
            model.addRow(test.toArray());
//            SquareTrial a = new SquareTrial(SquareTrial.Type.PLAIN);
//            a.setTile(Tile.A);
//            SquareTrial b = new SquareTrial(SquareTrial.Type.PLAIN);
//            b.setTile(Tile.B);
//
//            model.addRow(new Object[]{a, b, e});
        }


        BoardJTable table = new BoardJTable(model);
        table.setBorder(new BevelBorder(BevelBorder.RAISED));
        table.setRowHeight(50);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.ON);
        table.setTransferHandler(new BoardTransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

        return table;
    }

    protected JTable createRack() {


        Rack rack = new Rack(new Bag());
        rack.getModel().setValueAt(null, 0, 0);
        /*
        DefaultTableModel model = new DefaultTableModel(0, 7){
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        ArrayList<Tile> test = (ArrayList<Tile>) rack.getTilesList();

        ArrayList r = new ArrayList();

        for (int i = 0; i < test.size(); i++) {
            SquareTrial s = new SquareTrial();
            s.setTile(test.get(i));
            r.add(s);
        }

        model.addRow(test.toArray());
        */



        JTable table = new JTable(rack.getModel());
        table.setBorder(new BevelBorder(BevelBorder.RAISED));
        table.setRowHeight(50);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.ON);
        table.setTransferHandler(new RackTransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

        return table;
    }

}