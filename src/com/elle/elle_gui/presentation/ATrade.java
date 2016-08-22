package com.elle.elle_gui.presentation;

import java.util.Vector;
import javax.swing.JTable;

/**
 *This class is used to create a object of a trade in tables
 * @author fuxiaoqian
 * @since Feb 16, 2016
 * @version 0.9.2
 * 
 * updated by Corinne 8/13/2016
 */
public class ATrade {
    
    private int rowInTable;
    private JTable table; 
    
    
    public ATrade(int row, JTable table) {
        rowInTable = row;
        this.table = table;       
    }
    
    // creates a vector of rows to be displayed in the viewATradeWindow 
    //containing a field name and a value
     public Vector<Vector> getRowData(){
         Vector fieldValues = setFieldValues();
        Vector<Vector> rowData = new Vector<Vector>();
        int rowCount = fieldValues.size();
        for(int i = 0; i < rowCount; i++){
            Vector aRow = new Vector();
            aRow.addElement(table.getColumnName(i));
            aRow.addElement(fieldValues.get(i));
            rowData.addElement(aRow);
        }
        return rowData;
    }
    
   
    
     //creates a vector of the values in the selected row
     private Vector<Object> setFieldValues(){
        Vector values = new Vector<Object> ();
        
        for(int i = 0; i < table.getColumnCount(); i++ ){
            Object value = table.getValueAt(rowInTable, i);
            if(value == null){
               values.addElement(""); 
            }else{
               values.addElement(value); 
            }  
        }
        return values;
    }  
}
