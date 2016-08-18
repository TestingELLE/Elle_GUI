package com.elle.elle_gui.presentation;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/*
* HeaderRenderer This is the header renderer
*
* @author Xiaoqian Fu
*/

class TableHeaderRenderer implements TableCellRenderer {
   DefaultTableCellRenderer renderer;

   public TableHeaderRenderer(JTable table) {
       if(table.getTableHeader().getDefaultRenderer() instanceof DefaultTableCellRenderer){
       renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
       renderer.setHorizontalAlignment(JLabel.CENTER);
        }
   }

   @Override
   public Component getTableCellRendererComponent(
       JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int col) {
       return renderer.getTableCellRendererComponent(
               table, value, isSelected, hasFocus, row, col);
   }

}
