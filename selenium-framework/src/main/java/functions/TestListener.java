package functions;

import functions.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {


    // Initialize Log4j logs

    //private static Logger Log = Logger.class  Logger.getLogger(org.apache.commons.logging.Log.class.getName());
    private static Logger Log = LoggerFactory.getLogger(org.apache.commons.logging.Log.class);

    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

    public static void startTestCase(String sTestCaseName) {
        Log.info("****************************************************************");
        Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName + "  $$$$$$$$$$$$$$$$$$$$$$$$$");
        Log.info("******************************************************************");
    }


    public static void endTestCase(String sTestCaseName) {
        Log.info("XXXXXXXXXXXXXXXX " + "-E---N---D-" + "   XXXXXXXXXXXXXX");
    }


    // Need to create these methods, so that they can be called

    public static void info(String message) {
        Log.info("[Thread "+Thread.currentThread().getId()+"] " + message, true);
        ExtentTestManager.getTest().log(Status.INFO, message);
    }
    public static void warn(String message) {
        Log.warn("[Thread "+Thread.currentThread().getId()+"] " + message, true);
        ExtentTestManager.getTest().log(Status.WARNING, message);
    }
    public static void pass(String message) {
        Log.info("[Thread "+Thread.currentThread().getId()+"] " + message, true);
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        Log.error("[Thread "+Thread.currentThread().getId()+"] " + message, true);
        //Log.fatal(message);
        ExtentTestManager.getTest().log(Status.FAIL, message);
    }
//    public static void debug(String message) {
//        Log.debug(message);
//        ExtentTestManager.getTest().log(Status.DEBUG, message);
//    }

    public void onStart(ITestContext context) {
        Reporter.log("*** Test Suite " + context.getName() + " started ***", true);
    }

    public void onFinish(ITestContext context) {
        Reporter.log(("*** Test Suite " + context.getName() + " ending ***"),true);
        ExtentTestManager.endTest();
        //  ExtentManager.getInstance().flush();
    }

    public void onTestStart(ITestResult result) {
        Reporter.log(("[Thread - "+Thread.currentThread().getId()+"] "+ "** Running test method " + result.getMethod().getMethodName() + "..."), true);
        ExtentTestManager.startTest("Test: " + result.getMethod().getMethodName(), "Class: "+  result.getMethod().getTestClass().getName() + " | Description: " + result.getMethod().getDescription());
    }

    public void onTestSuccess(ITestResult result) {
        Reporter.log("[Thread - "+Thread.currentThread().getId()+"] " + "** Executed " + result.getMethod().getMethodName() + " test successfully...", true);
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult result) {
        Reporter.log("[Thread - "+Thread.currentThread().getId()+"] " + "** Test execution " + result.getMethod().getMethodName() + " failed...", true);
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed" + "\n\n" + result.getThrowable());
        Reporter.log("\n** Test Case Failed is due to : "+ result.getThrowable());

    }

    public void onTestSkipped(ITestResult result) {
        Reporter.log("[Thread - "+Thread.currentThread().getId()+ "]+ ** Test " + result.getMethod().getMethodName() + " skipped...", true);
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped | Skipped By -> " + result.getSkipCausedBy());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Reporter.log("*** Test failed but within percentage % " + result.getMethod().getMethodName(), true);
    }
}
