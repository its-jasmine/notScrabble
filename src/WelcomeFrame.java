import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
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
                new GameView(2);//default 2 players for now
                view.dispose();

            }
        }
    }
    public WelcomeFrame() {
        WelcomeController c = new WelcomeController(this);
        setLayout(new GridLayout(2, 1));
        setTitle("notScrabble");
        setSize(900, 400);
        ImageIcon logo = new ImageIcon("notScrabble_logo.png");

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        //panel.setLayout(new BorderLayout());
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new GridLayout(2,1,10,10));

        //setIconImage(logo.getImage());
        JLabel welcome = new JLabel(logo);
        welcome.setBorder(new BevelBorder(BevelBorder.RAISED));

        add(welcome);



        JButton instructions = new JButton("Instructions");
        //panel.add(instructions, BorderLayout.NORTH);
        panel.add(instructions);

        instructions.setActionCommand(INSTRUCTIONS_CMD);
        instructions.addActionListener(c);

        JButton newGame = new JButton("New Game");
        //panel.add(newGame, BorderLayout.CENTER);
        panel.add(newGame);

        newGame.setActionCommand(NEW_GAME_CMD);
        newGame.addActionListener(c);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        add(panel);


    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
