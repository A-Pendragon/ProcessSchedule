/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author Pendoragon
 */
public class TableSystemClipboard { 
                
    public static void copyTable(JTable table) {
        int numOfRows = table.getRowCount();
        int numOfColumns = table.getColumnCount();
        StringBuilder stringBuilder = new StringBuilder();          
        
        int inputColumns = (table.getColumnCount() == 6)? 3: 4;
        
        // Get the values in the table and append it into the string builder.
        for(int i = 0; i < numOfRows; i++) {
            for(int j = 0; j < inputColumns; j++) {
                stringBuilder.append(table.getValueAt(i, j));
                if(j < numOfColumns - 1) {
                    stringBuilder.append("\t");
                }
            }
            stringBuilder.append("\n");
        }
        
        StringSelection stringSelection = new StringSelection(stringBuilder.toString());
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(stringSelection, stringSelection); // Sets the contents of the system clipboard.
    }
    
    public static void pasteTable(JTable table) {                
        try {
            Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String string = (String)(systemClipboard.getContents(null).getTransferData(DataFlavor.stringFlavor)); // Get the contents in the system clipboard.
            //System.out.println("String is:\n" + string);
            StringTokenizer stringTokenizer = new StringTokenizer(string, "\n"); // Used as a condition in the for loop, to know if there are still more columns.
            
            String rowString, value;
            int inputColumns = (table.getColumnCount() == 6)? 3: 4;
            
            for(int i = 0; i < table.getRowCount() && stringTokenizer.hasMoreTokens(); i++) {                
                rowString = stringTokenizer.nextToken();                
                StringTokenizer stringTokenizer2 = new StringTokenizer(rowString, "\t"); // Used as a condition in the for loop, to know if there are more element in the row.
                
                for(int j = 0; j < inputColumns && stringTokenizer2.hasMoreTokens(); j++) {             
                    value = (String)stringTokenizer2.nextToken(); // Store the next token (which are the values in the cell) in the string value.                                        
                    table.setValueAt(value, i, j);
                }
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(TableSystemClipboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
