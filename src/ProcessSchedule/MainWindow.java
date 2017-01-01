/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProcessSchedule;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Note: Functions are refactored to become static member of other classes 
 *       since the main application's code has become too long and may be hard to maintain.
 *       If possible, the main window's events and initializers should go here; otherwise, to other classes.
 * @author Pendoragon
 */
public class MainWindow extends javax.swing.JFrame {
    public final int MAX_COLUMN = 8;
    public String selectedProcessType;
    public JTable previousTable;
    public DefaultTableModel ganttChartModel;   
    
    public MainWindow() {
        initComponents();
        initJFrame();
        initGlobalVariables();
        initListeners();
        this.ganttChartModel = new DefaultTableModel();
        this.ganttChart.setModel(ganttChartModel);
       
    }
    
    //<editor-fold defaultstate="collapsed" desc="Global Variables Initializations">
    private void initGlobalVariables() {
        selectedProcessType = (String)type_comboBox.getSelectedItem();
        previousTable = table;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="JFrame Initializations">
    /**
     * Initializations of the JFrame and its elements
     */
    private void initJFrame() {
        // Width is always + 10 and height is always + 35, because of some supernatural phenomenon. 
        // Just do it or you'll be hunted by the blonde spirit knight girl.
        this.setSize(930, 575);
        this.setResizable(false); // Disable maximize button
        
        // Set location of JFrame to appear in the center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));
                
        groupButton(); // Groups the Theme radio buttons in the Menu bar (View > Theme). Since they are radio buttons
        
        TableHandlers.centerTableHorizontalAlignment(table);
        TableHandlers.setGanttChart(ganttChart);
        
        setComponentsToDefault();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sets AT to enabled/disabled">
    private void atSetEnabled(boolean bool) {
        at_first_label.setEnabled(bool);
        at_first_textField.setEnabled(bool);
        at_second_label.setEnabled(bool);
        at_second_textField.setEnabled(bool);
        at_first_textField.setText("");
        at_second_textField.setText("");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sets BT to enabled/disabled">
    private void btSetEnabled(boolean bool) {
        bt_first_label.setEnabled(bool);
        bt_first_textField.setEnabled(bool);
        bt_second_label.setEnabled(bool);
        bt_second_textField.setEnabled(bool);
        bt_first_textField.setText("");
        bt_second_textField.setText("");
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Listeners Initialization">    
    private void initListeners() {                
        // Add action listeners to the at combo box for the labels in the advanced options.
        // The label will change in accordance to the selected item.         
        at_comboBox.addActionListener((ActionEvent event) -> {
            int selectedIndex = at_comboBox.getSelectedIndex();  
                        
            if(selectedIndex == 0) {
                atSetEnabled(false);
            } else if(selectedIndex == 1) {
                atSetEnabled(true);
                at_first_textField.setText("1");
                at_second_textField.setText("1");
            } else if(selectedIndex == 2) {
                atSetEnabled(true);
                at_first_textField.setText(String.valueOf(table.getRowCount()));
                at_second_textField.setText("1");
            } else if(selectedIndex == 3) {
                atSetEnabled(true);
                at_first_textField.setText("1");
                at_second_textField.setText(String.valueOf(table.getRowCount()));
            }
                        
            // Sets the label of at
            if(selectedIndex == 3) {                
                at_first_label.setText("Min");
                at_second_label.setText("Max");                
            } else {
                at_first_label.setText("Start");
                at_second_label.setText("Step");                
            }  
        });
        
        // Add action listeners to the bt combo box for the labels in the advanced options.
        // The label will change in accordance to the selected item.
        bt_comboBox.addActionListener((ActionEvent event) -> {
            int selectedIndex = bt_comboBox.getSelectedIndex();
            
            if(selectedIndex == 0) {
                btSetEnabled(false);
            } else if(selectedIndex == 1) {
                btSetEnabled(true);
                bt_first_textField.setText("1");
                bt_second_textField.setText("1");
            } else if(selectedIndex == 2) {
                btSetEnabled(true);
                bt_first_textField.setText(String.valueOf(table.getRowCount()));
                bt_second_textField.setText("1");
            } else if(selectedIndex == 3) {
                btSetEnabled(true);
                bt_first_textField.setText("1");
                bt_second_textField.setText(String.valueOf(table.getRowCount()));
            }
                        
            // Sets the label of bt
            if(selectedIndex == 3) {
                bt_first_label.setText("Min");
                bt_second_label.setText("Max");
            } else {
                bt_first_label.setText("Start");
                bt_second_label.setText("Step");
            }    
            
        });
        
        //<editor-fold defaultstate="collapsed" desc="Application Themes">
        // Event Listener for acryl in menu bar > view > theme    
        theme_Acryl.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for aero in menu bar > view > theme
        theme_Aero.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for aluminium in menu bar > view > theme
        theme_Aluminium.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for bernstein in menu bar > view > theme
        theme_Bernstein.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for fast in menu bar > view > theme
        theme_Fast.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for hifi in menu bar > view > theme
        theme_HiFi.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for mcwin in menu bar > view > theme
        theme_McWin.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for mint in menu bar > view > theme
        theme_Mint.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for noire in menu bar > view > theme
        theme_Noire.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for smart in menu bar > view > theme
        theme_Smart.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for luna in menu bar > view > theme
        theme_Luna.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Event Listener for texture in menu bar > view > theme
        theme_Texture.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }); 
        //</editor-fold>
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Theme Buttons">
    /**
     * Groups the Theme radio buttons in the Menu bar (View > Theme).
     */
    private void groupButton() {
        ButtonGroup bgTheme = new ButtonGroup();
        bgTheme.add(theme_Acryl);
        bgTheme.add(theme_Aero);
        bgTheme.add(theme_Aluminium);
        bgTheme.add(theme_Bernstein);
        bgTheme.add(theme_Fast);
        bgTheme.add(theme_HiFi);
        bgTheme.add(theme_McWin);
        bgTheme.add(theme_Mint);
        bgTheme.add(theme_Noire);
        bgTheme.add(theme_Smart);
        bgTheme.add(theme_Luna);
        bgTheme.add(theme_Texture);
                        
        theme_Aero.setSelected(true); // Set default theme (Aero) to selected (See main).                    
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sets Components to Default State">
    private void setComponentsToDefault() {
        // Initialize dynamic labels to ""
        type_label_text.setText("");
        count_label_text.setText("");
        mode_label_text.setText("");
        criterion_label_text.setText("");
        
        // Set the default selected index of type and count combo boxes to 0
        type_comboBox.setSelectedIndex(0);
        count_comboBox.setSelectedIndex(0);
        
        // Set the default selected index of at, bt, and priority combo boxes to 0
        at_comboBox.setSelectedIndex(0);
        bt_comboBox.setSelectedIndex(0);
        priority_comboBox.setSelectedIndex(0);                
        
        // Initialize the Advance Options label 
        at_label.setEnabled(false);
        bt_label.setEnabled(false);
        priority_label.setEnabled(false);
        
        // at and bt labels are initialized to disabled since the default element in their combo box is none.
        at_first_label.setEnabled(false);        
        at_second_label.setEnabled(false);                
        bt_first_label.setEnabled(false);        
        bt_second_label.setEnabled(false);
                
        // at and bt textfields are initialized to empty and disabled since the default element in their combo box is none.
        at_first_textField.setText("");
        at_second_textField.setText("");
        bt_first_textField.setText("");
        bt_second_textField.setText("");
        at_first_textField.setEnabled(false);
        at_second_textField.setEnabled(false);
        bt_first_textField.setEnabled(false);
        bt_second_textField.setEnabled(false);                
        
        // Set the at, bt, and priority to disabled as default
        at_comboBox.setEnabled(false);
        bt_comboBox.setEnabled(false);
        priority_comboBox.setEnabled(false);
        
        // Set the time quantum label to diabled and textbox to "" and disabled as default.        
        time_quantum_textBox.setText("");
        time_quantum_label.setEnabled(false);
        time_quantum_textBox.setEnabled(false);                
        
        // Set the default of 
        average_wt.setText("");
        average_tat.setText("");
        
        table.setModel(new DefaultTableModel(0, 0)); // Sets a null table as a default
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Advance Options Handler">
    private void advanceOptionsHandler() {
        int at_index = at_comboBox.getSelectedIndex();
        int bt_index = bt_comboBox.getSelectedIndex();
        int priority_index = priority_comboBox.getSelectedIndex();
        
        // Remove all non numeric characters and spaces        
        String at_first_str = NumericHandlers.removeAllNonNumeric(at_first_textField.getText());
        String at_second_str = NumericHandlers.removeAllNonNumeric(at_second_textField.getText());
        String bt_first_str = NumericHandlers.removeAllNonNumeric(bt_first_textField.getText());
        String bt_second_str = NumericHandlers.removeAllNonNumeric(bt_second_textField.getText());        
        
        // Remove all 0 (If user intentionally inputted a 0, although the default is 1) with 1 in the burst time inputs
        bt_first_str = NumericHandlers.replaceZeroAndEmptyWith(bt_first_str, "1");
        
        // Update the text fields
        at_first_textField.setText(at_first_str);
        at_second_textField.setText(at_second_str);
        bt_first_textField.setText(bt_first_str);
        bt_second_textField.setText(bt_second_str);
        
        // Convert string to double (this is done in the try catch below)
        double at_first = 0;
        double at_second = 0;
        double bt_first = 0;
        double bt_second = 0;
        
        // Try if the input is valid for conversion to double, catch sets the text field to "" then sets error to true to stop the method from continuing.   
        try { 
            at_first = Double.parseDouble(at_first_str); 
        } catch (NumberFormatException ex) { 
            at_first_textField.setText(""); 
        }
        try { 
            at_second = Double.parseDouble(at_second_str); 
        } catch (NumberFormatException ex) { 
            at_second_textField.setText(""); 
        } 
        try { 
            bt_first = Double.parseDouble(bt_first_str); 
        } catch (NumberFormatException ex) {
            bt_first_textField.setText(""); 
        }                       
        try {
            bt_second = Double.parseDouble(bt_second_str); 
        } catch (NumberFormatException ex) {
            bt_second_textField.setText(""); 
        }                                
        
        /**
         * case 0: none
         * case 1: ascending
         * case 2: descending
         * case 3: randomize
         */
        
        switch(at_index) {
            case 0:
                // do nothing
                break;
            case 1:
                ColumnHandlers.setColumnToAscendingDouble(table, 1, at_first, at_second);
                break;
            case 2:
                ColumnHandlers.setColumnToDescendingDouble(table, 1, at_first, at_second);
                break;
            case 3:                
                ColumnHandlers.setColumnToRandomizeDouble(table, 1, at_first, at_second);
                break;
            default:
                break;
        }                   
        
        switch(bt_index) {
            case 0:
                // do nothing
                break;
            case 1:
                ColumnHandlers.setColumnToAscendingDouble(table, 2, bt_first, bt_second);
                break;
            case 2:
                ColumnHandlers.setColumnToDescendingDouble(table, 2, bt_first, bt_second);
                break;
            case 3:                
                ColumnHandlers.setColumnToRandomizeDouble(table, 2, bt_first, bt_second);
                break;
            default:
                break;
        }    
        
        switch(priority_index) {
            case 0:
                // do nothing
                break;
            case 1:
                ColumnHandlers.setColumnToAscendingInt(table, 3, table.getRowCount());
                break;
            case 2:
                ColumnHandlers.setColumnToDescendingInt(table, 3, table.getRowCount());
                break;
            case 3:                
                ColumnHandlers.setColumnToRandomizeInt(table, 3, 0, table.getRowCount());
                break;
            default:
                break;
        }                
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Configurations Handler">
    private void configurationsHandler() {
        at_label.setEnabled(true);
        bt_label.setEnabled(true);
        at_comboBox.setEnabled(true);
        bt_comboBox.setEnabled(true);
        
        selectedProcessType = (String)type_comboBox.getSelectedItem();
        int row_count = Integer.valueOf((String)count_comboBox.getSelectedItem());
        
        TableHandlers.setTable(selectedProcessType, row_count, table);
        TableHandlers.setLabels(selectedProcessType, type_label_text, count_label_text, mode_label_text, criterion_label_text, type_comboBox, count_comboBox);
                
        // Sets the priority label and combo box to enabled or disabled depending on the selected type of process.
        if(selectedProcessType.equals("Non-Preemptive Priority") || selectedProcessType.equals("Preemptive Priority")) {
            priority_label.setEnabled(true);
            priority_comboBox.setEnabled(true);
        } else {
            priority_label.setEnabled(false);
            priority_comboBox.setEnabled(false);
        }
        
        if(selectedProcessType.equals("Round Robin")) {
            time_quantum_label.setEnabled(true);
            time_quantum_textBox.setEnabled(true);
        } else {
            time_quantum_label.setEnabled(false);
            time_quantum_textBox.setEnabled(false);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Randomizes All Inputs">
    private void randomizeAllInputs() {
        at_label.setEnabled(true);
        bt_label.setEnabled(true);
        at_comboBox.setEnabled(true);
        bt_comboBox.setEnabled(true);
        
        // Sets index of the combo boxes to a random number between 0 to max index.
        type_comboBox.setSelectedIndex(NumericHandlers.randomRange(0, type_comboBox.getItemCount()));
        count_comboBox.setSelectedIndex(NumericHandlers.randomRange(0, count_comboBox.getItemCount()));                 
        
        configurationsHandler();
        
        // Randomize the 3 Columns
        ColumnHandlers.setColumnToRandomizeDouble(table, 1, 1, 10);
        ColumnHandlers.setColumnToRandomizeDouble(table, 2, 1, 10);
        
        if(type_comboBox.getSelectedIndex() == 3 || type_comboBox.getSelectedIndex() == 4) {
            ColumnHandlers.setColumnToRandomizeInt(table, 3, 0, table.getRowCount());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Time Quantum Handler">
    private double timeQuantumHandler() {  
        String timeQuantum_str = time_quantum_textBox.getText();        
        if(timeQuantum_str.trim().isEmpty()) {
            time_quantum_textBox.setText("1");
            return 1;
        } 
        return Double.parseDouble(time_quantum_textBox.getText());
    }
    //</editor-fold>
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        type_comboBox = new javax.swing.JComboBox<>();
        config_type_label = new javax.swing.JLabel();
        config_label = new javax.swing.JLabel();
        priority_comboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0 ||
                    column == this.getColumnCount()-1 ||
                    column == this.getColumnCount()-2 ||
                    column == this.getColumnCount()-3);
            };
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        ganttChart = new javax.swing.JTable();
        computeButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        average_wt = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        ao_button = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        config_button = new javax.swing.JButton();
        config_count_label = new javax.swing.JLabel();
        priority_label = new javax.swing.JLabel();
        at_label = new javax.swing.JLabel();
        count_comboBox = new javax.swing.JComboBox<>();
        at_comboBox = new javax.swing.JComboBox<>();
        bt_comboBox = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        toolBar = new javax.swing.JToolBar();
        toolbar_reset = new javax.swing.JButton();
        toolbar_randomize = new javax.swing.JButton();
        toolbar_clear_table = new javax.swing.JButton();
        toolbar_shuffle_AT = new javax.swing.JButton();
        toolbar_shuffle_BT = new javax.swing.JButton();
        toolbar_shuffle_P = new javax.swing.JButton();
        toolbar_table_copy = new javax.swing.JButton();
        toolbar_table_paste = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        mode_label = new javax.swing.JLabel();
        count_label = new javax.swing.JLabel();
        criterion_label = new javax.swing.JLabel();
        type_label = new javax.swing.JLabel();
        criterion_label_text = new javax.swing.JLabel();
        count_label_text = new javax.swing.JLabel();
        type_label_text = new javax.swing.JLabel();
        at_second_textField = new javax.swing.JTextField();
        bt_second_textField = new javax.swing.JTextField();
        at_first_label = new javax.swing.JLabel();
        at_first_textField = new javax.swing.JTextField();
        bt_first_textField = new javax.swing.JTextField();
        at_second_label = new javax.swing.JLabel();
        bt_label = new javax.swing.JLabel();
        bt_first_label = new javax.swing.JLabel();
        bt_second_label = new javax.swing.JLabel();
        mode_label_text = new javax.swing.JLabel();
        time_quantum_label = new javax.swing.JLabel();
        time_quantum_textBox = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        average_tat = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        Menu_File = new javax.swing.JMenu();
        Menu_File_Exit = new javax.swing.JMenuItem();
        Menu_Edit = new javax.swing.JMenu();
        Menu_Edit_Reset = new javax.swing.JMenuItem();
        Menu_Edit_Clear = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        Menu_Edit_Randomize = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        Menu_Edit_Shuffle_AT = new javax.swing.JMenuItem();
        Menu_Edit_Shuffle_BT = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        theme = new javax.swing.JMenu();
        theme_Acryl = new javax.swing.JRadioButtonMenuItem();
        theme_Aero = new javax.swing.JRadioButtonMenuItem();
        theme_Aluminium = new javax.swing.JRadioButtonMenuItem();
        theme_Bernstein = new javax.swing.JRadioButtonMenuItem();
        theme_Fast = new javax.swing.JRadioButtonMenuItem();
        theme_HiFi = new javax.swing.JRadioButtonMenuItem();
        theme_McWin = new javax.swing.JRadioButtonMenuItem();
        theme_Mint = new javax.swing.JRadioButtonMenuItem();
        theme_Noire = new javax.swing.JRadioButtonMenuItem();
        theme_Smart = new javax.swing.JRadioButtonMenuItem();
        theme_Luna = new javax.swing.JRadioButtonMenuItem();
        theme_Texture = new javax.swing.JRadioButtonMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Process Scheduling");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 450));
        setSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        type_comboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        type_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Non-Preemptive Priority", "Preemptive Priority", "Round Robin" }));
        getContentPane().add(type_comboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 200, 32));

        config_type_label.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        config_type_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        config_type_label.setText("Type:");
        getContentPane().add(config_type_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 40, 30));

        config_label.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        config_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        config_label.setText("Configurations");
        config_label.setOpaque(true);
        getContentPane().add(config_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 280, 30));

        priority_comboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        priority_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(priority_comboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 200, 32));

        table.setAutoCreateRowSorter(true);
        table.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Process No.", "AT", "BT", "Priority", "CT", "TAT", "WT"
            }
        ));
        table.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 600, 170));

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        ganttChart.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ganttChart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ganttChart.setEnabled(false);
        ganttChart.setRowHeight(20);
        jScrollPane2.setViewportView(ganttChart);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 600, 60));

        computeButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        computeButton.setText("Compute");
        computeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(computeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, 80, 32));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Average WT =");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, -1, -1));

        average_wt.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        average_wt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        average_wt.setText("answer");
        getContentPane().add(average_wt, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 460, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 280, 10));

        ao_button.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ao_button.setText("Apply");
        ao_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ao_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(ao_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 70, 32));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 920, 10));

        config_button.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        config_button.setText("Apply");
        config_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                config_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(config_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 70, 32));

        config_count_label.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        config_count_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        config_count_label.setText("Count:");
        getContentPane().add(config_count_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 40, 30));

        priority_label.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        priority_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        priority_label.setText("Priority:");
        getContentPane().add(priority_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 40, 30));

        at_label.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        at_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        at_label.setText("AT:");
        getContentPane().add(at_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 40, 30));

        count_comboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        count_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
        getContentPane().add(count_comboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 200, 32));

        at_comboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        at_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(at_comboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 100, 32));

        bt_comboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        bt_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(bt_comboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 100, 32));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 10, 470));

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        toolbar_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/reset_24.png"))); // NOI18N
        toolbar_reset.setToolTipText("Reset All");
        toolbar_reset.setFocusable(false);
        toolbar_reset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_reset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_resetActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_reset);

        toolbar_randomize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/dice_24.png"))); // NOI18N
        toolbar_randomize.setToolTipText("Randomize All Inputs");
        toolbar_randomize.setFocusable(false);
        toolbar_randomize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_randomize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_randomize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_randomizeActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_randomize);

        toolbar_clear_table.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/empty_table_24.png"))); // NOI18N
        toolbar_clear_table.setToolTipText("Clear Table");
        toolbar_clear_table.setFocusable(false);
        toolbar_clear_table.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_clear_table.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_clear_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_clear_tableActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_clear_table);

        toolbar_shuffle_AT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/shuffle_AT_24.png"))); // NOI18N
        toolbar_shuffle_AT.setToolTipText("Shuffle Arrival Time (AT)");
        toolbar_shuffle_AT.setFocusable(false);
        toolbar_shuffle_AT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_shuffle_AT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_shuffle_AT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_shuffle_ATActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_shuffle_AT);

        toolbar_shuffle_BT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/shuffle_BT_24.png"))); // NOI18N
        toolbar_shuffle_BT.setToolTipText("Shuffle Burst Time (BT)");
        toolbar_shuffle_BT.setFocusable(false);
        toolbar_shuffle_BT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_shuffle_BT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_shuffle_BT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_shuffle_BTActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_shuffle_BT);

        toolbar_shuffle_P.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/shuffle_P_24.png"))); // NOI18N
        toolbar_shuffle_P.setToolTipText("Shuffle Priority");
        toolbar_shuffle_P.setFocusable(false);
        toolbar_shuffle_P.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_shuffle_P.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_shuffle_P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_shuffle_PActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_shuffle_P);

        toolbar_table_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/table_copy_24.png"))); // NOI18N
        toolbar_table_copy.setToolTipText("Copy Table Input Values");
        toolbar_table_copy.setFocusable(false);
        toolbar_table_copy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_table_copy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_table_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_table_copyActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_table_copy);

        toolbar_table_paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/table_paste_24.png"))); // NOI18N
        toolbar_table_paste.setToolTipText("Paste Into Table");
        toolbar_table_paste.setFocusable(false);
        toolbar_table_paste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolbar_table_paste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar_table_paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbar_table_pasteActionPerformed(evt);
            }
        });
        toolBar.add(toolbar_table_paste);

        getContentPane().add(toolBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 30));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Advance Options");
        jLabel7.setOpaque(true);
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 243, 280, 30));

        mode_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        mode_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mode_label.setText("Mode:");
        getContentPane().add(mode_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 50, -1));

        count_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        count_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        count_label.setText("Count:");
        getContentPane().add(count_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 40, -1));

        criterion_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        criterion_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        criterion_label.setText("Criterion:");
        getContentPane().add(criterion_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 50, -1));

        type_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        type_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        type_label.setText("Type:");
        getContentPane().add(type_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 40, -1));

        criterion_label_text.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        criterion_label_text.setText("text\n");
        getContentPane().add(criterion_label_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, -1, -1));

        count_label_text.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        count_label_text.setText("text\n");
        getContentPane().add(count_label_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

        type_label_text.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        type_label_text.setText("text ");
        getContentPane().add(type_label_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        at_second_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(at_second_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 40, 32));

        bt_second_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(bt_second_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 40, 32));

        at_first_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        at_first_label.setText("Start");
        getContentPane().add(at_first_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, 20));

        at_first_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(at_first_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 40, 32));

        bt_first_textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(bt_first_textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 40, 32));

        at_second_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        at_second_label.setText("Step");
        getContentPane().add(at_second_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, 20));

        bt_label.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bt_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bt_label.setText("BT:");
        getContentPane().add(bt_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 40, 30));

        bt_first_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        bt_first_label.setText("Start");
        getContentPane().add(bt_first_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, -1, 20));

        bt_second_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        bt_second_label.setText("Step");
        getContentPane().add(bt_second_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, -1, 20));

        mode_label_text.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        mode_label_text.setText("text\n");
        getContentPane().add(mode_label_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, -1, -1));

        time_quantum_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        time_quantum_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        time_quantum_label.setText("Time Quantum:");
        getContentPane().add(time_quantum_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 90, -1));

        time_quantum_textBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(time_quantum_textBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 70, 50, -1));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 273, 280, 10));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 280, 10));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Average TAT =");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 460, -1, -1));

        average_tat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        average_tat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        average_tat.setText("answer");
        getContentPane().add(average_tat, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 460, -1, -1));

        Menu_File.setText("File");
        Menu_File.setToolTipText("");

        Menu_File_Exit.setText("Exit");
        Menu_File_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_File_ExitActionPerformed(evt);
            }
        });
        Menu_File.add(Menu_File_Exit);

        Menu.add(Menu_File);

        Menu_Edit.setText("Edit");

        Menu_Edit_Reset.setText("Reset");
        Menu_Edit_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_Edit_ResetActionPerformed(evt);
            }
        });
        Menu_Edit.add(Menu_Edit_Reset);

        Menu_Edit_Clear.setText("Clear Table");
        Menu_Edit_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_Edit_ClearActionPerformed(evt);
            }
        });
        Menu_Edit.add(Menu_Edit_Clear);
        Menu_Edit.add(jSeparator4);

        Menu_Edit_Randomize.setText("Randomize All Inputs");
        Menu_Edit_Randomize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_Edit_RandomizeActionPerformed(evt);
            }
        });
        Menu_Edit.add(Menu_Edit_Randomize);
        Menu_Edit.add(jSeparator5);

        Menu_Edit_Shuffle_AT.setText("Shuffle Arrival Time (AT)");
        Menu_Edit_Shuffle_AT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_Edit_Shuffle_ATActionPerformed(evt);
            }
        });
        Menu_Edit.add(Menu_Edit_Shuffle_AT);

        Menu_Edit_Shuffle_BT.setText("Shuffle Burst Time (BT)");
        Menu_Edit_Shuffle_BT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_Edit_Shuffle_BTActionPerformed(evt);
            }
        });
        Menu_Edit.add(Menu_Edit_Shuffle_BT);

        Menu.add(Menu_Edit);

        jMenu3.setText("View");

        theme.setText("Theme");

        theme_Acryl.setSelected(true);
        theme_Acryl.setText("Acryl");
        theme.add(theme_Acryl);

        theme_Aero.setSelected(true);
        theme_Aero.setText("Aero");
        theme.add(theme_Aero);

        theme_Aluminium.setSelected(true);
        theme_Aluminium.setText("Aluminium");
        theme.add(theme_Aluminium);

        theme_Bernstein.setSelected(true);
        theme_Bernstein.setText("Bernstein");
        theme.add(theme_Bernstein);

        theme_Fast.setSelected(true);
        theme_Fast.setText("Fast");
        theme.add(theme_Fast);

        theme_HiFi.setSelected(true);
        theme_HiFi.setText("HiFi");
        theme.add(theme_HiFi);

        theme_McWin.setSelected(true);
        theme_McWin.setText("McWin");
        theme.add(theme_McWin);

        theme_Mint.setSelected(true);
        theme_Mint.setText("Mint");
        theme.add(theme_Mint);

        theme_Noire.setSelected(true);
        theme_Noire.setText("Noire");
        theme.add(theme_Noire);

        theme_Smart.setSelected(true);
        theme_Smart.setText("Smart");
        theme.add(theme_Smart);

        theme_Luna.setSelected(true);
        theme_Luna.setText("Luna");
        theme.add(theme_Luna);

        theme_Texture.setSelected(true);
        theme_Texture.setText("Texture");
        theme.add(theme_Texture);

        jMenu3.add(theme);

        Menu.add(jMenu3);

        jMenu4.setText("Help");

        jMenuItem2.setText("How to use");
        jMenu4.add(jMenuItem2);

        jMenuItem4.setText("About");
        jMenu4.add(jMenuItem4);

        Menu.add(jMenu4);

        setJMenuBar(Menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="Main Window Event Handlers">
    private void Menu_File_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_File_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_Menu_File_ExitActionPerformed
    
    private void config_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_config_buttonActionPerformed
        configurationsHandler();     
    }//GEN-LAST:event_config_buttonActionPerformed
                
    private void ao_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ao_buttonActionPerformed
        advanceOptionsHandler();
    }//GEN-LAST:event_ao_buttonActionPerformed

    private void toolbar_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_resetActionPerformed
        setComponentsToDefault();
    }//GEN-LAST:event_toolbar_resetActionPerformed
            
    private void toolbar_randomizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_randomizeActionPerformed
        randomizeAllInputs();
    }//GEN-LAST:event_toolbar_randomizeActionPerformed

    private void toolbar_clear_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_clear_tableActionPerformed
        TableHandlers.clearTable(table);
        TableHandlers.setProcessNumber(table);
    }//GEN-LAST:event_toolbar_clear_tableActionPerformed

    private void toolbar_shuffle_BTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_BTActionPerformed
        TableHandlers.shuffleTableColumnDouble(table, 2); // 2 which is the column of BT.
    }//GEN-LAST:event_toolbar_shuffle_BTActionPerformed

    private void toolbar_shuffle_ATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_ATActionPerformed
        TableHandlers.shuffleTableColumnDouble(table, 1); // 1 which is the column of AT.
    }//GEN-LAST:event_toolbar_shuffle_ATActionPerformed

    private void Menu_Edit_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_ResetActionPerformed
        setComponentsToDefault();
    }//GEN-LAST:event_Menu_Edit_ResetActionPerformed

    private void Menu_Edit_RandomizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_RandomizeActionPerformed
        randomizeAllInputs();
    }//GEN-LAST:event_Menu_Edit_RandomizeActionPerformed

    private void Menu_Edit_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_ClearActionPerformed
        TableHandlers.clearTable(table);
        TableHandlers.setProcessNumber(table);
    }//GEN-LAST:event_Menu_Edit_ClearActionPerformed

    private void Menu_Edit_Shuffle_ATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_Shuffle_ATActionPerformed
        TableHandlers.shuffleTableColumnDouble(table, 1); // 1 which is the column of AT.
    }//GEN-LAST:event_Menu_Edit_Shuffle_ATActionPerformed

    private void Menu_Edit_Shuffle_BTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_Shuffle_BTActionPerformed
        TableHandlers.shuffleTableColumnDouble(table, 2); // 2 which is the column of BT.
    }//GEN-LAST:event_Menu_Edit_Shuffle_BTActionPerformed

    private void toolbar_shuffle_PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_PActionPerformed
        if(table.getColumnCount() == 7) {
            TableHandlers.shuffleTableColumnInt(table, 3); // 3 which is the column of Priority.
        }
    }//GEN-LAST:event_toolbar_shuffle_PActionPerformed

    private void toolbar_table_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_table_copyActionPerformed
        TableSystemClipboard.copyTable(table);
        previousTable = table;
    }//GEN-LAST:event_toolbar_table_copyActionPerformed

    private void toolbar_table_pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_table_pasteActionPerformed
        TableSystemClipboard.pasteTable(table);
        TableHandlers.replaceNullWithEmpty(table);
    }//GEN-LAST:event_toolbar_table_pasteActionPerformed
                        
    private void computeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeButtonActionPerformed
        TableHandlers.validateTable(table);
        ProcessOperation pa = new ProcessOperation(TableHandlers.addValuesToProcess(selectedProcessType, table)); // Add process from table.        
        if(selectedProcessType.equals("Round Robin")) {
            TableHandlers.doProcessOperation(selectedProcessType, pa, timeQuantumHandler()); // Do the Process operation.
        } else {
            TableHandlers.doProcessOperation(selectedProcessType, pa); // Do the Process operation.
        }
        
        System.out.println("\nInitial Process Value:" +  pa.getProcessInitStorage() +
               "\nComputed Process Value:" + pa.getProcessComputation() +
               "\nScheduled Process Value:" + pa.getProcessSchedule());        
        System.out.println(pa.toString());
        
        this.ganttChartModel.setColumnCount(0);
        LinkedList<Process> processSchedule = pa.getProcessSchedule();
        if(processSchedule.size() > this.MAX_COLUMN){
            this.ganttChart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        for(int i = 0; i < processSchedule.size(); i++){
            this.ganttChartModel.addColumn("P" + processSchedule.get(i).getProcessNo(), new String[]{Double.toString(processSchedule.get(i).getBurstTime())} );
            
        } TableHandlers.centerTableHorizontalAlignment(this.ganttChart);
        TableHandlers.insertIntoTable(table, pa.toString());
        average_wt.setText(String.valueOf(pa.getAverageWaitingTime()));
        average_tat.setText(String.valueOf(pa.getAverageTurnAroundTime()));
    }//GEN-LAST:event_computeButtonActionPerformed
    //</editor-fold>
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        // Default theme (See groupButton())
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        /*
            JTatoo look and feels
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");/
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");/
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        */
        
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
    //<editor-fold defaultstate="collapsed" desc="Netbeans Generated Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenu Menu_Edit;
    private javax.swing.JMenuItem Menu_Edit_Clear;
    private javax.swing.JMenuItem Menu_Edit_Randomize;
    private javax.swing.JMenuItem Menu_Edit_Reset;
    private javax.swing.JMenuItem Menu_Edit_Shuffle_AT;
    private javax.swing.JMenuItem Menu_Edit_Shuffle_BT;
    private javax.swing.JMenu Menu_File;
    private javax.swing.JMenuItem Menu_File_Exit;
    private javax.swing.JButton ao_button;
    private javax.swing.JComboBox<String> at_comboBox;
    private javax.swing.JLabel at_first_label;
    private javax.swing.JTextField at_first_textField;
    private javax.swing.JLabel at_label;
    private javax.swing.JLabel at_second_label;
    private javax.swing.JTextField at_second_textField;
    private javax.swing.JLabel average_tat;
    private javax.swing.JLabel average_wt;
    private javax.swing.JComboBox<String> bt_comboBox;
    private javax.swing.JLabel bt_first_label;
    private javax.swing.JTextField bt_first_textField;
    private javax.swing.JLabel bt_label;
    private javax.swing.JLabel bt_second_label;
    private javax.swing.JTextField bt_second_textField;
    private javax.swing.JButton computeButton;
    private javax.swing.JButton config_button;
    private javax.swing.JLabel config_count_label;
    private javax.swing.JLabel config_label;
    private javax.swing.JLabel config_type_label;
    private javax.swing.JComboBox<String> count_comboBox;
    private javax.swing.JLabel count_label;
    private javax.swing.JLabel count_label_text;
    private javax.swing.JLabel criterion_label;
    private javax.swing.JLabel criterion_label_text;
    private javax.swing.JTable ganttChart;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel mode_label;
    private javax.swing.JLabel mode_label_text;
    private javax.swing.JComboBox<String> priority_comboBox;
    private javax.swing.JLabel priority_label;
    private javax.swing.JTable table;
    private javax.swing.JMenu theme;
    private javax.swing.JRadioButtonMenuItem theme_Acryl;
    private javax.swing.JRadioButtonMenuItem theme_Aero;
    private javax.swing.JRadioButtonMenuItem theme_Aluminium;
    private javax.swing.JRadioButtonMenuItem theme_Bernstein;
    private javax.swing.JRadioButtonMenuItem theme_Fast;
    private javax.swing.JRadioButtonMenuItem theme_HiFi;
    private javax.swing.JRadioButtonMenuItem theme_Luna;
    private javax.swing.JRadioButtonMenuItem theme_McWin;
    private javax.swing.JRadioButtonMenuItem theme_Mint;
    private javax.swing.JRadioButtonMenuItem theme_Noire;
    private javax.swing.JRadioButtonMenuItem theme_Smart;
    private javax.swing.JRadioButtonMenuItem theme_Texture;
    private javax.swing.JLabel time_quantum_label;
    private javax.swing.JTextField time_quantum_textBox;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton toolbar_clear_table;
    private javax.swing.JButton toolbar_randomize;
    private javax.swing.JButton toolbar_reset;
    private javax.swing.JButton toolbar_shuffle_AT;
    private javax.swing.JButton toolbar_shuffle_BT;
    private javax.swing.JButton toolbar_shuffle_P;
    private javax.swing.JButton toolbar_table_copy;
    private javax.swing.JButton toolbar_table_paste;
    private javax.swing.JComboBox<String> type_comboBox;
    private javax.swing.JLabel type_label;
    private javax.swing.JLabel type_label_text;
    // End of variables declaration//GEN-END:variables
 //</editor-fold>
}
