import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

public class AIPlayer extends Player{
    private static final int NUMBER_TILES_TO_TRY = 20; // to help limit how long the AI's turn is
    private final WordFinder wordFinder;

    public AIPlayer(Board board, Bag bag, int playerNumber) {
        super(board, bag, playerNumber);
        wordFinder = new WordFinder();
    }

    @Override
    public Game.Status submit() {
        if (board.isStartSquareEmpty()) return Game.Status.RUNNING; //TODO handle AI goes first

        ArrayList<Coordinate> prevPlayed = new ArrayList<>(board.getPreviouslyPlayed());
        shuffle(prevPlayed); // some systems will return hash sets in the same order every time

        ArrayList<ValidTry> validTries = new ArrayList<>(findLocationToTry(prevPlayed));

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
        StringBuilder wordFormat = new StringBuilder();
        ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        findTilesAboveAndCenter(coordinate,boardTilesAboveAndCenter, wordFormat);

        Coordinate coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        if (coordinateBelow == null) return validTries; // if true found edge of board

        for (int j = 1; j <= numTilesOnRack; j++) {
            coordinateBelow = findAnEmptyAndTilesBelow(coordinateBelow, emptySquareCoordinates, wordFormat,boardTilesBelow);

            String sWordFormat = wordFormat.toString();
            ArrayList<String> words = new ArrayList<>(wordFinder.findWord(allNeededTiles(boardTilesBelow, boardTilesAboveAndCenter), sWordFormat));

            validTries.addAll(findValidTries(words, sWordFormat, emptySquareCoordinates));
            if (coordinateBelow == null) break; // edge of board was found
        }

        return validTries;
    }

    private ArrayList<Tile> allNeededTiles(ArrayList<Tile> boardTilesBelow, ArrayList<Tile> boardTilesAboveAndCenter) {
        ArrayList<Tile> allTiles = new ArrayList<>(rack.getTilesList());
        allTiles.addAll(boardTilesBelow);
        allTiles.addAll(boardTilesAboveAndCenter);
        return allTiles;
    }

    private void findTilesAboveAndCenter(Coordinate centerCoordinate, ArrayList<Tile> boardTilesAboveAndCenter, StringBuilder wordFormat) {
        if (centerCoordinate == null || board.getSquareTile(centerCoordinate) == null) return;

        wordFormat.append(board.getSquareTile(centerCoordinate).toString());
        boardTilesAboveAndCenter.add(board.getSquareTile(centerCoordinate));

        Coordinate coordinateAbove = centerCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        if (coordinateAbove == null) return;// check for edge of board
        Tile tileAbove = board.getSquareTile(coordinateAbove);
        while (tileAbove != null) {
            wordFormat.append(tileAbove);
            boardTilesAboveAndCenter.add(tileAbove);
            coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
            if (coordinateAbove == null) {
                wordFormat.reverse(); // so letters will be appended to front
                return; // check for edge of board
            }
            tileAbove = board.getSquareTile(coordinateAbove);

        }
        wordFormat.reverse(); // so letters will be appended to front
    }

    private Coordinate findTilesBelow(Coordinate startSearchingCoordinate, ArrayList<Tile> boardTilesBelowAndCenter, StringBuilder wordFormat) {
        if (startSearchingCoordinate == null)  return null;
        if (board.getSquareTile(startSearchingCoordinate) == null) return startSearchingCoordinate;

        Coordinate coordinateBelow = startSearchingCoordinate; // just for naming
        Tile tileBelow = board.getSquareTile(coordinateBelow);
        while (tileBelow != null) {
            wordFormat.append(tileBelow);
            boardTilesBelowAndCenter.add(tileBelow);
            coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
            if (coordinateBelow == null) return null; // the edge of board was found after previously played tiles but before an empty square was found
            tileBelow = board.getSquareTile(coordinateBelow);
        }
        return coordinateBelow;
    }


    private Coordinate findAnEmptyAndTilesBelow(Coordinate startSearchingCoordinate, ArrayList<Coordinate> emptySquareCoordinates, StringBuilder wordFormat, ArrayList<Tile> boardTilesBelow) {
        if (startSearchingCoordinate == null) return null;

        Coordinate coordinateBelow = findTilesBelow(startSearchingCoordinate, boardTilesBelow, wordFormat); // gets consecutive tiles, no empties

        if (coordinateBelow == null) {
            // the edge of board was found after previously played tiles but before an empty square was found
            return null;
        }

        wordFormat.append(".");
        emptySquareCoordinates.add(coordinateBelow);
        coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);

        if (coordinateBelow == null) {
            // the edge of board was found after the empty square was found
            return null;
        }

        Tile tileBelow = board.getSquareTile(coordinateBelow);
        if (tileBelow != null) {
            coordinateBelow = findTilesBelow(coordinateBelow, boardTilesBelow, wordFormat);
        }
        return coordinateBelow;
    }


    private ArrayList<ValidTry> findValidTries(ArrayList<String> words, String wordFormat, ArrayList<Coordinate> emptySquareCoordinates) {
        ArrayList<ValidTry> validTries = new ArrayList<>();
        for (String w : words) {
            ArrayList<Integer> indexes = new ArrayList<>(); // where are the empties in the string
            for (int cIndex = 0; cIndex < wordFormat.length(); cIndex++) {
                if (wordFormat.charAt(cIndex) == '.') indexes.add(cIndex);
            }

            ArrayList<Tile> tilesToPlay = new ArrayList<>();
            for (int index = 0; index < indexes.size(); index++) {//TODO this makes tiles which is bad, should get them off rack
                String letter = String.valueOf(w.charAt(indexes.get(index)));
                tilesToPlay.add(Tile.valueOf(letter));
            }

            board.placeTiles(emptySquareCoordinates, tilesToPlay);
            rack.removeTiles(tilesToPlay);
            ValidTry validTry = submitAndReset(emptySquareCoordinates, tilesToPlay);
            if (validTry != null) validTries.add(validTry);
        }
        return validTries;
    }
}
