import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WordFinderTest {
    ArrayList<Tile> tileArrayList;
    WordFinder wf;
    ArrayList<String> expectedWords;
    String format;

    @Test
    public void getLetterMap() {
        tileArrayList = new ArrayList<>();
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.R);
        tileArrayList.add(Tile.A);
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.T);
        tileArrayList.add(Tile.S);
        tileArrayList.add(Tile.H);

        WordFinder wf = new WordFinder();
        wf.addLettersToMap(tileArrayList);
        assertEquals(2, (int) wf.getLetterMap().get('E'));
        assertEquals(1, (int) wf.getLetterMap().get('R'));
        assertEquals(1, (int) wf.getLetterMap().get('A'));
        assertEquals(1, (int) wf.getLetterMap().get('T'));
        assertEquals(1, (int) wf.getLetterMap().get('S'));
        assertEquals(1, (int) wf.getLetterMap().get('H'));

    }

    @Test
    public void findWord() {
        wf = new WordFinder();
        tileArrayList = new ArrayList<>();
        expectedWords = new ArrayList<>();
        format = ".a";
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.R);
        tileArrayList.add(Tile.A);
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.T);
        tileArrayList.add(Tile.S);
        tileArrayList.add(Tile.H);

        expectedWords.add("HA");
        expectedWords.add("TA");
        assertEquals(wf.findWord(tileArrayList,format), expectedWords);

        /*
        test multiple letters
        format word for any 4 letters while hand has D, O, O, M
        which can only form DOOM and MOOD
        */
        tileArrayList.clear();
        expectedWords.clear();
        format = "....";
        tileArrayList.add(Tile.D);
        tileArrayList.add(Tile.O);
        tileArrayList.add(Tile.O);
        tileArrayList.add(Tile.M);

        expectedWords.add("DOOM");
        expectedWords.add("MOOD");

        /*
        Test format
        letters = D, O, O, M.
        format starts with A, should return an empty list
        */
        expectedWords.clear();
        format = "A...";
        expectedWords.clear();
        assertEquals(wf.findWord(tileArrayList,format), expectedWords);
    }
}