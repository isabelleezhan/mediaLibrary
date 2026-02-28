package persistence;

import model.MediaLibrary;
import java.io.*;

// Based on: JsonSerializationDemo
// Represents a writer that writes the JSON representation of mediaLibrary to file 
public class JsonWriter {

    // EFFECTS: constructs writer to write to file specified in filePath
    public JsonWriter(String filePath) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: creates printwriter pointed at file, 
    // or throws FileNotFoundException if path doesn't exist
    public void open() throws FileNotFoundException {
        // stub;
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of lib to file
    public void write(MediaLibrary lib) {
        // stub;
    }

    // MODIFIES: this
    // EFFECTS: closes writer 
    public void close() {
        // stub;
    }

    // MODIFIES: this
    // EFFECTS: writes string to json file
    private void saveToFile(String json) {
        // stub
    }
}
