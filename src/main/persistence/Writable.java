package persistence;

import org.json.JSONObject;

// Interface that is implemented by any custom object that needs the custom method, toJson() for
// JSON serialization.
// **NOTE** This interface is taken directly from Dr. Paul Carter's JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
