public class Player {

    private Rack rack;
    private int score;
    private int turn;

    public Player(){
        Rack hand = new Rack();
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
    public void endTurn(){
        turn++; // keeps track of turns
        rack.getTiles();

    }

}
