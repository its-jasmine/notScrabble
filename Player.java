public class Player {

    private String name;
    private Rack rack;
    private int score;
    private int turn;




    public Player(){
        this.name = "";
        Rack rack = new Rack();
        this.score = 0;
        this.turn = 0;
    }

    public Player(int playerNumber) {
        this();
        this.name = "Player " + playerNumber;
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public String getName() {return name;}

    public Rack getRack() {
        return rack;
    }

    public int getScore() {
        return score;
    }

    public int getTurn() {
        return turn;
    }
    public void placeLetter(String letter){
    }

    /**
     * Ends the player's turn.
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status endTurn(){
        return rack.drawTiles();// draws tiles from bag onto the rack
    }

    /**
     * gets the rack score from rack
     * @return int
     */
    public int getRackScore() {
        return rack.getRackScore();
    }
    /**
     * adds the score of the placed word to score
     */
    public void addToScore(int leftOverLetterScore) {
    }

    /**
     * Not sure what this needs
     * @return RUNNING if turn is over but not the game, OVER if game is now over(last letter played)
     */
    public Game.Status takeTurn() {
        return endTurn();
    }
}
