package api.actions;

import java.util.HashMap;


import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*; 

public class ApiRequest extends SpecBuilder{
	
	public static Response post(String endPoint, Headers headers, 
			HashMap<String, Object> formParams, Cookies cookies) {
		return given(getRequestSpec()).
					headers(headers).
					formParams(formParams).
					cookies(cookies).
				when().
					post(endPoint).
				then().
					spec(getResponseSpec()).
					extract().
					response();
	}
	
	public static Response get(String endPoint, Cookies cookies) {
		return given(getRequestSpec()).
					cookies(cookies).
				when().
					post(endPoint).
				then().spec(getResponseSpec()).
					extract().
					response();
	}

}
