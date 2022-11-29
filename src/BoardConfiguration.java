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
        Basic, Expert, ExternalFile
    }
    public static final HashMap<BoardConfigType, String> boardConfigFiles = new HashMap<>() {{
        put(BoardConfigType.Basic, "basicConfig.json");
        put(BoardConfigType.Expert, "expertConfig.json");

    }};
    private BoardConfigType boardConfigType;
    private JsonObject config;

    public BoardConfiguration(BoardConfigType t) throws Exception {
        boardConfigType = t;
        JsonReader jsonReader = Json.createReader(new FileInputStream(boardConfigFiles.get(t)));
        importBoardConfig(jsonReader);
    }

    public BoardConfiguration(String fileName) throws Exception {
        boardConfigType = BoardConfigType.ExternalFile;
        JsonReader jsonReader = Json.createReader(new FileInputStream(fileName));
        importBoardConfig(jsonReader);
    }

    private void importBoardConfig(JsonReader jsonReader) throws Exception {
        JsonObject data = jsonReader.readObject();

        config = data.getJsonObject("config");
        if (config.size() != Coordinate.Row.values().length) throw new Exception("The config file is not of the required format");
        for (JsonValue v : config.values()){
            if (v.asJsonArray().size() != Coordinate.Column.values().length) new Exception("The config file is not of the required format");
        }
    }

    public DefaultTableModel generateDefaultTableModel() {
        DefaultTableModel m = new DefaultTableModel(Coordinate.Row.values().length, Coordinate.Column.values().length){
            //  renderers to be used based on Class
            public Class<Square> getColumnClass(int column)
            {
                return Square.class;
            }
        };
        JsonArray rawRowData;
        for (Coordinate.Row r : Coordinate.Row.values()){
            rawRowData = config.getJsonArray(r.name());
            String[] rowAsString = Arrays.stream(rawRowData.toArray()).map(v->v.toString().replace("\"","")).toArray(String[]::new);
            Square[] rowAsSquares = Arrays.stream(rowAsString).map(s -> new Square(Square.Type.valueOf(s))).toArray(Square[]::new);

            for (int i = 0; i < rowAsSquares.length; i++){
                m.setValueAt(rowAsSquares[i], r.ordinal(), i);
            }
        }
        return m;
    }
    public String toString(){
        DefaultTableModel m = generateDefaultTableModel();
        String s = "   ";
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
        return s;

    }

    public static void main(String[] args) {
        BoardConfiguration b =null;
        BoardConfiguration b2 =null;

        try {
            b = new BoardConfiguration(BoardConfigType.Basic);
            b2 = new BoardConfiguration(BoardConfigType.Expert);

        } catch (Exception e){
            System.out.println("Exception!");
        }
        System.out.println(b);
        System.out.println(b2);
    }

}

