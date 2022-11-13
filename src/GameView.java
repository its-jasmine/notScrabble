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
        currentView = 0;
        boardView = new BoardView(game.getBoard());
        playerViews = new ArrayList<>();
        ArrayList<Player> players =  game.getPlayers();
        for (Player player : players){
            playerViews.add(new PlayerView(player));
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Options");
        menuBar.add(menu);

        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem seeRules = new JMenuItem("See rules");

        menu.add(newGame);
        menu.add(restart);
        menu.add(seeRules);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        southContainer = new Container();
        southContainer.setLayout(new GridLayout(1,3));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> game.submit());
        JButton passButton = new JButton("Pass");
        passButton.addActionListener(e -> game.passTurn());

        southContainer.add(passButton, 0);
        southContainer.add(new JLabel("Rack goes here"), 1);
        southContainer.add(submitButton, 2);

        contentPane.add(southContainer, BorderLayout.SOUTH);



        contentPane.add(boardView, BorderLayout.CENTER);


        Container northContainer = new Container();
        northContainer.setLayout(new GridLayout(1, 3));
        contentPane.add(northContainer, BorderLayout.NORTH);

        JLabel timeLabel = new JLabel("game time GOES HERE");
        timeLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(timeLabel, 0);

        JLabel northLabel = new JLabel("NotScrabble");
        northLabel.setHorizontalAlignment(JLabel.CENTER);
        northLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(northLabel, 1);

        JLabel scoreLabel = new JLabel("game score GOES HERE");
        scoreLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(scoreLabel, 2);


        this.setSize(800,800);
        this.setVisible(true);

        game.addView(this);
        game.playGame();
    }

    public static void main(String[] args) {
        new GameView(2);
    }

    public void update(int playerTurn, boolean firstTurn) {

        // add new player view

        southContainer.remove(1);
        southContainer.add(playerViews.get(playerTurn), 1);
        southContainer.revalidate();
        southContainer.repaint();
    }
}
