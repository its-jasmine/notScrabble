import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

public class WordFinder {

    HashMap<Character, Integer> letterMap;
    ArrayList<String> possibleWords;

    private WordBank wordBank;
    public WordFinder() {
        letterMap = new HashMap<>();
        wordBank = new WordBank();
        possibleWords = new ArrayList<>();
    }

    public HashMap<Character, Integer> getLetterMap() {
        return letterMap;
    }

    public void addletter(List<Tile> tileList) {
        for (Tile t : tileList){
            letterMap.merge(t.letter.toUpperCase().charAt(0), 1,Integer::sum);
        }
    }

    public ArrayList<String> findWord(ArrayList<Tile> tileArrayList, String format){
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

        WordFinder wf = new WordFinder();
        wf.addletter(tileArrayList);
        System.out.println(wf.getLetterMap());
        ArrayList<String> words = new ArrayList<>();
        words = wf.findWord(tileArrayList,".....");
        System.out.println(words);
    }
}
