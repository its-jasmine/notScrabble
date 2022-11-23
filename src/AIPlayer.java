import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;

/**
 * This class models a computer controlled player that will play the highest scoring word it can.
 * @author Rebecca Elliott *
 */

public class AIPlayer extends Player{
    private static final int NUMBER_TILES_TO_TRY = 10; // to help limit how long the AI's turn is and make it easier to play against
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

        ArrayList<Coordinate> prevPlayed = new ArrayList<>(board.getPreviouslyPlayed());
        shuffle(prevPlayed); // some systems will return hash sets in the same order every time
        if (prevPlayed.size() == 0) {
            prevPlayed.add(new Coordinate(Board.START_COLUMN, Board.START_ROW));
        }


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
            trySpaces(prevPlayed.get(i), validTries, BoardValidator.Direction.VERTICAL);
            trySpaces(prevPlayed.get(i), validTries, BoardValidator.Direction.HORIZONTAL);
        }
    }

    /**
     * Tries to make words with empty squares up to the number of tiles on the rack. Also uses previously played tiles above and below the empty squares
     *
     * @param coordinate     the coordinate to start looking from
     * @param validTries     list of tries that were valid
     * @param direction the direction to try words in
     */
    private void trySpaces(Coordinate coordinate, ArrayList<ValidTry> validTries, BoardValidator.Direction direction) {
        Coordinate.Adjacent before;
        Coordinate.Adjacent after;
        if (direction == BoardValidator.Direction.HORIZONTAL) {
            before = Coordinate.Adjacent.LEFT;
            after = Coordinate.Adjacent.RIGHT;
        } else {
            before = Coordinate.Adjacent.ABOVE;
            after = Coordinate.Adjacent.BELOW;
        }



        int numTilesOnRack = rack.getNumTiles();
        int emptySpacesBefore;
        for (emptySpacesBefore = 0; emptySpacesBefore <= numTilesOnRack; emptySpacesBefore++) {

            StringBuilder wordFormat = new StringBuilder();
            ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>(); // order matters
            ArrayList<Tile> boardTiles = new ArrayList<>(); // order doesn't matter


            Coordinate coordinateBefore = findContiguousTiles(coordinate, boardTiles, wordFormat, before);
            for (int numEmpties = 0; numEmpties < emptySpacesBefore; numEmpties++) {
                coordinateBefore = findAnEmptyAndTiles(coordinateBefore, emptySquareCoordinates, wordFormat, boardTiles, before);
                if (coordinateBefore == null) break; // if true found edge of board
            }

            Coordinate coordinateAfter = coordinate.getAdjacentCoordinate(after);
            coordinateAfter = findContiguousTiles(coordinateAfter, boardTiles, wordFormat, after);
            if (coordinateAfter == null) return; // if true found edge of board

            int tileDif = numTilesOnRack - emptySpacesBefore;
            for (int j = 0; j <= tileDif; j++) {
                if (emptySpacesBefore == 0 && j == 0) continue; // we need to place at least one tile
                coordinateAfter = findAnEmptyAndTiles(coordinateAfter, emptySquareCoordinates, wordFormat, boardTiles, after);

                String sWordFormat = wordFormat.toString();
                ArrayList<String> words = new ArrayList<>(wordFinder.findWord(allNeededTiles(boardTiles), sWordFormat));

                findValidTries(words, sWordFormat, emptySquareCoordinates, validTries);
                if (coordinateAfter == null) break; // edge of board was found
            }
        }
    }

    /**
     * Puts all the tiles needed for wordFinder in one list. Needed tiles are previously played tiles that will be used and tiles from the rack that can be used.
     * @param boardTiles tiles on the board that will be used
     * @return list of all the needed tiles
     */
    private ArrayList<Tile> allNeededTiles(ArrayList<Tile> boardTiles) {
        ArrayList<Tile> allTiles = new ArrayList<>(rack.getTilesList());
        allTiles.addAll(boardTiles);
        return allTiles;
    }


    /**
     * Finds the consecutive previously played tiles in the given direction. This includes the start coordinate.     *
     *
     * @param startSearchingCoordinate start location
     * @param boardTiles list of board tiles to add to
     * @param wordFormat               format that shows where the empties and previously played tiles are
     * @param adjacentDirection direction to check
     * @return first empty square coordinate or null if at edge of board
     */
    private Coordinate findContiguousTiles(Coordinate startSearchingCoordinate, ArrayList<Tile> boardTiles, StringBuilder wordFormat, Coordinate.Adjacent adjacentDirection) {
        if (startSearchingCoordinate == null)  return null;
        if (board.getSquareTile(startSearchingCoordinate) == null) return startSearchingCoordinate;

        boolean isBEFORE = adjacentDirection == Coordinate.Adjacent.ABOVE || adjacentDirection == Coordinate.Adjacent.LEFT;
        if (isBEFORE) wordFormat.reverse(); // so we append to the front

        Coordinate nextCoordinate = startSearchingCoordinate; // just for naming
        Tile nextTile = board.getSquareTile(nextCoordinate);
        while (nextTile != null) {
            wordFormat.append(nextTile);
            boardTiles.add(nextTile);
            nextCoordinate = nextCoordinate.getAdjacentCoordinate(adjacentDirection);
            if (nextCoordinate == null) {
                // the edge of board was found adjacentDirection previously played tiles but before an empty square was found
                if (isBEFORE) wordFormat.reverse(); // return to normal order
                return null; }
            nextTile = board.getSquareTile(nextCoordinate);
        }
        if (isBEFORE) wordFormat.reverse(); // return to normal order
        return nextCoordinate;
    }

    /**
     * Adds the empty square coordinate and if the next square is not also empty the next set of consecutive tiles is
     * found as well.
     *
     * @param startSearchingCoordinate coordinate of an empty square
     * @param emptySquareCoordinates   keeps track of empty squares that will be used
     * @param wordFormat               format that shows where the empties and previously played tiles are
     * @param boardTiles          list of board tiles to add to
     * @param direction direction to check
     * @return second found empty square coordinate or null if at edge of board
     */
    private Coordinate findAnEmptyAndTiles(Coordinate startSearchingCoordinate, ArrayList<Coordinate> emptySquareCoordinates, StringBuilder wordFormat, ArrayList<Tile> boardTiles, Coordinate.Adjacent direction) {
        if (startSearchingCoordinate == null) return null;

        boolean isBEFORE = direction == Coordinate.Adjacent.ABOVE || direction == Coordinate.Adjacent.LEFT;
        if (isBEFORE) {
            wordFormat.reverse(); // so we append to the front
            wordFormat.append(".");
            wordFormat.reverse();
            emptySquareCoordinates.add(0, startSearchingCoordinate);
        } else {
            wordFormat.append(".");
            emptySquareCoordinates.add(startSearchingCoordinate);
        }
        Coordinate nextCoordinate = startSearchingCoordinate.getAdjacentCoordinate(direction);

        if (nextCoordinate == null) {
            // the edge of board was found direction the empty square was found
            return null;
        }

        Tile nextTile = board.getSquareTile(nextCoordinate);
        if (nextTile != null) {
            nextCoordinate = findContiguousTiles(nextCoordinate, boardTiles, wordFormat, direction);
        }
        return nextCoordinate;
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
                        System.out.println("couldn't get blank from rack. Letter: " + letter + " Rack: " + rack);
                        return;
                    }
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
