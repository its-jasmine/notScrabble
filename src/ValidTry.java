import java.util.ArrayList;

public class ValidTry implements Comparable<ValidTry>{


    private final int score;
    private final ArrayList<Tile> tilesToPlay;
    private final ArrayList<Coordinate> whereToPlayTiles;

    public ValidTry(int score, ArrayList<Tile> tilesToPlay, ArrayList<Coordinate> whereToPlayTiles) {
        this.score = score;
        this.tilesToPlay = new ArrayList<>();
        for (Tile t : tilesToPlay) {
            if (t instanceof BlankTile) t = new BlankTile(((BlankTile) t).getLetter()); // pass by value as blanks maybe be reset for other tries
            this.tilesToPlay.add(t);
        }
        this.whereToPlayTiles = new ArrayList<>(whereToPlayTiles);
    }


    @Override
    public int compareTo(ValidTry o) {
        return Integer.compare(score, o.getScore());
    }

    /**
     * gets the score
     * @return  an int of the score
     */
    public int getScore() {
        return score;
    }

    /**
     * gets tiles to be played
     * @return an array list of tiles
     */
    public ArrayList<Tile> getTilesToPlay() {
        return tilesToPlay;
    }

    /**
     * gets the positions for the tiles to be played
     * @return an arraylist of coordinate objects
     */
    public ArrayList<Coordinate> getWhereToPlayTiles() {
        return whereToPlayTiles;
    }

}
