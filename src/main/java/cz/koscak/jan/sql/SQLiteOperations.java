package cz.koscak.jan.sql;

import java.io.FileNotFoundException;
import java.util.List;

import cz.koscak.jan.utilities.FileIO;
import cz.koscak.jan.utilities.SQLiteClient;

public class SQLiteOperations {

	public static void initialize(String pathToDB) {
		SQLiteClient.initialize(pathToDB);
	}
	
	public static void createTableTemperatureAndHumidity() throws FileNotFoundException {
		String filePath = "res/create_table_temperature_and_humidity.sql";
		String sql = FileIO.read(filePath);
		System.out.println("SQL-Create table: " + sql);
		SQLiteClient.createTable(sql);
	}
	
	public static void selectFromTableTemperatureAndHumidity() {
		String sql = "SELECT id, date, temperature, humidity FROM temperature_and_humidity";
		SQLiteClient.select(sql);
	}
	
	public static List<String[]> selectFromTableTemperatureAndHumidityAndReturnAsList() {
		String sql = "SELECT id, date, temperature, humidity FROM temperature_and_humidity";
		return SQLiteClient.selectAndReturnAsList(sql);
	}
	
	public static String[] selectFromTableTemperatureAndHumidityLastEntryAndReturnAsArray() {
		String sql = "SELECT date, temperature, humidity FROM temperature_and_humidity ORDER BY date DESC LIMIT 1";
		List<String[]> resultAsList = SQLiteClient.selectAndReturnAsList(sql);
		String[] result = null;
		if (resultAsList != null && !resultAsList.isEmpty()) {
			result = resultAsList.get(0);
		}
		return result;
	}
	
	public static void insertIntoTableTemperatureAndHumidity() throws FileNotFoundException {
		//String date = "datetime('now')";
		String date = "datetime('now', 'localtime')";
		String temperature = "20.0";
		String humidity = "60.0";
		String sql = "INSERT OR REPLACE INTO temperature_and_humidity (date, temperature, humidity) VALUES (" + date + ", '" + temperature + "', '" + humidity + "')";   	
		SQLiteClient.insert(sql);
	}

}
