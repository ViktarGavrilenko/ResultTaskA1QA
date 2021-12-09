package com.example.utils;

import com.example.models.TestModel;
import org.jsoup.select.Elements;

import java.util.List;

public class TestUtils {
    public static TestModel getTestsFromElements(Elements cols) {
        TestModel test = new TestModel();
        test.name = cols.get(0).text();
        test.method = cols.get(1).text();
        test.status = cols.get(2).text();
        test.startTime = cols.get(3).text();
        test.endTime = cols.get(4).text();
        test.duration = cols.get(5).text();
        return test;
    }

    public static boolean isTestsInListApi(List<TestModel> pageTests, List<TestModel> apiTests) {
        pageTests.removeAll(apiTests);
        return (pageTests.isEmpty());
    }
}