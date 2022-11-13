import javax.swing.*;

public class PlayerView {
    private Player player;
    private Game game;
    private RackView rackView;
    private PlayerController playerController;
    private JButton submit;
    private JButton pass;

    public PlayerView(Player p, Game g){
        player = p;
        game = g;
        rackView = new RackView(new Rack(game.getBag()));
        playerController = new playerController(game);
        submit = new JButton("Submit");
        pass = new JButton("Pass");
    }

    public void update(){
        System.out.println("hi");
    }

}
