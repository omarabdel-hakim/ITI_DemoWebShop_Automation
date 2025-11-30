package Media;

import Utils.Logs.LogsManager;
import Utils.Reports.AllureAttachmentManager;
import Utils.TimeManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenShot
{
    public static final String SCREENSHOT_PATH = "Output_data/ScreenShots/";

    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOT_PATH + screenshotName + "-" + TimeManager.getTimesTamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            AllureAttachmentManager.attachScreenshot(screenshotName,screenshotFile.getAbsolutePath());

        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }
    public static void takeElementScreenShot(WebDriver driver, By locator)
    {
        try{
            String arialName = driver.findElement(locator).getAccessibleName();
            File srcFile = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
            File destFile = new File(SCREENSHOT_PATH + arialName +"-"+ TimeManager.getTimesTamp()+".png");
            FileUtils.copyFile(srcFile,destFile);
        }catch (Exception e)
        {
            LogsManager.error("Error taking element screenshot:", e.getMessage());
        }

    }
}
