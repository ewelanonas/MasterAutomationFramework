package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
import pages.StorePage;

public class HeaderPage extends BasePage{
	private final By storeMenuLink = By.cssSelector("#menu-item-1227 > a");
	
	public HeaderPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public StorePage clickStoreLink() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(storeMenuLink)).click();
		return new StorePage(driver);
		
	}
	

}
