public abstract class Square {
    public enum SquareKind{
        PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD
    }
    private Tile tile;
    private SquareKind type;

    public Square(SquareKind type){
        this.tile = null;
        this.type = type;
    }
    public Square(){
        this(SquareKind.PLAIN);
    }
    public Tile getTile() {
        return tile;
    }
    public boolean isEmpty() {
        return tile == null;
    }
    public void placeTile(Tile tile) {
        this.tile = tile;
    }
}
