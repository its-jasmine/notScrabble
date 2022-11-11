import java.util.EventObject;

public class BoardEvent extends EventObject {
    private Tile tile;
    private Coordinate coordinate;

    public BoardEvent(Board boardModel, Tile t, Coordinate c) {
        super(boardModel);
        this.tile = t;
        this.coordinate = c;

    }
    public BoardEvent(Board boardModel, Coordinate c){
        super(boardModel);
        this.coordinate = c;
    }

    public Tile getTile() {
        return tile;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
