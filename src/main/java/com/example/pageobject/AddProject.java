package com.example.pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddProject extends Form {
    private final ITextBox INPUT_NAME_PROJECT =
            getElementFactory().getTextBox(By.id("projectName"), "InputNameProject");

    private final IButton BUTTON_SAVE =
            getElementFactory().getButton(By.xpath("//button[@type='submit']"), "ButtonSave");

    private final ITextBox INFO_MESSAGE =
            getElementFactory().getTextBox(By.xpath("//div[@class='alert alert-success']"), "InfoMessage");

    public AddProject() {
        super(By.id("addProjectForm"), "AddProject");
    }

    public void writeNameProject(String nameProject) {
        INPUT_NAME_PROJECT.type(nameProject);
    }

    public void clickSave() {
        BUTTON_SAVE.click();
    }

    public boolean isSuccessfulMessage(String message) {
        return INFO_MESSAGE.getText().equals(message);
    }
}