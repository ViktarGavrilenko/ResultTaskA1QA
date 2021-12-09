package com.example.utils;

import aquality.selenium.core.logging.Logger;
import com.example.models.TestModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.example.utils.Const.*;
import static com.example.utils.TestUtils.getTestsFromElements;

public class JsoupUtils {
    public static ArrayList<TestModel> getTestsFromHtmlTable(String body) {
        Logger.getInstance().info(GET_TESTS_FROM_HTML);
        ArrayList<TestModel> listTest = new ArrayList<>();
        Document doc = Jsoup.parse(body, StandardCharsets.UTF_8.name());
        for (Element row : doc.getElementsByTag(TR)) {
            Elements cols = row.getElementsByTag(TD);
            if (!cols.isEmpty()) {
                listTest.add(getTestsFromElements(cols));
            }
        }
        return listTest;
    }
}