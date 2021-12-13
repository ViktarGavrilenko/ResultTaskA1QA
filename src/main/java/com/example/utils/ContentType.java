package com.example.utils;

public enum ContentType {
    IMAGE_PNG("image/png"),
    TEXT_HTML("text/html");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return type;
    }
}