import javax.swing.*;

public class PlayerView extends JPanel{
    private Player player;
    private RackView rackView;
    private JLabel scoreLabel;

    public PlayerView(Player p){
        super();
        this.player = p;
        player.addView(this);
        rackView = new RackView(player.getRack());
        scoreLabel = new JLabel(String.valueOf(player.getScore()));
        JLabel nameLabel = new JLabel(player.getName());
    }

    public void update(int score){
        scoreLabel.setText(String.valueOf(score));
    }

}
