package Drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory extends AbstractDriver
{
    private ChromeOptions getOptions()
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--start-maximized");
        //option.addArguments("--headless");
        option.addArguments("--remote-allow-origins=*");
        option.setAcceptInsecureCerts(true);
        return option;
    }
    @Override
    public WebDriver createDriver() {
        return new ChromeDriver(getOptions());
    }
}
