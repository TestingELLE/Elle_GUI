package com.elle.elle_gui.presentation;

import static com.elle.elle_gui.presentation.LabelConstants.VIEW_LABEL_TXT_DEFAULT_VIEW;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author corinne
 */
public class TabJTable extends JTable{
    TableColumnAdjuster tableColumnAdjuster;
    private final TableRenderer tableRenderer;
    private final TableFilter filter;
    private final TableBodyListener tableBodyListener;
    private final TableHeaderListener tableHeaderListener;
    private final ColumnPopupMenu columnPopupMenu;
    private String view;                           // sets the view label text
    private boolean allFields;                     // is all fields view showing                   
    private boolean isSelected;                 // sets if this table is selected
    private boolean recordSelected;
    private int recordsChanged;
    
    // manages a listener and dispatches a PropertyChangeEvent for recordSelected
    private PropertyChangeSupport recordSelectedPcs;
    
    // manages a listener and dispatches a PropertyChangeEvent for recordsChanged
    private PropertyChangeSupport recordsChangedPcs;
    private String tableName;
    
   
    TabJTable(RecordSetTableModel model){
       super();
       setModel(model);
       tableColumnAdjuster = new TableColumnAdjuster(this);
       tableRenderer = TableRenderer.getInstance();
       filter = new TableFilter(this);
       tableBodyListener = new TableBodyListener(this);
       tableHeaderListener = new TableHeaderListener(this);
       columnPopupMenu = new ColumnPopupMenu(this);
       view = VIEW_LABEL_TXT_DEFAULT_VIEW;
        allFields = false;
       isSelected = false;
       recordSelected = false;
       recordsChanged = 0;
       recordSelectedPcs = new PropertyChangeSupport(recordSelected);
       recordsChangedPcs = new PropertyChangeSupport(recordsChanged);
       tableName = null;
       
       initFilter();
       formatTable(); 
   } 
   
    
       
    public String getTableName(){
        return tableName;
    }
  
    // returns the total number of records in table model
    public int getTotalRecords() {
        int records = 0;
        if (getModel() != null){
            records = getModel().getRowCount();
        }
        return records;
    }

    //returns the number of records currently displayed by the table
    public int getRecordsShown() {
        return getRowCount();
    }
    
    public String getView(){
        return view;
    }
    
     public boolean isTableSelected(){
        return isSelected;
    }
     
     public void setView(String view) {
        this.view = view;
    }

    public boolean isAllFields() {
        return allFields;
    }

    public void setAllFields(boolean allFields) {
        this.allFields = allFields;
    }
    
    public void setTableSelected(boolean selected){
        isSelected = selected;
    }
    
   
    public void setTableName(String tableName){
        this.tableName = tableName;
    }
    
    //sets the column widths to fit the largest value cell value and the column name in the header 
    public void setDefaultColumnWidth(){            
        tableColumnAdjuster.adjustColumns();
    }
    
    //changes recordSelected and passes a propertyChangeEvent to the registered listener in Elle_GUI_Frame
    public void setIsRecordSelected(){
        recordSelected = true;
        recordSelectedPcs.firePropertyChange("recordSelected",
                                   false, recordSelected);
        
        
    }
    
    //registers listeners for changes to recordSelected
     public void
    addRecordSelectedListener(PropertyChangeListener listener) {
        recordSelectedPcs.addPropertyChangeListener(listener);
    }
    
    //increments records changed and notifies listeners in Elle_GUI_Frame of the change
    public void setRecordsChanged(int i){
        int oldValue = recordsChanged;
        if( i <= 1){
            recordsChanged = i;  
        }
        else{
            recordsChanged += i;
        }
        recordsChangedPcs.firePropertyChange("recordsChanged",
                                   oldValue, recordsChanged);
        
    } 
    //registers listeners for changes to recordsChanged
    public void addRecordsChangedListener(PropertyChangeListener listener) {
        recordsChangedPcs.addPropertyChangeListener(listener);
    }
    
    public void showColumnPopupMenu(MouseEvent e){
        columnPopupMenu.showPopupMenu(e);
    }
    
    
    /**************************************************************************
    ***************************Filter Methods********************************
    **************************************************************************/
    //returns an arraylist of the filter items for the selected DataTable
    
    public Map<Integer, ArrayList<Object>> getFilterItems(){
        return filter.getFilterItems();
    }
    
    //Filters the table by the specified column values
    public void filterByItems( int col, List<Object> filterItems){
        filter.addFilterItems(col, (ArrayList) filterItems);
        filter.applyFilter();
        
        setRecordsChanged(3);
    }
    
    //Filters the table by the underlying column value corresponding 
    //to the item double clicked on in the selected table
    public void filterByDoubleClick(){
        filter.filterByDoubleClick();
        setRecordsChanged(3);
    }
    
    //clears the filters for the column that is double clicked
    public void clearFilterByDoubleClick(int columnIndex){
        filter.clearFilterByDoubleClick(columnIndex);
        String colName = getColumnName(columnIndex);
        if (colName.toLowerCase().contains("symbol")){
           setRecordsChanged(1); 
        }
        else if (colName.toLowerCase().contains("lot_time") 
                || colName.toLowerCase().contains("trade_time")){
            setRecordsChanged(0);
        }
        else{
            setRecordsChanged(3);
        }
        
    }
    
    //Filters the table by the specified range in the date column
    public void filterByDate(Date startDate, Date endDate){
        filter.filterByDate(startDate, endDate);
        setRecordsChanged(3);
    }
    
    //filters this table by the specified value in the underlying column
    public void filterBySymbol(String symbol){
        filter.filterBySymbol(symbol);
        setRecordsChanged(3);
    }
    
    //removes the filter on the column corresponding to the filtername parameter 
    public void removeFilter(String filterName){
        filter.removeFilter(filterName); 
        setRecordsChanged(3);
    }
    
    //clears all filters from the selected DataTable
    public void clearAllFilters(){
       filter.clearAllFilters();
    }
    
 
    //initializes and applies a filter to the table
    private void initFilter(){
       //initiallize filter
        if (filter.getFilterItems() == null) {
            filter.initFilterItems();
        }
        
        // apply filter
        filter.applyFilter();
        filter.applyColorHeaders(); 
    }
    
    //Sets the table renderers, listeners, resize mode and column widths
    private void formatTable() {

        //Set the table listeners
        setHeaderListener();//sets the header listener
        addMouseListener(tableBodyListener); //sets the body listener

        //Set the table renderers
        tableRenderer.setTableRenderers(this); 

        // this is needed for the horizontal scrollbar to appear
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //sets the column widths to fit the largest value cell value and the column name in the header 
        setDefaultColumnWidth();
    }
    
    //Removes the default table header listeners and adds a TableHeaderListener
    private void setHeaderListener(){
        //disable default mouse listeners
        JTableHeader header = getTableHeader();
         MouseListener[] listeners = header.getMouseListeners();
         
         /* removes MouseInputHandler since it alters the behavior of toggleSortOrder()
         Corinne Martus 7/7/16 
         Cannot delete the entire BasicTableHeaderUI class because
         this disables dragging and resizing columns*/
        for (MouseListener ml: listeners)
        {
            String className = ml.getClass().toString();
            if (className.contains("BasicTableHeaderUI.MouseInputHandler"))
                header.removeMouseListener(ml);
        }
        
        if (header != null) {
            header.addMouseListener(tableHeaderListener);
        }
    }
}
