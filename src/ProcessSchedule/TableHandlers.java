/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Pendoragon
 */
public class TableHandlers {        
    /**
     * Sets the table in accordance to the type of process schedule algorithm selected and count
     * @param index the index of the process schedule algorithm type in the combo box
     * @param row_count the number of rows of the table
     * @param table
     */
    public static void setTable(int index, int row_count, JTable table) {
        String[] columnNames = new String[]{"Process No.", "AT", "BT", "CT", "TAT", "WT"};
        String[] columnNamesWithPriority = new String[]{"Process No.", "AT", "BT", "Priority", "CT", "TAT", "WT"};
        JTableHeader tableHeader = table.getTableHeader(); // Get table header
        TableColumnModel tableColumnModel = tableHeader.getColumnModel(); // Get table column model
        int col_count; // Assignments done in switch case
        
        switch (index) {
            case 0:
                col_count = columnNames.length;
                table.setModel(new DefaultTableModel(row_count, col_count));                   
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNames[i]);
                }
                break;
            case 1:
                col_count = columnNames.length;
                table.setModel(new DefaultTableModel(row_count, col_count));
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNames[i]);
                }
                break;
            case 2:
                col_count = columnNames.length;
                table.setModel(new DefaultTableModel(row_count, col_count));
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNames[i]);
                }
                break;
            case 3:
                col_count = columnNamesWithPriority.length;
                table.setModel(new DefaultTableModel(row_count, col_count));
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNamesWithPriority[i]);
                }
                break;
            case 4:
                col_count = columnNamesWithPriority.length;
                table.setModel(new DefaultTableModel(row_count, col_count));
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNamesWithPriority[i]);
                }
                break;
            case 5:
                col_count = columnNames.length;
                table.setModel(new DefaultTableModel(row_count, col_count));
                for (int i = 0; i < col_count; i++) {
                    tableColumnModel.getColumn(i).setHeaderValue(columnNames[i]);
                }
                break;
            default:
                break;
        }
        tableHeader.repaint();
        centerTableHorizontalAlignment(table);
        NumericHandlers.setProcessNumber(table);
    }

    /**
     * Sets the horizontal alignment of elements in a table to the center
     * @param table the table to be modified.
     */
    public static void centerTableHorizontalAlignment(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Sets the labels above the table in accordance to the type of process schedule algorithm selected and count
     * @param index the index of the process schedule algorithm type in the combo box
     */
    public static void setLabels(int index, JLabel typeLabel, JLabel countLabel, JLabel modeLabel, JLabel criterionLabel, JComboBox typeComboBox, JComboBox countComboBox) {
        typeLabel.setText(typeComboBox.getSelectedItem().toString());
        countLabel.setText((String) countComboBox.getSelectedItem());
        
        switch (index) {
            case 0:
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Arrival time (AT)");
                break;
            case 1:
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Burst time (BT)");
                break;
            case 2:
                modeLabel.setText("Preemptive");
                criterionLabel.setText("Burst time (BT)");
                break;
            case 3:
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Priority");
                break;
            case 4:
                modeLabel.setText("Preemptive");
                criterionLabel.setText("Priority");
                break;
            case 5:
                modeLabel.setText("Preemptive");
                criterionLabel.setText("");
                break;
            default:
                break;
        }
    }
    
}
