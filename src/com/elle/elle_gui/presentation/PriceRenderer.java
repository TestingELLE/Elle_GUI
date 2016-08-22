package com.elle.elle_gui.presentation;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author corinne
 */
public class PriceRenderer extends DefaultTableCellRenderer {
    private static final DecimalFormat formatter = new DecimalFormat("###,###.00");
    private static final PriceRenderer INSTANCE = new PriceRenderer();
    
    private PriceRenderer(){
        super();
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
    public static PriceRenderer getInstance(){
        return INSTANCE;
    }
    
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {

        Double doubleValue = 0.0;

        if (value != null) {
            doubleValue = Double.parseDouble(value.toString());
            super.setToolTipText(value.toString());
            value = formatter.format((Number) doubleValue);
        }

        return super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, col);
    }
}
