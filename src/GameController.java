import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private Game game;

    public GameController(Game g) {
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
