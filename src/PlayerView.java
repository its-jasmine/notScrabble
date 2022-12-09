import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PlayerView extends JPanel {
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
        setLayout(new BorderLayout());
        add(rackView, BorderLayout.CENTER);
        JPanel top = new JPanel(new GridLayout(1,2));
        top.add(nameLabel, 0);
        top.add(scoreLabel, 1);
        add(top, BorderLayout.NORTH);


        this.setVisible(true);
    }

    /**
     * updates the score label
     * @param turnScore the turnScore of the player
     */
    public void update(int turnScore){
        scoreLabel.setText("Score: "+player.getScore()+"        ");

        JDialog notif = new JDialog();
        String message = player.getName() + " got " + turnScore + " points";
        JLabel l = new JLabel(message, SwingConstants.CENTER);
        notif.setUndecorated(true);
        notif.setBackground(new Color(1.0F,0F,0F,0.8F));
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Tahoma",Font.BOLD, 14));
        notif.add(l);
        notif.setSize(200,50);
        notif.setLocationRelativeTo(null);
        Timer timer;
        if (player instanceof AIPlayer) {
            timer = new Timer(2000, event -> {
                notif.setVisible(true);
                Timer timer2 = new Timer(1000, event2 -> {
                    notif.setVisible(false);
                });
                timer2.setRepeats(false);
                timer2.start();
            });
            timer.setRepeats(false);
            timer.start();
        }
        else{
            notif.setVisible(true);
            timer = new Timer(2000, event -> {
                notif.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
