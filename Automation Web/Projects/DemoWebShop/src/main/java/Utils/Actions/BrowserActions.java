package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions
{
    private final WebDriver driver;
    private final WaitManager wait;
    public BrowserActions(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public void maximizeWindow()
    {
        driver.manage().window().maximize();
    }
    public void minimizeWindow()
    {
        driver.manage().window().minimize();
    }
    public void navigateTo(String url)
    {
        driver.get(url);

    }
    public void refreshPage()
    {
        driver.navigate().refresh();
    }
    public void closeCurrentWindow()
    {
        driver.close();
    }
    public void switchToSpecificWindow(String handle)
    {
        driver.switchTo().window(handle);
    }
    public void openNewWindow ()
    {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }
    public String getCurrentPageURL ()
    {
        return driver.getCurrentUrl();
    }
    public void closeExtensionTab ()
    {
        String currentWindowHandle = driver.getWindowHandle();
        try
        {
            wait.fWait().until(d -> d.getWindowHandles().size() > 1);
            for (String handle : driver.getWindowHandles())
            {
                if (!handle.equals(currentWindowHandle))
                {
                    switchToSpecificWindow(handle);
                    closeCurrentWindow();
                }
            }
            driver.switchTo().window(currentWindowHandle);
            LogsManager.info("Extension tab closed successfully.");
        }
        catch (org.openqa.selenium.TimeoutException e)
        {
            System.out.println("There is a single Tab, No extension tab to close.");
            LogsManager.error("There is a single Tab, No extension tab to close.", e.getMessage());
        }

    }
    public void navigateBack()
    {
        driver.navigate().back();
    }
}
