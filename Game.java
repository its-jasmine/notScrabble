import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Models the letter crossing game.
 *  @author Rebecca Elliott
 *  @version Milestone1
 */
public class Game {
    /** The allowable game statuses */
    public enum Status {RUNNING, OVER} // used as a way to have a named boolean for readability
    /** The maximum number of players in a game */
    private final static int MAXPLAYERS = 4; //could make this more
    /** The minimum number of players in a game */
    private final static int MINPLAYERS = 2;
    /** The list of players in the game */
    private List<Player> players; // if we don't want players to be able to join once a game has started this can be final
    /** The index corresponding to the player who is currently playing their turn */
    private int playerTurn; // index in the player list

    /**
     * Creates a new game with the specifed number of players and selects a random player to go first.
     * @param numPlayers the number of players of the game
     */
    public Game(int numPlayers) {
        Board board = new Board();
        BoardView boardView = new BoardView(board);
        RackView rackView = new RackView(board);
        Bag bag = new Bag();
        if (numPlayers < MINPLAYERS) numPlayers = 2; // could add print statements to notify about the change
        else if (numPlayers > MAXPLAYERS) numPlayers= 4;

        this.players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(board, bag, i + 1));
        }
        Random random = new Random();
        this.playerTurn = random.nextInt(numPlayers); // pick who goes first

    }

    /**
     * Starts the game and continues to tell players to take turns until the game is over.
     */
    public void playGame() {
        Status status = Status.RUNNING; // this is the only place this is needed currently, if other things
        // need it later it can be turned into an instance field

        while (status == Status.RUNNING) {
            status = playerTakeTurn(playerTurn);
            playerTurn = ++playerTurn % players.size();
        }

        // game is now over
        setFinalScores();
        Player winner = getWinner();

        System.out.println("The winner is " + (winner.getName()) + " with a score of" + winner.getScore() + "!\n" );
    }

    /**
     * Tells the player to take its turn
     * @param index of the player to take its turn
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    private Status playerTakeTurn(int index) {

        return players.get(index).takeTurn();
    }

    /**
     * Gets the player with the highest score.
     * @return the player with the highest score
     */
    private Player getWinner() {
        int highScore = 0;
        int winner = 0;
        for (int i = 0; i < players.size(); i++) {
            int score = players.get(i).getScore();
            if (score > highScore) {
                highScore = score;
                winner = i;
            }
        }
        return players.get(winner);
    }

    /**
     * Gives points of tiles still on other player's racks to the player that ran out of tiles first.
     */
    private void setFinalScores() {
        int leftOverLetterScore = 0;
        for (Player p : players) {
            leftOverLetterScore += p.getRackScore();
        }
        players.get(playerTurn).addToScore(leftOverLetterScore);
    }

    /**
     * Runs a game with 2 players
     * @param args N/A
     */
    public static void main(String[] args) {
        Game game = new Game(2);
        game.playGame();
    }
}
