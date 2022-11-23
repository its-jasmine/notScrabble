import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.*;

/**
 * Test class for WordExtractor.
 * @author Rebecca Elliott
 * @version Milestone2
 */
public class WordExtractorTest {
    private WordExtractor wordExtractor;
    private ArrayList<Coordinate> newCoordinateList;
    private ArrayList<Tile> newTileList;

    private ArrayList<Word> words;

    private Board board;


    @Test
    public void getWordsCreated() {
        // words do not need to be valid words

        /* test first tiles */
        // on start square
        Board b = new Board();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.E);
        newTileList.add(LetterTile.N);
        newTileList.add(LetterTile.D);
        b.placeTiles(newCoordinateList, newTileList);
        wordExtractor = new WordExtractor(b);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("END", words.get(0).toString());


        /* test tiles extend tiles horizontally */
        board = setBoardWithOneWordOnStartH();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("ENDIS", words.get(0).toString());

        /* test tiles extend tiles vertically */
        board = setBoardWithOneWordOnStartV();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.ELEVEN));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.VERTICAL);
        assertEquals(1, words.size());
        assertEquals("ENDIS", words.get(0).toString());


        /* test tiles prepend tiles horizontally */
        board = setBoardWithOneWordOnStartH();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.E, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("ISEND", words.get(0).toString());

        /* test tiles prepend tiles vertically */
        board = setBoardWithOneWordOnStartV();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.FIVE));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SIX));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.VERTICAL);
        assertEquals(1, words.size());
        assertEquals("ISEND", words.get(0).toString());

        /* test tiles cross horizontal tiles */
        board = setBoardWithOneWordOnStartH();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.VERTICAL);
        assertEquals(1, words.size());
        assertEquals("INS", words.get(0).toString());

        /* test tiles cross vertical tiles */
        board = setBoardWithOneWordOnStartV();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("INS", words.get(0).toString());

        /* test tiles intersect on the right with vertical tiles */
        board = setBoardWithOneWordOnStartV();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("NIS", words.get(0).toString());

        /* test tiles intersect on the left with vertical tiles */
        board = setBoardWithOneWordOnStartV();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        newCoordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(1, words.size());
        assertEquals("ISN", words.get(0).toString());

        /* test tiles intersect above with Horizontal tiles */
        board = setBoardWithOneWordOnStartH();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SIX));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.VERTICAL);
        assertEquals(1, words.size());
        assertEquals("ISN", words.get(0).toString());

        /* test tiles intersect below with Horizontal tiles */
        board = setBoardWithOneWordOnStartH();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        newCoordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        newTileList.add(LetterTile.I);
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.VERTICAL);
        assertEquals(1, words.size());
        assertEquals("NIS", words.get(0).toString());

        /* test tiles interact with multiple tiles */
        board = setBoardWithManyWordsOnIt();
        newCoordinateList = new ArrayList<>();
        newTileList = new ArrayList<>();
        newCoordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.S);
        newCoordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.H);
        newCoordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.I);
        newCoordinateList.add(new Coordinate(Coordinate.Column.M, Coordinate.Row.EIGHT));
        newTileList.add(LetterTile.S);
        board.placeTiles(newCoordinateList, newTileList);
        //System.out.println(board);

        wordExtractor = new WordExtractor(board);
        words = wordExtractor.getWordsCreated(newCoordinateList, BoardValidator.Direction.HORIZONTAL);
        assertEquals(2, words.size());
        assertEquals("SPACESHIPS", words.get(0).toString());
        assertEquals("ZOOS", words.get(1).toString());

    }

    private Board setBoardWithOneWordOnStartH() {
        Board b = new Board();
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.EIGHT));
        ArrayList<Tile> tileList = new ArrayList<>();
        tileList.add(LetterTile.E);
        tileList.add(LetterTile.N);
        tileList.add(LetterTile.D);
        b.placeTiles(coordinateList, tileList);
        return b;
    }

    private Board setBoardWithOneWordOnStartV() {
        Board b = new Board();
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        ArrayList<Tile> tileList = new ArrayList<>();
        tileList.add(LetterTile.E);
        tileList.add(LetterTile.N);
        tileList.add(LetterTile.D);
        b.placeTiles(coordinateList, tileList);
        return b;
    }

    private Board setBoardWithManyWordsOnIt() {
        Board b = new Board();
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        ArrayList<Tile> tileList = new ArrayList<>();
        coordinateList.add(new Coordinate(Coordinate.Column.D, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.S);
        coordinateList.add(new Coordinate(Coordinate.Column.E, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.P);
        coordinateList.add(new Coordinate(Coordinate.Column.F, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.A);
        coordinateList.add(new Coordinate(Coordinate.Column.G, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.C);
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.E);

        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        tileList.add(LetterTile.N);
        coordinateList.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        tileList.add(LetterTile.T);

        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.SEVEN));
        tileList.add(LetterTile.O);
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.SIX));
        tileList.add(LetterTile.O);
        coordinateList.add(new Coordinate(Coordinate.Column.I, Coordinate.Row.FIVE));
        tileList.add(LetterTile.Z);

        coordinateList.add(new Coordinate(Coordinate.Column.J, Coordinate.Row.SIX));
        tileList.add(LetterTile.D);
        coordinateList.add(new Coordinate(Coordinate.Column.K, Coordinate.Row.SIX));
        tileList.add(LetterTile.D);
        coordinateList.add(new Coordinate(Coordinate.Column.L, Coordinate.Row.SIX));
        tileList.add(LetterTile.S);

        coordinateList.add(new Coordinate(Coordinate.Column.L, Coordinate.Row.SEVEN));
        tileList.add(LetterTile.A);
        coordinateList.add(new Coordinate(Coordinate.Column.L, Coordinate.Row.EIGHT));
        tileList.add(LetterTile.P);

        b.placeTiles(coordinateList, tileList);
        //System.out.println(b);
        return b;
    }
}