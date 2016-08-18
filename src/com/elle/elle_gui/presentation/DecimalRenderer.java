/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elle.elle_gui.presentation;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*
* This is the decimal format renderer
* @author Xiaoqian Fu
*/
public class DecimalRenderer extends DefaultTableCellRenderer {
    private PropertyChangeSupport pcs;
    private boolean isNumFormatException;
    private static final DecimalRenderer INSTANCE = new DecimalRenderer();
    private static final DecimalFormat formatter = new DecimalFormat("###,###.##");

    private DecimalRenderer() { 
        super();           
        setHorizontalAlignment(JLabel.RIGHT);
        pcs = new PropertyChangeSupport(isNumFormatException);
        isNumFormatException = false;
    }
    
    public static DecimalRenderer getInstance(){
        return INSTANCE;
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {

        Double doubleValue = 0.0;

        if (value != null) {
            try{
                doubleValue = Double.parseDouble(value.toString());
                value = formatter.format((Number) doubleValue);
            }
            catch(NumberFormatException ex){
                isNumFormatException = true;
                pcs.firePropertyChange("NumberFormatException", false, isNumFormatException);
                
                isNumFormatException = false;
            }
       }

        return super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, col);
    }
    
    
    //registers listeners for NumberFormatExceptions
     public void
    addNumFormatExceptionListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}

