package base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigLoader;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected WebDriverWait waitShort;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // instead of using this method
		waitShort = new WebDriverWait(driver, Duration.ofSeconds(1));
	}
	
	public void loadEndpoint(String endpoint) {
		//old method
		//driver.get("https://askomdch.com" + endpoint);
		
		//for Singleton design to remove hardcoded
		//class.constructor(changed to static).method
		driver.get(ConfigLoader.getInstance().getBaseUrl() + endpoint);
	}
	
	public void waitMethod(By element) { //best method for explicit wait
		List<WebElement> overlays = driver.findElements(element);
		System.out.println("Overlay size: "+overlays.size());
		if(overlays.size() > 0) {
			/*new WebDriverWait(driver, Duration.ofSeconds(60))*/wait.until(
					ExpectedConditions.invisibilityOfAllElements(overlays));
			System.out.println("Overlays are successfully dissapeared");
		} else {
			System.out.println("Element of Overlay not found.");
		}
	}
	
	public void waitMethodByElement(By element) { //best method for explicit wait
		try {
			System.out.println("Initialize waiting of element");
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			System.out.println("Element found");
		} catch(Exception e) {
			System.out.println("Element is not found.");
		}
	}
	public void waitMethodByElementClickable(By element) { //best method for explicit wait
		try {
			System.out.println("Initialize waiting of element");
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			System.out.println("Element clicked");
		} catch(Exception e) {
			System.out.println("Element is not found.");
		}
	}
	
}
