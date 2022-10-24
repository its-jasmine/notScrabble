/**
 * Models a square on the playing board
 */

public class Square {
    public enum Type {
        START, PLAIN, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD
    }
    private Tile tile;
    private Type type;

    public Square(Type type){
        this.tile = null;
        this.type = type;
    }
    public Square(){
        this(Type.PLAIN);
    }
    public Tile getTile() {
        return tile;
    }

    public Type getType() {
        return type;
    }
    public boolean isEmpty() {
        return tile == null;
    }
    public void placeTile(Tile tile) {
        this.tile = tile;
    }

    public Tile removeTile() {
        Tile temp = this.tile;
        this.tile = null;
        return temp;
    }
    public String toString(){
        if (isEmpty()) return "_";
        return tile.toString();
    }
}
