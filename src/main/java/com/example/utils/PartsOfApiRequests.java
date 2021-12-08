package com.example.utils;

public enum PartsOfApiRequests {
    GET_TOKEN("/token/get"),
    GET_LIST_TESTS_JSON("/test/get/json"),
    CREATE_TEST_RECORD("/test/put"),
    UPDATE_STATUS("/test/update"),
    SEND_LOGS("/test/put/log"),
    SEND_APP("/test/put/attachment");


    private final String partOfApiRequest;

    PartsOfApiRequests(String partOfApiRequest) {
        this.partOfApiRequest = partOfApiRequest;
    }

    public String getRequest() {
        return partOfApiRequest;
    }
}