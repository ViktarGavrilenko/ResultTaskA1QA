package com.example.utils;

public enum ContentType {
    IMAGE_PNG("image/png"),
    TEXT_HTML("text/html");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}