import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
            if (source.getActionCommand().equals(INSTRUCTIONS_CMD)){
                JOptionPane.showMessageDialog(view, "Instructions blah blah blah");
            }else if (source.getActionCommand().equals(NEW_GAME_CMD)){
                int numPlayers = Integer.valueOf(JOptionPane.showInputDialog("How many players would you like?"));
                try {
                    new GameView(numPlayers, 0,null); // default 2 players for now
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                view.dispose();
            } else if (source.getActionCommand().equals(PLAYER_VS_AI)) {
                try {
                    new GameView(1,1,null);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                view.dispose();
            }else if (source.getActionCommand().equals(LOAD_GAME_CMD)) { // if no input, don't crete 2 player game
                String fileName = JOptionPane.showInputDialog("Provide file name:" );
                try {
                    new GameView(0,0,fileName);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
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
        container.add(instructions);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(newGame);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(loadGame);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(aiGame);

        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
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
