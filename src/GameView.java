import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GameView extends JFrame {

    private Game game;

    public GameView() throws HeadlessException {
        super("notScrabble");
        game = new Game(2);
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



        BoardView centerPanel = new BoardView(board);
        //centerPanel.setHorizontalAlignment(JLabel.CENTER);
        contentpane.add(centerPanel, BorderLayout.CENTER);

        RackView southPanel = new RackView(board);
        //southPanel.setHorizontalAlignment(JLabel.CENTER);
        southPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentpane.add(southPanel, BorderLayout.SOUTH);

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
        Board board = new Board();
        GameView b = new GameView(board,new Rack(new Bag()));
    }
}
