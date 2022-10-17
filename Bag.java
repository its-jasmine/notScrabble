/**
 * @author: Rebecca Elliott
 * @date: 2022
 *
 * This class represents the bag full of tiles that the players draw from.
 */

import java.util.*;
import static java.util.Collections.shuffle;

public class Bag {
    private List<Letters> letters;

    public Bag() {
        this.letters = new ArrayList<Letters>();
        for (Letters l : Letters.values()) {
            for (int i = l.getTotalNum();i > 0; i--) {
                letters.add(l);
            }
        }
        shuffle(letters); // randomizes the order letters will be drawn in
    }

    /**
     * Allows player to draw one letter at random
     * @param numToDraw number of letters to draw
     * @return list of drawn letters removed from the bag
     */
    public ArrayList<Letters> drawTile(int numToDraw) {
        ArrayList returnList = new ArrayList<>();
        for (int i = numToDraw; i > 0; numToDraw--){
            returnList.add(letters.remove(letters.size()-1));
        }
        return returnList;
    }

}
