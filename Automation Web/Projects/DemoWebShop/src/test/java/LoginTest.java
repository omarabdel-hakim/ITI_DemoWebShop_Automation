
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import Pages.LoginPage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;


@Listeners(TestNGListeners.class)
@Epic("User Authentication Module")
@Feature("Login Functionality")
public class LoginTest
{
    GUIDriver driver;
    LoginPage loginPage;
    JsonReader testData;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("login-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        loginPage = new LoginPage(driver);
        new NavigationBar(driver).navigate().clickOnLoginButton();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("User can login with valid credentials")
    @Description("This test verifies that a user can successfully login using valid email and password")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyValidLoginDataTC()
    {
        loginPage.enterEmail(testData.getJsonData("email"))
                .enterPassword(testData.getJsonData("password"))
                .chooseRememberMe().clickLoginButton().validAccountVerification();
    }

    @Test(priority = 1)
    @Story("Login fails when email is empty")
    @Description("This test verifies that leaving the email empty triggers proper validation errors while entering a valid password")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyLoginWithEmptyEmailAndValidDataShowsErrorTC()
    {
        loginPage.enterPassword(testData.getJsonData("password"))
                .clickLoginButton().validAccountVerification();
    }

    @Test(priority = 1)
    @Story("Login fails with invalid password")
    @Description("This test verifies that entering an invalid password with a valid email triggers proper validation errors")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyLoginWithInvalidPasswordAndValidDataShowsErrorTC()
    {
        loginPage.enterEmail(testData.getJsonData("email"))
                .enterPassword(testData.getJsonData("invalidPassword"))
                .clickLoginButton().validAccountVerification();
    }


}
