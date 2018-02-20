package cz.koscak.jan.sql;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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
		List<String[]> result = SQLiteClient.selectAndReturnAsList(sql);
		for (String[] strings : result) {
			for (int i = 0; i < strings.length; i++) {
				System.out.print(strings[i]);
				if (i < (strings.length - 1)) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
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
	
	public static List<String[]> selectFromTableTemperatureAndHumidityAndReturnAsList(LocalDateTime localDateTime) {
        String year = addLeadingZeroIfNeccesary(localDateTime.getYear());
        String month = addLeadingZeroIfNeccesary(localDateTime.getMonthValue());
        String day = addLeadingZeroIfNeccesary(localDateTime.getDayOfMonth()); 
		String sql = "SELECT date, temperature, humidity FROM temperature_and_humidity WHERE date LIKE \"" + year + "-" + month + "-" + day + "%\";";
		System.out.println(sql);
		return SQLiteClient.selectAndReturnAsList(sql);
	}
	
	private static String addLeadingZeroIfNeccesary(int number) {
		String text = String.valueOf(number);
		return text.length() == 1 ? "0" + text : text;
	}

}
