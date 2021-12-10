package com.example.utils;

import aquality.selenium.core.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static final String JSON_NOT_VALID = "JSON response is not valid";

    public static boolean isJSONValid(String jsonAnswer) {
        try {
            new JSONObject(jsonAnswer);
        } catch (JSONException ex) {
            try {
                new JSONArray(jsonAnswer);
            } catch (JSONException e) {
                Logger.getInstance().info(JSON_NOT_VALID);
                return false;
            }
        }
        return true;
    }
}