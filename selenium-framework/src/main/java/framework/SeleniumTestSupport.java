package framework;

import functions.ExtentTestManager;
import functions.TestListener;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;



import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import java.util.concurrent.atomic.AtomicInteger;

public class SeleniumTestSupport {

    protected static ThreadLocal<WebDriver> singleSharedDriver = new ThreadLocal<>();
    protected static WebDriver driver;

    protected static Long PAGE_LOAD_TIME_IN_SECONDS;
    protected static Long TIME_OUT_IN_SECONDS;
    protected static Properties singleSharedProperties;
    protected static String browserMode;
    protected static String GET_URL;
    protected static String ADMIN_EMAIL;
    protected static String ADMIN_PASSWORD;
    protected static String HUB_URL;

    protected static ThreadLocal<String> className = new ThreadLocal<String>();
    private static final AtomicInteger numberOfClassesExecuted = new AtomicInteger();
    public static Integer numberOfTests;
    String filepath = "./Reports/Screenshots/";

    public SeleniumTestSupport(){}

    public static WebDriver getDriver(){
        return singleSharedDriver.get();
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSeleniumClient() {
//        Logs.startLog("Test", "TestStart");
//        Logs.addToReport("Adding some", "INFO");
        singleSharedProperties = new Properties(System.getProperties());
        System.out.println();

        InputStream seleniumPropertyFile = null;
        //this.getClass().getClassLoader().getResourceAsStream("selenium.properties");
        try {
            seleniumPropertyFile = new FileInputStream("src/test/resources/selenium.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(seleniumPropertyFile);
        if (seleniumPropertyFile != null) {
            try {
                singleSharedProperties.load(seleniumPropertyFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        String pageLoadTimeInSeconds = System.getProperty("selenium.pageloadtime", singleSharedProperties.getProperty("selenium.pageloadtime", "2"));
        PAGE_LOAD_TIME_IN_SECONDS = Long.valueOf((pageLoadTimeInSeconds));


        String timeOutInSeconds = System.getProperty("selenium.timeout", singleSharedProperties.getProperty("selenium.timeout", "20"));
        TIME_OUT_IN_SECONDS = Long.valueOf(timeOutInSeconds);


        browserMode = System.getProperty("selenium.browserMode",singleSharedProperties.getProperty("selenium.browserMode"));
        if(browserMode != null){
            System.out.println("BROWSER MODE : "+ browserMode);
        }
        else {
            System.out.println("[NOTE]: selenium.browserMode is null! Tests will run in incognito");
            browserMode="";
        }

        System.out.println("PAGE_LOAD_TIME_IN_SECONDS : " + PAGE_LOAD_TIME_IN_SECONDS + " secs");

        System.out.println("TIME_OUT_IN_SECONDS : " + TIME_OUT_IN_SECONDS + " secs");

        GET_URL = System.getProperty("selenium.url",singleSharedProperties.getProperty("selenium.url"));
        System.out.println("APPLICATION URL IS : " + GET_URL);

        HUB_URL = System.getProperty("selenium.hub.url",singleSharedProperties.getProperty("selenium.hub.url"));
        System.out.println("HUB URL IS : " + HUB_URL);

        ADMIN_EMAIL = System.getProperty("selenium.email",singleSharedProperties.getProperty("selenium.email"));
        ADMIN_PASSWORD = System.getProperty("selenium.password",singleSharedProperties.getProperty("selenium.password"));

    }

    @AfterSuite(alwaysRun = true)
    public void stopSeleniumClient() {
 //       Logs.stopLog();


    }

    @AfterClass(alwaysRun = true)
    public void tearDownDriver(){
        driver.close();
        driver.quit();
    }

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setupDriver (String browser) {
        switch (browser) {
            case "firefox":
                setupFirefox();
                break;
            case "remote-firefox":
                setupRemoteFirefox();
                break;
            case "chrome":
                setupChrome();
                break;
            case "remote-chrome":
                setupRemoteChrome();
                break;
            default:
                setupChrome();
        }
    }

    public static void setupFirefox(){
        if (getDriver() == null) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--allow-running-insecure-content");
            firefoxOptions.addArguments("no-sandbox");
            singleSharedDriver.set(new FirefoxDriver(firefoxOptions));
            System.out.println("driver time out will be " + TIME_OUT_IN_SECONDS + " seconds");
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIME_OUT_IN_SECONDS));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME_IN_SECONDS));
            getDriver().manage().window().maximize();
        }
        driver = getDriver();
        Reporter.log("Firefox Driver Created Successfully!", true);
        driver.get(GET_URL);
        System.out.println("Current windows are " + driver.getWindowHandles());
    }

    public static void setupRemoteFirefox(){
        if (getDriver() == null) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--allow-running-insecure-content");
            firefoxOptions.addArguments("no-sandbox");
            try {
                singleSharedDriver.set(new RemoteWebDriver(new URL(HUB_URL),firefoxOptions));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("driver time out will be " + TIME_OUT_IN_SECONDS + " seconds");
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIME_OUT_IN_SECONDS));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME_IN_SECONDS));
            getDriver().manage().window().maximize();
        }
        driver = getDriver();
        Reporter.log("Firefox Driver Created Successfully!", true);
        driver.get(GET_URL);
        System.out.println("Current windows are " + driver.getWindowHandles());
    }

    public void setupChrome(){
        if (getDriver() == null) {
            String downloadFilePath = getResourcePath("");
            ChromeOptions options = getChromeOptions(downloadFilePath);
            singleSharedDriver.set(new ChromeDriver(options));
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIME_OUT_IN_SECONDS));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME_IN_SECONDS));
            getDriver().manage().window().maximize();
        }

        driver = getDriver();
        Reporter.log("Chrome Driver Created Successfully!", true);
        driver.get(GET_URL);
        System.out.println("Current windows are " + driver.getWindowHandles());
    }

    public void setupRemoteChrome() {
        if (getDriver() == null) {
            String downloadFilePath = getResourcePath("");
            ChromeOptions options = getChromeOptions(downloadFilePath);
            try {
                singleSharedDriver.set(new RemoteWebDriver(new URL(HUB_URL),options));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIME_OUT_IN_SECONDS));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME_IN_SECONDS));
            getDriver().manage().window().maximize();
        }

        driver = getDriver();
        Reporter.log("Remote Chrome Driver Created Successfully!", true);
        driver.get(GET_URL);
        System.out.println("Current windows are " + driver.getWindowHandles());
    }

    private ChromeOptions getChromeOptions(String downloadFilePath) {
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("download.default_directory", downloadFilePath);
        chromePrefs.put("profile.default_content_settings.cookies", 0);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-running-insecure-content");
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("no-sandbox");

        if(!browserMode.contains("normal")){
            options.addArguments("--incognito");
        }
        options.addArguments("start-maximized");
        return options;
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void finishingMethodRun(ITestResult result){

        if(ITestResult.FAILURE==result.getStatus())
        {
            String dateName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ExtentTestManager.getTest().addScreenCaptureFromBase64String(takeScreenShot(),result.getMethod().getMethodName() + "_" + dateName + "_" + "ScreenShot" );

        }
    }

    /**
     * Method to get file path from testFiles directory in resources
     *
     * @param str_FileName filename
     * @return file
     */
    public String getFilePath(String str_FileName) throws AssertionError {
        String url = this.getClass().getResource("/testFiles/" + str_FileName).getFile();
        System.out.println(url);
        String folderUrl = url.substring(0, url.lastIndexOf('/'));
        System.out.println(folderUrl);
        folderUrl = folderUrl.replace('/', File.separatorChar);
        if (folderUrl.charAt(2) == ':') {
            System.out.println(folderUrl.substring(1) + File.separatorChar + str_FileName);
            return folderUrl.substring(1) + File.separatorChar + str_FileName;
        } else {
            System.out.println(folderUrl + File.separatorChar + str_FileName);
            return folderUrl + File.separatorChar + str_FileName;
        }
    }

    /**
     *
     * @param str_Path path of test files under 'testFiles'
     * @param str_FileName file name
     * @return file location
     */
    public String getFilePath(String str_Path, String str_FileName) throws AssertionError {
        String url = this.getClass().getResource("/testFiles/" + str_Path + "/" + str_FileName).getFile();
        String folderUrl = url.substring(0, url.lastIndexOf('/'));
        folderUrl = folderUrl.replace('/', File.separatorChar);
        if (folderUrl.charAt(2) == ':') {
            return folderUrl.substring(1) + File.separatorChar + str_FileName;
        } else {
            return folderUrl + File.separatorChar + str_FileName;
        }
    }

    public boolean isFileDownloaded(String str_FileName) throws AssertionError {

        int counter = 30;
        boolean flag = false;
        while(counter>0){
            wait("[NOTE] waiting for file to be dowloaded...", 1);
            File dir = new File(getResourcePath(""));
            File[] dirContents = dir.listFiles();

            assert dirContents != null;

            for (int i = 0; i < dirContents.length; i++) {
                System.out.println(dirContents[i]);
                if (dirContents[i].getName().contains(str_FileName)) {
                    System.out.println(dirContents[i].getName().contains(str_FileName));
                    // File has been found, it can now be deleted:
                    //dirContents[i].delete();
                    flag = true;
                    counter=0;
                    break;
                }
            }
            counter--;
        }
        return flag;

    }

    public String downloadedFilePath(String str_FileName) throws AssertionError {

        int counter = 0;
        boolean flag = false;

        File dir = new File(getResourcePath(""));
        File[] dirContents = dir.listFiles();

        assert dirContents != null;
        String url = null;

        for (int i = 0; i < dirContents.length; i++) {
            System.out.println(dirContents[i]);
            if (dirContents[i].getName().contains(str_FileName)) {
                System.out.println(dirContents[i].getName().contains(str_FileName));
                // File has been found, it can now be deleted:
                //dirContents[i].delete();
                url = getResourcePath("") + dirContents[i].getName();
                counter++;
                break;
            }
        }
        if(counter==1){
            flag=true;
        }

        // There should be only one unique file. Make sure to delete download folder before running suite.
        Assert.assertTrue(flag);

       return url;

    }

    // Only uploads the file. To submit use relevant page operation.
    public void uploadFile(String str_Filename, WebElement element){
       String url = downloadedFilePath(str_Filename);
       element.isDisplayed();
       element.sendKeys(url);
       wait("wait for file to upload properly", 5);

    }

    public void deleteDownloadedFile(String str_FileName){

        boolean flag = false;

        File dir = new File(getResourcePath(""));
        File[] dirContents = dir.listFiles();

        assert dirContents != null;

        for (int i = 0; i < dirContents.length; i++) {
            System.out.println(dirContents[i]);
            if (dirContents[i].getName().contains(str_FileName)) {

                System.out.println(dirContents[i].getName().contains(str_FileName));

                flag = new File(String.valueOf(dirContents[i])).delete();

                break;
            }
        }

        // There should be only one unique file. Make sure to delete download folder before running suite.
        Assert.assertTrue(flag);

    }


    public String getResourcePath(String resourcePath) throws AssertionError {
        URL resourceUrl = this.getClass().getResource("/" + resourcePath);
        if (resourceUrl == null) {
            throw new AssertionError(resourceUrl + "not found");
        }
        String path = resourceUrl.getPath();

        if (path.charAt(2) == ':') {
            path = path.replaceAll("/", "\\\\");
            return path.substring(1);
        } else {
            return path;
        }

    }


    public String takeScreenShot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BASE64);
    }


    public void wait(String reason, int factor) {
        try {
            System.out.println(Thread.currentThread().getId() + " - WARN: necessary waiting for " + factor
                    + " seconds " + reason);
            info(" Necessary waiting for " + factor + " seconds " + reason);
            Thread.sleep(factor * 1000);
        } catch (InterruptedException e) {
            fail(" Necessary waiting for " + factor + " seconds " + reason);
            fail(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void wait(int factor) {
        try {
            System.out.println(Thread.currentThread().getId() + " - WARN: necessary waiting for " + factor
                    + " seconds " );
          //  info(" Necessary waiting for " + factor + " seconds " );
            Thread.sleep(factor * 1000);
        } catch (InterruptedException e) {
            fail(" Necessary waiting for " + factor + " seconds ");
            fail(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void wait(By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean isTextPresent(String text) {
        try {
            wait(1);
            boolean flag = (driver.getPageSource().contains(text));
            if(flag){
                info("Found " + text + " on page");
                return true;
            }
            else {
                info("Couldn't find " + text + " on page");
                return false;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            fail("While finding text threw exception" + exc.getMessage());
        }
        return false;
    }

    public boolean isWebElementPresent(By by){
        List<WebElement> list = driver.findElements(by);
        return list.size()>0;
    }

    public void scrollTillElementIsVisbile(By by){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(by);
        // Scrolling down the page till the element is found
        js.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public void scrollToTheTop(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }

    //============================================
    // Extent Report's In Test Steps log methods
    //============================================

    public void info(String message) {
       TestListener.info(message);
    }
    public void warn(String message) {
       TestListener.warn(message);
    }
    public void pass(String message) {
       TestListener.pass(message);
    }
    public void fail(String message) {
        TestListener.fail(message);
    }

}
