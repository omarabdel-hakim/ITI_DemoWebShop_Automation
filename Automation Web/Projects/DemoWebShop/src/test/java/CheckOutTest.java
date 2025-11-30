
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.*;
import Pages.Components.NavigationBar;
import Utils.DataReader.JsonReader;
import Utils.TimeManager;
import org.testng.annotations.*;
import io.qameta.allure.*;

@Listeners(TestNGListeners.class)
@Epic("Checkout Module")
@Feature("Complete Order Flow")
public class CheckOutTest
{
    GUIDriver driver;
    NavigationBar navigationBar;
    JsonReader testData;
    JsonReader loginData;
    JsonReader checkOutData;
    JsonReader registerData;
    CheckOutPage checkOutPage;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
        loginData = new JsonReader("login-data");
        checkOutData = new JsonReader("checkout-data");
        registerData = new JsonReader("register-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        navigationBar = new NavigationBar(driver);
        checkOutPage = new CheckOutPage(driver);

    }
    @AfterMethod
    public void tearDown()
    {
        try{Thread.sleep(1000);}
        catch(Exception _){}
        driver.quitDriver();
    }
    @Test(priority = 0)
    @Story("Complete checkout while logged in")
    @Description("Verify that a registered and logged-in user can complete an order successfully with valid data.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyCompleteOrderWithValidDataAndLoginTC()
    {
        navigationBar
                .navigate().clickOnRegisterButton();
        new RegisterPage(driver)
                .chooseGender()
                .enterFirstName(registerData.getJsonData("firstName"))
                .enterLastName(registerData.getJsonData("lastName"))
                .enterEmail(registerData.getJsonData("email") + TimeManager.getSimpleTimesTamp() + "@gmail.com")
                .enterPassword(registerData.getJsonData("password"))
                .enterConfirmPassword(registerData.getJsonData("confirmPassword"))
                .clickRegisterButton().verifyRegistration();
        navigationBar
                .navigateToCategory(testData.getJsonData("categoryPage.computerCat"));
        new CategoryPage(driver)
                .navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameB"));
        new ProductDetailsPage(driver)
                .verifyProductDetails(testData.getJsonData("productDetails.productNameB"),testData.getJsonData("productDetails.priceB"))
                .selectProductProcessor("fast")
                .selectProductRAM(8)
                .selectProductHDD(400)
                .selectProductSoftware("office").addToCart();
        navigationBar
                .navigateToCategory(testData.getJsonData("categoryPage.electronicsCat"));
        new CategoryPage(driver)
                .addProductToCart(testData.getJsonData("categoryPage.electronicsName"));
        navigationBar
                .clickOnShoppingButton();
        new ShoppingCartPage(driver)
                .selectTermsOfService().checkout().verifyCheckout();
        checkOutPage
                .fillBillingAddressForm()
                .selectSavedShippingAddress()
                .selectShippingMethod(checkOutData.getJsonData("shippingMethod"))
                .selectPaymentMethod(checkOutData.getJsonData("paymentMethod"))
                .fillCreditCardOptions(checkOutData.getJsonData("creditInformation.creditCardType")
                        ,checkOutData.getJsonData("creditInformation.cardHolder")
                        ,checkOutData.getJsonData("creditInformation.cardNumber")
                        ,checkOutData.getJsonData("creditInformation.expirationMonth")
                        ,checkOutData.getJsonData("creditInformation.expirationYear")
                        ,checkOutData.getJsonData("creditInformation.cardCode"))
                .verifyFilledCreditCardOptions()
                .verifyOrderDataIsDisplayed()
                .verifyTotalProductsAmount()
                .verifyFinalTotalAmount()
                .confirmOrder()
                .verifyConfirmationMessage();
    }

    @Test(priority = 1)
    @Story("Complete checkout without login")
    @Description("Verify that a guest user can complete an order successfully using valid billing, shipping, and payment information.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void CompleteOrderWithValidDataAndWithoutLoginTC()
    {
        navigationBar
                .navigate()
                .navigateToCategory(testData.getJsonData("categoryPage.computerCat"));
        new CategoryPage(driver)
                .navigateToProductDetailsPage(testData.getJsonData("productDetails.productNameB"));
        new ProductDetailsPage(driver)
                .verifyProductDetails(testData.getJsonData("productDetails.productNameB"),testData.getJsonData("productDetails.priceB"))
                .selectProductProcessor("fast")
                .selectProductRAM(8)
                .selectProductHDD(400)
                .selectProductSoftware("office").addToCart();
        navigationBar
                .navigateToCategory(testData.getJsonData("categoryPage.electronicsCat"));
        new CategoryPage(driver)
                .addProductToCart(testData.getJsonData("categoryPage.electronicsName"));
        navigationBar
                .clickOnShoppingButton();
        new ShoppingCartPage(driver)
                .selectTermsOfService().checkout().verifyCheckout();
        checkOutPage
                .fillBillingAddressForm()
                .selectSavedShippingAddress()
                .selectShippingMethod(checkOutData.getJsonData("shippingMethod"))
                .selectPaymentMethod(checkOutData.getJsonData("paymentMethod"))
                .fillCreditCardOptions(checkOutData.getJsonData("creditInformation.creditCardType")
                        ,checkOutData.getJsonData("creditInformation.cardHolder")
                        ,checkOutData.getJsonData("creditInformation.cardNumber")
                        ,checkOutData.getJsonData("creditInformation.expirationMonth")
                        ,checkOutData.getJsonData("creditInformation.expirationYear")
                        ,checkOutData.getJsonData("creditInformation.cardCode"))
                .verifyFilledCreditCardOptions()
                .verifyOrderDataIsDisplayed()
                .verifyTotalProductsAmount()
                .verifyFinalTotalAmount()
                .confirmOrder()
                .verifyConfirmationMessage();
    }
}
