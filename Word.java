import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Word {
    private class Node {
        public Tile tile;
        public Square.Type type;


        public Node(Tile tile, Square.Type type) {
            this.tile = tile;
            this.type = type;
        }
    }
    public static final WordBank wordBank = new WordBank();

    private LinkedList<Node> llWord;
    public Word(){
        llWord = new LinkedList<Node>();
    }
    public Word(LinkedList<Node> llWord){
        this.llWord = llWord;
    }
    public int size(){
        return llWord.size();
    }

    public void addNode(Tile tile, Square.Type type){
        llWord.add(new Node(tile, type));
    }

    /**
     * checks if the words are in wordBank
     *
     * @param words to check
     * @return true if all are valid, false otherwise
     */
    public static boolean areValidWords(List<Word> words) {
        for (Word w : words) {
            if (!w.isValidWord()) return false;
        }
        return true;
    }

    private boolean isValidWord() {
        return wordBank.isValidWord(this.toString());
    }

    /* Calculates the score for all the words created this turn
     * @param words a list of LinkList where each node has the Tile, and square type
     * @return total score
     */
    public static int scoreWords(List<Word> words) {
        int score = 0;
        for (Word w : words) {
            score += w.scoreWord();
        }
        return score;
    }
    private int scoreWord(){
        int score = 0;
        for (Node n : llWord) {
            score += n.tile.value;
        }
        return score;
    }

    /*
    Returns string representation of word in all lowercase.
    @return String representation of Word.
     */
    public String toString(){
        // Each tile letter is collected into a single lowercase string
        return llWord.stream().map(tile -> tile.toString()).collect(Collectors.joining()).toLowerCase();
    }
}
