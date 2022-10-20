/**
 * Models the letter crossing game
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Game {
    private final static int MAXPLAYERS = 4; //could make this more
    private final static int MINPLAYERS = 2;
    private List<Player> players;
    private int playerTurn; // index in the player list
    private final Board board;
    private final Bag bag;


    public Game(int numPlayers) {
        if (numPlayers < MINPLAYERS) numPlayers = 2; // could add print statements
        else if (numPlayers > MAXPLAYERS) numPlayers= 4;

        this.players = new ArrayList<Player>();
        for (int i = numPlayers; i > 0; i--) {
            players.add(new Player());
        }
        Random random = new Random();
        this.playerTurn = random.nextInt(numPlayers); // picks who goes first
        this.board = new Board();
        this.bag = new Bag();
    }

    /**
     * Starts the game and continues to tell players to take turns until the game is over
     */
    public void playGame() {
        boolean isGameOver = false;
        while (!isGameOver) {
            playerTurn++;
            isGameOver = playerTakeTurn(playerTurn);
        }
        int leftOverLetterScore = 0;
        for (Player p : players) {
            leftOverLetterScore += p.getRackScore();
        }

        players.get(playerTurn).addToScore(leftOverLetterScore);

        int highScore = 0;
        int winner = 0;
        for (int i = 0; i < players.size(); i++) {
            int score = players.get(i).getScore();
            if (score > highScore) {
                highScore = score;
                winner = i;
            }
        }

        System.out.println("The winner is player" + (winner + 1) + " with a score of" + highScore + "!\n" ); // winner plus 1 so player at index 0 is player 1

    }

    /**
     * Tells the player to take its turn
     * @param index of the player to take its turn
     * @return false if turn is over but not the game, true if game is now over(last letter played)
     */
    private boolean playerTakeTurn(int index) {
        return players.get(index).takeTurn();
    }
}
