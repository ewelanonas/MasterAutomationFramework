package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckedoutPage extends BasePage{
	// UI Element
	private final By checkedouttext = By.cssSelector(".woocommerce-notice");
	
	// constructor
	public CheckedoutPage(WebDriver driver) {
		super(driver);
	}
	
	public String validateCheckedouttext() {
		waitMethodByElement(checkedouttext);
		return driver.findElement(checkedouttext).getText();
	}

}
