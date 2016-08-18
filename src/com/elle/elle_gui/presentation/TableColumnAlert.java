package com.elle.elle_gui.presentation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JOptionPane;

/**
 * This class creates and displays alert messages for database column names which differ from 
 * the column name constants contained in TableConstants.java for that table
 * @author corinne
 * 8/15/16
 */
public class TableColumnAlert {
    private String alertMsg;
    private String missingColumnLog;
    private String unexpectedColumnLog;
    
    TableColumnAlert(Map<String, List<String>> unexpectedColumns,Map<String, List<String>> missingColumns){
        alertMsg = "";
        missingColumnLog = "";
        unexpectedColumnLog = "";
        
        setAlertMsg(unexpectedColumns, missingColumns);
        setMissingColumnLog(missingColumns);
        setUnexpectedColumnLog(unexpectedColumns);
    }
    
    
    //Called by Elle_GUI_Frame after the tables load to display an alert message for 
    //any column changes on the server
    public void displayAlert(LogWindow logWindow){
        if (!alertMsg.equals("")){
            
            System.out.print(alertMsg);
            System.out.print(unexpectedColumnLog);
            System.out.print(missingColumnLog);
            
            //writes the alert messages to the log file
            LogWindow.addMessageWithDate(alertMsg);
            LogWindow.addMessage(unexpectedColumnLog);
            LogWindow.addMessage(missingColumnLog);
            
            //displays the alert message and gives the user the option to view the log file
            Object[] optionViewLog = {"View Log", "OK"};
            int n = JOptionPane.showOptionDialog(null, alertMsg, 
                    "Alert", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, 
                    null, optionViewLog, optionViewLog[0] );
            
            //if option "view log" is selected, displays the log window
            if(n == 0){
                logWindow.setVisible(true); // show log window
                
            }
        }
    }
    //creates an alert message naming the tables with missing columns and the tables with unexpected columns
    private void setAlertMsg(Map<String, List<String>> unexpectedColumns,Map<String, List<String>> missingColumns){
        if(unexpectedColumns != null){
            for(String tableName: unexpectedColumns.keySet())
            alertMsg += "\nUnexpected column(s) exists in the " + tableName+ " table on the server-\n"
                        + " This column(s) will be added to the end of the " + tableName+ " table\n";
        }
        
        if ( missingColumns != null){
            for( String tableName: missingColumns.keySet()){
                alertMsg += "\nOther column(s) are expected in the " + tableName +
                    " table, but do not exist on the server\n"; 
            }
        }
    }
    
    //creates a log message that lists the missing columns for each table with missing columns
    private void setMissingColumnLog(Map<String, List<String>> missingColumns){
        if (missingColumns != null){
            for(Entry<String, List<String>> entry: missingColumns.entrySet()){
            missingColumnLog += "\nColumns missing from the " + entry.getKey() + " table:\n";
            
                //Adds the missing column names to the log message
                List<String> columnNames = entry.getValue();
                for(String columnName: columnNames) {
                    missingColumnLog += columnName + "\n";
                }
            }
                    
        }
    }
    //creates a log message that lists the unexpected columns for each table with unexpected columns
    private void setUnexpectedColumnLog(Map<String, List<String>> unexpectedColumns){
        if (unexpectedColumns != null){
            for(Entry<String, List<String>> entry: unexpectedColumns.entrySet()){
            unexpectedColumnLog += "\nUnexpected columns in the " + entry.getKey() + " table:\n";
            
                //Adds the unexpected column names to the log message
                List<String> columnNames = entry.getValue();
                for(String columnName: columnNames) {
                    unexpectedColumnLog += columnName + "\n";
                }
            }
        }
    }
}
