package persistence;

import model.*;

import java.io.IOException;
import org.json.*;

// Based on: JsonSerializationDemo
// Represents a reader that reads mediaLibrary from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs reader that reads from source file
    public JsonReader(String source) {
        // stub;
    }

    public String getSource() {
        return ""; // stub
    }

    // EFFECTS: reads media library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MediaLibrary read() throws IOException {
        return new MediaLibrary(); // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return ""; // stub
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private MediaLibrary parseMediaLibrary(JSONObject json) {
        return new MediaLibrary(); // stub
    }

    // MODIFIES: ml
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addMediaItems(MediaLibrary ml, JSONObject json) {
        // stub
    }

    // MODIFIES: ml
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addMedia(MediaLibrary ml, JSONObject json) {
        // stub
    }
}

