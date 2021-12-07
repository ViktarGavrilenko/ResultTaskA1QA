import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.utils.PartsOfApiRequests;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.pageobject.Projects.isDisplayed;
import static com.example.utils.ApiParameters.variant;
import static com.example.utils.ApiUtils.sendPost;
import static org.testng.Assert.*;

public class TestOfSecondVariant {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String WEB_URL = CONFIG_FILE.getValue("/web").toString();
    private static final String API_URL = CONFIG_FILE.getValue("/api").toString();
    private static final String LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    private static final String TASK_VARIANT = TEST_DATA_FILE.getValue("/taskVariant").toString();

    @Test(description = "Testing second variant")
    public void testOfSecondVariant() {
        Logger.getInstance().info("Get a token by API request");
        Map<String, String> data = new HashMap<>();
        data.put(variant.toString(), TASK_VARIANT);
        HttpResponse<String> response = sendPost(API_URL + PartsOfApiRequests.GET_TOKEN.getRequest(), data);
        assertEquals(response.statusCode(), HttpStatus.SC_OK, "The request was not successful");
        String token = response.body();
        assertFalse(token.isEmpty(), "Token not generated");
        Logger.getInstance().info("Token is " + token);

        Logger.getInstance().info("Go to the site " + WEB_URL);
        getBrowser().goTo(String.format(WEB_URL, LOGIN, PASSWORD));
        getBrowser().maximize();
        assertTrue(isDisplayed(), "Authorization completed");
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
