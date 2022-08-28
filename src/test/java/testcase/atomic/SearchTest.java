package testcase.atomic;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.StorePage;

public class SearchTest extends BaseTest{

	@Test(description = "This test case is to search with partial match from the product")
	public void searchWithPartialMatch() {
		String searchFor = "Blue";
		StorePage storePage = new StorePage(getDriver()).
				load().
				searchFunction(searchFor);
		Assert.assertEquals(storePage.validateTitle(), 
				"Search results: “" + searchFor + "”");
	}
}
