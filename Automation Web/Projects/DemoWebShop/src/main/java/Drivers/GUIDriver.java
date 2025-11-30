package Drivers;

import Utils.Actions.AlertActions;
import Utils.Actions.BrowserActions;
import Utils.Actions.ElementActions;
import Utils.Actions.FrameActions;
import Utils.Validation;
import Utils.Verification;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver
{
protected static WebDriver driver;
private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();
private static String browser ;


public GUIDriver()
{
    browser = new JsonReader("config").getJsonData("browserType");
    Browsers browserType = Browsers.valueOf(browser.toUpperCase());
    AbstractDriver abstractDriver = browserType.getDriverFactory(); //local
    driver = ThreadGuard.protect(abstractDriver.createDriver());
    localDriver.set(driver);
}
    public ElementActions element() {
        return new ElementActions(get());
    }
    public BrowserActions browser() {
        return new BrowserActions(get());
    }
    public FrameActions frame() {
        return new FrameActions(get());
    }
    public AlertActions alert() {
        return new AlertActions(get());
    }
    public Verification hardAssertions() {
        return new Verification(get());
    }
    public Validation softAssertions() {
        return new Validation(get());
    }

    public static WebDriver get()
    {
        if (localDriver == null)
        {
            LogsManager.error("WebDriver is not initialized. Call GUIDriver constructor first.");
            return null;
        }
        return localDriver.get();
    }

    public void quitDriver()
{
    localDriver.get().quit();
}
    public void closeDriver()
    {
        localDriver.get().close();
    }

}
