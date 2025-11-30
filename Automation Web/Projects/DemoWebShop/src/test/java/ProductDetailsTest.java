
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import Pages.ProductDetailsPage;
import Pages.CategoryPage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;

@Listeners(TestNGListeners.class)
@Epic("Product Module")
@Feature("Product Details Page Functionality")
public class ProductDetailsTest
{

    GUIDriver driver;
    JsonReader testData,testLogData;
    ProductDetailsPage productDetailsPage ;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
        testLogData = new JsonReader("login-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        productDetailsPage = new ProductDetailsPage(driver);

        new NavigationBar(driver).navigate();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Add a product with custom options to cart")
    @Description("This test verifies that a product with configurable options can be added to the shopping cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddingProductToCartWithOptionsTC()
    {
        new CategoryPage(driver).navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameB"));
        productDetailsPage
                .verifyProductDetails(testData.getJsonData("productDetails.productNameB"),testData.getJsonData("productDetails.priceB"))
                .selectProductProcessor("fast")
                .selectProductRAM(8)
                .selectProductHDD(400)
                .selectProductSoftware("office")
                .addToCart().verifyAddedToCart();
    }

    @Test(priority = 0)
    @Story("Add a product without options to cart")
    @Description("This test verifies that a product without configurable options can be added to the shopping cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddingProductToCartWithoutOptionsTC()
    {
        new CategoryPage(driver).navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameA"));
        productDetailsPage
                .verifyProductDetails(testData.getJsonData("productDetails.productNameA"),testData.getJsonData("productDetails.priceA"))
                .addToCart().verifyAddedToCart();
    }

    @Test(priority = 1)
    @Story("Add a product with quantity to cart")
    @Description("This test verifies that adding a product with an quantity is handled correctly")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddingProductToCartWithQuantityTC()
    {
        new CategoryPage(driver).navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameA"));
        productDetailsPage
                .verifyProductDetails(testData.getJsonData("productDetails.productNameA"),testData.getJsonData("productDetails.priceA"))
                .enterRecipientNameAndEmail(testData.getJsonData("productDetails.recipientName"),testData.getJsonData("productDetails.recipientEmail"))
                .enterSenderNameAndEmail(testData.getJsonData("productDetails.senderName"),testData.getJsonData("productDetails.senderEmail"))
                .changeQuantity(testData.getJsonData("productDetails.quantityA"))
                .addToCart().verifyAddedToCart();
    }

    @Test(priority = 1)
    @Story("Add a product with options to wishlist")
    @Description("This test verifies that a product with configurable options can be added to the wishlist")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyAddingProductToWishListWithOptionsTC()
    {
        new CategoryPage(driver).navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameB"));
        productDetailsPage
                .selectProductProcessor("fast")
                .selectProductRAM(8)
                .selectProductHDD(400)
                .selectProductSoftware("office")
                .addToWishList().verifyAddedToWishList();
    }





}
