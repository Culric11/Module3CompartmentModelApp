/*
 * -----------------
 * ModelEvaluation.java
 * -----------------
 * (C) Copyright 2017, by MMDS, Aalborg University and Contributors.
 *
 * Original Author:  Ulrike Pielmeier (for Aalborg University);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 5-Feb-2017 : Version 1 (UP);
 *
 */
package model;

import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;
import org.jfree.data.time.TimeTableXYDataset;

/**
 *
 * the evaluation results of a compartment model over a given time span defined by a start 
 * date and an end date; all results share the same set of x-values
 */
public class ModelEvaluation extends TimeTableXYDataset {

    /** constructor for a ModelEvaluation 
     * 
     * @param model the model which is evaluated
     * @param instant the instant of time of the evaluation; current system time
     * is used if instant is null
     * @param zone the time zone for the time span of this evaluation; default
     * time zone id is used if this parameter is null
     */
    public ModelEvaluation(CompartmentModel model, Instant instant, TimeZone zone) {
       
        //if the time zone is null then use the default time zone 
        //call TimeTableXYDataset constructor
        super((zone == null)? TimeZone.getTimeZone(ZoneId.systemDefault()) : zone);
        
        this.model = model;
        
        //if instant is null use the system current time in milliseconds
        this.instant = (instant == null) ? Instant.now() : instant;
    }
    
    /**
     * the reference to the model which has been evaluated
     */
    private CompartmentModel model;
    
    /**
     * the instant of time at which the evaluation happened
     */
    private Instant instant;

    /**
     * @return the model; null is not permitted
     */
    public CompartmentModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(CompartmentModel model) {
        this.model = model;
    }

    /**
     * @return the instant; null is not permitted
     */
    public Instant getInstant() {
        return instant;
    }

    /**
     * @param instant the instant to set
     */
    public void setInstant(Instant instant) {
        this.instant = instant;
    }
    
    
}
