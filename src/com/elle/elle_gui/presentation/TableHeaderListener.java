/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elle.elle_gui.presentation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author corinne
 */
public class TableHeaderListener extends MouseAdapter{
    private final JTableHeader header;
    private final TabJTable table;
    
    public TableHeaderListener(TabJTable table){
         super();
         this.table = table;
         header = table.getTableHeader();
     }
          
    @Override
    public void mouseClicked(MouseEvent e) {
        int columnIndex = header.columnAtPoint(e.getPoint());
        if (e.getClickCount() == 2) {
            e.consume();
            table.clearFilterByDoubleClick(columnIndex);
           }

        if (e.getClickCount() == 1 && !e.isConsumed() && e.isControlDown()) {
            e.consume();
            table.showColumnPopupMenu(e);
        }

        if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {

            if (columnIndex != -1) {
                    columnIndex = table.convertColumnIndexToModel(columnIndex);
                    table.getRowSorter().toggleSortOrder(columnIndex);
            }
            e.consume();
        }
    }

    /**
     * Popup menus are triggered differently on different platforms
     * Therefore, isPopupTrigger should be checked in both
     * mousePressed and mouseReleased events for proper
     * cross-platform functionality.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // this calls the column popup menu
            table.showColumnPopupMenu(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // this calls the column popup menu
            table.showColumnPopupMenu(e);
        }
    }
}
