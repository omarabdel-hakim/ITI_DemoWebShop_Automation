package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManager;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager wait;
    public AlertActions(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public void acceptAlert()
    {
        wait.fWait().until(d->
        {
            try{
                 d.switchTo().alert().accept();
                 LogsManager.info("Alert accepted successfully.");
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Failed to accept alert:", e.getMessage());
                return false ;
            }
        });
    }
    public void dismissAlert()
    {
        wait.fWait().until(d->
        {
            try{
                d.switchTo().alert().dismiss();
                LogsManager.info("Alert dismissed successfully.");
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Failed to dismiss alert:", e.getMessage());
                return false ;
            }
        });
    }
    public String getAlertText()
    {

         return wait.fWait().until(d->
        {
            try{
                String text = d.switchTo().alert().getText();
                LogsManager.info("Alert text retrieved successfully:", text);
                return !text.isEmpty() ? text : null ;
            }catch (Exception e)
            {
                LogsManager.error("Failed to get alert text:", e.getMessage());
                return null ;
            }
        });

    }
    public void setAlertText(String text)
    {

         wait.fWait().until(d->
        {
            try{
                d.switchTo().alert().sendKeys(text);
                LogsManager.info("Alert text set successfully:", text);
                return true ;
            }catch (Exception e)
            {
                LogsManager.error("Failed to set alert text:", e.getMessage());
                return false ;
            }
        });

    }
    public Boolean IsAlertPresent()
    {
        return wait.fWait().until(d->
        {
            try{
                d.switchTo().alert();
                return true;
            }catch (NoAlertPresentException e)
            {
                LogsManager.error("Failed to find alert:", e.getMessage());
                return false ;
            }
        });
    }
}
