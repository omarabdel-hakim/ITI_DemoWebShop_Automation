package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import Utils.TimeManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class CheckOutPage
{
    private GUIDriver driver;
    private final JsonReader testData = new JsonReader("checkout-data");
    public CheckOutPage(GUIDriver driver) {
        this.driver = driver;
    }

    static double finalAmount;

    private final By SelectBillingAddress = By.id("billing-address-select");
    private final By SelectShippingAddress = By.id("shipping-address-select");
    private final By firstName = By.id("BillingNewAddress_FirstName");
    private final By lastName = By.id("BillingNewAddress_LastName");
    private final By email = By.id("BillingNewAddress_Email");
    private final By company =By.id("BillingNewAddress_Company");
    private final By country = By.id("BillingNewAddress_CountryId");
    private final By state = By.id("BillingNewAddress_StateProvinceId");
    private final By city = By.id("BillingNewAddress_City");
    private final By address1 = By.id("BillingNewAddress_Address1");
    private final By address2 = By.id("BillingNewAddress_Address2");
    private final By zipPostalCode = By.id("BillingNewAddress_ZipPostalCode");
    private final By phoneNumber = By.id("BillingNewAddress_PhoneNumber");
    private final By faxNumber = By.id("BillingNewAddress_FaxNumber");
    private final By continueButton = By.cssSelector("[onclick=\"Billing.save()\"]");
    private final By inStoryPickupOption = By.id("PickUpInStore");
    private final By PurchaseOrderNumber = By.id("PurchaseOrderNumber");
    private final By subTotalAmount = By.xpath("(//*[@class=\"product-price\"])[1]");
    private final By shippingAmount = By.xpath("(//*[@class=\"product-price\"])[2]");
    private final By feeAmount = By.xpath("(//*[@class=\"product-price\"])[3]");
    private final By taxAmount = By.xpath("(//*[@class=\"product-price\"])[4]");
    private final By totalAmount = By.cssSelector("[class=\"product-price order-total\"]");
    private final By confirmOrderButton = By.cssSelector("[value=\"Confirm\"]");
    private final By backToOrdersButton = By.xpath("//*[@id=\"confirm-order-buttons-container\"] //a");
    private final By confirmMessage = By.cssSelector("[class=\"section order-completed\"] strong");
    private final By orderDetails = By.xpath("//*[contains(text(),\"order details\")]");
    private final By creditType = By.id("CreditCardType");
    private final By cardHolder = By.id("CardholderName");
    private final By cardNumber = By.id("CardNumber");
    private final By expirationMonth = By.id("ExpireMonth");
    private final By expirationYear = By.id("ExpireYear");
    private final By cardCode = By.id("CardCode");
    private final By creditCardErrorMessage = By.cssSelector("[class=\"validation-summary-errors\"]");
    private final By orderReviewData = By.cssSelector("[class=\"order-review-data\"]");
    private final By totalUnitPrice = By.cssSelector("[class=\"product-unit-price\"]");
    static int count ;


    //Main Actions
    @Step("Select saved billing address")
    public CheckOutPage selectSavedBillingAddress()
    {
        driver.element().selectFromDropDown(SelectBillingAddress, 0);
        this.clickContinueButton();
        return this;
    }
    @Step("Select saved shipping address")
    public CheckOutPage selectSavedShippingAddress()
    {
        driver.element().selectFromDropDown(SelectShippingAddress, 0);
        this.clickContinueButton();
        return this;
    }
    @Step("Fill billing address form")
    public CheckOutPage fillBillingAddressForm()
    {
        if(driver.element().isPresent(SelectBillingAddress))
            driver.element().selectFromDropDown(SelectBillingAddress, "New Address");
        driver.element().clearText(firstName);
        driver.element().typing(firstName, testData.getJsonData("firstName"));
        driver.element().clearText(lastName);
        driver.element().typing(lastName, testData.getJsonData("lastName"));
        driver.element().clearText(email);
        driver.element().typing(email, testData.getJsonData("email"));
        driver.element().clearText(company);
        driver.element().typing(company, testData.getJsonData("company"));
        driver.element().selectFromDropDown(country, testData.getJsonData("country"));
        driver.element().selectFromDropDown(state, testData.getJsonData("state"));
        driver.element().clearText(city);
        driver.element().typing(city, testData.getJsonData("city"));
        driver.element().clearText(address1);
        driver.element().typing(address1, testData.getJsonData("address1"));
        driver.element().clearText(address2);
        driver.element().typing(address2, testData.getJsonData("address2"));
        driver.element().clearText(zipPostalCode);
        driver.element().typing(zipPostalCode, testData.getJsonData("zipPostalCode"));
        driver.element().clearText(phoneNumber);
        driver.element().typing(phoneNumber, testData.getJsonData("phoneNumber"));
        driver.element().clearText(faxNumber);
        driver.element().typing(faxNumber, testData.getJsonData("faxNumber"));
        this.clickContinueButton();
        return this;
    }
    @Step("Fill shipping address form")
    public CheckOutPage fillShippingAddressForm()
    {
        driver.element().selectFromDropDown(SelectShippingAddress, "New Address");
        driver.element().clearText(firstName);
        driver.element().typing(firstName, testData.getJsonData("firstName"));
        driver.element().clearText(lastName);
        driver.element().typing(lastName, testData.getJsonData("lastName"));
        driver.element().clearText(email);
        driver.element().typing(email, testData.getJsonData("email"));
        driver.element().clearText(company);
        driver.element().typing(company, testData.getJsonData("company"));
        driver.element().selectFromDropDown(country, testData.getJsonData("country"));
        driver.element().selectFromDropDown(state, testData.getJsonData("state"));
        driver.element().clearText(city);
        driver.element().typing(city, testData.getJsonData("city"));
        driver.element().clearText(address1);
        driver.element().typing(address1, testData.getJsonData("address1"));
        driver.element().clearText(address2);
        driver.element().typing(address2, testData.getJsonData("address2"));
        driver.element().clearText(zipPostalCode);
        driver.element().typing(zipPostalCode, testData.getJsonData("zipPostalCode"));
        driver.element().clearText(phoneNumber);
        driver.element().typing(phoneNumber, testData.getJsonData("phoneNumber"));
        driver.element().clearText(faxNumber);
        driver.element().typing(faxNumber, testData.getJsonData("faxNumber"));
        return this;
    }
    @Step("Click continue button")
    public CheckOutPage clickContinueButton()
    {
        driver.element().clicking(By.xpath("(//*[@value=\"Continue\"])["+(++count)+"]"));
        return this;
    }
    @Step("Select in-store pickup option")
    public CheckOutPage selectStoryPickupBox() {
        driver.element().clicking(inStoryPickupOption);
        this.clickContinueButton();
        return this;
    }
    @Step("Select shipping method: {method}")
    public CheckOutPage selectShippingMethod(String method)
    {
        switch (method.toLowerCase())
        {
            case "ground" -> driver.element().clicking(By.xpath("(//*[@name=\"shippingoption\"])[1]"));
            case "next day air" -> driver.element().clicking(By.xpath("(//*[@name=\"shippingoption\"])[2]"));
            case "2nd day air" -> driver.element().clicking(By.xpath("(//*[@name=\"shippingoption\"])[3]"));
        }
        this.clickContinueButton();
        return this;
    }
    @Step("Select payment method: {method}")
    public CheckOutPage selectPaymentMethod(String method)
    {
        switch (method.toLowerCase())
        {
            case "cash" -> driver.element().clicking(By.xpath("(//*[@name=\"paymentmethod\"])[1]"));
            case "check" -> driver.element().clicking(By.xpath("(//*[@name=\"paymentmethod\"])[2]"));
            case "credit" -> driver.element().clicking(By.xpath("(//*[@name=\"paymentmethod\"])[3]"));
            case "purchase" -> driver.element().clicking(By.xpath("(//*[@name=\"paymentmethod\"])[4]"));
        }
        this.clickContinueButton();
        return this;
    }
    @Step("Fill credit card options")
    public CheckOutPage fillCreditCardOptions(String creditCardType, String cardholder , String card16number , String expirationmonth, String expirationyear , String cardcode)
    {
        driver.element().selectFromDropDown(creditType,creditCardType);
        driver.element().typing(cardHolder,cardholder);
        driver.element().typing(cardNumber,card16number);
        driver.element().selectFromDropDown(expirationMonth,expirationmonth);
        driver.element().selectFromDropDown(expirationYear,expirationyear);
        driver.element().typing(cardCode,cardcode);
        this.clickContinueButton();
        return this ;
    }
    @Step("Enter purchase order number")
    public CheckOutPage enterPurchaseOrderNumber()
    {
        driver.element().typing(PurchaseOrderNumber, "P" + TimeManager.getSimpleTimesTamp());
        this.clickContinueButton();
        return this;
    }
    @Step("Confirm order")
    public CheckOutPage confirmOrder()
    {
        driver.element().clicking(confirmOrderButton);
        return this;
    }
    @Step("Navigate back to orders page")
    public CheckOutPage backToOrdersPage()
    {
        driver.element().clicking(backToOrdersButton);
        return this;
    }
    @Step("Navigate to order details page")
    public CheckOutPage navigateToOrderDetails()
    {
        driver.element().clicking(orderDetails);
        return this;
    }

    //Verifications
    @Step("Verify order review data is displayed")
    public CheckOutPage verifyOrderDataIsDisplayed()
    {
        driver.hardAssertions().assertTrue(driver.element().isDisplayed(orderReviewData)
                ,"Order Review Data isn't displayed");
        return this;
    }
    @Step("Verify final total amount")
    public CheckOutPage verifyFinalTotalAmount()
    {
        double expectedTotal = 0.0;
        for (int i=1 ; i<driver.element().findElements(By.xpath("//*[@class=\"cart-total-right\"] //span //span")).size() ;i++ )
        {
            expectedTotal += Double.parseDouble(driver.element().getText(By.xpath("(//*[@class=\"cart-total-right\"] //span //span)["+i+"]")));
        }
        double actualTotal = Double.parseDouble(driver.element().getText(totalAmount));
        driver.hardAssertions().assertTrue(actualTotal == expectedTotal," Total Amount Verification Failed");
        return this;
    }
    @Step("Verify order confirmation message")
    public CheckOutPage verifyConfirmationMessage()
    {
        String actualMessage = driver.element().getTextIfPresent(confirmMessage);
        driver.hardAssertions().assertEquals(actualMessage, testData.getJsonData("confirmationMessage")
                , "Order Confirmation Message Verification Failed");
        return this;
    }
    @Step("Verify filled credit card options")
    public CheckOutPage verifyFilledCreditCardOptions()
    {
        try{Thread.sleep(1000);}
        catch(Exception _){}
        if(driver.element().isPresent(creditCardErrorMessage))
        {
            driver.hardAssertions().assertFail(String.join(", ", getMissingFields()));
        }
        else driver.hardAssertions().assertTrue(true,"Oops!");
        return this ;
    }
    @Step("Verify total products amount")
    public CheckOutPage verifyTotalProductsAmount()
    {
        double unitPrice = 0.0;
        double qty = 0.0;
        double total = 0.0;
        for(int counter = 1 ; counter <= driver.element().findElements(totalUnitPrice).size() ; counter++)
        {
            unitPrice = Double.parseDouble(driver.element().getText(By.xpath("(//*[@class=\"product-unit-price\"])["+counter+"]")));
            qty = Double.parseDouble(driver.element().getText(By.xpath("(//*[@class=\"cart-item-row\"] //*[@class=\"qty nobr\"] //span)["+(counter*2)+"]")));
            total = Double.parseDouble(driver.element().getText(By.xpath("(//*[@class=\"product-subtotal\"])["+counter+"]")));
            if (unitPrice*qty != total ) driver.hardAssertions().assertFail("Wrong in Total Amount at product number " + counter);
            finalAmount += total;
        }
        double subTotal = Double.parseDouble(driver.element().getText(subTotalAmount));
        driver.hardAssertions().assertTrue(finalAmount == subTotal
                ,"Total Product Amount ("+finalAmount+") not equal to Sub-Total Amount ("+subTotal+")");
        return this ;
    }

    private List<String> getMissingFields()
    {
        List<String> fieldErrors = new ArrayList<>();
        for (int i = 1; i <= driver.element().findElements(creditCardErrorMessage).size(); i++)
        {
            fieldErrors.add(driver.element()
                    .getTextIfPresent(By.xpath("(//*[@class=\"validation-summary-errors\"] //li)["+i+"]")));
        }
        return fieldErrors;
    }







}
