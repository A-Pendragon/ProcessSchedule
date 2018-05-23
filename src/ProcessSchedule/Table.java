package ProcessSchedule;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public abstract class Table extends JTable {
    
    private final JTable table;
    private final int rowCount;
    
    public Table(JTable table, int rowCount) {
        this.table = table;
        this.rowCount = rowCount;
    }
                
    public void update() {
        table.getTableHeader().repaint();        
    }
    
    @Override
    public int getRowCount() {
        return rowCount;
    }
    
    @Override
    public int getColumnCount() {
        return table.getColumnCount();
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        return table.getValueAt(row, column);
    }
    
    public void setColumn(int column, int[] values) {
        if(values == null) {
            return;
        }
        for(int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(values[i], i, column);
        }
    }
    
    public void setColumn(int column, double[] values) {
        if(values == null) {
            return;
        }
        for(int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(values[i], i, column);
        }
    }
    
    public void setModel(int colsCount) {
        table.setModel(new DefaultTableModel(rowCount, colsCount));
    }
    
    public void setHeader(String[] columnNames) {
        TableColumnModel tableColumnModel = table.getTableHeader().getColumnModel();
        int colCount = columnNames.length;        
        
        for (int i = 0; i < colCount; i++) {
            tableColumnModel.getColumn(i).setHeaderValue(columnNames[i]);
        }
    }                    
    
    public void centerHorizontalAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }          
    
    public void clear() {
        for(int j = 0; j < getColumnCount(); j++) {
            for(int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt("", i, j);
            }
        }
    }
    
    public void copyTable(int rowCount, int columnCount) {        
        StringBuilder stringBuilder = new StringBuilder();                  
        
        for(int i = 0; i < rowCount; i++) {
            for(int j = 0; j < columnCount; j++) {
                if(table.getValueAt(i, j).toString().isEmpty()) {
                    stringBuilder.append("null");
                } else {
                    stringBuilder.append(table.getValueAt(i, j));
                }
                if(j < columnCount - 1) {
                    stringBuilder.append("\t");
                }
            }
            stringBuilder.append("\n");
        }
        StringSelection stringSelection = new StringSelection(stringBuilder.toString());
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(stringSelection, stringSelection);
    }        

    public void copyTable() {
        copyTable(getRowCount(), getColumnCount());
    }
    
    public void pasteTable(int rowCount, int columnCount) {                               
        try {
            Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String string = (String)(systemClipboard.getContents(null).getTransferData(DataFlavor.stringFlavor));
            StringTokenizer stringTokenizer = new StringTokenizer(string, "\n");
            String rowString, value;
            for(int i = 0; i < rowCount && stringTokenizer.hasMoreTokens(); i++) {                
                rowString = stringTokenizer.nextToken();
                StringTokenizer stringTokenizer2 = new StringTokenizer(rowString, "\t");
                for(int j = 0; j < columnCount && stringTokenizer2.hasMoreTokens(); j++) {        
                    value = (String)stringTokenizer2.nextToken();
                    table.setValueAt((value.equals("null")? " " : value), i ,j);
                }
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pasteTable() {
        pasteTable(getRowCount(), getColumnCount());
    }
    
    public void shuffleTableColumn(int column) {
        ArrayList<Object> list = new ArrayList<>();
        
        for(int i = 0; i < getRowCount(); i++) {
           list.add(table.getValueAt(i, column));
        }                
        Collections.shuffle(list);        
        for(int i = 0; i < getRowCount(); i++) {
            table.setValueAt(list.get(i), i, column);
        }
    }
}
