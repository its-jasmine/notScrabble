import org.junit.Test;
import java.util.*;
/*import java.util.ArrayList;
import java.util.HashSet;*/

import static org.junit.Assert.*;

public class WordFinderTest {
    ArrayList<Tile> tileArrayList;
    WordFinder wf;
    Set<String> expectedWords;
    String format;

    @Test
    public void findWord() {
        wf = new WordFinder();
        tileArrayList = new ArrayList<>();
        expectedWords = new HashSet<>();
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
        assertEquals(new HashSet<>(wf.findWord(tileArrayList,format)), expectedWords);

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
        assertEquals(new HashSet<>(wf.findWord(tileArrayList,format)), expectedWords);
        /*
        Test format
        letters = D, O, O, M.
        format starts with A, should return an empty list
        */
        expectedWords.clear();
        format = "A...";
        expectedWords.clear();
        assertEquals(new HashSet<>(wf.findWord(tileArrayList,format)), expectedWords);

        /*
        find words with a blank tile
         */
        tileArrayList.clear();
        expectedWords.clear();
        format = "....";
        tileArrayList.add(Tile.D);
        tileArrayList.add(Tile.E);
        tileArrayList.add(Tile.D);
        tileArrayList.add(Tile.BLANK);

        expectedWords.add("DIED");
        expectedWords.add("DUDE");
        expectedWords.add("DYED");
        expectedWords.add("EDDO");
        expectedWords.add("EDDY");
        expectedWords.add("DEAD");
        expectedWords.add("REDD");
        expectedWords.add("DEED");
        assertEquals(new HashSet<>(wf.findWord(tileArrayList,format)),expectedWords);

    }
}