package testcase.atomic;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import pages.HomePage;
import pages.StorePage;

@Epic("Navigation Test")
@Feature("Navigation Testing Feature")
public class NavigationTest extends BaseTest{
	
	@Story("Navigating to the Store Page")
	//allure issue tracker - needs to create properties file first before integrating to the script
	@Link("https://example.org")
	@Link(name = "allure", type = "mylink")
	@TmsLink("12345")
	@Issue("12345")
	
	//allure description report
	@Description ("This is Navigation test with issue on quit driver")
	
	@Test(description = "This API test case is for Navigation from home to store using in the main menu")
	public void NavigateFromHomeToStoreUsingMainMenu(){
		 StorePage storePage = new HomePage(getDriver()).
			loadMain().
			//with composition
			getMyHeader().
			clickStoreLink();
			
			//old method
//			clickStoreLink();
			Assert.assertEquals(storePage.validateTitle(), 
					"Store");
	}

}
