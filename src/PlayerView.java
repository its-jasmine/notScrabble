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
        scoreLabel = new JLabel("Score: "+player.getScore()+"        ", SwingConstants.RIGHT);
        JLabel nameLabel = new JLabel("        "+player.getName(), SwingConstants.LEFT);
        //JLabel otherLabel = new JLabel("");
        setLayout(new BorderLayout());
        add(rackView, BorderLayout.CENTER);
        JPanel top = new JPanel(new GridLayout(1,2));
        top.add(nameLabel, 0);
        top.add(scoreLabel, 1);
        //top.add(otherLabel, 2);
/*        add(nameLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.SOUTH);*/
        add(top, BorderLayout.NORTH);


        this.setVisible(true);
    }

    public void update(int score){
        scoreLabel.setText("Score: "+score+"        ");
    }

    public Component getRackView() {
        return rackView;
    }
}
