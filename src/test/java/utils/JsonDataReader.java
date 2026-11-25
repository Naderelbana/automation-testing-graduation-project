package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonDataReader {
    private static final JsonObject root;

    static {
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (input == null) {
                throw new RuntimeException("testdata.json not found");
            }
            root = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load testdata.json", e);
        }
    }

    public static JsonObject getObject(String key) {
        return root.getAsJsonObject(key);
    }

    public static String getString(String key) {
        return root.get(key).getAsString();
    }
}