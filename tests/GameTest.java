import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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
                expectedValues = new ArrayList<>(Arrays.asList(0,1,2,3));
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
        int firstPlayerIndex = game2.getPlayerTurn();
        Player firstPlayer = game2.getPlayers().get(firstPlayerIndex);

        /* Test 1: Submitting when no tiles have been placed */
        game2.submit();
        assertEquals(firstPlayerIndex, game2.getPlayerTurn()); // player did not place any tiles, same player should be playing

        /* Test 2: Submitting when INVALID word has been placed */
        firstPlayerIndex = game3.getPlayerTurn();
        firstPlayer = game3.getPlayers().get(firstPlayerIndex);

        ArrayList<Coordinate> coords = new ArrayList<>();
        HashSet<Coordinate> coordsHash = new HashSet<>();
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.NINE));
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.ELEVEN));
        coordsHash.addAll(coords);

        // invalid word
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.A);

        game3.getBoard().placeTiles(coords, tiles);
        game3.getBoard().setPlayedThisTurn(coordsHash);
        game3.submit();
        assertEquals(game3.getPlayerTurn(), firstPlayerIndex); // game should keep the same player
        assertEquals(0,firstPlayer.getScore()); // player should not recieve any points


        /* Test 3: Submitting when valid word has been placed */
        firstPlayerIndex = game4.getPlayerTurn();
        firstPlayer = game4.getPlayers().get(firstPlayerIndex);

        // using same coords as before

        // valid word
        tiles = new ArrayList<>();
        tiles.add(LetterTile.B);
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.T);
        tiles.add(LetterTile.H);

        game4.getBoard().placeTiles(coords, tiles);
        game4.getBoard().setPlayedThisTurn(coordsHash);
        game4.submit();

        int currentPlayerIndex = game4.getPlayerTurn();
        assertNotEquals(currentPlayerIndex, firstPlayerIndex); // game should move on to next player
        assertEquals(18,firstPlayer.getScore()); // first player should receive score update for their played word
    }

    @Test
    public void testSaveLoad() throws IOException, ClassNotFoundException {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(LetterTile.A);
        tiles.add(LetterTile.N);
        tiles.add(LetterTile.D);
        ArrayList<Coordinate> coords = new ArrayList<>();
        HashSet<Coordinate> coordsHash = new HashSet<>();
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.SEVEN));
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.EIGHT));
        coords.add(new Coordinate(Coordinate.Column.H, Coordinate.Row.TEN));
        coordsHash.addAll(coords);
        game4.getBoard().placeTiles(coords, tiles);
        game4.getBoard().setPlayedThisTurn(coordsHash);
        game4.submit();

        game4.saveGame("gameTest.txt");

        Game game5 = (Game) Game.loadGame("gameTest.txt");

        // Same Players
        ArrayList<Player> game4Players = game4.getPlayers();
        ArrayList<Player> game5Players = game5.getPlayers();
        for (int i=0; i< game4Players.size(); i++){
            assertEquals(game4Players.get(i).getName(),game5Players.get(i).getName());
            assertEquals(game4Players.get(i).getScore(),game5Players.get(i).getScore());
        }
        // Same turn
        assertEquals(game4.getPlayerTurn(), game5.getPlayerTurn());

        // Same Racks
        assertEquals(game4Players.get(0).getRack().getTilesList().toString(), game5Players.get(0).getRack().getTilesList().toString());
        assertEquals(game4Players.get(1).getRack().getTilesList().toString(), game5Players.get(1).getRack().getTilesList().toString());

        // Same Bag
        assertEquals(game4.getBag().getNumTilesLeft(), game4.getBag().getNumTilesLeft());

        // Same Board
        assertEquals(game4.getBoard().getPlayedThisTurn(),game5.getBoard().getPlayedThisTurn());
        assertEquals(game4.getBoard().getPreviouslyPlayed(),game5.getBoard().getPreviouslyPlayed());
    }
}