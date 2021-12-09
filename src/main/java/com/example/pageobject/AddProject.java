package com.example.pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class AddProject {
    private static final ITextBox INPUT_NAME_PROJECT =
            getElementFactory().getTextBox(By.id("projectName"), "InputNameProject");

    private static final IButton BUTTON_SAVE =
            getElementFactory().getButton(By.xpath("//button[@type='submit']"), "ButtonSave");

    private static final ITextBox INFO_MESSAGE =
            getElementFactory().getTextBox(By.xpath("//div[@class='alert alert-success']"), "InfoMessage");

    public static void writeNameProject(String nameProject) {
        INPUT_NAME_PROJECT.type(nameProject);
    }

    public static void clickSave() {
        BUTTON_SAVE.click();
    }

    public static boolean isSuccessfulMessage(String message) {
        return INFO_MESSAGE.getText().equals(message);
    }
}