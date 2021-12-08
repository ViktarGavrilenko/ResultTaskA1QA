package com.example.utils;

import aquality.selenium.core.logging.Logger;
import com.example.models.TestModel;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static TestModel getTestsFromElements(Elements cols){
        TestModel test = new TestModel();
        test.name = cols.get(0).text();
        test.method = cols.get(1).text();
        test.status = cols.get(2).text().toUpperCase();
        test.startTime = cols.get(3).text();
        test.endTime = cols.get(4).text();
        test.duration = cols.get(5).text();
        return test;
    }

    public static boolean isTestInListApi(List<TestModel> pageTests, List<TestModel> apiTests) {
        int countTests = 0;
        apiTests.removeAll(pageTests);
        Logger.getInstance().info("Количество тестов " + apiTests.size());
/*        for (TestModel pageTest : pageTests) {
            for (TestModel apiTest : apiTests) {
                if (pageTest.equals(apiTest)) {
                    countTests++;
                    break;
                }
            }
        }*/
        Logger.getInstance().info("Number of identical tests " + countTests);
        return (countTests == pageTests.size());
    }
}
