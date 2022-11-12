import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {


    private Player p1;
    private Player p2;
    private Player p3;
    private Bag bag;


    @Before
    public void setUp(){
        bag = new Bag();
        p1 = new Player(new Board(), bag, "Bill");
        p2 = new Player(new Board(), bag, 1);
        p3 = new Player(new Board(), bag);
    }

    @Test
    public void getName() {
        assertEquals("Bill", p1.getName());
        assertEquals("Player 1", p2.getName());
        assertEquals("", p3.getName());

        Player p4 = new Player(new Board(), new Bag(), "23456789");
        assertEquals("23456789", p4.getName());

        p4 = new Player(new Board(), new Bag(), "!@%^&");
        assertEquals("!@%^&", p4.getName());
    }

    @Test
    public void endTurn() {
        // p1 plays a word of n tiles
        p1.endTurn();
        assertEquals(7, p1.getRack().getTilesAmount());

    }

    @Test
    public void addToScore() {
        p1.addToScore(1);
        assertEquals(1, p1.getScore());

        p1.addToScore(345);
        assertEquals(346, p1.getScore());
    }

    @Test
    public void takeTurn() {
    }
}