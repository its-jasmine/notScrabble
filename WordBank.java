import java.io.FileNotFoundException;
import java.util.HashSet;
import java.io.File;
import java.util.Scanner;
/**
 * This class is part of the "Scrabble" application.
 *
 * This class stores words from "validWords.txt", which contains a list
 * of 10 000 valid English words from https://www.mit.edu/~ecprice/wordlist.10000.
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
            sc = new Scanner(new File("validWords.txt"));
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
        return validWords.contains(word.toLowerCase());
    }

}
