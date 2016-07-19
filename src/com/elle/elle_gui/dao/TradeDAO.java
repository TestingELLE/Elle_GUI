
package com.elle.elle_gui.dao;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Trade;
import com.elle.elle_gui.logic.ITableConstants;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TradeDAO
 * @author Carlos Igreja
 * @since  May 7, 2016
 */
public class TradeDAO {

    // database table information
    public static final String DB_TABLE_NAME = "trades";
    public static final String COL_PK_ID = "id";
    public static final String COL_TRADE_TIME = "trade_Time";
    public static final String COL_OC = "OC";
    public static final String COL_LS = "LS";
    public static final String COL_SYMBOL = "symbol";
    public static final String COL_Q = "Q";
    public static final String COL_PRICE = "price";
    public static final String COL_COMM = "comm";
    public static final String COL_PROCEEDS = "proceeds";
    public static final String COL_BASIS = "basis";
    public static final String COL_ADJ_PROCEEDS = "adj_proceeds";
    public static final String COL_PROCESSED = "processed";
    public static final String COL_REALIZED_PL = "realized_PL";
    public static final String COL_CODES = "codes";
    public static final String COL_KSFLAG = "ksflag";
    public static final String COL_NOTES = "notes";
    public static final String COL_ACCOUNT = "account";
    public static final String COL_YR = "yr";
    public static final String COL_FILE_CODE = "filecode";
    public static final String COL_INPUT_LINE = "inputLine";
    public static final String COL_LOCKED = "locked";
    public static final String COL_MFLAG = "mflag";
    public static final String COL_SEC_TYPE = "secType";
    public static final String COL_MULTI = "multi";
    public static final String COL_UNDERLYING = "underlying";
    public static final String COL_EXPIRY = "expiry";
    public static final String COL_STRIKE = "strike";
    public static final String COL_O_TYPE = "O_Type";
    public static final String COL_BKRGROUP = "bkrGroup";
    public static final String COL_XCHANGE = "Xchange";
    public static final String COL_ORDER = "order";
    public static final String COL_FILLS = "fills";
    public static final String COL_TOTAL_Q = "TotalQ";
    public static final String COL_TIMESTAMP = "timeStamp";
    
    //Names of columns in the database
    public static final String DATABASE_COL_PK_ID = "id";
    public static final String DATABASE_COL_TRADE_TIME = "trade_Time";
    public static final String DATABASE_COL_OC = "OC";
    public static final String DATABASE_COL_LS = "LS";
    public static final String DATABASE_COL_SYMBOL = "symbol";
    public static final String DATABASE_COL_Q = "Q";
    public static final String DATABASE_COL_PRICE = "price";
    public static final String DATABASE_COL_COMM = "comm";
    public static final String DATABASE_COL_PROCEEDS = "proceeds";
    public static final String DATABASE_COL_BASIS = "basis";
    public static final String DATABASE_COL_ADJ_PROCEEDS = "adj_proceeds";
    public static final String DATABASE_COL_PROCESSED = "processed";
    public static final String DATABASE_COL_REALIZED_PL = "realized_PL";
    public static final String DATABASE_COL_CODES = "codes";
    public static final String DATABASE_COL_KSFLAG = "ksflag";
    public static final String DATABASE_COL_NOTES = "notes";
    public static final String DATABASE_COL_ACCOUNT = "account";
    public static final String DATABASE_COL_YR = "yr";
    public static final String DATABASE_COL_FILE_CODE = "filecode";
    public static final String DATABASE_COL_INPUT_LINE = "inputLine";
    public static final String DATABASE_COL_LOCKED = "locked";
    public static final String DATABASE_COL_MFLAG = "mflag";
    public static final String DATABASE_COL_SEC_TYPE = "secType";
    public static final String DATABASE_COL_MULTI = "multi";
    public static final String DATABASE_COL_UNDERLYING = "underlying";
    public static final String DATABASE_COL_EXPIRY = "expiry";
    public static final String DATABASE_COL_STRIKE = "strike";
    public static final String DATABASE_COL_O_TYPE = "O_Type";
    public static final String DATABASE_COL_BKRGROUP = "bkrGroup";
    public static final String DATABASE_COL_XCHANGE = "Xchange";
    public static final String DATABASE_COL_ORDER = "order";
    public static final String DATABASE_COL_FILLS = "fills";
    public static final String DATABASE_COL_TOTAL_Q = "TotalQ";
    public static final String DATABASE_COL_TIMESTAMP = "timeStamp";
    
    // Array of trades table column names to display in Elle_GUI
    public static final String[] TRADES_COL_NAMES = new String[]
    {
        COL_PK_ID,
        COL_TRADE_TIME,
        COL_OC,
        COL_LS,
        COL_SYMBOL,
        COL_Q,
        COL_PRICE,
        COL_COMM,
        COL_PROCEEDS,
        COL_BASIS,
        COL_ADJ_PROCEEDS,
        COL_PROCESSED,
        COL_REALIZED_PL,
        COL_CODES,
        COL_KSFLAG,
        COL_NOTES,
        COL_ACCOUNT,
        COL_YR,
        COL_FILE_CODE,
        COL_INPUT_LINE,
        COL_LOCKED,
        COL_MFLAG,
        COL_SEC_TYPE,
        COL_MULTI,
       COL_UNDERLYING,
        COL_EXPIRY,
        COL_STRIKE,
        COL_O_TYPE,
        COL_BKRGROUP,
        COL_XCHANGE,
        COL_ORDER,
        COL_FILLS,
        COL_TOTAL_Q,
        COL_TIMESTAMP
    };
    
    //Array of trades database column Names 
    public static final String[] TRADES_DATABASE_COL_NAMES = new String[]
    {
        DATABASE_COL_PK_ID,
        DATABASE_COL_TRADE_TIME,
        DATABASE_COL_OC,
        DATABASE_COL_LS,
        DATABASE_COL_SYMBOL,
        DATABASE_COL_Q,
        DATABASE_COL_PRICE,
        DATABASE_COL_COMM,
        DATABASE_COL_PROCEEDS,
        DATABASE_COL_BASIS,
        DATABASE_COL_ADJ_PROCEEDS,
        DATABASE_COL_PROCESSED,
        DATABASE_COL_REALIZED_PL,
        DATABASE_COL_CODES,
        DATABASE_COL_KSFLAG,
        DATABASE_COL_NOTES,
        DATABASE_COL_ACCOUNT,
        DATABASE_COL_YR,
        DATABASE_COL_FILE_CODE,
        DATABASE_COL_INPUT_LINE,
        DATABASE_COL_LOCKED,
        DATABASE_COL_MFLAG,
        DATABASE_COL_SEC_TYPE,
        DATABASE_COL_MULTI,
        DATABASE_COL_UNDERLYING,
        DATABASE_COL_EXPIRY,
        DATABASE_COL_STRIKE,
        DATABASE_COL_O_TYPE,
        DATABASE_COL_BKRGROUP,
        DATABASE_COL_XCHANGE,
        DATABASE_COL_ORDER,
        DATABASE_COL_FILLS,
        DATABASE_COL_TOTAL_Q,
        DATABASE_COL_TIMESTAMP
    };
 //map of the column names in the database to the column names to display 
    public static Map<String, String>  columnNameConstants = getColumnNameConstants();
           
    
    public static Map getColumnNameConstants(){
        Map<String, String> constants = new HashMap <String, String>();
        for (int col = 0; col < TRADES_DATABASE_COL_NAMES.length; col++){
            constants.put(TRADES_DATABASE_COL_NAMES[col],
                    TRADES_COL_NAMES[col]);
        }
        return constants;
    }
}
