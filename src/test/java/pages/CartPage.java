package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class CartPage extends BasePage{
	//UI elements
//	private final By productName = By.cssSelector("td[class='product-name'] a");
//	private final By checkoutButton = By.cssSelector(".checkout-button");
	private final By cartTitleHeading = By.cssSelector(".has-text-align-center");
	
	//using @FindBy
	@FindBy(css = "td[class='product-name'] a") private WebElement productName;
	
	@FindBy(how = How.CSS, using = ".checkout-button") @CacheLookup private WebElement checkoutButton; 
	//^ for proper format declaring as CSS, to avoid mistakes on misspelled format Ex. CCS
	//@FindBy(how = How.CSS, using = ".checkout-button") private WebElement checkoutButton; -- without CacheLookUp


	//required to build constructor
	public CartPage(WebDriver driver) {
		super(driver);
		
		//in order to reflect the FindBy, need to instantiated in the constructor - PageFactory
		//can be declared in the parent class, but for training, declared in the child for now
		PageFactory.initElements(driver, this);
	}
	
	public String getProductName() {
		// return driver.findElement(productName).getText(); -- using By
		// return productName.getText(); -- using FindBy
		return wait.until(ExpectedConditions.visibilityOf(productName)).getText(); // -- using FindBy
	}
	

	public CheckoutPage clickCheckoutPage() { //when navigating to next page, will have separate class
		//driver.findElement(checkoutButton).click(); -- using By
		//checkoutButton.click(); // -- using FindBy
		wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
		return new CheckoutPage(driver);
	}
	
	//functional page object here:

	
	//Sample of text of the element vs. text explicit wait
	public Boolean isLoaded() {
		return wait.until(ExpectedConditions.textToBe(cartTitleHeading, "Cart"));
	}
}
