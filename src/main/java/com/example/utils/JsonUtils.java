package com.example.utils;

import aquality.selenium.core.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.utils.Const.JSON_INCORRECT;

public class JsonUtils {
    public static boolean isJSONValid(String jsonAnswer) {
        try {
            new JSONObject(jsonAnswer);
        } catch (JSONException ex) {
            try {
                new JSONArray(jsonAnswer);
            } catch (JSONException ex1) {
                Logger.getInstance().info(JSON_INCORRECT);
                return false;
            }
        }
        return true;
    }
}