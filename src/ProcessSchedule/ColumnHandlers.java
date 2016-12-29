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
public class ColumnHandlers {
    /**
     * Sets a column of a table to random double value
     * @param table the table to be modified.
     * @param column_index the index of the table column.
     * @param rangeMin
     * @param rangeMax
     */
    public static void setColumnToRandomizeDouble(JTable table, int column_index, double rangeMin, double rangeMax) {        
        for (int i = 0; i < table.getRowCount(); i++) {            
            double randomValue = NumericHandlers.randomRange(rangeMin, rangeMax);
            table.setValueAt(NumericHandlers.roundTo2DecimalPlaces(randomValue), i, column_index);
        }
    }
    
    /**
     * Set the value of a table column to ascending double by incremental value.
     * @param table the table to be modified.
     * @param column_index index of the table column.
     * @param starting_value the first value before ascending.
     * @param incremental_value the value to increment.
     */
    public static void setColumnToAscendingDouble(JTable table, int column_index, double starting_value, double incremental_value) {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(starting_value + (i * incremental_value), i, column_index);
        }
    }

    /**
     * Set the value of a table column to descending double by incremental value.
     * @param table the table to be modified.
     * @param column_index index of the table column.
     * @param starting_value the first value before descending.
     * @param decremental_value the value to decrement.
     */
    public static void setColumnToDescendingDouble(JTable table, int column_index, double starting_value, double decremental_value) {
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            // If value is less than zero then set it to zero.
            if (starting_value - (i * decremental_value) < 0) {
                table.setValueAt(0.0, i, column_index);
            } else {
                table.setValueAt(starting_value - (i * decremental_value), i, column_index);
            }
        }
    }

    /**
     * Sets the value of a table column to ascending integer values.
     * @param table the table to be modified
     * @param column_index the index of the table column
     * @param range the range of the ascend.
     */
    public static void setColumnToAscendingInt(JTable table, int column_index, int range) {
        for (int i = 0; i < range; i++) {
            table.setValueAt(i + 1, i, column_index);
        }
    }
    
    /**
     * Sets the value of a table column to descending integer values.
     * @param table the table to be modified
     * @param column_index the index of the table column
     * @param range the range of the ascend.
     */
    public static void setColumnToDescendingInt(JTable table, int column_index, int range) {
        int j = 0;
        for (int i = range - 1; i >= 0; i--) {
            table.setValueAt(i + 1, j, column_index);
            j++;
        }
    }
    
    /**
     * Sets a column of the table to random integer values.
     * @param table the table to be modified.
     * @param column_index the index of the table column.
     * @param rangeMin the minimum range of randomization
     * @param rangeMax the max range range of randomization
     */
    public static void setColumnToRandomizeInt(JTable table, int column_index, int rangeMin, int rangeMax) {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(NumericHandlers.randomRange(rangeMin, rangeMax) + 1, i, column_index);
        }
    }
}
