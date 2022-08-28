package config.abstracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverManagerAbstract extends DriverManagerAbstract{
	
	@Override
	protected void startDriver() {
		WebDriverManager.edgedriver().cachePath("Drivers").setup();
		driver = new EdgeDriver();
		driver.manage().window().maximize();
	}

}
