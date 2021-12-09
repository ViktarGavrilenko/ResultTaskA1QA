package com.example.utils;

import aquality.selenium.core.logging.Logger;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.utils.Const.SWITCH_TO_TAB;

public class BrowserUtils {
    public static void switchToAddTab(String tab) {
        for (String tabBrowser : getBrowser().getDriver().getWindowHandles()) {
            if (!tabBrowser.equals(tab)) {
                Logger.getInstance().info(String.format(SWITCH_TO_TAB, tabBrowser));
                getBrowser().getDriver().switchTo().window(tabBrowser);
            }
        }
    }
}