package com.elle.elle_gui.logic;
import com.elle.elle_gui.database.DBConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Corinne Martus
 */
public class TableFromServer  {
    Vector data = new Vector();
    Vector columnNamesFromDatabase = new Vector();
    Vector columnNames = new Vector();
     Class[] columnClasses;
    int columnCount;
    ResultSet rs = null;
    ResultSetMetaData metaData = null;
    String sql = "";
    String tableName;
    String accountName;
    private static Statement statement; 
    ArrayList constants = new ArrayList();
    DefaultTableModel tableModel = new DefaultTableModel();
    Map<String, String> columnNameConstants = new HashMap<String, String>();
    Map<String, Boolean> hasDeletedColumnAlert = new HashMap<String,Boolean>();
    Map<String, Boolean> hasUnexpectedColumnAlert = new HashMap<String,Boolean>();
    String tableColumnAlert = "";
    String missingColumnLog;
    String unexpectedColumnLog;
   
     //Constructor
    public TableFromServer(String tableName, String accountName, Map<String, String> columnNameConstants, 
            Map<String, Boolean> hasDeletedColumnAlert, Map<String, 
            Boolean> hasUnexpectedColumnAlert){
        this.tableName = tableName;
        this.accountName = accountName;
        this.columnNameConstants = columnNameConstants;
        statement = DBConnection.getStatement();
        setSQLStatement();
        setResultSet();
        setMetaData();
        setColumnCount();
        columnClasses = null;
        setColumnNamesFromDatabase();
        this.hasDeletedColumnAlert = hasDeletedColumnAlert;
        this.hasUnexpectedColumnAlert = hasUnexpectedColumnAlert;
        unexpectedColumnLog = "";
        missingColumnLog = "";
        setColumnNames();
        setDataVector();
        setTableModel();
     }
    
    private void setSQLStatement(){
        if (accountName == "Combined") {
            sql = "SELECT * FROM " + tableName
                    + " ORDER BY symbol ASC";
        } else {
            sql = "SELECT * FROM " + tableName
                    + " WHERE Account = '" + accountName
                    + "' ORDER BY symbol ASC";
        }
    }
    
    public String getSQLStatement(){
        return sql;
    }
    
    public void setResultSet(){
        try {
            rs = statement.executeQuery(sql);
        } catch (Exception ex) {
            System.out.println("error");
            LoggingAspect.afterThrown(ex);
            //return table;
        }
    }
    
    public ResultSet getResultSet(){
        return rs;
    }
    
    private void setMetaData(){
        try {
            metaData = rs.getMetaData();
        }
        catch (SQLException ex) {
            LoggingAspect.afterThrown(ex);
        }
    }
    
    public ResultSetMetaData getMetaData(){
        return metaData;
    }
    
    public Class[] getColumnClasses(){
        return columnClasses;
    }
    
    private void setColumnCount(){
        try {
            columnCount = metaData.getColumnCount();
        }
        catch (SQLException ex) {
            LoggingAspect.afterThrown(ex);
        }
    }
    
    public int getColumnCount(){
        return columnCount;
    }
    
    private void setColumnNamesFromDatabase(){
       try {
            for (int i = 1; i <= columnCount; i++){
                columnNamesFromDatabase.addElement(metaData.getColumnName(i));
            }
        } catch (SQLException ex) {
            LoggingAspect.afterThrown(ex);
        } 
    }
    
    public Vector getColumnNamesFromDatabase(){
        return columnNamesFromDatabase;
    }
    
    private void setColumnNames(){
        String unexpectedColumns = "";
        String missingColumns = "";
        Vector newColumnNames = new Vector();
        Boolean newColumn = false;
        Boolean missingColumn = false;
        for (int col = 0; col < columnCount; col++){
            Object key = columnNamesFromDatabase.get(col);
            if (columnNameConstants.containsKey(key)){
                columnNames.add(columnNameConstants.get(key));
            }
            // a new column exists on the server
            else{
                newColumnNames.add(key);
                newColumn = true;
                if(!hasUnexpectedColumnAlert.get(tableName)){
                 unexpectedColumns += key + "\n";
                }
            }
        }
        
        if (unexpectedColumns.length() > 1){
            unexpectedColumnLog = "\nUnexpected columns in the " + tableName + " table: \n" + unexpectedColumns;
        }
        
        //add the new columns to the end of the column vector
        if (newColumnNames != null){
            columnNames.addAll(newColumnNames);
        }
        
        if(newColumn && !hasUnexpectedColumnAlert.get(tableName)){            
           tableColumnAlert +=  "Unexpected column(s) exists in the " + tableName+ " table on the server-\n"
                        + " This column(s) will be added to the end of the " + tableName+ " table\n\n";
            
            //Update map so that this alert will not display again for this table
            hasUnexpectedColumnAlert.put(tableName,true);
        }
        //Set of the expected database column names
        Set<String> expectedColumnNames = columnNameConstants.keySet();
        
        //Check if any of the expected columns have been deleted from the server (are not in result set)
        for (Iterator<String> it = expectedColumnNames.iterator(); it.hasNext(); ) {
            String expectedColumnName = it.next();
            if (!columnNames.contains(expectedColumnName)){
               missingColumn = true;
                if (!hasDeletedColumnAlert.get(tableName)){
                missingColumns += expectedColumnName + "\n";
                }
            }
        }
        
        if (missingColumns.length() > 1){
            missingColumnLog = "\nColumns missing from the " + tableName + " table: \n" + missingColumns;
        }
        
        if(missingColumn && !hasDeletedColumnAlert.get(tableName)){
           tableColumnAlert += "Other column(s) are expected in the " + tableName +
                    " table, but do not exist on the server\n\n"; 
            
            //Update map so that this alert will not display again for this table
            hasDeletedColumnAlert.put(tableName,true);
        }
    }
    
    public Vector getColumnNames(){
        return columnNames;
    }
    
    private void setDataVector(){
       Vector newColumns = new Vector();
       Vector rows = new Vector();
       Boolean hasNewColumns = false;
        try{
            while (rs.next()) {
               Vector columns = new Vector(columnCount);
                for (int col = 1; col <= columnCount; col++ ){
                    String value = rs.getString(col);
                    
                    if (columnNameConstants.containsKey(columnNamesFromDatabase.get(col - 1))){
                      columns.add(value);
                    }
                    else {
                       newColumns.add(value);
                        hasNewColumns = true;
                    }
                }
                
                if (hasNewColumns = true){
                    for (int col = 0; col < newColumns.size(); col++){
                    columns.add(newColumns.get(col));
                    }
                }
                rows.add(columns);
                
                // Reset value
                hasNewColumns = false;    
            }
            data = rows;
            }  
        catch (SQLException ex) {
            LoggingAspect.afterThrown(ex);
        } 
    }
    
    public Vector getDataVector(){
        return data;
    }
        
    private void setTableModel(){
        tableModel = new DefaultTableModel(data, columnNames);
    }
    
    public DefaultTableModel getTableModel(){
        return tableModel;
    }
    
    public Map updatehasDeletedColumnAlert(){
        return hasDeletedColumnAlert;
    }
    
    public Map updatehasUnexpectedColumnAlert(){
        return hasUnexpectedColumnAlert;
    }
    
    public String getTableColumnAlert(){
        return tableColumnAlert;
    }
    
    public String getMissingColumnLog(){
        return missingColumnLog;
    }
    
    public String getUnexpectedColumnLog(){
        return unexpectedColumnLog;
    }
}


    
    
  
