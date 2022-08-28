package utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Cookie;

import io.restassured.http.Cookies;

public class CookieUtils {

	//since we are needing Selenium cookies, import Cookie selenium package.
	//Note that we dont have any "Cookies" for Selenium, only for Rest-assured
	//put it on the List in order to retrieve cookies.
	//inside the param is Cookies coming from restassured.
	public List<Cookie> convertRestAssuredCookiesToSeleniumCookies(Cookies cookies){
		//calling the list of restassured cookie then convert as a list of cookies being thrown in the method.
		List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>();
		restAssuredCookies = cookies.asList();
		//now calling the selenium object list
		List<Cookie> seleniumCookies = new ArrayList<>();
		
		//requiring to create new selenium cookie object
		for(io.restassured.http.Cookie cookie: restAssuredCookies) {
			//new object for parameters for Cookie of Selenium
			seleniumCookies.add(new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(),
					cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured(), cookie.isHttpOnly(),
					cookie.getSameSite()));
		}
		
		return seleniumCookies;
		
		
	}
}
