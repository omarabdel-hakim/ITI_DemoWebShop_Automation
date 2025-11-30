
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.CategoryPage;
import Pages.Components.NavigationBar;
import Pages.ShoppingCartPage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;

@Listeners(TestNGListeners.class)
@Epic("Shopping Cart Module")
@Feature("Shopping Cart Functionality")
public class ShoppingCartTest
{
    GUIDriver driver;
    JsonReader testData;
    ShoppingCartPage shoppingCart;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        shoppingCart = new ShoppingCartPage(driver);

        new NavigationBar(driver)
                .navigate().navigateToCategory(testData.getJsonData("categoryPage.Apparel&ShoesCat"));
        new CategoryPage(driver)
                .addProductToCart(testData.getJsonData("categoryPage.Apparel&ShoesNameB"))
                .addProductToCart(testData.getJsonData("categoryPage.Apparel&ShoesNameC"))
                .navigateToProductDetailsPage(testData.getJsonData("categoryPage.Apparel&ShoesNameA"))
                .addProductToCart();
        new NavigationBar(driver).clickOnShoppingButton();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Update product quantity")
    @Description("Ensure that the product quantity in the cart can be updated successfully with a valid value")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyUpdatingProductQuantityTC()
    {
        shoppingCart.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameA")
                ,testData.getJsonData("categoryPage.quantity"))
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
    }

    @Test(priority = 0)
    @Story("Proceed to checkout without accepting Terms of Service")
    @Description("Verify that user cannot navigate to checkout without accepting terms of service")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyNavigatingToCheckOutPageWithoutTermsOfServicesTC()
    {
        shoppingCart.checkout().verifyCheckout();
    }

    @Test(priority = 0)
    @Story("Proceed to checkout without login")
    @Description("Verify that checkout redirects user to login page if user is not authenticated")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyNavigatingToCheckOutPageWithoutLoginTC()
    {
        shoppingCart.selectTermsOfService().checkout().verifyCheckout();
    }

    @Test(priority = 1)
    @Story("Remove product from cart")
    @Description("Ensure that a product can be removed from the shopping cart successfully")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyRemovingProductFromCartTC() {
        shoppingCart.removeProductFromCart(testData.getJsonData("categoryPage.Apparel&ShoesNameA"))
                .verifyDeletedProduct(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
    }

    @Test(priority = 2)
    @Story("Update quantity with zero value")
    @Description("Verify system behavior when updating product quantity to zero")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyUpdateWithZeroQuantityShowsErrorTC()
    {
        shoppingCart.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameC")
                        ,"0")
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameC"));
    }

    @Test(priority = 2)
    @Story("Update quantity with negative value")
    @Description("Verify system behavior when updating product quantity to a negative number")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyUpdateWithNegativeQuantityShowsErrorTC()
    {
        shoppingCart.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameB")
                        ,"-5")
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameB"));
    }

    @Test(priority = 2)
    @Story("Update quantity with invalid non-numeric value")
    @Description("Ensure that an error is shown when entering invalid input (non-numeric) for quantity")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyUpdateWithInvalidQuantityShowsErrorTC()
    {
        shoppingCart.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameB")
                        ,"four")
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameB"));
    }


    }
