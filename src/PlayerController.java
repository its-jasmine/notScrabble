import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController implements ActionListener {
    private Game game;

    public PlayerController(Game g) {
        game = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")){
            game.submit();
        }
        if (e.getActionCommand().equals("Pass")){
            game.passTurn();
        }
    }
}
