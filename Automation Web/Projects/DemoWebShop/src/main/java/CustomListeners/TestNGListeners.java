package CustomListeners;

import Drivers.GUIDriver;
import Media.ScreenShot;
import Utils.DataReader.PropertyReader;
import Utils.FileUtillls;
import Utils.Logs.LogsManager;
import Utils.Reports.AllureAttachmentManager;
import Utils.Reports.AllureConstants;
import Utils.Reports.AllureEnvironmentManager;
import Utils.Reports.AllureReportGenerator;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;


public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {

    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("ITI Project");
    }
    public void onExecutionStart() {
        LogsManager.info("Test Execution started");
        cleanTestOutputDirectories();
        createTestOutputDirectories();
        PropertyReader.loadProperties();
        AllureEnvironmentManager.setEnvironmentVariables();
    }

    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.renameReport();
        //AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult)
    {
        if (method.isTestMethod())
        {
            WebDriver driver = GUIDriver.get();
            switch (testResult.getStatus()){
                    case ITestResult.SUCCESS -> ScreenShot.takeFullPageScreenshot(driver,"passed-" + testResult.getName());
                    case ITestResult.FAILURE -> ScreenShot.takeFullPageScreenshot(driver,"failed-" + testResult.getName());
                    case ITestResult.SKIP -> ScreenShot.takeFullPageScreenshot(driver,"skipped-" + testResult.getName());
                }
            AllureAttachmentManager.attachLogs();
        }
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " passed");
    }
    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " failed");
    }
    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " skipped");
    }


    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtillls.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtillls.cleanDirectory(new File(ScreenShot.SCREENSHOT_PATH));
        FileUtillls.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtillls.forceDelete(new File(LogsManager.LOGS_PATH +"logs.log"));
    }
    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtillls.createDirectory(ScreenShot.SCREENSHOT_PATH);
        FileUtillls.createDirectory("src/test/resources/downloads/");

    }
}