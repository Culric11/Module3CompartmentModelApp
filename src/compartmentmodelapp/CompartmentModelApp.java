


package compartmentmodelapp;

import java.awt.Color;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ModelEvaluation;
import model.TwoCompartmentInsulin;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeTableXYDataset;

/**
 *
 * @author Ulrike Pielmeier
 * @since 26.01.2017
 */
public class CompartmentModelApp extends Application implements ChartMouseListenerFX, PlotChangeListener {
    
    
    private static JFreeChart createChart(TimeTableXYDataset dataset) {
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart("My chart", "time", "concentration", dataset);       
        
        chart.setBackgroundPaint(Color.white);
        
        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.setDomainPannable(true);
        xyplot.setRangePannable(true);
        
        return chart;
    }
    
    @Override
    public void start(Stage stage) {
        
        /** This program tests a two compartment insulin kinetics model with parameters:  */
        
        TwoCompartmentInsulin model = new TwoCompartmentInsulin();       
           
        /* insulin infusion in mU/min */
        model.getRxi().add(RegularTimePeriod.createInstance(Second.class, java.util.Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES)), model.getZone()), 6);
        model.getRxi().add(RegularTimePeriod.createInstance(Second.class, java.util.Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES).plusSeconds(120)), model.getZone()), 10);
        model.getRxi().add(RegularTimePeriod.createInstance(Second.class, java.util.Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES).plusSeconds(240)), model.getZone()), 4);
        
        
        model.setCp_init(350); /* initial plasma insulin concentration */
        model.setCq_init(0);   /* initial interst. insulin concentration */
        model.setVp(4.1);      /* plasma volume in liters */
        model.setVq(11.79);    /* interstitial volume in liters */ 
        
        model.setK2(0.03/60);  /* the fractional rate constants in 1/min -- divide by 60 to get 1/s */
        model.setK3(0.07/60);
        model.setK4(0.01/60);
        model.setK1(model.getK2()*model.getVq()/model.getVp());
        
        
        
        
        ModelEvaluation results = model.calculate();
        
        JFreeChart chart = createChart(results); 
        
        chart.getPlot().addChangeListener(this);
                
        ChartViewer viewer = new ChartViewer(chart);
        
        viewer.addChartMouseListener(this);
        
        
        stage.setScene(new Scene(viewer)); 
        stage.setTitle("JFreeChart: Two compartment insulin kinetics model"); 
        stage.setWidth(600);
        stage.setHeight(350);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Write the event to the console, just to illustrate.
     * 
     * @param event  event info. 
     */
    @Override
    public void chartMouseClicked(ChartMouseEventFX event) {
        System.out.println(event);
    }

    /**
     * Write the event to the console, just to illustrate.
     * 
     * @param event  event info. 
     */
    @Override
    public void chartMouseMoved(ChartMouseEventFX event) {
        System.out.println(event);
    }

    @Override
    public void plotChanged(PlotChangeEvent event) {
        System.out.println(event.getType().toString());
    }

    
    
    
}
