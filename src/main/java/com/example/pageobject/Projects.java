package com.example.pageobject;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;
import static com.example.utils.StringUtils.getIdFromLink;
import static com.example.utils.StringUtils.getVariant;

public class Projects {
    private static final ITextBox LIST_GROUP =
            getElementFactory().getTextBox(By.className("list-group"), "ListGroup");
    private static final ITextBox VERSION =
            getElementFactory().getTextBox(By.xpath("//div[@class='container']//span"), "Version");
    private static final ILink BUTTON_ADD =
            getElementFactory().getLink(By.xpath("//div[@class='panel-heading']//a"), "ButtonAdd");

    private static final String LOCATOR_LINK_TO_PROJECT = "//a[text()='%s']";

    public static boolean isDisplayedPageProjects() {
        return LIST_GROUP.state().isDisplayed();
    }

    public static String getVariantNumber() {
        return getVariant(VERSION.getText());
    }

    public static ILink getLinkProject(String nameProject) {
        return getElementFactory().getLink(By.xpath(String.format(LOCATOR_LINK_TO_PROJECT, nameProject)),
                String.format("LinkTo%sProject", nameProject));
    }

    public static void goToProject(String nameProject) {
        getLinkProject(nameProject).click();
    }

    public static String getProjectId(String nameProject) {
        return getIdFromLink(getLinkProject(nameProject).getHref());
    }

    public static void clickButtonAdd() {
        BUTTON_ADD.click();
    }
}