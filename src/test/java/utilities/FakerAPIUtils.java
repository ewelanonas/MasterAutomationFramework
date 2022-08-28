package utilities;

import com.github.javafaker.Faker;

public class FakerAPIUtils {
	
	//creating Faker API - can generate random data
	public Long generateRandomNumber() {
		Faker faker = new Faker();
		 return faker.number().randomNumber(10, true);
	}

}
