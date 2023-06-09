import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * The initial frame of the game, provides options for the user to start a new game or see game instructions.
 * @author Jasmine Gad El Hak
 * @version Milestone4
 */
public class WelcomeFrame extends JFrame {
    private static final String  INSTRUCTIONS_CMD = "instructions";
    private static final String  NEW_GAME_CMD = "new game";
    private static final String PLAYER_VS_AI = "1 vs ai";
    private static final String LOAD_GAME_CMD = "load game";
    private class WelcomeController implements ActionListener {
        private WelcomeFrame view;

        private WelcomeController(WelcomeFrame view){
            this.view = view;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            String actCmd = source.getActionCommand();
            if (actCmd.equals(LOAD_GAME_CMD)) { // if no input, don't crete 2 player game
                String fileName = JOptionPane.showInputDialog("Provide file name:" );
                try {
                    new GameView(null,fileName);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                GameConfiguration c;
                int numPlayers;
                int numAI;

                if (actCmd.equals(NEW_GAME_CMD)) {
                    try {
                        numPlayers = Integer.valueOf(JOptionPane.showInputDialog("How many players would you like?"));
                    } catch (Exception ex) {return;}
                    numAI = 0;
                } else { // source.getActionCommand().equals(PLAYER_VS_AI)
                    numPlayers = 1;
                    numAI = 1;
                }

                BoardConfiguration b = requestBoardConfiguration();
                c = new GameConfiguration(b, numPlayers, numAI);
                view.dispose();
                try {
                    new GameView(c, null);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
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

            for (BoardConfiguration.Type t : BoardConfiguration.Type.values()){
                b = new JRadioButton(t.name());
                b.setActionCommand(t.name());
                options.add(b);
                radioButtonPanel.add(b);
            }
            panel.add(radioButtonPanel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(null, panel);

            BoardConfiguration.Type t = BoardConfiguration.Type.valueOf(options.getSelection().getActionCommand());
            try {
                if (t == BoardConfiguration.Type.ExternalFile) {
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
        setSize(920, 550);
        String imagePath = "images/notScrabble_logo.png";
        ImageIcon logo = new ImageIcon(imagePath);


        setIconImage(logo.getImage());
        JLabel image = new JLabel(logo);


        JButton newGame = new JButton("    New Game   ");
        newGame.setFocusPainted(false);
        newGame.setBackground(Color.RED);
        newGame.setForeground(Color.WHITE);
        newGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        newGame.setActionCommand(NEW_GAME_CMD);
        newGame.addActionListener(c);

        JButton loadGame = new JButton("    Load Game   ");
        loadGame.setFocusPainted(false);
        loadGame.setBackground(Color.RED);
        loadGame.setForeground(Color.WHITE);
        loadGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        loadGame.setActionCommand(LOAD_GAME_CMD);
        loadGame.addActionListener(c);

        JButton aiGame = new JButton("1 VS Computer");
        aiGame.setFocusPainted(false);
        aiGame.setBackground(Color.RED);
        aiGame.setForeground(Color.WHITE);
        aiGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        aiGame.setActionCommand(PLAYER_VS_AI);
        aiGame.addActionListener(c);


        container.add(image);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(newGame);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(loadGame);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(aiGame);

        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        aiGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
