package testcase.atomic;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;

import api.actions.CartAPI;
import api.actions.SignUpAPI;
import base.BaseTest;
import object.Login;
import object.Product;
import pages.CheckoutPage;
import utilities.ConfigLoader;
import utilities.FakerAPIUtils;

public class LoginUsingAPITest extends BaseTest{
	
	@Test (description = "This API test case is to login during checkout")
	public void loginDuringCheckout() throws IOException {
		SignUpAPI signUpAPI = new SignUpAPI();
		//copied from Dummy Class
		String username = "eweldemo" + new FakerAPIUtils().generateRandomNumber();
		Login user = new Login().
			setUsername(username).
			setPassword("Ewel1234").
			setEmail(username +"@askomdch.com");
		signUpAPI.postRegister(user);
		System.out.println(user);
		
		//getting details
		CartAPI cartAPI = new CartAPI();
		Product product = new Product(1215);
		cartAPI.addToCart(product.getId(), 1);
		
		//driver needs to load first before getting the cookies
		CheckoutPage checkOutPage = new CheckoutPage(getDriver()).load();
		
		//inject the method from the BaseTest.java here
		injectCookiesToBrowser(cartAPI.getCookies());
		
		//need to pass the checkout page to load (public)
		//checkout page coming from the testcase using builder pattern
		checkOutPage.load().
				setLogin(user);
		
		//Validation if it were already in the Checkout Page
		Assert.assertTrue(checkOutPage.getProductName().contains(product.getName()));
	}
}
