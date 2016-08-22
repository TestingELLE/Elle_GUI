package com.elle.elle_gui.presentation;

import com.elle.elle_gui.database.DataManagerFactory;
import static com.elle.elle_gui.miscellaneous.TableConstants.*;
import com.elle.elle_gui.entities.RecordSet;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.elle.elle_gui.miscellaneous.LoggingAspect;

/**
 *This class is used to create a tab object. This object contains all the
 * components of the tab in Elle_GUI and manages the tables in that tab. 
 * Each tab may have its own attributes and  that is what this class is for.
 * 
 * @author corinne 8/1/2016
 */


public class Tab {
            
    //map of the tab's table names to the corresponding DataTables 
    private final Map<String, TabJTable> tables;
    private final String accountName;
    
    //stores if this is the selected tab
    private boolean isSelectedTab; 
    
    //increments when the number of records displayed changed for one of this tab's tables
    private int recordsChanged;   
    
    //stores if a table record is currently selected
    private Boolean recordSelected;
    
    // manages a listener and dispatches a PropertyChangeEvent for recordSelected
    private PropertyChangeSupport recordSelectedPcs;
    
    //manages a listener and dispatches a PropertyChangeEvent for recordsChanged
    private PropertyChangeSupport recordsChangedPcs;
    
    //For each table containing unexpected and missing columns,
    //maps the tableNames to a list of the unexpected and missing columns respectively
    Map<String, List<String>> unexpectedColumns;
    Map<String, List<String>> missingColumns;
    
    PropertyChangeListener recordsChangedListener;
    PropertyChangeListener recordSelectedListener;
    
    //no-argument constructor
    public Tab(){
        accountName = "";
        tables = new HashMap<>();
        isSelectedTab = false;
        recordsChanged= 0;
        recordSelected = false;
        recordSelectedPcs = new PropertyChangeSupport(recordSelected);
        recordsChangedPcs = new PropertyChangeSupport(recordsChanged);
        recordsChangedListener = null;
        recordSelectedListener = null;
        unexpectedColumns = new HashMap<>();
        missingColumns = new HashMap<>();
    }
    
    public Tab(String accountName){
        this.accountName = accountName;
        tables = new HashMap<>();
        isSelectedTab = false;
        recordsChanged= 0;
        recordSelected = false;
        recordSelectedPcs = new PropertyChangeSupport(recordSelected);
        recordsChangedPcs = new PropertyChangeSupport(recordsChanged);
        recordsChangedListener = null;
        recordSelectedListener = null;
        unexpectedColumns = new HashMap<>();
        missingColumns = new HashMap<>();
        
        loadTables();
        setRecordSelectedListener(); //registers a listener for changes to recordSelected in each TabJTable
        setRecordsChangedListener(); //registers a listener for changes to recordsChanged in each TabJTable
        setSelectedTable(POSITIONS_TABLE_NAME); // sets the positions table to be the default selected table        
    }
        
    public String getSelectedTableName()
    {
        String tableName = "";
        for(Entry<String, TabJTable> entry: tables.entrySet()){
            if (entry.getValue().isTableSelected()){
               tableName = entry.getKey();
            }
        }
        return tableName;
    }
    
    
    public TabJTable getTable(String tableName){
        return tables.get(tableName); 
    }
    
    public TabJTable getSelectedTable(){
        return tables.get(getSelectedTableName());
    }
    
    public boolean isSelectedTab(){
        return isSelectedTab;
    }
    
    //sets isTableSelected in the specified table to true and all other tables to false   
    public void setSelectedTable(String tableName){
        for(Entry<String, TabJTable> entry: tables.entrySet()){
            if (entry.getKey().equals(tableName)){
                entry.getValue().setTableSelected(true);
            }
            else{
                entry.getValue().setTableSelected(false);
            }
        }
    }
    
    public String getView() {
        return getSelectedTable().getView();
    }

    public void setView(String view) {
        getSelectedTable().setView(view);
    }

    public boolean isAllFields() {
        return getSelectedTable().isAllFields();
    }

    public void setAllFields(boolean allFields) {
        getSelectedTable().setAllFields(allFields);
    }
    
    public void setDefaultColumnWidth(){
        getSelectedTable().setDefaultColumnWidth();
    }
    
    public void setIsSelectedTab(boolean isSelected){
        isSelectedTab = isSelected;
    }
    
   //changes recordSelected and passes a propertyChangeEvent to the registered listener in Elle_GUI_Frame
    public void setIsRecordSelected(){
        recordSelected = true;
        recordSelectedPcs.firePropertyChange("recordSelected",
                                   false, true);
    }
    
