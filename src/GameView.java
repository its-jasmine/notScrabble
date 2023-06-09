import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.*;
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
    private static String SHOW_EXCHANGE_CMD = "ShowExchangeView";
    private static String PROCESS_EXCHANGE_CMD = "ProcessExchange";

    public Game getGame() { // for testing
        return game;
    }
    private Game game;
    private ArrayList<PlayerView> playerViews;
    private Container southContainer;
    private BoardView boardView;
    private JButton exchangeButton;

    public GameView(GameConfiguration gameConfig, String fileName) throws HeadlessException, IOException, ClassNotFoundException {
        super("notScrabble");
        if (fileName != null){
            game = (Game) Game.loadGame(fileName);
        }
        else {
            game = new Game(gameConfig);
        }
        boardView = new BoardView(game.getBoard());
        playerViews = new ArrayList<>();
        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            playerViews.add(new PlayerView(player));
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Options");
        menuBar.add(menu);

        JMenuItem restart = new JMenuItem("New Game");
        restart.addActionListener(e -> {new WelcomeFrame(); this.dispose();});

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> {
            String saveFileName = JOptionPane.showInputDialog("Provide file name:" );
            try {
                game.saveGame(saveFileName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menu.add(restart);
        menu.add(saveGame);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        southContainer = new Container();
        southContainer.setLayout(new BorderLayout());

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Tahoma",Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(100,50));
        submitButton.addActionListener(e -> {
            game.submit();
            setExchangeButtonStatus(SHOW_EXCHANGE_CMD);

        });

        JButton passButton = new JButton("Pass");
        passButton.setFocusPainted(false);
        passButton.setBackground(Color.RED);
        passButton.setForeground(Color.WHITE);
        passButton.setFont(new Font("Tahoma",Font.BOLD, 14));
        passButton.setPreferredSize(new Dimension(100,50));
        passButton.addActionListener(e -> {
            getCurrentPlayerView().update(0);
            game.passTurn();
            setExchangeButtonStatus(SHOW_EXCHANGE_CMD);
        });

        exchangeButton = new JButton("Exchange");
        exchangeButton.setFocusPainted(false);
        exchangeButton.setBackground(Color.RED);
        exchangeButton.setForeground(Color.WHITE);
        exchangeButton.setFont(new Font("Tahoma",Font.BOLD, 13));
        exchangeButton.setPreferredSize(new Dimension(100,50));
        exchangeButton.setActionCommand(SHOW_EXCHANGE_CMD);
        exchangeButton.addActionListener(e -> {
            JButton b = (JButton)e.getSource();
            String actCmd = b.getActionCommand();
            if (actCmd.equals(SHOW_EXCHANGE_CMD)) {
                displayExchangeView();
                setExchangeButtonStatus(PROCESS_EXCHANGE_CMD);
            } else if (actCmd.equals(PROCESS_EXCHANGE_CMD)){
                boolean success = game.exchangeTiles();
                if (success) {
                    setExchangeButtonStatus(SHOW_EXCHANGE_CMD);
               } else {
                JOptionPane.showMessageDialog(this,
                        "There are not enough tiles in the bag to exchange these tiles. Try exchanging less tiles, or pass instead.");
                }
            }
        });

        Container rightSouth = new Container();
        rightSouth.setLayout(new BorderLayout());
        rightSouth.add(submitButton, BorderLayout.WEST);
        rightSouth.add(passButton, BorderLayout.CENTER);
        rightSouth.add(exchangeButton, BorderLayout.EAST);

        southContainer.add(rightSouth, BorderLayout.EAST);
        southContainer.add(new JLabel("Rack goes here"), BorderLayout.CENTER);

        contentPane.add(southContainer, BorderLayout.SOUTH);

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
        if (fileName != null) game.playGame(false);
        else game.playGame(true);
    }

    /**
     * Changes the exchange button appearance and behavior according to specified command.
     * @param command Action command desired for exchange button.
     */
    public void setExchangeButtonStatus(String command){
        if (exchangeButton.getActionCommand().equals(command)) return; // already set to desired state

        if (command.equals(SHOW_EXCHANGE_CMD)) {
            exchangeButton.setText("Exchange");
            exchangeButton.setActionCommand(SHOW_EXCHANGE_CMD);
        } else if (command.equals(PROCESS_EXCHANGE_CMD)){
            exchangeButton.setText("Done");
            exchangeButton.setActionCommand(PROCESS_EXCHANGE_CMD);
        }
    }

    private void displayExchangeView() {
        PlayerView currentPlayerView = getCurrentPlayerView();
        currentPlayerView.displayExchangeView();
        southContainer.revalidate();
        southContainer.repaint();
    }

    private PlayerView getCurrentPlayerView() {
        BorderLayout layout =  (BorderLayout)southContainer.getLayout();
        return (PlayerView) layout.getLayoutComponent(BorderLayout.CENTER);

    public Game getGame() { // for testing
        return game;
    }


    /**
     * updates the view
     * @param playerTurn the current turn
     * @param firstTurn boolean of first turn. true if it is the first turn, false otherwise
     */
    public void update(int playerTurn, boolean firstTurn) {

        // add new player view
        southContainer.remove(1);
        southContainer.add(playerViews.get(playerTurn), 1);

        BorderLayout layout =  (BorderLayout)southContainer.getLayout();
        Container rightSouth = (Container) layout.getLayoutComponent(BorderLayout.EAST);
        BorderLayout layout2 =  (BorderLayout) rightSouth.getLayout();
        Component c = layout2.getLayoutComponent(BorderLayout.EAST);

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
