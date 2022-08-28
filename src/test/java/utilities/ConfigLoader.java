package utilities;

import java.util.Properties;

import javax.management.RuntimeErrorException;

import constants.ConfigLoaderTypes;

public class ConfigLoader {
	private final Properties properties;
	private static ConfigLoader configLoader; // this is important - type of the loader can be class as well.
											  // will create a constructor in private - any other class can't create instance on this class
	private ConfigLoader() {
		//properties = PropertyUtils.propertyLoader("src/test/resources/configfile/config.properties");
		
		//for multiple environment support
		String env = System.getProperty("env",String.valueOf(ConfigLoaderTypes.STAGE));
		switch (ConfigLoaderTypes.valueOf(env)) {
		
		case STAGE:
			properties = PropertyUtils.propertyLoader("src/test/resources/configfile/stg_config.properties");
			break;
		case PRODUCTION:
			properties = PropertyUtils.propertyLoader("src/test/resources/configfile/prod_config.properties");
			break;
		default:
			throw new IllegalStateException("Illegal env type: "+env);
		}
	}
	
	//Reason for the method - checking that if there's null - creating new instance and ascending the ConfigLoader and 
	// returning it back, calling 2nd time, it will not be null anymore
	// can be called only ones then globally declared
	public static ConfigLoader getInstance() {
		if(configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}
	
	// Note:
	// These methods are very convenient in terms of proper declaring the script, but
	// can be also use direct getProperty method in the script (if for beginners)
	//you can also add new property method on this class, for easy maintenance
	
	public String getBaseUrl() {
		// get the property from config.properties Note: it should be same name as in the file
		String prop = properties.getProperty("baseUrl"); 
		//to check if the properties not null
		if(prop != null) return prop;
		else throw new RuntimeException("property baseUrl is not specified in the config.properties file");
	}
	
	public String getUserName() {
		// get the property from config.properties Note: it should be same name as in the file
		String prop = properties.getProperty("username"); 
		//to check if the properties not null
		if(prop != null) return prop;
		else throw new RuntimeException("property baseUrl is not specified in the config.properties file");
	}
	
	public String getPassword() {
		// get the property from config.properties Note: it should be same name as in the file
		String prop = properties.getProperty("password"); 
		//to check if the properties not null
		if(prop != null) return prop;
		else throw new RuntimeException("property baseUrl is not specified in the config.properties file");
	}

}
