package api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import object.Login;
import utilities.ConfigLoader;

import static io.restassured.RestAssured.*; // required to write it manually for setting up of the Rest Assured
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class SignUpAPI {
	private Cookies cookies;
	
	public Cookies getCookies() {
		return cookies;
	}
	
	/*
	 * How to fetch using Groovy Gpath if not familiar with Groovy commands, kindly
	 * see some tips here: https://www.baeldung.com/rest-assured-groovy
	 */
	
	public String fetchRegisterNonceValue() {
		Response response = getAccount();
		return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value");
	}
	
	/*
	 * How to fetch using Jsoup if not familiar with Groovy commands, kindly
	 * if not familiar, yyou can search the Document Jsoup in google
	 */
	
	public String fetchRegisterNonceValueJsoup() {
		
		Response response = getAccount();
		//Note that Jsoup packages
		Document document = Jsoup.parse(response.body().prettyPrint()); // parse the html in a form of document
		// get the Element from parsed document
		//selectFirst is a method that  returns the single element
		Element element = document.selectFirst(("#woocommerce-register-nonce"));
		//getting the attribute value for the element
		//it will be converted to String.
		return element.attr("value");
	}
	
	//goal is to get the cookies from register form = id="woocommerce-register-nonce" --inspected element
	//make public to test it in DummyClass.java for testing of API (rest-assured only)
	
	private Response getAccount() { // will be using for this class // get request
		//create new object for cookies, it requires to have cookies to track the API
		Cookies cookies = new Cookies(); // it is same getCookies(), but needs to create new object in order to obtain the values
		
		Response response = given().
			baseUri(ConfigLoader.getInstance().getBaseUrl()).
			cookies(cookies).
			log().all().
		when().
			get("/account").
		then().
			log().all(). //to see details on the response and body content
			extract().
			response();
		
		if(response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to fetch the account. Http code request: "+response.getStatusCode());
		}
		return response;
	}
	
	//in case you're wondering why this is public, since it is needed to our test class
	public Response postRegister(Login user) { //will create parameters for login of username and password
		//create new object for cookies, it requires to have cookies to track the API
		Cookies cookies = new Cookies(); // it is same getCookies().
		
		//for post request, assign header (without s) for the content type header
		// not case sensitive for the title of header type
		Header header = new Header("content-type", "application/x-www-form-urlencoded");
		
		//for post, needs to have Header for proper configuration of object.
		Headers headers = new Headers(header);
		
		//for post request, it requires also a Hash Map to gather and throws body request
		HashMap<String, String> formParams = new HashMap<>();
		//add put method for the hashmap
		//this was based on the UI of dev tools under the network tab for the request upon registration
		formParams.put("username", user.getUsername());
		formParams.put("password", user.getPassword());
		formParams.put("email", user.getEmail());
		formParams.put("woocommerce-register-nonce", fetchRegisterNonceValueJsoup()); // you can also use Groovy, depends on your taste 
		formParams.put("register", "Register");
		
		Response response = given().
			baseUri(ConfigLoader.getInstance().getBaseUrl()).
			//for post request to add header
			headers(headers).
			//required for the post to have body request
//			body(formParams). // note: this will not work, unless it uses JSON Format value instead of hashmap
			formParams(formParams). //use formParams by restassured for hashmap build
			cookies(cookies).
			log().all().
		when().
		//change the get to post
			post("/account").
		then().
			log().all(). //to see details on the response and body content
			extract().
			response();
		
		if(response.getStatusCode() != 302) { // 302 depends on the request in the UI, it was also displayed on F12 dev tools network tab
			throw new RuntimeException("Failed to create an account. Http code request: "+response.getStatusCode());
		}
		
		//fetch cookies for iteration for passing cookies call before returning of value
		//will fetch not just cookies only, it will get all the details
		this.cookies = response.getDetailedCookies(); //it will explained Rest Assured course
		return response;
	}
}
