import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Test class for WordBank.
 * @author Jasmine Gad El Hak
 * @version Milestone2
 */
public class WordBankTest {

    @Test
    public void isValidWord() {
        WordBank wordBank = new WordBank();
        assertTrue(wordBank.isValidWord("AA"));
        assertFalse(wordBank.isValidWord("AAA"));
        assertTrue(wordBank.isValidWord("HAPPY"));
        assertTrue(wordBank.isValidWord("SCRABBLE"));

        assertFalse(wordBank.isValidWord("HAPEE"));
        assertFalse(wordBank.isValidWord("BABAK"));
        assertFalse(wordBank.isValidWord("SYSC"));
        assertFalse(wordBank.isValidWord("1234"));
        assertFalse(wordBank.isValidWord("!@$%"));
        assertFalse(wordBank.isValidWord(""));
    }
}