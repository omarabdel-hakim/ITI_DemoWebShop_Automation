
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import io.qameta.allure.*;
import Pages.CategoryPage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;

@Listeners(TestNGListeners.class)
@Epic("Category Module")
@Feature("Category Page Functionality")
public class CategoryTest
{
    GUIDriver driver;
    NavigationBar navigationBar;
    JsonReader testData;
    CategoryPage categoryPage;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        navigationBar = new NavigationBar(driver);
        categoryPage = new CategoryPage(driver);
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Add a normal product to cart from category page")
    @Description("This test verifies that a normal product can be added to the shopping cart from a category page")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddNormalProductToCartTC()
    {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.electronicsCat"));
        categoryPage.addProductToCart(testData.getJsonData("categoryPage.electronicsName"))
                .verifyAddedProductToCart();
    }

    @Test(priority = 0)
    @Story("Add a customizable product to cart from category page")
    @Description("This test verifies that a customizable product can be added to the shopping cart from a category page")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddCustomizableProductToCartTC()
     {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.computerCat"));
        categoryPage.addProductToCart(testData.getJsonData("categoryPage.computerName"))
                .verifyAddedProductToCart();
    }

    @Test(priority = 1)
    @Story("Verify Add to Cart buttons are displayed")
    @Description("This test ensures that the 'Add to Cart' buttons are displayed for all products in the category page")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddToCartButtonsAreDisplayedTC()
    {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.computerCat"));
        categoryPage.verifyAddIsDisplayed();
    }

    @Test(priority = 1)
    @Story("Verify product options are displayed")
    @Description("This test checks that all available options (like size, color) are displayed for products in the category")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyCategoryOptionsAreDisplayedTC()
    {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.electronicsCat"));
        categoryPage.verifyOptionsDisplayed();
    }

    @Test(priority = 1)
    @Story("Navigate to product details page")
    @Description("This test verifies that clicking on a product navigates to its detailed product page")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyNavigationToProductDetailsPageTC()
    {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.electronicsCat"));
        categoryPage.navigateToProductDetailsPage(testData.getJsonData("categoryPage.electronicsName"))
                .verifyNavigateToProductDetailsPage(testData.getJsonData("categoryPage.electronicsName"));
    }

    @Test(priority = 1)
    @Story("Verify working product options")
    @Description("This test verifies that selecting different product options like sorting, display per page, view type, and price filters works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyWorkingCategoryOptionsTC()
    {
        navigationBar.navigate().navigateToCategory(testData.getJsonData("categoryPage.computerCat"));
        categoryPage.sortBy(testData.getJsonData("categoryPage.sortBy"))
                .displayPerPage(testData.getJsonData("categoryPage.displayByPage"))
                .viewAs(testData.getJsonData("categoryPage.viewAs"))
                .filterByPrice(testData.getJsonData("categoryPage.filterByPrice"));
    }
}
