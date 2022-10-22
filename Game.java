/**
 * Models the letter crossing game
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Game {
    public enum Status {RUNNING, OVER} // used as a way to have a named boolean for readability
    private final static int MAXPLAYERS = 4; //could make this more
    private final static int MINPLAYERS = 2;
    private List<Player> players; // if we don't want players to be able to join once a game has started this can be final
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
        Status status = Status.RUNNING; // this is the only place this is needed currently, if other things
        // need it later it can be turned into an instance field
        while (status == Status.RUNNING) {
            playerTurn++;
            status = playerTakeTurn(playerTurn);
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
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    private Status playerTakeTurn(int index) {
        return players.get(index).takeTurn();
    }
}
