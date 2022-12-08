import java.util.*;

/**
 * Represents a player in the game.
 * @author Arthur Atangana
 * @version Milestone1
 */
public class Player {
    /** The board of the game the player is participating in */
    protected final Board board;
    /** The player's rack */
    protected final Rack rack;
    /** The player's current score */
    private int score;
    /** The player's name */
    protected String name;
    /** the views of the player */
    private List<PlayerView> views;
    private Bag bag;


    /**
     * Creates a new player with a specified board and bag.
     *
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     */
    public Player(Board board, Bag bag, Stack<Move> moves){
        views = new ArrayList<>();
        this.board = board;
        this.name = "";
        this.score = 0;
        this.bag = bag;
        this.rack = new Rack(bag, moves);

    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param playerNumber the index of the player being created
     */
    public Player(Board board, Bag bag, Stack<Move> moves, int playerNumber) {
        this(board, bag, moves);
        this.name = "Player " + playerNumber;
    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param name of the player
     */
    public Player(Board board, Bag bag, Stack<Move> moves, String name) {
        this(board, bag, moves);
        this.name = name;
    }

    /**
     * Returns the name of the player.
     * @return name of the player
     */
    public String getName() {return name;}

    /**
     * Returns the player's rack.
     * @return the player's rack
     */
    public Rack getRack() {
        return rack;
    }
    /**
     * Returns the player's score.
     * @return the player's score
     */
    public int getScore() {
        return score;
    }
    /**
     * Adds to the player's score.
     * @param score The score to add.
     */
    public void addToScore(int score) {
        this.score += score;
        for (PlayerView v : views) {
            v.update(score);
        }
    }
    /**
     * Ends the player's turn.
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status endTurn(){
        board.addThisTurnToPreviously();// also resets playedThisTurn
        return rack.drawTiles();// draws tiles from bag onto the rack
    }

    /**
     * gets the rack score from rack
     * @return int
     */
    public int getRackScore() {
        return rack.tallyRackScore();
    }

    /**
     * Adds views to the player
     * @param view the view to be added to the player
     */
    public void addView(PlayerView view){
        views.add(view);
    }

    /**
     * submits the currently placed tiles to the board
     * @return Game.Status the status of the game
     */
    public Game.Status submit(){
        int turnScore = board.submit();
        if (turnScore < 0) return Game.Status.RETRY;
        Square s;
        for (Coordinate c : board.getPlayedThisTurn()){
            s = board.getSquare(c);
            s.setSquareAsPlayedPreviously();
        }
        this.addToScore(turnScore);
        return this.endTurn();
    }

    /**
     * Resets the players turn, returning all tiles they've played this round back to their rack.
     */
    public void resetTurn() {
        ArrayList<Tile> returnTiles = new ArrayList<>();
        for (Coordinate c: board.getPlayedThisTurn()) {
            Square s = (Square) board.getModel().getValueAt(c.getRowIndex(), c.getColumnIndex());
            Tile temp = s.getTile();
            if (temp instanceof BlankTile){
                ((BlankTile) temp).resetLetter();
            }
            returnTiles.add(temp);
            s.setTile(null);
        }
        rack.putTilesOnRack(returnTiles);
        board.resetPlayedThisTurn();
        board.getModel().fireTableDataChanged();
    }


    /**Attempts to exchange tiles from player's Rack for new tiles from the bag.
     * @return true, if the exchange is successful, false, if there are not enough tiles in the bag to perform the exchange.
     */
    public boolean exchangeTiles() {
        ArrayList<Tile> tilesToExchange = rack.removeTilesToExchange();
        ArrayList<Tile> newTiles = bag.exchangeTiles(tilesToExchange);
        if (newTiles.isEmpty()) {
            rack.putTilesOnRack(tilesToExchange);
            return false;
        }
        rack.putTilesOnRack(newTiles);
        for (PlayerView v : views) v.update(0);
        return true;
    }

}
