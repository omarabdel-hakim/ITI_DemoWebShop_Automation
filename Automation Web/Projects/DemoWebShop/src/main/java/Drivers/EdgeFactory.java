package Drivers;

import Utils.DataReader.JsonReader;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeFactory extends AbstractDriver
{

    private EdgeOptions getOptions()
    {
        EdgeOptions option = new EdgeOptions();
        option.addArguments("--start-maximized");
        option.addArguments("--remote-allow-origins=*");
        option.setAcceptInsecureCerts(true);
        option.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if(!new JsonReader("config").getJsonData("executionType").equals("Local")) option.addArguments("--headless");

        return option;
    }
    @Override
    public WebDriver createDriver() {
        return new EdgeDriver(getOptions());
    }
}
