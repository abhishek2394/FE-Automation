package functions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Properties;


public class ExtentManager {

    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Automation Run Report");
        reporter.config().thumbnailForBase64(true);
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("AUTHOR", "Abhishek Kumar");
        extentReports.setSystemInfo("URL", seleniumProperties().getProperty("selenium.url"));
        extentReports.setSystemInfo("BROWSER", seleniumProperties().getProperty("selenium.browserStartCommand"));
        extentReports.setSystemInfo("BROWSER_MODE", seleniumProperties().getProperty("selenium.browserMode"));
        extentReports.setSystemInfo("PAGE_LOAD_TIME_IN_SECONDS", seleniumProperties().getProperty("selenium.pageloadtime"));
        extentReports.setSystemInfo("TIME_OUT_IN_SECONDS", seleniumProperties().getProperty("selenium.timeout"));
        extentReports.setSystemInfo("EMAIL", seleniumProperties().getProperty("selenium.email"));
        extentReports.setSystemInfo("PASSWORD", seleniumProperties().getProperty("selenium.password"));

        return extentReports;
    }

    public static Properties seleniumProperties(){
        SeleniumPropertyReader propertyReader = new SeleniumPropertyReader();
        return propertyReader.loadProperties();
    }
}
