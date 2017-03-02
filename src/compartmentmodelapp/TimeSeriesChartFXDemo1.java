
package compartmentmodelapp;
/* ===========================
 * TimeSeriesChartFXDemo1.java
 * ===========================
 * 
 * Copyright (c) 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.jfree.org/jfreechart/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */


import static compartmentmodelapp.BMIDemoTest.getBmi;
import static javafx.application.Application.launch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;

/**
 * A demo showing the display of JFreeChart within a JavaFX application.
 * 
 * The ChartCanvas code is based on: 
 * http://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/
 * 
 */
public class TimeSeriesChartFXDemo1 extends Application {

    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Hans' og Grethes BMI",    // title
            "Tid",                     // x-axis label
            "BMI (kg/square meter)",   // y-axis label
            dataset);

        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle("Source: http://www.ico.org/historical/2010-19/PDF/HIST-PRICES.pdf", 
                new Font(fontName, Font.PLAIN, 14)));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setBaseStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, 
                    BasicStroke.JOIN_BEVEL), false);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, new Color(24, 123, 58));
            renderer.setSeriesPaint(2, new Color(149, 201, 136));
            renderer.setSeriesPaint(3, new Color(1, 62, 29));
            renderer.setSeriesPaint(4, new Color(81, 176, 86));
            renderer.setSeriesPaint(5, new Color(0, 55, 122));
            renderer.setSeriesPaint(6, new Color(0, 92, 165));
        }

        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    private static XYDataset createDataset() {

        //here we can use the static method getBmi without calling an actual instance of BarChartFXDemo1
        double bmi_Hans1    = getBmi(59, 1.87);
        double bmi_Grethe1  = getBmi(65, 1.65);
        double bmi_Hans2    = getBmi(69, 1.87);
        double bmi_Grethe2  = getBmi(70, 1.65);
        
        
        TimeSeries s1 = new TimeSeries("Hans");
        
        s1.add(new Day(1, 12, 2016), bmi_Hans1);
        s1.add(new Day(1, 1, 2017), bmi_Hans2);
        

        TimeSeries s2 = new TimeSeries("Grethe");
        
        
        s2.add(new Day(1, 1, 2017),  bmi_Grethe2);
        
       
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        
        return dataset;
    }

    @Override 
    public void start(Stage stage) throws Exception {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 
        ChartViewer viewer = new ChartViewer(chart);
        stage.setScene(new Scene(viewer)); 
        stage.setTitle("JFreeChart: TimeSeriesFXDemo1.java...a little changed"); 
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
}

