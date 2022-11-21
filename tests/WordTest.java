import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests class Word
 * Scoring of words are expected to have failing unit tests.
 * (i.e., all testScore* tests except for testScorePlain)
 *
 * @author Victoria Malouf
 * @version Milestone2
 */

public class WordTest {
    Word w;
    List<Word> words;

    /**
     * Initiates a new Word object and an ArrayList that will hold Word objects
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        w = new Word();
        words = new ArrayList<>();
    }

    /**
     * Tests that the initial size of an empty word is 0
     */
    @Test
    public void initialSizeIsZero() {
        assertEquals(0, w.size());
    }

    /**
     * Tests that the addFirst method appends Nodes at the beginning of the word
     */
    @Test
    public void testAddFirst() {
        w.addFirst(LetterTile.K, Square.Type.PLAIN);
        w.addFirst(LetterTile.C, Square.Type.PLAIN);
        w.addFirst(LetterTile.A, Square.Type.PLAIN);
        w.addFirst(LetterTile.B, Square.Type.PLAIN);
        assertEquals(4, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    /**
     * Tests that the addLast method appends Nodes at the end of the word
     */
    @Test
    public void testAddLast() {
        w.addLast(LetterTile.C, Square.Type.PLAIN);
        w.addLast(LetterTile.O, Square.Type.PLAIN);
        w.addLast(LetterTile.N, Square.Type.PLAIN);
        w.addLast(LetterTile.S, Square.Type.PLAIN);
        w.addLast(LetterTile.U, Square.Type.PLAIN);
        w.addLast(LetterTile.L, Square.Type.PLAIN);
        w.addLast(LetterTile.T, Square.Type.PLAIN);
        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    /**
     * Tests the addFirst and addLast methods work together
     */
    @Test
    public void testAddFirstAndLast() {
        // Word: "DEVELOP"
        w.addFirst(LetterTile.E, Square.Type.PLAIN);
        w.addLast(LetterTile.L, Square.Type.PLAIN);
        w.addFirst(LetterTile.V, Square.Type.PLAIN);
        w.addLast(LetterTile.O, Square.Type.PLAIN);
        w.addFirst(LetterTile.E, Square.Type.PLAIN);
        w.addLast(LetterTile.P, Square.Type.PLAIN);
        w.addFirst(LetterTile.D, Square.Type.PLAIN);
        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    /**
     * Tests that the areValidWords method returns true when all words are valid
     */
    @Test
    public void testAreValidWords() {
        w.addLast(LetterTile.G, Square.Type.PLAIN);
        w.addLast(LetterTile.R, Square.Type.PLAIN);
        w.addLast(LetterTile.A, Square.Type.PLAIN);
        w.addLast(LetterTile.I, Square.Type.PLAIN);
        w.addLast(LetterTile.N, Square.Type.PLAIN);
        words.add(w);
        Word w2 = new Word();
        w2.addLast(LetterTile.E, Square.Type.PLAIN);
        w2.addLast(LetterTile.V, Square.Type.PLAIN);
        w2.addLast(LetterTile.I, Square.Type.PLAIN);
        w2.addLast(LetterTile.L, Square.Type.PLAIN);
        words.add(w2);
        Word w3 = new Word();
        w3.addLast(LetterTile.F, Square.Type.PLAIN);
        w3.addLast(LetterTile.L, Square.Type.PLAIN);
        w3.addLast(LetterTile.O, Square.Type.PLAIN);
        w3.addLast(LetterTile.O, Square.Type.PLAIN);
        w3.addLast(LetterTile.R, Square.Type.PLAIN);
        words.add(w3);
        assertEquals(true, w.areValidWords(words));
    }

    /**
     * Tests that the areValidWords method returns false when any word is invalid
     */
    @Test
    public void testAreInvalidWords() {
        w.addLast(LetterTile.K, Square.Type.PLAIN);
        w.addLast(LetterTile.A, Square.Type.PLAIN);
        w.addLast(LetterTile.Y, Square.Type.PLAIN);
        words.add(w);
        Word w2 = new Word();
        w2.addLast(LetterTile.L, Square.Type.PLAIN);
        w2.addLast(LetterTile.E, Square.Type.PLAIN);
        w2.addLast(LetterTile.D, Square.Type.PLAIN);
        words.add(w2);
        Word w3 = new Word();
        w3.addLast(LetterTile.M, Square.Type.PLAIN);
        w3.addLast(LetterTile.S, Square.Type.PLAIN);
        w3.addLast(LetterTile.N, Square.Type.PLAIN);
        w3.addLast(LetterTile.E, Square.Type.PLAIN);
        words.add(w3);
        assertFalse(w.areValidWords(words));
    }

    /**
     * Tests that scoreWords for PLAIN Square Types work as expected
     */
    @Test
    public void testScorePlain() {
        // Word: "LIST" L=1, I=1, S=1, T=1 --> score = 4
        w.addLast(LetterTile.L, Square.Type.PLAIN);
        w.addLast(LetterTile.I, Square.Type.PLAIN);
        w.addLast(LetterTile.S, Square.Type.PLAIN);
        w.addLast(LetterTile.T, Square.Type.PLAIN);
        words.add(w);
        assertEquals(4, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for DOUBLE_LETTER Square Types work as expected
     * FAIL: Expected: 10, Actual: 6
     */
    @Test
    public void testScoreDoubleLetter() {
        // Word: "EVE" E=1, V=4x2=8, E=1 --> score = 10
        w.addLast(LetterTile.E, Square.Type.PLAIN);
        w.addLast(LetterTile.V, Square.Type.DOUBLE_LETTER);
        w.addLast(LetterTile.E, Square.Type.PLAIN);
        words.add(w);
        assertEquals(10, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for TRIPLE_LETTER Square Types work as expected
     * FAIL: Expected: 15, Actual: 7
     */
    @Test
    public void testScoreTripleLetter() {
        // Word: "FELL" F=4x3=12, E=1, L=1, L=1
        w.addFirst(LetterTile.L, Square.Type.PLAIN);
        w.addFirst(LetterTile.L, Square.Type.PLAIN);
        w.addFirst(LetterTile.E, Square.Type.PLAIN);
        w.addFirst(LetterTile.F, Square.Type.TRIPLE_LETTER);
        words.add(w);
        assertEquals(15, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for DOUBLE_WORD Square Types work as expected
     * FAIL: Expected: 12, Actual: 6
     */
    @Test
    public void testScoreDoubleWord() {
        // Word: "HAT" H=4, A=1, T=1 --> score = 6x2 = 12
        w.addLast(LetterTile.H, Square.Type.DOUBLE_WORD);
        w.addLast(LetterTile.A, Square.Type.PLAIN);
        w.addLast(LetterTile.T, Square.Type.PLAIN);
        words.add(w);
        assertEquals(12, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for TRIPLE_WORD Square Types work as expected
     * FAIL: Expected: 27, Actual: 9
     */
    @Test
    public void testScoreTripleWord() {
        // Word: "KAREN" K=5, A=1, R=1, E=1, N=1 --> score = 9x3 = 27
        w.addLast(LetterTile.K, Square.Type.TRIPLE_WORD);
        w.addLast(LetterTile.A, Square.Type.PLAIN);
        w.addLast(LetterTile.R, Square.Type.PLAIN);
        w.addLast(LetterTile.E, Square.Type.PLAIN);
        w.addLast(LetterTile.N, Square.Type.PLAIN);
        words.add(w);
        assertEquals(27, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for a START Square Type works as expected
     * FAIL: Expected: 10, Actual: 5
     */
    @Test
    public void testScoreStartWord() {
        // Word: "PIE" P=3, I=1, E=1 --> score = 5x2 = 10
        w.addLast(LetterTile.P, Square.Type.START);
        w.addLast(LetterTile.I, Square.Type.PLAIN);
        w.addLast(LetterTile.E, Square.Type.PLAIN);
        words.add(w);
        assertEquals(10, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for a combination of Square Types work as expected
     * FAIL: Expected: 96, Actual: 5
     */
    @Test
    public void testScoreAllTileTypes() {
        // Word: "NOTE" N=1x2=2, O=1x3=3, T=1, E=1, S=1 --> score = 8x2x3x2 = 96
        w.addLast(LetterTile.N, Square.Type.DOUBLE_LETTER);
        w.addLast(LetterTile.O, Square.Type.TRIPLE_LETTER);
        w.addLast(LetterTile.T, Square.Type.DOUBLE_WORD);
        w.addLast(LetterTile.E, Square.Type.TRIPLE_WORD);
        w.addLast(LetterTile.S, Square.Type.START);
        words.add(w);
        assertEquals(96, w.scoreWords(words));
    }

    /**
     * Tests that the toString of an empty word returns an empty string
     */
    @Test
    public void testToStringEmptyWord() {
        assertEquals("", w.toString());
    }

    /**
     * Tests that the toString of a word returns the word in all capital letters
     */
    @Test
    public void testToString() {
        // Word: "NOTIFY"
        w.addLast(LetterTile.N, Square.Type.START);
        w.addLast(LetterTile.O, Square.Type.PLAIN);
        w.addLast(LetterTile.T, Square.Type.PLAIN);
        w.addLast(LetterTile.I, Square.Type.PLAIN);
        w.addLast(LetterTile.F, Square.Type.PLAIN);
        w.addLast(LetterTile.Y, Square.Type.PLAIN);
        assertEquals("NOTIFY", w.toString());
    }
}
