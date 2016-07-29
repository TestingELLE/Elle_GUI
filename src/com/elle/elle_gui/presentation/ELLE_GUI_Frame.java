package com.elle.elle_gui.presentation;

import com.elle.elle_gui.admissions.Authorization;
import com.elle.elle_gui.dao.AllocationDAO;
import com.elle.elle_gui.dao.PositionDAO;
import com.elle.elle_gui.dao.TradeDAO;
import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Allocation;
import com.elle.elle_gui.entities.Position;
import com.elle.elle_gui.entities.Trade;
import com.elle.elle_gui.logic.ATrade;
import com.elle.elle_gui.logic.ColumnPopupMenu;
import com.elle.elle_gui.logic.AccountTable;
import com.elle.elle_gui.logic.CreateDocumentFilter;
import com.elle.elle_gui.logic.EditableTableModel;
import com.elle.elle_gui.logic.ITableConstants;
import com.elle.elle_gui.logic.LoggingAspect;
import com.elle.elle_gui.logic.PrintWindow;
import com.elle.elle_gui.logic.TableFilter;
import com.elle.elle_gui.logic.Validator;
import com.elle.elle_gui.logic.TableColumnAdjuster;
import com.elle.elle_gui.logic.TableFromServer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import java.awt.event.*;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.Timer;
import java.lang.Double;
import static java.lang.Double.parseDouble;
import javafx.scene.control.Tooltip;

/**
 * ELLE_GUI_Frame
 *
 * @author Carlos Igreja
 * @since 8-14-2015
 * @version ELLE_GUI-0.6.9
 */
public class ELLE_GUI_Frame extends JFrame implements ITableConstants {

    public static String creationDate;  // set automatically from manifest
    public static String version;       // set automatically from manifest
    
    // attributes
    private Map<String, Map<String, AccountTable>> tabs; // stores individual accountTable objects 
    private static Statement statement;
    private String database;
    private String server;
    
    //maps store whether an alert logging column changes in the database has been displayed for this table
    //prevents the same alert message from being displayed more than once for the same table
    Map<String, Boolean> hasDeletedColumnAlert = new HashMap<String,Boolean>();
    Map<String, Boolean> hasUnexpectedColumnAlert = new HashMap<String,Boolean>();
    String tableColumnAlertMessage;
    //Stores a message which logs columns which are contained in the column name constants,
    //but are missing from the resultSet
    String missingColumnLog = ""; 
    String unexpectedColumnLog = ""; //logs columns not contained in the column name constants
    

    // components
    private static ELLE_GUI_Frame instance;
    private LogWindow logWindow;
    private LoginWindow loginWindow;
    private ATrade aTradeInView;
    private ViewATradeWindow viewATradeWindow;
    private SqlOutputWindow sqlOutputWindow;

    // button colors
    private Color colorBtnDefault;
    private Color colorBtnSelected;
    
    // create a jlabel to show the database and server used
    private JLabel databaseLabel;
    
    // DAO
    private AllocationDAO allocationDAO;
    private PositionDAO positionDAO;
    private TradeDAO tradeDAO;

