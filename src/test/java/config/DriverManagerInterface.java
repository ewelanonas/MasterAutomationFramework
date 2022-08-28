package config;

import org.openqa.selenium.WebDriver;

public interface DriverManagerInterface {
	//implements it to the ChromeDriverManager and FireFoxDriverManager
	//the instance method create for initial of the driver (like a template) when inherited to sub class
	WebDriver createDriver();
}
