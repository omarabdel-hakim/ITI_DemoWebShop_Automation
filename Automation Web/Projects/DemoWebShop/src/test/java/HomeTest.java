
import CustomListeners.TestNGListeners;
import Drivers.GUIDriver;
import Pages.Components.NavigationBar;
import Pages.HomePage;
import Utils.DataReader.JsonReader;
import org.testng.annotations.*;
import io.qameta.allure.*;


@Listeners(TestNGListeners.class)
@Epic("Home Module")
@Feature("Home Page Features")
public class HomeTest
{
    GUIDriver driver;
    NavigationBar navigationBar;
    JsonReader testData;
    HomePage homePage ;

    @BeforeClass
    public void preCondition()
    {
        testData = new JsonReader("product-data");
    }
    @BeforeMethod
    public void setUp()
    {
        driver = new GUIDriver();
        navigationBar = new NavigationBar(driver);
        navigationBar.navigate();
        homePage = new HomePage(driver);
    }
    @AfterMethod
    public void tearDown()
    {
        driver.quitDriver();
    }

    @Test(priority = 0)
    @Story("Verify featured products are displayed on homepage")
    @Description("This test ensures that all featured products are correctly displayed on the homepage")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Omar Abdelhakim")
    public void verifyFeaturedProductsDisplayedTC()
    {
        homePage.verifyFeaturedProductsDisplayed();
    }

    @Test(priority = 1)
    @Story("Verify top menu navigation works correctly")
    @Description("This test verifies that clicking on items in the top menu navigates to the correct pages")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyTopMenuNavigationTC()
    {
        homePage.verifyTopMenuNavigation();
    }

    @Test(priority = 1)
    @Story("Verify valid search returns results")
    @Description("This test verifies that entering a valid product name in the search bar returns the correct search results")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Omar Abdelhakim")
    public void verifyValidSearchGetsResultsTC()
    {
        navigationBar.searchForProduct(testData.getJsonData("searchProduct.name"))
                .verifySearching();
    }

    @Test(priority = 2)
    @Story("Verify invalid search returns no results")
    @Description("This test verifies that entering an invalid product name returns no search results")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyInvalidSearchGetsNoResultsTC()
    {
        navigationBar.searchForProduct(testData.getJsonData("searchProduct.invalidName"))
                .verifySearching();
    }

    @Test(priority = 2)
    @Story("Verify empty search triggers error")
    @Description("This test verifies that performing a search without entering a keyword shows the appropriate error message")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyEmptySearchGetsAlertErrorTC()
    {
        navigationBar.searchForProduct("")
                .verifySearching();
    }

    @Test(priority = 2)
    @Story("Verify newsletter subscription with invalid email")
    @Description("This test verifies that entering an invalid email in the newsletter subscription field triggers validation error")
    @Severity(SeverityLevel.MINOR)
    @Owner("Omar Abdelhakim")
    public void verifyEnterInvalidEmailInNewsLetterTC()
    {
        homePage.enterEmailInNewsLetter(testData.getJsonData("homePage.inValidNewsletterEmail"))
                .verifyEnterEmailInNewsLetter();
    }



}

