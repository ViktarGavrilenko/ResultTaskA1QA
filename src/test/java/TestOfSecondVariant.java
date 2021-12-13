import aquality.selenium.core.logging.Logger;
import com.example.models.TestModel;
import com.example.pageobject.AddProject;
import com.example.pageobject.AllTests;
import com.example.pageobject.Projects;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.apiresponses.ApiParameters.*;
import static com.example.apiresponses.ProcessingApiResponses.*;
import static com.example.utils.BrowserUtils.*;
import static com.example.utils.ContentType.IMAGE_PNG;
import static com.example.utils.StringUtils.*;
import static com.example.utils.TestUtils.isTestsInListApi;
import static org.testng.Assert.*;

public class TestOfSecondVariant extends BaseTest {
    private static final String TASK_VARIANT = TEST_DATA_FILE.getValue("/taskVariant").toString();
    private static final String NAME_PROJECT = TEST_DATA_FILE.getValue("/nameProject").toString();
    private static final String SUCCESSFUL_MESSAGE = TEST_DATA_FILE.getValue("/successfulMessage").toString();
    private static final String TEST_NAME = TEST_DATA_FILE.getValue("/testName").toString();
    private static final String LOGGER_FILE =
            System.getProperty("user.dir") + CONFIG_FILE.getValue("/logger").toString();

    private static final String WINDOWS_CLOSE = "window.close();";

    private static final AddProject ADD_PROJECT = new AddProject();
    private static final AllTests ALL_TESTS = new AllTests();
    private static final Projects PROJECTS = new Projects();

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

        assertTrue(PROJECTS.isDisplayedPageProjects(), "Authorization completed");
        addCookie(token);
        refresh();
        assertEquals(PROJECTS.getVariantNumber(), TASK_VARIANT, "Incorrect variant number specified");
        Logger.getInstance().info("Completed STEP 2");

        Logger.getInstance().info("Start STEP 3");
        Logger.getInstance().info("Go to project " + NAME_PROJECT);
        String idProject = PROJECTS.getProjectId(NAME_PROJECT);
        PROJECTS.goToProject(NAME_PROJECT);
        data.clear();
        data.put(projectId.toString(), idProject);
        List<TestModel> listTestsFromApi = getTestsInJson(data);
        List<TestModel> listTestsFromPage = ALL_TESTS.getTestsFromPage();
        List<TestModel> listTestsFromPageSort = listTestsFromPage.stream()
                .sorted(Comparator.comparing(TestModel::getStartTime).reversed())
                .collect(Collectors.toList());
        assertEquals(listTestsFromPage, listTestsFromPageSort, "Tests are not sorted in descending order of date");
        assertTrue(isTestsInListApi(listTestsFromPage, listTestsFromApi),
                "Tests do not match those returned by the api request");
        Logger.getInstance().info("Completed STEP 3");

        Logger.getInstance().info("Start STEP 4");
        goBack();
        PROJECTS.clickButtonAdd();
        String projectTab = getWindowHandle();
        switchToAddTab(projectTab);
        String nameProject = getProjectName() + generateRandomText();
        ADD_PROJECT.writeNameProject(nameProject);
        ADD_PROJECT.clickSave();
        assertTrue(ADD_PROJECT.isSuccessfulMessage(String.format(SUCCESSFUL_MESSAGE, nameProject)), "Project not saved");
        executeJS(WINDOWS_CLOSE);
        ArrayList<String> numberTabs = new ArrayList<>(getWindowHandles());
        assertEquals(numberTabs.size(), 1, "Tab is not closed");
        switchToTab(projectTab);
        refresh();
        assertTrue(PROJECTS.getLinkProject(nameProject).state().isDisplayed(), "Project not added to the list");
        Logger.getInstance().info("Completed STEP 4");

        Logger.getInstance().info("Start STEP 5");
        PROJECTS.goToProject(nameProject);
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
        data.put(contentType.toString(), IMAGE_PNG.getContentType());
        sendApp(data);

        assertTrue(ALL_TESTS.isTestDisplayed(), "Test is not displayed");
        ArrayList<TestModel> test = ALL_TESTS.getTestsFromPage();
        assertEquals(test.get(0).name, TEST_NAME, "Test names do not match");
        assertEquals(test.get(0).method, method, "Test method do not match");
        Logger.getInstance().info("Completed STEP 5");
    }
}