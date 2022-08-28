package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
import pages.components.HeaderPage;
import pages.components.ProductThumbnail;

public class HomePage extends BasePage{
	
	// declaring of elements for HomePage 
	//take note that each page has its own class
	//transfered to pages.components > HeaderPage.java
	// private final By storeMenuLink = By.cssSelector("#menu-item-1227 > a");
	
	//duplicated code 
	//transfered to pages.components > ProductThumbnail.java
	// private final By cartLink = By.cssSelector("a[title='View cart']");
	
	//for Composition instance variable:
	private HeaderPage myHeader;
	private ProductThumbnail productThumbnail;
 
	//webdriver declaration
	// old method
//	public HomePage(WebDriver driver) {
//		super(driver);
//		// TODO Auto-generated constructor stub
//	}
	
	// For composition, the compenents needs to be built in the constructor
	
	public HeaderPage getMyHeader() {
		return myHeader;
	}

	public ProductThumbnail getProductThumbnail() {
		return productThumbnail;
	}
	
	public HomePage(WebDriver driver) {
		super(driver);
		// add components here, since it is private - this is already a setter. assigned the private variables to the new object
		myHeader = new HeaderPage(driver);
		productThumbnail = new ProductThumbnail(driver);
	}
	
	public HomePage loadMain() {
		
		loadEndpoint("/");
		//explicit wait using URL load
		wait.until(ExpectedConditions.titleContains("Ask"));
		return this;
	}


	//method for function of the elements
	// this was moved to pages.components > HeaderPage
	//since this UI is header, it will placed to components
	
	/*
	 * public StorePage clickStoreLink() {
	 * driver.findElement(storeMenuLink).click(); return new StorePage(driver);
	 * 
	 * }
	 */
	
	
	//duplicated codes from StorePage - will be using Composition topic
	//to remove duplication, this was moved to pages.components > ProductThumbnail
	//used a composition approach
	
	/*
	 * private By getAddToCartButton(String productName) { return
	 * By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']"); }
	 * 
	 * 
	 * public HomePage clickAddtoCartButton(String productName) { By addtoCartButton
	 * = getAddToCartButton(productName); waitMethodByElement(addtoCartButton);
	 * driver.findElement(addtoCartButton).click(); return this; }
	 * 
	 * public CartPage clickViewCart() { waitMethodByElement(cartLink);
	 * driver.findElement(cartLink).click(); return new CartPage(driver); }
	 */
}

