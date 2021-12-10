package com.example.apiresponses;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.TestModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.utils.ApiUtils.sendPost;
import static com.example.utils.JsonUtils.isJSONValid;

public class ProcessingApiResponses {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final String API_URL = CONFIG_FILE.getValue("/api").toString();
    private static final String NOT_DESERIALIZE = "Could not deserialize: ";
    private static final String JSON_INCORRECT = "JSON response is incorrect";

    public static String getToken(Map<String, String> data) {
        HttpResponse<String> response = sendPost(API_URL + PartsOfApiRequests.GET_TOKEN.getRequest(), data);
        return response.body();
    }

    public static ArrayList<TestModel> getTestsInJson(Map<String, String> data) {
        HttpResponse<String> response =
                sendPost(API_URL + PartsOfApiRequests.GET_LIST_TESTS_JSON.getRequest(), data);
        assert isJSONValid(response.body()) : JSON_INCORRECT;
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<TestModel>>() {
            });
        } catch (IOException e) {
            Logger.getInstance().info(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static String createTestRecord(Map<String, String> data) {
        HttpResponse<String> response =
                sendPost(API_URL + PartsOfApiRequests.CREATE_TEST_RECORD.getRequest(), data);
        return response.body();
    }

    public static void sendLogs(Map<String, String> data) {
        sendPost(API_URL + PartsOfApiRequests.SEND_LOGS.getRequest(), data);
    }

    public static void sendApp(Map<String, String> data) {
        sendPost(API_URL + PartsOfApiRequests.SEND_APP.getRequest(), data);
    }
}