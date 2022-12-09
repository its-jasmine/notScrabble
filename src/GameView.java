import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * The main frame of the game, displays the game board, active player rack,
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 * @author Victoria Malouf
 * @version Milestone4
 */
public class GameView extends JFrame {
    private Game game;
    private ArrayList<PlayerView> playerViews;
    private Container southContainer;
    private BoardView boardView;
    private int currentView;

    public GameView(GameConfiguration gameConfig) throws HeadlessException {
        super("notScrabble");

        game = new Game(gameConfig);

        currentView = 0;
        boardView = new BoardView(game.getBoard());
        playerViews = new ArrayList<>();
        ArrayList<Player> players =  game.getPlayers();
        for (Player player : players){
            playerViews.add(new PlayerView(player));
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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


        Container northContainer = new Container();
        contentPane.add(northContainer, BorderLayout.NORTH);
        northContainer.setLayout(new GridLayout(1, 2));

        JButton undo = new JButton("UNDO");
        undo.addActionListener(e -> game.undo());
        northContainer.add(undo);

        JButton redo = new JButton("REDO");
        redo.addActionListener(e -> game.redo());
        northContainer.add(redo);


        this.setSize(1000, 865);
        setLocationRelativeTo(null);
        this.setVisible(true);

        game.addView(this);
        game.playGame();
    }


    public Game getGame() { // for testing
        return game;
    }


    /**
     * updates the view
     * @param playerTurn the current turn
     * @param firstTurn boolean of first turn. true if it is the first turn, false otherwise
     */
    public void update(int playerTurn, boolean firstTurn) {
        Player player = game.getPlayers().get(playerTurn);
        if (firstTurn) {
            JOptionPane.showMessageDialog(this,
                    player.getName() + " drew the highest tile and gets to go first",
                    "Congrats!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (player instanceof AIPlayer) {
            game.submit();

            playerTurn = ++playerTurn % game.getPlayers().size();
            southContainer.add(playerViews.get(playerTurn), 1);
        } else southContainer.add(playerViews.get(playerTurn), 1);

        southContainer.revalidate();
        southContainer.repaint();
    }
}
