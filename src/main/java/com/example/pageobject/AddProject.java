package com.example.pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;
import static com.example.utils.StringUtils.getProjectName;

public class AddProject {
    private static final ITextBox inputNameProject =
            getElementFactory().getTextBox(By.id("projectName"), "InputNameProject");

    private static final IButton buttonSave =
            getElementFactory().getButton(By.xpath("//button[@type='submit']"), "buttonSave");

    private static final ITextBox infoMessage =
            getElementFactory().getTextBox(By.xpath("//div[@class='alert alert-success']"), "InfoMessage");

    public static void writeNameProject(String nameProject){
        inputNameProject.type(nameProject);
    }

    public static void clickSave(){
        buttonSave.click();
    }

    public static boolean isSaveMessage(String message){
        Logger.getInstance().info("message " + message);
        Logger.getInstance().info("infoMessage.getText() " + infoMessage.getText());
        return infoMessage.getText().equals(message);
    }
}
