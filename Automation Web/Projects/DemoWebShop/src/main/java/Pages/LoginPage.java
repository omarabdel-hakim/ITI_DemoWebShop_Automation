package Pages;

import Drivers.GUIDriver;
import Utils.DataReader.JsonReader;
import Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage
{
    private final GUIDriver driver;
    JsonReader testData = new JsonReader("login-data");
    public LoginPage(GUIDriver driver)
    {
        this.driver = driver;
    }
    private final By emailField = By.id("Email");
    private final By passwordField = By.id("Password");
    private final By loginButton = By.cssSelector("[value=\"Log in\"]");
    private final By RememberMeCheckbox = By.id("RememberMe");
    private final By forgotPasswordLink = By.xpath("//*[.='Forgot password?']");
    private final By registerButton = By.cssSelector("[value=\"Register\"]");
    private final By accountVerification = By.xpath("(//*[@class=\"account\"])[1]");
    private final By recoverButton = By.cssSelector("[value=\"Recover\"]");
    private final By recoveryEmailMessage = By.cssSelector("[class=\"result\"]");
    private final By logoutButton = By.cssSelector("[class=\"ico-logout\"]");
    private final By registerLink = By.cssSelector("[class=\"ico-register\"]");
    private final By validationErrorMessage = By.cssSelector("[class=\"validation-summary-errors\"] li");

    @Step("Navigate to Login Page")
    public LoginPage navigateToLoginPage()
    {
        driver.browser().navigateTo(testData.getJsonData("loginURL"));
        return this;
    }
    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email)
    {
        driver.element().typing(emailField,email);
        return this;
    }
    @Step("Enter password")
    public LoginPage enterPassword(String password)
    {
        driver.element().typing(passwordField,password);
        return this;
    }
    @Step("Click Login button")
    public LoginPage clickLoginButton()
    {
        driver.element().clicking(loginButton);
        return this;
    }
    @Step("Select Remember Me")
    public LoginPage chooseRememberMe()
    {
        driver.element().clicking(RememberMeCheckbox);
        return this;
    }
    @Step("Click Forgot Password link")
    public LoginPage enterForgotPassword()
    {
        driver.element().clicking(forgotPasswordLink);
        return this;
    }
    @Step("Click Register button inside Login Page")
    public LoginPage enterRegisterButton()
    {
        driver.element().clicking(registerButton);
        return this;
    }
    @Step("Refresh login page")
    public LoginPage refreshPage()
    {
        driver.browser().refreshPage();
        return this;
    }
    @Step("Click Recover Password button")
    public LoginPage enterRecoveryButton()
    {
        driver.element().clicking(recoverButton);
        String msg = driver.element().getText(recoveryEmailMessage);
        driver.hardAssertions().assertTrue(msg.contains("Email with instructions has been sent to you."),
                "Password recovery email not sent!");
        LogsManager.info("Recovery email is sent successfully.");
        return this;
    }
    @Step("Verify Login results")
    public LoginPage validAccountVerification()
    {
        if(driver.element().isPresent(validationErrorMessage))
        {
            String msg = driver.element().getTextIfPresent(validationErrorMessage) ;
            if (msg.contains("No customer")) driver.hardAssertions().assertEquals(msg
                ,testData.getJsonData("invalidOrEmptyEmailMessage")
                ,"Login successfully");
            else driver.hardAssertions().assertEquals(msg
                    ,testData.getJsonData("invalidOrEmptyPasswordMessage")
                    ,"Login successfully");
        }
        else driver.hardAssertions().assertEquals(driver.element().getTextIfPresent(accountVerification),
                testData.getJsonData("email"),"Failed To Login");



        return this;
    }
    @Step("Verify navigation to Forgot Password page")
    public LoginPage forgotPasswordPageVerification()
    {
        driver.hardAssertions().assertEquals(driver.browser().getCurrentPageURL()
                ,testData.getJsonData("forgotPasswordURL"),"Forgot Password navigation failed!");
        LogsManager.info("Successfully navigated to Forgot Password page");
        return this;
    }
    @Step("Verify navigation to Register page from Login")
    public LoginPage registerPageVerification()
    {
        driver.hardAssertions().assertEquals(driver.browser().getCurrentPageURL()
                ,testData.getJsonData("registerURL"),"Register page navigation failed!");
        LogsManager.info("Successfully navigated to Register page");
        return this;
    }
    @Step("Logout User")
    public LoginPage logout()
    {
        driver.element().clicking(logoutButton);
        driver.hardAssertions().assertEquals(driver.element().getText(registerLink),
                "Register","Logout failed!" + driver.element().getText(registerLink));
        LogsManager.info("Logged out successfully.");
        return this;
    }



}
