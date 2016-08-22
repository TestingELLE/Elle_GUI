package com.elle.elle_gui.database;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.MetaData;
import com.elle.elle_gui.entities.Record;
import com.elle.elle_gui.entities.RecordSet;
import com.elle.elle_gui.miscellaneous.LoggingAspect;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *Singleton class: only one object will be instantiated
 * @author corinne
 */
public class RecordDAO {
    private ResultSet rs;
    private String sql;
    private String tableName;
    private final static RecordDAO INSTANCE = new RecordDAO();
    
    private RecordDAO(){
        ResultSet rs = null;
        sql = "";
        tableName = null;
    }
    
    public static RecordDAO getInstance(){
        return INSTANCE;
    }
   
    
    public RecordSet getRecordSet(String tableName, String accountName) {
        this.tableName = tableName;
        String sql =  getSQL(accountName);
        return setRecordSet(sql);
        
    }
    
    public RecordSet getRecordSet(String tableName){
       this.tableName = tableName; 
       String sql = getSQL();
       return setRecordSet(sql);
    }
    
    public RecordSet getRecordSetFromSQL(String input) {
        // the table name is unknown and the table is not a constant
        this.tableName = null;
        return setRecordSet(input);   
    }
    
    private RecordSet setRecordSet(String sql){
       RecordSet recordSet = null;
        List<Record> tableRecords = new ArrayList(); 
        
        ResultSet rs = getResultSet(sql);
        Record record = null;
        List<Object> rowData = null;
        try {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            while(rs.next()){
                rowData = new ArrayList<>();
                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
                   rowData.add(rs.getObject(i));
                } 
                record = new Record(rowData);
                tableRecords.add(record);
            }
            
            MetaData metaData = getMetaData(resultSetMetaData);
            recordSet = new RecordSet(tableRecords, metaData);
        }
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return recordSet;
    }

    
    
    private MetaData getMetaData(ResultSetMetaData rsMetaData){
        List <String> colNames = new ArrayList<>();
        List<String> colClasses = new ArrayList<>();
        MetaData metaData = null;
        try{
            for (int i = 1; i <= rsMetaData.getColumnCount(); i ++){
                colNames.add(rsMetaData.getColumnName(i));
                colClasses.add(rsMetaData.getColumnClassName(i));
            }
           metaData = new MetaData(colNames,colClasses, tableName);
        }
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return metaData;
    }
   
    
    private String getSQL(String accountName){
            if (accountName == "Combined") {
            sql = "SELECT * FROM " + tableName
                    + " ORDER BY symbol ASC";
        } else {
            sql = "SELECT * FROM " + tableName
                    + " WHERE Account = '" + accountName
                    + "' ORDER BY symbol ASC";
        }
        return sql;   
    }
    
    private String getSQL(){
        String sql = "SELECT * FROM " + tableName;
        return sql;
    }
   
    
    private ResultSet getResultSet(String sql){        
        
        try{

            DBConnection.close();
            DBConnection.open();
            rs = DBConnection.getStatement().executeQuery(sql);
        }
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        return rs;
    }
}
