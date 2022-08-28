package testcase;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;

public class MyFirstTestCaseMessy extends BaseTest{
	public static WebDriver driver;
	static String webbrowser = "chrome";
	public static WebDriverWait wait;
	
	@Test (enabled = false)
	public void guestCheckoutUsingDirectBankTransfer() {
		
		driver.get("https://askomdch.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		
		//main page
		driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
		driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys("Blue");
		driver.findElement(By.cssSelector("button[value='Search']")).click();
		Assert.assertEquals(
				driver.findElement(By.cssSelector(".woocommerce-products-header__title.page-title")).getText(), 
				"Search results: “Blue”");
		//search page
		driver.findElement(By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']")).click();
		driver.findElement(By.cssSelector("a[title='View cart']")).click();
		Assert.assertEquals(
				driver.findElement(By.cssSelector("td[class='product-name'] a")).getText(),
				"Blue Shoes");
		driver.findElement(By.cssSelector(".checkout-button")).click();
		
		//checkout page
		driver.findElement(By.id("billing_first_name")).sendKeys("demo");
		driver.findElement(By.id("billing_last_name")).sendKeys("user");
		driver.findElement(By.id("billing_address_1")).sendKeys("San Francisco");
		driver.findElement(By.id("billing_city")).sendKeys("San Francisco");
		driver.findElement(By.id("billing_postcode")).sendKeys("94188");
		driver.findElement(By.id("billing_email")).sendKeys("askomdch@gmail.com");

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("place_order")));
			driver.findElement(By.id("place_order")).click();
			
			Assert.assertEquals(
					driver.findElement(By.cssSelector(".woocommerce-notice")).getText(), 
					"Thank you. Your order has been received.");
			
		} catch (Exception e) {
			System.out.println("No element found.");
		}

	}	

	@Test (enabled = false)
	public void loginAndCheckoutUsingDirectBankTransfer() {
		
		driver.get("https://askomdch.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		
		//main page
		driver.findElement(By.cssSelector("#menu-item-1227 > a")).click();
		driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys("Blue");
		driver.findElement(By.cssSelector("button[value='Search']")).click();
		Assert.assertEquals(
				driver.findElement(By.cssSelector(".woocommerce-products-header__title.page-title")).getText(), 
				"Search results: “Blue”");
		//search page
		driver.findElement(By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']")).click();
		driver.findElement(By.cssSelector("a[title='View cart']")).click();
		Assert.assertEquals(
				driver.findElement(By.cssSelector("td[class='product-name'] a")).getText(),
				"Blue Shoes");
		driver.findElement(By.cssSelector(".checkout-button")).click();
		
		//click login
		driver.findElement(By.cssSelector(".showlogin")).click();
		
		//enables login
		driver.findElement(By.id("username")).sendKeys("eweldemo");
		driver.findElement(By.id("password")).sendKeys("Ewel1234");
		driver.findElement(By.name("login")).click();
		
		//checkout page
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing_first_name")));
			
			driver.findElement(By.id("billing_first_name")).sendKeys("demo");
			driver.findElement(By.id("billing_last_name")).sendKeys("user");
			driver.findElement(By.id("billing_address_1")).sendKeys("San Francisco");
			driver.findElement(By.id("billing_city")).sendKeys("San Francisco");
			driver.findElement(By.id("billing_postcode")).sendKeys("94188");
			driver.findElement(By.id("billing_email")).clear();
			driver.findElement(By.id("billing_email")).sendKeys("askomdch@gmail.com");
			
		} catch (Exception e) {
			System.out.println("element not found");
		}
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("place_order")));
			driver.findElement(By.id("place_order")).click();
			
			Assert.assertEquals(
					driver.findElement(By.cssSelector(".woocommerce-notice")).getText(), 
					"Thank you. Your order has been received.");
			
		} catch (Exception e) {
			System.out.println("No element found.");
			
		}
	}	
}
