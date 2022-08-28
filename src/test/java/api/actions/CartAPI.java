package api.actions;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utilities.ConfigLoader;

public class CartAPI {
	private Cookies cookies;
	
	//constructor default -- for the path of not logged in
	public CartAPI() {
		
	}
	//constructor with parameter -- for the path of logged in (already had a cookie from login)
	public CartAPI(Cookies cookies) {
		this.cookies = cookies;
	}
	
	//getter
	public Cookies getCookies() {
		return cookies;
	}
	
	//post call
	public Response addToCart(int productId, int quantity) {
		// we will not be using cookies, since this is different from logged in/ registered data
		//need to send empty cookie
//		Cookies cookies = new Cookies()
				
		//for post request, assign header (without s) for the content type header
		// not case sensitive for the title of header type
		Header header = new Header("content-type", "application/x-www-form-urlencoded");
				
				//for post, needs to have Header for proper configuration of object.
				Headers headers = new Headers(header);
				
				//for post request, it requires also a Hash Map to gather and throws body request
				HashMap<String, Object> formParams = new HashMap<>();
				//add put method for the hashmap
				//this was based on the UI of dev tools under the network tab
				//get the Form Data
				formParams.put("product_sku", "");
				formParams.put("product_id", productId);
				formParams.put("quantity", quantity);
				
				//creating a condition where if the cookies were null, it will have new object to create
				//this is for the path of default constructor
				if(cookies == null) {
					cookies = new Cookies();
				}

				
				Response response = given().
					baseUri(ConfigLoader.getInstance().getBaseUrl()).
					//for post request to add header
					headers(headers).
					//required for the post to have body request
//					body(formParams). // note: this will not work, unless it uses JSON Format value instead of hashmap
					formParams(formParams). //use formParams by restassured for hashmap build
					cookies(cookies).
					log().all().
				when().
				//change the get to post -- get the end point where we add product to the cart
					post("/?wc-ajax=add_to_cart").
				then().
					log().all(). //to see details on the response and body content
					extract().
					response();
				
				if(response.getStatusCode() != 200) { // depends on the request in the UI, it was also displayed on F12 dev tools network tab
					throw new RuntimeException("Failed to create an account "+productId+" to the cart, "
							+ "HTTP Status Code: "+response.getStatusCode());
				}
				
				//fetch cookies for iteration for passing cookies call before returning of value
				//will fetch not just cookies only, it will get all the details
				this.cookies = response.getDetailedCookies(); //it will explained Rest Assured course
				return response;
			}
}
