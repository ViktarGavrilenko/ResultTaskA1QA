package com.example.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import static com.example.utils.StringUtils.getIdFromLink;
import static com.example.utils.StringUtils.getVariant;

public class Projects extends Form {
    private final ITextBox LIST_GROUP =
            getElementFactory().getTextBox(By.className("list-group"), "ListGroup");
    private final ITextBox VERSION =
            getElementFactory().getTextBox(By.xpath("//div[@class='container']//span"), "Version");
    private final ILink BUTTON_ADD =
            getElementFactory().getLink(By.xpath("//div[@class='panel-heading']//a"), "ButtonAdd");

    private static final String LOCATOR_LINK_TO_PROJECT = "//a[text()='%s']";

    public Projects() {
        super(By.className("list-group"), "Projects");
    }

    public boolean isDisplayedPageProjects() {
        return LIST_GROUP.state().isDisplayed();
    }

    public String getVariantNumber() {
        return getVariant(VERSION.getText());
    }

    public ILink getLinkProject(String nameProject) {
        return getElementFactory().getLink(By.xpath(String.format(LOCATOR_LINK_TO_PROJECT, nameProject)),
                String.format("LinkTo%sProject", nameProject));
    }

    public void goToProject(String nameProject) {
        getLinkProject(nameProject).click();
    }

    public String getProjectId(String nameProject) {
        return getIdFromLink(getLinkProject(nameProject).getHref());
    }

    public void clickButtonAdd() {
        BUTTON_ADD.click();
    }
}