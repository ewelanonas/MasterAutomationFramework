package testcase.atomic;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.actions.CartAPI;
import api.actions.SignUpAPI;
import base.BaseTest;
import object.BillingAddress;
import object.Login;
import object.Product;
import pages.CheckedoutPage;
import pages.CheckoutPage;
import utilities.FakerAPIUtils;
import utilities.JacksonUtils;

public class CheckOutUsingAPITest extends BaseTest{
	
	@Test (enabled = true, description = "This API test case is to check out guest only for direct bank transfer")
	public void guestCheckoutUsingDirectBankTransfer() throws IOException {
		//calling checkoutpage objects and billing address coming from TDD
		CheckoutPage checkOutPage = new CheckoutPage(getDriver()).load();
		BillingAddress billingAddress = JacksonUtils.deserializeJSON("testdata\\myBillingAddress.json", BillingAddress.class);
		
		//calling cartAPI object for adding of product to the cart
		CartAPI cartAPI = new CartAPI();
		cartAPI.addToCart(1215, 1);
		
		//injecting the cookies (without logging in) from the BaseTest.java
		//getting cookies from cartAPI
		injectCookiesToBrowser(cartAPI.getCookies());
		
		//then load the checkout page again for validation that the atomic test proceeds and passed
		checkOutPage.load().
		setBillingAddress(billingAddress).
		selectDirectBankTransfer();
		
		
		//Validation for Assertion:
		//checked-out page
		CheckedoutPage checkedoutPage = checkOutPage.clickPlaceOrder();
		Assert.assertEquals(checkedoutPage.validateCheckedouttext(),
				"Thank you. Your order has been received.");
	}
	
	@Test (enabled = true, description = "This API test case is to check out logged in only for direct bank transfer")
	public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException {
		//data for billing address
		BillingAddress billingAddress = JacksonUtils.deserializeJSON("testdata\\myBillingAddress.json", BillingAddress.class);
		
		// need to login here, copy the existing code in the LoginTestUsingAPI.java
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
		//for the cartAPI, we will be needing the cookies, pass the parameters inside of cartAPI for signupApi
		CartAPI cartAPI = new CartAPI(signUpAPI.getCookies());
		Product product = new Product(1215);
		cartAPI.addToCart(product.getId(), 1);
		
		//driver needs to load first before getting the cookies
		CheckoutPage checkOutPage = new CheckoutPage(getDriver()).load();
		
		//inject the method from the BaseTest.java here
		//instead of using cartAPI cookies, it will use the signUpAPI cookies, since it is connect from login
		injectCookiesToBrowser(signUpAPI.getCookies());
		
		//need to pass the checkout page to load (public)
		//checkout page coming from the testcase using builder pattern
		checkOutPage.load().
		setBillingAddress(billingAddress).
		selectDirectBankTransfer();
		
		//Validation for Assertion:
		//checked-out page
		CheckedoutPage checkedoutPage = checkOutPage.clickPlaceOrder();
		Assert.assertEquals(checkedoutPage.validateCheckedouttext(),
				"Thank you. Your order has been received.");
	}

}
