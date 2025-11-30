
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.LoginPage;
import Utils.DataReader.JsonReader;

import io.qameta.allure.*;
import org.testng.annotations.*;

@Listeners(TestNGListeners.class)
@Epic("Authentication")
@Feature("Login & Logout Flow")
public class LogoutTest {

    GUIDriver driver;
    LoginPage loginPage;
    JsonReader testData;

    @BeforeClass
    @Step("Initialize WebDriver, LoginPage object, and load login test data")
    public void preCondition() {
        driver = new GUIDriver();
        loginPage = new LoginPage(driver);
        testData = new JsonReader("login-data");
    }

    @AfterClass
    @Step("Quit WebDriver after test execution")
    public void tearDown() {
        driver.quitDriver();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can successfully login using valid email and password")
    @Story("Valid Login")
    @Step("Execute valid login test case")
    public void validLoginTC() {

        loginPage.navigateToLoginPage()
                .enterEmail(testData.getJsonData("email"))
                .enterPassword(testData.getJsonData("password"))
                .chooseRememberMe()
                .clickLoginButton()
                .validAccountVerification()
                .refreshPage()
                .validAccountVerification();
    }

    @Test(dependsOnMethods = "validLoginTC")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a logged-in user can logout successfully")
    @Story("Logout Flow")
    @Step("Execute logout test case")
    public void logoutTest() {
        loginPage.logout();
    }
}
