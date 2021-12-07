package com.example.pageobject;

import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class Projects {

    private static final ITextBox listGroup =
            getElementFactory().getTextBox(By.className("list-group"), "ListGroup");

    public static boolean isDisplayed() {
        return listGroup.state().isDisplayed();
    }
}