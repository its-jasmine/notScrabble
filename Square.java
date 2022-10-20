public class Square {
    public enum SquareType {
        STAR, PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD
    }
    private Letter letter;
    private SquareType type;

    public Square(SquareType type){
        this.letter = null;
        this.type = type;
    }
    public Square(){
        this(SquareType.PLAIN);
    }
    public Letter getTile() {
        return letter;
    }
    public boolean isEmpty() {
        return letter == null;
    }
    public void placeTile(Letter letter) {
        this.letter = letter;
    }
}
