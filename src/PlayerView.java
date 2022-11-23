import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

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

    /**
     * updates the score
     * @param score the score of the player
     */
    public void update(int score){
        scoreLabel.setText("Score: "+score+"        ");
        /*
        JDialog notif = new JDialog();
        String message = player.getName() + " got " + score + " points";
        JLabel l = new JLabel(message, SwingConstants.CENTER);
        notif.setUndecorated(true);
        //notif.setBackground(Color.RED);
        notif.setBackground(new Color(1.0F,0F,0F,0.8F));
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Tahoma",Font.BOLD, 18));
        notif.add(l);
        notif.setSize(300,100);
        notif.setLocationRelativeTo(null);
        //notif.setOpacity(0.8F);
        if (player instanceof AIPlayer) {
            Timer timer = new Timer(3000, event -> {
            });
            timer.setRepeats(false);
            timer.start();
        }
        notif.setVisible(true);

        Timer timer = new Timer(2000, event -> {
            notif.dispose();
        });
        timer.setRepeats(false);
        timer.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

    }

    /**
     * gets the player's rackView
     * @return the rack's view
     */
    public Component getRackView() {
        return rackView;
    }
}
