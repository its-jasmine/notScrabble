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

        AboveXorBelowInfo aboveInfo = findTilesAboveAndCenter(coordinate);
        String wordFormat = aboveInfo.wordFormat;
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>(aboveInfo.boardTilesAboveXorBelowAndCenter);


        Coordinate coordinateBelow = coordinate.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        if (coordinateBelow == null) return validTries; // if true found edge of board

        ArrayList<Coordinate> emptySquareCoordinates = new ArrayList<>();
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        for (int j = 1; j <= numTilesOnRack; j++) {
            SearchingBelowInfo searchingBelowInfo = findAnEmptyAndTilesBelow(coordinateBelow);

            coordinateBelow = searchingBelowInfo.coordinateBelow; // can be null
            if (searchingBelowInfo.emptySquareCoordinate != null) emptySquareCoordinates.add(searchingBelowInfo.emptySquareCoordinate);
            if (searchingBelowInfo.boardTilesBelow != null) boardTilesBelow.addAll(searchingBelowInfo.boardTilesBelow);
            wordFormat += searchingBelowInfo.wordFormat;


            ArrayList<String> words = new ArrayList<>(wordFinder.findWord(allNeededTiles(boardTilesBelow, boardTilesAboveAndCenter), wordFormat));

            validTries.addAll(findValidTries(words, wordFormat, emptySquareCoordinates));
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

    private SearchingBelowInfo findTilesBelow(Coordinate startSearchingCoordinate) {
        Tile tile = board.getSquareTile(startSearchingCoordinate);
        String wordFormat = "";
        if (startSearchingCoordinate == null || tile == null) {
            return new SearchingBelowInfo(wordFormat,null, null, null);
        }
        ArrayList<Tile> boardTilesBelowAndCenter = new ArrayList<>();
        boardTilesBelowAndCenter.add(board.getSquareTile(startSearchingCoordinate));



        Tile tileBelow;
        Coordinate coordinateBelow = startSearchingCoordinate; // just for naming
        tileBelow = board.getSquareTile(coordinateBelow);
        while (tileBelow != null) {
            wordFormat += tileBelow;
            boardTilesBelowAndCenter.add(tileBelow);
            coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
            if (coordinateBelow == null) return new SearchingBelowInfo(wordFormat,null, boardTilesBelowAndCenter, null); // the edge of board was found after previously played tiles but before an empty square was found
            tileBelow = board.getSquareTile(coordinateBelow);
        }
        return new SearchingBelowInfo(wordFormat,null, boardTilesBelowAndCenter, coordinateBelow);
    }

    private AboveXorBelowInfo findTilesAboveAndCenter(Coordinate centerCoordinate) {
        String wordFormat = board.getSquareTile(centerCoordinate).toString();
        ArrayList<Tile> boardTilesAboveAndCenter = new ArrayList<>();
        boardTilesAboveAndCenter.add(board.getSquareTile(centerCoordinate));

        Coordinate coordinateAbove = centerCoordinate.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        if (coordinateAbove == null) return new AboveXorBelowInfo(wordFormat, boardTilesAboveAndCenter);// check for edge of board
        Tile tileAbove = board.getSquareTile(coordinateAbove);
        while (tileAbove != null) {
            wordFormat = tileAbove + wordFormat;
            boardTilesAboveAndCenter.add(tileAbove);
            coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
            if (coordinateAbove == null) return new AboveXorBelowInfo(wordFormat, boardTilesAboveAndCenter); // check for edge of board
            tileAbove = board.getSquareTile(coordinateAbove);

        }
        return new AboveXorBelowInfo(wordFormat, boardTilesAboveAndCenter);
    }



    private SearchingBelowInfo findAnEmptyAndTilesBelow(Coordinate startSearchingCoordinate) {
        String wordFormat = "";
        ArrayList<Tile> boardTilesBelow = new ArrayList<>();

        Coordinate coordinateBelow = startSearchingCoordinate; // just for naming
        if (coordinateBelow == null) return new SearchingBelowInfo(wordFormat,null,null, null); // the edge of board was found
        Tile tileBelow = board.getSquareTile(coordinateBelow);
        if (tileBelow != null) {
            SearchingBelowInfo foundTilesBelow = findTilesBelow(coordinateBelow);
            wordFormat += foundTilesBelow.wordFormat;
            coordinateBelow = foundTilesBelow.coordinateBelow;
        }

        if (coordinateBelow == null) {
            // the edge of board was found after previously played tiles but before an empty square was found
            return new SearchingBelowInfo(wordFormat,null,boardTilesBelow, null);
        }

        wordFormat += ".";
        Coordinate emptySquareCoordinate = coordinateBelow;
        coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);

        if (coordinateBelow == null) {
            // the edge of board was found after the empty square was found
            return new SearchingBelowInfo(wordFormat,emptySquareCoordinate,boardTilesBelow, null);
        }

        tileBelow = board.getSquareTile(coordinateBelow);
        if (tileBelow != null) {
            SearchingBelowInfo foundTilesBelowAfterEmpty = findTilesBelow(coordinateBelow);
            boardTilesBelow.addAll(foundTilesBelowAfterEmpty.boardTilesBelow);
            wordFormat += foundTilesBelowAfterEmpty.wordFormat;
            coordinateBelow = foundTilesBelowAfterEmpty.coordinateBelow;
        }
        return new SearchingBelowInfo(wordFormat,emptySquareCoordinate,boardTilesBelow, coordinateBelow);
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

    private class AboveXorBelowInfo {
        private final String wordFormat;
        private final ArrayList<Tile> boardTilesAboveXorBelowAndCenter;

        public AboveXorBelowInfo(String wordFormat, ArrayList<Tile> boardTilesAboveXorBelowAndCenter) {
            this.wordFormat = wordFormat;
            this.boardTilesAboveXorBelowAndCenter = boardTilesAboveXorBelowAndCenter;
        }
    }

    private class SearchingBelowInfo {
        private final String wordFormat;
        private final Coordinate emptySquareCoordinate;
        private final ArrayList<Tile> boardTilesBelow;
        private final Coordinate coordinateBelow;

        public SearchingBelowInfo(String wordFormat, Coordinate emptySquareCoordinate, ArrayList<Tile> boardTilesBelow, Coordinate coordinateBelow) {
            this.wordFormat = wordFormat;
            this.emptySquareCoordinate = emptySquareCoordinate;
            this.boardTilesBelow = boardTilesBelow;
            this.coordinateBelow = coordinateBelow;
        }
    }
}
