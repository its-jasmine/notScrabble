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

    /**
     * Only updates GUI, does not alter the model. Will not be tested.
     */
    @Test
    public void playGame() {

    }

    @Test
    public void passTurn() {
        /* Test 1: 2 Player game*/
        ArrayList<Player> players = game2.getPlayers();
        ArrayList<ArrayList<Tile>> tileLists = new ArrayList<>();
        for (Player p : players) tileLists.add(p.getRack().getTilesList());
        // Note that calling getTilesList always returns a new ArrayList instance, containing the tiles in the Rack at that given moment.
        // Changes to the Rack model will not be reflected in this ArrayList, which is why we can use it for our expected comparison



        //Verifies that passing a turn twice on a two player game goes back to the first player.
        int firstPlayer = game2.getPlayerTurn();
        game2.passTurn();
        assertNotEquals(firstPlayer, game2.getPlayerTurn());
        game2.passTurn();
        assertEquals(firstPlayer, game2.getPlayerTurn());

        //Verifies that players do not gain points when they pass a turn.
        assertNoPointsGained(players);

        //Verifies that the player's rack does not change after they've passed their turn
        assertSameTilesForAllPlayers(tileLists,players);

        players = game4.getPlayers();
        tileLists = new ArrayList<>();
        for (Player p : players) tileLists.add(p.getRack().getTilesList());


        /* Test 2: Four player game */

        //Verifies that a four player game appropriately cycles through all the necessary indicies if a turn is passed at any point.
        firstPlayer = game4.getPlayerTurn();
        ArrayList<Integer> expectedValues;

        //Note: The first player of the game cannot be predetermined, it depends on random drawing of tiles.
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

        /* Verifies that players do not gain points when they pass a turn.*/
        assertNoPointsGained(players);

        /* Verifies that the player's rack does not change after they've passed their turn */
        assertSameTilesForAllPlayers(tileLists,players);
    }

    /**
     * Verifies that the tiles in each list are the same Tile objects for all players.
     * @param tileLists list containing the lists of the tiles expected for each player
     * @param players list of players
     */
    private void assertSameTilesForAllPlayers(ArrayList<ArrayList<Tile>> tileLists,ArrayList<Player> players){
        assertEquals(players.size(), tileLists.size()); // there should be a tile list for each player
        ArrayList<Tile> expected;
        ArrayList<Tile> actual;
        for (int i = 0; i < players.size(); i++) {
            expected = tileLists.get(i);
            actual = players.get(i).getRack().getTilesList();
            assertEquals(7, actual.size()); // asserts rack is still full
            assertSameTiles(expected, actual);
        }

    }

    /**
     * Verifies that the tiles in each list are the same Tile objects.
     * @param expected list of the tiles expected
     * @param actual list of the actual tiles
     */
    private void assertSameTiles(ArrayList<Tile> expected, ArrayList<Tile> actual){
        assertEquals(expected.size(), actual.size());
        for (int j = 0; j < expected.size(); j++){
            assertEquals(expected.get(j), actual.get(j));
        }
    }

    /**
     * Verifies that all players have 0 points.
     * @param players The list of players being checked.
     */
    private void assertNoPointsGained(ArrayList<Player> players){
        for (Player p : players){
            assertEquals(0, p.getScore());
        }
    }


    @Test
    public void submit() {

    }

    @Test
    public void addView() {


    }
}