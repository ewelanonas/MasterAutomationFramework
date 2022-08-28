package config.abstracts;

import org.openqa.selenium.WebDriver;

public abstract class DriverManagerAbstract {
	protected WebDriver driver;
	
	protected abstract void startDriver();
	
	public void quitDriverAbstract() {
		
		/*Optional, but best practice to do*/
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
	
	public WebDriver getDriverAbstract() {
		if(driver == null) {
			startDriver();
		}
		return driver;
	}

}
