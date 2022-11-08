import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests class Word
 * Scoring of words are expected to have failing unit tests. (i.e., all testScore* tests)
 *
 * @author Victoria Malouf
 * @version Milestone2
 */

public class WordTest {
    Word w;
    List<Word> words;

    @Before
    public void setUp() throws Exception {
        w = new Word();
        words = new ArrayList<>();
    }

    @Test
    public void initialSizeIsZero() {
        assertEquals(0, w.size());
    }

    @Test
    public void testSingleLetterWord() {
        w.addFirst(Tile.B, Square.Type.START);
        assertEquals(1, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    @Test
    public void testAddFirst() {
        // Word is "BACK"
        w.addFirst(Tile.K, Square.Type.PLAIN);
        w.addFirst(Tile.C, Square.Type.PLAIN);
        w.addFirst(Tile.A, Square.Type.PLAIN);
        w.addFirst(Tile.B, Square.Type.PLAIN);
        assertEquals(4, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    @Test
    public void testAddLast() {
        // Word is "CONSULT"
        w.addLast(Tile.C, Square.Type.START);
        w.addLast(Tile.O, Square.Type.PLAIN);
        w.addLast(Tile.N, Square.Type.PLAIN);
        w.addLast(Tile.S, Square.Type.PLAIN);
        w.addLast(Tile.U, Square.Type.PLAIN);
        w.addLast(Tile.L, Square.Type.PLAIN);
        w.addLast(Tile.T, Square.Type.PLAIN);
        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    @Test
    public void testAddFirstAndLast() {
        // Word is "DEVELOP"
        w.addFirst(Tile.E, Square.Type.START);
        w.addLast(Tile.L, Square.Type.PLAIN);
        w.addFirst(Tile.V, Square.Type.PLAIN);
        w.addLast(Tile.O, Square.Type.PLAIN);
        w.addFirst(Tile.E, Square.Type.PLAIN);
        w.addLast(Tile.P, Square.Type.PLAIN);
        w.addFirst(Tile.D, Square.Type.PLAIN);
        assertEquals(7, w.size());
        words.add(w);
        assertEquals(true, w.areValidWords(words));
    }

    @Test
    public void testAreValidWords() {
        w.addLast(Tile.G, Square.Type.PLAIN);
        w.addLast(Tile.R, Square.Type.PLAIN);
        w.addLast(Tile.A, Square.Type.PLAIN);
        w.addLast(Tile.I, Square.Type.PLAIN);
        w.addLast(Tile.N, Square.Type.PLAIN);
        words.add(w);
        Word w2 = new Word();
        w2.addLast(Tile.E, Square.Type.PLAIN);
        w2.addLast(Tile.V, Square.Type.PLAIN);
        w2.addLast(Tile.I, Square.Type.PLAIN);
        w2.addLast(Tile.L, Square.Type.PLAIN);
        words.add(w2);
        Word w3 = new Word();
        w3.addLast(Tile.F, Square.Type.PLAIN);
        w3.addLast(Tile.L, Square.Type.PLAIN);
        w3.addLast(Tile.O, Square.Type.PLAIN);
        w3.addLast(Tile.O, Square.Type.PLAIN);
        w3.addLast(Tile.R, Square.Type.PLAIN);
        words.add(w3);
        assertEquals(true, w.areValidWords(words));
    }

    @Test
    public void testAreInvalidWords() {
        w.addLast(Tile.K, Square.Type.PLAIN);
        w.addLast(Tile.A, Square.Type.PLAIN);
        w.addLast(Tile.Y, Square.Type.PLAIN);
        words.add(w);
        Word w2 = new Word();
        w2.addLast(Tile.L, Square.Type.PLAIN);
        w2.addLast(Tile.E, Square.Type.PLAIN);
        w2.addLast(Tile.D, Square.Type.PLAIN);
        words.add(w2);
        Word w3 = new Word();
        w3.addLast(Tile.M, Square.Type.PLAIN);
        w3.addLast(Tile.S, Square.Type.PLAIN);
        w3.addLast(Tile.N, Square.Type.PLAIN);
        w3.addLast(Tile.E, Square.Type.PLAIN);
        words.add(w3);
        assertFalse(w.areValidWords(words));
    }

    @Test
    public void testScorePlain() {
        // Word "LIST" L=1, I=1, S=1, T=1 --> score = 4
        w.addLast(Tile.L, Square.Type.PLAIN);
        w.addLast(Tile.I, Square.Type.PLAIN);
        w.addLast(Tile.S, Square.Type.PLAIN);
        w.addLast(Tile.T, Square.Type.PLAIN);
        words.add(w);
        assertEquals(4, w.scoreWords(words));
    }

    /**
     * FAIL: Expected: 10, Actual: 6
     */
    @Test
    public void testScoreDoubleLetter() {
        // Word "EVE" E=1, V=4x2=8, E=1 --> score = 10
        w.addLast(Tile.E, Square.Type.PLAIN);
        w.addLast(Tile.V, Square.Type.DOUBLE_LETTER);
        w.addLast(Tile.E, Square.Type.PLAIN);
        words.add(w);
        assertEquals(10, w.scoreWords(words));
    }

    /**
     * FAIL: Expected: 15, Actual: 7
     */
    @Test
    public void testScoreTripleLetter() {
        // Word "FELL" F=4x3=12, E=1, L=1, L=1
        w.addFirst(Tile.L, Square.Type.PLAIN);
        w.addFirst(Tile.L, Square.Type.PLAIN);
        w.addFirst(Tile.E, Square.Type.PLAIN);
        w.addFirst(Tile.F, Square.Type.TRIPLE_LETTER);
        words.add(w);
        assertEquals(15, w.scoreWords(words));
    }

    /**
     * FAIL: Expected: 12, Actual: 6
     */
    @Test
    public void testScoreDoubleWord() {
        // Word "HAT" H=4, A=1, T=1 --> score = 6x2 = 12
        w.addLast(Tile.H, Square.Type.DOUBLE_WORD);
        w.addLast(Tile.A, Square.Type.PLAIN);
        w.addLast(Tile.T, Square.Type.PLAIN);
        words.add(w);
        assertEquals(12, w.scoreWords(words));
    }

    /**
     * FAIL: Expected: 27, Actual: 9
     */
    @Test
    public void testScoreTripleWord() {
        // Word "KAREN" K=5, A=1, R=1, E=1, N=1 --> score = 9x3 = 27
        w.addLast(Tile.K, Square.Type.TRIPLE_WORD);
        w.addLast(Tile.A, Square.Type.PLAIN);
        w.addLast(Tile.R, Square.Type.PLAIN);
        w.addLast(Tile.E, Square.Type.PLAIN);
        w.addLast(Tile.N, Square.Type.PLAIN);
        words.add(w);
        assertEquals(27, w.scoreWords(words));
    }

    /**
     * FAIL: Expected: 42, Actual: 4
     */
    @Test
    public void testScoreAllTileTypes() {
        // Word "NOTE" N=1x2=2, O=1x3=3, T=1, E=1 --> score = 7x2x3 = 42
        w.addLast(Tile.N, Square.Type.DOUBLE_LETTER);
        w.addLast(Tile.O, Square.Type.TRIPLE_LETTER);
        w.addLast(Tile.T, Square.Type.DOUBLE_WORD);
        w.addLast(Tile.E, Square.Type.TRIPLE_WORD);
        words.add(w);
        assertEquals(42, w.scoreWords(words));
    }

    @Test
    public void testToStringEmptyWord() {
        assertEquals("", w.toString());
    }

    @Test
    public void testToString() {
        // Word "NOTIFY"
        w.addLast(Tile.N, Square.Type.START);
        w.addLast(Tile.O, Square.Type.PLAIN);
        w.addLast(Tile.T, Square.Type.PLAIN);
        w.addLast(Tile.I, Square.Type.PLAIN);
        w.addLast(Tile.F, Square.Type.PLAIN);
        w.addLast(Tile.Y, Square.Type.PLAIN);
        assertEquals("NOTIFY", w.toString());
    }
}