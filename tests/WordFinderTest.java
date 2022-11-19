import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WordFinderTest {
    ArrayList<Tile> tileArrayList;

    @Test
    public void addletter() {
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
        System.out.println(wf.findWord(tileArrayList, "..a..r"));


        ArrayList<Tile> arrayList2 = new ArrayList<>();
        arrayList2.add(Tile.E);
        arrayList2.add(Tile.R);
        arrayList2.add(Tile.A);
        arrayList2.add(Tile.E);
        arrayList2.add(Tile.T);
        arrayList2.add(Tile.S);
        arrayList2.add(Tile.H);
        arrayList2.add(Tile.E);

        WordFinder wf2 = new WordFinder();
        wf2.addLettersToMap(arrayList2);

        assertTrue(wf.getLetterMap().equals(wf2.getLetterMap()));
    }
}