package object;

import java.io.IOException;

import utilities.JacksonUtils;

public class Product {
	private int id;
	private String name;
	
	public Product() {
		
	}
	
	public Product(int id) throws IOException {
		//assigning the json array to the product array class
		Product[] products = JacksonUtils.deserializeJSON("testdata\\products.json", Product[].class);
		//extracting the value of JSON
		for(Product product: products) {
			if(product.id == id) {
				this.id = id;
				this.name = product.getName();
			}
		}
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
