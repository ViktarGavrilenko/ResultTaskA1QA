import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.TestModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.apiresponses.ApiParameters.*;
import static com.example.apiresponses.ProcessingApiResponses.*;
import static com.example.pageobject.AddProject.*;
import static com.example.pageobject.AllTests.getTestsFromPage;
import static com.example.pageobject.AllTests.isTestDisplayed;
import static com.example.pageobject.Projects.*;
import static com.example.utils.BrowserUtils.*;
import static com.example.utils.StringUtils.*;
import static com.example.utils.TestUtils.isTestsInListApi;
import static org.testng.Assert.*;

public class TestOfSecondVariant {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String WEB_URL = CONFIG_FILE.getValue("/web").toString();
    private static final String LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    private static final String TASK_VARIANT = TEST_DATA_FILE.getValue("/taskVariant").toString();
    private static final String NAME_PROJECT = TEST_DATA_FILE.getValue("/nameProject").toString();
    private static final String SUCCESSFUL_MESSAGE = TEST_DATA_FILE.getValue("/successfulMessage").toString();
    private static final String TEST_NAME = TEST_DATA_FILE.getValue("/testName").toString();
    private static final String IMAGE_PNG = TEST_DATA_FILE.getValue("/image_png").toString();
    private static final String LOGGER_FILE =
            System.getProperty("user.dir") + TEST_DATA_FILE.getValue("/logger").toString();

    private static final String WINDOWS_CLOSE = "window.close();";

    @Test(description = "Testing second variant")
    public void testOfSecondVariant() {
        Logger.getInstance().info("Start STEP 1");
        Logger.getInstance().info("Get a token by API request");
        Map<String, String> data = new HashMap<>();
        data.put(variant.toString(), TASK_VARIANT);
        String token = getToken(data);
        assertFalse(token.isEmpty(), "Token not generated");
        Logger.getInstance().info("Completed STEP 1");

        Logger.getInstance().info("Start STEP 2");
        Logger.getInstance().info("Go to the site " + WEB_URL);
        goTo(String.format(WEB_URL, LOGIN, PASSWORD));
        maximizeBrowser();
        assertTrue(isDisplayedPageProjects(), "Authorization completed");
        addCookie(token);
        refresh();
        assertEquals(getVariantNumber(), TASK_VARIANT, "Incorrect variant number specified");
        Logger.getInstance().info("Completed STEP 2");

        Logger.getInstance().info("Start STEP 3");
        Logger.getInstance().info("Go to project " + NAME_PROJECT);
        String idProject = getProjectId(NAME_PROJECT);
        goToProject(NAME_PROJECT);
        data.clear();
        data.put(projectId.toString(), idProject);
        List<TestModel> listTestsFromApi = getTestsInJson(data);
        List<TestModel> listTestsFromPage = getTestsFromPage();
        List<TestModel> listTestsFromPageSort = listTestsFromPage.stream()
                .sorted(Comparator.comparing(TestModel::getStartTime).reversed())
                .collect(Collectors.toList());
        assertEquals(listTestsFromPage, listTestsFromPageSort, "Tests are not sorted in descending order of date");
        assertTrue(isTestsInListApi(listTestsFromPage, listTestsFromApi),
                "Tests do not match those returned by the api request");
        Logger.getInstance().info("Completed STEP 3");

        Logger.getInstance().info("Start STEP 4");
        goBack();
        clickButtonAdd();
        String projectTab = getWindowHandle();
        switchToAddTab(projectTab);
        String nameProject = getProjectName() + generateRandomText();
        writeNameProject(nameProject);
        clickSave();
        assertTrue(isSuccessfulMessage(String.format(SUCCESSFUL_MESSAGE, nameProject)), "Project not saved");
        executeJS(WINDOWS_CLOSE);
        ArrayList<String> numberTabs = new ArrayList<>(getWindowHandles());
        assertEquals(numberTabs.size(), 1, "Tab is not closed");
        switchToTab(projectTab);
        refresh();
        assertTrue(getLinkProject(nameProject).state().isDisplayed(), "Project not added to the list");
        Logger.getInstance().info("Completed STEP 4");

        Logger.getInstance().info("Start STEP 5");
        goToProject(nameProject);
        data.clear();
        data.put(SID.toString(), getSessionId());
        data.put(projectName.toString(), nameProject);
        data.put(testName.toString(), TEST_NAME);
        String method = getClass().getName() + "#" + Thread.currentThread().getStackTrace()[1].getMethodName();
        data.put(methodName.toString(), method);
        data.put(env.toString(), getComputerName());
        data.put(browser.toString(), getBrowserName());
        String idTest = createTestRecord(data);

        data.clear();
        data.put(testId.toString(), idTest);
        data.put(content.toString(), getLogOfTest(LOGGER_FILE));
        sendLogs(data);

        data.clear();
        data.put(testId.toString(), idTest);
        data.put(content.toString(), encodingBytesIntoBase64(getScreenshot()));
        data.put(contentType.toString(), IMAGE_PNG);
        sendApp(data);

        assertTrue(isTestDisplayed(), "Test is not displayed");
        ArrayList<TestModel> test = getTestsFromPage();
        assertEquals(test.get(0).name, TEST_NAME, "Test names do not match");
        assertEquals(test.get(0).method, method, "Test method do not match");
        Logger.getInstance().info("Completed STEP 5");
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}