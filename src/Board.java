import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a 15x15-grid board where the tiles of the game are played.
 * @author Rebecca Elliott
 * @author Jasmine Gad El Hak
 * @author Arthur Atangana
 * @author Victoria Malouf
 * @version Milestone2
 */
public class Board implements Serializable {
    public final static Coordinate.Row START_ROW = Coordinate.Row.EIGHT;
    public final static Coordinate.Column START_COLUMN = Coordinate.Column.H;

    /** the tiles being played during the turn */
    private HashSet<Coordinate> playedThisTurn;

    /** the tiles that were played until now */
    private HashSet<Coordinate> previouslyPlayed;


    /** The direction of the word currently be validated */
    private BoardValidator boardValidator = new BoardValidator(this);

    /** the word extractor */
    private WordExtractor wordExtractor = new WordExtractor(this);
    /** The list of boardModel on the board */
    private final DefaultTableModel boardModel;

    /**
     * Creates a new board with plain boardModel.
     */
    public Board() {
        boardModel = new DefaultTableModel(Coordinate.Row.values().length, Coordinate.Column.values().length){
            //  renderers to be used based on Class
            public Class<Square> getColumnClass(int column)
            {
                return Square.class;
            }
        };

        for (Coordinate.Row r : Coordinate.Row.values()) {
            for (Coordinate.Column c : Coordinate.Column.values()) {
                int row = r.ordinal();
                int col = c.ordinal();
                boardModel.setValueAt(new Square(), row, col);
            }
        }
        String[][] squarePlacement =
        {{"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"},
        {"PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN"},
        {"PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN"},
        {"DOUBLE_LETTER","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER"},
        {"PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN"},
        {"PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER", "PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN"},
        {"PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN"},
        {"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","START","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"},
        {"PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN"},
        {"PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER", "PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN"},
        {"PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","PLAIN"},
        {"DOUBLE_LETTER","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER"},
        {"PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN","PLAIN"},
        {"PLAIN","DOUBLE_WORD","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_LETTER","PLAIN","PLAIN","PLAIN","DOUBLE_WORD","PLAIN"},
        {"TRIPLE_WORD","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","PLAIN","TRIPLE_WORD","PLAIN","PLAIN","PLAIN","DOUBLE_LETTER","PLAIN","PLAIN","TRIPLE_WORD"}};

        for (int r = 0; r < squarePlacement.length; r++){
            for (int c = 0; c < squarePlacement[r].length; c++){
                Square.Type type = Square.Type.valueOf(squarePlacement[r][c]);
                boardModel.setValueAt(new Square(type), r, c);
            }
        }

        this.playedThisTurn = new HashSet<>();
        this.previouslyPlayed = new HashSet<>();

    }

    /**
     * Gets the DefaultTableModel of the board
     * @return boardModel
     */
    public DefaultTableModel getModel() {
        return boardModel;
    }

    /**
     * Gets the coordinates of tiles played for the current turn
     * @return a hashset of coordinate objects
     */
    public HashSet<Coordinate> getPlayedThisTurn() {
        return playedThisTurn;
    }

    /**
     * gets the coordinates of previously played tiles on the board
     * @return a hashset of coordinate objects
     */
    public HashSet<Coordinate> getPreviouslyPlayed() {
        return previouslyPlayed;
    }

    /**
     * adds the turn's placed tile to the previously played tiles
     */
    public void addThisTurnToPreviously() {
        previouslyPlayed.addAll(playedThisTurn);
        resetPlayedThisTurn();
    }

    /**
     * reset which tiles where played this turn
     */
    public void resetPlayedThisTurn() {
        playedThisTurn = new HashSet<>();
    }

    /**
     * converts the hashset of coordinates to an arraylist
     * @return an arraylist of coordinate objects
     */
    private ArrayList<Coordinate> playedHashToList() {
        return new ArrayList<>(playedThisTurn);
    }

    /**
     * Gets the square at the specified coordinate on the board.
     * @param coordinate of the Square to retrieve
     * @return Square at specified coordinate
     */
    public Square getSquare(Coordinate coordinate) {
        return (Square) boardModel.getValueAt(coordinate.getRowIndex(), coordinate.getColumnIndex());
    }


    /**
     * Calls all the methods needed to validated and score words created this turn.
     * @return -1 if any validation fails (player should try again), otherwise returns the score for the turn
     */
    public int submit() {
        ArrayList<Coordinate> tilesPlaced = playedHashToList();
        BoardValidator.Direction d = boardValidator.isValidTileAlignment(tilesPlaced);
        if ( d == BoardValidator.Direction.UNKNOWN) return -1;
        // at this point tilesPlaced is now sorted and direction is HORIZONTAL or VERTICAL

        List<Word> words = wordExtractor.getWordsCreated(tilesPlaced, d);
        if (words.size() == 0) { // no words, at least two letters long, were created
            System.out.println("Words must be at least two letters long."); // only possible at game start
            return -1;
        }

        if (!Word.areValidWords(words)) {
            // should tell player this System.out.println("One or more of the words created was invalid.");
            return -1;
        }

        // at this point words are all valid
        int score = Word.scoreWords(words);
        if (tilesPlaced.size() == 7) score += 50;
        return score;
    }

