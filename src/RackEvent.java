import java.util.EventObject;
import java.util.*;

public class RackEvent extends EventObject {

    private Tile tile;
    public RackEvent(Rack rackModel, Tile t) {
        super(rackModel);
        this.tile = t;
    }


    public Tile getTile() {
        return tile;
    }
}
