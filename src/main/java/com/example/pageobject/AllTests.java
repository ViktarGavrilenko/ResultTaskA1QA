package com.example.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import com.example.models.TestModel;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static com.example.utils.JsoupUtils.getTestsFromHtmlTable;

public class AllTests extends Form {
    private final ITextBox TABLE =
            getElementFactory().getTextBox(By.xpath("//table[@class='table']/.."), "Table");
    private final ILink LINK_TEST =
            getElementFactory().getLink(By.xpath("//table[@id='allTests']//a"), "LinkTest");

    private static final String INNER_HTML = "innerHTML";

    public AllTests() {
        super(By.id("allTests"), "AllTests");
    }

    public ArrayList<TestModel> getTestsFromPage() {
        isTestDisplayed();
        return getTestsFromHtmlTable(TABLE.getElement().getAttribute(INNER_HTML));
    }

    public boolean isTestDisplayed() {
        return LINK_TEST.state().waitForDisplayed();
    }
}