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
    private JButton exchangeButton;
    private JButton doneExchangeButton;

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
        southContainer.setLayout(new BorderLayout());

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Tahoma",Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(100,50));
        submitButton.addActionListener(e -> game.submit());

        JButton passButton = new JButton("Pass");
        passButton.setFocusPainted(false);
        passButton.setBackground(Color.RED);
        passButton.setForeground(Color.WHITE);
        passButton.setFont(new Font("Tahoma",Font.BOLD, 14));
        passButton.setPreferredSize(new Dimension(100,50));
        passButton.addActionListener(e -> game.passTurn());

        exchangeButton = new JButton("Exchange");
        exchangeButton.setFocusPainted(false);
        exchangeButton.setBackground(Color.RED);
        exchangeButton.setForeground(Color.WHITE);
        exchangeButton.setFont(new Font("Tahoma",Font.BOLD, 13));
        exchangeButton.setPreferredSize(new Dimension(100,50));
        exchangeButton.addActionListener(e -> displayExchangeView());

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

        doneExchangeButton = new JButton("Done");
        doneExchangeButton.setFocusPainted(false);
        doneExchangeButton.setBackground(Color.RED);
        doneExchangeButton.setForeground(Color.WHITE);
        doneExchangeButton.setFont(new Font("Tahoma",Font.BOLD, 13));
        doneExchangeButton.setPreferredSize(new Dimension(100,50));
        doneExchangeButton.addActionListener(
                e -> { boolean success = game.exchangeTiles();
                    if (!success) JOptionPane.showMessageDialog(this,
                            "There are not enough tiles in the bag to exchange these tiles. Try exchanging less tiles, or pass instead.");
                } );

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



        /*Container northContainer = new Container();
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
        northContainer.add(scoreLabel, 2);*/


        this.setSize(1000, 865);
        setLocationRelativeTo(null);
        this.setVisible(true);

        game.addView(this);
        game.playGame();
    }

    private void displayExchangeView() {
        BorderLayout layout =  (BorderLayout)southContainer.getLayout();
        PlayerView currentPlayerView = (PlayerView) layout.getLayoutComponent(BorderLayout.CENTER);
        Container rightSouth = (Container) layout.getLayoutComponent(BorderLayout.EAST);

        currentPlayerView.displayExchangeView();
        rightSouth.remove(exchangeButton);

        rightSouth.add(doneExchangeButton, BorderLayout.EAST);
        southContainer.revalidate();
        southContainer.repaint();
    }

    public static void main(String[] args) {
        new GameView(4);
    }

    public void update(int playerTurn, boolean firstTurn) {

        // add new player view

        southContainer.remove(1);
        southContainer.add(playerViews.get(playerTurn), 1);

        BorderLayout layout =  (BorderLayout)southContainer.getLayout();
        Container rightSouth = (Container) layout.getLayoutComponent(BorderLayout.EAST);
        BorderLayout layout2 =  (BorderLayout) rightSouth.getLayout();
        Component c = layout2.getLayoutComponent(BorderLayout.EAST);
        if (c == doneExchangeButton) {
            rightSouth.remove(c);
            rightSouth.add(exchangeButton, BorderLayout.EAST);
        }
        southContainer.revalidate();
        southContainer.repaint();
    }
}
