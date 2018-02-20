package cz.koscak.jan.db;

import java.io.IOException;

import org.junit.Test;

import cz.koscak.jan.core.Application;
import cz.koscak.jan.utilities.SQLiteClient;

public class SQLClientTest {

	@Test
	public void testSelects() throws IOException {
		
		Application.initialize();
		
		String sql = "SELECT id, date, temperature, humidity FROM temperature_and_humidity";
		
		String[] firstRow = SQLiteClient.selectFirstRow(sql);
		for (String string : firstRow) {
			System.out.println("First row: " + string);
		}
		
		String resultString = SQLiteClient.selectString(sql);
		System.out.println("String: " + resultString);
		
		Integer resultInteger = SQLiteClient.selectInteger(sql);
		System.out.println("Integer: " + resultInteger);
		
		String sql2 = "SELECT temperature FROM temperature_and_humidity";
				
		String resultString2 = SQLiteClient.selectString(sql2);
		System.out.println("String: " + resultString2);
		
	}
	
}
