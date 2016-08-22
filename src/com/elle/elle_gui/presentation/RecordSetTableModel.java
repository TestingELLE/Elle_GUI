package com.elle.elle_gui.presentation;

import com.elle.elle_gui.entities.RecordSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author corinne
 */
public class RecordSetTableModel extends AbstractTableModel{
    private final RecordSet recordSet;
    
    public RecordSetTableModel(RecordSet recordSet){
        super();
        this.recordSet = recordSet;
    }
    
    @Override
    public Object getValueAt(int row, int col){
        return recordSet.getValueAt(row, col);
    }
     
    @Override
    public int getColumnCount(){
        return recordSet.getColumnCount();
    }
    
    @Override
    public int getRowCount(){
      return recordSet.getRecordsCount();  
    }
    
    @Override
    public Class<?> getColumnClass(int index){
        Class<?>  classType = null;
        try {
         classType = Class.forName(recordSet.getColumnClass(index));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecordSetTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classType;
    }
    
     
    @Override
    public String getColumnName(int index){
        return recordSet.getColumnName(index);
    }
  
}
