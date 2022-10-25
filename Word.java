import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a sequence of consecutive tiles on the game board.
 * @author Jasmine Gad El Hak
 * @version Milestone1
 */
public class Word {
    /* Represents a placed tile in the sequence of consecutive tiles of a word.*/
    private class Node {
        /** The tile in the word */
        public Tile tile;
        /** The type of Square the tile is placed on */
        public Square.Type type;

        /** Creates new node.
         * @param tile the tile in the word
         * @param type the type of Square the tile is placed on
         */
        public Node(Tile tile, Square.Type type) {
            this.tile = tile;
            this.type = type;
        }
    }
    /** The word bank for the game */
    public static final WordBank wordBank = new WordBank(); // used for word validation
    /** The linked list containing the sequence of tiles in the word */
    private LinkedList<Node> llWord;

    /**
     * Creates new empty word.
     */
    public Word(){
        llWord = new LinkedList<>();
    }

    /**
     * Adds new node to word.
     * @param tile the tile to be added to the word
     * @param type the type of Square the tile is placed on
     */
    public void addNode(Tile tile, Square.Type type){
        llWord.add(new Node(tile, type));
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
        return wordBank.isValidWord(this.toString());
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
        for (Node n : llWord) {
            score += n.tile.value;
        }
        return score;
    }

    /**
     * Returns string representation of word.
     * @return string representation of word in all capital letters
     */
    public String toString(){
        // Each tile letter is collected into a single lowercase string
        return llWord.stream().map(node -> node.tile.toString()).collect(Collectors.joining());
    }
}
