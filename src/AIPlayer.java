import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

public class AIPlayer extends Player{
    private static final int NUMBER_TILES_TO_TRY = 10; // to help limit how long the AI's turn is
    private WordFinder wordFinder;

    public AIPlayer(Board board, Bag bag, int playerNumber) {
        super(board, bag, playerNumber);
        wordFinder = new WordFinder();
    }

    @Override
    public Game.Status submit() {
        if (board.isStartSquareEmpty()) return Game.Status.RUNNING; //TODO handle AI goes first

        ArrayList<ValidTry> validTries = new ArrayList<>();
        ArrayList<Coordinate> prevPlayed = new ArrayList<>();
        prevPlayed.addAll(board.getPreviouslyPlayed());
        shuffle(prevPlayed); // some systems will return hash sets in the same order every time

        validTries.addAll(findLocationToTry(prevPlayed));

        if (validTries.size() == 0) return Game.Status.RUNNING; // couldn't make any valid word, passing

        ValidTry bestTry = Collections.max(validTries);
        ArrayList<Tile> playTiles = bestTry.getTilesToPlay();
        rack.removeTiles(playTiles);
        board.placeTiles(bestTry.getWhereToPlayTiles(),playTiles);
        this.addToScore(board.submit());
        System.out.println(bestTry.getScore());
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
        for (int i = 0; i < min(prevPlayed.size(), NUMBER_TILES_TO_TRY); i++) {
            validTries.addAll(lookForTileSpaceBelowWords(rack.getNumTiles(), prevPlayed.get(i)));
        }
        return validTries;
    }

    private ArrayList<ValidTry> lookForTileSpaceBelowWords(int numTilesOnRack, Coordinate coordinate) {
        ArrayList<ValidTry> validTries = new ArrayList<>();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        boardTilesAboveAndCenter.add(board.getSquareTile(coordinate));

        String wordFormat = board.getSquareTile(coordinate).toString();
        Coordinate coordinateAbove = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        if (coordinateAbove != null) { // check for edge of board
            Tile tileAbove = board.getSquareTile(coordinateAbove);
            while (coordinateAbove != null && tileAbove != null) {
                wordFormat = tileAbove + wordFormat;
                boardTilesAboveAndCenter.add(tileAbove);
                coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
                if (coordinateAbove != null) { // check for edge of board
                    tileAbove = board.getSquareTile(coordinateAbove);
                }

            }
        }
        Coordinate coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        if (coordinateBelow == null) return validTries; // if true found edge of board

        Tile tileBelow = board.getSquareTile(coordinateBelow);
        int emptySquares = 0;
        ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        for (int j = 1; j <= numTilesOnRack; j++) {

            searching:
            while (emptySquares < j) {

                while (tileBelow != null) {
                    wordFormat += tileBelow;
                    boardTilesBelow.add(tileBelow);
                    coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                    if (coordinateBelow == null) break searching; // if the edge of board was found stop looking for empty squares(double break)
                    tileBelow = board.getSquareTile(coordinateBelow);
                }

                wordFormat += ".";
                emptySquares++;
                emptySquareCoordinates.add(coordinateBelow);
                coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                if (coordinateBelow == null) break searching; // check for edge of board
                tileBelow = board.getSquareTile(coordinateBelow);
                if (tileBelow != null) {
                    while (tileBelow != null) {
                        wordFormat += tileBelow;
                        boardTilesBelow.add(tileBelow);
                        coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
                        if (coordinateBelow == null) break searching; // if the edge of board was found stop looking for empty squares(double break)
                        tileBelow = board.getSquareTile(coordinateBelow);
                    }
                }
            }
            // "break searching;" sends execution here

            ArrayList<Tile> allTiles = new ArrayList<>();

            allTiles.addAll(rack.getTilesList());
            allTiles.addAll(boardTilesBelow);
            allTiles.addAll(boardTilesAboveAndCenter);
            ArrayList<String> words;
            words = wordFinder.findWord(allTiles, wordFormat);
            for (String w : words) {
                ArrayList<Integer> indexes = new ArrayList<>(); // where are the empties in the string
                for (int cIndex = 0; cIndex < wordFormat.length(); cIndex++) {
                    if (wordFormat.charAt(cIndex) == '.') indexes.add(cIndex);
                }
                ArrayList<Tile> tilesToPlay = new ArrayList<>();
                for (int index = 0; index < indexes.size(); index++) {// this makes tiles which is bad
                    String letter = String.valueOf(w.charAt(indexes.get(index)));
                    tilesToPlay.add(Tile.valueOf(letter));
                }
                board.placeTiles(emptySquareCoordinates, tilesToPlay);
                ValidTry validTry = submitAndReset(emptySquareCoordinates, tilesToPlay);
                if (validTry != null) validTries.add(validTry);
            }
            if (coordinateBelow == null) break;
        }

        return validTries;
    }
}
