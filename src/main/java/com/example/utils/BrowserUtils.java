package com.example.utils;

import aquality.selenium.core.logging.Logger;
import org.openqa.selenium.Cookie;

import java.util.Set;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BrowserUtils {
    private static final String TOKEN = "token";

    public static void goTo(String url) {
        getBrowser().goTo(url);
    }

    public static void maximizeBrowser() {
        getBrowser().maximize();
    }

    public static void switchToAddTab(String tab) {
        for (String tabBrowser : getBrowser().getDriver().getWindowHandles()) {
            if (!tabBrowser.equals(tab)) {
                Logger.getInstance().info(String.format("Switch to '%s' tab", tabBrowser));
                getBrowser().getDriver().switchTo().window(tabBrowser);
            }
        }
    }

    public static void addCookie(String token) {
        Logger.getInstance().info("Add a token to cookies");
        getBrowser().getDriver().manage().addCookie(new Cookie(TOKEN, token));
    }

    public static void switchToTab(String projectTab) {
        Logger.getInstance().info(String.format("Go to the '%s' tab", projectTab));
        getBrowser().getDriver().switchTo().window(projectTab);
    }

    public static byte[] getScreenshot() {
        Logger.getInstance().info("Get screenshot");
        return getBrowser().getScreenshot();
    }

    public static String getBrowserName() {
        Logger.getInstance().info("Get browser name");
        return getBrowser().getBrowserName().name();
    }

    public static String getWindowHandle() {
        Logger.getInstance().info("Get the page handle");
        return getBrowser().getDriver().getWindowHandle();
    }

    public static Set<String> getWindowHandles() {
        Logger.getInstance().info("Get page handles");
        return getBrowser().getDriver().getWindowHandles();
    }

    public static void goBack() {
        Logger.getInstance().info("Go to back");
        getBrowser().goBack();
    }

    public static void refresh() {
        getBrowser().refresh();
    }

    public static void executeJS(String script) {
        Logger.getInstance().info(String.format("Executing JS '%s' method", script));
        getBrowser().executeScript(script);
    }

    public static String getSessionId() {
        Logger.getInstance().info("Get session id");
        return getBrowser().getDriver().getSessionId().toString();
    }
}