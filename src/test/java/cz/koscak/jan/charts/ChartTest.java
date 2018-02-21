package cz.koscak.jan.charts;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.junit.Test;

import cz.koscak.jan.core.Application;
import cz.koscak.jan.jsf.beans.TemperatureAndHumidityBean;

public class ChartTest {

	@Test
	public void creatingChartsBasedOnDBData() throws IOException {
		
		generateChart();
		
		System.out.println("Done.");
		
	}

    
    private static void generateChart() throws IOException {
    	
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(22); //.minusDays(1); //.minusDays(5);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy"); // "HH:mm:ss d.M.yyyy"
		String dateTimeText = localDateTime.format(formatter);

		System.out.println(localDateTime);

		final XYDataset dataset = createDataset(localDateTime);
        final JFreeChart chart = createChart(localDateTime, dataset);
        
        XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainGridlinesVisible(true);
		xyPlot.setRangeGridlinesVisible(true);
		xyPlot.setRangeGridlinePaint(Color.black);
		xyPlot.setDomainGridlinePaint(Color.black);
		//xyPlot.setBackgroundPaint( Color.WHITE /*Color.LIGHT_GRAY*/ );
		xyPlot.setBackgroundPaint(new Color(240, 240, 240));
		
		int imageWidth = 600; //700; // 1000; // 560;
		int imageHeight = 350; //400; // 800; // 370;
		File chartFile = new File("out/xy_chart-teplota-" + dateTimeText + "-7" + ".jpeg");
		ChartUtilities.saveChartAsJPEG(chartFile, chart, imageWidth, imageHeight);
        
	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return the dataset.
	 * @throws IOException 
	 */
	private static XYDataset createDataset(LocalDateTime localDateTime) throws IOException {

		Application.initialize();
		TemperatureAndHumidityBean temperatureAndHumidityBean = new TemperatureAndHumidityBean();
		List<String[]> result = temperatureAndHumidityBean.selectFromTableTemperatureAndHumidityAndReturnAsList(localDateTime);

		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		final TimeSeries s1 = new TimeSeries("Teplota");

		int year = localDateTime.getYear();
		int month = localDateTime.getMonthValue();
		int day = localDateTime.getDayOfMonth();

		for (String[] strings : result) {
			System.out.println(strings[0].split(" ")[1]);
			String[] time = strings[0].split(" ")[1].split(":");
			if (Double.parseDouble(strings[1]) != 0.0) {
				s1.add(new Minute(Integer.parseInt(time[1]), Integer.parseInt(time[0]), day, month, year), Double.parseDouble(strings[1]));
			}
		}

		dataset.addSeries(s1);

		System.out.println(result);
		
		return dataset;

	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset a dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(LocalDateTime localDateTime, final XYDataset dataset) {

		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d.M.yyyy" /* "HH:mm:ss d.M.yyyy" */);
		String formattedDateTime = localDateTime.format(formatter2);

		final JFreeChart result = ChartFactory.createTimeSeriesChart("Teplota dne " + formattedDateTime, "Čas (hodiny:minuty)", "Teplota (°C)", dataset, true, true,
				false);

		final XYPlot plot = result.getXYPlot();
		ValueAxis domain = plot.getDomainAxis();
		//domain.setAutoRange(true);
		ValueAxis range = plot.getRangeAxis();
		DateAxis axisX = (DateAxis) plot.getDomainAxis();
		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		
		/*SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.DAY_SEGMENT_SIZE, 1, 0);
		axis.setTimeline(timeline);*/
		
		/*DateRange dateRange = new DateRange(new Range(localDateTime.minusDays(1).toEpochSecond(null), localDateTime.toEpochSecond(null)));
		domain.setRange(dateRange);*/
		
		
		/*
		Date minDate = new Date();
		minDate.setHours(0);
		minDate.setMinutes(0);
		minDate.setSeconds(0);
		*/
		/*Calendar current = Calendar.getInstance();
		//current.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE),0,0,0);
		Date minDate = new Date(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE), 0, 0, 0);*/
		/*
		Date maxDate = new Date();
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);
		maxDate.setDate(maxDate.getDate()+1);
		*/
		
		
		/*Date minDate = new Date();
		minDate.setDate(minDate.getDate()-1);
		minDate.setHours(23);
		minDate.setMinutes(0);
		minDate.setSeconds(0);
		
		Date maxDate = new Date();
		maxDate.setDate(maxDate.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(59);
		maxDate.setSeconds(59);*/
		
		Date date = new Date(localDateTime.getYear()-1900, localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth());
		/*date.setDate(localDateTime.getDayOfMonth());
		date.setMonth(localDateTime.getMonthValue()-1);*/
		
		/*Date minDate = new Date(date.getTime());
		minDate.setHours(0);
		minDate.setMinutes(0);
		minDate.setSeconds(0);*/
		
		//Date maxDate = new Date(date.getTime());
		/*maxDate.setHours(23);
		maxDate.setMinutes(59);
		maxDate.setSeconds(59);*/
		/*maxDate.setDate(maxDate.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);*/
		
		Date minDate = new Date(date.getTime()-1);
		minDate.setHours(23);
		minDate.setMinutes(59);
		minDate.setSeconds(59);
		
		Date maxDate = new Date(date.getTime());
		/*maxDate.setHours(23);
		maxDate.setMinutes(59);
		maxDate.setSeconds(59);*/
		maxDate.setDate(maxDate.getDate()+1);
		maxDate.setHours(0);
		maxDate.setMinutes(0);
		maxDate.setSeconds(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		System.out.println("MinDate: " + sdf.format(minDate));
		System.out.println("MaxDate: " + sdf.format(maxDate));
		
		axisX.setMinimumDate(minDate);
		axisX.setMaximumDate(maxDate);
		
		axisX.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE, 120));
		axisX.setDateFormatOverride(new SimpleDateFormat("HH:mm"));

		// range.setRange(-10, 50);
		range.setRange(0, 30);
		
		axisY.setTickUnit(new NumberTickUnit(2));
		
		return result;
	}

}
