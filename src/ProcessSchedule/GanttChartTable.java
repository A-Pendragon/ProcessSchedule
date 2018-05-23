package ProcessSchedule;

import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GanttChartTable extends Table {
    
    private final DefaultTableModel model = new DefaultTableModel();    
    private final LinkedList<Process> processSchedule;
    private final int MAX_COLUMN_FIT = 8;
    
    public GanttChartTable(JTable table, int rowCount, LinkedList<Process> processSchedule) {
        super(table, rowCount);
        this.processSchedule = processSchedule;
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        if (processSchedule.size() > MAX_COLUMN_FIT){
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }
    
    @Override
    public DefaultTableModel getModel() {
        return model;
    }
    
    public void showProcessSchedule() {                
        for (int i = 0; i < processSchedule.size(); i++){
            model.addColumn("P" + processSchedule.get(i).getProcessNo(), 
                    new String[]{Double.toString(processSchedule.get(i).getBurstTime())});
        }
    }
}
