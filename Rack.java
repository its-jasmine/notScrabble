import java.util.*;
public class Rack {

    private List<Letters> lettersList;
    private int lettersAmount;
    private final static MAXLETTERS = 7;

    public Rack(){
        List lettersList = new ArrayList<Letters>();
        this.lettersAmount = 0;
    }
    public int getTilesAmount() {
        return lettersAmount;
    }
    public List<Letters> getTilesList() {
        return lettersList;
    }
    public void getLetters(){
        if (lettersAmount>=7){
            System.out.println("The rack already has 7 tiles in it.");
            return;
        }
        //This is for the version that picks up 1 tile from the bag at a time
        for (int i = 0; i < (7-lettersAmount); i++){

            lettersList.add(getBag().drawLetter());
            lettersAmount++;
        }

    }

}
