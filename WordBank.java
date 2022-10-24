import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

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
        return validWords.contains(word.toLowerCase());
    }

    public static void main(String[] args) {
        WordBank wordbank = new WordBank();
        int p = 0;
        int f = 0;
        // Valid words
        if (wordbank.isValidWord("adsl")) p++; else f++;
        if (wordbank.isValidWord("thumbzilla")) p++; else f++;
        if (wordbank.isValidWord("usgs")) p++; else f++;
        if (wordbank.isValidWord("zshops")) p++; else f++;
        if (wordbank.isValidWord("zdnet")) p++; else f++;
        // Invalid words
        if (!wordbank.isValidWord("zxc")) p++; else f++;
        if (!wordbank.isValidWord("hignfbh")) p++; else f++;
        if (!wordbank.isValidWord("silents")) p++; else f++;
        if (!wordbank.isValidWord("orangez")) p++; else f++;
        if (!wordbank.isValidWord("breuisn")) p++; else f++;

        System.out.print("PASSED: " + p + "\nFAILED: " + f);
    }

}
