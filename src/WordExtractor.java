import java.util.ArrayList;
import java.util.List;

/**
 * A class for getting newly crated potential words from a board.
 * @author  Rebecca Elliott
 */

public class WordExtractor {

    private final Board board;

    public WordExtractor(Board board) {
        this.board = board;
    }

    /**
     * Gets all the potential words that were created this turn.
     * @param tilesPlayed a sorted list of the tile coordinates played this turn, coordinates are confirmed in a line
     * @return list of potential words created this turn
     */
    public ArrayList<Word> getWordsCreated(List<Coordinate> tilesPlayed, BoardValidator.Direction direction) {
        ArrayList<Word> words = new ArrayList<>();

        if (direction == BoardValidator.Direction.HORIZONTAL) {
            // get word played, possibly extending previously played word
            Word word = getHorizontalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word); // to be counted as a word it must be at least two letters long

            // get any newly formed vertical words
            for (Coordinate c : tilesPlayed) {
                word = getVerticalWord(c);
                if (word.size() > 1) words.add(word);
            }

        } else if (direction == BoardValidator.Direction.VERTICAL) {
            // get word played, possibly extending previously played word
            Word word = getVerticalWord(tilesPlayed.get(0));
            if (word.size() > 1) words.add(word);

            // get any newly formed horizontal words
            for (Coordinate c : tilesPlayed) {
                word = getHorizontalWord(c);
                if (word.size() > 1) words.add(word);
            }
        }
        return words; // this can be an empty ArrayList in the scenario a player plays one Tile to start the game
    }

    /**
     * Gets any potential horizontal word, can be one letter long at this point.
     * @param startSearch the location on the board to search from
     * @return potential word that was created
     */
    private Word getHorizontalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateToLeft = startSearch.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);

        // adds letter to the front of the word
        while (coordinateToLeft != null && !board.getSquare(coordinateToLeft).isEmpty()) { // if coordinateToLeft is null we are at the left edge of the board
            word.addFirst(board.getSquareTile(coordinateToLeft), board.getSquareType(coordinateToLeft));
            coordinateToLeft = coordinateToLeft.getAdjacentCoordinate(Coordinate.Adjacent.LEFT);
        }

        Coordinate coordinateToRight = startSearch;
        //adds letters to the end of the word
        while (coordinateToRight != null && !board.getSquare(coordinateToRight).isEmpty()) {  // if coordinateToRight is null we are at the Right edge of the board
            word.addLast(board.getSquareTile(coordinateToRight), board.getSquareType(coordinateToRight));
            coordinateToRight = coordinateToRight.getAdjacentCoordinate(Coordinate.Adjacent.RIGHT);
        }

        return word;
    }

    /**
     * Gets any potential vertical word, can be one letter long at this point.
     * @param startSearch the location on the board to search from
     * @return potential word that was created
     */
    private Word getVerticalWord(Coordinate startSearch) {
        Word word = new Word();
        Coordinate coordinateAbove = startSearch.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);

        // adds letter to the front of the word
        while (coordinateAbove != null && !board.getSquare(coordinateAbove).isEmpty()) { // if coordinateAbove is null we are at the top of the board
            word.addFirst(board.getSquareTile(coordinateAbove), board.getSquareType(coordinateAbove));
            coordinateAbove = coordinateAbove.getAdjacentCoordinate(Coordinate.Adjacent.ABOVE);
        }

        Coordinate coordinateBelow = startSearch;
        //adds letters to the end of the word
        while (coordinateBelow != null && !board.getSquare(coordinateBelow).isEmpty()) { // if coordinateBelow is null we are at the bottom of the board
            word.addLast(board.getSquareTile(coordinateBelow), board.getSquareType(coordinateBelow));
            coordinateBelow = coordinateBelow.getAdjacentCoordinate(Coordinate.Adjacent.BELOW);
        }

        return word;
    }
}
