package com.example.utils;

import com.example.models.TestModel;

import java.util.List;
import java.util.Map;

import static com.example.utils.HtmlTableColumnNames.*;

public class TestUtils {
    public static TestModel getTestsFromMap(Map<String, String> tuple) {
        TestModel test = new TestModel();
        test.name = tuple.get(COLUMN_NAME.getRequest());
        test.method = tuple.get(COLUMN_METHOD.getRequest());
        test.status = tuple.get(COLUMN_STATUS.getRequest());
        test.startTime = tuple.get(COLUMN_START_TIME.getRequest());
        test.endTime = tuple.get(COLUMN_END_TIME.getRequest());
        test.duration = tuple.get(COLUMN_DURATION.getRequest());
        return test;
    }

    public static boolean isTestsInListApi(List<TestModel> pageTests, List<TestModel> apiTests) {
        pageTests.removeAll(apiTests);
        return (pageTests.isEmpty());
    }
}