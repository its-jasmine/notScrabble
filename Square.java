public class Square {
    public enum Type {
        STAR, PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD
    }
    private Letter letter;
    private Type type;

    public Square(Type type){
        this.letter = null;
        this.type = type;
    }
    public Square(){
        this(Type.PLAIN);
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
