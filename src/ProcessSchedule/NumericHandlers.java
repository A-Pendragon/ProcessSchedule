/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import javax.swing.JTable;

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
        return str.trim().replaceAll("[^\\d.]", "");
    }

    /**
     * Rounds off a double to 2 decimal places
     * @param d the double to be rounded off
     * @return the rounded off double
     */
    public static double roundTo2DecimalPlaces(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    // Set the Process number to incrementing order.
    public static void setProcessNumber(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(i + 1, i, 0);
        }
    }
    
}
