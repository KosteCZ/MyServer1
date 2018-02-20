package cz.koscak.jan.core;

import java.io.IOException;
import java.util.Properties;

import cz.koscak.jan.utilities.ApplicationConfigurationUtils;
import cz.koscak.jan.utilities.SQLiteClient;

/**
 * Core application class.
 * 
 * NOTE: Access point for TomCat is ApplicationInitializationServlet class.
 * 
 * @author koscaj01
 *
 */
public class Application {
	
	private static String pathToDB;
	private static Properties properties;

	public static void main(String[] args) throws IOException {

		initialize();
		
	}

	public static void initialize() throws IOException {
		
		properties = ApplicationConfigurationUtils.loadProperties();
		pathToDB = ApplicationConfigurationUtils.getPropertyPathToDB(properties);
		SQLiteClient.initialize(pathToDB);
		System.out.println("BD path: " + pathToDB);
		
	}

}
