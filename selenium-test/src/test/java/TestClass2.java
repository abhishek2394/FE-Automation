import framework.SeleniumTestSupport;
import framework.pages.GooglePage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestClass2 extends SeleniumTestSupport {



    private GooglePage googlePage;

    @BeforeClass
    public void page_Setup(){
        googlePage = new GooglePage(driver);

    }


    @BeforeMethod
    public void pre_Requisite(){
        driver.get(GET_URL);
    }

    @Test(description = "Description Test 8")
    public void test_8(){
       String title =  googlePage.getTitle();
       Assert.assertEquals(title, driver.getTitle());

       googlePage.searchText("test");

       String selectedTab = googlePage.getSelectedTab();
       Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 9")
    public void test_9(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 10")
    public void test_10(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "Alll");
    }

    @Test(description = "Description Test 11")
    public void test_11(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 12")
    public void test_12(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 13")
    public void test_13(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 14")
    public void test_14(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "Alll");
    }




//
//    @Test(priority = 0, description = "Validate Carbon metric data given no calculation and no plan created", dataProvider = "csv", dataProviderClass = CSVParametersProvider.class)
//    @DataFileParameters(name = "Site_L3_Carbon.csv", path = "/src/test/resources/testData/")
//    public void testSampleForCSV(  ){
//
//
//    }

}
