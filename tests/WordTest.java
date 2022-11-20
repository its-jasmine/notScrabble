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

    public Square newPlainSquareWithTile(Tile tile){
        Square s = new Square();
        s.setTile(tile);
        return s;
    }
    public Square newPremiumSquareWithTile(Tile tile, Square.Type type){
        Square s = new Square(type);
        s.setTile(tile);
        return s;
    }

    /**
     * Tests that the addFirst method appends at the beginning of the word
     */
    @Test
    public void testAddFirst() {
        // Word: "BACK"
        Square s = newPlainSquareWithTile(LetterTile.K);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.C);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.A);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.B);
        w.addFirst(s);

        assertEquals(4, w.size());
        words.add(w);
        assertEquals(true, Word.areValidWords(words));
    }


    /**
     * Tests that the addLast method appends at the end of the word
     */
    @Test
    public void testAddLast() {
        // Word: "CONSULT"
        Square s = newPlainSquareWithTile(LetterTile.C);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.O);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.N);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.S);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.U);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.L);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.T);
        w.addLast(s);

        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, Word.areValidWords(words));
    }

    /**
     * Tests the addFirst and addLast methods work together
     */
    @Test
    public void testAddFirstAndLast() {
        // Word: "DEVELOP"
        Square s = newPlainSquareWithTile(LetterTile.E);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.L);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.V);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.O);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.P);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.D);
        w.addFirst(s);

        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, Word.areValidWords(words));


    }

    /**
     * Tests that the areValidWords method returns true when all words are valid
     */
    @Test
    public void testAreValidWords() {
        // Word: "GRAIN"
        Square s = newPlainSquareWithTile(LetterTile.G);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.R);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.A);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.I);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.N);
        w.addLast(s);
        words.add(w);

        // Word: "EVIL"
        Word w2 = new Word();
        s = newPlainSquareWithTile(LetterTile.E);
        w2.addLast(s);
        s = newPlainSquareWithTile(LetterTile.V);
        w2.addLast(s);
        s = newPlainSquareWithTile(LetterTile.I);
        w2.addLast(s);
        s = newPlainSquareWithTile(LetterTile.L);
        w2.addLast(s);
        words.add(w2);

        // Word: "FLOOR"
        Word w3 = new Word();
        s = newPlainSquareWithTile(LetterTile.F);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.L);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.O);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.O);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.R);
        w3.addLast(s);
        words.add(w3);

        assertTrue(Word.areValidWords(words));
    }

    /**
     * Tests that the areValidWords method returns false when any word is invalid
     */
    @Test
    public void testAreInvalidWords() {
        Square s = newPlainSquareWithTile(LetterTile.K);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.A);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.Y);
        w.addLast(s);
        words.add(w);

        Word w2 = new Word();
        s = newPlainSquareWithTile(LetterTile.L);
        w2.addLast(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w2.addLast(s);
        s = newPlainSquareWithTile(LetterTile.D);
        w2.addLast(s);
        words.add(w2);

        Word w3 = new Word();
        s = newPlainSquareWithTile(LetterTile.M);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.S);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.N);
        w3.addLast(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w3.addLast(s);
        words.add(w3);

        assertFalse(Word.areValidWords(words));
    }

    /**
     * Tests that scoreWords for PLAIN Square Types work as expected
     */
    @Test
    public void testScorePlain() {
        // Word: "LIST" L=1, I=1, S=1, T=1 --> score = 4
        Square s = newPlainSquareWithTile(LetterTile.L);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.I);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.S);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.T);
        w.addLast(s);
        words.add(w);

        assertEquals(4, Word.scoreWords(words));
    }

    /**
     * Tests that scoreWords for DOUBLE_LETTER Square Types work as expected
     */
    @Test
    public void testScoreDoubleLetter() {
        // Word: "EVE" E=1, V=4x2=8, E=1 --> score = 10
        Square s = newPlainSquareWithTile(LetterTile.E);
        w.addLast(s);
        s = newPremiumSquareWithTile(LetterTile.V, Square.Type.DOUBLE_LETTER);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w.addLast(s);
        words.add(w);
        assertEquals(10, Word.scoreWords(words));
    }

    /**
     * Tests that scoreWords for TRIPLE_LETTER Square Types work as expected
     */
    @Test
    public void testScoreTripleLetter() {
        // Word: "FELL" F=4x3=12, E=1, L=1, L=1

        Square s = newPlainSquareWithTile(LetterTile.L);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.L);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w.addFirst(s);
        s = newPremiumSquareWithTile(LetterTile.F, Square.Type.TRIPLE_LETTER);
        w.addFirst(s);
        words.add(w);
        assertEquals(15, Word.scoreWords(words));
    }

    /**
     * Tests that scoreWords for DOUBLE_WORD Square Types work as expected
     */
    @Test
    public void testScoreDoubleWord() {
        // Word: "HAT" H=4, A=1, T=1 --> score = 6x2 = 12
        Square s = newPremiumSquareWithTile(LetterTile.H, Square.Type.DOUBLE_WORD);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.A);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.T);
        w.addFirst(s);

        words.add(w);
        assertEquals(12, w.scoreWords(words));


    }

    /**
     * Tests that scoreWords for TRIPLE_WORD Square Types work as expected
     */
    @Test
    public void testScoreTripleWord() {
        // Word: "KAREN" K=5, A=1, R=1, E=1, N=1 --> score = 9x3 = 27
        Square s = newPremiumSquareWithTile(LetterTile.K, Square.Type.TRIPLE_WORD);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.A);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.R);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.N);
        w.addFirst(s);
        words.add(w);
        assertEquals(27, w.scoreWords(words));
    }

    /**
     * Tests that scoreWords for a START Square Type works as expected
     */
    @Test
    public void testScoreStartWord() {
        // Word: "PIE" P=3, I=1, E=1 --> score = 5x2 = 10
        Square s = newPremiumSquareWithTile(LetterTile.P, Square.Type.START);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.I);
        w.addFirst(s);
        s = newPlainSquareWithTile(LetterTile.E);
        w.addFirst(s);

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
        Square s = newPremiumSquareWithTile(LetterTile.N, Square.Type.DOUBLE_LETTER);
        w.addFirst(s);
        s = newPremiumSquareWithTile(LetterTile.O, Square.Type.TRIPLE_LETTER);
        w.addFirst(s);
        s = newPremiumSquareWithTile(LetterTile.T, Square.Type.DOUBLE_WORD);
        w.addFirst(s);
        s = newPremiumSquareWithTile(LetterTile.E, Square.Type.TRIPLE_WORD);
        w.addFirst(s);
        s = newPremiumSquareWithTile(LetterTile.S, Square.Type.START);
        w.addFirst(s);

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
        Square s = newPremiumSquareWithTile(LetterTile.N, Square.Type.START);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.O);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.T);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.I);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.F);
        w.addLast(s);
        s = newPlainSquareWithTile(LetterTile.Y);
        w.addLast(s);

        assertEquals("NOTIFY", w.toString());
    }
}
