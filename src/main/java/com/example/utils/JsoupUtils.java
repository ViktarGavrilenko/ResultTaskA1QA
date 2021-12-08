package com.example.utils;

import com.example.models.TestModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.utils.TestUtils.getTestsFromElements;

public class JsoupUtils {
    public static ArrayList<TestModel> getTestsFromHtml(String body) {
        ArrayList<TestModel> listTest = new ArrayList<>();
        Document doc = Jsoup.parse(body, "UTF-8");
        for (Element row : doc.getElementsByTag("tr")) {
            Elements cols = row.getElementsByTag("td");
            if (!cols.isEmpty()) {
                listTest.add(getTestsFromElements(cols));
            }
        }
        return listTest;
    }
}
