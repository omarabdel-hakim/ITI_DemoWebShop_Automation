package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterPage
{
    private final GUIDriver driver;
    private final JsonReader testData = new JsonReader("register-data");

    public RegisterPage(GUIDriver driver)
    {
        this.driver = driver;
    }


    private final By gender = By.id("gender-" + new JsonReader("register-data").getJsonData("gender"));
    private final By firstName = By.id("FirstName");
    private final By lastName = By.id("LastName");
    private final By email = By.id("Email");
    private final By password = By.id("Password");
    private final By confirmPassword = By.id("ConfirmPassword");
    private final By registerButton = By.id("register-button");
    private final By confirmationMessage = By.className("result");
    private final By fieldValidationMessage = By.cssSelector("[class=\"field-validation-error\"]");
    private final By firstFieldValidationMessage = By.xpath("(//*[@class=\"field-validation-error\"])[1]");
    private final By existEmailMessage = By.cssSelector("[class=\"validation-summary-errors\"] li");

    @Step("Choose gender")
    public RegisterPage chooseGender()
    {
        driver.element().clicking(gender);
        return this;
    }
    @Step("Enter first name: {fName}")
    public RegisterPage enterFirstName(String fName)
    {
        driver.element().typing(firstName,fName);
        return this;
    }
    @Step("Enter last name: {lName}")
    public RegisterPage enterLastName(String lName)
    {
        driver.element().typing(lastName,lName);
        return this;
    }
    @Step("Enter email: {mail}")
    public RegisterPage enterEmail(String mail)
    {
        driver.element().typing(email,mail);
        return this;
    }
    @Step("Enter password")
    public RegisterPage enterPassword(String pass) {
        driver.element().typing(password, pass);
        return this;
    }
    @Step("Enter confirm password")
    public RegisterPage enterConfirmPassword(String cPass) {
        driver.element().typing(confirmPassword, cPass);
        return this;
    }
    @Step("Click on Register button")
    public RegisterPage clickRegisterButton()
    {
        driver.element().clicking(registerButton);
        return this;
    }
    @Step("Verify Registration Result")
    public RegisterPage verifyRegistration()
    {
        if(driver.element().isPresent(fieldValidationMessage))
            driver.hardAssertions().assertTrue(driver.element().getTextIfPresent(firstFieldValidationMessage)
                            .contains("is required.")
            ,String.join(", ", getMissingFields()));


        else if(driver.element().isPresent(existEmailMessage))
            driver.hardAssertions().assertEquals(driver.element().getTextIfPresent(existEmailMessage)
                    ,testData.getJsonData("existMessage"),"Email already exists");


        else if(driver.element().isPresent(confirmationMessage))
            driver.hardAssertions().assertEquals(driver.element().getTextIfPresent(confirmationMessage),
                testData.getJsonData("message"),
                "Missing or wrong registration fields");
        return this;
    }
    @Step("Get missing fields validation messages")
    private List<String> getMissingFields()
    {
        List<String> fieldErrors = new ArrayList<>();
        for (int i = 1; i <= driver.element().findElements(fieldValidationMessage).size(); i++)
        {
            fieldErrors.add(driver.element()
                    .getTextIfPresent(By.xpath("(//*[@class=\"field-validation-error\"])["+i+"]")));
        }
    return fieldErrors;
    }
}
