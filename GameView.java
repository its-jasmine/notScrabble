import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GameView extends JFrame {

    public GameView(Board board) throws HeadlessException {
        super("notScrabble");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        Container contentpane = this.getContentPane();
        contentpane.setLayout(new BorderLayout());
        Container northContainer = new Container();
        northContainer.setLayout(new GridLayout(1,3));
        contentpane.add(northContainer, BorderLayout.NORTH);
        JMenuBar menuBar = new JMenuBar();
        this.add(menuBar);
        JMenu menu = new JMenu("Options");
        menuBar.add(menu);

        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem seeRules = new JMenuItem("See rules");

        menu.add(newGame);
        menu.add(restart);
        menu.add(seeRules);



        BoardView centerPanel = new BoardView();
        //centerPanel.setHorizontalAlignment(JLabel.CENTER);
        contentpane.add(centerPanel, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel("game time GOES HERE");
        timeLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(timeLabel, 0);

        JLabel northLabel = new JLabel("opponent rack GOES HERE");
        northLabel.setHorizontalAlignment(JLabel.CENTER);
        northLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(northLabel, 1);

        JLabel scoreLabel = new JLabel("game score GOES HERE");
        scoreLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(scoreLabel, 2);

        RackView sourthPanel = new RackView(board);
        //sourthPanel.setHorizontalAlignment(JLabel.CENTER);
        sourthPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentpane.add(sourthPanel, BorderLayout.SOUTH);

        JLabel eastLabel = new JLabel("maybe another rack");
        eastLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentpane.add(eastLabel, BorderLayout.EAST);

        JLabel westLabel = new JLabel("maybe another rack");
        westLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentpane.add(westLabel, BorderLayout.WEST);

        this.setSize(800,800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        GameView b = new GameView(new Board());
    }
}
