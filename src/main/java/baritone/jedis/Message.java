package baritone.jedis;

import java.lang.Throwable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import baritone.api.utils.Helper;

public class Message implements Helper {
  //
  private String json;
  private JsonObject jsonObject;

  public Message(String json) {
    this.json = json;
    this.jsonObject = deserialize(json);
  }

  public Message(HashMap<String, String> map) throws InvalidMessageArgumentException {
    this(serialize(validate(map)));
  }

  private static HashMap<String, String> validate(HashMap<String, String> map) throws InvalidMessageArgumentException {
    if (map.size() != 3 && map.size() != 4)
      throw new InvalidMessageArgumentException();

    for (String key : map.keySet()) {
      if (key.matches("(from|role|to|payload)"))
        continue;
      throw new InvalidMessageArgumentException();
    }

    return map;
  }

  public Message(String from, String role, Optional<String> to, String payload) {
    this(String.format("{\"from\": \"%s\", \"role\": \"%s\", \"to\": \"%s\", \"payload\": \"%s\"}", from, role,
        to.orElse(""), payload));
  }

  public static JsonObject deserialize(String jsonString) {
    return new Gson().fromJson(jsonString, JsonObject.class);
  }

  public static String serialize(HashMap<String, String> map) {
    return new Gson().toJson(map);
  }

  public JsonObject getJsonObject() {
    return this.jsonObject;
  }

  public String getJson() {
    return this.json;
  }
  /*
   * { sender: uuid, role: role, to?: uuid, payload: msg }
   */
}

private class InvalidMessageArgumentException extends Throwable {
  public InvalidMessageArgumentException() {
    super(
        "Invalid message arguments (in your hashmap) passed to Message.\n\nValid arguments: uuid, role, uuid?, payload");
  }
}