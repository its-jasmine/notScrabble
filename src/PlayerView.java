import javax.swing.*;
import java.awt.*;

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
        setLayout(new BorderLayout());
        add(rackView, BorderLayout.CENTER);
        JPanel top = new JPanel(new GridLayout(1,2));
        top.add(scoreLabel, 0);
        top.add(nameLabel, 1);
        add(top, BorderLayout.NORTH);


        this.setVisible(true);
    }

    public void update(int score){
        scoreLabel.setText(String.valueOf(score));
    }

    public Component getRackView() {
        return rackView;
    }
}
