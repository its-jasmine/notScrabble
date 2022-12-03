import javax.json.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
/**
 * Class responsible for extracting board configuration data from .json files.
 * Please see User Manual for information regarding required Json file format.
 * @author Jasmine Gad El Hak
 * @version Milestone4
 */
public class BoardConfiguration {
    /** The different board configuration types */
    public enum Type {
        Basic, // class scrabble board
        Expert, // no premium squares
        Smile, // smiley face of premium squares
        ExternalFile // external config file possibly provided by user
    }

    /** Game-provided BoardConfigTypes and their corresponding fileNames */
    public static final HashMap<Type, String> boardConfigFiles = new HashMap<>() {{
        put(Type.Basic, "basicConfig.json");
        put(Type.Expert, "expertConfig.json");
        put(Type.Smile, "smileConfig.json");


    }};
    /** The type of board configuration */
    private Type boardConfigType;
    /** The board configuration data */
    private JsonObject configJson;


    /**
     * Creates a new board configuration of the specified type.
     *
     * @param t The type of board configuration being instatiated.
     * @throws IOException        thrown when json config file cannot be found.
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    public BoardConfiguration(Type t) throws IOException {
        boardConfigType = t;
        JsonReader jsonReader = Json.createReader(new FileInputStream(boardConfigFiles.get(t)));
        configJson = convertDataToJsonObject(jsonReader);
    }

    /**
     * Creates a new board configuration sourced from the given file name.
     *
     * @param fileName The name of the json file contain the board configuration data.
     * @throws IOException        thrown when json config file cannot be found.
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    public BoardConfiguration(String fileName) throws IOException {
        boardConfigType = Type.ExternalFile;
        JsonReader jsonReader = Json.createReader(new FileInputStream(fileName));
        configJson = convertDataToJsonObject(jsonReader);
    }

    /**
     * Converts data read from .json file into JsonObject.
     *
     * @param jsonReader The json reader initialized to read from desired board config json file.
     * @return JsonObject contained the baord config data
     * @throws InstantiationError thrown when json config file is not of the required 15x15 grid format
     */
    private static JsonObject convertDataToJsonObject(JsonReader jsonReader) throws InstantiationError {
        JsonObject data = jsonReader.readObject();

        JsonObject configJsonToValidate = data.getJsonObject("config");
        if (configJsonToValidate.size() != Coordinate.Row.values().length)
            throw new InstantiationError("The config file is not of the required format");
        for (JsonValue v : configJsonToValidate.values()) {
            if (v.asJsonArray().size() != Coordinate.Column.values().length)
               throw new InstantiationError("The config file is not of the required format");
        }
        return configJsonToValidate;
    }

    /**
     * Creates a default table model of Squares based on this board configuration.
     *
     * @return generated DefaultTableModel that represents this BoardConfiguration
     */
    public DefaultTableModel generateDefaultTableModel() {
        DefaultTableModel m = new DefaultTableModel(Coordinate.Row.values().length, Coordinate.Column.values().length) {
            //  renderers to be used based on Class
            public Class<Square> getColumnClass(int column) {
                return Square.class;
            }
        };
        JsonArray rawRowData;
        for (Coordinate.Row r : Coordinate.Row.values()) {
            rawRowData = configJson.getJsonArray(r.name());
            String[] rowAsString = Arrays.stream(rawRowData.toArray()).map(v -> v.toString().replace("\"", "")).toArray(String[]::new);
            Square[] rowAsSquares = Arrays.stream(rowAsString).map(s -> new Square(Square.Type.valueOf(s))).toArray(Square[]::new);

            for (int i = 0; i < rowAsSquares.length; i++) {
                m.setValueAt(rowAsSquares[i], r.ordinal(), i);
            }
        }
        return m;
    }
}



