import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel{
    private Player player;
    private RackView rackView;
    private ExchangeView exchangeView;
    private JLabel scoreLabel;

    public PlayerView(Player p){
        super();
        this.player = p;
        player.addView(this);
        rackView = new RackView(player.getRack());
        exchangeView = new ExchangeView(player.getRack());
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

        //JPanel bottom = new JPanel();
        //bottom.setLayout(new BorderLayout());

        //bottom.add(exchangeView, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void update(int score){

        scoreLabel.setText("Score: "+score+"        ");
        hideExchangeView();
    }

    public void displayExchangeView() {//TODO
        this.add(exchangeView, BorderLayout.SOUTH);
    }

    private void hideExchangeView() { //TODO
        BorderLayout layout = (BorderLayout) this.getLayout();
        Component c = layout.getLayoutComponent(BorderLayout.SOUTH);
        if (c == exchangeView) this.remove(c);
    }
    public Component getRackView() {
        return rackView;
    }

    public Component getExchangeView() {
        return exchangeView;
    }
}
