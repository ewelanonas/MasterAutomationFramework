package utilities;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

import object.BillingAddress;

public class JacksonUtils {

	//not generic
//	public static BillingAddress deserializeJSON(InputStream inputStream, BillingAddress billingAddress) throws IOException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.readValue(inputStream, billingAddress.getClass());
//	}
	
	//generic
	public static <T> T deserializeJSON(String filename, Class<T> T) throws IOException {
		InputStream inputStream = JacksonUtils.class.getClassLoader().getResourceAsStream(filename); //copied from the tc class
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(inputStream, T);
	}
}
