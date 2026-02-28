package persistence;

import org.json.JSONObject;

// Based on: JsonSerializationDemo
// Represents the behaviour of an object that can be serialized to JSON format
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
