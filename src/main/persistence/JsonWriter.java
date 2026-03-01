package persistence;

import model.MediaLibrary;
import org.json.JSONObject;

import java.io.*;

// Based on: JsonSerializationDemo
// Represents a writer that writes the JSON representation of mediaLibrary to file 
public class JsonWriter {

    private static final int TAB = 4; 
    private PrintWriter writer; 
    private String filePath; 

    // EFFECTS: constructs writer to write to file specified in filePath
    public JsonWriter(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    // MODIFIES: this
    // EFFECTS: creates printwriter pointed at file, 
    // or throws FileNotFoundException if path doesn't exist
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filePath));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of lib to file
    public void write(MediaLibrary lib) {
        JSONObject json = lib.toJson(); 
        saveToFile(json.toString(TAB)); 
    }

    // MODIFIES: this
    // EFFECTS: closes writer and flushes buffered content to disk
    public void close() {
        writer.close(); 
    }

    // MODIFIES: this
    // EFFECTS: writes string to json file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

