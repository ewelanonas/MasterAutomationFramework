package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import constants.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManagerOriginal {
						  
	public WebDriver initializeDriver(String browser) {// For Parameters of TestNG.xml
		WebDriver driver;
		/*FOR MAVEN COMMAND LINE APPLICABLE*/
		switch (BrowserType.valueOf(browser)) {
			case CHROME -> {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
			case FIREFOX -> {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			case EDGE -> {
				WebDriverManager.edgedriver().cachePath("Drivers").setup();
				driver = new EdgeDriver();
			}
			default -> throw new IllegalStateException("Invalid brower name "+browser);
		
		}
		
		/* used for manual execution in Eclipse*/
		/*
		 * String webbrowser = "firefox";
		 * if(webbrowser.equalsIgnoreCase("chrome")) {
		 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver(); }
		 * 
		 * else if(webbrowser.equalsIgnoreCase("firefox")) {
		 * WebDriverManager.firefoxdriver().setup(); driver = new FirefoxDriver(); }
		 * 
		 * else if(webbrowser.equalsIgnoreCase("edge")) {
		 * WebDriverManager.edgedriver().cachePath("Drivers").setup(); driver = new
		 * EdgeDriver(); }
		 */
		
		/* Passing through the logic */
		driver.manage().window().maximize();
		/*Implicit wait -*/ //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L)); 
		return driver;
		
	}

}
