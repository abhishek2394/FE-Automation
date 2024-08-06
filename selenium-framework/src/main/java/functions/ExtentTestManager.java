package functions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;


public class ExtentTestManager {

    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    static ExtentReports extent = ExtentManager.createExtentReports();


    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.flush();
    }

    public static synchronized ExtentTest startTest(String testName, String description) {
        ExtentTest test = extent.createTest("[" + testName + "]" + " - " + description, description);
        extentTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

}
