
package com.elle.elle_gui.dao;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Allocation;
import com.elle.elle_gui.logic.ITableConstants;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * AllocationDAO
 * @author Carlos Igreja
 * @since  May 7, 2016
 */
public class AllocationDAO {
    
    // database table information
    public static final String DB_TABLE_NAME = "allocations";
   
    // Column names to display in Elle_GUI
    public static final String COL_PK_ID = "id";
    public static final String COL_SYMBOL = "symbol";
    public static final String COL_TRADE_TIME = "trade_Time";
    public static final String COL_TRADE_Q = "tradeQ";
    public static final String COL_TRADE_PRICE = "tradePrice";
    public static final String COL_METHOD = "method";
    public static final String COL_MATH_Q = "mathQ";
    public static final String COL_MATH_PROCEEDS = "mathProceeds";
    public static final String COL_LOT_TIME = "lot_Time";
    public static final String COL_LINE = "line";
    public static final String COL_PRICE_ADJ = "price_adj";
    public static final String COL_MATCH_BASIS = "matchBasis";
    public static final String COL_REALIZED_PL = "realized_PL";
    public static final String COL_TERM = "term";
    public static final String COL_ACCOUNT = "account";

    //names of columns in the database
    public static final String DATABASE_COL_PK_ID = "id";
    public static final String DATABASE_COL_SYMBOL = "symbol";
    public static final String DATABASE_COL_TRADE_TIME = "trade_Time";
    public static final String DATABASE_COL_TRADE_Q = "tradeQ";
    public static final String DATABASE_COL_TRADE_PRICE = "tradePrice";
    public static final String DATABASE_COL_METHOD = "method";
    public static final String DATABASE_COL_MATH_Q = "mathQ";
    public static final String DATABASE_COL_MATH_PROCEEDS = "mathProceeds";
    public static final String DATABASE_COL_LOT_TIME = "lot_Time";
    public static final String DATABASE_COL_LINE = "line";
    public static final String DATABASE_COL_PRICE_ADJ = "price_adj";
    public static final String DATABASE_COL_MATCH_BASIS = "matchBasis";
    public static final String DATABASE_COL_REALIZED_PL = "realized_PL";
    public static final String DATABASE_COL_TERM = "term";
    public static final String DATABASE_COL_ACCOUNT = "account";
    
    public static final String[] ALLOCATIONS_COL_NAMES = new String[]
    {
        COL_PK_ID,
        COL_SYMBOL,
        COL_TRADE_TIME,
        COL_TRADE_Q,
        COL_TRADE_PRICE,
        COL_METHOD,
        COL_MATH_Q,
        COL_MATH_PROCEEDS,
        COL_LOT_TIME,
        COL_LINE,
        COL_PRICE_ADJ,
        COL_MATCH_BASIS,
        COL_REALIZED_PL,
        COL_TERM,
        COL_ACCOUNT
    };
    
    //Allocations database column names
     public static final String[] ALLOCATIONS_DATABASE_COL_NAMES = new String[]
    {
        DATABASE_COL_PK_ID,
        DATABASE_COL_SYMBOL,
        DATABASE_COL_TRADE_TIME,
        DATABASE_COL_TRADE_Q,
        DATABASE_COL_TRADE_PRICE,
        DATABASE_COL_METHOD,
        DATABASE_COL_MATH_Q,
        DATABASE_COL_MATH_PROCEEDS,
        DATABASE_COL_LOT_TIME,
        DATABASE_COL_LINE,
        DATABASE_COL_PRICE_ADJ,
        DATABASE_COL_MATCH_BASIS,
        DATABASE_COL_REALIZED_PL,
        DATABASE_COL_TERM,
        DATABASE_COL_ACCOUNT
    };
    
    //map of the column names in the database to the column names to display 
    public static Map<String, String> columnNameConstants = getColumnNameConstants();
    
    public static Map getColumnNameConstants(){
        Map<String, String> constants = new HashMap <String, String>();
        for (int col = 0; col < ALLOCATIONS_DATABASE_COL_NAMES.length; col++){
            constants.put(ALLOCATIONS_DATABASE_COL_NAMES[col],
                    ALLOCATIONS_COL_NAMES[col]);
        }
        return constants;
    }
}
