import javax.json.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;

/*
An
 */
public class BoardConfiguration {
    public enum BoardConfigType {
        Basic, Expert
    }
    public static final HashMap<BoardConfigType, String> boardConfigFiles = new HashMap<>() {{
        put(BoardConfigType.Basic, "basicConfig.json");
        put(BoardConfigType.Expert, "expertConfig.json");

    }};
    private String name;
    private JsonObject config;

    public BoardConfiguration(BoardConfigType t) throws Exception {
        JsonReader jsonReader = Json.createReader(new FileInputStream(boardConfigFiles.get(t)));
        JsonObject data = jsonReader.readObject();
        name = data.getString("name").toString();
        config = data.getJsonObject("config");

        if (config.size() != Coordinate.Row.values().length) throw new Exception("The config file is not of the required format");
        for (JsonValue v : config.values()){
            if (v.asJsonArray().size() != Coordinate.Column.values().length) new Exception("The config file is not of the required format");
        }
    }

    public DefaultTableModel generateDefaultTableModel() {

        DefaultTableModel m = new DefaultTableModel();
        JsonArray rawRowData;
        for (Coordinate.Row r : Coordinate.Row.values()){
            //System.out.println(r.name());
            rawRowData = config.getJsonArray(r.name());
            Object[] rowAsString = Arrays.stream(rawRowData.toArray()).map(v->v.toString()).toArray();

            //System.out.println(rowData.toArray()[0].getClass());
            //JsonString[] rowOfJsonStrings = (JsonString[]) rowData.toArray();
            Square[] rowOfSquares = (Square[]) Arrays.stream(rowAsString).map(s ->new Square(Square.Type.valueOf((String)s))).toArray();
            m.addRow(rowOfSquares);

            //System.out.println(rowData);

        }

        return m;
    }

    public static void main(String[] args) {
        BoardConfiguration b =null;
        try {
            b = new BoardConfiguration(BoardConfigType.Basic);
            /*String s = "   ";
            for (Coordinate.Column c : Coordinate.Column.values()) {
                s += c + "  ";
            }
            s += "\n";
            for (Coordinate.Row r : Coordinate.Row.values()) {
                int row = r.ordinal() + 1;
                if(row < 10) s += row + "  "; else s += row + " "; // keeps columns straight
                for (Coordinate.Column c : Coordinate.Column.values()) {
                    s += m.getValueAt(r.ordinal(), c.ordinal()) + "  "; // Each square will be separated by two spaces.
                }
                s += "\n";
            }
            System.out.println(s);*/
        } catch (Exception e){
            System.out.println("Exception!");
        }
        DefaultTableModel m = b.generateDefaultTableModel();


    }

}

