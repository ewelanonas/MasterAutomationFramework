package dataprovider;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import object.Product;
import utilities.JacksonUtils;

public class DataProviders {
	
	@DataProvider (name="getFeaturedProducts", parallel = true)
	//DataProvider in JSON, since it was based on the Product.class under object
	public Product[] getFeaturedProducts() throws IOException {
		//copied from Product.java under object package
		return JacksonUtils.deserializeJSON("testdata\\products.json", Product[].class);
	}

}
