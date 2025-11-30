package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager wait;
    public FrameActions(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public void switchToDefaultContent()
    {
        driver.switchTo().defaultContent();
    }
    public void switchToFrame(int index)
    {
        wait.fWait().until(d ->
        {
            try {
                {
                    driver.switchTo().frame(index);
                    return true;

                }
            }catch (Exception e)
            {
                LogsManager.error("Error switching to frame by index:", String.valueOf(index), e.getMessage());
                return false;
            }
        });
    }

    public void switchToFrame(String NameorId)
    {
        wait.fWait().until(d ->
        {
            try {
                {
                     driver.switchTo().frame(NameorId);
                    return true;
                }
            }catch (Exception e)
            {
                LogsManager.error("Error switching to frame by Name or Id:", NameorId, e.getMessage());
                return false;
            }
        });
    }
    public void switchToFrame(By element)
    {
        wait.fWait().until(d ->
        {
            try {
                {
                     driver.switchTo().frame(d.findElement(element));
                    return true;
                }
            }catch (Exception e)
            {
                LogsManager.error("Error switching to frame by WebElement:", element.toString(), e.getMessage());
                return false;
            }
        });
    }

}
