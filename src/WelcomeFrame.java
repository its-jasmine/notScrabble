import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WelcomeFrame extends JFrame {
    private static final String  INSTRUCTIONS_CMD = "instructions";
    private static final String  NEW_GAME_CMD = "new game";
    private static final String PLAYER_VS_AI = "1 vs ai";
    private class WelcomeController implements ActionListener {
        private WelcomeFrame view;

        private WelcomeController(WelcomeFrame view){
            this.view = view;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            if (source.getActionCommand().equals(INSTRUCTIONS_CMD)){
                JOptionPane.showMessageDialog(view, "Instructions blah blah blah");
            }else {
                GameConfiguration c;
                int numPlayers;
                int numAI;

                if (source.getActionCommand().equals(NEW_GAME_CMD)) {
                    numPlayers = Integer.valueOf(JOptionPane.showInputDialog("How many players would you like?"));
                    numAI = 0;
                } else { // source.getActionCommand().equals(PLAYER_VS_AI)
                    numPlayers = 1;
                    numAI = 0;
                }

                BoardConfiguration b = requestBoardConfiguration();
                c = new GameConfiguration(b, numPlayers, numAI);
                view.dispose();
                new GameView(c);
            }
        }

        private BoardConfiguration requestBoardConfiguration() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel select = new JLabel("Please select a board configuration option:");
            panel.add(select, BorderLayout.NORTH);
            JPanel radioButtonPanel = new JPanel();
            ButtonGroup options = new ButtonGroup();
            JRadioButton b;

            for (BoardConfiguration.BoardConfigType t : BoardConfiguration.BoardConfigType.values()){
                b = new JRadioButton(t.name());
                b.setActionCommand(t.name());
                options.add(b);
                radioButtonPanel.add(b);
            }
            panel.add(radioButtonPanel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(null, panel);

            BoardConfiguration.BoardConfigType t = BoardConfiguration.BoardConfigType.valueOf(options.getSelection().getActionCommand());
            try {
                if (t == BoardConfiguration.BoardConfigType.ExternalFile) {
                    String fileName = JOptionPane.showInputDialog("Please enter the file name of the .json board configuration file");
                    return new BoardConfiguration(fileName);
                } else return new BoardConfiguration(t);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Invalid Board Configuration file. Default board configuration will be used", "Board Configuration Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
    }
    public WelcomeFrame() {
        WelcomeController c = new WelcomeController(this);
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        setTitle("notScrabble");
        setSize(920, 450);
        String imagePath = "images/notScrabble_logo.png";
        ImageIcon logo = new ImageIcon(imagePath);


        setIconImage(logo.getImage());
        JLabel image = new JLabel(logo);
        //image.setBorder(new BevelBorder(BevelBorder.RAISED));



        JButton instructions = new JButton("  Instructions  ");
        instructions.setFocusPainted(false);
        instructions.setFont(new Font("Tahoma",Font.BOLD, 15));
        instructions.setBackground(Color.RED);
        instructions.setForeground(Color.WHITE);
        instructions.setActionCommand(INSTRUCTIONS_CMD);
        instructions.addActionListener(c);

        JButton newGame = new JButton("    New Game   ");
        newGame.setFocusPainted(false);
        newGame.setBackground(Color.RED);
        newGame.setForeground(Color.WHITE);
        newGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        newGame.setActionCommand(NEW_GAME_CMD);
        newGame.addActionListener(c);

        JButton aiGame = new JButton("1 VS Computer");
        aiGame.setFocusPainted(false);
        aiGame.setBackground(Color.RED);
        aiGame.setForeground(Color.WHITE);
        aiGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        aiGame.setActionCommand(PLAYER_VS_AI);
        aiGame.addActionListener(c);


        container.add(image);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(instructions);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(newGame);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(aiGame);

        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        aiGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
