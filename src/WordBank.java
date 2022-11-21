import java.io.FileNotFoundException;
import java.util.HashSet;
import java.io.File;
import java.util.Scanner;
/**
 * This class is part of the "Scrabble" application.
 *
 * This class stores words from "dictionary.txt", which contains a list
 * of Scrabble words from https://github.com/redbo/scrabble/blob/master/dictionary.txt.
 * This class can confirm if an input String is a valid word.
 *
 * @author Victoria Malouf
 * @version Milestone1
 */

public class WordBank {
    private HashSet<String> validWords;

    /**
     * Create a WordBank object.
     */
    public WordBank() {
        this.validWords = new HashSet<>();
        Scanner sc;
        try {
            sc = new Scanner(new File("dictionary.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            validWords.add(sc.nextLine());
        }
    }
    /**
     * Checks the validity of a word.
     * @param word to be validated
     * @return true if word is valid, false otherwise
     */
    public boolean isValidWord(String word){
        return validWords.contains(word);
    }

}
