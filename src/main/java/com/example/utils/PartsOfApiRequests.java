package com.example.utils;

public enum PartsOfApiRequests {
    GET_TOKEN("/token/get");

    private final String partOfApiRequest;

    PartsOfApiRequests(String partOfApiRequest) {
        this.partOfApiRequest = partOfApiRequest;
    }

    public String getRequest() {
        return partOfApiRequest;
    }
}