    /**
     * Places tile in square if available.
     * @param coordinate of the tile being placed
     * @param tile to be placed
     * @return true if letter was placed, false otherwise
     */
    protected boolean placeTile(Coordinate coordinate, Tile tile) {
        Square square = getSquare(coordinate);
        if (square.isEmpty()) {
            square.setTile(tile);
            playedThisTurn.add(coordinate);
            return true;
        }
        return false;
    }

    /**
     * Places all the tiles at the specified coordinates.
     * @param coordinates of the tiles to be placed.
     * @param tiles to be placed on the board.
     * @return true if all tiles are successfully placed on the board, false otherwise
     */
    public boolean placeTiles(ArrayList<Coordinate> coordinates, ArrayList<Tile> tiles){
        if (coordinates.size() != tiles.size()) {
            System.out.println("error");
        }
        for (int i = 0; i < coordinates.size(); i++){
            if (!placeTile(coordinates.get(i),tiles.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Removes and returns the Tile in a square if available.
     * @param coordinate of the Tile being removed
     * @return the tile if it was removed, null otherwise
     */
    private Tile removeTile(Coordinate coordinate) {
        return  getSquare(coordinate).removeTile();
    }

    /**
     * removes tiles from the board
     * @param tileCoordinates : a list of coordinates for the tiles to be removed
     * @return a list a removed tiles;
     */
    public ArrayList<Tile> removeTiles(ArrayList<Coordinate> tileCoordinates){
        ArrayList<Tile> tilesLst = new ArrayList<>();
        for (Coordinate c : tileCoordinates){
            tilesLst.add(removeTile(c));
            playedThisTurn.remove(c);
        }
        return tilesLst;
    }

    /**
     * Checks if the square has a tile in it already.
     * @param coordinate of the square being checked
     * @return true if the square has no Tile yet, false otherwise
     */
    public boolean isSquareEmpty(Coordinate coordinate) {
        return getSquare(coordinate).isEmpty();
    }

    /**
     * Check if the start square is empty
     * @return true if square is empty, false otherwise
     */
    public boolean isStartSquareEmpty() {
        Coordinate coordinate = new Coordinate(START_COLUMN,START_ROW);
        return getSquare(coordinate).isEmpty();
    }

    /**
     * Gets the tile on a square without removing it.
     * @param coordinate of the square being checked
     * @return the Tile or null if square has no tile
     */
    public Tile getSquareTile(Coordinate coordinate) {
        return getSquare(coordinate).getTile();
    }

    /**
     * Gets the type of square at a give coordinate.
     * @param coordinate of the square being checked
     * @return the boardModel type
     */
    public Square.Type getSquareType(Coordinate coordinate) {
        return getSquare(coordinate).getType();
    }



    /**
     * Returns string representation of board.
     * @return string of current board state
    */
    public String toString() {
        String s = "   ";
        for (Coordinate.Column c : Coordinate.Column.values()) {
            s += c + "  ";
        }
        s += "\n";
        for (Coordinate.Row r : Coordinate.Row.values()) {
            int row = r.ordinal() + 1;
            if(row < 10) s += row + "  "; else s += row + " "; // keeps columns straight
            for (Coordinate.Column c : Coordinate.Column.values()) {
                s += boardModel.getValueAt(r.ordinal(), c.ordinal()) + "  "; // Each square will be separated by two spaces.
            }
            s += "\n";
        }
        return s;
    }

    /**
     * testing only
     * @param playedThisTurn
     */
    public void setPlayedThisTurn(HashSet<Coordinate> playedThisTurn) {
        this.playedThisTurn = playedThisTurn;
    }

    /**
     * testing only
     * @param previouslyPlayed
     */
    public void setPreviouslyPlayed(HashSet<Coordinate> previouslyPlayed) {
        this.previouslyPlayed = previouslyPlayed;
    }

    /*private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.boardValidator);
        out.writeObject(this.wordExtractor);
    }
    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
        boardValidator = ((Board) aInputStream.readObject()).getBoardValidator();
        wordExtractor = ((Board)aInputStream.readObject()).getWordExtractor();
    }*/

    private WordExtractor getWordExtractor() {
        return wordExtractor;
    }

    private BoardValidator getBoardValidator() {
        return boardValidator;
    }
}
