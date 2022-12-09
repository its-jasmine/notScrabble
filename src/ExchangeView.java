/**
 * Represents the exchange rack in the GUI.
 * @author Jasmine Gad El Hak
 * @version Milestone4
 */

public class ExchangeView extends RackView {
    public ExchangeView(Rack rack) {
        super(rack);
    }
    @Override
    public void setModel(){
        setModel(this.getRack().getExchangeModel());
    }
}
