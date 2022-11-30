public class GameConfiguration {
    private final BoardConfiguration boardConfiguration;

    private final int numPlayers;
    private final int numAI;
    public GameConfiguration(BoardConfiguration b, int numPlayers, int numAI){
        this.boardConfiguration = b;
        this.numPlayers = numPlayers;
        this.numAI = numAI;
    }
    public BoardConfiguration getBoardConfiguration() {
        return boardConfiguration;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public int getNumAI() {
        return numAI;
    }

}
