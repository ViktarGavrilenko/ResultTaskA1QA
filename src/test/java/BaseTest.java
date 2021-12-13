import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.utils.BrowserUtils.goTo;
import static com.example.utils.BrowserUtils.maximizeBrowser;

public class BaseTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String WEB_URL = CONFIG_FILE.getValue("/web").toString();
    private static final String LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();

    @BeforeMethod
    protected void beforeMethod() {
        Logger.getInstance().info("Go to the site " + WEB_URL);
        goTo(String.format(WEB_URL, LOGIN, PASSWORD));
        maximizeBrowser();
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}