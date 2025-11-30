package Pages.Components;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class NavigationBar
{
    private final GUIDriver driver;
    JsonReader testData = new JsonReader("product-data");

    public NavigationBar(GUIDriver driver)
    {
        this.driver = driver;
    }
    private final By homeLogo = By.cssSelector("[alt=\"Tricentis Demo Web Shop\"]");
    private final By register = By.cssSelector("[class=\"ico-register\"]");
    private final By userAccount = By.xpath("(//*[@class=\"account\"])[1]");
    private final By login = By.cssSelector("[class=\"ico-login\"]");
    private final By logout = By.cssSelector("[class=\"ico-logout\"]");
    private final By shoppingCart = By.xpath("(//*[.='Shopping cart'])[1]");
    private final By wishlist = By.xpath("(//*[.='Wishlist'])[1]");
    private final By searchBox = By.id("small-searchterms");
    private final By searchButton = By.cssSelector("[class=\"button-1 search-box-button\"]");
    private final By topMenuCategories = By.cssSelector("[class=\"top-menu\"] >li");//7
    private final By searchMessage = By.cssSelector("[class=\"search-results\"] strong");
    private final By searchResults = By.cssSelector("[class=\"item-box\"]");

    @Step("Navigate to category: {category}")
    public NavigationBar navigateToCategory(String category) {
        int flag = 0;
        By locator = By.xpath(String.format("//*[@class=\"top-menu\"] //*[contains(text(),\"%s\")]", StringUtils.capitalize(category)));
        By MenuElement ;
        for (int rows = 1 ; rows <= driver.element().findElements(topMenuCategories).size() ; rows++)
        {
            MenuElement = By.cssSelector("[class=\"top-menu\"] >li:nth-child("+rows+")");
                driver.element().hovering(MenuElement);
                if(driver.element().isPresent(locator))
                {
                    driver.element().clicking(locator);
                    flag = 1 ;
                    break;
                }
        }
        if (flag == 0)
        {
            LogsManager.error("Category Not Found: ", category);
            return null ;
        }
        return this ;
    }
    @Step("Open Base URL")
    public NavigationBar navigate()
    {
        driver.browser().navigateTo(new JsonReader("config").getJsonData("baseURL"));
        return this;
    }
    @Step("Return to Home Page")
    public NavigationBar returnToHomePage()
    {
        driver.element().clicking(homeLogo);
        return this;
    }
    @Step("Click on Register button")
    public NavigationBar clickOnRegisterButton()
    {
        driver.element().clicking(register);
        return this;
    }
    @Step("Click on Login button")
    public NavigationBar clickOnLoginButton()
    {
        driver.element().clicking(login);
        return this;
    }
    @Step("Click on Logout button")
    public NavigationBar clickOnLogoutButton()
    {
        driver.element().clicking(logout);
        return this;
    }
    @Step("Open Shopping Cart")
    public NavigationBar clickOnShoppingButton()
    {
        driver.element().clicking(shoppingCart);
        return this;
    }
    @Step("Open Wishlist")
    public NavigationBar clickOnWishlistButton()
    {
        driver.element().clicking(wishlist);
        return this;
    }
    @Step("Search for product: {productName}")
    public NavigationBar searchForProduct(String productName)
    {
        driver.element().typing(searchBox,productName);
        driver.element().clicking(searchButton);
        return this;
    }
    @Step("Open User Profile")
    public NavigationBar clickOnUserProfile()
    {
        driver.element().clicking(userAccount);
        return this;
    }
    @Step("Verify Search Results")
    public NavigationBar verifySearching()
    {
        try{
            if(!driver.element().findElements(searchResults).isEmpty()) driver.hardAssertions().assertTrue(true,
                    "No Result Found"+ driver.element().getTextIfPresent(searchMessage));

            else if (driver.element().isPresent(searchMessage))
                driver.hardAssertions().assertEquals(driver.element().getTextIfPresent(searchMessage),
                        testData.getJsonData("searchProduct.invalidSearchMessage")
                        ,"No Validation Message is Appeared");

            else if (driver.alert().IsAlertPresent())
                driver.hardAssertions().assertEquals(driver.alert().getAlertText(),testData.getJsonData("homePage.alertMessage")
                        ,"No Alert Found");

            else driver.hardAssertions().assertFail("Failed To Verify Search");

        }catch (Exception _){}

        return this;
    }


}
