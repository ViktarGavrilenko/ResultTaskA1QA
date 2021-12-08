import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.TestModel;
import com.example.utils.PartsOfApiRequests;
import org.apache.hc.core5.http.HttpStatus;
import org.openqa.selenium.Cookie;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.apiresponses.ProcessingApiResponses.*;
import static com.example.pageobject.AddProject.*;
import static com.example.pageobject.AllTests.getTestsFromPage;
import static com.example.pageobject.AllTests.isTestDisplayed;
import static com.example.pageobject.Projects.*;
import static com.example.utils.ApiParameters.*;
import static com.example.utils.ApiUtils.sendPost;
import static com.example.utils.BrowserUtils.getComputerName;
import static com.example.utils.BrowserUtils.switchToAddTab;
import static com.example.utils.StringUtils.*;
import static com.example.utils.TestUtils.isTestInListApi;
import static org.testng.Assert.*;

public class TestOfSecondVariant {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String WEB_URL = CONFIG_FILE.getValue("/web").toString();
    private static final String API_URL = CONFIG_FILE.getValue("/api").toString();
    private static final String LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    private static final String TASK_VARIANT = TEST_DATA_FILE.getValue("/taskVariant").toString();
    private static final String NAME_PROJECT = TEST_DATA_FILE.getValue("/nameProject").toString();
    private static final String INFO_MESSAGE = TEST_DATA_FILE.getValue("/infoMessage").toString();
    private static final String TEST_NAME = TEST_DATA_FILE.getValue("/testName").toString();
    protected static final String LOGGER_FILE =
            System.getProperty("user.dir") + TEST_DATA_FILE.getValue("/logger").toString();


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
        getBrowser().getDriver().manage().addCookie(new Cookie("token", token));
        getBrowser().refresh();
        assertEquals(getVariantNumber(), TASK_VARIANT, "Incorrect variant number specified");

        Logger.getInstance().info("Go to project " + NAME_PROJECT);
        data.clear();
        data.put(projectId.toString(), getProjectId(NAME_PROJECT));
        List<TestModel> listTestsFromApi = getTests(API_URL, data);
        goToProject(NAME_PROJECT);
        List<TestModel> listTestsFromPage = getTestsFromPage();

/*
        List<TestModel> listTestsFromPageSort = listTestsFromPage.stream()
                .sorted(Comparator.comparing(TestModel::getStartTime))
                .collect(Collectors.toList());

        assertEquals(listTestsFromPage,  listTestsFromPage.stream()
                .sorted(Comparator.comparing(TestModel::getStartTime))
                .collect(Collectors.toList()), "Tests are not sorted in descending order of date");
*/

        assertTrue(isTestInListApi(listTestsFromPage, listTestsFromApi),
                "Tests do not match those returned by the api request");

        getBrowser().goBack();
        clickButtonAdd();
        String projectTab = getBrowser().getDriver().getWindowHandle();
        switchToAddTab(projectTab);
        String nameProject = getProjectName() + generateRandomText();
        writeNameProject(nameProject);
        clickSave();
        assertTrue(isSaveMessage(String.format(INFO_MESSAGE, nameProject)), "Project not saved");
        getBrowser().executeScript("window.close();");
        ArrayList<String> numberTabs = new ArrayList<>(getBrowser().getDriver().getWindowHandles());
        assertEquals(numberTabs.size(), 1, "Tab is not closed");
        getBrowser().getDriver().switchTo().window(projectTab);
        getBrowser().refresh();
        assertTrue(getLinkProject(nameProject).state().isDisplayed(), "Project not added to the list");

        goToProject(nameProject);
        data.clear();
        data.put(SID.toString(), getBrowser().getDriver().getSessionId().toString());
        data.put(projectName.toString(), nameProject);
        data.put(testName.toString(), TEST_NAME);
        data.put(methodName.toString(), getClass().getName() + "#" +
                Thread.currentThread().getStackTrace()[1].getMethodName());
        data.put(env.toString(), getComputerName());
        data.put(browser.toString(), getBrowser().getBrowserName().name());
        String idTest = createTestRecord(API_URL,data);

        data.clear();
        data.put(testId.toString(), idTest);
        data.put(content.toString(), getLogOfTest(LOGGER_FILE));
        sendLogs(API_URL, data);

        data.clear();
        data.put(testId.toString(), idTest);
        data.put(content.toString(), Base64.getEncoder().encodeToString(getBrowser().getScreenshot()));
        data.put(contentType.toString(), "image/png" );
        sendApp(API_URL, data);
        assertTrue(isTestDisplayed(), "Test is not displayed");

    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