    //registers listeners for changes to recordSelected
     public void
    addRecordSelectedChangeListener(PropertyChangeListener listener) {
        recordSelectedPcs.addPropertyChangeListener(listener);
    }
    //increments records changed and notifies listeners in Elle_GUI_Frame of the change
    public void setRecordsChanged(int i){
        int oldValue = recordsChanged;
        if (i > 0){
            recordsChanged = i;
        }
        else{
            recordsChanged =+ i;
        }
        recordsChangedPcs.firePropertyChange("recordsChanged",
                                   oldValue, recordsChanged);
        
    } 
    //registers listeners for changes to recordsChanged
    public void addRecordsChangedListener(PropertyChangeListener listener) {
        recordsChangedPcs.addPropertyChangeListener(listener);
    }
    
   //Returns the string to be displayed in the records shown label in Elle_GUI_Frame.
    //contains the number of records shown in the table
     public String getRecordsShownLabel() {

        String output;        
        output =  "Number of records shown: " + getSelectedTable().getRecordsShown();
               
        return output;
    }
    
    /**
     * @return String 
     * This returns a string that has the total records in the table model.
     * The String is displayed in Ell_GUI_Frame in the total records label.
     */
    public String getTotalRecordsLabel() {
        String tableName = getSelectedTableName();
        String output;

        if (TABLE_NAMES.contains(tableName)) {
                output = "Number of records in " + tableName + ": " + getSelectedTable().getTotalRecords();
        }
        else{
        // this means an invalid table name constant was passed
        // this exception will be handled and thrown here
        // the program will still run and show the stack trace for debugging
            
            output = "<html><pre>"
                    + "*******ATTENTION*******"
                    + "<br/>Not a valid table name constant entered"
                    + "</pre></html>";
            try {
                String errorMessage = "ERROR: unknown table";
                throw new NoSuchFieldException(errorMessage);
            } catch (NoSuchFieldException ex) {
                LoggingAspect.afterThrown(ex);
            }

        }

        return output;
    }
    
    public Map<String, List<String>> getMissingColumns(){
        return missingColumns;
    }
    
    public Map<String, List<String>> getUnexpectedColumns(){
        return unexpectedColumns;
    }
   
    /*
    For name in the list of table name constants, retrieves a recordsSet, 
    creates a RecordSeTabletModel, creates a new TabJTable with this model, 
    and add the table to the map of tableNames to tables.
    */
    public void loadTables(){
        //tab must be initialized with an accountName to load the table data
        //Otherwise, does not load--method does nothing
        if(accountName != null){ 
            for (String tableName: TABLE_NAMES){
                RecordSet recordSet = DataManagerFactory.getDataManager().getRecordSet(tableName, accountName);
                RecordSetTableModel recordSetTableModel = new RecordSetTableModel(recordSet);
                TabJTable table = new TabJTable(recordSetTableModel);
                table.setTableName(tableName);
                tables.put(tableName, table);
                
               //for each table that has unexpected columns, 
               //add the unexpected column names to the map of unexpected columns 
               if (recordSet.getUnexpectedColumns() != null){
                   unexpectedColumns.put(tableName, recordSet.getUnexpectedColumns());
               }
               
               //for each table that has missing columns, 
               //add the missing column names to the map of missing columns 
               if(recordSet.getMissingColumns() != null){
                   missingColumns.put(tableName, recordSet.getMissingColumns());
               }
            }
        } 
    }
    
    /**************************************************************************
    ***************************Filter Methods********************************
    **************************************************************************/
    
    
    //Filters the selected table by the specified range in the date column
    public void filterByDate(Date startDate, Date endDate){
        getSelectedTable().filterByDate(startDate, endDate);
         
        //the number of records displayed has changed
        setRecordsChanged(-1);
    }
    
    //filters the selected table by the specified value in the underlying column
    public void filterBySymbol(String symbol){
        getSelectedTable().filterBySymbol(symbol);
         
        //the number of records displayed has changed
        setRecordsChanged(-1);
    }
    
    //removes the filter on the column corresponding to the filtername parameter in the selected table
    public void removeFilter(String filterName){
        getSelectedTable().removeFilter(filterName);
         
        //the number of records displayed has changed
        setRecordsChanged(-1);
    }
    
    //clears all filters from the selected table
    public void clearAllFilters(){
        getSelectedTable().clearAllFilters();
         
        //the number of records displayed has changed
        setRecordsChanged(-1);
    }
    
    private void setRecordsChangedListener(){
            recordsChangedListener = new PropertyChangeListener(){
           
                @Override
            public void propertyChange(PropertyChangeEvent evt){
                setRecordsChanged((int) evt.getNewValue());
            }
        };
        
            for(Entry <String, TabJTable> entry: tables.entrySet()){
               entry.getValue() .addRecordsChangedListener(recordsChangedListener);
            }
    }
    
    private void setRecordSelectedListener(){
        recordSelectedListener = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt){
                setIsRecordSelected();
            }
        };
        
        for(Entry <String, TabJTable> entry: tables.entrySet()){
           entry.getValue().addRecordSelectedListener(recordSelectedListener);
        }
    }
    
     
}
