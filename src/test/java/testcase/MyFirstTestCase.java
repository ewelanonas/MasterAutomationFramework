package testcase;

import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;
import object.BillingAddress;
import object.Login;
import object.Product;
import pages.CartPage;
import pages.CheckedoutPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.StorePage;
import utilities.ConfigLoader;
import utilities.JacksonUtils;

public class MyFirstTestCase extends BaseTest{
	
	
	@Test (enabled = true, priority = 1, description = "This test case is used to check out for Guest only")
	public void guestCheckoutUsingDirectBankTransfer() throws IOException {
		String searchFor = "Blue";
		//POJO new constructor (without building pattern)
//		BillingAddress billingAddress = new BillingAddress();
//		billingAddress.setFirstName("eweldemo");
//		billingAddress.setLastName("user");
//		billingAddress.setAddressLineOne("San Francisco");
//		billingAddress.setCity("San Francisco");
//		billingAddress.setPostalCode("91488");
//		billingAddress.setEmail("askomdch@gmail.com");
		
		//POJO new constructor with building pattern - it should be returning Class, not void)
//		BillingAddress billingAddress = new BillingAddress().
//				setFirstName("eweldemo").
//				setLastName("user").
//				setAddressLineOne("San Francisco").
//				setCity("San Francisco").
//				setPostalCode("91488").
//				setEmail("askomdch@gmail.com");

		//POJO new constructor with building pattern - it should be returning Class, not void)
		//shorter code, but doesn't have readability compared with building pattern  
//		BillingAddress billingAddress = new BillingAddress("eweldemo", "user", "San Francisco",
//				"San Francisco", "91488", "askomdch@gmail.com");
		
		//using JSON parameterized
//		BillingAddress billingAddress = new BillingAddress();
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testdata\\myBillingAddress.json"); //get the path (relative)
//		billingAddress = JacksonUtils.deserializeJSON(inputStream, billingAddress); // need to add exception IOException


		//using JSON deserialized and generic format
		BillingAddress billingAddress = JacksonUtils.deserializeJSON("testdata\\myBillingAddress.json", BillingAddress.class); // parsing the file and class object
		
		//Product for  removing the hard code of "Blue Shoes" (JSON Array)
		Product product = new Product(1215);
		
		//Home Page
		//HomePage homePage = new HomePage(driver).loadMain();
		
		//for parallel execution
		HomePage homePage = new HomePage(getDriver()).loadMain();	
		
		//It can be below:
//		 StorePage storePage = new HomePage(driver).
//			loadMain().
//			clickStoreLink().
//			searchFunction("Blue");
		
		//Store Page
		StorePage storePage = homePage.
		//using composition
			getMyHeader().
			clickStoreLink();
		//old method
//		clickStoreLink(); // since it was a fluent interface, but if not, it should be instantiated first
		/*
		 * input under the storePage. Used a pattern builder (structural function page),
		 * kindly see HomePage.java
		 * Example:
		 */						
	//		storePage
	//			.enterTextInSearchField("Blue")
	//			.clickSearchButton(); 
		
		//Functional Page Object
		storePage.searchFunction(searchFor);
		Assert.assertEquals(storePage.validateTitle(), 
				"Search results: “" + searchFor + "”");
		//old method
//		storePage.clickAddtoCartButton("Blue Shoes");
		
		//with json array
		storePage.
		getProductThumbnail().
		clickAddtoCartButton(product.getName());
//		clickAddtoCartButton(product.getName());
		CartPage cartPage = storePage.
		getProductThumbnail().
		clickViewCart();
//		clickViewCart(); // since it was a fluent interface, but if not, it should be instantiated first
		
		//old method
//		Assert.assertEquals(cartPage.getProductName(),
//				"Blue Shoes");
		
		//new method (json array)
		Assert.assertEquals(cartPage.getProductName(),
				product.getName());
		
		
		//checkout page
//		CheckoutPage checkoutPage = cartPage.clickCheckoutPage(); // since it was a fluent interface, but if not, it should be instantiated first
// Structural Page Object
//		checkoutPage.
//			populatebillingFirstName("demo").
//			populatebillingLastName("user").
//			populatebillingAddress("San Francisco").
//			populatebillingCity("San Francisco").
//			populatebillingPostCode("94188").
//			populatebillingEmail("askomdch@gmail.com");
		
// Functional Page Object
//		checkoutPage.enterBillingDetails("demo", "user", "San Francisco", 
//				"San Francisco", "94188", "askomdch@gmail.com");

		// to associate POJO (Encapsulated) for data hiding
		CheckoutPage checkoutPage = cartPage.
				clickCheckoutPage().
				setBillingAddress(billingAddress).
				selectDirectBankTransfer();
		
		//checked-out page
		CheckedoutPage checkedoutPage = checkoutPage.clickPlaceOrder();
		Assert.assertEquals(checkedoutPage.validateCheckedouttext(),
				"Thank you. Your order has been received.");
	}	

	@Test (enabled = false, priority = 2, description = "This testcase is used for login before checking out")
	public void loginAndCheckoutUsingDirectBankTransfer() throws IOException {
		
		/*	Optimized Structural Page Object
		 * 1. Navigated to Home Page Navigate to Store Link 
		 * 2. Navigate to main page and
		 * 3. search the "Blue"
		 */
		
		// StorePage storePage = new HomePage(driver).loadMain(). //get urlclickStoreLink().searchFunction("Blue");
		 
		//for parallel execution:
		StorePage storePage = new HomePage(getDriver()).
				loadMain(). //get url
				getMyHeader().
				clickStoreLink().
				//clickStoreLink().
				searchFunction("Blue");
		
		//Validate the search result
		Assert.assertEquals(storePage.validateTitle(), 
				"Search results: “Blue”");
		//Click Add to Cart
		storePage.getProductThumbnail().
		clickAddtoCartButton("Blue Shoes");
//		clickAddtoCartButton("Blue Shoes");
		
		//View Cart
		CartPage cartPage = storePage.
				getProductThumbnail().
				clickViewCart();
//				clickViewCart();
		
		Assert.assertEquals(cartPage.getProductName(),
				"Blue Shoes");
		
		//checkout page
		CheckoutPage checkoutPage = cartPage.clickCheckoutPage();
		
		//login page //old method
		//checkoutPage.login("eweldemo", "Ewel1234");
		
		//using JSON deserialized and generic format
//		Login login = JacksonUtils.deserializeJSON("testdata\\login.json", Login.class); // parsing the file and class object
//		checkoutPage.setLogin(login);
		
		//using Singleton design to remove hard code and proper storing in the config file
		//class.constructor(changed to static).method
		checkoutPage.login(ConfigLoader.getInstance().getUserName(), ConfigLoader.getInstance().getPassword());
				
		checkoutPage.enterBillingDetails("demo", "user", "San Francisco", 
				"San Francisco", "94188", "askomdch@gmail.com", "United States (US)", "California").
		selectDirectBankTransfer();
		
		//checked-out page
		CheckedoutPage checkedoutPage = checkoutPage.clickPlaceOrder();
		Assert.assertEquals(checkedoutPage.validateCheckedouttext(),
				"Thank you. Your order has been received.");
	}	
}
