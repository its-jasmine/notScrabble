import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {

    private Game game;
    private List<PlayerView> playerViews;
    private Container contentPane;
    private BoardView boardView;
    private int currentView;

    public GameView(int numPlayers) throws HeadlessException {
        super("notScrabble");
        game = new Game(numPlayers);
        game.addView(this);
        GameController gameController = new GameController(game);
        boardView = new BoardView(game.getBoard());
        game.addBoardViewToBoard(boardView);
        currentView = 0;
        playerViews = new ArrayList<>();
        for (int i = 0; i<numPlayers; i++){
            playerViews.add(new PlayerView());
            game.addPlayerViewToPlayer(playerViews.get(i),i);

        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
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

        submitButton
        JButton passButton = new JButton("Pass");



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

    public void update(int playerTurn) {
        //remove contentpane south
        this.getContentPane().remove(playerViews.get(currentView));
        // add new player view
        this.getContentPane().add(playerViews.get(playerTurn), BorderLayout.SOUTH);

    }
}
