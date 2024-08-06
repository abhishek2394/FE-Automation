import framework.SeleniumTestGroups;
import framework.SeleniumTestSupport;
import framework.pages.GooglePage;


import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {SeleniumTestGroups.SANITY})
public class TestClass1 extends SeleniumTestSupport {



    private GooglePage googlePage;

    @BeforeClass
    public void page_Setup(){
        googlePage = new GooglePage(driver);

    }


    @BeforeMethod
    public void pre_Requisite(){
       driver.get(GET_URL);
       wait(3);
    }

    @Test(description = "Description Test 1")
    public void test_1(){
       String title =  googlePage.getTitle();
       Assert.assertEquals(title, driver.getTitle());

       googlePage.searchText("test");

       String selectedTab = googlePage.getSelectedTab();
       Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 2")
    public void test_2(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 3")
    public void test_3(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "Alll");
    }

    @Test(description = "Description Test 4")
    public void test_4(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 5")
    public void test_5(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 6")
    public void test_6(){
        String title =  googlePage.getTitle();
        Assert.assertEquals(title, driver.getTitle());

        googlePage.searchText("test");

        String selectedTab = googlePage.getSelectedTab();
        Assert.assertEquals(selectedTab, "All");
    }

    @Test(description = "Description Test 7")
    public void test_7(){
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
