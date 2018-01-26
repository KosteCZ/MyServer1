package cz.koscak.jan.db;

import java.io.IOException;

import org.junit.Test;

import cz.koscak.jan.core.Application;
import cz.koscak.jan.sql.SQLiteOperations;

public class DBCreationTest {
	
	@Test
	public void testCreateDB() throws IOException {
		
		Application.initialize();
		//SQLiteOperations.createTableTemperatureAndHumidity();
		SQLiteOperations.selectFromTableTemperatureAndHumidity();
		SQLiteOperations.insertIntoTableTemperatureAndHumidity();
		SQLiteOperations.selectFromTableTemperatureAndHumidity();
		
	}

}
