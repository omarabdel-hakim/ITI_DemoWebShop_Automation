package Utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Verification
{
    private final WebDriver driver;
    public Verification(WebDriver driver)
    {
        this.driver = driver;
    }
    public void assertTrue(boolean condition, String message)
    {
        Assert.assertTrue(condition, message);
    }
    public void assertFalse(boolean condition, String message)
    {
        Assert.assertFalse(condition, message);
    }
    public void assertEquals(String actual, String expected, String message)
    {
        Assert.assertEquals(actual, expected, message);
    }
    public void assertNotEquals(String actual, String expected, String message)
    {
        Assert.assertNotEquals(actual, expected, message);
    }
    public void assertPageUrl(String expectedUrl)
    {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "URL does not match. Expected: " + expectedUrl + ", Actual: " + actualUrl);
    }
    public void assertPageTitle(String expectedTitle)
    {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Title does not match. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }
    public void assertFail(String message)
    {
        Assert.fail(message);
    }
}
