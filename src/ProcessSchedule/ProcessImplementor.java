package ProcessSchedule;

import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ProcessImplementor implements ProcessImplementation {
        
    private final JTable table;        
    private final Configurations config;
    private final JLabel typeLabel;
    private final JLabel countLabel;
    private final JLabel modeLabel;
    private final JLabel criterionLabel;
    private final JLabel timeQuantumJLabel;
    private final JTextField timeQuantumTextField;
    private ProcessScheduleTable processScheduleTable;
    private String processType;
    private int rowCount;
    
    public ProcessImplementor(JTable table, Configurations config, JLabel typeLabel, 
            JLabel countLabel, JLabel modeLabel, JLabel criterionLabel, 
            JLabel timeQuantumJLabel, JTextField timeQuantumTextField) {        
        this.table = table;
        this.config = config;
        this.typeLabel = typeLabel;
        this.countLabel = countLabel;
        this.modeLabel = modeLabel;
        this.criterionLabel = criterionLabel;
        this.timeQuantumJLabel = timeQuantumJLabel;
        this.timeQuantumTextField = timeQuantumTextField;
    }
    
    public double getTimeQuantum() {
        String timeQuantum_str = timeQuantumTextField.getText();
        
        if(timeQuantum_str.trim().isEmpty()) {
            timeQuantumTextField.setText("1");
            return 1;
        }
        return Double.parseDouble(timeQuantumTextField.getText());
    }
    
    public ProcessScheduleTable getProcessScheduleTable() {
        return processScheduleTable;
    }
    
    public void implement() {
        processType = config.getType();
        rowCount = config.getCount();
        processScheduleTable = new ProcessScheduleTable(table, config, rowCount);
        processScheduleTable.initTable();
        setLabels();
        setTimeQuantumUI(config.isRoundRobin());
    }
        
    private void setLabels() {
        typeLabel.setText(processType);
        countLabel.setText(Integer.toString(rowCount));
        switch (processType) {
            case "First Come First Serve":
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Arrival time (AT)");
                break;
            case "Shortest Job First":
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Burst time (BT)");
                break;
            case "Shortest Remaining Time First":
                modeLabel.setText("Preemptive");
                criterionLabel.setText("Burst time (BT)");
                break;
            case "Non-Preemptive Priority":
                modeLabel.setText("Non-preemptive");
                criterionLabel.setText("Priority");
                break;
            case "Preemptive Priority":
                modeLabel.setText("Preemptive");
                criterionLabel.setText("Priority");
                break;
            case "Round Robin":
                modeLabel.setText("Preemptive");
                criterionLabel.setText("Arrival time (AT)");
                break;
            default: break;
        }
    }        
    
    private void setTimeQuantumUI(boolean isRoundRobin) {
        if(isRoundRobin) {
            timeQuantumJLabel.setEnabled(true);
            timeQuantumTextField.setEnabled(true);
        } else {
            timeQuantumJLabel.setEnabled(false);
            timeQuantumTextField.setEnabled(false);
        }
    }
    
    public void resetInfo() {
        typeLabel.setText("");
        countLabel.setText("");
        modeLabel.setText("");
        criterionLabel.setText("");
        timeQuantumJLabel.setEnabled(false);
        timeQuantumTextField.setText("");
        timeQuantumTextField.setEnabled(false);
    }
    
    @Override
    public LinkedList<Process> addValuesToProcess() {
        LinkedList<Process> plist = new LinkedList<>();
        if(config.isPriority()) {
            for (int i = 0; i < table.getRowCount(); i++) { 
                plist.add(new Process(
                        Integer.parseInt(processScheduleTable.getValueAt(i, 0).toString()),
                        Double.parseDouble(processScheduleTable.getValueAt(i, 1).toString()),
                        Double.parseDouble(processScheduleTable.getValueAt(i, 2).toString()),
                        Integer.parseInt(processScheduleTable.getValueAt(i, 3).toString())));
            }
        } else {
            for (int i = 0; i < table.getRowCount(); i++) {
                plist.add(new Process(
                        Integer.parseInt(processScheduleTable.getValueAt(i, 0).toString()),
                        Double.parseDouble(processScheduleTable.getValueAt(i, 1).toString()),
                        Double.parseDouble(processScheduleTable.getValueAt(i, 2).toString())));
            }
        }
        return plist;
    }
    
    @Override
    public void doProcessOperation(ProcessOperation po) {
        switch (processType) {
            case "First Come First Serve":
                po.nonPreemptiveSchedule("firstcomefirstserve");
                break;
            case "Shortest Job First":
                po.nonPreemptiveSchedule("shortestjobfirst");
                break;
            case "Non-Preemptive Priority":
                po.nonPreemptiveSchedule("nppriority");
                break;
            case "Shortest Remaining Time First":
                po.preemptiveSchedule("shortestremainingtime");
                break;
            case "Preemptive Priority":
                po.preemptiveSchedule("ppriority");
                break;
            default:
                break;
        }
    }
    
    @Override
    public void doProcessOperation(ProcessOperation po, double timeQuantum) {
        po.roundRobinSchedule(timeQuantum);
    }
}
