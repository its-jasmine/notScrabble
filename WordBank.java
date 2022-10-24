import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
/**
 *
 * @author Victoria Malouf
 * @version Milestone1
 */

public class WordBank {
    private ArrayList<String> validWords;

    public WordBank() {
        this.validWords = new ArrayList<>();
        File file = new File("validWords.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            validWords.add(sc.nextLine());
        }
    }
    /**
     * Checks validity of word
     * @param word to be validated
     * @return true if word is valid, false otherwise
     */
    public boolean isValidWord(String word){
        return validWords.contains(word);
    }


}
