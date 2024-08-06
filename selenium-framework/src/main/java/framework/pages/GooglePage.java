package framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePage extends Page {

    private static final String SEARCH_TEXT = "//textarea[@class='gLFyf']";
    private static final String SEARCH_BTN = "//input[@class='gNO89b']";
    private static final String SELECTED_TAB = "//div[@selected and @class='YmvwI']";

    @FindBy(xpath = SEARCH_TEXT)
    private WebElement searchText;

    @FindBy(xpath = SEARCH_BTN)
    private WebElement clickSearch;

    @FindBy(xpath = SELECTED_TAB)
    private WebElement selectedTab;

    public GooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getTitle(){
        return driver.getTitle();

    }
    public void searchText(String text)
    {

       searchText.isDisplayed();
       searchText.sendKeys(text);

       clickSearch.isDisplayed();
       clickSearch.click();
    }

    public String getSelectedTab(){

        selectedTab.isDisplayed();
        return selectedTab.getText();

    }


}
