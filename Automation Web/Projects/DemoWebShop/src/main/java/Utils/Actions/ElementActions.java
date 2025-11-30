package Utils.Actions;

import Utils.Logs.LogsManager;
import Utils.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

public class ElementActions
{
    private final WebDriver driver;
    private final WaitManager wait ;

    public ElementActions(WebDriver driver)
    {
        this.driver = driver;
        wait = new WaitManager(driver);
    }
    private void scrollToElementJS(By locator)
    {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(""" 
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
    }
    public WebElement findElement(By locator)
    {
        return driver.findElement(locator);
    }
    public List<WebElement> findElements(By locator)
    {
        return driver.findElements(locator);
    }

    public void typing (By locator,String text)
    {
        wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(text);
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error typing into element: ", locator.toString(), e.getMessage());
                return false;
            }

        });
    }
    public void clicking (By locator)
    {
        wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                element.click();
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error clicking on element: ", locator.toString(), e.getMessage());
                return false;
            }
        });
    }
    public void hovering (By locator)
    {
        Actions action = new Actions(driver);
        wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                action.moveToElement(element).perform();
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error hovering over element: ", locator.toString(), e.getMessage());
                return false;
            }

        });
    }
    public String getText (By locator)
    {
        return wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                String msg = element.getText();
                return msg.isEmpty() ? null : msg;
            }catch (Exception e)
            {
                return null;
            }
        });
    }
    public String getTextIfPresent(By locator)
    {
        try
        {
            return wait.fWait().until(d -> {
                try {
                    WebElement element = findElement(locator);
                    if (!element.isDisplayed())
                        return null;

                    scrollToElementJS(locator);
                    String msg = element.getText().trim();

                    return msg.isEmpty() ? null : msg;

                } catch (Exception ignore) {
                    return null;
                }
            });

        } catch (Exception ignore) {
            return null;
        }
    }

    public void uploadFile (By locator,String filepath)
    {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filepath;
        wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                typing(locator,fileAbsolute);
                
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error uploading file to element: ", locator.toString(), e.getMessage());
                return false;
            }

        });
    }
    public ElementActions selectFromDropDown (By locator,String value)
    {
         wait.fWait().until(d ->
        {
            try
            {
                Select select = new Select (findElement(locator));
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                select.selectByVisibleText(value);
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error selecting from dropdown element: ", locator.toString(), e.getMessage());
                return false;
            }
        });return this;
    }
    public ElementActions selectFromDropDown (By locator,int index)
    {
        wait.fWait().until(d ->
        {
            try
            {
                Select select = new Select (findElement(locator));
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                select.selectByIndex(index);
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error selecting from dropdown element: ", locator.toString(), e.getMessage());
                return false;
            }
        });return this;
    }

    public Boolean isPresent(By locator)
    {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }
    public Boolean isDisplayed(By locator)
    {
        try
        {
        return wait.fWait().until(d ->
        {
            try {
                return findElement(locator).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });
        } catch (Exception ignore) {
            return false;
        }
    }

    public void clearText(By quantity)
    {
        wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(quantity);
                scrollToElementJS(quantity);
                element.clear();
                return true;
            }catch (Exception e)
            {
                LogsManager.error("Error clearing text from element: ", quantity.toString(), e.getMessage());
                return false;
            }

        });
    }
    public String getPropertyValue (By locator,String attributeName)
    {
        return wait.fWait().until(d ->
        {
            try
            {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                String value = element.getDomProperty(attributeName);
                return value.isEmpty() ? null : value;
            }catch (Exception e)
            {
                return null;
            }
        });
    }
}