    /**
     * ELLE_GUI_Frame Creates the ELLE_GUI_Frame which is the main window of the
     * application
     */
    public ELLE_GUI_Frame() {
        initComponents();
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setTitle("Elle GUI");

        // the statement is used for sql statements with the database connection
        // the statement is created in LoginWindow and passed to ELLE_GUI.
        statement = DBConnection.getStatement();
        database = DBConnection.getDatabase();
        instance = this;                         // this is used to call this instance of ELLE_GUI 
        colorBtnDefault = btnPositions.getBackground();
        colorBtnSelected = Color.RED;
        
        // init DAO
        allocationDAO = new AllocationDAO();
        positionDAO = new PositionDAO();
        tradeDAO = new TradeDAO();

        // initialize tabs
        tabs = new HashMap();
        
        tableColumnAlertMessage = "";
        
        hasDeletedColumnAlert.put(TRADES_TABLE_NAME, false);
        hasDeletedColumnAlert.put(POSITIONS_TABLE_NAME, false);
        hasDeletedColumnAlert.put(ALLOCATIONS_TABLE_NAME, false);
        
        hasUnexpectedColumnAlert.put(TRADES_TABLE_NAME, false);
        hasUnexpectedColumnAlert.put(POSITIONS_TABLE_NAME, false);
        hasUnexpectedColumnAlert.put(ALLOCATIONS_TABLE_NAME, false);
        /**
         * *************** IB9048 Account ***************************
         */
        // create hashmap for IB9048 tables
        Map<String, AccountTable> tabIB9048 = new HashMap();
        tabIB9048.put(POSITIONS_TABLE_NAME, new AccountTable());
        tabIB9048.put(ALLOCATIONS_TABLE_NAME, new AccountTable());
        tabIB9048.put(TRADES_TABLE_NAME, new AccountTable());
        // initialize tables for IB9048 -Postions table
        tabIB9048.get(POSITIONS_TABLE_NAME).setTable(new JTable());
        tabIB9048.get(POSITIONS_TABLE_NAME).setTableName(POSITIONS_TABLE_NAME);
        //tabIB9048.get(POSITIONS_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_POSITIONS_DEFAULT_VIEW);
        //tabIB9048.get(POSITIONS_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_POSITIONS_ALL_FIELDS);
        tabIB9048.get(POSITIONS_TABLE_NAME).setFilter(new TableFilter(tabIB9048.get(POSITIONS_TABLE_NAME).getTable()));
        tabIB9048.get(POSITIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabIB9048.get(POSITIONS_TABLE_NAME).getFilter()));
        // initialize tables for IB9048 -Trades table
        tabIB9048.get(TRADES_TABLE_NAME).setTable(new JTable());
        tabIB9048.get(TRADES_TABLE_NAME).setTableName(TRADES_TABLE_NAME);
        //tabIB9048.get(TRADES_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_TRADES_DEFAULT_VIEW);
        //tabIB9048.get(TRADES_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_TRADES_ALL_FIELDS);
        tabIB9048.get(TRADES_TABLE_NAME).setFilter(new TableFilter(tabIB9048.get(TRADES_TABLE_NAME).getTable()));
        tabIB9048.get(TRADES_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabIB9048.get(TRADES_TABLE_NAME).getFilter()));
        // initialize tables for IB9048 -Allocations table
        tabIB9048.get(ALLOCATIONS_TABLE_NAME).setTable(new JTable());
        tabIB9048.get(ALLOCATIONS_TABLE_NAME).setTableName(ALLOCATIONS_TABLE_NAME);
        //tabIB9048.get(ALLOCATIONS_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_ALLOCATIONS_DEFAULT_VIEW);
        //tabIB9048.get(ALLOCATIONS_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_ALLOCATIONS_ALL_FIELDS);
        tabIB9048.get(ALLOCATIONS_TABLE_NAME).setFilter(new TableFilter(tabIB9048.get(ALLOCATIONS_TABLE_NAME).getTable()));
        tabIB9048.get(ALLOCATIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabIB9048.get(ALLOCATIONS_TABLE_NAME).getFilter()));
        // add tables to the IB9048 account accountTable
        tabs.put(IB9048_ACCOUNT_NAME, tabIB9048);

        /**
         * *************** TOS3622 Account ***************************
         */
        // create hashmap for TOS3622 tables
        Map<String, AccountTable> tabTOS3622 = new HashMap();
        tabTOS3622.put(POSITIONS_TABLE_NAME, new AccountTable());
        tabTOS3622.put(ALLOCATIONS_TABLE_NAME, new AccountTable());
        tabTOS3622.put(TRADES_TABLE_NAME, new AccountTable());
        // initialize tables for TOS3622 -Postions table
        tabTOS3622.get(POSITIONS_TABLE_NAME).setTable(new JTable());
        tabTOS3622.get(POSITIONS_TABLE_NAME).setTableName(POSITIONS_TABLE_NAME);
        //tabTOS3622.get(POSITIONS_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_POSITIONS_DEFAULT_VIEW);
        //tabTOS3622.get(POSITIONS_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_POSITIONS_ALL_FIELDS);
        tabTOS3622.get(POSITIONS_TABLE_NAME).setFilter(new TableFilter(tabTOS3622.get(POSITIONS_TABLE_NAME).getTable()));
        tabTOS3622.get(POSITIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabTOS3622.get(POSITIONS_TABLE_NAME).getFilter()));
        // initialize tables for TOS3622 -Trades table
        tabTOS3622.get(TRADES_TABLE_NAME).setTable(new JTable());
        tabTOS3622.get(TRADES_TABLE_NAME).setTableName(TRADES_TABLE_NAME);
        //tabTOS3622.get(TRADES_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_TRADES_DEFAULT_VIEW);
        //tabTOS3622.get(TRADES_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_TRADES_ALL_FIELDS);
        tabTOS3622.get(TRADES_TABLE_NAME).setFilter(new TableFilter(tabTOS3622.get(TRADES_TABLE_NAME).getTable()));
        tabTOS3622.get(TRADES_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabTOS3622.get(TRADES_TABLE_NAME).getFilter()));
        // initialize tables for TOS3622 -Allocations table
        tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setTable(new JTable());
        tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setTableName(ALLOCATIONS_TABLE_NAME);
        //tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setColWidthPercentDefaultView(COL_WIDTH_PER_ALLOCATIONS_DEFAULT_VIEW);
        //tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setColWidthPercentAllFields(COL_WIDTH_PER_ALLOCATIONS_ALL_FIELDS);
        tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setFilter(new TableFilter(tabTOS3622.get(ALLOCATIONS_TABLE_NAME).getTable()));
        tabTOS3622.get(ALLOCATIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabTOS3622.get(ALLOCATIONS_TABLE_NAME).getFilter()));
        // add tables to the TOS3622 account accountTable
        tabs.put(TOS3622_ACCOUNT_NAME, tabTOS3622);

        /**
         * *************** Combined Accounts ***************************
         */
        // create hashmap for Combined tables
        Map<String, AccountTable> tabCombined = new HashMap();
        tabCombined.put(POSITIONS_TABLE_NAME, new AccountTable());
        tabCombined.put(ALLOCATIONS_TABLE_NAME, new AccountTable());
        tabCombined.put(TRADES_TABLE_NAME, new AccountTable());
        // initialize tables for Combined -Postions table
        tabCombined.get(POSITIONS_TABLE_NAME).setTable(new JTable());
        tabCombined.get(POSITIONS_TABLE_NAME).setTableName(POSITIONS_TABLE_NAME);
        tabCombined.get(POSITIONS_TABLE_NAME).setFilter(new TableFilter(tabCombined.get(POSITIONS_TABLE_NAME).getTable()));
        tabCombined.get(POSITIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabCombined.get(POSITIONS_TABLE_NAME).getFilter()));
        // initialize tables for Combined -Trades table
        tabCombined.get(TRADES_TABLE_NAME).setTable(new JTable());
        tabCombined.get(TRADES_TABLE_NAME).setTableName(TRADES_TABLE_NAME);
        tabCombined.get(TRADES_TABLE_NAME).setFilter(new TableFilter(tabCombined.get(TRADES_TABLE_NAME).getTable()));
        tabCombined.get(TRADES_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabCombined.get(TRADES_TABLE_NAME).getFilter()));
        // initialize tables for Combined -Allocations table
        tabCombined.get(ALLOCATIONS_TABLE_NAME).setTable(new JTable());
        tabCombined.get(ALLOCATIONS_TABLE_NAME).setTableName(ALLOCATIONS_TABLE_NAME);
        
        tabCombined.get(ALLOCATIONS_TABLE_NAME).setFilter(new TableFilter(tabCombined.get(ALLOCATIONS_TABLE_NAME).getTable()));
        tabCombined.get(ALLOCATIONS_TABLE_NAME).setColumnPopupMenu(new ColumnPopupMenu(tabCombined.get(ALLOCATIONS_TABLE_NAME).getFilter()));
        // add tables to the Combined account accountTable
        tabs.put(COMBINED_ACCOUNT_NAME, tabCombined);

        // this sets the KeyboardFocusManger
        //setKeyboardFocusManager();
        // load data from database to tables
       loadTables(tabs);
       
        //display an alert message for any column changes in the database
        displayTableColumnAlert();

        // now that the tables are loaded, 
        // the columnnames string array can be loaded for each table
        // this may not even be needed for this application
        tabs.get(IB9048_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(IB9048_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).getTable());
        tabs.get(IB9048_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(IB9048_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).getTable());
        tabs.get(IB9048_ACCOUNT_NAME).get(TRADES_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(IB9048_ACCOUNT_NAME).get(TRADES_TABLE_NAME).getTable());
        tabs.get(TOS3622_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(TOS3622_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).getTable());
        tabs.get(TOS3622_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(TOS3622_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).getTable());
        tabs.get(TOS3622_ACCOUNT_NAME).get(TRADES_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(TOS3622_ACCOUNT_NAME).get(TRADES_TABLE_NAME).getTable());
        tabs.get(COMBINED_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(COMBINED_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).getTable());
        tabs.get(COMBINED_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(COMBINED_ACCOUNT_NAME).get(ALLOCATIONS_TABLE_NAME).getTable());
        tabs.get(COMBINED_ACCOUNT_NAME).get(TRADES_TABLE_NAME).
                setTableColNamesAndIDList(tabs.get(COMBINED_ACCOUNT_NAME).get(TRADES_TABLE_NAME).getTable());

        // hide sql panel by default
        panelSQL.setVisible(false);
        this.setSize(this.getWidth(), 493);

        // show IB9048 positions table (initial start up)
        AccountTable IB9048_positions = tabs.get(IB9048_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME);
        IB9048_positions.setTableSelected(true);
        JTable table = IB9048_positions.getTable();
        JScrollPane scroll = new JScrollPane(table);
        setScrollBarFormat(scroll, table);                  // fix issue with scroll bar dissappearing
        panelIB9048.removeAll();
        panelIB9048.setLayout(new BorderLayout());
        panelIB9048.add(scroll, BorderLayout.CENTER);
        IB9048_positions.setTableSelected(true);

        // set initial records label
        String recordsText = IB9048_positions.getRecordsLabel();
        labelRecords.setText(recordsText);

        // start table with positions button selected
        btnPositions.setBackground(colorBtnSelected);
        btnPositions.requestFocus();

        // start the other tables initially on positions
        tabs.get(TOS3622_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).setTableSelected(true);
        tabs.get(COMBINED_ACCOUNT_NAME).get(POSITIONS_TABLE_NAME).setTableSelected(true);
       
        //gray out the existign three taps in other menu
        menuItemIB8949.setEnabled(false);
        menuItemReconcile.setEnabled(false);
        menuItemTL8949.setEnabled(false);
        
        
        Authorization.authorize(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        filechooser = new javax.swing.JFileChooser();
        panelCTRLPanel = new javax.swing.JPanel();
        btnTrades = new javax.swing.JButton();
        btnAllocations = new javax.swing.JButton();
        btnSymbol = new javax.swing.JButton();
        btnDateRange = new javax.swing.JButton();
        checkBoxSymbol = new javax.swing.JCheckBox();
        checkBoxDateRange = new javax.swing.JCheckBox();
        labelHyphen = new javax.swing.JLabel();
        textFieldSymbol = new javax.swing.JTextField();
        textFieldStartDate = new javax.swing.JTextField();
        textFieldEndDate = new javax.swing.JTextField();
        btnPositions = new javax.swing.JButton();
        labelRecords = new javax.swing.JLabel();
        btnTableDisplayState = new javax.swing.JLabel();
        btnClearAllFilters = new java.awt.Button();
        panelSQL = new javax.swing.JPanel();
        scrollPaneSQL = new javax.swing.JScrollPane();
        textAreaSQL = new javax.swing.JTextArea();
        btnEnterSQL = new javax.swing.JButton();
        btnClearSQL = new javax.swing.JButton();
        btnShowTables = new javax.swing.JButton();
        panelAccounts = new javax.swing.JPanel();
        tabbedPaneAccounts = new javax.swing.JTabbedPane();
        panelIB9048 = new javax.swing.JPanel();
        scrollPaneIB9048 = new javax.swing.JScrollPane();
        tableIB9048 = new javax.swing.JTable();
        panelTOS3622 = new javax.swing.JPanel();
        scrollPaneTOS3622 = new javax.swing.JScrollPane();
        tableTOS3622 = new javax.swing.JTable();
        panelCombined = new javax.swing.JPanel();
        scrollPaneCombined = new javax.swing.JScrollPane();
        tableCombined = new javax.swing.JTable();
        informationLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemVersion = new javax.swing.JMenuItem();
        menuItemRead = new javax.swing.JMenuItem();
        menuPrint = new javax.swing.JMenu();
        menuItemPrintGUI = new javax.swing.JMenuItem();
        menuItemPrintDisplayWindow = new javax.swing.JMenuItem();
        menuItemSave = new javax.swing.JMenuItem();
        menuItemLogOut = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuItemConnection = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuConnections = new javax.swing.JMenu();
        menuItemAWS = new javax.swing.JMenuItem();
        menuItemPupone = new javax.swing.JMenuItem();
        menuItemLocal = new javax.swing.JMenuItem();
        menuFind = new javax.swing.JMenu();
        menuReports = new javax.swing.JMenu();
        menuTools = new javax.swing.JMenu();
        menuItemBackup = new javax.swing.JMenuItem();
        menuItemReloadTabData = new javax.swing.JMenuItem();
        menuLoad = new javax.swing.JMenu();
        menuItemLoadFile = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuView = new javax.swing.JMenu();
        menuItemCheckBoxLog = new javax.swing.JCheckBoxMenuItem();
        menuItemCheckBoxSQL = new javax.swing.JCheckBoxMenuItem();
        menuItemAViewATrades = new javax.swing.JMenuItem();
        menuItemIB = new javax.swing.JMenuItem();
        menuItemTL = new javax.swing.JMenuItem();
        menuItemLoadsTable = new javax.swing.JMenuItem();
        viewmatches = new javax.swing.JMenuItem();
        viewnomatches = new javax.swing.JMenuItem();
        menuItemDefaultColumnWidths = new javax.swing.JMenuItem();
        menuOther = new javax.swing.JMenu();
        menuItemIB8949 = new javax.swing.JMenuItem();
        menuItemReconcile = new javax.swing.JMenuItem();
        menuItemTL8949 = new javax.swing.JMenuItem();

        filechooser.setDialogTitle("Open from files...");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelCTRLPanel.setLayout(new java.awt.GridBagLayout());

        btnTrades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/elle/elle_gui/images/button1.png"))); // NOI18N
        btnTrades.setText("Trades");
        btnTrades.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrades.setMaximumSize(new java.awt.Dimension(88, 52));
        btnTrades.setMinimumSize(new java.awt.Dimension(88, 52));
        btnTrades.setPreferredSize(new java.awt.Dimension(88, 52));
        btnTrades.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTradesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 16);
        panelCTRLPanel.add(btnTrades, gridBagConstraints);

        btnAllocations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/elle/elle_gui/images/button3.png"))); // NOI18N
        btnAllocations.setText("Allocations");
        btnAllocations.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAllocations.setMaximumSize(new java.awt.Dimension(88, 52));
        btnAllocations.setMinimumSize(new java.awt.Dimension(88, 52));
        btnAllocations.setPreferredSize(new java.awt.Dimension(88, 52));
        btnAllocations.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAllocations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllocationsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 16);
        panelCTRLPanel.add(btnAllocations, gridBagConstraints);

        btnSymbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/elle/elle_gui/images/filter.png"))); // NOI18N
        btnSymbol.setText(" ");
        btnSymbol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSymbol.setMaximumSize(new java.awt.Dimension(35, 33));
        btnSymbol.setMinimumSize(new java.awt.Dimension(35, 33));
        btnSymbol.setPreferredSize(new java.awt.Dimension(35, 33));
        btnSymbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSymbolActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panelCTRLPanel.add(btnSymbol, gridBagConstraints);

        btnDateRange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/elle/elle_gui/images/filter.png"))); // NOI18N
        btnDateRange.setText(" ");
        btnDateRange.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDateRange.setIconTextGap(0);
        btnDateRange.setMaximumSize(new java.awt.Dimension(35, 33));
        btnDateRange.setMinimumSize(new java.awt.Dimension(35, 33));
        btnDateRange.setPreferredSize(new java.awt.Dimension(35, 33));
        btnDateRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateRangeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 10);
        panelCTRLPanel.add(btnDateRange, gridBagConstraints);

        checkBoxSymbol.setText("Symbol");
        checkBoxSymbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxSymbolActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 0, 0, 0);
        panelCTRLPanel.add(checkBoxSymbol, gridBagConstraints);

        checkBoxDateRange.setText("Date Range");
        checkBoxDateRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxDateRangeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 50, 0, 0);
        panelCTRLPanel.add(checkBoxDateRange, gridBagConstraints);

        labelHyphen.setText("-");
        labelHyphen.setMaximumSize(new java.awt.Dimension(4, 30));
        labelHyphen.setMinimumSize(new java.awt.Dimension(4, 30));
        labelHyphen.setPreferredSize(new java.awt.Dimension(4, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 6);
        panelCTRLPanel.add(labelHyphen, gridBagConstraints);

        textFieldSymbol.setMaximumSize(new java.awt.Dimension(95, 30));
        textFieldSymbol.setMinimumSize(new java.awt.Dimension(95, 30));
        textFieldSymbol.setPreferredSize(new java.awt.Dimension(95, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelCTRLPanel.add(textFieldSymbol, gridBagConstraints);

        textFieldStartDate.setMaximumSize(new java.awt.Dimension(95, 30));
        textFieldStartDate.setMinimumSize(new java.awt.Dimension(95, 30));
        textFieldStartDate.setPreferredSize(new java.awt.Dimension(95, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 0, 0);
        panelCTRLPanel.add(textFieldStartDate, gridBagConstraints);

        textFieldEndDate.setMaximumSize(new java.awt.Dimension(95, 30));
        textFieldEndDate.setMinimumSize(new java.awt.Dimension(95, 30));
        textFieldEndDate.setPreferredSize(new java.awt.Dimension(95, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelCTRLPanel.add(textFieldEndDate, gridBagConstraints);

        btnPositions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/elle/elle_gui/images/button1.png"))); // NOI18N
        btnPositions.setText("Positions");
        btnPositions.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPositions.setMaximumSize(new java.awt.Dimension(88, 52));
        btnPositions.setMinimumSize(new java.awt.Dimension(88, 52));
        btnPositions.setPreferredSize(new java.awt.Dimension(88, 52));
        btnPositions.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPositions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPositionsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 16);
        panelCTRLPanel.add(btnPositions, gridBagConstraints);

        labelRecords.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelRecords.setText("records label");
        labelRecords.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panelCTRLPanel.add(labelRecords, gridBagConstraints);

        btnTableDisplayState.setForeground(new java.awt.Color(51, 153, 0));
        btnTableDisplayState.setText("Default Views");
        btnTableDisplayState.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTableDisplayStateMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panelCTRLPanel.add(btnTableDisplayState, gridBagConstraints);

        btnClearAllFilters.setActionCommand("Clear All Filters");
        btnClearAllFilters.setLabel("Clear All Filters");
        btnClearAllFilters.setMinimumSize(new java.awt.Dimension(99, 28));
        btnClearAllFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllFiltersActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 50, 0, 10);
        panelCTRLPanel.add(btnClearAllFilters, gridBagConstraints);

        panelSQL.setLayout(new java.awt.GridBagLayout());

        scrollPaneSQL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        textAreaSQL.setBackground(new java.awt.Color(153, 255, 204));
        textAreaSQL.setColumns(20);
        textAreaSQL.setRows(5);
        textAreaSQL.setText("Please input an SQL statement:\n>>");
        textAreaSQL.setText("Please input an SQL statement:\n>>");
        ((AbstractDocument) textAreaSQL.getDocument())
        .setDocumentFilter(new CreateDocumentFilter(33));
        scrollPaneSQL.setViewportView(textAreaSQL);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1157;
        gridBagConstraints.ipady = 73;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panelSQL.add(scrollPaneSQL, gridBagConstraints);

        btnEnterSQL.setText("Enter");
        btnEnterSQL.setMaximumSize(new java.awt.Dimension(93, 23));
        btnEnterSQL.setMinimumSize(new java.awt.Dimension(93, 23));
        btnEnterSQL.setPreferredSize(new java.awt.Dimension(93, 23));
        btnEnterSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterSQLActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelSQL.add(btnEnterSQL, gridBagConstraints);

        btnClearSQL.setText("Clear");
        btnClearSQL.setMaximumSize(new java.awt.Dimension(93, 23));
        btnClearSQL.setMinimumSize(new java.awt.Dimension(93, 23));
        btnClearSQL.setPreferredSize(new java.awt.Dimension(93, 23));
        btnClearSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSQLActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 9);
        panelSQL.add(btnClearSQL, gridBagConstraints);

        btnShowTables.setText("Show Tables");
        btnShowTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowTablesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 2, 0);
        panelSQL.add(btnShowTables, gridBagConstraints);

        panelAccounts.setMinimumSize(new java.awt.Dimension(400, 200));
        panelAccounts.setPreferredSize(new java.awt.Dimension(1200, 400));
        panelAccounts.setLayout(new java.awt.GridBagLayout());

        tabbedPaneAccounts.setMinimumSize(new java.awt.Dimension(400, 200));
        tabbedPaneAccounts.setPreferredSize(new java.awt.Dimension(1200, 400));
        tabbedPaneAccounts.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneAccountsStateChanged(evt);
            }
        });

        panelIB9048.setMinimumSize(new java.awt.Dimension(400, 200));
        panelIB9048.setPreferredSize(new java.awt.Dimension(1200, 400));

        scrollPaneIB9048.setMinimumSize(new java.awt.Dimension(400, 200));
        scrollPaneIB9048.setPreferredSize(new java.awt.Dimension(1200, 400));

        tableIB9048.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableIB9048.setMinimumSize(new java.awt.Dimension(400, 200));
        tableIB9048.setPreferredSize(new java.awt.Dimension(1200, 400));
        scrollPaneIB9048.setViewportView(tableIB9048);

        javax.swing.GroupLayout panelIB9048Layout = new javax.swing.GroupLayout(panelIB9048);
        panelIB9048.setLayout(panelIB9048Layout);
        panelIB9048Layout.setHorizontalGroup(
            panelIB9048Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIB9048Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneIB9048, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIB9048Layout.setVerticalGroup(
            panelIB9048Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIB9048Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(scrollPaneIB9048, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPaneAccounts.addTab("IB9048", panelIB9048);

        scrollPaneTOS3622.setPreferredSize(new java.awt.Dimension(1300, 402));

        tableTOS3622.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneTOS3622.setViewportView(tableTOS3622);

        javax.swing.GroupLayout panelTOS3622Layout = new javax.swing.GroupLayout(panelTOS3622);
        panelTOS3622.setLayout(panelTOS3622Layout);
        panelTOS3622Layout.setHorizontalGroup(
            panelTOS3622Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1195, Short.MAX_VALUE)
            .addGroup(panelTOS3622Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPaneTOS3622, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE))
        );
        panelTOS3622Layout.setVerticalGroup(
            panelTOS3622Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
            .addGroup(panelTOS3622Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTOS3622Layout.createSequentialGroup()
                    .addComponent(scrollPaneTOS3622, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tabbedPaneAccounts.addTab("TOS3622", panelTOS3622);

        scrollPaneCombined.setPreferredSize(new java.awt.Dimension(1200, 400));

        tableCombined.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableCombined.setMinimumSize(new java.awt.Dimension(1200, 400));
        tableCombined.setPreferredSize(new java.awt.Dimension(1200, 400));
        scrollPaneCombined.setViewportView(tableCombined);

        javax.swing.GroupLayout panelCombinedLayout = new javax.swing.GroupLayout(panelCombined);
        panelCombined.setLayout(panelCombinedLayout);
        panelCombinedLayout.setHorizontalGroup(
            panelCombinedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1195, Short.MAX_VALUE)
            .addGroup(panelCombinedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPaneCombined, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE))
        );
        panelCombinedLayout.setVerticalGroup(
            panelCombinedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
            .addGroup(panelCombinedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCombinedLayout.createSequentialGroup()
                    .addComponent(scrollPaneCombined, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tabbedPaneAccounts.addTab("Combined", panelCombined);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        panelAccounts.add(tabbedPaneAccounts, gridBagConstraints);

        informationLabel.setText("Information Label");
        informationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        informationLabel.setMaximumSize(new java.awt.Dimension(250, 14));
        informationLabel.setMinimumSize(new java.awt.Dimension(200, 14));
        informationLabel.setOpaque(true);
        informationLabel.setPreferredSize(new java.awt.Dimension(250, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 0);
        panelAccounts.add(informationLabel, gridBagConstraints);

        menuBar.setMargin(new java.awt.Insets(0, 0, 0, 5));

        menuFile.setText("File");

        menuItemVersion.setText("Version");
        menuItemVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVersionActionPerformed(evt);
            }
        });
        menuFile.add(menuItemVersion);

        menuItemRead.setText("Read from CSV File");
        menuItemRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemReadActionPerformed(evt);
            }
        });
        menuFile.add(menuItemRead);

        menuPrint.setText("Print");

        menuItemPrintGUI.setText("Print GUI");
        menuItemPrintGUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintGUIActionPerformed(evt);
            }
        });
        menuPrint.add(menuItemPrintGUI);

        menuItemPrintDisplayWindow.setText("Print Display Window");
        menuItemPrintDisplayWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPrintDisplayWindowActionPerformed(evt);
            }
        });
        menuPrint.add(menuItemPrintDisplayWindow);

        menuFile.add(menuPrint);

        menuItemSave.setText("Save Tab to CSV");
        menuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveActionPerformed(evt);
            }
        });
        menuFile.add(menuItemSave);

        menuItemLogOut.setText("Log out");
        menuItemLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLogOutActionPerformed(evt);
            }
        });
        menuFile.add(menuItemLogOut);

        menuBar.add(menuFile);

        menuEdit.setText("Edit");

        menuItemConnection.setText("Connection...");
        menuItemConnection.setEnabled(false);
        menuItemConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemConnectionActionPerformed(evt);
            }
        });
        menuEdit.add(menuItemConnection);

        jMenuItem1.setText("Manage DB");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuEdit.add(jMenuItem1);

        menuConnections.setText("Select Connections");

        menuItemAWS.setText("AWS");
        menuItemAWS.setEnabled(false);
        menuItemAWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAWSActionPerformed(evt);
            }
        });
        menuConnections.add(menuItemAWS);

        menuItemPupone.setText("Pupone");
        menuItemPupone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPuponeActionPerformed(evt);
            }
        });
        menuConnections.add(menuItemPupone);

        menuItemLocal.setText("Local");
        menuItemLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLocalActionPerformed(evt);
            }
        });
        menuConnections.add(menuItemLocal);

        menuEdit.add(menuConnections);

        menuBar.add(menuEdit);

        menuFind.setText("Find");
        menuBar.add(menuFind);

        menuReports.setText("Reports");
        menuBar.add(menuReports);

        menuTools.setText("Tools");

        menuItemBackup.setText("Backup Tables");
        menuItemBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBackupActionPerformed(evt);
            }
        });
        menuTools.add(menuItemBackup);

        menuItemReloadTabData.setText("Reload Tab Data");
        menuItemReloadTabData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemReloadTabDataActionPerformed(evt);
            }
        });
        menuTools.add(menuItemReloadTabData);

        menuBar.add(menuTools);

        menuLoad.setText("Load");

        menuItemLoadFile.setText("Load File...");
        menuItemLoadFile.setEnabled(false);
        menuItemLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadFileActionPerformed(evt);
            }
        });
        menuLoad.add(menuItemLoadFile);

        menuBar.add(menuLoad);

        menuHelp.setText("Help");
        menuBar.add(menuHelp);

        menuView.setText("View");

        menuItemCheckBoxLog.setText("Log");
        menuItemCheckBoxLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCheckBoxLogActionPerformed(evt);
            }
        });
        menuView.add(menuItemCheckBoxLog);

        menuItemCheckBoxSQL.setText("SQL Command");
        menuItemCheckBoxSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCheckBoxSQLActionPerformed(evt);
            }
        });
        menuView.add(menuItemCheckBoxSQL);

        menuItemAViewATrades.setText("Display Record");
        menuItemAViewATrades.setToolTipText("");
        menuItemAViewATrades.setEnabled(false);
        menuItemAViewATrades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAViewATradesActionPerformed(evt);
            }
        });
        menuView.add(menuItemAViewATrades);

        menuItemIB.setText("Display IB_8949-All Fields");
        menuItemIB.setEnabled(false);
        menuItemIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemIBActionPerformed(evt);
            }
        });
        menuView.add(menuItemIB);

        menuItemTL.setText("Display TL_8949-All Fields");
        menuItemTL.setEnabled(false);
        menuItemTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTLActionPerformed(evt);
            }
        });
        menuView.add(menuItemTL);

        menuItemLoadsTable.setText("Display Loads Table");
        menuItemLoadsTable.setEnabled(false);
        menuItemLoadsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadsTableActionPerformed(evt);
            }
        });
        menuView.add(menuItemLoadsTable);

        viewmatches.setText("View Matches");
        viewmatches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewmatchesActionPerformed(evt);
            }
        });
        menuView.add(viewmatches);

        viewnomatches.setText("View noMatches");
        viewnomatches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewnomatchesActionPerformed(evt);
            }
        });
        menuView.add(viewnomatches);

        menuItemDefaultColumnWidths.setText("Default Column Widths");
        menuItemDefaultColumnWidths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDefaultColumnWidthsActionPerformed(evt);
            }
        });
        menuView.add(menuItemDefaultColumnWidths);

        menuBar.add(menuView);

        menuOther.setText("Other");

        menuItemIB8949.setText("IB 8949");
        menuItemIB8949.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemIB8949ActionPerformed(evt);
            }
        });
        menuOther.add(menuItemIB8949);

        menuItemReconcile.setText("Reconcile 8949s");
        menuItemReconcile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemReconcileActionPerformed(evt);
            }
        });
        menuOther.add(menuItemReconcile);

        menuItemTL8949.setText("TL 8949");
        menuItemTL8949.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTL8949ActionPerformed(evt);
            }
        });
        menuOther.add(menuItemTL8949);

        menuBar.add(menuOther);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(panelAccounts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCTRLPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelCTRLPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelAccounts, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSQL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelAccounts.getAccessibleContext().setAccessibleParent(panelAccounts);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTradesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTradesActionPerformed

        String tabName = getSelectedTabName();

        // set the trades table as selected
        tabs.get(tabName).get(POSITIONS_TABLE_NAME).setTableSelected(false);
        tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).setTableSelected(false);
        tabs.get(tabName).get(TRADES_TABLE_NAME).setTableSelected(true);

        AccountTable accountTable = tabs.get(tabName).get(TRADES_TABLE_NAME);
        JTable table = accountTable.getTable();
        TableFilter filter = accountTable.getFilter();
        JScrollPane scroll = new JScrollPane(table);
        // update button colors
        btnPositions.setBackground(colorBtnDefault);
        btnTrades.setBackground(colorBtnSelected);
        btnAllocations.setBackground(colorBtnDefault);
        
        // update view label
        btnTableDisplayState.setText(accountTable.getView());

        // change panel table to positions
        JPanel panel = getSelectedTabPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        // apply filter for the positions table
        filter.applyFilter();
        filter.applyColorHeaders();

        // update records label
        String recordsText = accountTable.getRecordsLabel();
        labelRecords.setText(recordsText);
        
        //disable the View Record option on the view menu if no records are selected
        //Corinne Martus 7/7/2016
        disableMenuItemAViewATrades();
    }//GEN-LAST:event_btnTradesActionPerformed

    private void menuItemPuponeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPuponeActionPerformed

        loginWindow = new LoginWindow();
        loginWindow.setLocationRelativeTo(this);
        loginWindow.setVisible(true);
    }//GEN-LAST:event_menuItemPuponeActionPerformed

    private void menuItemLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLocalActionPerformed

        loginWindow = new LoginWindow();
        loginWindow.setLocationRelativeTo(this);
        loginWindow.setVisible(true);
    }//GEN-LAST:event_menuItemLocalActionPerformed

    private void menuItemAWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAWSActionPerformed

    }//GEN-LAST:event_menuItemAWSActionPerformed
                                                                                    
    private void menuItemReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemReadActionPerformed

        int returnVal = filechooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            String filename = filechooser.getSelectedFile().getName();
            String extension = filename.substring(filename.lastIndexOf("."), filename.length());

            if (!".csv".equals(extension)) {
                JOptionPane.showMessageDialog(filechooser, "Invalid file type!!! Please choose a csv file!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                String[] columnNames = new String[0];
                Object[][] data = new Object[0][0];

                String line = "";
                String splitSign = ",";
                int i = 0;
                try {
                    //initialize the data array
                    BufferedReader br
                            = new BufferedReader(
                                    new FileReader(file));
                    while (br.readLine() != null) {
                        i++;
                    }
                    br.close();
                    data = new Object[i - 1][];

                    i = 0;
                    br = new BufferedReader(new FileReader(file));
                    line = br.readLine();
                    columnNames = line.split(splitSign);
                    String[] rowNumber = {"#"};
                    System.arraycopy(rowNumber, 0, columnNames, 0, 1);
                    line = br.readLine();
                    while (line != null) {
                        data[i] = new Object[line.split(splitSign).length + 1];
                        data[i][0] = i + 1;
                        for (int j = 1; j < data[i].length; j++) {
                            data[i][j] = line.split(splitSign)[j - 1];
                        }
                        i++;
                        line = br.readLine();
                    }
                } catch (FileNotFoundException ex) {
                    LoggingAspect.afterThrown(ex);
                } catch (IOException ex) {
                    LoggingAspect.afterThrown(ex);
                }

                read_csv readcsvfiles = new read_csv(data, columnNames);
                readcsvfiles.setTitle(filename);
                readcsvfiles.setVisible(true);
                readcsvfiles.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }
    }//GEN-LAST:event_menuItemReadActionPerformed

    private void menuItemPrintGUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintGUIActionPerformed

        print(this);
    }//GEN-LAST:event_menuItemPrintGUIActionPerformed

    private void menuItemPrintDisplayWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPrintDisplayWindowActionPerformed

        // this should print table actually
        print(panelAccounts);

    }//GEN-LAST:event_menuItemPrintDisplayWindowActionPerformed

    private void menuItemLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadFileActionPerformed

    }//GEN-LAST:event_menuItemLoadFileActionPerformed

    private void btnAllocationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllocationsActionPerformed

        String tabName = getSelectedTabName();
        
        // set the allocations table as selected
        tabs.get(tabName).get(POSITIONS_TABLE_NAME).setTableSelected(false);
        tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).setTableSelected(true);
        tabs.get(tabName).get(TRADES_TABLE_NAME).setTableSelected(false);
        
        AccountTable accountTable = tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME);
        JTable table = accountTable.getTable();
        TableFilter filter = accountTable.getFilter();
        JScrollPane scroll = new JScrollPane(table);

        // update button colors
        btnPositions.setBackground(colorBtnDefault);
        btnTrades.setBackground(colorBtnDefault);
        btnAllocations.setBackground(colorBtnSelected);
        
        // update view label
        btnTableDisplayState.setText(accountTable.getView());

        // set the allocations table as selected
        tabs.get(tabName).get(POSITIONS_TABLE_NAME).setTableSelected(false);
        tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).setTableSelected(true);
        tabs.get(tabName).get(TRADES_TABLE_NAME).setTableSelected(false);

        // change panel table to positions
        JPanel panel = getSelectedTabPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        // apply filter for the positions table
        filter.applyFilter();
        filter.applyColorHeaders();

        // update records label
        String recordsText = accountTable.getRecordsLabel();
        labelRecords.setText(recordsText);
        
        //disable the View Record option on the view menu if no records are selected
        //Corinne Martus 7/7/2016
        disableMenuItemAViewATrades();
    }//GEN-LAST:event_btnAllocationsActionPerformed

    private void menuItemIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemIBActionPerformed

    }//GEN-LAST:event_menuItemIBActionPerformed

    private void menuItemTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTLActionPerformed

    }//GEN-LAST:event_menuItemTLActionPerformed

    private void menuItemLoadsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadsTableActionPerformed

    }//GEN-LAST:event_menuItemLoadsTableActionPerformed

    private void menuItemConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemConnectionActionPerformed

    }//GEN-LAST:event_menuItemConnectionActionPerformed

    private void menuItemReconcileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemReconcileActionPerformed

    }//GEN-LAST:event_menuItemReconcileActionPerformed

    private void btnSymbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSymbolActionPerformed

        applySymbolSearchFilter();

    }//GEN-LAST:event_btnSymbolActionPerformed

    private void btnDateRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateRangeActionPerformed

        applyDateRangeFilter();
    }//GEN-LAST:event_btnDateRangeActionPerformed

    /**
     * applyDateRangeFilter
     */
    private void applyDateRangeFilter() {
        String startDate = textFieldStartDate.getText();
        String endDate = textFieldEndDate.getText();
        String errorMsg = "Not a valid ";
        boolean isError = false;
        Component component = this;
        Date startDateRange = null;
        Date endDateRange = null;

        if (Validator.isValidDate(startDate)) {
            if (Validator.isValidDate(endDate)) {
                // parse the dates
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    startDateRange = simpleDateFormat.parse(startDate);
                    endDateRange = simpleDateFormat.parse(endDate);
                    // execute filter
                    AccountTable tab = getSelectedTab();
                    TableFilter filter = tab.getFilter();
                    int dateColumnIndex = filter.getDateColumnIndex();
                    filter.removeFilterItems(dateColumnIndex);
                    filter.addDateRange(startDateRange, endDateRange);
                    filter.applyFilter();
                    // apply checkbox selection
                    boolean isFiltering = filter.isDateRangeFiltering();
                    checkBoxDateRange.setSelected(isFiltering);
                    // update records label
                    String recordsLabelStr = tab.getRecordsLabel();
                    labelRecords.setText(recordsLabelStr);
                } catch (ParseException e) {
                    LoggingAspect.afterThrown(e);
                }
            } else {
                isError = true;
                errorMsg += "end date range";
                component = textFieldEndDate;
            }
        } else {
            isError = true;
            errorMsg += "start date range";
            component = textFieldStartDate;
        }

        if (isError) {
            errorMsg += "\nDate format not correct: YYYY-MM-DD";
            JOptionPane.showMessageDialog(component, errorMsg);
            checkBoxDateRange.setSelected(false);
        }
    }

    /**
     * btnEnterSQLActionPerformed
     *
     * @param evt
     */
    private void btnEnterSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterSQLActionPerformed

        int commandStart = textAreaSQL.getText().lastIndexOf(">>") + 2;
        String command = textAreaSQL.getText().substring(commandStart);
        if(sqlOutputWindow == null){
            sqlOutputWindow = new SqlOutputWindow(command,this); 
        }
        else{
            sqlOutputWindow.setLocationRelativeTo(this);
            sqlOutputWindow.toFront();
            sqlOutputWindow.setTableModel(command);
            sqlOutputWindow.setVisible(true);
        }

    }//GEN-LAST:event_btnEnterSQLActionPerformed

    private void btnClearSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSQLActionPerformed

        ((AbstractDocument) textAreaSQL.getDocument())
                .setDocumentFilter(new CreateDocumentFilter(0));
        textAreaSQL.setText("Please input an SQL statement:\n>>");
        ((AbstractDocument) textAreaSQL.getDocument())
                .setDocumentFilter(new CreateDocumentFilter(33));
    }//GEN-LAST:event_btnClearSQLActionPerformed

    private void menuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File As");
        int userSelection = fileChooser.showSaveDialog(this);
        
        AccountTable selectedTab = getSelectedTab();
        JTable selectedTable = selectedTab.getTable();
        TableModel model = selectedTable.getModel();

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".csv");

                for (int i = 0; i < model.getColumnCount(); i++) {
                    if (model.getColumnName(i) == null) {
                        writer.write("" + ",");
                    } else {
                        writer.write(model.getColumnName(i) + ",");
                    }
                }
                
                writer.write("\n");
                
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        if (model.getValueAt(i, j) == null) {
                            writer.write("" + ",");
                        } else {
                            writer.write(model.getValueAt(i, j).toString() + ",");
                        }
                    }
                    writer.write("\n");
                }
                writer.close();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(selectedTable, "Error Writing File.\nFile may be in use by another application."
                        + "\nCheck and try re-exporting", "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuItemSaveActionPerformed

    private void checkBoxDateRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxDateRangeActionPerformed

        if (checkBoxDateRange.isSelected()) {
            applyDateRangeFilter();
        } else {
            AccountTable tab = getSelectedTab();
            TableFilter filter = tab.getFilter();
            int dateColumnIndex = filter.getDateColumnIndex();
            filter.removeFilterItems(dateColumnIndex);
            filter.applyFilter();
            // update records label
            String recordsLabelStr = tab.getRecordsLabel();
            labelRecords.setText(recordsLabelStr);
            // apply checkbox selection
            boolean isFiltering = filter.isDateRangeFiltering();
            checkBoxDateRange.setSelected(isFiltering);
        }
    }//GEN-LAST:event_checkBoxDateRangeActionPerformed

    private void checkBoxSymbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxSymbolActionPerformed

        if (checkBoxSymbol.isSelected()) {
            applySymbolSearchFilter();
        } else {
            AccountTable tab = getSelectedTab();
            TableFilter filter = tab.getFilter();
            // clear symbol search filter
            int underlyingColumnIndex = filter.getUnderlyingColumnIndex();
            filter.removeFilterItems(underlyingColumnIndex);
            int symbolColumnIndex = filter.getSymbolColumnIndex();
            filter.removeColorHeader(symbolColumnIndex);
            filter.applyFilter();
            // update records label
            String recordsLabelStr = tab.getRecordsLabel();
            labelRecords.setText(recordsLabelStr);
        }
    }//GEN-LAST:event_checkBoxSymbolActionPerformed

    private void btnClearAllFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllFiltersActionPerformed

        // clear all filters
        //String tabName = getSelectedTabName();
        AccountTable tab = getSelectedTab();
        TableFilter filter = tab.getFilter();
        filter.clearAllFilters();
        filter.applyFilter();
        filter.applyColorHeaders();

        // apply checkbox selection
        boolean isFiltering = filter.isDateRangeFiltering();
        checkBoxDateRange.setSelected(isFiltering);

        // set label record information
        String recordsLabel = tab.getRecordsLabel();
        labelRecords.setText(recordsLabel);
    }//GEN-LAST:event_btnClearAllFiltersActionPerformed

    private void menuItemIB8949ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemIB8949ActionPerformed

    }//GEN-LAST:event_menuItemIB8949ActionPerformed

    private void menuItemTL8949ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTL8949ActionPerformed

    }//GEN-LAST:event_menuItemTL8949ActionPerformed

    private void btnPositionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPositionsActionPerformed

        String tabName = getSelectedTabName();

        // set the positions table as selected
        tabs.get(tabName).get(POSITIONS_TABLE_NAME).setTableSelected(true);
        tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).setTableSelected(false);
        tabs.get(tabName).get(TRADES_TABLE_NAME).setTableSelected(false);

        AccountTable accountTable = tabs.get(tabName).get(POSITIONS_TABLE_NAME);
        JTable table = accountTable.getTable();
        TableFilter filter = accountTable.getFilter();
        JScrollPane scroll = new JScrollPane(table);
        // update button colors
        btnPositions.setBackground(colorBtnSelected);
        btnTrades.setBackground(colorBtnDefault);
        btnAllocations.setBackground(colorBtnDefault);
        
        // update view label
        btnTableDisplayState.setText(accountTable.getView());

        // change panel table to positions
        JPanel panel = getSelectedTabPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        // apply filter for the positions table
        filter.applyFilter();
        filter.applyColorHeaders();

        // update records label
        String recordsText = accountTable.getRecordsLabel();
        labelRecords.setText(recordsText);
        
        //disable the View Record option on the view menu if no records are selected
        //Corinne Martus 7/7/2016
        disableMenuItemAViewATrades();
    }//GEN-LAST:event_btnPositionsActionPerformed

    private void menuItemCheckBoxSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCheckBoxSQLActionPerformed
        /**
         * ************* Strange behavior ************************* The
         * jPanelSQL.getHeight() is the height before the
         * jCheckBoxMenuItemViewSQLActionPerformed method was called.
         *
         * The jPanelSQL.setVisible() does not change the size of the sql panel
         * after it is executed.
         *
         * The jPanel size will only change after the
         * jCheckBoxMenuItemViewSQLActionPerformed is finished.
         *
         * That is why the the actual integer is used rather than getHeight().
         *
         * Example: jPanelSQL.setVisible(true); jPanelSQL.getHeight(); // this
         * returns 0
         */

        if (menuItemCheckBoxSQL.isSelected()) {

            // show sql panel
            panelSQL.setVisible(true);
            this.setSize(this.getWidth(), 493 + 128);

        } else {

            // hide sql panel
            panelSQL.setVisible(false);
            this.setSize(this.getWidth(), 493);
        }
    }//GEN-LAST:event_menuItemCheckBoxSQLActionPerformed

    private void menuItemCheckBoxLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCheckBoxLogActionPerformed
        if (menuItemCheckBoxLog.isSelected()) {

            logWindow.setLocationRelativeTo(this);
            logWindow.setVisible(true); // show log window

            // remove check if window is closed from the window
            logWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    menuItemCheckBoxLog.setSelected(false);
                }
            });
        } else {
            // hide log window
            logWindow.setVisible(false);
        }
    }//GEN-LAST:event_menuItemCheckBoxLogActionPerformed

    private void tabbedPaneAccountsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneAccountsStateChanged
        // called by init components so just skip if tabs is null
        if (tabs != null) {

            String tabName = getSelectedTabName(); // account name
            AccountTable accountTable = null;      // the account table
            String tableName = "";                 // table name
            
            if (tabs.get(tabName).get(POSITIONS_TABLE_NAME).isTableSelected()) {
                tableName = POSITIONS_TABLE_NAME;
                accountTable = tabs.get(tabName).get(tableName);
                // update button colors
                btnPositions.setBackground(colorBtnSelected);
                btnTrades.setBackground(colorBtnDefault);
                btnAllocations.setBackground(colorBtnDefault);
            } else if (tabs.get(tabName).get(TRADES_TABLE_NAME).isTableSelected()) {
                tableName = TRADES_TABLE_NAME;
                accountTable = tabs.get(tabName).get(tableName);
                // update button colors
                btnTrades.setBackground(colorBtnSelected);
                btnPositions.setBackground(colorBtnDefault);
                btnAllocations.setBackground(colorBtnDefault);
            } else if (tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).isTableSelected()) {
                tableName = ALLOCATIONS_TABLE_NAME;
                accountTable = tabs.get(tabName).get(tableName);
                // update button colors
                btnPositions.setBackground(colorBtnDefault);
                btnTrades.setBackground(colorBtnDefault);
                btnAllocations.setBackground(colorBtnSelected);
            }
            
            // set view label
            btnTableDisplayState.setText(accountTable.getView());
            
            // display correct table
            displayTable(tabName, tableName);  // shows the correct table depending on accountTable and button selected}
        }
    }//GEN-LAST:event_tabbedPaneAccountsStateChanged

    private void menuItemAViewATradesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAViewATradesActionPerformed
        AccountTable selectedTab = getSelectedTab();
        JTable selectedTable = selectedTab.getTable();
        String tableName = selectedTab.getTableName();
        int row = selectedTable.getSelectedRow();
        aTradeInView = new ATrade(row, selectedTab);
        viewATradeWindow = new ViewATradeWindow(aTradeInView, tableName);
        viewATradeWindow.setVisible(true);
    }//GEN-LAST:event_menuItemAViewATradesActionPerformed

    /**
     * This method is invoked when the label is clicked for default views
     * @param evt 
     */
    private void btnTableDisplayStateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTableDisplayStateMouseClicked
        
        /**
         * toggle design [all fields, default view]
         */
        JLabel viewLabel = (JLabel) evt.getComponent();
        String tabName = getSelectedTabName();
        String tableName = getTableName(tabName);
        AccountTable accountTable = tabs.get(tabName).get(tableName);

        if (viewLabel.getText().equals(VIEW_LABEL_TXT_ALL_FIELDS)) {
            accountTable.setView(VIEW_LABEL_TXT_DEFAULT_VIEW);
            accountTable.setAllFields(false);
            btnTableDisplayState.setText(accountTable.getView());
        }
        else{
            accountTable.setView(VIEW_LABEL_TXT_ALL_FIELDS);
            accountTable.setAllFields(true);
            btnTableDisplayState.setText(accountTable.getView());
        }

        setColumnFormat(accountTable.getTable());
        displayTable(tabName, tableName);
    }//GEN-LAST:event_btnTableDisplayStateMouseClicked

    private void menuItemBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBackupActionPerformed
        // open new connection
        DBConnection.close(); // connection might be timed out on server
        if (DBConnection.open()) {  // open a new connection
            BackupDBTablesDialog backupDBTables = new BackupDBTablesDialog(this);
        } else {
            JOptionPane.showMessageDialog(this, "Could not connect to Database");
        }
    }//GEN-LAST:event_menuItemBackupActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        EditDatabaseWindow editDBWindow = new EditDatabaseWindow();
        editDBWindow.setLocationRelativeTo(this);
        editDBWindow.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnShowTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowTablesActionPerformed
        String sqlCommand = "show tables;";

        System.out.println("sql output window = " + sqlOutputWindow);
        if(sqlOutputWindow == null){
            sqlOutputWindow = new SqlOutputWindow(sqlCommand,this); 
        }
        else{
            sqlOutputWindow.setLocationRelativeTo(this);
            sqlOutputWindow.toFront();
            sqlOutputWindow.setTableModel(sqlCommand);
            sqlOutputWindow.setVisible(true);
        }
    }//GEN-LAST:event_btnShowTablesActionPerformed

    private void viewmatchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewmatchesActionPerformed
        loadtablematchesornomatches("select * from matches");
    }//GEN-LAST:event_viewmatchesActionPerformed

    private void viewnomatchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewnomatchesActionPerformed
        loadtablematchesornomatches("select * from noMatches");
    }//GEN-LAST:event_viewnomatchesActionPerformed

    private void menuItemReloadTabDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemReloadTabDataActionPerformed
       reloadDataAction();
    }//GEN-LAST:event_menuItemReloadTabDataActionPerformed

    /**
     * 
     * @param evt 
     */
    private void menuItemLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLogOutActionPerformed
              Object[] options = {"Reconnect", "Log Out"};  // the titles of buttons

        int n = JOptionPane.showOptionDialog(this, "Would you like to reconnect?", "Log off",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        switch (n) {
            case 0: {               // Reconnect

                // create a new Login Window
                loginWindow = new LoginWindow();
                loginWindow.setLocationRelativeTo(this);
                loginWindow.setVisible(true);

                // dispose of this Object and return resources
                this.dispose();

                break;
            }
            case 1:
                System.exit(0); // Quit
        }
    }//GEN-LAST:event_menuItemLogOutActionPerformed

    private void menuItemVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVersionActionPerformed
        JOptionPane.showMessageDialog(this, "Creation Date: "
                + creationDate + "\n"
                + "Version: " + version);
    }//GEN-LAST:event_menuItemVersionActionPerformed

    private void menuItemDefaultColumnWidthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDefaultColumnWidthsActionPerformed
       adjustCurrentTabColumnWidths(); 
    }//GEN-LAST:event_menuItemDefaultColumnWidthsActionPerformed
    
    private void menuItemObjectTableActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        //Wei 2016/07/03 using the same functions designed for loading matches or noMatchs and csv reading. 
        //loadltableojects("select * from tableObjects");
        
        viewTableObjects tableobjectswindow = new viewTableObjects();        
        tableobjectswindow.setLocation(200,200);
        tableobjectswindow.setVisible(true);
        tableobjectswindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }                                                   
    
    //Returns the columns of all of the tables in the selected tab to their default widths
    public void adjustCurrentTabColumnWidths(){
        for(String table: tables)
        adjustTableColumnWidths(table);
    }
    
    // Returns columns of the the specified table in selected tab to their default widths
    public void adjustTableColumnWidths(String tableName){
        String tabName = getSelectedTabName();
        JTable table = tabs.get(tabName).get(tableName).getTable();
        TableColumnAdjuster tableColumnAdjuster = new TableColumnAdjuster(table);
        tableColumnAdjuster.adjustColumns();
    }
    
    // Corinne Martus  
    //June 30, 2016
    //Called after tables load to display an alert message for 
    //any column changes on the server
    public void displayTableColumnAlert(){
        if (hasUnexpectedColumnAlert.containsValue(true) 
                || hasDeletedColumnAlert.containsValue(true)){
            System.out.print(tableColumnAlertMessage + "\n");
            
            if(hasUnexpectedColumnAlert.containsValue(true)){
                System.out.print("\n" + unexpectedColumnLog + "\n");
            }
            if (hasDeletedColumnAlert.containsValue(true)){
                System.out.print("\n" + missingColumnLog + "\n");
            }
            
            
            
            Object[] optionViewLog = {"View Log", "OK"};
            
         
            int n = JOptionPane.showOptionDialog(null, tableColumnAlertMessage, 
                    "Alert", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, optionViewLog, optionViewLog[0] );
           
                if(n == 0){
                LogWindow logWindow = new LogWindow();
                logWindow.setLocationRelativeTo(this);
                logWindow.readCurrentMessages(tableColumnAlertMessage + "\n");
                logWindow.readCurrentMessages("\n" + unexpectedColumnLog + "\n");
                logWindow.readCurrentMessages("\n" + missingColumnLog + "\n");
                logWindow.setVisible(true); // show log window

                // remove check if window is closed from the window
                logWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        menuItemCheckBoxLog.setSelected(false);
                    }
                });
            } 
        }
    }
    
    private void reloadDataAction() {
        // reload modified table data into dropdown list
        loadTables(tabs);
    }
    
    /**
     * initTotalRowCounts called once to initialize the total rowIndex counts of
     * each tabs table
     *
     * @param tabs
     * @return
     */
    public Map<String, AccountTable> initTotalRowCounts(Map<String, AccountTable> tabs) {

        int totalRecords;

        boolean isFirstTabRecordLabelSet = false;

        for (Map.Entry<String, AccountTable> entry : tabs.entrySet()) {
            AccountTable tab = tabs.get(entry.getKey());
            JTable table = tab.getTable();
            totalRecords = table.getRowCount();
            tab.setTotalRecords(totalRecords);

            if (isFirstTabRecordLabelSet == false) {
                String recordsLabel = tab.getRecordsLabel();
                labelRecords.setText(recordsLabel);
                isFirstTabRecordLabelSet = true; // now its set
            }
        }

        return tabs;
    }
    
    //set the timer for information Label show
    public  void startCountDownFromNow(int waitSeconds) {
        Timer timer = new Timer(waitSeconds * 1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                informationLabel.setText("v."+version);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * loadTables This method takes a tabs Map and loads all the tabs/tables
     *
     * @param tabs
     * @return
     */
    public Map<String, Map<String, AccountTable>> loadTables(Map<String, Map<String, AccountTable>> tabs) {

        for (Map.Entry<String, Map<String, AccountTable>> tabEntry : tabs.entrySet()) {
            String accountName = tabEntry.getKey();
            System.out.println("1: " + accountName);
            Map<String, AccountTable> tables = tabs.get(accountName);
            for (Map.Entry<String, AccountTable> tableEntry : tables.entrySet()) {

                String tableName = tableEntry.getKey();
                AccountTable tab = tables.get(tableName);
                JTable table = tab.getTable();
                System.out.println("2: " + tableName);
                loadTable(table, tableName, accountName);
                //setTableListeners(tab);
                // set initial total records
                int totalRecords = table.getRowCount();
                tab.setTotalRecords(totalRecords);
                setInformationLabel("Tables loaded succesfully", 10);
            }
            
        }

        return tabs;
    }
    

    /**
     * loadTable This method takes a table and loads it Does not need to pass
     * the table back since it is passed by reference However, it can make the
     * code clearer and it's good practice to return
     *
     * @param table
     */
    public JTable loadTable(JTable table, String tableName, String accountName) {
        TableFromServer tableFromServer;
        DefaultTableModel tableModel = new DefaultTableModel();
        // check what table it is
        switch (tableName) {
            case TRADES_TABLE_NAME:
                tableFromServer = new TableFromServer(tableName, accountName, TRADE_COLUMN_NAME_CONSTANTS,
                hasDeletedColumnAlert, hasUnexpectedColumnAlert);
                break;
            case POSITIONS_TABLE_NAME:
                tableFromServer = new TableFromServer(tableName, accountName, POSITIONS_COLUMN_NAME_CONSTANTS,
                hasDeletedColumnAlert, hasUnexpectedColumnAlert);
                break;
            case ALLOCATIONS_TABLE_NAME:
                tableFromServer = new TableFromServer(tableName, accountName, ALLOCATIONS_COLUMN_NAME_CONSTANTS,
                hasDeletedColumnAlert, hasUnexpectedColumnAlert);
                break;
            default:
                System.out.print("Not a valid table name");
                throw new RuntimeException();
        }
        //Update maps so that alerts will not display more than once for this table 
        hasDeletedColumnAlert = tableFromServer.updatehasDeletedColumnAlert();
        hasUnexpectedColumnAlert = tableFromServer.updatehasUnexpectedColumnAlert();
        
        //Update log messages
        missingColumnLog += tableFromServer.getMissingColumnLog();
        unexpectedColumnLog += tableFromServer.getUnexpectedColumnLog();
        
        //store any alert messages for changes to the table's column(s) on the server
        tableColumnAlertMessage += tableFromServer.getTableColumnAlert();
        
        tableModel = tableFromServer.getTableModel();
        EditableTableModel etm = new EditableTableModel(tableModel, tableFromServer.getColumnClasses());
        table.setModel(etm);
        
        // check that the filter items are initialized
        AccountTable accountTable = tabs.get(accountName).get(tableName);
        TableFilter filter = accountTable.getFilter();
        if (filter.getFilterItems() == null) {
            filter.initFilterItems();
        }

        // apply filter
        filter.applyFilter();
        filter.applyColorHeaders();

        // load all checkbox items for the checkbox column pop up filter
        ColumnPopupMenu columnPopupMenu = accountTable.getColumnPopupMenu();
        columnPopupMenu.loadAllCheckBoxItems();

        setColumnFormat(table);

        // set the listeners for the table
        setTableListeners(accountTable);

        // format the table
        formatTable(table);

        return table;
    }
    
    /**
     * Inserts a new rowIndex in the table
     * @param table
     * @param trade 
     */
    public void inserTableRow(JTable table, Trade trade) {

        Object[] rowData = new Object[39];
        rowData[0] = trade.getId();
        rowData[1] = trade.getTradeTime();
        rowData[2] = trade.getOc();
        rowData[3] = trade.getLs();
        rowData[4] = trade.getSymbol();
        rowData[5] = trade.getQ();
        rowData[6] = trade.getPrice();
        rowData[7] = trade.getComm();
        rowData[8] = trade.getProceeds();
        rowData[9] = trade.getBasis();
        rowData[10] = trade.getAdjProceeds();
        rowData[11] = trade.getProcessed();
        rowData[12] = trade.getLotTime();
        rowData[13] = trade.getRealizedPl();
        rowData[14] = trade.getCodes();
        rowData[15] = trade.getKsflag();
        rowData[16] = trade.getNotes();
        rowData[17] = trade.getAccount();
        rowData[18] = trade.getYr();
        rowData[19] = trade.getFileCode();
        rowData[20] = trade.getInputLine();
        rowData[21] = trade.getLocked();
        rowData[22] = trade.getMflag();
        rowData[23] = trade.getSecType();
        rowData[24] = trade.getMulti();
        rowData[25] = trade.getUnderlying();
        rowData[26] = trade.getExpiry();
        rowData[27] = trade.getStrike();
        rowData[28] = trade.getoType();
        rowData[29] = trade.getBkrGroup();
        rowData[30] = trade.getStategy();
        rowData[31] = trade.getxChange();
        rowData[32] = trade.getOrder();
        rowData[33] = trade.getFills();
        rowData[34] = trade.getTotalQ();
        rowData[35] = trade.gettGrp();
        rowData[36] = trade.getMatching();
        rowData[37] = trade.getMethod();
        rowData[38] = trade.getTimeStamp();
        ((DefaultTableModel)table.getModel()).addRow(rowData);
    }

    /**
     * Inserts a new rowIndex in the table
     * @param table
     * @param position 
     */
    public void inserTableRow(JTable table, Position position) {

        Object[] rowData = new Object[32];
        rowData[0] = position.getPosId();
        rowData[1] = position.getSymbol();
        rowData[2] = position.getLotTime();
        rowData[3] = position.getQ();
        rowData[4] = position.getLine();
        rowData[5] = position.getOce();
        rowData[6] = position.getOceTime();
        rowData[7] = position.getLs();
        rowData[9] = position.getPriceAdj();
        rowData[10] = position.getBasisAdj();
        rowData[11] = position.getPrice();
        rowData[12] = position.getBasis();
        rowData[13] = position.getHow();
        rowData[14] = position.gettId();
        rowData[15] = position.getWash();
        rowData[16] = position.getKsflag();
        rowData[17] = position.getCodes();
        rowData[18] = position.getAccount();
        rowData[19] = position.getYr();
        rowData[20] = position.getlCodes();
        rowData[21] = position.getSecType();
        rowData[22] = position.getMulti();
        rowData[23] = position.getUnderlying();
        rowData[24] = position.getExpiry();
        rowData[25] = position.getStrike();
        rowData[26] = position.getoType();
        rowData[27] = position.getNotes();
        rowData[28] = position.getFileCode();
        rowData[29] = position.getInputLine();
        rowData[30] = position.getGrp();
        rowData[31] = position.getTimeStamp();
        ((DefaultTableModel)table.getModel()).addRow(rowData);
    }
    
    /**
     * Inserts a new rowIndex in the table
     * @param table
     * @param allocation 
     */
    public void inserTableRow(JTable table, Allocation allocation) {

        Object[] rowData = new Object[15];
        rowData[0] = allocation.getId();
        rowData[1] = allocation.getSymbol();
        rowData[2] = allocation.getTradeTime();
        rowData[3] = allocation.getTradeQ();
        rowData[4] = allocation.getTradePrice();
        rowData[5] = allocation.getMethod();
        rowData[6] = allocation.getMathQ();
        rowData[7] = allocation.getMathProceeds();
        rowData[8] = allocation.getLotTime();
        rowData[9] = allocation.getLine();
        rowData[10] = allocation.getPriceAdj();
        rowData[11] = allocation.getMatchBasis();
        rowData[12] = allocation.getRealizedPL();
        rowData[13] = allocation.getTerm();
        rowData[14] = allocation.getAccount();
        ((DefaultTableModel)table.getModel()).addRow(rowData);
    }
    
    /**
     * formatTable This formats the table
     *
     * @author Xiaoqian Fu
     * @param table
     */
    private void formatTable(JTable table) {

        tableHeaderRenderer(table); // Make table headers align CENTER

        tableCellAlignment(table);   // Make table cells align CENTER

        tableCellDecimalFormat(table); // Make table cells have four decimals

        tableTimeCellFormat(table);
        // Make the table cells which shows time have "yyyy-mm-dd hh:mm:ss" format

        //rename selected columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equalsIgnoreCase("wash")) {
                table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue("W");
            }
        }
    }

    /**
     * setScrollBarFormat This formats the scroll bar so that it is always
     * visible. This fixes the default behavior because the scrollbar becomes
     * smaller and smaller until it dissappears.
     *
     * @param scroll
     */
    private void setScrollBarFormat(JScrollPane scroll, JTable table) {

        scroll.setViewportView(table);
        scroll.setPreferredSize(new Dimension(924, 900));
    }

    /**
     * tableHeaderRenderer This is the table header renderer
     *
     * @author Xiaoqian Fu
     * @param table
     */
    private void tableHeaderRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.removeAll();
        header.setDefaultRenderer(new HeaderRenderer(table));
    }

    /**
     * tableCellDecimalFormat This is the table cell decimal format
     *
     * @author Xiaoqian Fu
     * @param table
     */
    private void tableCellDecimalFormat(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {

            TableColumn tableColumnI = table.getColumnModel().getColumn(i);

            if (table.getColumnName(i).toLowerCase().contains("price") || table.getColumnName(i).equalsIgnoreCase("q")
                    || table.getColumnName(i).equalsIgnoreCase("basis") || table.getColumnName(i).equalsIgnoreCase("price_adj") 
                    || table.getColumnName(i).equalsIgnoreCase("basis_adj"))
               {
                tableColumnI.setCellRenderer(new priceFormatRenderer());
            } else if (table.getColumnName(i).equalsIgnoreCase("totalq")) {
                tableColumnI.setCellRenderer(new TwoDecimalFormatRenderer());
            } else if (table.getColumnClass(i).getName().toLowerCase().contains("decimal")
                    || table.getColumnClass(i).getName().toLowerCase().contains("integer")
                    || table.getColumnName(i).contains("proceeds")
                    || table.getColumnName(i).contains("realized_PL")){
                tableColumnI.setCellRenderer(new CommaFormatRenderer());
            }
        }
    }

    /**
     * tableCellAlignment This is the table cell alignment
     *
     * @author Xiaoqian Fu
     * @param table
     */
    private void tableCellAlignment(JTable table) {

        for (int i = 0; i < table.getColumnCount(); i++) {

            TableColumn tableColumnI = table.getColumnModel().getColumn(i);

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            if (table.getColumnName(i).toLowerCase().equals("price")
                    || table.getColumnName(i).toLowerCase().equals("adj_price")
                    || table.getColumnName(i).toLowerCase().equals("q")
                    || table.getColumnName(i).toLowerCase().equals("basis")
                    || table.getColumnName(i).toLowerCase().equals("strike")
                    || table.getColumnName(i).toLowerCase().equals("adj_basis")
                    || table.getColumnName(i).toLowerCase().equals("totalq")
                    || table.getColumnName(i).toLowerCase().equals("realized_pl")
                    || table.getColumnName(i).toLowerCase().equals("CommTax")
                    || table.getColumnName(i).toLowerCase().equals("proceeds")) {

                renderer.setHorizontalAlignment(SwingConstants.RIGHT);

            } else {

                renderer.setHorizontalAlignment(SwingConstants.CENTER);

            }

            tableColumnI.setCellRenderer(renderer);

        }
    }

    /**
     * tableTimeCellFormat This is the table time cell format
     *
     * @author Xiaoqian Fu
     * @param table
     */
    private void tableTimeCellFormat(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {

            if (table.getColumnName(i).toLowerCase().contains("time")) {
//                System.out.println(i + " " + table.getColumnSystemName(i));

                table.getColumnModel().getColumn(i).setCellRenderer(new DataRenderer());

            }
        }
    }

    /**
     * 
     * @param sql 
     */
    //Wei general class function for loading tables from mysql server. 
    private void loadtablematchesornomatches(String sql) {
        Vector data = new Vector();
        Vector columnNames = new Vector();
        Vector columnClass = new Vector();
        int columns;

        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        try {
            rs = statement.executeQuery(sql);
            metaData = rs.getMetaData();
        } catch (Exception ex) {
            System.out.println("error");
            LoggingAspect.afterThrown(ex);
            //return table;
        }
        try {
            columns = metaData.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                columnClass.addElement(metaData.getColumnClassName(i));
                columnNames.addElement(metaData.getColumnName(i));
//                System.out.println(metaData.getColumnName(i) + " original: " + metaData.getColumnClassName(i));
            }
            while (rs.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
            }
            rs.close();

        } catch (SQLException ex) {
            LoggingAspect.afterThrown(ex);
        }
        
        String tablename = sql.substring(14);

        read_csv readcsvfiles = new read_csv(data, columnNames);
        readcsvfiles.setTitle(tablename);
        readcsvfiles.setVisible(true);
        readcsvfiles.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private String removeAnyCommas(String src) {
        if(src == null) {
            return "";
        }
        
        for(int i = 0; i < src.length(); i++) {
            if(src.charAt(i) == ',') {
                src = src.substring(0,i) + src.substring(i+1, src.length());
            }
        }
        
        return src;    
    }

    /**
     * returns the selected table name for the tab
     * @param tabName name of the selected tab
     * @return name of the selected table for the selected tab
     */
    private String getTableName(String tabName) {
        if(tabs.get(tabName).get(TRADES_TABLE_NAME).isTableSelected()){
            return TRADES_TABLE_NAME;
        }
        else if(tabs.get(tabName).get(POSITIONS_TABLE_NAME).isTableSelected()){
            return POSITIONS_TABLE_NAME;
        }
        else if(tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).isTableSelected()){
            return ALLOCATIONS_TABLE_NAME;
        }
        else{
            return null;
        }
    }

    /**
     * DataRenderer 
     * This removes the .0 at the end of the original value in the data object.
     * @author Xiaoqian Fu
     */
    private static class DataRenderer extends DefaultTableCellRenderer {

        public DataRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            // change value
            if(value == null){
                value = "";
            }
            else{
                String s = value.toString();
                value = s.replace(".0", "");
            }

            return super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, col);
        }
    }

    /**
     * HeaderRenderer This is the header renderer
     *
     * @author Xiaoqian Fu
     */
    private static class HeaderRenderer implements TableCellRenderer {

        DefaultTableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            if(table.getTableHeader().getDefaultRenderer() instanceof DefaultTableCellRenderer){
            renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
        }
        }
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            return renderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, col);
        }

    }

    /**
     * DecimalFormatRenderer This is the four decimal format renderer
     *
     * @author Xiaoqian Fu
     */
    private static class TwoDecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("###,###.00");

        public TwoDecimalFormatRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            Double doubleValue = 0.0;

            if (value != null) {
                doubleValue = Double.parseDouble(value.toString());
                value = formatter.format((Number) doubleValue);
            }

            return super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, col);
        }

    }

    /**
     * DecimalFormatRenderer This is the decimal format renderer
     *
     * @author Xiaoqian Fu
     */
    private static class CommaFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("###,###.##");

        public CommaFormatRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            Double doubleValue = 0.0;

            if (value != null) {
                doubleValue = Double.parseDouble(value.toString());
                value = formatter.format((Number) doubleValue);
            }

            return super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, col);
        }

    }

    /**
     * DecimalFormatRenderer This is the decimal format renderer
     *
     * @author Xiaoqian Fu
     */
    private static class FourDecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("###,###.0000");

        public FourDecimalFormatRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            Double doubleValue = 0.0;

            if (value != null) {
                doubleValue = Double.parseDouble(value.toString());
                value = formatter.format((Number) doubleValue);
            }

            return super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, col);
        }

    }
    
    /**
     * DecimalFormatRenderer This is the format renderer for price column only
     * 
     * (including decimal formatting, tooltiptext and tooltiplocaltion settings)
     * 
     *
     * @author Wei Ren
     */
    
    private static class priceFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("###,###.00");

        public priceFormatRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            Double doubleValue = 0.0;
            
            if (value != null) {
                doubleValue = Double.parseDouble(value.toString());
                super.setToolTipText((String) value);
                value = formatter.format((Number) doubleValue);
            }

            return super.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, col);
        }

    }
    
    /**
     * setTableListeners This adds mouselisteners and keylisteners to tables.
     *
     * @param table
     */
    public void setTableListeners(final AccountTable tab) {

        JTable table = tab.getTable();
        ColumnPopupMenu columnPopupMenu = tab.getColumnPopupMenu();

        // this adds a mouselistener to the table header
        JTableHeader header = table.getTableHeader();
        
         //disable default mouse listeners
         MouseListener[] listeners = header.getMouseListeners();
         
         /* removes MouseInputHandler since it alters the behavior of toggleSortOrder()
         Corinne Martus, 7/7/16: Changed from deleting the entire BasicTableHeaderUI class 
         (this disables dragging and resizing columns)
         to only the MouseInputHandler*/
        for (MouseListener ml: listeners)
        {
            String className = ml.getClass().toString();

            if (className.contains("BasicTableHeaderUI.MouseInputHandler"))
               
                header.removeMouseListener(ml);
                
           
        }
        
        if (header != null) {
            header.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (e.getClickCount() == 2) {
                        e.consume();
                        clearFilterDoubleClick(e, table);
                        

                    }
                    
                    if (e.getClickCount() == 1 && !e.isConsumed() && e.isControlDown()) {
                        e.consume();
                        columnPopupMenu.showPopupMenu(e);
                        
                    }
                    
                    
                    if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                        
                        int columnIndex = header.columnAtPoint(e.getPoint());
                        if (columnIndex != -1) {
                                columnIndex = table.convertColumnIndexToModel(columnIndex);
                                table.getRowSorter().toggleSortOrder(columnIndex);
                                //System.out.println("clicked " + columnIndex);
                                
                        }
                        e.consume();
                    }
                }

                /**
                 * Popup menus are triggered differently on different platforms
                 * Therefore, isPopupTrigger should be checked in both
                 * mousePressed and mouseReleased events for proper
                 * cross-platform functionality.
                 *
                 * @param e
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        // this calls the column popup menu
                        columnPopupMenu.showPopupMenu(e);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        // this calls the column popup menu
                        columnPopupMenu.showPopupMenu(e);
                    }
                }
            });
        }

        // add mouselistener to the table
        table.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        // if left mouse clicks
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (e.getClickCount() == 2) {
                                filterByDoubleClick(table);
//                                System.out.println(table.getName());
//                                for (int i = 0; i < table.getModel().getRowCount(); i++) {
//                                    System.out.println(accountTable.getIDList()[i] + " and " + table.getModel().getValueAt(i, 1));
//                                }
                            } else if (e.getClickCount() == 1) {
//                                if (jLabelEdit.getText().equals("ON ")) {
//                                    selectAllText(e);
//                                }
                                enableMenuItemAViewATrades();
                                
                            }
                        } // end if left mouse clicks
                        // if right mouse clicks
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            if (e.getClickCount() == 2) {

                                // make table editable
                                //makeTableEditable(true);
                                // get selected cell
                                int columnIndex = table.columnAtPoint(e.getPoint()); // this returns the column i
                                int rowIndex = table.rowAtPoint(e.getPoint()); // this returns the rowIndex i
                                if (rowIndex != -1 && columnIndex != -1) {

                                    // make it the active editing cell
                                    table.changeSelection(rowIndex, columnIndex, false, false);

                                    selectAllText(e);

                                } // end not null condition

                            } // end if 2 clicks 
                        } // end if right mouse clicks

                    }// end mouseClicked

                    private void selectAllText(MouseEvent e) {// Select all text inside jTextField

                        JTable table = (JTable) e.getComponent();
                        int row = table.getSelectedRow();
                        int column = table.getSelectedColumn();
                        if (column != 0) {
                            table.getComponentAt(row, column).requestFocus();
                            table.editCellAt(row, column);
                            JTextField selectCom = (JTextField) table.getEditorComponent();
                            if (selectCom != null) {
                                selectCom.requestFocusInWindow();
                                selectCom.selectAll();
                            }
                        }

                    }
                }
        );

        // add table model listener
        table.getModel().addTableModelListener(new TableModelListener() {  // add table model listener every time the table model reloaded
            @Override
            public void tableChanged(TableModelEvent e) {
                //TODO
            }
        });

        // add keyListener to the table
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_F2) {

                    // I believe this is meant to toggle edit mode
                    // so I passed the conditional
                    //makeTableEditable(jLabelEdit.getText().equals("ON ")?false:true);
                }
            }
        });
    }

    /**
     * setTableListeners This method overloads the seTerminalFunctions to take
     * tabs instead of a single table
     *
     * @param tabs
     * @return
     */
    public Map<String, AccountTable> setTableListeners(Map<String, AccountTable> tabs) {

        for (Map.Entry<String, AccountTable> entry : tabs.entrySet()) {
            setTableListeners(tabs.get(entry.getKey()));
        }
        return tabs;
    }

    /**
     * filterByDoubleClick this selects the item double clicked on to be
     * filtered
     *
     * @param table
     */
    public void filterByDoubleClick(JTable table) {

        //int columnIndex = table.getSelectedColumn(); // this returns the column i
        
        //Wei, make the filter working with underlying only.
        int columnIndex = 20; 
                
        int rowIndex = table.getSelectedRow(); // this returns the rowIndex i
        if (rowIndex != -1) {
            Object selectedField = table.getValueAt(rowIndex, columnIndex);
            AccountTable tab = getSelectedTab();
            TableFilter filter = tab.getFilter();
            
            
            filter.addFilterItem(columnIndex, selectedField);
            filter.applyFilter();
            String recordsLabel = tab.getRecordsLabel();
            labelRecords.setText(recordsLabel);
            labelRecords.repaint();

            // apply checkbox selection
            int dateColIndex = filter.getDateColumnIndex();
            if (columnIndex == dateColIndex) {
                // this is no longer using a date range filter if applicable
                checkBoxDateRange.setSelected(false);
            }
        }
    }

    /**
     * clearFilterDoubleClick This clears the filters for that column by double
     * clicking on that column header.
     */
    private void clearFilterDoubleClick(MouseEvent e, JTable table) {

        int columnIndex = table.getColumnModel().getColumnIndexAtX(e.getX());
        //String tabName = getSelectedTabName();
        AccountTable tab = getSelectedTab();
        TableFilter filter = tab.getFilter();

        // clear column filter
        int symbolColumnIndex = filter.getSymbolColumnIndex();
        if (columnIndex == symbolColumnIndex) {
            int underlyingColumnIndex = filter.getUnderlyingColumnIndex();
            filter.clearColFilter(underlyingColumnIndex);
            filter.removeColorHeader(symbolColumnIndex);
            checkBoxSymbol.setSelected(false);
        } else {
            filter.clearColFilter(columnIndex);
        }
        filter.applyFilter();

        // update records label
        String recordsLabel = tab.getRecordsLabel();
        labelRecords.setText(recordsLabel);
    }
    /*called by setTableListeners on a single click
    enables the View Record option on the view menu tab when a row is selected
    by Corinne Martus  7/7/2016 */
    public void enableMenuItemAViewATrades(){
        AccountTable selectedTab = getSelectedTab();
        JTable selectedTable = selectedTab.getTable();
        int row = selectedTable.getSelectedRow();
        if(row != -1){
            menuItemAViewATrades.setEnabled(true);
        }
        
    }
    
    /*called by setTableListeners to disable
    the View Record option on the view menu tab when a row is selected
    by Corinne Martus  7/7/2016 */
    public void disableMenuItemAViewATrades(){
        AccountTable selectedTab = getSelectedTab();
        JTable selectedTable = selectedTab.getTable();
        int row = selectedTable.getSelectedRow();
        if(row == -1){
            menuItemAViewATrades.setEnabled(false);
        }
    }
    
    /**
     * setColumnFormat sets column format for each table
     *
     * @param width
     * @param table
     */
    
    public void setColumnFormat(JTable table) {
        // this is needed for the horizontal scrollbar to appear
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         
         TableColumnAdjuster tableColumnAdjuster = new TableColumnAdjuster(table);
         tableColumnAdjuster.adjustColumns();
         
        
         /*int colWidth;
        for (int i = 0; i < table.getColumnCount(); i++) {

            TableColumn column = table.getColumnModel().getColumn(i);
            String tableColName = table.getColumnName(i);
            colWidth = colWidths.get(tableColName);
            column.setMinWidth(colWidth);
            column.setMaxWidth(colWidth + 100);
            column.setPreferredWidth(colWidth);
        }
                 });*/
                 }

    /**
     * applySymbolSearchFilter apply symbol search filter.
     */
    private void applySymbolSearchFilter() {

        // get selected accountTable
        AccountTable tab = getSelectedTab();

        // apply filter for the symbol
        String filterItem = textFieldSymbol.getText();
        TableFilter filter = tab.getFilter();
        int colIndexUL = filter.getUnderlyingColumnIndex();   // underlying column i
        filter.addFilterItem(colIndexUL, filterItem);
        filter.removeColorHeader(colIndexUL);
        filter.applyFilter();
        int colIndexSymbol = filter.getSymbolColumnIndex();  // symbol column i
        filter.addColorHeader(colIndexSymbol);

        checkBoxSymbol.setSelected(true);

        // update records label
        String recordsLabel = tab.getRecordsLabel();
        labelRecords.setText(recordsLabel);
    }

    /**
     * print prints the component passed (either JFrame or JPanel)
     *
     * @param component
     */
    public void print(Component component) {

        PrinterJob job = PrinterJob.getPrinterJob();

        if (component instanceof JFrame) {
            job.setPrintable(new PrintWindow((JFrame) component));
        } else if (component instanceof JPanel) {
            job.setPrintable(new PrintWindow((JPanel) component));
        }

        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.pageDialog(job.defaultPage());
                LoggingAspect.addLogMsgWthDate("Start to print the display window...");
                job.print();
                LoggingAspect.addLogMsgWthDate(job.getJobName()
                        + " is successfully printed!\n");
            } catch (PrinterException ex) {
                LoggingAspect.afterThrown(ex);
            }
        }
    }

    /**
     * displayTable Displays the correct table depending on the accountTable and button
 selected
     */
    public void displayTable(String tabName, String tableName) {

        // get the account table
        AccountTable accountTable = tabs.get(tabName).get(tableName);
        JTable table = accountTable.getTable();
        TableFilter filter = accountTable.getFilter();
        JScrollPane scroll = new JScrollPane(table);
        setScrollBarFormat(scroll, table);            // fix issue with scroll bar dissappearing

        // change panel table
        JPanel panel = getSelectedTabPanel();         // accountTable panel used to display the account table
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        // apply filter for the positions table
        filter.applyFilter();
        filter.applyColorHeaders();

        // update records label
        String recordsText = accountTable.getRecordsLabel();
        labelRecords.setText(recordsText);
    }

    /**
     * getSelectedTab Returns the selected accountTable and table selected
     *
     * @return
     */
    public AccountTable getSelectedTab() {
        String tabName = getSelectedTabName();
        if (tabs.get(tabName).get(POSITIONS_TABLE_NAME).isTableSelected()) {
            return tabs.get(tabName).get(POSITIONS_TABLE_NAME);
        } else if (tabs.get(tabName).get(TRADES_TABLE_NAME).isTableSelected()) {
            return tabs.get(tabName).get(TRADES_TABLE_NAME);
        } else if (tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME).isTableSelected()) {
            return tabs.get(tabName).get(ALLOCATIONS_TABLE_NAME);
        }
        // this should never be reached
        return new AccountTable();
    }

    /**
     * ************************************************************************
     ******************* SETTERS AND GETTERS **********************************
     * ************************************************************************
     */
    public Map<String, Map<String, AccountTable>> getTabs() {
        return tabs;
    }

    public void setTabs(Map<String, Map<String, AccountTable>> tabs) {
        this.tabs = tabs;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        ELLE_GUI_Frame.statement = statement;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    
    public void setServer(String server){
        this.server = server;
    }

     public void showDatabase() {
        databaseLabel = new JLabel(
            server + "." + database);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(databaseLabel);
    }
     
     public JLabel getInformationLabel() {
        return this.informationLabel;
    }

    public void setInformationLabel(String inf, int second) {
        informationLabel.setText(inf);
        startCountDownFromNow(second);
    }
     
    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public static ELLE_GUI_Frame getInstance() {
        return instance;
    }

    public JLabel getLabelNumOfRecords() {
        return labelRecords;
    }

    public LogWindow getLogWindow() {
        return logWindow;
    }

    public void setLogWindow(LogWindow logWindow) {

        this.logWindow = logWindow;
    }

    public String getSelectedTabName() {
        return tabbedPaneAccounts.getTitleAt(tabbedPaneAccounts.getSelectedIndex());
    }

    public JPanel getSelectedTabPanel() {

        // not sure of the i of the table
        //return (JTable)tabbedPaneAccounts.getComponentAt(0);
        // temporary test code
        String tabName = getSelectedTabName();

        if (tabName == "IB9048") {
            return panelIB9048;
        } else if (tabName == "TOS3622") {
            return panelTOS3622;
        } else {
            return panelCombined;
        }
    }
    

    @SuppressWarnings("unused")
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAllocations;
    private java.awt.Button btnClearAllFilters;
    private javax.swing.JButton btnClearSQL;
    private javax.swing.JButton btnDateRange;
    private javax.swing.JButton btnEnterSQL;
    private javax.swing.JButton btnPositions;
    private javax.swing.JButton btnShowTables;
    private javax.swing.JButton btnSymbol;
    private javax.swing.JLabel btnTableDisplayState;
    private javax.swing.JButton btnTrades;
    private javax.swing.JCheckBox checkBoxDateRange;
    private javax.swing.JCheckBox checkBoxSymbol;
    private javax.swing.JFileChooser filechooser;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel labelHyphen;
    private javax.swing.JLabel labelRecords;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuConnections;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuFind;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAViewATrades;
    private javax.swing.JMenuItem menuItemAWS;
    private javax.swing.JMenuItem menuItemBackup;
    private javax.swing.JCheckBoxMenuItem menuItemCheckBoxLog;
    private javax.swing.JCheckBoxMenuItem menuItemCheckBoxSQL;
    private javax.swing.JMenuItem menuItemConnection;
    private javax.swing.JMenuItem menuItemDefaultColumnWidths;
    private javax.swing.JMenuItem menuItemIB;
    private javax.swing.JMenuItem menuItemIB8949;
    private javax.swing.JMenuItem menuItemLoadFile;
    private javax.swing.JMenuItem menuItemLoadsTable;
    private javax.swing.JMenuItem menuItemLocal;
    private javax.swing.JMenuItem menuItemLogOut;
    private javax.swing.JMenuItem menuItemPrintDisplayWindow;
    private javax.swing.JMenuItem menuItemPrintGUI;
    private javax.swing.JMenuItem menuItemPupone;
    private javax.swing.JMenuItem menuItemRead;
    private javax.swing.JMenuItem menuItemReconcile;
    private javax.swing.JMenuItem menuItemReloadTabData;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemTL;
    private javax.swing.JMenuItem menuItemTL8949;
    private javax.swing.JMenuItem menuItemVersion;
    private javax.swing.JMenu menuLoad;
    private javax.swing.JMenu menuOther;
    private javax.swing.JMenu menuPrint;
    private javax.swing.JMenu menuReports;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenu menuView;
    private javax.swing.JPanel panelAccounts;
    private javax.swing.JPanel panelCTRLPanel;
    private javax.swing.JPanel panelCombined;
    private javax.swing.JPanel panelIB9048;
    private javax.swing.JPanel panelSQL;
    private javax.swing.JPanel panelTOS3622;
    private javax.swing.JScrollPane scrollPaneCombined;
    private javax.swing.JScrollPane scrollPaneIB9048;
    private javax.swing.JScrollPane scrollPaneSQL;
    private javax.swing.JScrollPane scrollPaneTOS3622;
    private javax.swing.JTabbedPane tabbedPaneAccounts;
    private javax.swing.JTable tableCombined;
    private javax.swing.JTable tableIB9048;
    private javax.swing.JTable tableTOS3622;
    private javax.swing.JTextArea textAreaSQL;
    private javax.swing.JTextField textFieldEndDate;
    private javax.swing.JTextField textFieldStartDate;
    private javax.swing.JTextField textFieldSymbol;
    private javax.swing.JMenuItem viewmatches;
    private javax.swing.JMenuItem viewnomatches;
    // End of variables declaration//GEN-END:variables

}

