
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
    public static final String COL_T_ID = "t_id";
    public static final String COL_BKRGROUP = "bkrGroup";
    public static final String COL_PROCESSED = "processed";   
    public static final String COL_SYMBOL = "symbol";
    public static final String COL_MATH_Q = "matchQ";
    public static final String COL_TRADE_TIME = "tradeTime";
    public static final String COL_P_ID = "p_id";
    public static final String COL_LINE = "line";
    public static final String COL_LOT_TIME = "lot_Time";
    public static final String COL_P_SYMBOL = "p_symbol";
    public static final String COL_P_Q = "p_Q";
    public static final String COL_P_PRICE_ADJ = "p_price_adj";
    public static final String COL_P_BASIS_ADJ = "p_basis_adj";
    public static final String COL_P_PRICE = "p_price";
    public static final String COL_P_BASIS = "p_basis";
    public static final String COL_MATH_PROCEEDS = "proceeds";
    public static final String COL_REALIZED_PL = "realized_PL";
    public static final String COL_HOLDING_DAYS = "holdingDays";
    public static final String COL_TERM = "term";
    public static final String COL_MATCHING = "matching";
    public static final String COL_MFLAG = "mflag";
    public static final String COL_LOCKED = "locked";
    public static final String COL_METHOD = "method";
    public static final String COL_YR = "yr";
    public static final String COL_ACCOUNT = "account";
    public static final String COL_TIMESTAMP = "timeStamp";
    public static final String COL_NOTES = "notes";
    public static final String COL_BKR_ID = "bkr_id";

    //names of columns in the database
    public static final String DATABASE_COL_PK_ID = "id";
    public static final String DATABASE_COL_T_ID = "t_id";
    public static final String DATABASE_COL_BKRGROUP = "bkrGroup";
    public static final String DATABASE_COL_PROCESSED = "processed";   
    public static final String DATABASE_COL_SYMBOL = "symbol";
    public static final String DATABASE_COL_MATH_Q = "matchQ";
    public static final String DATABASE_COL_TRADE_TIME = "tradeTime";
    public static final String DATABASE_COL_P_ID = "p_id";
    public static final String DATABASE_COL_LINE = "line";
    public static final String DATABASE_COL_LOT_TIME = "lot_Time";
    public static final String DATABASE_COL_P_SYMBOL = "p_symbol";
    public static final String DATABASE_COL_P_Q = "p_Q";
    public static final String DATABASE_COL_P_PRICE_ADJ = "p_price_adj";
    public static final String DATABASE_COL_P_BASIS_ADJ = "p_basis_adj";
    public static final String DATABASE_COL_P_PRICE = "p_price";
    public static final String DATABASE_COL_P_BASIS = "p_basis";
    public static final String DATABASE_COL_MATH_PROCEEDS = "proceeds";
    public static final String DATABASE_COL_REALIZED_PL = "realized_PL";
    public static final String DATABASE_COL_HOLDING_DAYS = "holdingDays";
    public static final String DATABASE_COL_TERM = "term";
    public static final String DATABASE_COL_MATCHING = "matching";
    public static final String DATABASE_COL_MFLAG = "mflag";
    public static final String DATABASE_COL_LOCKED = "locked";
    public static final String DATABASE_COL_METHOD = "method";
    public static final String DATABASE_COL_YR = "yr";
    public static final String DATABASE_COL_ACCOUNT = "account";
    public static final String DATABASE_COL_TIMESTAMP = "timeStamp";
    public static final String DATABASE_COL_NOTES = "notes";
    public static final String DATABASE_COL_BKR_ID = "bkr_id";
 
   
 

    
    
    public static final String[] ALLOCATIONS_COL_NAMES = new String[]
    {
        COL_PK_ID,
    COL_T_ID ,
    COL_BKRGROUP,
    COL_PROCESSED,   
    COL_SYMBOL,
    COL_MATH_Q,
     COL_TRADE_TIME,
    COL_P_ID,
    COL_LINE,
    COL_LOT_TIME,
    COL_P_SYMBOL,
    COL_P_Q,
    COL_P_PRICE_ADJ,
    COL_P_BASIS_ADJ,
    COL_P_PRICE,
    COL_P_BASIS,
    COL_MATH_PROCEEDS,
    COL_REALIZED_PL,
    COL_HOLDING_DAYS,
    COL_TERM,
    COL_MATCHING,
    COL_MFLAG,
    COL_LOCKED,
    COL_METHOD,
    COL_YR ,
    COL_ACCOUNT,
    COL_TIMESTAMP,
    COL_NOTES,
    COL_BKR_ID
    };
    
    //Allocations database column names
     public static final String[] ALLOCATIONS_DATABASE_COL_NAMES = new String[]
    {
    DATABASE_COL_PK_ID,
    DATABASE_COL_T_ID ,
    DATABASE_COL_BKRGROUP,
    DATABASE_COL_PROCESSED,   
    DATABASE_COL_SYMBOL,
    DATABASE_COL_MATH_Q,
     DATABASE_COL_TRADE_TIME,
    DATABASE_COL_P_ID,
    DATABASE_COL_LINE,
    DATABASE_COL_LOT_TIME,
    DATABASE_COL_P_SYMBOL,
    DATABASE_COL_P_Q,
    DATABASE_COL_P_PRICE_ADJ,
    DATABASE_COL_P_BASIS_ADJ,
    DATABASE_COL_P_PRICE,
   DATABASE_COL_P_BASIS,
   DATABASE_COL_MATH_PROCEEDS,
    DATABASE_COL_REALIZED_PL,
    DATABASE_COL_HOLDING_DAYS,
    DATABASE_COL_TERM,
    DATABASE_COL_MATCHING,
    DATABASE_COL_MFLAG,
    DATABASE_COL_LOCKED,
    DATABASE_COL_METHOD,
    DATABASE_COL_YR ,
    DATABASE_COL_ACCOUNT,
    DATABASE_COL_TIMESTAMP,
    DATABASE_COL_NOTES,
    DATABASE_COL_BKR_ID
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
