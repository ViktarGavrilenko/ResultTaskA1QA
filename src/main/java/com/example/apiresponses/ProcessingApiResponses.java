package com.example.apiresponses;

import aquality.selenium.core.logging.Logger;
import com.example.models.TestModel;
import com.example.utils.PartsOfApiRequests;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.example.utils.ApiUtils.sendPost;

public class ProcessingApiResponses {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NOT_DESERIALIZE = "Could not deserialize: ";

    public static ArrayList<TestModel> getTests(String apiUrl, Map<String, String> data) {
        HttpResponse<String> response =
                sendPost(apiUrl + PartsOfApiRequests.GET_LIST_TESTS_JSON.getRequest(), data);
        Logger.getInstance().info("Status code " + response.statusCode());
        Logger.getInstance().info(response.body());
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<TestModel>>() {
            });
        } catch (IOException e) {
            Logger.getInstance().info(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static String createTestRecord(String apiUrl, Map<String, String> data) {
        HttpResponse<String> response =
                sendPost(apiUrl + PartsOfApiRequests.CREATE_TEST_RECORD.getRequest(), data);
        return response.body();
    }

    public static void sendLogs(String apiUrl, Map<String, String> data) {
        sendPost(apiUrl + PartsOfApiRequests.SEND_LOGS.getRequest(), data);
    }

    public static void sendApp(String apiUrl, Map<String, String> data) {
        sendPost(apiUrl + PartsOfApiRequests.SEND_APP.getRequest(), data);
    }

}

