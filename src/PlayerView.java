import javax.swing.*;

public class PlayerView {
    private Player player;
    private Game game;
    private RackView rackView;

    public PlayerView(Player p, Game g){
        this.player = p;
        this.game = g;
        rackView = new RackView(player.getRack());


    }

    public void update(){
        System.out.println("hi");
    }

}
