package Utils;
import Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class Validation
{
    private final WebDriver driver;
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used = false; // Flag to track usage

    public Validation(WebDriver driver)
    {
        this.driver = driver;
    }

    public void assertTrue(boolean condition, String message) {
        used = true;
        softAssert.assertTrue(condition, message);
    }

    public void assertFalse(boolean condition, String message) {
        used = true;
        softAssert.assertFalse(condition, message);
    }

    public void assertEquals(String actual, String expected, String message) {
        used = true;
        softAssert.assertEquals(actual, expected, message);
    }
    public static void assertAll(ITestResult result) {
        if (!used) return;
        try {
            softAssert.assertAll();
        }
        catch (AssertionError e)
        {
            LogsManager.error("Assertion failed:", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }
}
