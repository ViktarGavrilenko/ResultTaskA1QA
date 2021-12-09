package com.example.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import com.example.models.TestModel;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static aquality.selenium.browser.AqualityServices.getElementFactory;
import static com.example.utils.Const.INNER_HTML;
import static com.example.utils.JsoupUtils.getTestsFromHtmlTable;

public class AllTests {
    private static final ITextBox TABLE =
            getElementFactory().getTextBox(By.xpath("//table[@class='table']/.."), "Table");

    private static final ILink LINK_TEST =
            getElementFactory().getLink(By.xpath("//table[@id='allTests']//a"), "LinkTest");

    public static ArrayList<TestModel> getTestsFromPage() {
        isTestDisplayed();
        return getTestsFromHtmlTable(TABLE.getElement().getAttribute(INNER_HTML));
    }

    public static boolean isTestDisplayed() {
        return LINK_TEST.state().waitForDisplayed();
    }
}