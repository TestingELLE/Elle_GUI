package com.elle.elle_gui.presentation;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

 /*
* DataRenderer 
* This removes the .0 at the end of the original value in the data object.
* @author Xiaoqian Fu
*/
public class TimeRenderer extends DefaultTableCellRenderer {
    public static final TimeRenderer INSTANCE = new TimeRenderer();

    private TimeRenderer() {
        super();
        setHorizontalAlignment(JLabel.CENTER);
    }

    public static TimeRenderer getInstance(){
        return INSTANCE;
    }
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int col) {

        // change value
        if(value == null){
            value = "";
        }
        else{
            String s = value.toString();
            value = s.replace(".0", "");
        }

        return super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, col);
    }
}

  
