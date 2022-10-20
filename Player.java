public class Player {

    private Rack rack;
    private int score;
    private int turn;



    public Player(){
        Rack rack = new Rack();
        this.score = 0;
        this.turn = 0;
    }

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
     */
    public void endTurn(){
        rack.drawTiles();// draws tiles from bag onto the rack
        turn++; // keeps track of turns

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
     * @return
     */
    public boolean takeTurn() {
        return true;
    }
}
