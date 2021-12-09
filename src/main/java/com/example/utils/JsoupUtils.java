package com.example.utils;

import aquality.selenium.core.logging.Logger;
import com.example.models.TestModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.utils.Const.*;
import static com.example.utils.TestUtils.getTestsFromMap;

public class JsoupUtils {
    public static ArrayList<TestModel> getTestsFromHtmlTable(String body) {
        Logger.getInstance().info(GET_TESTS_FROM_HTML_TABLE);
        Document doc = Jsoup.parse(body, StandardCharsets.UTF_8.name());
        Elements rows = doc.select(TR);
        Elements first = rows.get(0).select(TH_TD);
        List<String> headers = new ArrayList<>();

        for (Element header : first) {
            headers.add(header.text());
        }

        ArrayList<TestModel> listTest = new ArrayList<>();

        for (int row = 1; row < rows.size(); row++) {
            Elements colVals = rows.get(row).select(TH_TD);
            int colCount = 0;
            Map<String, String> tuple = new HashMap<>();
            for (Element colVal : colVals) {
                tuple.put(headers.get(colCount++), colVal.text());
            }
            listTest.add(getTestsFromMap(tuple));
        }

        return listTest;
    }
}