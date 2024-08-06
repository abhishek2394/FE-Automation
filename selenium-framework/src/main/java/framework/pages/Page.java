package framework.pages;

import framework.SeleniumTestSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Page extends SeleniumTestSupport {

    public Page(WebDriver driver) {
     //   super(driver);
        this.driver = driver;
  //      actions = new Actions(driver);
    }

}