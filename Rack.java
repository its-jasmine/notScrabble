import java.util.*;
public class Rack {

    private List<Tile> tilesList;
    private int tilesAmount;

    public Rack(){
        List lettersList = new ArrayList<Tile>();
        this.tilesAmount = 0;
    }
    public int getTilesAmount() {
        return tilesAmount;
    }
    public List<Tile> getTilesList() {
        return tilesList;
    }
    public void getTiles(){
        if (tilesAmount>7){
            System.out.println("The rack already has 7 tiles in it.");
            return;
        }
        //This is for the version that picks up 1 tile from the bag at a time
        for (int i = 0; i < (7-tilesAmount); i++){

            tilesList.add(bag.drawTile());
            tilesAmount++;
        }

    }

}
