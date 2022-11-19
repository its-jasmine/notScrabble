import java.util.*;
import java.util.regex.*;

/**
 * Finds words for the AI to place on the board using tiles and a format
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

    /**
     * Gets the letterMap hashmap
     * @return a hashmap of characters keys with their occurrence value
     */
    public HashMap<Character, Integer> getLetterMap() {
        return letterMap;
    }

    /**
     * clears the letterMap and adds new letters to the hashmap and their occurrences
     * @param tileList the list of tiles to add to the map
     */
    public void addLettersToMap(List<Tile> tileList) { //public for testing findWord()
        letterMap.clear();
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
        possibleWords.clear(); //reset the list of possible words
        addLettersToMap(tileArrayList); //reset and add the new letters
        Pattern wordPattern = Pattern.compile(format.toUpperCase()); //gets a regex pattern using the format
        //Matcher matcher;
        for (String word : wordBank.getValidWords()){
            boolean sameVal = true;
            HashMap<Character, Integer> wordMap = new HashMap<>();
            if (word.length() != format.length()){ //checks if the word is the same length as the format
                continue;
            }
            if (!wordPattern.matcher(word).find()){ // checks if letter at format place is not there go next
                continue;
            }
            for (int i = 0; i < word.length(); i++){ // put word in a hashmap with frequency
                wordMap.merge(word.toUpperCase().charAt(i), 1,Integer::sum);
            }
            for (Character key : wordMap.keySet()){ // checks if the letterMap has every letter from the word.
                if (letterMap.get(key) == null | (letterMap.get(key) != null && wordMap.get(key) > letterMap.get(key))){
                    sameVal = false;
                }
            }
            if (sameVal) {
                possibleWords.add(word);
            }
        }
        return possibleWords;
    }
}
