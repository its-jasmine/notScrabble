import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GameTest {
    private Game game2;
    private Game game3;
    private Game game4;



    @Before
    public void setUp() {
        game2 = new Game(2);
        game3 = new Game(3);
        game4 = new Game(4);
    }
    @Test
    public void playGame() {
        game2.playGame();

        game3.playGame();

        game4.playGame();
    }

    @Test
    public void passTurn() {
        //Note: The first player of the game cannot be predetermined.

        /* Verifies that passing a turn twice on a two player game goes back to the first player.*/
        int firstPlayer = game2.getPlayerTurn();
        game2.passTurn();
        assertNotEquals(firstPlayer, game2.getPlayerTurn());
        game2.passTurn();
        assertEquals(firstPlayer, game2.getPlayerTurn());


        /* Verifies that a four player game appropriately cycles through all the necessary indicies if a turn is passed at any point.*/
        firstPlayer = game4.getPlayerTurn();
        ArrayList<Integer> expectedValues;

        switch (firstPlayer){
            case 0:
                expectedValues = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
                break;
            case 1:
                expectedValues = new ArrayList<>(Arrays.asList(2,3,0,1));
                break;
            case 2:
                expectedValues = new ArrayList<>(Arrays.asList(3,0,1, 2));
                break;
            default:
                expectedValues = new ArrayList<>(Arrays.asList(3,0,1,2));
        }
        for (Integer expectedValue : expectedValues){
            game4.passTurn();
            assertEquals(expectedValue, (Integer)game4.getPlayerTurn());
        }

    }

    @Test
    public void submit() {

    }

    @Test
    public void addView() {


    }
}