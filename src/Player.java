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
    private String name;
    /** the views of the player */
    private List<PlayerView> views;


    /**
     * Creates a new player with a specified board and bag.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     */
    public Player(Board board, Bag bag){
        views = new ArrayList<>();
        this.board = board;
        this.name = "";
        this.score = 0;
        this.rack = new Rack(bag);
    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param playerNumber the index of the player being created
     */
    public Player(Board board, Bag bag, int playerNumber) {
        this(board,bag);
        this.name = "Player " + playerNumber;
    }
    /**
     * Creates a new player with a specified board, bag and player number.
     * @param board of the game the player is participating in
     * @param bag that the player will draw tiles from
     * @param name of the player
     */
    public Player(Board board, Bag bag,String name) {
        this(board, bag);
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
            v.update(this.score);
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
        int turnScore = board.submit(); // we will update board to have internal list of tiles, no need for arg
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
     * resets the turn
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


}
