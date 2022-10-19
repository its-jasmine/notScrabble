import java.util.*;
public class Rack {

    private List<Letters> lettersList;
    private int tilesAmount;

    public Rack(){
        List lettersList = new ArrayList<Letters>();
        this.tilesAmount = 0;
    }
    public int getTilesAmount() {
        return tilesAmount;
    }
    public List<Letters> getTilesList() {
        return lettersList;
    }
    public void getTiles(){
        if (tilesAmount>7){
            System.out.println("The rack already has 7 tiles in it.");
            return;
        }
        //This is for the version that picks up 1 tile from the bag at a time
        for (int i = 0; i < (7-tilesAmount); i++){

            lettersList.add(drawTile());
            tilesAmount++;
        }

    }

}
