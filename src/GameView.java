import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameView extends JFrame {

    public Game getGame() { // for testing
        return game;
    }

    private Game game;
    private ArrayList<PlayerView> playerViews;
    private Container southContainer;
    private BoardView boardView;
    private int currentView;

    public GameView(int numPlayers, int numAI) throws HeadlessException {
        super("notScrabble");

        game = new Game(numPlayers, numAI);
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
        southContainer.setLayout(new BorderLayout());
        //southContainer.setLayout(new GridLayout(1,3));

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Tahoma",Font.BOLD, 18));
        submitButton.setPreferredSize(new Dimension(100,50));
        submitButton.addActionListener(e -> game.submit());

        JButton passButton = new JButton("Pass");
        passButton.setFocusPainted(false);
        passButton.setBackground(Color.RED);
        passButton.setForeground(Color.WHITE);
        passButton.setFont(new Font("Tahoma",Font.BOLD, 18));
        passButton.setPreferredSize(new Dimension(100,50));
        passButton.addActionListener(e -> game.passTurn());

        southContainer.add(passButton, BorderLayout.WEST);
        southContainer.add(new JLabel("Rack goes here"), BorderLayout.CENTER);
        southContainer.add(submitButton, BorderLayout.EAST);

        contentPane.add(southContainer, BorderLayout.SOUTH);

        contentPane.add(boardView, BorderLayout.CENTER);


        Container northContainer = new Container();
        northContainer.setLayout(new GridLayout(1, 3));
        contentPane.add(northContainer, BorderLayout.NORTH);

        /*JLabel timeLabel = new JLabel("game time GOES HERE");
        timeLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(timeLabel, 0);

        JLabel northLabel = new JLabel("NotScrabble");
        northLabel.setHorizontalAlignment(JLabel.CENTER);
        northLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(northLabel, 1);

        JLabel scoreLabel = new JLabel("game score GOES HERE");
        scoreLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
        northContainer.add(scoreLabel, 2);*/


        this.setSize(1000, 910);
        this.setVisible(true);

        game.addView(this);
        game.playGame();
    }

    public static void main(String[] args) {
        new GameView(1, 1);
    }

    public void update(int playerTurn, boolean firstTurn) {
        southContainer.remove(1);
        Player player = game.getPlayers().get(playerTurn);
        if (player instanceof AIPlayer) {
            game.submitAI();
            playerTurn = ++playerTurn % game.getPlayers().size();
            southContainer.add(playerViews.get(playerTurn), 1);
        } else southContainer.add(playerViews.get(playerTurn), 1);

        southContainer.revalidate();
        southContainer.repaint();
    }
}
