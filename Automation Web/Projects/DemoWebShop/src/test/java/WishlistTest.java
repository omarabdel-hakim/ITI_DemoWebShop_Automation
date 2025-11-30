
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.CategoryPage;
import Pages.Components.NavigationBar;
import Pages.ProductDetailsPage;
import Pages.ShoppingCartPage;
import Pages.WishlistPage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;

@Listeners(TestNGListeners.class)
@Epic("Wishlist Module")
@Feature("Wishlist Functionality")
public class WishlistTest
{
    GUIDriver driver;
    JsonReader testData;
    WishlistPage wishlistPage;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        wishlistPage = new WishlistPage(driver);

        new NavigationBar(driver)
                .navigate().navigateToCategory(testData.getJsonData("categoryPage.Apparel&ShoesCat"));
        new CategoryPage(driver)
                .navigateToProductDetailsPage(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
        new ProductDetailsPage(driver).addToWishList();
        new NavigationBar(driver).clickOnWishlistButton();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Verify product added to wishlist")
    @Description("This test ensures that a product can be successfully added to the wishlist")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyProductAddedToWishlistTC()
    {
        wishlistPage.verifyWishlistedProducts();
    }

    @Test(priority = 1)
    @Story("Verify updating product quantity in wishlist with valid value")
    @Description("This test ensures that updating a product quantity with a valid number is correctly reflected in the wishlist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyUpdateWithValidQuantityInWishlistTC() {
       wishlistPage.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameA")
                        ,testData.getJsonData("categoryPage.quantity"))
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
    }

    @Test(priority = 0)
    @Story("Verify adding wishlist product to cart")
    @Description("This test ensures that a product from the wishlist can be successfully added to the shopping cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddWishlistProductToCartTC()
    {
        wishlistPage.addProductToCart(testData.getJsonData("categoryPage.Apparel&ShoesNameA"))
                .verifyAddedProductToCart();
    }

    @Test(priority = 2)
    @Story("Verify updating product quantity in wishlist with invalid value")
    @Description("This test ensures that updating a product quantity with invalid input shows error ")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyUpdateWithInvalidQuantityShowsErrorTC()
    {
        wishlistPage.updateProductQuantity(testData.getJsonData("categoryPage.Apparel&ShoesNameA")
                        ,"two")
                .verifyIfQuantityUpdated(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
    }

    @Test(priority = 1)
    @Story("Verify removing product from wishlist")
    @Description("This test ensures that a product can be removed from the wishlist successfully")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyRemoveProductFromWishlistTC()
    {
        wishlistPage.removeProductFromWishlist(testData.getJsonData("categoryPage.Apparel&ShoesNameA"))
                .verifyDeletedProduct(testData.getJsonData("categoryPage.Apparel&ShoesNameA"));
    }





}
