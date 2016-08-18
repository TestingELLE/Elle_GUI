package com.elle.elle_gui.presentation;

import com.elle.elle_gui.database.DataManager;
import com.elle.elle_gui.database.DataManagerFactory;
import com.elle.elle_gui.entities.RecordSet;
import java.awt.Component;

/**
 * Singleton utility class that receives a SQL statement or a tableName and 
 * retrieves the corresponding RecordSet from the DataManager, 
 * creates a RecordSetTableModel with the RecordSet,
 * and creates a TableDisplayWindow to display the data
 * @author corinne
 * 8/13/2016
 */
public class TableDataDisplay {
    private static final TableDataDisplay INSTANCE = new TableDataDisplay();
    private final DataManager dataManager;
    private TableDataDisplay(){
       dataManager = DataManagerFactory.getDataManager();
    }
    
    public static TableDataDisplay getInstance(){
        return INSTANCE;
    }
    
    //recieves a SQL statement and displays the requested data in a TableDisplayWindow
    public void displayTableFromSQL(String sql, Component parentComponent){
        RecordSet recordSet = dataManager.getRecordSetFromSQL(sql);
       RecordSetTableModel model = new RecordSetTableModel(recordSet);
       TableDisplayWindow tableDisplayWindow = new TableDisplayWindow(model,parentComponent);
       tableDisplayWindow.setTitle("SQL Output");
    }
    
    public void displayTable(String tableName, Component parentComponent){
      RecordSet recordSet = dataManager.getRecordSet(tableName);
      RecordSetTableModel model = new RecordSetTableModel(recordSet);
      TableDisplayWindow tableDisplayWindow = new TableDisplayWindow(model, parentComponent);
      tableDisplayWindow.setTitle(tableName);
    }
}
