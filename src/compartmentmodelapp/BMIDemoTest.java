    
package compartmentmodelapp;

import java.awt.Color;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
* A demo showing the display of JFreeChart within a JavaFX application.
* For more information about the JFreeSVG vs Batik performance test, see
* this link: http://www.object-refinery.com/blog/blog-20140423.html
*/
public class BMIDemoTest extends Application implements ChartMouseListenerFX {

    /**
     * Returns a sample dataset with BMI values for 2 persons Hans and Gretel.
     *
     * @return The dataset.
     */
   private static CategoryDataset createDataset() {
        
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        double bmi_Hans1    = getBmi(59, 1.87);
        double bmi_Grethe1  = getBmi(65, 1.65);
        double bmi_Hans2    = getBmi(69, 1.87);
        double bmi_Grethe2  = getBmi(70, 1.65);
        
        dataset.addValue( getBmi(59, 1.87),   "01.12.2016", "Hans");
        dataset.addValue(bmi_Grethe1, "01.12.2016", "Grethe");
        dataset.addValue(bmi_Hans2,   "01.01.2017", "Hans");
        dataset.addValue(bmi_Grethe2, "01.01.2017", "Grethe");
        
        return dataset;
    }
    
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Hans' og Grethes BMI", "Navn" /* x-axis label*/, 
                "BMI (kg/square meter)" /* y-axis label */, dataset);
        
        chart.addSubtitle(new TextTitle("før og efter jul i heksehuset"));
        
        chart.setBackgroundPaint(Color.GREEN);
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }
    /**
     * Adds a chart viewer to the stage and displays it.
     * 
     * @param stage  the stage.
     * @throws Exception if something goes wrong.
     */
    @Override 
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        
        
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 
        ChartViewer viewer = new ChartViewer(chart);
        
        viewer.addChartMouseListener(this);
        
        
        Scene scene = new Scene(viewer);
        stage.setScene(scene); 
        stage.setTitle("BMI Demo");
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show(); 
    }
    
    /** 
     * @param weight the weight in kg
     * @param height the height in metres
     * @return the body mass index for a given height and weight
     *  bodymass [kg]/height[m]*height[m]
     */
    public static double getBmi(double weight, double height) {
       return (weight/(height*height));
    }
    
    
    /**
     * Entry point.
     * 
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
  
}
