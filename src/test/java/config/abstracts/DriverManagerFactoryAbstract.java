package config.abstracts;

import constants.BrowserType;

public class DriverManagerFactoryAbstract {
	
	public static DriverManagerAbstract getManager(BrowserType driverType) {
		switch(driverType) {
		case CHROME -> {
			return new ChromeDriverManagerAbstract();
			}
		case FIREFOX -> {
			return new FirefoxDriverManagerAbstract();
			}
		case EDGE -> {
			return new EdgeDriverManagerAbstract();
			}
		default -> {
			throw new IllegalStateException("Invalid driver: "+ driverType);
			}
		}
		
	}

}
