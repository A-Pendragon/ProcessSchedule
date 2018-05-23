package ProcessSchedule;

import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ProcessScheduleTable extends Table {
    
    private final JTable table;
    private final Configurations config;
    private final String[] columnNames = new String[] {
        "Process No.", "AT", "BT", "CT", "TAT", "WT"};
    private final String[] columnNamesWithPriority = new String[] {
        "Process No.",  "AT", "BT", "Priority", "CT", "TAT", "WT"};
    private enum Column {
        PROCESS_NUMBER, AT, BT, PRIORITY;
    }
    
    public ProcessScheduleTable(JTable table, Configurations config, int rowCount) {
        super(table, rowCount);
        this.table = table;
        this.config = config;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return !(column == 0 
                || column == this.getColumnCount() - 1 
                || column == this.getColumnCount() - 2 
                || column == this.getColumnCount() - 3);
    }
    
    public int getProcessNumberColumn() {
        return Column.PROCESS_NUMBER.ordinal();
    }

    public int getATColumn() {
        return Column.AT.ordinal();
    }

    public int getBTColumn() {
        return Column.BT.ordinal();
    }

    public int getPriorityColumn() {
        return Column.PRIORITY.ordinal();
    }
    
    public int getInputColumnCount() {
        return config.isPriority()? 4 : 3;
    }
    
    public void initTable() {
        if(config.isPriority()) {
            super.setModel(columnNamesWithPriority.length); 
            super.setHeader(columnNamesWithPriority);
        } else {
            super.setModel(columnNames.length); 
            super.setHeader(columnNames);
        }        
        update();
        setProcessNumber();
    }
    
    @Override
    public void clear() {
        super.clear();
        setProcessNumber();
    }
    
    @Override
    public void update() {
        super.update();
        table.getTableHeader().setReorderingAllowed(false);
        super.centerHorizontalAlignment();
        super.clear(); 
    }
    
    public void setProcessNumber() {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(i + 1, i, 0);
        }
    }        
    
    public void insert(String string) {                
        StringTokenizer stringTokenizer = new StringTokenizer(string, "\n");
        String rowString, value;

        for(int i = 0; stringTokenizer.hasMoreTokens(); i++) {
            rowString = stringTokenizer.nextToken();                
            StringTokenizer stringTokenizer2 = new StringTokenizer(rowString, "\t"); 
            for(int j = 0; stringTokenizer2.hasMoreTokens(); j++) {
                value = (String)stringTokenizer2.nextToken();                
                if(i < table.getRowCount() && j < table.getColumnCount()) {
                    if(j == 3 && table.getColumnCount() != 7) {
                        value = (String)stringTokenizer2.nextToken();
                    }
                    table.setValueAt(value.trim(), i, j);
                }                    
            }
        }
    }
    
    public void sort() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.table.getModel());
        LinkedList<RowSorter.SortKey> sortKeys = new LinkedList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);
    }
    
    public void fixValues() {
        int columnLimit = table.getColumnCount() - 3;
        
        for(int i = 1; i < columnLimit; i++) {
            for(int j = 0; j < table.getRowCount(); j++) {                
                String value_str = String.valueOf(table.getValueAt(j, i));
                if(table.getColumnCount() == 7 && i == Column.PRIORITY.ordinal()) {                    
                    int value = Integer.parseInt(replaceZeroAndEmptyWith(value_str, "1"));
                    table.setValueAt(value, j, i);
                } else if(i == Column.BT.ordinal()) {
                    double value = Double.parseDouble(replaceZeroAndEmptyWith(value_str, "1.0"));
                    table.setValueAt(value, j, i);
                } else {
                    double value = Double.parseDouble(removeAllNonNumeric(value_str));
                    table.setValueAt(value, j, i);
                }             
            }
        }
    }
    
    public String replaceZeroAndEmptyWith(String str, String target) {        
        if (str.trim().equals("0") || str.trim().equals("")) {
            return target;
        }
        return str;
    }
           
    public void replaceNullWithEmpty() {
        for(int i = 0; i < table.getColumnCount(); i++) {
            for(int j = 0; j < table.getRowCount(); j++) {
                if(table.getValueAt(j, i) != null && table.getValueAt(j, i).equals("null")) {
                    table.setValueAt("", j, i);
                }
            }
        }
    }
    
    public String removeAllNonNumeric(String str) {
        if (str.equals("")) {
            return "0";
        }
        return str.trim().replaceAll("[^\\d.]", "");
    }
}
