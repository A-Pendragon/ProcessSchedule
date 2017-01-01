/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Pendoragon
 */
public class NumericHandlers {

    /**
     * Removes all non-numeric characters and spaces.
     * @param str the string to be modified
     * @return the string with only numbers and point
     */
    public static String removeAllNonNumeric(String str) {
        if(str.equals("")) {
            return "0";
        }
        return str.trim().replaceAll("[^\\d.]", "");
    }
    
    public static String replaceZeroAndEmptyWith(String str, String target) {        
        if(str.equals("0") || str.equals("")) {
            return target;
        }
        return str;
    }

    /**
     * Rounds off a double to 2 decimal places
     * @param d the double to be rounded off
     * @return the rounded off double
     */
    public static double roundTo2DecimalPlaces(double d) {
        return Math.round(d * 100.0) / 100.0;
    }
        
    // Returns a random integer number between min [inclusive] and max [exclusive].
    // Note that max is exclusive, so using randomRange( 0, 10 ) will return values between 0 and 9. If max equals min, min will be returned.
    public static int randomRange(int min, int max) {        
        if(min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextInt(min, max);                
    }
    
    // Returns a random double number between and min [inclusive] and max [exclusive].
    // Note that max is exclusive, so using Random.Range( 0.0, 1.0 ) will return values between 0.0 and 1.0. If max equals min, min will be returned.
    public static double randomRange(double min, double max) {
        if(min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }    
}
