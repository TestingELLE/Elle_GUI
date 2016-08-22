package com.elle.elle_gui.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stores a list of record objects and a metaData object 
 * and manages requests for data and metaData on this table
 * @author corinne
 */
public class RecordSet {
    private final List<Record> records;
    private final MetaData metaData;
    private boolean isSelected;
    
    public RecordSet(List<Record> records, MetaData metaData){
     this.records = records;
     this.metaData = metaData;
    }
    
    public String getTableName(){
        return metaData.getTableName();
    }
    
    public String getColumnName(int index){
        return metaData.getColumnLabel(index);
    }
    
    public String getColumnClass(int index) throws ClassNotFoundException{
        return metaData.getColumnClass(index);
    }
    
    public boolean isSelected(){
        return isSelected;
    }
    
    public List<String> getMissingColumns(){
        return metaData.getMissingColumns();
    }
    
    public List<String> getUnexpectedColumns(){
        return metaData.getUnexpectedColumns();
    }
    
    public Object getValueAt(int row, int col){
      return records.get(row).getRecordValue(col); 
    }
    
    public int getRecordsCount(){
        return records.size();
    }
    
    public int getColumnCount(){
       return metaData.getColumnCount(); 
    }
}
