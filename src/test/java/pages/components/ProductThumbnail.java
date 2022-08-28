package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import pages.CartPage;
import pages.HomePage;

public class ProductThumbnail extends BasePage{
	
	private final By cartLink = By.cssSelector("a[title='View cart']");
	
	public ProductThumbnail(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	private By getAddToCartButton(String productName) {
		return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
	}

	
	public ProductThumbnail clickAddtoCartButton(String productName) {
		By addtoCartButton = getAddToCartButton(productName);
		waitMethodByElement(addtoCartButton);
		driver.findElement(addtoCartButton).click();
		return this;
	}
	
	public CartPage clickViewCart() {
		waitMethodByElement(cartLink);
		driver.findElement(cartLink).click();
		return new CartPage(driver);
	}

}
