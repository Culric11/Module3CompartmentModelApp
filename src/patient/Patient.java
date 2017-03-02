
package patient;


import java.time.Instant;
import java.time.temporal.ChronoField;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @since 1.0 
 */
public class Patient {

    /**
     * creates a new patient with the given id
     * TODO: CHECK THAT THIS ID IS UNIQUE
     * TODO: CHECK IF THAT ID IS CORRECT (f.eks. CPR number)
     * this constructor returns null if the id is not unique or correct 
     * @param id the comparable id of this patient
     * 
     */
    public Patient(Comparable id) {
        this.patientid = id;
    }
    
    
    Comparable patientid;
    
    TimeSeries weight_measurements = new TimeSeries("Vægtmålinger");
    TimeSeries bmi_values = new TimeSeries("BMI");
    
    
    /**
     * this method - when called - adds a weight measurement - at the current time
     * by calling addWeightMeasurementAtDay
     */
    public void addWeightMeasurement(int weight) {
        addWeightMeasurementAtDate(weight, Instant.now());
    }
    
    /**
     * this method - when called - adds a weight measurement - at the specified time point
     * this method overloads the method addWeightMeasurement
     */
    public void addWeightMeasurementAtDate(int weight, Instant i) {
        
        //retrieve the day from the instant
        int day = i.get(ChronoField.DAY_OF_MONTH);
        
        //retrieve the month
        int month = i.get(ChronoField.MONTH_OF_YEAR);
        
        //retrieve the year
        int year = i.get(ChronoField.YEAR);
        
        weight_measurements.add(new Day(day, month, year), weight);
    }
}
