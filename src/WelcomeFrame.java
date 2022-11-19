import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    private static final String  INSTRUCTIONS_CMD = "instructions";
    private static final String  NEW_GAME_CMD = "new game";
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
                new GameView(numPlayers, 1); // default 2 players for now
                view.dispose();

            }
        }
    }
    public WelcomeFrame() {
        WelcomeController c = new WelcomeController(this);
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        setTitle("notScrabble");
        setSize(920, 400);
        String imagePath = "images/notScrabble_logo.png";
        ImageIcon logo = new ImageIcon(imagePath);


        setIconImage(logo.getImage());
        JLabel image = new JLabel(logo);
        //image.setBorder(new BevelBorder(BevelBorder.RAISED));



        JButton instructions = new JButton("Instructions");
        instructions.setFocusPainted(false);
        instructions.setFont(new Font("Tahoma",Font.BOLD, 15));
        instructions.setBackground(Color.RED);
        instructions.setForeground(Color.WHITE);
        instructions.setActionCommand(INSTRUCTIONS_CMD);
        instructions.addActionListener(c);

        JButton newGame = new JButton("  New Game ");
        newGame.setFocusPainted(false);
        newGame.setBackground(Color.RED);
        newGame.setForeground(Color.WHITE);
        newGame.setFont(new Font("Tahoma",Font.BOLD, 15));
        newGame.setActionCommand(NEW_GAME_CMD);
        newGame.addActionListener(c);

        container.add(image);
        container.add(Box.createRigidArea(new Dimension(0,10)));
        container.add(instructions);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(newGame);

        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
