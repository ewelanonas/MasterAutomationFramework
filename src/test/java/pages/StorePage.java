package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
import pages.components.HeaderPage;
import pages.components.ProductThumbnail;

public class StorePage extends BasePage {
	// elements for the StorePage (another page)
	//take note that each page has its own class



	private final By searchField = By.id("woocommerce-product-search-field-0");
	private final By searchButton = By.cssSelector("button[value='Search']");
	private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
	private final By cartLink = By.cssSelector("a[title='View cart']");
	//private final By addToCartButton = By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']"); kindly see below, since the "Blue Shoes" is not dynamic
	
	//for Composition instance variable:
		private ProductThumbnail productThumbnail;
		
	//getter for the components
	public ProductThumbnail getProductThumbnail() {
			return productThumbnail;
		}
		
	//required to connect the driver from BasePage
	public StorePage(WebDriver driver) {
		super(driver);
		// add components here, since it is private - this is already a setter.
		productThumbnail = new ProductThumbnail(driver);
	}

	/*
	 * function methods here will be using Builder Pattern for chaining of function
	 * will change to private for now, since it was structural page object, will be turned to functional page object
	 */
	
	private StorePage enterTextInSearchField(String text) {
		wait.until(ExpectedConditions.presenceOfElementLocated(searchField)).sendKeys(text);
		//driver.findElement(searchField).sendKeys(text); //"Blue"
		return this;
	}
	
	
	private StorePage clickSearchButton() { 
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		//driver.findElement(searchButton).click();
		return this;
	}
	
	public String validateTitle() {
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
//			
//		} catch (Exception e) {
//			System.out.println("Element not found.");
//			driver.quit();
//			e.printStackTrace();
//		}
//		
//		return driver.findElement(title).getText();
		try {
			waitShort.until(ExpectedConditions.urlContains("?s="));
			System.out.println("Searched for something");
		} catch (Exception e) {
			System.out.println("No search was done");
		}
		return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
	}
	
	//to remove duplication, this was moved to pages.components > ProductThumbnail
	//used a composition approach
	
//	private By getAddToCartButton(String productName) {
//		return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
//	}
//
//	
//	public StorePage clickAddtoCartButton(String productName) {
//		By addtoCartButton = getAddToCartButton(productName);
//		waitMethodByElement(addtoCartButton);
//		driver.findElement(addtoCartButton).click();
//		return this;
//	}
//	
//
//	public CartPage clickViewCart() {
//		waitMethodByElement(cartLink);
//		driver.findElement(cartLink).click();
//		return new CartPage(driver);
//	}
	
	public StorePage load() {
		loadEndpoint("/store/");
		return this;
	}
	
	//sample of Functional Page Object
	public StorePage searchFunction(String text) {
		enterTextInSearchField(text)
		.clickSearchButton();
		return this;
		}
	
	//Sample of URL contains explicit wait
	
	public Boolean isLoaded() {
		return wait.until(ExpectedConditions.urlContains("/store"));
	}

}
