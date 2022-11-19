import java.util.*;
import java.util.regex.*;

/**
 * Finds words for the AI to place on the board
 */
public class WordFinder {

    /** Hashmap containing letters and their occurrences to find a word */
    private HashMap<Character, Integer> letterMap;
    /** list of possible words to pass to the AI for placement */
    private ArrayList<String> possibleWords;

    /** Bank of playable words */
    private WordBank wordBank;
    public WordFinder() {
        letterMap = new HashMap<>();
        wordBank = new WordBank();
        possibleWords = new ArrayList<>();
    }

    public HashMap<Character, Integer> getLetterMap() {
        return letterMap;
    }

    /**
     * adds letters to the hashmap and their occurrences
     * @param tileList the list of tiles to add to the map
     */
    public void addLettersToMap(List<Tile> tileList) { //public for testing findWord()
        for (Tile t : tileList){
            letterMap.merge(t.letter.toUpperCase().charAt(0), 1,Integer::sum);
        }
    }

    /**
     * finds all the words playable with the specified tiles and format
     * @param tileArrayList list of tiles used to form a word
     * @param format format considering the board tiles' placement
     * @return a list of possible words
     */
    public ArrayList<String> findWord(ArrayList<Tile> tileArrayList, String format){
        addLettersToMap(tileArrayList);
        Pattern wordPattern = Pattern.compile(format.toUpperCase());
        Matcher matcher;
        for (String word : wordBank.getValidWords()){
            boolean sameVal = true;
            HashMap<Character, Integer> wordMap = new HashMap();
            if (word.length() != format.length()){
                continue;
            }
            // if letter at format place is not there go next
            if (!wordPattern.matcher(word).find()){
                continue;
            }
            // put word in a hashmap with frequency
            for (int i = 0; i < word.length(); i++){
                wordMap.merge(word.toUpperCase().charAt(i), 1,Integer::sum);
            }
            //if both hashmaps are equal, add to the possible words list.
            for (Character key : wordMap.keySet()){
                if (wordMap.get(key) != letterMap.get(key)){
                    sameVal = false;
                }
            }
            if (sameVal) {
                possibleWords.add(word);
            }
        }
        return possibleWords;
    }

    public static void main(String[] args) {

        ArrayList<Tile> tileArrayList = new ArrayList<>();
        tileArrayList.add(Tile.R);
        tileArrayList.add(Tile.A);
        tileArrayList.add(Tile.I);
        tileArrayList.add(Tile.S);
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.C);
        tileArrayList.add(Tile.N);
        tileArrayList.add(Tile.N);
        tileArrayList.add(Tile.N);

        WordFinder wf = new WordFinder();
        wf.addLettersToMap(tileArrayList);
        System.out.println(wf.getLetterMap());
        ArrayList<String> words;
        words = wf.findWord(tileArrayList,"A.");
        System.out.println(words);
        System.out.println(words.size());
    }
}
