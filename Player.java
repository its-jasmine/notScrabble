public class Player {

    private Rack rack;
    private int score;
    private int turn;
    private boolean isTurn;


    public Player(){
        Rack rack = new Rack();
        this.score = 0;
        this.turn = 0;
        this.isTurn = false;
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
        if (!isTurn){
            System.out.println("it is not your turn yet");
        }


    }

    /**
     * Ends the player's turn.
     */
    public void endTurn(){
        if (!isTurn){
            System.out.println("it is not your turn yet");
        }

        rack.drawTiles();// draws tiles from bag onto the rack
        isTurn = false;
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

    public boolean takeTurn() {
        if (!isTurn){
            return true; //if you are not in your turn, sets is turn to indicate that it is now your turn
        }
        else{
            return false; // false
        }
        //return isTurn;
    }
}
