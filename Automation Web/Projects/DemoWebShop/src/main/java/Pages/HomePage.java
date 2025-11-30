package Pages;

import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import Utils.DataReader.JsonReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
public class HomePage
{
    private final GUIDriver driver;
    private final JsonReader testData = new JsonReader("product-data");
    public HomePage(GUIDriver driver)
    {
        this.driver = driver;
    }
//Locators
    private final By productsBox = By.cssSelector("[class=\"item-box\"]");
    private final By headerMenuBlock = By.cssSelector("[class=\"header-menu\"]");
    private final By categoryNavigationBlock = By.cssSelector("[class=\"block block-category-navigation\"]");
    private final By dataThumbBlock = By.cssSelector("[class=\"slider-wrapper theme-default\"]");
    private final By manufacturerNavigationBlock = By.cssSelector("[class=\"block block-manufacturer-navigation\"]");
    private final By popularTagsBlock = By.cssSelector("[class=\"block block-popular-tags\"]");
    private final By welcomeTextBlock = By.cssSelector("[class=\"topic-html-content\"]");
    private final By recentViewedProductsBlock = By.cssSelector("[class=\"block block-recently-viewed-products\"]");
    private final By newsLettersBlock = By.cssSelector("[class=\"block block-newsletter\"]");
    private final By communityPollBlock = By.cssSelector("[class=\"block block-poll\"]");
    private final By productTitle = By.cssSelector("[class=\"page-title\"] h1");
    private final By newLetterField = By.id("newsletter-email");
    private final By subscribeButton = By.cssSelector("[class=\"button-1 newsletter-subscribe-button\"]");
    private final By newLetterMessage = By.cssSelector("[class=\"newsletter-result-block\"]");


    @Step("Verifying all featured sections are displayed on Home Page")
    public HomePage verifyFeaturedProductsDisplayed()
    {
        By[] menuItems = {productsBox, headerMenuBlock, categoryNavigationBlock, dataThumbBlock,
                manufacturerNavigationBlock, popularTagsBlock, welcomeTextBlock,
                newsLettersBlock, communityPollBlock};
        for (By item : menuItems) {
            driver.hardAssertions().assertTrue(driver.element().isPresent(item),
                    "The element " + item.toString() + " is not present on the Home Page");
        }
    return this;
    }
    @Step("Verifying top menu navigation for all major categories")
    public HomePage verifyTopMenuNavigation()
    {
        NavigationBar navigateBar = new NavigationBar(driver);
        String[] menuItems = {"Books", "Computers", "Electronics", "Apparel & Shoes", "Digital downloads", "Jewelry", "Gift Cards"};
        for (String item : menuItems)
        {
            navigateBar.navigateToCategory(item);
            driver.softAssertions().assertTrue(driver.element().getTextIfPresent(productTitle).contains(item),
                    "Navigation to " + item + " page failed.");
            driver.browser().navigateBack();
        }
        return this;
    }
    @Step("Entering email into Newsletter subscription field: {email}")
    public HomePage enterEmailInNewsLetter(String email)
    {
        driver.element().typing(newLetterField, email);
        driver.element().clicking(subscribeButton);
        return this;
    }
    @Step("Verifying Newsletter subscription result message")
    public HomePage verifyEnterEmailInNewsLetter()
    {
        String msg = driver.element().getTextIfPresent(newLetterMessage);
        driver.hardAssertions().assertTrue(msg != null && msg.contains(testData.getJsonData("homePage.newLetterMessage"))
                , "The Newsletter subscription failed please " + msg);
        return this;
    }


}
