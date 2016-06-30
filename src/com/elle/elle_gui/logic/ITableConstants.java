package com.elle.elle_gui.logic;

import com.elle.elle_gui.dao.AllocationDAO;
import com.elle.elle_gui.dao.PositionDAO;
import com.elle.elle_gui.dao.TradeDAO;
import java.util.Map;

/**
 * ITableConstants This interface stores all the table constants
 *
 * @author Carlos Igreja
 * @since June 10, 2015
 * @version 0.6.3
 */
public interface ITableConstants {

    // view label constants
    public static final String VIEW_LABEL_TXT_ALL_FIELDS = "All Fields";
    public static final String VIEW_LABEL_TXT_DEFAULT_VIEW = "Default View";
    
    // account names
    public static final String IB9048_ACCOUNT_NAME = "IB9048";
    public static final String TOS3622_ACCOUNT_NAME = "TOS3622";
    public static final String COMBINED_ACCOUNT_NAME = "Combined";

    // table names
    public static final String POSITIONS_TABLE_NAME = "positions";
    public static final String TRADES_TABLE_NAME = "trades";
    public static final String ALLOCATIONS_TABLE_NAME = "allocations";
    
    //Array of table names
    String[] tables = new String[]{
      POSITIONS_TABLE_NAME,
      TRADES_TABLE_NAME,
      ALLOCATIONS_TABLE_NAME
    };
    
    //map of the column names in the database to the column names to display
    Map<String, String> TRADE_COLUMN_NAME_CONSTANTS = TradeDAO.columnNameConstants;
    Map<String, String> POSITIONS_COLUMN_NAME_CONSTANTS = PositionDAO.columnNameConstants;
    Map<String, String> ALLOCATIONS_COLUMN_NAME_CONSTANTS = AllocationDAO.columnNameConstants;  
}