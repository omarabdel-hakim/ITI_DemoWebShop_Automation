package Pages;

import Drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;


public class WishlistPage
{
    private final GUIDriver driver;
    private static Integer productAddedToCart;

    public WishlistPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final By productsNumber = By.cssSelector("[class=\"product-picture\"]");
    private final By updateWishlistButton = By.cssSelector("[name=\"updatecart\"]");
    private final By addToCartButton = By.cssSelector("[name=\"addtocartbutton\"]");
    private final By quantityFields = By.xpath("//*[@class=\"qty nobr\"] //input");
    private final By productsWishlistedQuantity = By.cssSelector("[class=\"wishlist-qty\"]");
    private final By productsCartQuantity = By.cssSelector("[class=\"cart-qty\"]");
    private static String quantityBeforeUpdating;

    //Actions
    @Step("Remove product(s) from wishlist: {productName}")
    public WishlistPage removeProductFromWishlist(String... productName)
    {
        for (String name : productName)
        {
            driver.element().clicking(getProductRemoveBox(name));
        }
        driver.element().clicking(updateWishlistButton);
        return this;
    }
    @Step("Add product(s) to cart from wishlist: {productName}")
    public WishlistPage addProductToCart(String... productName)
    {
        productAddedToCart = Integer.parseInt(driver.element().getText(productsCartQuantity)
                .replaceAll("\\D", ""));
        for (String name : productName)
        {
            driver.element().clicking(getProductRemoveBox(name));
            productAddedToCart++;
        }
        driver.element().clicking(addToCartButton);
        return this;
    }
    @Step("Open product details page: {productName}")
    public WishlistPage openProductDetailsPage(String productName)
    {
        driver.element().clicking(getProductName(productName));
        return this;
    }
    @Step("Update quantity of '{productName}' to: {qty}")
    public WishlistPage updateProductQuantity(String productName , String qty)
    {
        quantityBeforeUpdating = driver.element().getPropertyValue(getProductQuantityField(productName),"value");
        driver.element().clearText(getProductQuantityField(productName));
        driver.element().typing(getProductQuantityField(productName),qty);
        driver.element().clicking(updateWishlistButton);
        return this;
    }

    //Verifications
    @Step("Verify number of products shown in wishlist matches wishlist counter")
    public WishlistPage verifyNumberOfProductInWishlist()
    {
        int actualNumber = 0;
        for (WebElement element:driver.element().findElements(quantityFields))
        {
            actualNumber += Integer.parseInt(element.getText());
        }
        int expectedNumber = Integer.parseInt(driver.element().getText(productsWishlistedQuantity).replaceAll("\\D", ""));
        driver.hardAssertions().assertTrue(actualNumber == expectedNumber,
                "Expected Wishlist quantity is: " + expectedNumber +
                        " , but Actual Wishlist quantity is: " + actualNumber);
        return this;
    }
    @Step("Verify number of wishlisted products matches the expected number")
    public WishlistPage verifyWishlistedProducts()
    {
       int actualNumber = driver.element().findElements(productsNumber).size();
        driver.hardAssertions().assertTrue(actualNumber == ProductDetailsPage.wishlistedProductsNumber,
                "Expected Wishlist quantity is: " + ProductDetailsPage.wishlistedProductsNumber +
                        " , but Actual Wishlist quantity is: " + actualNumber);
        return this;
    }
    @Step("Verify product '{productName}' quantity updated correctly")
    public WishlistPage verifyIfQuantityUpdated(String productName)
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
    @Step("Verify product added to cart from wishlist")
    public WishlistPage verifyAddedProductToCart()
    {
        int actualNumber = Integer.parseInt(driver.element().getText(productsWishlistedQuantity).replaceAll("\\D", ""));
        driver.hardAssertions().assertTrue(actualNumber == productAddedToCart,
                "Expected Wishlist quantity is: " + productAddedToCart +
                        " , but Actual Wishlist quantity is: " + actualNumber);
        return this;
    }
    @Step("Verify product '{productName}' is deleted from wishlist")
    public WishlistPage verifyDeletedProduct(String productName)
    {
        driver.softAssertions().assertTrue(driver.element().isPresent(getProductName(productName)) == null,
                "Product: " + productName + " is still present in the shopping cart after deletion");
        return this;
    }

    private By getProductQuantityField(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"qty nobr\"] //input)["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductName(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]");
            }
        }
        return locator ;
    }
    private By getProductRemoveBox(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@name=\"removefromcart\"])["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductCartBox(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@name=\"addtocart\"])["+ counter+ "]");
            }
        }
        return locator ;
    }
    private By getProductUnitPrice(String productName)
    {
        By locator = null;
        for(int counter = 1 ; counter <= driver.element().findElements(productsNumber).size() ; counter++)
        {
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
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
            if(driver.element().getTextIfPresent(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"product\"] //a)[" + counter + "]"))
                    .contains(productName))
            {
                locator = By.xpath("(//*[@class=\"product-subtotal\"])["+ counter+ "]");
            }
        }
        return locator ;
    }
}
