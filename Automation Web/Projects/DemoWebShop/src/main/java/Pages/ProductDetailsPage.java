package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage
{
    private GUIDriver driver;
    private final JsonReader testData = new JsonReader("product-data");
    public static int wishlistedProductsNumber = 0;
    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final By productName = By.cssSelector("[itemprop=\"name\"]");
    private final By price = By.cssSelector("[itemprop=\"price\"]");
    private final By quantity = By.cssSelector("[class=\"qty-input\"]");
    private final By addToWishList = By.cssSelector("[class=\"button-2 add-to-wishlist-button\"]");
    private final By addToCompareList = By.cssSelector("[value=\"Add to compare list\"]");
    private final By addToCart = By.cssSelector("[class=\"button-1 add-to-cart-button\"]");
    private final By contentMessage = By.cssSelector("[class=\"content\"]");
    private final By closeButton = By.cssSelector("[class=\"close\"]");
    private final By compareIcon = By.xpath("//*[text()=\"Compare products\"]");

    //Custom Locators
    private final By recipientName = By.cssSelector("#giftcard_2_RecipientName");
    private final By recipientEmail = By.cssSelector("#giftcard_2_RecipientEmail");
    private final By senderName = By.cssSelector("[class=\"sender-name\"]");
    private final By senderEmail = By.cssSelector("[class=\"sender-email\"]");

    private final By processor = By.xpath("(//*[@class=\"option-list\"] )[1]");
    private final By RAM = By.xpath("(//*[@class=\"option-list\"] )[2]");
    private final By HDD = By.xpath("(//*[@class=\"option-list\"] )[3]");
    private final By software = By.xpath("(//*[@class=\"option-list\"] )[4]");

    private final By typeSelection = By.xpath("//input)[1]"); //1 of 3 of 4
    private final By size = By.cssSelector("#product_attribute_28_7_10");
    private final By whiteColors = By.cssSelector("(//*[@id=\"color-squares-11\"] //li)[1]");
    private final By blackColors = By.cssSelector("(//*[@id=\"color-squares-11\"] //li)[2]");
    private final By greenColors = By.cssSelector("(//*[@id=\"color-squares-11\"] //li)[3]");

    //Main Actions
    @Step("Adding product to cart from Product Details page")
    public ProductDetailsPage addToCart()
    {
        driver.element().clicking(addToCart);
        driver.element().clicking(closeButton);
        return this;
    }
    @Step("Changing product quantity to: {qty}")
    public ProductDetailsPage changeQuantity(String qty)
    {
        driver.element().clearText(quantity);
        driver.element().typing(quantity, qty);
        return this;
    }
    @Step("Adding product to Wish List")
    public ProductDetailsPage addToWishList()
    {
        if(driver.element().isPresent(addToWishList))
        {
            driver.element().clicking(addToWishList);
            driver.element().clicking(closeButton);
            wishlistedProductsNumber++;
        }
        return this;
    }
    @Step("Adding product to Compare List")
    public ProductDetailsPage addToCompareList()
    {
        driver.element().clicking(addToCompareList);
        return this;
    }

    //Verifications and Validations
    @Step("Verifying Product Details: Name '{expectedName}', Price '{expectedPrice}'")
    public ProductDetailsPage verifyProductDetails(String expectedName,String expectedPrice)
    {
        String actualName = driver.element().getText(productName);
        String actualPrice = driver.element().getText(price);
        driver.hardAssertions().assertEquals(actualName, expectedName, "Product Name Verification Failed");
        driver.hardAssertions().assertEquals(actualPrice, expectedPrice, "Product Price Verification Failed");
        return this;
    }
    @Step("Verifying product is added to cart")
    public ProductDetailsPage verifyAddedToCart()
    {
        String actualMessage = driver.element().getText(contentMessage);
        driver.hardAssertions().assertEquals(actualMessage, testData.getJsonData("messages.addToCartMessage"), "Add to Cart Message Verification Failed");
        return this;
    }
    @Step("Verifying product is added to Wish List")
    public ProductDetailsPage verifyAddedToWishList()
    {
        if(driver.element().isPresent(addToWishList))
        {
            String actualMessage = driver.element().getText(contentMessage);
            driver.hardAssertions().assertEquals(actualMessage, testData.getJsonData("messages.addToWishlistMessage")
                    , "Add to WishList Message Verification Failed");
        }
        else  driver.hardAssertions().assertFail("Add-To-WishList Button isn't visible");
        return this;
    }
    @Step("Verifying product is added to Compare List")
    public ProductDetailsPage verifyAddedToCompareList()
    {
        String actualMessage = driver.element().getText(compareIcon);
        driver.hardAssertions().assertEquals(actualMessage, "Compare products", "Add to Compare List Message Verification Failed");
        return this;
    }


    //Sub-Actions
    @Step("Entering Recipient Name: {name} and Email: {email}")
    public ProductDetailsPage enterRecipientNameAndEmail(String name,String email)
    {
        driver.element().typing(recipientName, name);
        driver.element().typing(recipientEmail, email);
        return this;
    }
    @Step("Entering Sender Name: {name} and Email: {email}")
    public ProductDetailsPage enterSenderNameAndEmail(String name,String email)
    {
        driver.element().typing(senderName, name);
        driver.element().typing(senderEmail, email);
        return this;
    }
    @Step("Selecting product size '{sizeValue}'")
    public ProductDetailsPage selectProductSize(String sizeValue)
    {
        driver.element().selectFromDropDown(size, sizeValue);
        return this;
    }
    @Step("Selecting product color '{color}'")
    public ProductDetailsPage selectProductColor(String color)
    {
        switch (color.toLowerCase())
        {
            case "white" -> driver.element().clicking(whiteColors);
            case "black" -> driver.element().clicking(blackColors);
            case "green" -> driver.element().clicking(greenColors);
        }
        return this;
    }
    @Step("Selecting Processor option: {type}")
    public ProductDetailsPage selectProductProcessor(String type)
    {
        switch (type.toLowerCase())
        {
            case "slow" -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[1])"));
            case "medium" -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[2])"));
            case "fast" -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[3])"));
        }
        return this;
    }
    @Step("Selecting RAM option: {type} GB")
    public ProductDetailsPage selectProductRAM(int type)
    {
        switch (type)
        {
            case 2 -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[4])"));
            case 4 -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[5])"));
            case 8 -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[6])"));
        }
        return this;
    }
    @Step("Selecting HDD option: {type} GB")
    public ProductDetailsPage selectProductHDD(int type)
    {
        switch (type)
        {
            case 320 -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[7])"));
            case 400 -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[8])"));
        }
        return this;
    }
    @Step("Selecting Software option: {type}")
    public ProductDetailsPage selectProductSoftware(String type)
    {
        switch (type.toLowerCase())
        {
            case "image" -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[9])"));
            case "office" -> driver.element().clicking(By.xpath("((//*[@class=\"option-list\"] //li //input)[10])"));
        }
        return this;
    }

}


