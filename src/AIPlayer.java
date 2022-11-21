import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

/**
 * This class models a computer controlled player that will play the highest scoring word it can.
 * @author Rebecca Elliott *
 */

public class AIPlayer extends Player{
    private static final int NUMBER_TILES_TO_TRY = 20; // to help limit how long the AI's turn is
    private final WordFinder wordFinder;

    public AIPlayer(Board board, Bag bag, int playerNumber) {
        super(board, bag, playerNumber);
        wordFinder = new WordFinder();
    }

    /**
     * Overrides the Player submit method so that multiple words can be tried.
     * @return the status of the game once the AI has played
     */
    @Override
    public Game.Status submit() {
        if (board.isStartSquareEmpty()) return Game.Status.RUNNING; //TODO handle AI goes first

        ArrayList<Coordinate> prevPlayed = new ArrayList<>(board.getPreviouslyPlayed());
        shuffle(prevPlayed); // some systems will return hash sets in the same order every time

        ArrayList<ValidTry> validTries = new ArrayList<>();
        tryToMakeWords(prevPlayed, validTries);
        if (validTries.size() == 0) return Game.Status.RUNNING; // couldn't make any valid word, passing

        ValidTry bestTry = Collections.max(validTries);
        ArrayList<Tile> playTiles = bestTry.getTilesToPlay();
        rack.removeTiles(playTiles);
        board.placeTiles(bestTry.getWhereToPlayTiles(),playTiles);
        this.addToScore(board.submit());
        System.out.println(bestTry.getScore());
        return this.endTurn();
    }

    /**
     * Submits a word to see if all the words it forms are also valid. If they are all valid, creates a ValidTry. Resets the turn so other words can be tried.
     * @param emptySquareCoordinates Where to play tiles
     * @param tilesToPlay The tiles to play
     * @return a ValidTry if submit was greater than or equal to zero, null otherwise
     */
    private ValidTry submitAndReset(ArrayList<Coordinate> emptySquareCoordinates, ArrayList<Tile> tilesToPlay) {
        int score = board.submit();
        resetTurn();
        if (score >= 0) {
            return new ValidTry(score, tilesToPlay,emptySquareCoordinates);
        }
        return null;
    }

    /**
     * Tries to make words linked to the previously placed tiles.
     *
     * @param prevPlayed tiles already on board
     * @param validTries list of tries that were valid
     */
    private void tryToMakeWords(ArrayList<Coordinate> prevPlayed, ArrayList<ValidTry> validTries){
        for (int i = 0; i < min(prevPlayed.size(), NUMBER_TILES_TO_TRY); i++) {
            tryTileSpacesBelow(rack.getNumTiles(), prevPlayed.get(i), validTries);
        }
    }

    /**
     * Tries to make words with empty squares up to the number of tiles on the rack. Also uses previously played tiles above and below the empty squares
     *
     * @param numTilesOnRack the number of tiles on the AI's rack
     * @param coordinate     the coordinate to start looking from
     * @param validTries list of tries that were valid
     */
    private void tryTileSpacesBelow(int numTilesOnRack, Coordinate coordinate, ArrayList<ValidTry> validTries) {
        StringBuilder wordFormat = new StringBuilder();
        ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        findTilesAboveAndCenter(coordinate,boardTilesAboveAndCenter, wordFormat);

        Coordinate coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        if (coordinateBelow == null) return; // if true found edge of board

        for (int j = 1; j <= numTilesOnRack; j++) {
            coordinateBelow = findAnEmptyAndTilesBelow(coordinateBelow, emptySquareCoordinates, wordFormat,boardTilesBelow);

            String sWordFormat = wordFormat.toString();
            ArrayList<String> words = new ArrayList<>(wordFinder.findWord(allNeededTiles(boardTilesBelow, boardTilesAboveAndCenter), sWordFormat));

            findValidTries(words, sWordFormat, emptySquareCoordinates, validTries);
            if (coordinateBelow == null) break; // edge of board was found
        }
    }

    /**
     * Puts all the tiles needed for wordFinder in one list. Needed tiles are previously played tiles that will be used and tiles from the rack that can be used.
     * @param boardTilesBelow tiles on the board that will be used
     * @param boardTilesAboveAndCenter tiles on the board that will be used
     * @return list of all the needed tiles
     */
    private ArrayList<Tile> allNeededTiles(ArrayList<Tile> boardTilesBelow, ArrayList<Tile> boardTilesAboveAndCenter) {
        ArrayList<Tile> allTiles = new ArrayList<>(rack.getTilesList());
        allTiles.addAll(boardTilesBelow);
        allTiles.addAll(boardTilesAboveAndCenter);
        return allTiles;
    }

    /**
     * Finds the consecutive previously played tiles that are before the given tile as well as the given tile.
     * @param centerCoordinate start location
     * @param boardTilesAboveAndCenter list of board tiles to add to
     * @param wordFormat format that shows where the empties and previously played tiles are
     */
    private void findTilesAboveAndCenter(Coordinate centerCoordinate, ArrayList<Tile> boardTilesAboveAndCenter, StringBuilder wordFormat) {
        if (centerCoordinate == null || board.getSquareTile(centerCoordinate) == null) return;
        wordFormat.reverse(); // so we can append to the front, call again before returning

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

    /**
     * Finds the consecutive previously played tiles that are after the given tile as well as the given tile.
     * @param startSearchingCoordinate start location
     * @param boardTilesBelowAndCenter list of board tiles to add to
     * @param wordFormat format that shows where the empties and previously played tiles are
     * @return first empty square coordinate or null if at edge of board
     */
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

    /**
     * Finds the consecutive previously played tiles that are after the given tile as well as the given tile.
     * Then finds one empty square. If the square after that is not also empty the next set of consecutive tiles is
     * found as well.
     * @param startSearchingCoordinate start location
     * @param emptySquareCoordinates keeps track of empty squares that will be used
     * @param wordFormat format that shows where the empties and previously played tiles are
     * @param boardTilesBelow list of board tiles to add to
     * @return second found empty square coordinate or null if at edge of board
     */

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

    /**
     * Tries all the words on the board to see which will return a valid score.
     * @param words list of words that are valid on their own
     * @param wordFormat format that shows where the empties and previously played tiles are
     * @param emptySquareCoordinates coordinates of the empty squares to be used
     */
    private void findValidTries(ArrayList<String> words, String wordFormat, ArrayList<Coordinate> emptySquareCoordinates, ArrayList<ValidTry> validTries) {
        for (String w : words) {
            ArrayList<Integer> indexes = new ArrayList<>(); // where the empties are in the string
            for (int cIndex = 0; cIndex < wordFormat.length(); cIndex++) {
                if (wordFormat.charAt(cIndex) == '.') indexes.add(cIndex);
            }

            ArrayList<Tile> tilesToPlay = new ArrayList<>();
            for (int index = 0; index < indexes.size(); index++) {//TODO this makes tiles which is bad, should get them off rack
                String letter = String.valueOf(w.charAt(indexes.get(index)));
                tilesToPlay.add(Tile.valueOf(letter));
            }

            board.placeTiles(emptySquareCoordinates, tilesToPlay);
            rack.removeTiles(tilesToPlay); // tried tiles will be returned in a call to the turnRest method
            ValidTry validTry = submitAndReset(emptySquareCoordinates, tilesToPlay);
            if (validTry != null) validTries.add(validTry);
        }
    }
}
