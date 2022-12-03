import java.util.*;
import java.util.stream.Collectors;

/**
 * Models the letter crossing game.
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 * @author Victoria Malouf
 * @version Milestone4
 */
public class Game {

    /** The allowable game statuses */
    public enum Status {RUNNING, OVER, RETRY;} // used as a way to have a named boolean for readability
    /** The maximum number of players in a game */
    private final static int MAXPLAYERS = 4; //could make this more
    /** The minimum number of players in a game */
    private final static int MINPLAYERS = 2;
    /** The list of players in the game */
    private List<Player> players; // if we don't want players to be able to join once a game has started this can be final


    /** The index corresponding to the player who is currently playing their turn */
    private int playerTurn; // index in the player list

    private List<GameView> views;
    private Board board;
    private Bag bag;

    /**
     * Creates a new game with the specifed number of players.
     * @param numPlayers the number of players of the game
     */
    public Game(int numPlayers) {
        views = new ArrayList<>();
        board = new Board();
        bag = new Bag();

        if (numPlayers < MINPLAYERS) numPlayers = 2; // could add print statements to notify about the change
        else if (numPlayers > MAXPLAYERS) numPlayers= 4;

        this.playerTurn = firstPlayer(bag, numPlayers);

        this.players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(board, bag, i + 1));
        }
    }


    /**
     * Creates a new game using the given game configuration.
     * @param gameConfig contains the game configuration information.
     */
    public Game(GameConfiguration gameConfig) {
        int numPlayers = gameConfig.getNumPlayers();
        int numAI = gameConfig.getNumAI();
        BoardConfiguration b = gameConfig.getBoardConfiguration();

        views = new ArrayList<>();
        if (b == null) board = new Board();
        else board = new Board(b);
        bag = new Bag();

        if (numPlayers < MINPLAYERS) numPlayers = 1; // could add print statements to notify about the change
        else if (numPlayers > MAXPLAYERS) numPlayers= 4;

        this.playerTurn = firstPlayer(bag, numPlayers + numAI);

        this.players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(board, bag, i + 1));
        }
        for (int i = 0; i < numAI; i++) {
            players.add(new AIPlayer(board, bag, i + 1));
        }
    }

    /**
     * Determine the player who draws the tile closest to A or the blank tile.
     * @param bag of this Game
     * @param numPlayers of this Game
     * @return index of the Player that will go first
     */
    private int firstPlayer(Bag bag, int numPlayers) {
        int playerIndex;
        ArrayList<Tile> tilesDrawn = new ArrayList<>();
        int lowestOrdinal, lowestOrdinalCount, blankTileCount;
        do {
            ArrayList<Tile> tilesDrawnThisRound = bag.drawTiles(numPlayers);
            ArrayList<LetterTile> letterTilesDrawnThisRound = new ArrayList<>();
            tilesDrawn.addAll(tilesDrawnThisRound);
            playerIndex = 0;
            lowestOrdinalCount = 0;
            blankTileCount = 0;
            for (int i = 0; i < numPlayers; i++) {
                Tile t = tilesDrawnThisRound.get(i);
                if (t instanceof BlankTile) {
                    playerIndex = i;
                    blankTileCount++;
                } else {
                    LetterTile l = (LetterTile) t;
                    letterTilesDrawnThisRound.add(l);
                }
            }

            if (blankTileCount == 0) {
                playerIndex = 0;
                List<Integer> tileOrdinals = letterTilesDrawnThisRound.stream().map(tile -> tile.ordinal()).collect(Collectors.toList());
                lowestOrdinal = tileOrdinals.get(0);
                for (int i = 0; i < numPlayers; i++) {
                    if (tileOrdinals.get(i) < lowestOrdinal) {
                        lowestOrdinal = tileOrdinals.get(i);
                        playerIndex = i;
                    }
                }
                for (LetterTile t : letterTilesDrawnThisRound) { // Check if 2 players picked the same highest tile
                    if (t.ordinal() == lowestOrdinal) {
                        lowestOrdinalCount++;
                    }
                }
            }
        }
        while (lowestOrdinalCount > 1 || blankTileCount > 1);
        bag.returnTiles(tilesDrawn);
        return playerIndex;
    }

    /**
     * gets the game's board
     * @return a board object
     */
    public Board getBoard(){
        return board;
    }

    /**
     * gets the game's bag
     * @return a bag object
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Getter for the player of the current turn (for testing purposes only)
     * @return index of the current player
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * Getter for the views list (for testing purposes only)
     * @return list of the GameViews observing this game
     */
    public List<GameView> getViews() {
        return views;
    }

    /**
     * @return an arraylist of players
     */
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players;
    }

    /**
     * Starts the game and continues to tell players to take turns until the game is over.
     */
    public void playGame() {
        updateGameView(true);
    }

    /**
     * Increments the turn
     */
    private void nextTurn(){
        playerTurn = ++playerTurn % players.size();
    }

    /**
     * Passes the turn of the player
     */
    public void passTurn(){
        Player player = players.get(playerTurn);
        player.resetTurn();
        nextTurn();
        updateGameView(false);
    }

    /**
     * Submits the player's word
     */
    public void submit(){
        Player player = players.get(playerTurn);
        Status status = player.submit();

        if (status == Status.RUNNING){
            nextTurn();
            if (!(player instanceof AIPlayer)) {
                updateGameView(false);
            }
        }
        else if (status == Status.RETRY){ // only happens for non-AI players
            player.resetTurn();
        }
        else{
            endGame();
            System.out.println("game done");
        }
    }
    public boolean exchangeTiles() {
        boolean successfulExchange = players.get(playerTurn).exchangeTiles();
        if (successfulExchange) {passTurn(); return true;}
        return false;
    }


    /**
     * Ends the game
     */
    private void endGame() {
        // game is now over
        setFinalScores();
        Player winner = getWinner();

        System.out.println("The winner is " + (winner.getName()) + " with a score of " + winner.getScore() + "!\n" );
    }

    /**
     * updates the views of the game.
     * @param firstTurn if it is the first turn, true, else, false
     */
    private void updateGameView(boolean firstTurn){
        for (GameView view : views){
            view.update(playerTurn, firstTurn);
        }
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
     * adds a view to the game
     * @param gameView the view to be added
     */
    public void addView(GameView gameView) {
        views.add(gameView);
    }

    /**
     * Runs a game with 2 players
     * @param args N/A
     */
    public static void main(String[] args) {
        //Game game = new Game(1, 1, );
        //game.playGame();
    }
}
