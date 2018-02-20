package cz.koscak.jan.jsf.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cz.koscak.jan.sql.SQLiteOperations;

@ManagedBean(name="temperatureAndHumidity")
@SessionScoped
public class TemperatureAndHumidityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String printDemoMessage() {
		
		Exception e = new Exception();
		String methodName = e.getStackTrace()[0].getMethodName();
		return "Class: " + this.getClass().getSimpleName() + ", method: " + methodName + "().";	
		
	}
	
	public String printDataFromDB() {
		
		List<String[]> listOfRows = SQLiteOperations.selectFromTableTemperatureAndHumidityAndReturnAsList();
		StringBuilder sb = new StringBuilder();
		
		for (String[] row : listOfRows) {
			
			for (int i = 0; i < row.length; i++) {
				
				sb.append(row[i]);
				
				if (i < (row.length - 1)) {
					sb.append(" ");
				}
					
			}

			sb.append("\n");
			
		}
		
		String result = sb.toString();
		System.out.println("Result: " + "\n" + result);
		return result;
		
	}
	
	public String printLastDataFromDB() {
		
		String[] row = SQLiteOperations.selectFromTableTemperatureAndHumidityLastEntryAndReturnAsArray();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Last data: " + "\n");
		sb.append("Date: " + row[0] + " " + "\n");
		sb.append("Temperature: " + row[1] + "°C " + "\n");
		sb.append("Humidity: " + row[2] + "%" + "\n");
		
		String result = sb.toString();
		System.out.println("Result: " + "\n" + result);
		return result;
		
	}
	
	public String printLastDataFromDBDate() {
		
		String result;
		
		String[] row = SQLiteOperations.selectFromTableTemperatureAndHumidityLastEntryAndReturnAsArray();
		
		try {
			
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(row[0], formatter1);
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss d.M.yyyy");
			String formattedDateTime = dateTime.format(formatter2);
			result = formattedDateTime;
			
		} catch (DateTimeParseException e) {
			
			result = row[0];
			
		}
		
		return result;
		
	}
	
	public String printLastDataFromDBTemperature() {
		
		String[] row = SQLiteOperations.selectFromTableTemperatureAndHumidityLastEntryAndReturnAsArray();
		return row[1];
		
	}
	
	public String printLastDataFromDBHumidity() {
		
		String[] row = SQLiteOperations.selectFromTableTemperatureAndHumidityLastEntryAndReturnAsArray();
		return row[2];
		
	}
	
	public String printCurrentTime() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss d.M.yyyy");
		LocalDateTime date = LocalDateTime.now();
		return date.format(formatter);
		
	}

	public List<String[]> selectFromTableTemperatureAndHumidityAndReturnAsList(LocalDateTime localDateTime) {
		return SQLiteOperations.selectFromTableTemperatureAndHumidityAndReturnAsList(localDateTime);
	}
	
}
