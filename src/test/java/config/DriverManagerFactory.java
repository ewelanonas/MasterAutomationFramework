package config;

import constants.BrowserType;

public class DriverManagerFactory {
	
	public static DriverManagerInterface getManager(BrowserType driverType) {
		switch(driverType) {
		case CHROME -> {
			return new ChromeDriverManager();
			}
		case FIREFOX -> {
			return new FirefoxDriverManager();
			}
		case EDGE -> {
			return new EdgeDriverManager();
			}
		default -> {
			throw new IllegalStateException("Invalid driver: "+ driverType);
			}
		}
		
	}

}
