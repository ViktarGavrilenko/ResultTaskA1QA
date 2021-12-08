package com.example.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import com.example.models.TestModel;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static aquality.selenium.browser.AqualityServices.getElementFactory;
import static com.example.utils.JsoupUtils.getTestsFromHtml;

public class AllTests {
    private static final ITextBox table =
            getElementFactory().getTextBox(By.xpath("//table[@class='table']"), "Table");

    private static final ILink linkTest =
            getElementFactory().getLink(By.xpath("//table[@id='allTests']//a"), "LinkTest");

    public static ArrayList<TestModel> getTestsFromPage() {
        isTestDisplayed();
        return getTestsFromHtml("<table>" + table.getElement().getAttribute("innerHTML") + "</table>");
    }

    public static boolean isTestDisplayed() {
        return linkTest.state().waitForDisplayed();
    }
}
