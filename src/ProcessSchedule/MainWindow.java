package ProcessSchedule;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends javax.swing.JFrame {
                
    private Configurations config;
    private ProcessImplementor processImplementor;
    private ProcessScheduleTable processScheduleTable;
    private AdvanceOptions advanceOptions;    
    
    public MainWindow() {
        initComponents();
        initWindow();
        initListeners();        
    }
    
    private void initWindow() {
        this.setSize(930, 575);
        this.setResizable(false);
        setWindowToCenter();
        config = new Configurations(typeComboBox.getSelectedItem().toString(),
                Integer.parseInt(countComboBox.getSelectedItem().toString()));
        processImplementor = new ProcessImplementor(table, config, typeLabel, countLabel, 
                modeLabel, criterionLabel, timeQuantumJLabel,  timeQuantumTextField);
        advanceOptions = new AdvanceOptions(config, atLabel, btLabel,
                atFirstLabel, atSecondLabel, btFirstLabel, btSecondLabel,
                atComboBox, btComboBox, atFirstTextField, atSecondTextField,
                btFirstTextField, btSecondTextField, priorityLabel, 
                priorityComboBox, aoButton);
        setComponentsToDefault();
    }
    
    private void setWindowToCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width / 2) - (this.getSize().width / 2), 
                (dim.height / 2) - (this.getSize().height / 2));
    }
    
    private void setComponentsToDefault() {                                                                       
        typeComboBox.setSelectedIndex(0);
        countComboBox.setSelectedIndex(0);
        advanceOptions.setInitOptionsEnabled(false);        
        advanceOptions.setATInputEnabled(false);
        advanceOptions.setBTInputEnabled(false);        
        processImplementor.resetInfo();
        average_wt.setText("");
        averageTat.setText("");
        table.setModel(new DefaultTableModel(0, 0));
        ganttChart.setModel(new DefaultTableModel(0, 0));
    }
        
    private void initListeners() {                
        atComboBox.addActionListener((ActionEvent event) -> {
            int selectedIndex = atComboBox.getSelectedIndex();
            if (selectedIndex == 3) {                
                atFirstLabel.setText("Min");
                atSecondLabel.setText("Max");                
            } else {
                atFirstLabel.setText("Start");
                atSecondLabel.setText("Step");                
            } 
            switch (selectedIndex) {
                case 0: advanceOptions.setATInputEnabled(false);
                        break;
                case 1: advanceOptions.setATInputEnabled(true);
                        atFirstTextField.setText("1");
                        atSecondTextField.setText("1");
                        break;
                case 2: advanceOptions.setATInputEnabled(true);
                        atFirstTextField.setText(String.valueOf(table.getRowCount()));
                        atSecondTextField.setText("1");
                        break;
                case 3: advanceOptions.setATInputEnabled(true);
                        atFirstTextField.setText("1");
                        atSecondTextField.setText(String.valueOf(table.getRowCount()));
                        break;
                default: break;
            }                        
        });
        btComboBox.addActionListener((ActionEvent event) -> {
            int selectedIndex = btComboBox.getSelectedIndex();
            if (selectedIndex == 3) {
                btFirstLabel.setText("Min");
                btSecondLabel.setText("Max");
            } else {
                btFirstLabel.setText("Start");
                btSecondLabel.setText("Step");
            }
            switch (selectedIndex) {
                case 0: advanceOptions.setBTInputEnabled(false); 
                        break;
                case 1: advanceOptions.setBTInputEnabled(true);
                        btFirstTextField.setText("1");
                        btSecondTextField.setText("1");
                        break;
                case 2: advanceOptions.setBTInputEnabled(true);
                        btFirstTextField.setText(String.valueOf(table.getRowCount()));
                        btSecondTextField.setText("1");
                        break;
                case 3: advanceOptions.setBTInputEnabled(true);
                        btFirstTextField.setText("1");
                        btSecondTextField.setText(String.valueOf(table.getRowCount()));
                        break;
            }
        });
    }
    
    private void randomizeAllInputs() {
        setComponentsToDefault();
        Random rand = new Random();
        int typeCount = typeComboBox.getItemCount();
        int countCount = countComboBox.getItemCount();
        int atCount = atComboBox.getItemCount();
        int btCount = btComboBox.getItemCount();        

        typeComboBox.setSelectedIndex(rand.nextInt(typeCount));
        countComboBox.setSelectedIndex(rand.nextInt(countCount));
        applyConfigurations();
        atComboBox.setSelectedIndex(rand.nextInt(atCount - 1) + 1);
        btComboBox.setSelectedIndex(rand.nextInt(btCount - 1) + 1);        
        if (config.isPriority()) {
            int priorityCount = priorityComboBox.getItemCount();            
            priorityComboBox.setSelectedIndex(rand.nextInt(priorityCount - 1) + 1);
        }        
        applyAdvanceOptions();
    }
       
    private void applyConfigurations() {
        config.setType(typeComboBox.getSelectedItem().toString());
        config.setCount(Integer.parseInt(countComboBox.getSelectedItem().toString()));        
        processImplementor.implement();
        processScheduleTable = processImplementor.getProcessScheduleTable();
        advanceOptions.setInitOptionsEnabled(true);
    }
    
    private void applyAdvanceOptions() {
        int rowCount = processScheduleTable.getRowCount();
        
        processScheduleTable.setColumn(processScheduleTable.getATColumn(), advanceOptions.getATResult(rowCount));
        processScheduleTable.setColumn(processScheduleTable.getBTColumn(), advanceOptions.getBTResult(rowCount));        
        if (config.isPriority()) {
            processScheduleTable.setColumn(processScheduleTable.getPriorityColumn(), advanceOptions.getPriorityResult(rowCount));
        }
    }
    
    private void acceptDigitOnly(KeyEvent evt) {
        char c = evt.getKeyChar();
        
        if (!Character.isDigit(c) && c != KeyEvent.VK_PERIOD) {
            evt.consume();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeComboBox = new javax.swing.JComboBox<>();
        configTypeLabel = new javax.swing.JLabel();
        configLabel = new javax.swing.JLabel();
        priorityComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ganttChart = new javax.swing.JTable();
        computeButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        average_wt = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        aoButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        configButton = new javax.swing.JButton();
        configCountLabel = new javax.swing.JLabel();
        priorityLabel = new javax.swing.JLabel();
        atLabel = new javax.swing.JLabel();
        countComboBox = new javax.swing.JComboBox<>();
        atComboBox = new javax.swing.JComboBox<>();
        btComboBox = new javax.swing.JComboBox<>();
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
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        criterionLabel = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        atSecondTextField = new javax.swing.JTextField();
        btSecondTextField = new javax.swing.JTextField();
        atFirstLabel = new javax.swing.JLabel();
        atFirstTextField = new javax.swing.JTextField();
        btFirstTextField = new javax.swing.JTextField();
        atSecondLabel = new javax.swing.JLabel();
        btLabel = new javax.swing.JLabel();
        btFirstLabel = new javax.swing.JLabel();
        btSecondLabel = new javax.swing.JLabel();
        modeLabel = new javax.swing.JLabel();
        timeQuantumJLabel = new javax.swing.JLabel();
        timeQuantumTextField = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        averageTat = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        Menu_File = new javax.swing.JMenu();
        Menu_File_Exit = new javax.swing.JMenuItem();
        Menu_Edit = new javax.swing.JMenu();
        Menu_Edit_Reset = new javax.swing.JMenuItem();
        Menu_Edit_Clear = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        Menu_Edit_Randomize = new javax.swing.JMenuItem();
        Menu_Edit_Shuffle_AT = new javax.swing.JMenuItem();
        Menu_Edit_Shuffle_BT = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Process Scheduling");
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        typeComboBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Come First Serve", "Shortest Job First", "Shortest Remaining Time First", "Non-Preemptive Priority", "Preemptive Priority", "Round Robin" }));
        getContentPane().add(typeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 200, 32));

        configTypeLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        configTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        configTypeLabel.setText("Type:");
        getContentPane().add(configTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 40, 30));

        configLabel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        configLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        configLabel.setText("Configurations");
        configLabel.setOpaque(true);
        getContentPane().add(configLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 280, 30));

        priorityComboBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        priorityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(priorityComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 200, 32));

        table.setAutoCreateRowSorter(true);
        table.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
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

        ganttChart.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
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

        computeButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        computeButton.setText("Compute");
        computeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(computeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, -1, 32));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Average WT =");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, -1, -1));

        average_wt.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        average_wt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        average_wt.setText("answer");
        getContentPane().add(average_wt, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 460, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 280, 10));

        aoButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        aoButton.setText("Apply");
        aoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(aoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 70, 32));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 920, 10));

        configButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        configButton.setText("Apply");
        configButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configButtonActionPerformed(evt);
            }
        });
        getContentPane().add(configButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 70, 32));

        configCountLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        configCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        configCountLabel.setText("Count:");
        getContentPane().add(configCountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 40, 30));

        priorityLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        priorityLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        priorityLabel.setText("Priority:");
        getContentPane().add(priorityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 40, 30));

        atLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        atLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        atLabel.setText("AT:");
        getContentPane().add(atLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 40, 30));

        countComboBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        countComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9" }));
        getContentPane().add(countComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 200, 32));

        atComboBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        atComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(atComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 100, 32));

        btComboBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Ascending", "Descending", "Randomize" }));
        getContentPane().add(btComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 100, 32));

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

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Mode:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 50, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Count:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 40, -1));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Criterion:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 50, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Type:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 40, -1));

        criterionLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        criterionLabel.setText("text\n");
        getContentPane().add(criterionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, -1, -1));

        countLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        countLabel.setText("text\n");
        getContentPane().add(countLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

        typeLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        typeLabel.setText("text ");
        getContentPane().add(typeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        atSecondTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        atSecondTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                atSecondTextFieldKeyTyped(evt);
            }
        });
        getContentPane().add(atSecondTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 40, 32));

        btSecondTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        btSecondTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btSecondTextFieldKeyTyped(evt);
            }
        });
        getContentPane().add(btSecondTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 40, 32));

        atFirstLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        atFirstLabel.setText("Start");
        getContentPane().add(atFirstLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, 20));

        atFirstTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        atFirstTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                atFirstTextFieldKeyTyped(evt);
            }
        });
        getContentPane().add(atFirstTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 40, 32));

        btFirstTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        btFirstTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btFirstTextFieldKeyTyped(evt);
            }
        });
        getContentPane().add(btFirstTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 40, 32));

        atSecondLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        atSecondLabel.setText("Step");
        getContentPane().add(atSecondLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, 20));

        btLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btLabel.setText("BT:");
        getContentPane().add(btLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 40, 30));

        btFirstLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btFirstLabel.setText("Start");
        getContentPane().add(btFirstLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, -1, 20));

        btSecondLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btSecondLabel.setText("Step");
        getContentPane().add(btSecondLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, -1, 20));

        modeLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        modeLabel.setText("text\n");
        getContentPane().add(modeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, -1, -1));

        timeQuantumJLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        timeQuantumJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        timeQuantumJLabel.setText("Time Quantum:");
        getContentPane().add(timeQuantumJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 90, -1));

        timeQuantumTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(timeQuantumTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 65, 50, -1));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 273, 280, 10));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 280, 10));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Average TAT =");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 460, -1, -1));

        averageTat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        averageTat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        averageTat.setText("answer");
        getContentPane().add(averageTat, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 460, -1, -1));

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

        Menu_Edit_Reset.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
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
    
    private void configButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configButtonActionPerformed
        applyConfigurations();     
    }//GEN-LAST:event_configButtonActionPerformed
                
    private void aoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aoButtonActionPerformed
        applyAdvanceOptions();
    }//GEN-LAST:event_aoButtonActionPerformed

    private void toolbar_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_resetActionPerformed
        setComponentsToDefault();
    }//GEN-LAST:event_toolbar_resetActionPerformed
            
    private void toolbar_randomizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_randomizeActionPerformed
        randomizeAllInputs();
    }//GEN-LAST:event_toolbar_randomizeActionPerformed

    private void toolbar_clear_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_clear_tableActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.clear();
        }
    }//GEN-LAST:event_toolbar_clear_tableActionPerformed

    private void toolbar_shuffle_BTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_BTActionPerformed
        if(processScheduleTable != null) {
            processScheduleTable.shuffleTableColumn(2);
        }
    }//GEN-LAST:event_toolbar_shuffle_BTActionPerformed

    private void toolbar_shuffle_ATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_ATActionPerformed
        if(processScheduleTable != null) {
            processScheduleTable.shuffleTableColumn(1);
        }
    }//GEN-LAST:event_toolbar_shuffle_ATActionPerformed

    private void Menu_Edit_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_ResetActionPerformed
        setComponentsToDefault();
    }//GEN-LAST:event_Menu_Edit_ResetActionPerformed

    private void Menu_Edit_RandomizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_RandomizeActionPerformed
        randomizeAllInputs();
    }//GEN-LAST:event_Menu_Edit_RandomizeActionPerformed

    private void Menu_Edit_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_ClearActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.clear();
        }
    }//GEN-LAST:event_Menu_Edit_ClearActionPerformed

    private void Menu_Edit_Shuffle_ATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_Shuffle_ATActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.shuffleTableColumn(processScheduleTable.getATColumn());
        }
    }//GEN-LAST:event_Menu_Edit_Shuffle_ATActionPerformed

    private void Menu_Edit_Shuffle_BTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_Edit_Shuffle_BTActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.shuffleTableColumn(processScheduleTable.getBTColumn());
        }        
    }//GEN-LAST:event_Menu_Edit_Shuffle_BTActionPerformed

    private void toolbar_shuffle_PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_shuffle_PActionPerformed
        if (processScheduleTable != null && config.isPriority()) {
            processScheduleTable.shuffleTableColumn(3);
        }
    }//GEN-LAST:event_toolbar_shuffle_PActionPerformed
        
    private void toolbar_table_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_table_copyActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.copyTable(
                    processScheduleTable.getRowCount(), 
                    processScheduleTable.getInputColumnCount());
        }        
    }//GEN-LAST:event_toolbar_table_copyActionPerformed

    private void toolbar_table_pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbar_table_pasteActionPerformed
        if (processScheduleTable != null) {
            processScheduleTable.pasteTable(
                    processScheduleTable.getRowCount(), 
                    processScheduleTable.getInputColumnCount());
        }        
    }//GEN-LAST:event_toolbar_table_pasteActionPerformed
                        
    private void computeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeButtonActionPerformed
        processScheduleTable.sort();
        processScheduleTable.fixValues();
        ProcessOperation po = new ProcessOperation(processImplementor.addValuesToProcess());
        if (config.getType().equals("Round Robin")) {
            processImplementor.doProcessOperation(po, processImplementor.getTimeQuantum());
        } else {
            processImplementor.doProcessOperation(po);
        }
        LinkedList<Process> processSchedule = po.getProcessSchedule();
        GanttChartTable ganttChartTable = new GanttChartTable(ganttChart, 0, processSchedule);
        ganttChartTable.showProcessSchedule();
        processScheduleTable.insert(po.toString());
        average_wt.setText(String.valueOf(po.getAverageWaitingTime()));
        averageTat.setText(String.valueOf(po.getAverageTurnAroundTime()));
    }//GEN-LAST:event_computeButtonActionPerformed

    private void atFirstTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atFirstTextFieldKeyTyped
        acceptDigitOnly(evt);
    }//GEN-LAST:event_atFirstTextFieldKeyTyped

    private void atSecondTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atSecondTextFieldKeyTyped
        acceptDigitOnly(evt);
    }//GEN-LAST:event_atSecondTextFieldKeyTyped

    private void btFirstTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFirstTextFieldKeyTyped
        acceptDigitOnly(evt);
    }//GEN-LAST:event_btFirstTextFieldKeyTyped

    private void btSecondTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btSecondTextFieldKeyTyped
        acceptDigitOnly(evt);
    }//GEN-LAST:event_btSecondTextFieldKeyTyped
    //</editor-fold>
   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
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
    private javax.swing.JButton aoButton;
    private javax.swing.JComboBox<String> atComboBox;
    private javax.swing.JLabel atFirstLabel;
    private javax.swing.JTextField atFirstTextField;
    private javax.swing.JLabel atLabel;
    private javax.swing.JLabel atSecondLabel;
    private javax.swing.JTextField atSecondTextField;
    private javax.swing.JLabel averageTat;
    private javax.swing.JLabel average_wt;
    private javax.swing.JComboBox<String> btComboBox;
    private javax.swing.JLabel btFirstLabel;
    private javax.swing.JTextField btFirstTextField;
    private javax.swing.JLabel btLabel;
    private javax.swing.JLabel btSecondLabel;
    private javax.swing.JTextField btSecondTextField;
    private javax.swing.JButton computeButton;
    private javax.swing.JButton configButton;
    private javax.swing.JLabel configCountLabel;
    private javax.swing.JLabel configLabel;
    private javax.swing.JLabel configTypeLabel;
    private javax.swing.JComboBox<String> countComboBox;
    private javax.swing.JLabel countLabel;
    private javax.swing.JLabel criterionLabel;
    private javax.swing.JTable ganttChart;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel modeLabel;
    private javax.swing.JComboBox<String> priorityComboBox;
    private javax.swing.JLabel priorityLabel;
    private javax.swing.JTable table;
    private javax.swing.JLabel timeQuantumJLabel;
    private javax.swing.JTextField timeQuantumTextField;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton toolbar_clear_table;
    private javax.swing.JButton toolbar_randomize;
    private javax.swing.JButton toolbar_reset;
    private javax.swing.JButton toolbar_shuffle_AT;
    private javax.swing.JButton toolbar_shuffle_BT;
    private javax.swing.JButton toolbar_shuffle_P;
    private javax.swing.JButton toolbar_table_copy;
    private javax.swing.JButton toolbar_table_paste;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
 //</editor-fold>
}
