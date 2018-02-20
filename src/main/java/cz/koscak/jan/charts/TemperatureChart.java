package cz.koscak.jan.charts;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class TemperatureChart {

	public static void main(String[] args) throws IOException {
		
		generateChart();
		
		System.out.println("Done.");
		
	}

    
    private static void generateChart() throws IOException {
    	
		final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        
        XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainGridlinesVisible(true);
		xyPlot.setRangeGridlinesVisible(true);
		xyPlot.setRangeGridlinePaint(Color.black);
		xyPlot.setDomainGridlinePaint(Color.black);
		//xyPlot.setBackgroundPaint( Color.WHITE /*Color.LIGHT_GRAY*/ );
		xyPlot.setBackgroundPaint(new Color(240, 240, 240));
		
		int imageWidth = 700; // 1000; // 560;
		int imageHeight = 400; // 800; // 370;
		File chartFile = new File("src/main/resources/xy_chart-teplota-3.jpeg");
		ChartUtilities.saveChartAsJPEG(chartFile, chart, imageWidth, imageHeight);
        
	}

	/**
     * Creates a sample dataset.
     *
     * @return the dataset.
     */
    private static XYDataset createDataset() {
    	
    	LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d.M.yyyy" /* "HH:mm:ss d.M.yyyy" */);
		String formattedDateTime = dateTime.format(formatter2);

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        
        System.out.println(day + ", " + month + ", " + year);
        
        final TimeSeries s1 = new TimeSeries("Teplota");
        s1.add(new Minute(0, 0, day, month, year), 24.0);
        s1.add(new Minute(30, 12, day, month, year), 16.0);
        s1.add(new Minute(15, 14, day, month, year), 18.0);
        s1.add(new Minute(15, 23, day, month, year), 23.0);
        
        //final TimeSeries s2 = new TimeSeries("Vlhkost (% / 3)", Minute.class);
        final TimeSeries s2 = new TimeSeries("Ideální teplota");
        s2.add(new Minute(0, 0, day, month, year), 22.0);
        s2.add(new Minute(00, 24, day, month, year), 22.0);
        
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;

    }
    

	/**
     * Creates a chart.
     * 
     * @param dataset a dataset.
     * 
     * @return A chart.
     */
    private static JFreeChart createChart(final XYDataset dataset) {
    	
    	LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d.M.yyyy" /* "HH:mm:ss d.M.yyyy" */);
		String formattedDateTime = dateTime.format(formatter2);
		
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Teplota dne " + formattedDateTime, "Čas", "Teplota (°C)", dataset, true, true, false);
        
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE, 120));
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));

        //range.setRange(-10, 50);
        range.setRange(0, 30);
        
        return result;
    }
	
}
