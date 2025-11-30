
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import Pages.RegisterPage;
import Utils.DataReader.JsonReader;
import Utils.TimeManager;
import io.qameta.allure.*;

import org.testng.annotations.*;

@Listeners(TestNGListeners.class)
@Epic("User Registration Module")
@Feature("Register Functionality")
public class RegisterTest
{
    GUIDriver driver;
    RegisterPage registerPage;
    JsonReader testData;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("register-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        registerPage = new RegisterPage(driver);
        new NavigationBar(driver).navigate().clickOnRegisterButton();
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Valid user can register successfully")
    @Description("This test verifies that a user can successfully register with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void ValidRegistrationTC()
    {
        registerPage.chooseGender()
                .enterFirstName(testData.getJsonData("firstName"))
                .enterLastName(testData.getJsonData("lastName"))
                .enterEmail(testData.getJsonData("email") + TimeManager.getSimpleTimesTamp() + "@gmail.com")
                .enterPassword(testData.getJsonData("password"))
                .enterConfirmPassword(testData.getJsonData("confirmPassword"))
                .clickRegisterButton().verifyRegistration();
    }

    @Test(priority = 1)
    @Story("Registering with an existing email shows error")
    @Description("This test verifies that registering with an already existing email address triggers an error message")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyRegisterWithExistEmailShowsErrorTC()
    {
        registerPage.chooseGender()
                .enterFirstName(testData.getJsonData("firstName"))
                .enterLastName(testData.getJsonData("lastName"))
                .enterEmail("omarabdelhakim777@gmail.com")
                .enterPassword(testData.getJsonData("password"))
                .enterConfirmPassword(testData.getJsonData("confirmPassword"))
                .clickRegisterButton().verifyRegistration();
    }

    @Test(priority = 1)
    @Story("Registering with empty last name and email shows error")
    @Description("This test verifies that leaving the last name and email empty triggers proper validation errors")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyRegisterWithEmptyLastNameAndEmailShowsErrorTC()
    {
        registerPage.chooseGender()
                .enterFirstName(testData.getJsonData("firstName"))
                .enterPassword(testData.getJsonData("password"))
                .enterConfirmPassword(testData.getJsonData("confirmPassword"))
                .clickRegisterButton().verifyRegistration();
    }

}
