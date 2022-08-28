package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import config.DriverManagerFactory;
import config.DriverManagerOriginal;
import config.abstracts.DriverManagerAbstract;
import config.abstracts.DriverManagerFactoryAbstract;
import constants.BrowserType;
import io.restassured.http.Cookies;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utilities.CookieUtils;

public class BaseTest {
	
	//protected WebDriver driver;
	protected ThreadLocal <WebDriver> driver = new ThreadLocal<>(); // for parallel execution. instantiation
	// for the Abstraction calling
	protected ThreadLocal <DriverManagerAbstract> driverManager = new ThreadLocal<>();
		
	//step 1 for ThreadLocal private void method
	private void setDriver(WebDriver driver) {
		this.driver.set(driver); //set the current thread's copy of this thread-local variable to the specified value
	}
	
	//step 2 for ThreadLocal public get method for encapsulation
	protected WebDriver getDriver() { //Returns the value in the current thread's copy of this thread-local variable.
		return this.driver.get();
		}
	
	
	

	//step 1 for ThreadLocal private void method
	private void setDriverManager(DriverManagerAbstract driverManager) {
		this.driverManager.set(driverManager); //set the current thread's copy of this thread-local variable to the specified value
	}
	//step 2 for ThreadLocal public get method for encapsulation
	protected DriverManagerAbstract getDriverManager() { //Returns the value in the current thread's copy of this thread-local variable.
		return this.driverManager.get();
		}
	
	
	@Parameters("browser")
	@BeforeMethod // TestNG method will run first before the "BeforeTest" or "Test"
	public synchronized void startDriver(@Optional String browser) { // for Parameters in TestNG.xml
		
		if(browser == null) browser = "CHROME";
		else browser = System.getProperty("browser", browser); // for Maven Command Line
		  										/*for default browser when manually executed*/
		/*
		 * this driver represent from config.DriverManager, it initializes the
		 * driver manager. no inheritance needed, since it was declared as public (generic)
		 * 
		 */
		//driver = new DriverManager().initializeDriver(browser);
		
		//for parallel execution of ThreadLocal
		//for the old method (TheadLocal)
//		setDriver(new DriverManagerOriginal().initializeDriver(browser));
		
		//for implementation of Factory Design Pattern with ThreadLocal
		// format: DriverManagerFactory class > getManager method > get the value from the constant. createDriver method from the class being called.
//		setDriver(DriverManagerFactory.getManager(BrowserType.valueOf(browser)).createDriver());
		
		
		//Factory Design Pattern in Abstraction
		//calling for getting the driver
		setDriverManager(DriverManagerFactoryAbstract.getManager(BrowserType.valueOf(browser)));
		//to execute the driver
		setDriver(getDriverManager().getDriverAbstract());
		
		System.out.println("Current Thread: "+ Thread.currentThread().getId()+", " + "Driver: "+getDriver());
	}
	
	
	@Parameters("browser")
	@AfterMethod // TestNG method will run after the "AfterTest" or "Test"
	public synchronized void quitDriver(@Optional String browser, ITestResult result) throws IOException {
		//driver.quit();
		
		//for parallel execution
		System.out.println("Current Thread: "+ Thread.currentThread().getId()+", " + "Driver: "+getDriver());
		
		//for test failure screenshot
		if(result.getStatus() == ITestResult.FAILURE) {
			//create file - format to create the result object
			File destFile = new File("screenshot" + File.separator + browser + File.separator + 
					result.getTestClass().getRealClass().getSimpleName() + "_" +
					result.getMethod().getMethodName() + ".png");
			
			//call the function of screenshot method below: then add exception once called
//			takeScreenshot(destFile);
			
			//call the function of screenshot using AShot
			takeScreenshotUsingAShot(destFile);
		}
			
		//old method (ThreadLocal)
//		getDriver().quit();
		
		//Factory Design Pattern in Abstraction
		getDriverManager().getDriverAbstract().quit();
	}
	
	//calling of Selenium cookies to Browser
	public void injectCookiesToBrowser (Cookies cookies) {
		List<Cookie> seleniumCookie = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
		// all cookies will be injected to the driver
		for(Cookie cookie: seleniumCookie) {
			//need to get driver
			getDriver().manage().addCookie(cookie);
		}
	}
	
	//creation of screenshot function
	//private, since will be using it within the class
	private void takeScreenshot(File destFile) throws IOException {
		//create TakesScreenshot from selenium then cast it from ThreadLocal getter
		TakesScreenshot takeScreenshot = (TakesScreenshot) getDriver();
		//File Object
		File srcFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		//add FileUtils
		FileUtils.copyFile(srcFile, destFile);
	}
	
	//Take Screenshot using AShot (implemented in Maven - GitHub)
	private void takeScreenshotUsingAShot(File destFile) {
		Screenshot screenshot = new AShot()
		  .shootingStrategy(ShootingStrategies.viewportPasting(100))
		  .takeScreenshot(getDriver());
		
		//create try catch block for the exception 
		try {
			ImageIO.write(screenshot.getImage(), "PNG", destFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
