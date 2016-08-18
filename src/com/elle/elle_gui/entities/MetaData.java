package com.elle.elle_gui.entities;

import static com.elle.elle_gui.miscellaneous.TableConstants.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stores a ColumnMetaData object for every unexpected, expected, missing column from the database
 * and forward methods to the appropriate ColumnMetaData
 * @author corinne
 */
public class MetaData {
    private final List<ColumnMetaData> columnMetaData;
    private final String tableName;
    private final List<String> missingColumns;
    private final List<String> unexpectedColumns;
    private final int columnCount;
    
    public MetaData(List<String> dbNames, List<String> classTypes, String tableName){
        this.tableName = tableName;
        missingColumns = setMissingColumns(dbNames);
        unexpectedColumns = setUnexpectedColumns(dbNames);
        columnMetaData = setColumnMetaData(dbNames, classTypes);
        columnCount = dbNames.size();
    }
    
    public String getTableName(){
    return tableName;
    }
    
    public String getDatabaseName(int index){
        return getColumnMetaData(index).getDbName();
    }
    
    /*
    If the ColumnMetaData object's label is not null
    (the column has a name stored in the table's constants) returns 
    the constant name. Otherwise, it returns the name of the column in the database.
    */
    public String getColumnLabel(int index){
         ColumnMetaData column = getColumnMetaData(index);
       String label =  column.getLabel(); 
      if(label != null){
        return label;
        }
      else{
          return getColumnMetaData(index).getDbName();
      }
    }
        
    
    public String getColumnClass(int index) throws ClassNotFoundException{
        return getColumnMetaData(index).getClassType();
    } 
   
    public boolean getIsUnexpected(int index){
        return getColumnMetaData(index).isUnexpected();
    }
    
    public boolean getIsMissing(int index){
        return getColumnMetaData(index).isMissing();
    }
    
    public List<String> getUnexpectedColumns(){
        return unexpectedColumns;
    }
    
    public List<String> getMissingColumns(){
        return missingColumns;
    }
    
    public int getColumnCount(){
        return columnCount;
    }
    
    private ColumnMetaData getColumnMetaData(int index){
        ColumnMetaData column = null;
        
        for(ColumnMetaData currentColumn:columnMetaData ){
            if(currentColumn.getIndex() == index){
                column = currentColumn;
                break;
            }
        }
        return column;
    }
    
    /*
    Creates a new ColumnMetaData object for every database column name and missing column name
     and sets the appropriate attributes.
    */
    private List<ColumnMetaData> setColumnMetaData(List<String> dbNames, 
            List<String> classTypes){
        List<ColumnMetaData> columns = new ArrayList();
        List<Integer> columnIndexes = getColumnIndexes(dbNames);
        
        
        for(int i = 0; i < dbNames.size(); i++){
            String dbName = dbNames.get(i);
            ColumnMetaData columnMetaData = new ColumnMetaData();
            columnMetaData.setIndex(columnIndexes.get(i));
            columnMetaData.setDbName(dbName);
            columnMetaData.setClassType(classTypes.get(i));
            
            //The current column is unexpected
            if(unexpectedColumns != null && unexpectedColumns.contains(dbName)){
                columnMetaData.setIsUnexpected(true);
            }
            
            /*The current column is expected and is contained 
            in a table constant. Determined by testing if tableName is null.
            It is null when the table is not a constant. 
            This happens when a table is loaded from a sql command input by a user in Elle_GUI_Frame
            */
            else if (TABLE_NAMES.contains(tableName)){
                Map<String, String> columnConstants = getColumnConstantsMap(tableName);
                columnMetaData.setLabel(columnConstants.get(dbName));
            }
            
            columns.add(columnMetaData);
        }
        
        if(missingColumns != null){
            for (int i = 0; i <missingColumns.size(); i++){
                
                /*the column should not be displayed so the index is not set since 
                it is initialized as -1 in the ColumnMetaDataConstructor*/
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setLabel(missingColumns.get(i));
                columnMetaData.setIsMissing(true);
                
                columns.add(columnMetaData);
            }
        }
        return columns;
    }
    
    /*
    If the tableName is a constant in TableConstants.java, this method
    determines and returns the columns contained database table's column names, 
    but not in the table's column constants.
    */
    private List<String> setUnexpectedColumns(List<String> dbNames){
        List<String>unexpectedColumns = new ArrayList();
        if (TABLE_NAMES.contains(tableName)){
            List<String> expectedColumns = getColumnConstants(tableName);

            for (int i = 0; i < dbNames.size(); i++ ){
                if (!(expectedColumns).contains(dbNames.get(i))){
                    unexpectedColumns.add(dbNames.get(i));
                }
            }
        }
        if (unexpectedColumns.size() > 0) {
            return unexpectedColumns;
        } 
        else{
            return null;
        }
     
    }
    
    /*
    If the tableName is a constant in TableConstants.java,
    this method determines and returns the columns contained in the table's column constants, 
    but missing from the database table's column names.
    */
    private List<String> setMissingColumns(List<String> dbNames){
         List<String> missingColumns = new ArrayList();
         if (TABLE_NAMES.contains(tableName)){
            List<String> expectedColumns = getColumnConstants(tableName);
            for (int i = 0; i < expectedColumns.size(); i++ ){
                if (!dbNames.contains(expectedColumns.get(i))){
                    missingColumns.add(expectedColumns.get(i));
                }
            }
         }
     if (missingColumns.size() > 0) {
            return missingColumns;
        } 
        else{
            return null;
        }
    }
    
    /*Determines the order that a table's columns should be displayed.
    putting unexpected columns and missing columns at the end of the table and
    creates an List of integers which store the column indexes for each column.
    */
    private List<Integer>  getColumnIndexes(List<String> dbNames){
        List<Integer> sortOrder = null;
        List<Integer> columnIndexes = null;
      
        sortOrder = getSortedDBIndexes(dbNames);

        columnIndexes = new ArrayList(dbNames.size());

       //set the size of columnIndexes by initializiing the elements to 0.
       //Otherwise the list's capacity is set but not the size
       //This is necessary to for the following operation--
       //to insert values at specified positions in the list
       while(columnIndexes.size() < dbNames.size()) columnIndexes.add(0);
         
        // Reverse sortOrder to create a list of column indexes ordered by the column positions 
        //in the list of columnMetaData. This is used to set the columnMetaData object indexes
        for (int i = 0; i< sortOrder.size(); i++){
            int index = sortOrder.get(i);
           columnIndexes.set(index, i); 
        }
        
        return columnIndexes;
    }
     
    //returns a sorted list of columns indexes for the column names in the database
    //putting unexpected columns at the end of the table
    private List<Integer> getSortedDBIndexes(List<String> dbNames){
       List<Integer> sortOrder = new ArrayList();
        List<Integer> unexpectedColIndexes = new ArrayList();
       
        //iterates over each of the column names from the database
        for(int i = 0; i < dbNames.size(); i++){
            
            //column is unexpected 
            if(unexpectedColumns != null && unexpectedColumns.contains(dbNames.get(i))){
                unexpectedColIndexes.add(i);
            }
      
            //column is expected
            else{
                sortOrder.add(i);
            }
        }
        //adds the indexes of the unexpected columns to the end of the List of column indexes
        if (unexpectedColIndexes.size() > 0){
        sortOrder.addAll(unexpectedColIndexes);
        }
        
        return sortOrder;
    }
   
}

