import java.util.ArrayList;
import java.util.List;

public class ValidTry implements Comparable<ValidTry>{


    private int score;
    private ArrayList<Tile> tilesToPlay;
    private ArrayList<Coordinate> whereToPlayTiles;

    public ValidTry(int score, ArrayList<Tile> tilesToPlay, ArrayList<Coordinate> whereToPlayTiles) {
        this.score = score;
        this.tilesToPlay = tilesToPlay;
        this.whereToPlayTiles = whereToPlayTiles;
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
