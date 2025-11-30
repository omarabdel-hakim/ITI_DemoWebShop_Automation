package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CategoryPage {
    private GUIDriver driver;
    private final JsonReader testData = new JsonReader("product-data");

    public CategoryPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final By sortByDropdown = By.id("products-orderby");
    private final By displayPerPageDropdown = By.id("products-pagesize");
    private final By viewAsDropdown = By.id("products-viewmode");
    private final By filterByPrice = By.cssSelector("[class=\"price-range-selector\"] li a");
    private final By addToCartMassage = By.cssSelector("[class=\"content\"]");
    private final By itemBox = By.cssSelector("[class=\"item-box\"]");
    private final By closeButton = By.className("close");
    private final By itemName = By.cssSelector("[itemprop=\"name\"]");
    private final By addToCartButtons = By.xpath("//input[@value='Add to cart']");
    private final By barNotification = By.cssSelector("[id=\"bar-notification\"]");

    @Step("Adding product '{productName}' to cart from category page")
    public CategoryPage addProductToCart(String productName) {
        for (int counter = 1; counter <= driver.element().findElements(itemBox).size(); counter++) {
            if (driver.element().getText(productTitle(counter)).contains(productName)) {
                driver.element().clicking(cartButton(counter));
                break;
            }
        }
        return this;
    }
    @Step("Adding the first product in category list to cart")
    public CategoryPage addProductToCart()
    {
                driver.element().clicking(cartButton(1));
        return this;
    }
    @Step("Navigating to product details page: {productName}")
    public CategoryPage navigateToProductDetailsPage(String productName) {
        for (int counter = 1; counter <= driver.element().findElements(itemBox).size(); counter++) {
            if (driver.element().getText(productTitle(counter)).contains(productName)) {
                driver.element().clicking(productTitle(counter));
                break;
            }
        }
        return this;
    }
    @Step("Sorting products by '{sortOption}'")
    public CategoryPage sortBy(String sortOption) {
        driver.element().selectFromDropDown(sortByDropdown, sortOption);
        return this;
    }
    @Step("Setting products per page to '{displayOption}'")
    public CategoryPage displayPerPage(String displayOption) {
        driver.element().selectFromDropDown(displayPerPageDropdown, displayOption);
        return this;
    }
    @Step("Viewing products as '{viewOption}'")
    public CategoryPage viewAs(String viewOption) {
        driver.element().selectFromDropDown(viewAsDropdown, viewOption);
        return this;
    }
    @Step("Filtering products by price range: '{priceRange}'")
    public CategoryPage filterByPrice(String priceRange)
    {
        if(driver.element().isPresent(filterByPrice))
        {
            for (int i = 1; i <= 3; i++) {
                if (driver.element().getText(priceRange(i)).contains(priceRange)) {
                    driver.element().clicking(priceRange(i));
                    break;
                }
            }
        }
        else  driver.hardAssertions().assertTrue(false,"Filter-By-Price option isn't visible");

        return this;
    }
    @Step("Verifying product is successfully added to cart")
    public CategoryPage verifyAddedProductToCart()
    {
        if(driver.element().isDisplayed(barNotification))
        {
            String msg = driver.element().getTextIfPresent(addToCartMassage);
            driver.hardAssertions().assertTrue(msg != null && msg.contains(testData.getJsonData("messages.addToCartMessage"))
                    , "The Product isn't add " + msg);
            driver.element().clicking(closeButton);
        }
        else if (driver.element().isDisplayed(itemName)) driver.hardAssertions().assertFail("User is navigated to Product Details Page and Product isn't added");
        else driver.hardAssertions().assertFail("Failed TO Verification");
        return this;
    }
    @Step("Verifying navigation to product details page for: {productName}")
    public CategoryPage verifyNavigateToProductDetailsPage(String productName)
    {
        String actualProductName = driver.element().getTextIfPresent(itemName);
        driver.hardAssertions().assertTrue(actualProductName.contains(productName), "Failed to navigate to product details page");
        return this;
    }
    @Step("Verifying Sort/Display/View/Filter options are visible")
    public CategoryPage verifyOptionsDisplayed()
    {
        driver.softAssertions().assertTrue(driver.element().isPresent(sortByDropdown),"Sort-By option isn't visible");
        driver.softAssertions().assertTrue(driver.element().isPresent(displayPerPageDropdown),"Display-Per-Page option isn't visible");
        driver.softAssertions().assertTrue(driver.element().isPresent(viewAsDropdown),"View As option isn't visible");
        driver.softAssertions().assertTrue(driver.element().isPresent(filterByPrice),"Filter-By-Price option isn't visible");
        return this;
    }
    @Step("Verifying each product has an Add-To-Cart button")
    public CategoryPage verifyAddIsDisplayed()
    {
        int cartButtons = driver.element().findElements(addToCartButtons).size();
        int productsNumber = driver.element().findElements(itemBox).size();
        driver.hardAssertions().assertTrue( productsNumber == cartButtons
                ,"There are "+(productsNumber-cartButtons)+" missed buttons");

        return this;
    }

    private By productTitle(int count)
    {
        return By.xpath("(//*[@class=\"product-title\"])["+count+"]");
    }
    private By cartButton(int count)
    {
        return By.xpath("(//input[@value='Add to cart'])["+count+"]");
    }
    private By priceRange(int count)
    {
        return By.xpath("(//*[@class=\"price-range-selector\"]  //a)["+count+"]");
    }
}
