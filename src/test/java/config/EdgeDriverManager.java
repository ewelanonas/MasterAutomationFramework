package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverManager implements DriverManagerInterface{
	
	@Override
	public WebDriver createDriver() {
		WebDriverManager.edgedriver().cachePath("Drivers").setup();
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		return driver;
	}

}
