import java.util.ArrayList;

public class ValidTry implements Comparable<ValidTry>{


    private final int score;
    private final ArrayList<Tile> tilesToPlay;
    private final ArrayList<Coordinate> whereToPlayTiles;

    public ValidTry(int score, ArrayList<Tile> tilesToPlay, ArrayList<Coordinate> whereToPlayTiles) {
        this.score = score;
        this.tilesToPlay = new ArrayList<>();
        for (Tile t : tilesToPlay) {
            if (t instanceof BlankTile) t = new BlankTile(((BlankTile) t).letter); // pass by value as blanks maybe be reset for other tries
            this.tilesToPlay.add(t);
        }
        this.whereToPlayTiles = new ArrayList<>(whereToPlayTiles);
    }


    @Override
    public int compareTo(ValidTry o) {
        return Integer.compare(score, o.getScore());
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Tile> getTilesToPlay() {
        return tilesToPlay;
    }

    public ArrayList<Coordinate> getWhereToPlayTiles() {
        return whereToPlayTiles;
    }

}
