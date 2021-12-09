package com.example.utils;

public enum HtmlTableColumnNames {
    COLUMN_NAME("Test name"),
    COLUMN_METHOD("Test method"),
    COLUMN_STATUS("Latest test result"),
    COLUMN_START_TIME("Latest test start time"),
    COLUMN_END_TIME("Latest test end time"),
    COLUMN_DURATION("Latest test duration (H:m:s.S)");

    private final String columnName;

    HtmlTableColumnNames(String columnName) {
        this.columnName = columnName;
    }

    public String getRequest() {
        return columnName;
    }
}