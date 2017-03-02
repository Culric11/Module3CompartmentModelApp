 /*
 * -----------------
 * TwoCompartmentInsulin.java
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

import java.time.Instant;
import org.jfree.data.time.TimeSeries;
import java.util.Date;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;

/**
 *
 * @author Ulrike Pielmeier
 * @since 1.0
 */
public class TwoCompartmentInsulin extends CompartmentModel {
   
    
    
    /**
     * fractional rate constants
     */
    private double k1, k2, k3, k4;
    
    /**
     * plasma volume in liters
     */
    private double vp;
    
    /**
     * interstitial volume  in liters
     */
    private double vq;
    
    /**
     * default initial concentration values in plasma and interstitial compartment
     */
    private double cp_init, cq_init;
    
    /**
     * the key identifiers for the state variable series 
     * 
     */
    Comparable key_cq = "Interstitial fluid concentration";
    Comparable key_cp = "Plasma insulin concentration"; 
    Comparable key_rxi = "Cont.iv infusion";
    
    /**
     * the exogenous insulin infusion as time series 
     */
    private TimeSeries rxi = new TimeSeries(key_rxi);
    
    
    
    public TwoCompartmentInsulin() {
        super();
    }
    
    
    
    /**
     * calculates the next plasma insulin concentration over the given time step,
     * for a given plasma concentration and interstitial concentration and insulin
     * infusion
     * @param cp current concentration in plasma (mU/l)
     * @param cq current concentration in interst. (mU/l)
     * @param rxit current infusion in mU*deltat/60s
     * @return next plasma concentration
     */
    private double calculate_cp(double cp, double cq, double rxit)
    {
        double dc = (rxit - ((k1+k3)*cp*getVp()) + (getK2()*cq*getVq()) ) * getDeltat(); 
        return ( cp*getVp() +dc )/getVp(); 
    }
    
    /**
     * calculates the next interstitial insulin concentration over the given time step,
     * for a given plasma concentration and interstitial concentration
     * @param cp current concentration in plasma (mU/l)
     * @param cq current concentration in interst. (mU/l)
     * @return next interstitial concentration
     */
    private double calculate_cq(double cp, double cq)
    {
        double dq = (getK1()*cp*getVp() - ( (getK2()+getK4())*cq*getVq() ) ) * getDeltat();  
        return (cq*getVq() +dq)/getVq();
    }

    
    
    @Override
    protected void evaluateModel(ModelEvaluation results) {
        
        double cp = getCp_init();
        double cq = getCq_init();
        
        /** create time period for start point */
        Date start = getTimeperiod().getStart();
        
        /** create a second - this is the start time point */
        RegularTimePeriod t = RegularTimePeriod.createInstance(Second.class, start, getZone());
        
        /** if there is no insulin infusion at this start time point - set rxi = 0 */
        double rxi_t = (rxi.getValue(t) == null)? 0 : rxi.getValue(t).doubleValue()* deltat/60;
        
        /** add initial values to the results */
        results.add(t, cp, key_cp);        
        results.add(t, cq, key_cq);
        results.add(t, rxi_t, key_rxi);
        
        Instant instant = start.toInstant();
        Instant end = getTimeperiod().getEnd().toInstant();
        
        
        
        while (instant.isBefore(end))  {
            
            instant = instant.plusSeconds(getDeltat());  //increase t by delta t
            
            t = RegularTimePeriod.createInstance(Second.class, Date.from(instant), getZone());
            
            /** if there is no insulin infusion at this start time point - set rxi to previous value */
            rxi_t = (rxi.getValue(t) == null)? rxi_t : rxi.getValue(t).doubleValue() * deltat/60;
            
            double next_cp = calculate_cp(cp, cq, rxi_t); //evaluate plasma
            double next_cq = calculate_cq(cp, cq);        //evaluate interstitium
        
            results.add(t, next_cp, key_cp);        
            results.add(t, next_cq, key_cq);
            results.add(t, rxi_t, key_rxi);
            
            cp = next_cp;
            cq = next_cq;
        }
    }


    /**
     * @return the k1
     */
    public double getK1() {
        return k1;
    }

    /**
     * @param k1 the k1 to set
     */
    public void setK1(double k1) {
        this.k1 = k1;
    }

    /**
     * @return the k2
     */
    public double getK2() {
        return k2;
    }

    /**
     * @param k2 the k2 to set
     */
    public void setK2(double k2) {
        this.k2 = k2;
    }

    /**
     * @return the k3
     */
    public double getK3() {
        return k3;
    }

    /**
     * @param k3 the k3 to set
     */
    public void setK3(double k3) {
        this.k3 = k3;
    }

    /**
     * @return the k4
     */
    public double getK4() {
        return k4;
    }

    /**
     * @param k4 the k4 to set
     */
    public void setK4(double k4) {
        this.k4 = k4;
    }

    /**
     * @return the vp
     */
    public double getVp() {
        return vp;
    }

    /**
     * @param vp the vp to set
     */
    public void setVp(double vp) {
        this.vp = vp;
    }

    /**
     * @return the vq
     */
    public double getVq() {
        return vq;
    }

    /**
     * @param vq the vq to set
     */
    public void setVq(double vq) {
        this.vq = vq;
    }

    /**
     * @return the cp_init
     */
    public double getCp_init() {
        return cp_init;
    }

    /**
     * @param cp_init the cp_init to set
     */
    public void setCp_init(double cp_init) {
        this.cp_init = cp_init;
    }

    /**
     * @return the cq_init
     */
    public double getCq_init() {
        return cq_init;
    }

    /**
     * @param cq_init the cq_init to set
     */
    public void setCq_init(double cq_init) {
        this.cq_init = cq_init;
    }

   

    /**
     * @return the rxi
     */
    public TimeSeries getRxi() {
        return rxi;
    }

    /**
     * @param rxi the rxi to set
     */
    public void setRxi(TimeSeries rxi) {
        this.rxi = rxi;
    }

}
