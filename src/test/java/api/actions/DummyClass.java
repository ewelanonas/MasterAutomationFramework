package api.actions;

import object.Login;
import utilities.FakerAPIUtils;

public class DummyClass {

	public static void main(String[] args) {
		// for rest-assured method
		// new SignUpAPI().getAccount(); 
		
		//for Groovy GPath method -- getting the value from the Register for post request
//		String value = new SignUpAPI().fetchRegisterNonceValue();
//		System.out.println(value);
		
		//for Jsoup method -- getting the value from the Register for post request
//		String valueJsoup = new SignUpAPI().fetchRegisterNonceValueJsoup();
//		System.out.println(valueJsoup);
		
		//post request for putting the details to the register 
		String username = "eweldemo" + new FakerAPIUtils().generateRandomNumber();
		//create an object for login
		//do not forget to add constructor to apply objects for overloading
		Login user = new Login().
		//build a username, password, email for the object for post (Login)
			setUsername(username).
			setPassword("Ewel1234").
			setEmail(username +"@askomdch.com");
		
		//then check the details below:
		SignUpAPI signUpApi = new SignUpAPI();
		//to trigger logged in
		signUpApi.postRegister(user);
		//System.out.println(signUpApi.postRegister(user));
		System.out.println(user);
		//to fetch and create the cookies -- prints the cookies
		//it will also trigger this code: this.cookies = response.getDetailedCookies();
		//this will also used for the CartAPI with existing cookies
		System.out.println("REGISTER COOKIES: "+signUpApi.getCookies());
		
		//for CartAPI
		CartAPI cartAPI = new CartAPI(); //generate its own cookies -- path for without logged in
		//in order to trigger with Logged in, uncomment the post request
		cartAPI.addToCart(1215, 1);
		System.out.println("CART COOKIES "+cartAPI.getCookies());
	}
}
