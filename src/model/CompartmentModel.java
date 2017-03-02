/*
 * -----------------
 * CompartmentModel.java
 * -----------------
 * (C) Copyright 2017, by MMDS, Aalborg University and Contributors.
 *
 * Original Author:  Ulrike Pielmeier (for Aalborg University);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 31-Jan-2017 : Version 1 (UP);
 *
 */
package model;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeTableXYDataset;

/**
 * a compartment model represents a set of ordinary differential equations which are evaluated over a
 * given time period, per time step given by delta t (in whole seconds). Evaluations of the model equations 
 * create ModelEvaluation instances which are of {@link TimeTableXYDataset} since they are evaluated 
 * over an equally spaced time range. By default the time step is 15 sec and the
 * time period is current system time with a duration of one hour, at the default time zone.
 * the class is abstract so it cannot be directly instantiated
 * @since 1.0
 */
public abstract class CompartmentModel {
    
    /**
     * the time step in seconds for stepwise evaluation of the model equations
     */
    protected int deltat = 15;
    
    /**
     * the time period defining the start and end of the stepwise calculations
     */
    protected TimePeriod timeperiod;
        
    
    /** the time zone for the time points in the evaluation */
    protected TimeZone zone; 
    
    
    /**
     * default constructor, intended to be called by subclasses
     * instantiates a model with default time period now + 1 hour, 
     * default time step of 15 sec 
     */
    protected CompartmentModel() {
        this(15);      
    }
    
    
    /**
     * default constructor, intended to be called by subclasses
     * instantiates a model with default time period now + 1 hour, 
     * default time step of 15 sec 
     * @param delta the time delta in whole seconds of time
     */
    protected CompartmentModel(int delta) {
        
        setDeltat(deltat);
        
        setTimeperiod(new SimpleTimePeriod(java.util.Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES)), java.util.Date.from(Instant.now().truncatedTo(ChronoUnit.MINUTES).plus(Duration.ofMinutes(60))) ));
             
        setZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
    }
    
   
    /**
     * performs a model evaluation with current parameter values and stores the
     * result in a model evaluation instance
     * @return the model evaluation instance after calculating model equations
     */
     public ModelEvaluation calculate() {
        
         // create model evaluation for the results, default time zone, default instant of evaluation time
         ModelEvaluation results = new ModelEvaluation(this, null, null);
         
         // do the model evaluation - this method is overridden by subclasses
         evaluateModel(results);
         
         // return results to the caller 
         return results;
     }
    
     
     /**
      * method to perform the model calculation
      * @param results the evaluation results object to store the results from calculating the model equations 
      */
     protected abstract void evaluateModel(ModelEvaluation results);
     
     
     
    /**
     * @return the deltat
     */
    public int getDeltat() {
        return deltat;
    }

    /**
     * @param deltat the deltat to set
     */
    public final void setDeltat(int deltat) {
        this.deltat = deltat;
    }

    /**
     * @return the timeperiod
     */
    public TimePeriod getTimeperiod() {
        return timeperiod;
    }

    /**
     * @param timeperiod the timeperiod to set
     */
    public final void setTimeperiod(TimePeriod timeperiod) {
        this.timeperiod = timeperiod;
    }

    /**
     * @return the zone
     */
    public TimeZone getZone() {
        return zone;
    }

    /**
     * @param zone the zone to set
     */
    public final void setZone(TimeZone zone) {
        this.zone = zone;
    }

    
    
}
