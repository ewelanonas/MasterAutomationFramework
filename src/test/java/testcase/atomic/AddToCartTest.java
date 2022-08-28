package testcase.atomic;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import dataprovider.DataProviders;
import object.Product;
import pages.CartPage;
import pages.HomePage;
import pages.StorePage;
import utilities.JacksonUtils;

public class AddToCartTest extends BaseTest {

	@Test (description = "This test case is to add to cart from store page")
	public void addToCartFromStorePage() throws IOException {
		Product product = new Product(1215);
		CartPage cartPage = new StorePage(getDriver()).
				load().
				//composition method
				getProductThumbnail().
				clickAddtoCartButton(product.getName())
				.clickViewCart();
		
				//old method 
				//clickAddtoCartButton(product.getName()).
				//clickViewCart();
		
		Assert.assertEquals(cartPage.getProductName(),
				product.getName());	
	}
	
	@Test (dataProvider = "getFeaturedProducts", dataProviderClass = DataProviders.class,
			description = "This test case is to add to cart from featured products")
	public void addToCartFeaturedProducts(Product product) {
		CartPage cartPage = new HomePage(getDriver()).loadMain().
				//with components
				getProductThumbnail().
				clickAddtoCartButton(product.getName()).
				clickViewCart();
				
				//old method
//				clickAddtoCartButton(product.getName()).
//				clickViewCart();
		
		Assert.assertEquals(cartPage.getProductName(),
				product.getName());		
	}
	
	//old method. Please see @Test (dataProvider)
	/*
	 * public void addToCartFeaturedProducts() { CartPage cartPage = new
	 * HomePage(getDriver()).loadMain(). clickAddtoCartButton("Blue Shoes").
	 * clickViewCart();
	 * 
	 * Assert.assertEquals(cartPage.getProductName(), "Blue Shoes"); }
	 */
	
}
