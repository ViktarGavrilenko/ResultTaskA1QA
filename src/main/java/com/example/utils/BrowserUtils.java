package com.example.utils;

import java.util.Map;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BrowserUtils {
    public static void switchToAddTab(String tab) {
        for (String tabBrowser : getBrowser().getDriver().getWindowHandles()) {
            if (!tabBrowser.equals(tab)) {
                getBrowser().getDriver().switchTo().window(tabBrowser);
            }
        }
    }

    public static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown Computer");
    }
}
