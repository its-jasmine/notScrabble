import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a sequence of consecutive tiles on the game board.
 * @author Jasmine Gad El Hak
 * @version Milestone3
 */
public class Word {
    /* Represents a placed tile in the sequence of consecutive tiles of a word.*/


    /** The word bank for the game */
    public static final WordBank wordBank = new WordBank(); // used for word validation
    /** The linked list containing the sequence of tiles in the word */
    private LinkedList<Square> llWord;

    /**
     * Creates new empty word.
     */
    public Word(){
        llWord = new LinkedList<>();
    }

    /**
     * Adds new node to the front of the word.
     * @param s the square containing the tile to be added to the word
     */
    public void addFirst(Square s){
        llWord.addFirst(s);
    }

    /**
     * Adds new node to the end of the word.
     * @param s the square containing the tile to be added to the word
     */
    public void addLast(Square s){
        llWord.addLast(s);
    }

    /**
     * Returns the length of the word.
     * @return word length
     */
    public int size(){
        return llWord.size();
    }

    /**
     * Validates whether all the words are in wordBank.
     * @param words the words to validate
     * @return true if all words are valid, false otherwise
     */
    public static boolean areValidWords(List<Word> words) {
        for (Word w : words) {
            if (!w.isValidWord()) return false;
        }
        return true;
    }

    /**
     * Validates whether word is in the game's word bank.
     * @return true if word is valid, false otherwise
     */
    private boolean isValidWord() {
        boolean valid = wordBank.isValidWord(this.toString());
        if (!valid) resetBlankTiles();
        return valid;
    }

    /**
     * Calculates the score for all the words in the list.
     * @param words the list of words
     * @return total score
     */
    public static int scoreWords(List<Word> words) {
        int score = 0;
        for (Word w : words) {
            score += w.scoreWord();
        }
        return score;
    }
    /**
     * Calculates the score of the word.
     * @return word score
     */
    private int scoreWord(){
        int score = 0;
        int wordMultiplier = 1;
        for (Square s : llWord) {
            if (s.tileWasPlacedPreviously()) score += s.getTile().getValue(); // we don't want multipliers to apply for previously played tiles
            else{
                score += s.getTile().getValue() * s.getType().letterMultiplier;
                wordMultiplier *= s.getType().wordMultiplier;
            }
        }
        return score * wordMultiplier;
    }

    /**
     * Resets blank tiles in a word if blank tile was placed this turn.
     */
    public void resetBlankTiles() {
        for (Square s: llWord) {
            if (s.getTile() instanceof BlankTile){
                if (!s.tileWasPlacedPreviously()) {  // Will reset the tile if it was played this turn
                    ((BlankTile) s.getTile()).resetLetter();
                }
            }
        }
    }

    /**
     * Returns string representation of word.
     * @return string representation of word in all capital letters
     */
    public String toString(){
        // Each tile letter is collected into a single lowercase string
        return llWord.stream().map(square -> square.getTile().toString()).collect(Collectors.joining());
    }
}
