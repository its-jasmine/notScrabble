import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameView extends JFrame {

    private Game game;
    private ArrayList<PlayerView> playerViews;
    private Container southContainer;
    private BoardView boardView;
    private int currentView;

    public GameView(int numPlayers) throws HeadlessException {
        super("notScrabble");
        game = new Game(numPlayers);
        game.addView(this);
        GameController gameController = new GameController(game);
        boardView = new BoardView(game.getBoard());
        currentView = 0;
        playerViews = new ArrayList<>();
        ArrayList<Player> players = (ArrayList) game.getPlayers();
        for (Player player : players){
            playerViews.add(new PlayerView(player));
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        Container southContainer = new Container();
        Container northContainer = new Container();
        northContainer.setLayout(new GridLayout(1,3));
        contentPane.add(northContainer, BorderLayout.NORTH);
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

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> game.submit());
        JButton passButton = new JButton("Pass");
        passButton.addActionListener(e -> game.passTurn());

        southContainer.add(passButton,0);
        southContainer.add(passButton,2);

        contentPane.add(boardView, BorderLayout.CENTER);

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
        contentPane.add(eastLabel, BorderLayout.EAST);

        JLabel westLabel = new JLabel("maybe another rack");
        westLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentPane.add(westLabel, BorderLayout.WEST);

        this.setSize(800,800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GameView(2);
    }

    public void update(int playerTurn, boolean firstTurn) {
        if (!firstTurn){
            //remove contentpane south
            southContainer.remove(1);
        }
        // add new player view
        southContainer.add(playerViews.get(playerTurn), 1);
        southContainer.revalidate();
        southContainer.repaint();
    }
}
