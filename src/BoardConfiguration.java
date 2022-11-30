import javax.json.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/*
Class responsible for extracting board configuration data from Json files.
Please see User Manual for information regarding required Json file format.

 */
public class BoardConfiguration {
    public enum BoardConfigType {
        Basic, // class scrabble board
        Expert, // no premium squares
        Smile, // smiley face of premium squares
        ExternalFile // external config file possibly provided by user
    }
    /** Game-provided BoardConfigTypes and their corresponding fileNames.*/
    public static final HashMap<BoardConfigType, String> boardConfigFiles = new HashMap<>() {{
        put(BoardConfigType.Basic, "basicConfig.json");
        put(BoardConfigType.Expert, "expertConfig.json");
        put(BoardConfigType.Smile, "smileConfig.json");



    }};
    /** The type of board configuration */
    private BoardConfigType boardConfigType;
    /** The board configuration data */
    private JsonObject configJson;


    /**
     * Creates a new board configuration of the specified type.
     * @param t The type of board configuration being instatiated.
     * @throws IOException thrown when json config file cannot be found.
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    public BoardConfiguration(BoardConfigType t)  throws IOException, InstantiationError{
        boardConfigType = t;
        JsonReader jsonReader = Json.createReader(new FileInputStream(boardConfigFiles.get(t)));
        configJson = convertDataToJsonObject(jsonReader);
        if (configJson == null) throw new InstantiationError("The config file is not of the required format");

        //System.out.println("ERROR: Board creation was unsuccessfull using Board Configuration Type " + t.name());
    }
    /**
     * Creates a new board configuration sourced from the given file name.
     * @param fileName The name of the json file contain the board configuration data.
     * @throws IOException thrown when json config file cannot be found.
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    public BoardConfiguration(String fileName) throws IOException, InstantiationError {
        boardConfigType = BoardConfigType.ExternalFile;
        JsonReader jsonReader = Json.createReader(new FileInputStream(fileName));
        configJson = convertDataToJsonObject(jsonReader);
    }

    /**
     *
     * @param jsonReader The json reader initialized to read from desired board config json file.
     * @return JsonObject contained the baord config data
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    private static JsonObject convertDataToJsonObject(JsonReader jsonReader) throws InstantiationError {
        JsonObject data = jsonReader.readObject();

        JsonObject configJsonToValidate = data.getJsonObject("config");
        if (configJsonToValidate.size() != Coordinate.Row.values().length) throw new InstantiationError("The config file is not of the required format");
        for (JsonValue v : configJsonToValidate.values()){
            if (v.asJsonArray().size() != Coordinate.Column.values().length) throw new InstantiationError("The config file is not of the required format");
        }
        return configJsonToValidate;
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
            rawRowData = configJson.getJsonArray(r.name());
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
        BoardConfiguration b3 =null;
        BoardConfiguration b4 =null;



        try {
            b = new BoardConfiguration(BoardConfigType.Basic);
            b2 = new BoardConfiguration(BoardConfigType.Expert);
            b4 = new BoardConfiguration(BoardConfigType.Smile);
            b3 = new BoardConfiguration("nonexistent.json");

        } catch (Exception e){
            System.out.println("Exception!");
        }

        System.out.println(b);
        System.out.println(b2);
        System.out.println(b4);
    }

}

