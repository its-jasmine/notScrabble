import javax.swing.*;

import java.awt.*;

public class DnDTrial {

    public static void main(String[] args) {
        new DnDTrial();
    }

    public DnDTrial() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                BoardView board =new BoardView(new Board()); //createBoard();
                RackView rack = new RackView(new Rack(new Bag()));//createRack();

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Container contentpane = frame.getContentPane();
                contentpane.setLayout(new BorderLayout());
                contentpane.add(board, BorderLayout.CENTER);
                contentpane.add(rack, BorderLayout.SOUTH);

                JButton submit = new JButton("Submit");
                submit.addActionListener(e -> {board.submit();});
                contentpane.add(submit, BorderLayout.NORTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}