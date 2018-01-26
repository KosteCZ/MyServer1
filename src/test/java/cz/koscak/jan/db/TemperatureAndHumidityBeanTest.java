package cz.koscak.jan.db;

import java.io.IOException;

import org.junit.Test;

import cz.koscak.jan.core.Application;
import cz.koscak.jan.jsf.beans.TemperatureAndHumidityBean;

public class TemperatureAndHumidityBeanTest {
	
	@Test
	public void testBean() throws IOException {
		
		Application.initialize();
		TemperatureAndHumidityBean temperatureAndHumidityBean = new TemperatureAndHumidityBean();
		String result = temperatureAndHumidityBean.printLastDataFromDB();
		System.out.println(result);
		
	}
	
	@Test
	public void testBean2() throws IOException {
		
		Application.initialize();
		TemperatureAndHumidityBean temperatureAndHumidityBean = new TemperatureAndHumidityBean();
		String result = temperatureAndHumidityBean.printCurrentTime();
				
		System.out.println(result);
		
	}

}
