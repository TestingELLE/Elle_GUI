package com.elle.elle_gui.presentation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author corinne
 */
class TableBodyListener extends MouseAdapter{
   private final TabJTable table;
    
    TableBodyListener(TabJTable table){
       this.table = table;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // if left mouse clicks
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getClickCount() == 2) {
                table.filterByDoubleClick();
            }
            else if (e.getClickCount() == 1) {
                 table.setIsRecordSelected();
            }
        } // end if left mouse clicks

        // if right mouse clicks
        else if (SwingUtilities.isRightMouseButton(e)) {
            if (e.getClickCount() == 2) {

                // get selected cell
                int columnIndex = table.columnAtPoint(e.getPoint()); // this returns the column i
                int rowIndex = table.rowAtPoint(e.getPoint()); // this returns the rowIndex i
                if (rowIndex != -1 && columnIndex != -1) {

                // make it the active editing cell
                table.changeSelection(rowIndex, columnIndex, false, false);

                selectAllText(e);

                } // end not null condition

            } // end if 2 clicks 
        } // end if right mouse clicks

    }// end mouseClicked

    // Select all text inside jTextField
    private void selectAllText(MouseEvent e) {

        JTable table = (JTable) e.getComponent();
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if (column != 0) {
            table.getComponentAt(row, column).requestFocus();
            table.editCellAt(row, column);
            JTextField selectCom = (JTextField) table.getEditorComponent();
            if (selectCom != null) {
                selectCom.requestFocusInWindow();
                selectCom.selectAll();
            }
        }
    }
}
