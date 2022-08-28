package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;
import object.BillingAddress;
import object.Login;

public class CheckoutPage extends BasePage{
	// UI Elements
	private final By billingFirstName = By.id("billing_first_name");
	private final By billingLastName = By.id("billing_last_name");
	private final By billingAddress = By.id("billing_address_1");
	private final By billingCity = By.id("billing_city");
	private final By billingPostcode = By.id("billing_postcode");
	private final By billingEmail = By.id("billing_email");
	private final By placeOrder = By.id("place_order");
	private final By checkoutOverlay = By.cssSelector(".blockUI.blockOverlay");
	private final By countryDropdown = By.id("billing_country");
	private final By stateDropdown = By.id("billing_state");
	
	//application UI Element
	private final By radioDirectBankTransfer = By.id("payment_method_bacs");
	
	//Login UI Elements
	private final By loginLink = By.cssSelector(".showlogin");
	private final By userName = By.id("username");
	private final By password = By.id("password");
	private final By loginButton = By.name("login");
	
	//Validation for checkout page
	private final By productName = By.cssSelector("td[class='product-name']");
	
	
	// constructor for driver
	public CheckoutPage(WebDriver driver) {
		super(driver);
	}
	
	public CheckoutPage load() {
		loadEndpoint("/checkout/");
		return this;
	}
	
	
	// functions here
	private CheckoutPage populatebillingFirstName(String firstName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstName));
			driver.findElement(billingFirstName).clear();
			driver.findElement(billingFirstName).sendKeys(firstName);
		} catch (Exception e) {
			System.out.println("Element not found.");
			driver.quit();
			e.printStackTrace();
		}
			return this;
	}
	
	private CheckoutPage populatebillingLastName(String lastName) {
		driver.findElement(billingLastName).clear();
		driver.findElement(billingLastName).sendKeys(lastName);
		return this;
	}
	
	private CheckoutPage populatebillingAddress(String address) {
		driver.findElement(billingAddress).clear();
		driver.findElement(billingAddress).sendKeys(address);
		return this;
	}
	
	private CheckoutPage populatebillingCity(String city) {
		driver.findElement(billingCity).clear();
		driver.findElement(billingCity).sendKeys(city);
		return this;
	}
	
	private CheckoutPage populatebillingPostCode(String postcode) {
		driver.findElement(billingPostcode).clear();
		driver.findElement(billingPostcode).sendKeys(postcode);
		return this;
	}
	
	private CheckoutPage populatebillingEmail(String email) {
		driver.findElement(billingEmail).clear();
		driver.findElement(billingEmail).sendKeys(email);
		return this;
	}
	
	private CheckoutPage selectCountry(String countryName) {
		//drop down menu
		Select select = new Select(driver.findElement(countryDropdown));
		select.selectByVisibleText(countryName);
		return this;
	}
	
	private CheckoutPage selectState(String stateName) {
		//drop down menu
		Select select = new Select(driver.findElement(stateDropdown));
		select.selectByVisibleText(stateName);
		return this;
	}
	
	public CheckedoutPage clickPlaceOrder() {
		
		//use the explicit wait for the block to remove:
		//Kindly check the wait method on BasePage.java
//		List<WebElement> overlays = driver.findElements(checkoutOverlay);
//		if(overlays.size() > 0) {
//			new WebDriverWait(driver, Duration.ofSeconds(15)).until(
//					ExpectedConditions.invisibilityOfAllElements(overlays));
//		}
		
		try {
			//wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrder)); // instead of this method
			waitMethod(checkoutOverlay); // best way to use wait method
			waitMethodByElementClickable(placeOrder); // will work on my end
//			driver.findElement(placeOrder).click(); // already used a method for waitMethodByElementClickable
		} catch (Exception e) {
			System.out.println("No element found.");
		}
		
		return new CheckedoutPage(driver);
		
	}
	
	//login methods
	
	public CheckoutPage clickLoginlink() {
		wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
		//driver.findElement(loginLink).click();
		return this;
	}
	
	public CheckoutPage inputUserName(String username) {
		waitMethodByElement(userName);
		driver.findElement(userName).sendKeys(username);
		return this;
		
	}
	
	public CheckoutPage inputPassword(String Password) {
		driver.findElement(password).sendKeys(Password);
		return this;
		
	}
	
	public CheckoutPage clickLogin() {
		driver.findElement(loginButton).click();
		return this;
		
	}
	
	// Radio button function
	
	public CheckoutPage selectDirectBankTransfer() {
		WebElement e = wait.until(ExpectedConditions.elementToBeClickable(radioDirectBankTransfer));
		if(!e.isSelected()) {
			e.click();
		}
		return this;
	}
	
	//creating validation to prevent from Stale Element Reference Exception
	private CheckoutPage isLoginButtonDisplayed() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loginButton));
		return this;
	}
	//functional page objects
	
	public CheckoutPage enterBillingDetails(String firstName, String lastName, String address, 
			String city, String postcode, String email, String country, String state) {
		populatebillingFirstName(firstName).
		populatebillingLastName(lastName).
		populatebillingAddress(address).
		populatebillingCity(city).
		populatebillingPostCode(postcode).
		populatebillingEmail(email);
		selectCountry(country);
		selectState(state);
		
		return this;
	}
	
	public CheckoutPage login(String username, String password) {
		clickLoginlink().
		inputUserName(username).
		inputPassword(password).
		clickLogin();
		return this;
		
	}
	
//	POJO method for encapsulating fields
	
	public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
		return populatebillingFirstName(billingAddress.getFirstName()).
			   populatebillingLastName(billingAddress.getLastName()).
			   populatebillingAddress(billingAddress.getAddressLineOne()).
			   populatebillingCity(billingAddress.getCity()).
			   populatebillingPostCode(billingAddress.getPostalCode()).
			   populatebillingEmail(billingAddress.getEmail()).
			   selectCountry(billingAddress.getCountry()).
			   selectState(billingAddress.getState());
			   
	}
	
	public CheckoutPage setLogin(Login login) {
		return  clickLoginlink().
				inputUserName(login.getUsername()).
				inputPassword(login.getPassword()).
				clickLogin().
				//added to prevent Stale Element Reference Exception
				isLoginButtonDisplayed();
	}
	
	public CheckoutPage loginTraining(Login login) {
		return inputUserName(login.getUsername()).
				inputPassword(login.getPassword());
	}

// For UI validation for API integration
	public String getProductName() {
		// to avoid Stale Element Reference Exception - to confirm the DOM is already synch
		//wait.until(ExpectedConditions.stalenessOf(null));
		//or try catch block to handle the exception
		return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
		
	}
}
