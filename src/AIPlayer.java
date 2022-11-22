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
    /** A Wordfinder to find words */
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
        if (score >= 0) {
            ValidTry validTry = new ValidTry(score, tilesToPlay, emptySquareCoordinates);
            resetTurn(); // must reset after ValidTry creation in case a blank was used
            return validTry;
        }
        resetTurn();
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
            trySpaces(rack.getNumTiles(), prevPlayed.get(i), validTries, BoardValidator.Direction.VERTICAL);
            trySpaces(rack.getNumTiles(), prevPlayed.get(i), validTries, BoardValidator.Direction.HORIZONTAL);
        }
    }

    /**
     * Tries to make words with empty squares up to the number of tiles on the rack. Also uses previously played tiles above and below the empty squares
     *
     * @param numTilesOnRack the number of tiles on the AI's rack
     * @param coordinate     the coordinate to start looking from
     * @param validTries     list of tries that were valid
     * @param direction the direction to try words in
     */
    private void trySpaces(int numTilesOnRack, Coordinate coordinate, ArrayList<ValidTry> validTries, BoardValidator.Direction direction) {
        Coordinate.Adjacent before;
        Coordinate.Adjacent after;
        if (direction == BoardValidator.Direction.HORIZONTAL) {
            before = Coordinate.Adjacent.LEFT;
            after = Coordinate.Adjacent.RIGHT;
        } else {
            before = Coordinate.Adjacent.ABOVE;
            after = Coordinate.Adjacent.BELOW;
        }

        StringBuilder wordFormat = new StringBuilder();
        ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        findTilesBeforeAndCenter(coordinate,boardTilesAboveAndCenter, wordFormat, before);

        Coordinate coordinateAfter = coordinate.getAdjacentCoordinate(after);
        if (coordinateAfter == null) return; // if true found edge of board

        for (int j = 1; j <= numTilesOnRack; j++) {
            coordinateAfter = findAnEmptyAndTilesAfter(coordinateAfter, emptySquareCoordinates, wordFormat,boardTilesBelow, after);

            String sWordFormat = wordFormat.toString();
            ArrayList<String> words = new ArrayList<>(wordFinder.findWord(allNeededTiles(boardTilesBelow, boardTilesAboveAndCenter), sWordFormat));

            findValidTries(words, sWordFormat, emptySquareCoordinates, validTries);
            if (coordinateAfter == null) break; // edge of board was found
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
     *
     * @param centerCoordinate         start location
     * @param boardTilesBeforeAndCenter list of board tiles to add to
     * @param wordFormat               format that shows where the empties and previously played tiles are
     * @param before    direction to check can be ABOVE or LEFT
     */
    private void findTilesBeforeAndCenter(Coordinate centerCoordinate, ArrayList<Tile> boardTilesBeforeAndCenter, StringBuilder wordFormat, Coordinate.Adjacent before) {
        if (centerCoordinate == null || board.getSquareTile(centerCoordinate) == null) return;
        wordFormat.reverse(); // so we can append to the front, call again before returning

        wordFormat.append(board.getSquareTile(centerCoordinate).toString());
        boardTilesBeforeAndCenter.add(board.getSquareTile(centerCoordinate));

        Coordinate coordinateBefore = centerCoordinate.getAdjacentCoordinate(before);
        if (coordinateBefore == null) return;// check for edge of board
        Tile tileBefore = board.getSquareTile(coordinateBefore);
        while (tileBefore != null) {
            wordFormat.append(tileBefore);
            boardTilesBeforeAndCenter.add(tileBefore);
            coordinateBefore = coordinateBefore.getAdjacentCoordinate(before);
            if (coordinateBefore == null) {
                wordFormat.reverse(); // so letters will be appended to front
                return; // check for edge of board
            }
            tileBefore = board.getSquareTile(coordinateBefore);

        }
        wordFormat.reverse(); // so letters will be appended to front
    }

    /**
     * Finds the consecutive previously played tiles that are after the given tile as well as the given tile.
     *
     * @param startSearchingCoordinate start location
     * @param boardTilesAfterAndCenter list of board tiles to add to
     * @param wordFormat               format that shows where the empties and previously played tiles are
     * @param after direction to check can be BELOW or RIGHT
     * @return first empty square coordinate or null if at edge of board
     */
    private Coordinate findTilesAfter(Coordinate startSearchingCoordinate, ArrayList<Tile> boardTilesAfterAndCenter, StringBuilder wordFormat, Coordinate.Adjacent after) {
        if (startSearchingCoordinate == null)  return null;
        if (board.getSquareTile(startSearchingCoordinate) == null) return startSearchingCoordinate;

        Coordinate coordinateAfter = startSearchingCoordinate; // just for naming
        Tile tileAfter = board.getSquareTile(coordinateAfter);
        while (tileAfter != null) {
            wordFormat.append(tileAfter);
            boardTilesAfterAndCenter.add(tileAfter);
            coordinateAfter = coordinateAfter.getAdjacentCoordinate(after);
            if (coordinateAfter == null) return null; // the edge of board was found after previously played tiles but before an empty square was found
            tileAfter = board.getSquareTile(coordinateAfter);
        }
        return coordinateAfter;
    }

    /**
     * Finds the consecutive previously played tiles that are after the given tile as well as the given tile.
     * Then finds one empty square. If the square after that is not also empty the next set of consecutive tiles is
     * found as well.
     *
     * @param startSearchingCoordinate start location
     * @param emptySquareCoordinates   keeps track of empty squares that will be used
     * @param wordFormat               format that shows where the empties and previously played tiles are
     * @param boardTilesBelow          list of board tiles to add to
     * @param after direction to check can be BELOW or RIGHT
     * @return second found empty square coordinate or null if at edge of board
     */
    private Coordinate findAnEmptyAndTilesAfter(Coordinate startSearchingCoordinate, ArrayList<Coordinate> emptySquareCoordinates, StringBuilder wordFormat, ArrayList<Tile> boardTilesBelow, Coordinate.Adjacent after) {
        if (startSearchingCoordinate == null) return null;

        Coordinate coordinateAfter = findTilesAfter(startSearchingCoordinate, boardTilesBelow, wordFormat, after); // gets consecutive tiles, no empties

        if (coordinateAfter == null) {
            // the edge of board was found after previously played tiles but before an empty square was found
            return null;
        }

        wordFormat.append(".");
        emptySquareCoordinates.add(coordinateAfter);
        coordinateAfter = coordinateAfter.getAdjacentCoordinate(after);

        if (coordinateAfter == null) {
            // the edge of board was found after the empty square was found
            return null;
        }

        Tile tileAfter = board.getSquareTile(coordinateAfter);
        if (tileAfter != null) {
            coordinateAfter = findTilesAfter(coordinateAfter, boardTilesBelow, wordFormat, after);
        }
        return coordinateAfter;
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
            for (int index = 0; index < indexes.size(); index++) {
                String letter = String.valueOf(w.charAt(indexes.get(index)));
                Tile tile = rack.removeTileFromRack(LetterTile.valueOf(letter));
                if (tile == null) {
                    Tile blankTile = rack.removeTileFromRack(new BlankTile());
                    if (blankTile == null) {
                        System.out.println("couldn't get blank from rack");
                        return;
                    }
                    System.out.println("USED BLANK");
                    ((BlankTile)blankTile).setLetter(LetterTile.valueOf(letter));
                    tilesToPlay.add(blankTile);
                } else tilesToPlay.add(tile);
            }

            board.placeTiles(emptySquareCoordinates, tilesToPlay);
            ValidTry validTry = submitAndReset(emptySquareCoordinates, tilesToPlay);
            if (validTry != null) validTries.add(validTry);
        }
    }
}
