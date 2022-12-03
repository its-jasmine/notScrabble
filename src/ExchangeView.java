import javax.swing.*;

public class ExchangeView extends RackView {
    public ExchangeView(Rack rack) {
        super(rack);
    }
    @Override
    public void setModel(){
        setModel(this.getRack().getExchangeModel());
    }
}
