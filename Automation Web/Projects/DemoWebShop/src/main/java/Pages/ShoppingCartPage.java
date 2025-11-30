package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class ShoppingCartPage
{
    private final GUIDriver driver;
    private final JsonReader testData = new JsonReader("product-data");

    public ShoppingCartPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By productsNumber = By.cssSelector("[class=\"product-name\"]");
    private final By updateShoppingCartButton = By.cssSelector("[name=\"updatecart\"]");
    private final By continueShoppingButton = By.cssSelector("name=\"continueshopping\"");
    private final By discountCode= By.cssSelector("class=\"discount-coupon-code\"");
    private final By discountCodeButton = By.cssSelector("[name=\"applydiscountcouponcode\"]");
    private final By giftCards = By.cssSelector("[name=\"giftcardcouponcode\"]");
    private final By giftCardsButton = By.cssSelector("[name=\"applygiftcardcouponcode\"]");
    private final By country = By.cssSelector("[class=\"country-input valid\"]");
    private final By state = By.cssSelector("[class=\"state-input\"]");
    private final By estimateShippingButton = By.cssSelector("[name=\"estimateshipping\"]");
    private final By termsOfService = By.cssSelector("[id=\"termsofservice\"]");
    private final By subTotalAmount = By.xpath("(//*[@class=\"product-price\"])[1]");
    private final By shippingAmount = By.xpath("(//*[@class=\"product-price\"])[2]");
    private final By taxAmount = By.xpath("(//*[@class=\"product-price\"])[3]");
    private final By totalAmount = By.cssSelector("[class=\"product-price order-total\"]");
    private final By checkoutButton = By.id("checkout");
    private final By editButton = By.cssSelector("[class=\"product\"] [class=\"edit-item\"] a");
    private final By messageContent = By.cssSelector("[class=\"message\"]");
    private final By checkOutIcon = By.xpath("//*[text()=\"Billing address\"]");
    private final By productsQuantity = By.cssSelector("class=\"cart-qty\"");
    private final By termsOfServiceWidget = By.cssSelector("[id=\"terms-of-service-warning-box\"] p");

    private static String quantityBeforeUpdating;


    //Main Actions
    @Step("Enter discount code: {number}")
    public ShoppingCartPage enterDiscount(String number)
    {
         driver.element().typing(discountCode, number);
         driver.element().clicking(discountCodeButton);
         return this;
   }
    @Step("Enter gift card number: {number}")
    public ShoppingCartPage enterGiftCard(String number)
    {
        driver.element().typing(giftCards, number);
        driver.element().clicking(giftCardsButton);
        return this;
    }
    @Step("Enter gift card number: {number}")
    public ShoppingCartPage selectCountryAndState(String countryName,String stateName)
    {
        driver.element().selectFromDropDown(country, countryName);
        driver.element().selectFromDropDown(state, stateName);
        return this;
    }
    @Step("Click Estimate Shipping button")
    public ShoppingCartPage enterEstimateShippingButton()
    {
        driver.element().clicking(estimateShippingButton);
        return this;
    }
    @Step("Accept Terms of Service")
    public ShoppingCartPage selectTermsOfService()
    {
        driver.element().clicking(termsOfService);
        return this;
    }
    @Step("Click Checkout button")
    public ShoppingCartPage checkout()
    {
        driver.element().clicking(checkoutButton);
        return this;
    }
    @Step("Remove product(s) from cart: {productName}")
    public ShoppingCartPage removeProductFromCart(String... productName)
    {
        for (String name : productName)
        {
            driver.element().clicking(getProductCheckBox(name));
        }
        driver.element().clicking(updateShoppingCartButton);
        return this;
    }
    @Step("Update product '{productName}' quantity to: {qty}")
    public ShoppingCartPage updateProductQuantity(String productName , String qty)
    {
        quantityBeforeUpdating = driver.element().getPropertyValue(getProductQuantityField(productName),"value");
        driver.element().clearText(getProductQuantityField(productName));
        driver.element().typing(getProductQuantityField(productName),qty);
        driver.element().clicking(updateShoppingCartButton);
        return this;
    }
    @Step("Edit product in cart: {productName}")
    public ShoppingCartPage editProductInCart(String productName)
    {

        WebElement locator = driver.element().findElement(with(editButton).below(driver.element().findElement(getProductName(productName))));
        locator.click();
        return this;
    }
    @Step("Click Continue Shopping button")
    public ShoppingCartPage continueShopping()
    {
        driver.element().clicking(continueShoppingButton);
        return this;
    }
    @Step("Verify total amount calculation")
    public ShoppingCartPage verifyTotalAmount()
    {
        double subTotal = Double.parseDouble(driver.element().getText(subTotalAmount));
        double shipping = Double.parseDouble(driver.element().getText(shippingAmount));
        double tax = Double.parseDouble(driver.element().getText(taxAmount));
        double expectedTotal = subTotal + shipping + tax ;
        double actualTotal = Double.parseDouble(driver.element().getText(totalAmount));
        driver.hardAssertions().assertTrue(actualTotal == expectedTotal
                ,"Actual-Total = "+actualTotal+" not Equal to Expected-Total = "+expectedTotal);
        return this;
    }
    @Step("Verify quantity updated for product: {productName}")
    public ShoppingCartPage verifyIfQuantityUpdated(String productName)
    {
        if (getProductName(productName) != null)
        {
            if (Objects.equals(quantityBeforeUpdating, driver.element().getPropertyValue(getProductQuantityField(productName), "value")))
                driver.hardAssertions().assertFail("update isn't occurred");
            else
            {
                double unitPrice = Double.parseDouble(driver.element().getText(getProductUnitPrice(productName)));
                double quantity = Double.parseDouble(driver.element().getPropertyValue(getProductQuantityField(productName),"value"));
                double expectedTotal = unitPrice * quantity;
                double actualTotal = Double.parseDouble(driver.element().getText(getProductTotalPrice(productName)));
                driver.hardAssertions().assertTrue(actualTotal == expectedTotal
                        ,"Actual-Total = "+actualTotal+" not Equal to Expected-Total = "+expectedTotal);
            }
        }
        else if (getProductName(productName) == null)
            driver.hardAssertions().assertFail("the product is removed");

        else driver.hardAssertions().assertFail("Verification Failed");

        return this;
    }
    @Step("Verify product '{productName}' is deleted from shopping cart")
    public ShoppingCartPage verifyDeletedProduct(String productName)
    {
        driver.softAssertions().assertFalse(driver.element().isPresent(getProductName(productName)),
                "Product: " + productName + " is still present in the shopping cart after deletion");
        return this;
    }
    @Step("Verify discount code applied")
    public ShoppingCartPage verifyDiscountApplied()
    {
        driver.softAssertions().assertFalse(driver.element().getTextIfPresent(messageContent)
                .contains(testData.getJsonData("messages.discountAndGiftMessage")),
                "Discount Code is applied successfully");
        return this;
    }
    @Step("Verify gift card applied")
    public ShoppingCartPage verifyGiftCardApplied()
    {
        driver.softAssertions().assertFalse(driver.element().getTextIfPresent(messageContent)
                        .contains(testData.getJsonData("messages.discountAndGiftMessage")),
                "Gift Card is applied successfully");
        return this;
    }
    @Step("Verify checkout page navigation")
    public ShoppingCartPage verifyCheckout()
    {
        if(driver.element().isPresent(termsOfServiceWidget))
            driver.hardAssertions().assertFail(driver.element().getTextIfPresent(termsOfServiceWidget));

        else driver.hardAssertions().assertTrue(driver.element().isPresent(checkOutIcon),
                "Filed to navigate to checkout page");
        return this;
    }
    @Step("Verify number of products in shopping cart")
    public ShoppingCartPage verifyNumberOfProductInShoppingCart()
    {
        int actualNumber = driver.element().findElements(productsQuantity).size();
        int expectedNumber = Integer.parseInt(driver.element().getText(productsQuantity).replaceAll("\\D", ""));
        driver.hardAssertions().assertTrue(expectedNumber == actualNumber,
                "Expected Wishlist quantity is: " + expectedNumber +
                        " , but Actual Wishlist quantity is: " + actualNumber);
        return this;
    }

    //Private Methods
    private By getProductName(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"product-name\"])[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"product-name\"])[" + counter + "]");
            }
        }
        return locator ;
    }
    private By getProductCheckBox(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"product-name\"])[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@name=\"removefromcart\"])["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductQuantityField(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"product-name\"])[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"qty nobr\"] //input)["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductUnitPrice(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"product-name\"])[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"product-unit-price\"])["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductTotalPrice(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"product-name\"])[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"product-subtotal\"])["+ counter+ "]");
            }
        }
        return locator ;
    }

}
