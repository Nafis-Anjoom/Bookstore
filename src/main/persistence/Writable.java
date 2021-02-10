package persistence;

import org.json.JSONObject;

//Denotes objects that can be parsed to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
