package cz.koscak.jan.utilities;

import java.io.IOException;
import java.util.Properties;

public class ApplicationConfigurationUtils {
	
	private static final String CONFIGURATION_FILE_PATH = "MyServer-configuration.properties";
	
	private static final String PROPERTY_KEY_DB_PATH = "db_path";
	
	public static Properties loadProperties() throws IOException {
		
		return FileIO.readPropertiesFromDisk(CONFIGURATION_FILE_PATH);	
		
	}
	
	public static String getPropertyPathToDB(Properties properties) throws IOException {
		
		return getProperty(properties, PROPERTY_KEY_DB_PATH);
		
	}
	
	public static String getProperty(Properties properties, String propertyName) throws IOException {
		
		return properties.getProperty(propertyName);		
		
	}

}
