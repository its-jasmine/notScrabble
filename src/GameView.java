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
        if (numAI >0){
            game = new Game(numPlayers, numAI);
        }
        else {
            game = new Game(numPlayers);
        }
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

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Tahoma",Font.BOLD, 18));
        submitButton.setPreferredSize(new Dimension(100,45));
        submitButton.addActionListener(e -> game.submit());

        JButton exchangeButton = new JButton("Exchange");
        exchangeButton.setFocusPainted(false);
        exchangeButton.setBackground(Color.RED);
        exchangeButton.setForeground(Color.WHITE);
        exchangeButton.setFont(new Font("Tahoma",Font.BOLD, 18));
        exchangeButton.setPreferredSize(new Dimension(100,45));
        //exchangeButton.addActionListener(e -> game.exchangeTiles());


        JButton passButton = new JButton("Pass");
        passButton.setFocusPainted(false);
        passButton.setBackground(Color.RED);
        passButton.setForeground(Color.WHITE);
        passButton.setFont(new Font("Tahoma",Font.BOLD, 18));
        passButton.setPreferredSize(new Dimension(100,45));
        passButton.addActionListener(e -> game.passTurn());

        southContainer.add(passButton, BorderLayout.WEST);
        southContainer.add(new JLabel("Rack goes here"), BorderLayout.CENTER);
        southContainer.add(submitButton, BorderLayout.EAST);
        //southContainer.add(exchangeButton, BorderLayout.EAST);

        contentPane.add(southContainer, BorderLayout.SOUTH);

        Container rightContainer = new Container();
        rightContainer.setLayout(new BorderLayout());
        rightContainer.add(exchangeButton,BorderLayout.SOUTH);
        //contentPane.add(rightContainer, BorderLayout.EAST);

        Container centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        centerContainer.add(boardView, BorderLayout.CENTER);
        centerContainer.add(boardView.getTableHeader(), BorderLayout.NORTH);
        boardView.getTableHeader().setReorderingAllowed(false);
        contentPane.add(centerContainer);

        Container leftContainer = new Container();
        leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));
        for (int i = 0; i<15; i++){
            if (i == 0){
                leftContainer.add(Box.createRigidArea(new Dimension(20, 37)));
            }
            leftContainer.add(new JLabel((i+1)+""));
            leftContainer.add(Box.createRigidArea(new Dimension(20,32)));
        }
        contentPane.add(leftContainer,BorderLayout.WEST);

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


        this.setSize(1000, 865);
        setLocationRelativeTo(null);
        this.setVisible(true);

        game.addView(this);
        game.playGame();
    }

    public static void main(String[] args) {
        new GameView(1, 1);
    }

    /**
     * updates the view
     * @param playerTurn the current turn
     * @param firstTurn boolean of first turn. true if it is the first turn, false otherwise
     */
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
