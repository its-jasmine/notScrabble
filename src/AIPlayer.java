import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

public class AIPlayer extends Player{
    private WordFinder wordFinder;

    public AIPlayer(Board board, Bag bag, int playerNumber) {
        super(board, bag, playerNumber);
        wordGenorator = new WordFinder();
    }

    @Override
    public Game.Status submit() {
        ArrayList<ValidTry> validTries = new ArrayList<>();
        ArrayList<Coordinate> prevPlayed = new ArrayList<>();
        prevPlayed.addAll(board.getPreviouslyPlayed());
        shuffle(prevPlayed); // some systems will return hash sets in the same order every time
        validTries.addAll(findLocationToTry(prevPlayed));
        ValidTry bestTry = Collections.max(validTries);
        board.placeTiles(bestTry.getWhereToPlayTiles(),bestTry.getTilesToPlay());

        int turnScore = board.submit();
        if (turnScore < 0) resetTurn(); // couldn't make any valid word, passing
        else this.addToScore(turnScore);
        return this.endTurn();
    }
    private ValidTry submitAndReset(ArrayList<Coordinate> emptySquareCoordinates, ArrayList<Tile> tilesToPlay) {
        int score = board.submit();
        resetTurn();
        if (score > 0) {
            return new ValidTry(score, tilesToPlay,emptySquareCoordinates);
        }
        return null;
    }

    private ArrayList<ValidTry> findLocationToTry(ArrayList<Coordinate> prevPlayed){
        ArrayList<ValidTry> validTries = new ArrayList<>();
        for (int i = 0; i < min(prevPlayed.size(), 5); i++) {
            for (int n = 1; n <= rack.getNumTiles(); n++) {
                validTries.addAll(lookForTileSpaceBelowWords(n, prevPlayed.get(i)));
            }
        }
        return validTries;
    }

    private ArrayList<ValidTry> lookForTileSpaceBelowWords(int i, Coordinate coordinate) {
        ArrayList<ValidTry> validTries = new ArrayList<>();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        boardTilesAboveAndCenter.add(board.getSquareTile(coordinate));

        String wordFormat = board.getSquareTile(coordinate).toString();
        Coordinate coordinateAbove = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        Tile tileAbove = board.getSquareTile(coordinateAbove);
        while (tileAbove != null) {
            wordFormat = tileAbove + wordFormat;
            boardTilesAboveAndCenter.add(tileAbove);
            coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
            tileAbove = board.getSquareTile(coordinateAbove);

        }

        Coordinate coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        Tile tileBelow = board.getSquareTile(coordinateBelow);
        for (int j = 1; j <= i; j++) {
            ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
            ArrayList<Tile> boardTilesBelow = new ArrayList<>();

            int emptySquares = 0;
            while (emptySquares < j) {

                while (tileBelow != null) {
                    wordFormat += tileBelow;
                    boardTilesBelow.add(tileBelow);
                    coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                    tileBelow = board.getSquareTile(coordinateBelow);

                }
                wordFormat += ".";
                emptySquares++;
                emptySquareCoordinates.add(new Coordinate(coordinateBelow.column, coordinateBelow.row));
                coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                tileBelow = board.getSquareTile(coordinateBelow);
                if (tileBelow != null) {
                    while (tileBelow != null) {
                        wordFormat += tileBelow;
                        boardTilesBelow.add(tileBelow);
                        coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                        tileBelow = board.getSquareTile(coordinateBelow);

                    }
                }
            }
            ArrayList<Tile> allTiles = new ArrayList<>();
            allTiles.addAll(rack.getTilesList());
            allTiles.addAll(boardTilesBelow);
            allTiles.addAll(boardTilesAboveAndCenter);
            ArrayList<String> words = wordGenorator.findWord(wordFormat, allTiles);
            for (String w : words) {
                ArrayList<Integer> indexes = new ArrayList<>(); // where are the empties in the string
                StringBuilder wordF = new StringBuilder(wordFormat);
                for (int index = 0; index < wordF.length(); index++) {
                    if (wordF.charAt(i) == '.') indexes.add(i);
                }
                StringBuilder word = new StringBuilder(w);
                ArrayList<Tile> tilesToPlay = new ArrayList<>();
                for (int index = 0; index < indexes.size(); index++) {// this makes tiles which is bad
                    tilesToPlay.add(Tile.valueOf(String.valueOf(word.charAt(indexes.get(index)))));
                }
                board.placeTiles(emptySquareCoordinates, tilesToPlay);
                validTries.add(submitAndReset(emptySquareCoordinates, tilesToPlay));

            }
        }

        return validTries;
    }
}
