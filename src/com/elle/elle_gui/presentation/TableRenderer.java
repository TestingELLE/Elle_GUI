package com.elle.elle_gui.presentation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *Class forwards table columns to the appropriate cell renderer 
 * @author Corinne
 */
public class TableRenderer {
    private static final TableRenderer INSTANCE = new TableRenderer();
    private final TimeRenderer timeRenderer;
    private final DecimalRenderer decimalRenderer;
    private final PriceRenderer priceRenderer;
    private PropertyChangeListener numFormatExceptionListener;
    private TableColumn column;
    
    private TableRenderer(){
        timeRenderer = TimeRenderer.getInstance();
        decimalRenderer = DecimalRenderer.getInstance();
        priceRenderer = PriceRenderer.getInstance();
        numFormatExceptionListener = null;
        
        setNumFormatExceptionListener();
    }
    public static TableRenderer getInstance(){
        return INSTANCE;
    }
    
    public void setTableRenderers(JTable table){
        setTableHeaderRenderer(table);
        
        //iterates over the table's columns and sets the appropriate renderer
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
             column =  table.getColumnModel().getColumn(i);
        
            if (table.getColumnClass(i).getName().toLowerCase().contains("time")) {
               column.setCellRenderer(timeRenderer);
            }
            
            else if (table.getColumnName(i).toLowerCase().contains("strike")){
                setDefaultCellRenderer(column, SwingConstants.RIGHT);
            }
            
            else if (table.getColumnName(i).toLowerCase().contains("price") ||
                    table.getColumnName(i).toLowerCase().equals("q") ||
                    table.getColumnName(i).toLowerCase().equals("basis") ||
                    table.getColumnName(i).toLowerCase().equals("basis_adj")){
                column.setCellRenderer(priceRenderer);
            }
            
            else if (Number.class.isAssignableFrom(table.getColumnClass(i))){
                column.setCellRenderer(decimalRenderer);                
            }
            else {
                setDefaultCellRenderer(column, SwingConstants.CENTER);
            }
        }
    }
    
    //Sets the table header renderer
    //Xiaoqian Fu
    private void setTableHeaderRenderer(JTable table){
       JTableHeader header = table.getTableHeader();
        header.removeAll();
        header.setDefaultRenderer(new TableHeaderRenderer(table)); 
    }
    
    private void setDefaultCellRenderer(TableColumn column, int alignment){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        
        if(alignment == SwingConstants.RIGHT){
            renderer.setHorizontalAlignment(SwingConstants.RIGHT); 
        }
        
        if(alignment == SwingConstants.CENTER){
            renderer.setHorizontalAlignment(SwingConstants.CENTER); 
        }
        
        column.setCellRenderer(renderer);

    }
    //sets a listener for number formatting exceptions thrown by decimalRenderer
    private void setNumFormatExceptionListener(){
        numFormatExceptionListener = new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt){
                setDefaultCellRenderer(column, SwingConstants.CENTER);
            }
        };
        
        decimalRenderer.addNumFormatExceptionListener(numFormatExceptionListener);
    }
}
    