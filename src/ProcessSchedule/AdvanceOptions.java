package ProcessSchedule;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AdvanceOptions {
    
    private final Configurations config;
    private final JLabel atLabel;
    private final JLabel btLabel;
    private final JLabel atFirstLabel;
    private final JLabel atSecondLabel;
    private final JLabel btFirstLabel;
    private final JLabel btSecondLabel;
    private final JComboBox atComboBox;
    private final JComboBox btComboBox; 
    private final JTextField atFirstTextField;
    private final JTextField atSecondTextField;
    private final JTextField btFirstTextField;
    private final JTextField btSecondTextField;
    private final JLabel priorityLabel;
    private final JComboBox priorityComboBox;
    private final JButton aoButton;
    
    public AdvanceOptions(Configurations config, JLabel atLabel, JLabel btLabel, 
            JLabel atFirstLabel, JLabel atSecondLabel, JLabel btFirstLabel, 
            JLabel btSecondLabel, JComboBox atComboBox, JComboBox btComboBox, 
            JTextField atFirstTextField, JTextField atSecondTextField, 
            JTextField btFirstTextField, JTextField btSecondTextField, 
            JLabel priorityLabel, JComboBox priorityComboBox, JButton aoButton) {
        this.config = config;
        this.atLabel = atLabel;
        this.btLabel = btLabel;
        this.atFirstLabel = atFirstLabel;
        this.atSecondLabel = atSecondLabel;
        this.btFirstLabel = btFirstLabel;
        this.btSecondLabel = btSecondLabel;
        this.atComboBox = atComboBox;
        this.btComboBox = btComboBox;  
        this.atFirstTextField = atFirstTextField;
        this.atSecondTextField = atSecondTextField;
        this.btFirstTextField = btFirstTextField;
        this.btSecondTextField = btSecondTextField;
        this.priorityLabel = priorityLabel;
        this.priorityComboBox = priorityComboBox;
        this.aoButton = aoButton;
    }        
    
    public void setInitOptionsEnabled(boolean enabled) {
        atLabel.setEnabled(enabled);
        btLabel.setEnabled(enabled);
        atComboBox.setEnabled(enabled);
        btComboBox.setEnabled(enabled);
        atComboBox.setSelectedIndex(0);
        btComboBox.setSelectedIndex(0);
        if (enabled) {
            priorityLabel.setEnabled(config.isPriority());
            priorityComboBox.setEnabled(config.isPriority());
        } else {
            priorityLabel.setEnabled(false);
            priorityComboBox.setEnabled(false);
        }
        aoButton.setEnabled(enabled);
    }
    
    public void setATInputEnabled(boolean bool) {
        atFirstLabel.setEnabled(bool);        
        atSecondLabel.setEnabled(bool);
        atFirstTextField.setEnabled(bool);
        atSecondTextField.setEnabled(bool);
        atFirstTextField.setText("");
        atSecondTextField.setText("");
    }
    
    public void setBTInputEnabled(boolean bool) {
        btFirstLabel.setEnabled(bool);        
        btSecondLabel.setEnabled(bool);
        btFirstTextField.setEnabled(bool);
        btSecondTextField.setEnabled(bool);
        btFirstTextField.setText("");
        btSecondTextField.setText("");
    }
        
    public double[] getATResult(int rowCount) {        
        int atIndex = atComboBox.getSelectedIndex();
        
        switch (atIndex) {
            case 1: return rangeStep(rowCount, 
                    textFieldValue(atFirstTextField), 
                    textFieldValue(atSecondTextField));
            case 2: return rangeStep(rowCount, 
                    textFieldValue(atFirstTextField), 
                    -textFieldValue(atSecondTextField));
            case 3: return NumericHandlers.randomArray(rowCount, 
                    textFieldValue(atFirstTextField), 
                    textFieldValue(atSecondTextField));
            default: break;
        }
        return null;
    }
    
    public double[] getBTResult(int rowCount) {
        int btIndex = btComboBox.getSelectedIndex();

        switch (btIndex) {
            case 1: return rangeStep(rowCount, 
                    textFieldValue(btFirstTextField), 
                    textFieldValue(btSecondTextField));
            case 2: return rangeStep(rowCount, 
                    textFieldValue(btFirstTextField), 
                    -textFieldValue(btSecondTextField));
            case 3: return NumericHandlers.randomArray(rowCount, 
                    textFieldValue(btFirstTextField), 
                    textFieldValue(btSecondTextField));
            default: break;
        }
        return null;
    }
    
    public int[] getPriorityResult(int rowCount) {
        int priorityIndex = priorityComboBox.getSelectedIndex();
        
        switch (priorityIndex) {
            case 1: return rangeStep(rowCount, 1, 1);
            case 2: return rangeStep(rowCount, rowCount, -1);
            case 3: return NumericHandlers.randomUniqueArray(1, rowCount);
            default: break;
        }
        return null;
    }
    
    private double textFieldValue(JTextField textField) {
        return (textField.getText().isEmpty())? 0 : 
                Double.parseDouble(NumericHandlers.removeExcessDecimalPoint(textField.getText()));
    }
    
    private int[] rangeStep(int size, int from, int step) {
        int[] array = new int[size];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = from + (i * step);
        }
        return array;
    }
    
    private double[] rangeStep(int size, double from, double step) {
        double[] array = new double[size];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = NumericHandlers.round(from + (i * step), 2);
        }        
        return array;
    }
}
