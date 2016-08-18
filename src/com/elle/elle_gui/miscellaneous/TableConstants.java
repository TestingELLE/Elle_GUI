package com.elle.elle_gui.miscellaneous;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.elle.elle_gui.miscellaneous.LoggingAspect;

/**
 *Noninstantiable utility class that stores the table constants
 * @author corinne
 */
public final class TableConstants {
       
    // account names
    public static final String IB9048_ACCOUNT_NAME = "IB9048";
    public static final String TOS3622_ACCOUNT_NAME = "TOS3622";
    public static final String COMBINED_ACCOUNT_NAME = "Combined";
    
    //immutable list of account names
    public static final List<String> ACCOUNT_NAMES = Collections.unmodifiableList(Arrays.asList
            (IB9048_ACCOUNT_NAME,TOS3622_ACCOUNT_NAME,COMBINED_ACCOUNT_NAME));

    // table names
    public static final String POSITIONS_TABLE_NAME = "positions";
    public static final String TRADES_TABLE_NAME = "trades";
    public static final String ALLOCATIONS_TABLE_NAME = "allocations";
    
    //immutable list of table names
    public static final List<String> TABLE_NAMES = Collections.unmodifiableList(Arrays.asList
            (POSITIONS_TABLE_NAME, TRADES_TABLE_NAME,ALLOCATIONS_TABLE_NAME));
    
    //column name constants
    
    //trades table column names to display in Elle_GUI
    public static final String TRADES_COL_PK_ID = "id";
    public static final String TRADES_COL_TRADE_TIME = "trade_Time";
    public static final String TRADES_COL_OC = "OC";
    public static final String TRADES_COL_LS = "LS";
    public static final String TRADES_COL_SYMBOL = "symbol";
    public static final String TRADES_COL_Q = "Q";
    public static final String TRADES_COL_PRICE = "price";
    public static final String TRADES_COL_COMM = "comm";
    public static final String TRADES_COL_PROCEEDS = "proceeds";
    public static final String TRADES_COL_BASIS = "basis";
    public static final String TRADES_COL_ADJ_PROCEEDS = "adj_proceeds";
    public static final String TRADES_COL_PROCESSED = "processed";
    public static final String TRADES_COL_REALIZED_PL = "realized_PL";
    public static final String TRADES_COL_CODES = "codes";
    public static final String TRADES_COL_KSFLAG = "ksflag";
    public static final String TRADES_COL_NOTES = "notes";
    public static final String TRADES_COL_ACCOUNT = "account";
    public static final String TRADES_COL_YR = "yr";
    public static final String TRADES_COL_FILE_CODE = "filecode";
    public static final String TRADES_COL_INPUT_LINE = "inputLine";
    public static final String TRADES_COL_LOCKED = "locked";
    public static final String TRADES_COL_MFLAG = "mflag";
    public static final String TRADES_COL_SEC_TYPE = "secType";
    public static final String TRADES_COL_MULTI = "multi";
    public static final String TRADES_COL_UNDERLYING = "underlying";
    public static final String TRADES_COL_EXPIRY = "expiry";
    public static final String TRADES_COL_STRIKE = "strike";
    public static final String TRADES_COL_O_TYPE = "O_Type";
    public static final String TRADES_COL_BKRGROUP = "bkrGroup";
    public static final String TRADES_COL_XCHANGE = "Xchange";
    public static final String TRADES_COL_ORDER = "order";
    public static final String TRADES_COL_FILLS = "fills";
    public static final String TRADES_COL_TOTAL_Q = "TotalQ";
    public static final String TRADES_COL_TIMESTAMP = "timeStamp";
    
    //trades table expected database columns names
    public static final String TRADES_DATABASE_COL_PK_ID = "id";
    public static final String TRADES_DATABASE_COL_TRADE_TIME = "trade_Time";
    public static final String TRADES_DATABASE_COL_OC = "OC";
    public static final String TRADES_DATABASE_COL_LS = "LS";
    public static final String TRADES_DATABASE_COL_SYMBOL = "symbol";
    public static final String TRADES_DATABASE_COL_Q = "Q";
    public static final String TRADES_DATABASE_COL_PRICE = "price";
    public static final String TRADES_DATABASE_COL_COMM = "comm";
    public static final String TRADES_DATABASE_COL_PROCEEDS = "proceeds";
    public static final String TRADES_DATABASE_COL_BASIS = "basis";
    public static final String TRADES_DATABASE_COL_ADJ_PROCEEDS = "adj_proceeds";
    public static final String TRADES_DATABASE_COL_PROCESSED = "processed";
    public static final String TRADES_DATABASE_COL_REALIZED_PL = "realized_PL";
    public static final String TRADES_DATABASE_COL_CODES = "codes";
    public static final String TRADES_DATABASE_COL_KSFLAG = "ksflag";
    public static final String TRADES_DATABASE_COL_NOTES = "notes";
    public static final String TRADES_DATABASE_COL_ACCOUNT = "account";
    public static final String TRADES_DATABASE_COL_YR = "yr";
    public static final String TRADES_DATABASE_COL_FILE_CODE = "filecode";
    public static final String TRADES_DATABASE_COL_INPUT_LINE = "inputLine";
    public static final String TRADES_DATABASE_COL_LOCKED = "locked";
    public static final String TRADES_DATABASE_COL_MFLAG = "mflag";
    public static final String TRADES_DATABASE_COL_SEC_TYPE = "secType";
    public static final String TRADES_DATABASE_COL_MULTI = "multi";
    public static final String TRADES_DATABASE_COL_UNDERLYING = "underlying";
    public static final String TRADES_DATABASE_COL_EXPIRY = "expiry";
    public static final String TRADES_DATABASE_COL_STRIKE = "strike";
    public static final String TRADES_DATABASE_COL_O_TYPE = "O_Type";
    public static final String TRADES_DATABASE_COL_BKRGROUP = "bkrGroup";
    public static final String TRADES_DATABASE_COL_XCHANGE = "Xchange";
    public static final String TRADES_DATABASE_COL_ORDER = "order";
    public static final String TRADES_DATABASE_COL_FILLS = "fills";
    public static final String TRADES_DATABASE_COL_TOTAL_Q = "TotalQ";
    public static final String TRADES_DATABASE_COL_TIMESTAMP = "timeStamp";
    
    //Positions table column names to display in Elle_GUI
    public static final String POSITIONS_COL_PK_ID = "pos_id";
    public static final String POSITIONS_COL_SYMBOL = "symbol";
    public static final String POSITIONS_COL_LOT_TIME = "lot_Time";
    public static final String POSITIONS_COL_Q = "Q";
    public static final String POSITIONS_COL_LINE = "line";
    public static final String POSITIONS_COL_OCE = "OCE";
    public static final String POSITIONS_COL_OCE_TIME = "OCE_Time";
    public static final String POSITIONS_COL_LS = "LS";
    public static final String POSITIONS_COL_PRICE_ADJ = "price_adj";
    public static final String POSITIONS_COL_BASIS_ADJ = "basis_adj";
    public static final String POSITIONS_COL_PRICE = "price";
    public static final String POSITIONS_COL_BASIS = "basis";
    public static final String POSITIONS_COL_HOW = "how";
    public static final String POSITIONS_COL_T_ID = "T_id";
    public static final String POSITIONS_COL_WASH = "W";
    public static final String POSITIONS_COL_KSFLAG = "ksflag";
    public static final String POSITIONS_COL_CODES = "codes";
    public static final String POSITIONS_COL_ACCOUNT = "account";
    public static final String POSITIONS_COL_YR = "yr";
    public static final String POSITIONS_COL_SEC_TYPE = "secType";
    public static final String POSITIONS_COL_MULTI = "multi";
    public static final String POSITIONS_COL_UNDERLYING = "underlying";
    public static final String POSITIONS_COL_EXPIRY = "expiry";
    public static final String POSITIONS_COL_STRIKE = "strike";
    public static final String POSITIONS_COL_O_TYPE = "O_Type";
    public static final String POSITIONS_COL_NOTES = "notes";
    public static final String POSITIONS_COL_FILECODE = "filecode";
    public static final String POSITIONS_COL_INPUTLINE = "inputLine";
    public static final String POSITIONS_COL_GRP = "grp";
    public static final String POSITIONS_COL_TIMESTAMP = "timeStamp";
    
    //names of positions table  columns in the database 
    public static final String POSITIONS_DATABASE_COL_PK_ID = "pos_id";
    public static final String POSITIONS_DATABASE_COL_SYMBOL = "symbol";
    public static final String POSITIONS_DATABASE_COL_LOT_TIME = "lot_Time";
    public static final String POSITIONS_DATABASE_COL_Q = "Q";
    public static final String POSITIONS_DATABASE_COL_LINE = "line";
    public static final String POSITIONS_DATABASE_COL_OCE = "OCE";
    public static final String POSITIONS_DATABASE_COL_OCE_TIME = "OCE_Time";
    public static final String POSITIONS_DATABASE_COL_LS = "LS";
    public static final String POSITIONS_DATABASE_COL_PRICE_ADJ = "price_adj";
    public static final String POSITIONS_DATABASE_COL_BASIS_ADJ = "basis_adj";
    public static final String POSITIONS_DATABASE_COL_PRICE = "price";
    public static final String POSITIONS_DATABASE_COL_BASIS = "basis";
    public static final String POSITIONS_DATABASE_COL_HOW = "how";
    public static final String POSITIONS_DATABASE_COL_T_ID = "T_id";
    public static final String POSITIONS_DATABASE_COL_WASH = "wash";
    public static final String POSITIONS_DATABASE_COL_KSFLAG = "ksflag";
    public static final String POSITIONS_DATABASE_COL_CODES = "codes";
    public static final String POSITIONS_DATABASE_COL_ACCOUNT = "account";
    public static final String POSITIONS_DATABASE_COL_YR = "yr";
    public static final String POSITIONS_DATABASE_COL_SEC_TYPE = "secType";
    public static final String POSITIONS_DATABASE_COL_MULTI = "multi";
    public static final String POSITIONS_DATABASE_COL_UNDERLYING = "underlying";
    public static final String POSITIONS_DATABASE_COL_EXPIRY = "expiry";
    public static final String POSITIONS_DATABASE_COL_STRIKE = "strike";
    public static final String POSITIONS_DATABASE_COL_O_TYPE = "O_Type";
    public static final String POSITIONS_DATABASE_COL_NOTES = "notes";
    public static final String POSITIONS_DATABASE_COL_FILECODE = "filecode";
    public static final String POSITIONS_DATABASE_COL_INPUTLINE = "inputLine";
    public static final String POSITIONS_DATABASE_COL_GRP = "grp";
    public static final String POSITIONS_DATABASE_COL_TIMESTAMP = "timeStamp";

    
    // Allocations column names to display in Elle_GUI
    public static final String ALLOCATIONS_COL_PK_ID = "id";
    public static final String ALLOCATIONS_COL_T_ID = "t_id";
    public static final String ALLOCATIONS_COL_BKRGROUP = "bkrGroup";
    public static final String ALLOCATIONS_COL_PROCESSED = "processed";   
    public static final String ALLOCATIONS_COL_SYMBOL = "symbol";
    public static final String ALLOCATIONS_COL_MATH_Q = "matchQ";
    public static final String ALLOCATIONS_COL_TRADE_TIME = "tradeTime";
    public static final String ALLOCATIONS_COL_P_ID = "p_id";
    public static final String ALLOCATIONS_COL_LINE = "line";
    public static final String ALLOCATIONS_COL_LOT_TIME = "lot_Time";
    public static final String ALLOCATIONS_COL_P_SYMBOL = "p_symbol";
    public static final String ALLOCATIONS_COL_P_Q = "p_Q";
    public static final String ALLOCATIONS_COL_P_PRICE_ADJ = "p_price_adj";
    public static final String ALLOCATIONS_COL_P_BASIS_ADJ = "p_basis_adj";
    public static final String ALLOCATIONS_COL_P_PRICE = "p_price";
    public static final String ALLOCATIONS_COL_P_BASIS = "p_basis";
    public static final String ALLOCATIONS_COL_MATH_PROCEEDS = "proceeds";
    public static final String ALLOCATIONS_COL_REALIZED_PL = "realized_PL";
    public static final String ALLOCATIONS_COL_HOLDING_DAYS = "holdingDays";
    public static final String ALLOCATIONS_COL_TERM = "term";
    public static final String ALLOCATIONS_COL_MATCHING = "matching";
    public static final String ALLOCATIONS_COL_MFLAG = "mflag";
    public static final String ALLOCATIONS_COL_LOCKED = "locked";
    public static final String ALLOCATIONS_COL_METHOD = "method";
    public static final String ALLOCATIONS_COL_YR = "yr";
    public static final String ALLOCATIONS_COL_ACCOUNT = "account";
    public static final String ALLOCATIONS_COL_TIMESTAMP = "timeStamp";
    public static final String ALLOCATIONS_COL_NOTES = "notes";
    public static final String ALLOCATIONS_COL_BKR_ID = "bkr_id";

    //names of allocations table columns in the database
    public static final String ALLOCATIONS_DATABASE_COL_PK_ID = "id";
    public static final String ALLOCATIONS_DATABASE_COL_T_ID = "t_id";
    public static final String ALLOCATIONS_DATABASE_COL_BKRGROUP = "bkrGroup";
    public static final String ALLOCATIONS_DATABASE_COL_PROCESSED = "processed";   
    public static final String ALLOCATIONS_DATABASE_COL_SYMBOL = "symbol";
    public static final String ALLOCATIONS_DATABASE_COL_MATH_Q = "matchQ";
    public static final String ALLOCATIONS_DATABASE_COL_TRADE_TIME = "tradeTime";
    public static final String ALLOCATIONS_DATABASE_COL_P_ID = "p_id";
    public static final String ALLOCATIONS_DATABASE_COL_LINE = "line";
    public static final String ALLOCATIONS_DATABASE_COL_LOT_TIME = "lot_Time";
    public static final String ALLOCATIONS_DATABASE_COL_P_SYMBOL = "p_symbol";
    public static final String ALLOCATIONS_DATABASE_COL_P_Q = "p_Q";
    public static final String ALLOCATIONS_DATABASE_COL_P_PRICE_ADJ = "p_price_adj";
    public static final String ALLOCATIONS_DATABASE_COL_P_BASIS_ADJ = "p_basis_adj";
    public static final String ALLOCATIONS_DATABASE_COL_P_PRICE = "p_price";
    public static final String ALLOCATIONS_DATABASE_COL_P_BASIS = "p_basis";
    public static final String ALLOCATIONS_DATABASE_COL_MATH_PROCEEDS = "proceeds";
    public static final String ALLOCATIONS_DATABASE_COL_REALIZED_PL = "realized_PL";
    public static final String ALLOCATIONS_DATABASE_COL_HOLDING_DAYS = "holdingDays";
    public static final String ALLOCATIONS_DATABASE_COL_TERM = "term";
    public static final String ALLOCATIONS_DATABASE_COL_MATCHING = "matching";
    public static final String ALLOCATIONS_DATABASE_COL_MFLAG = "mflag";
    public static final String ALLOCATIONS_DATABASE_COL_LOCKED = "locked";
    public static final String ALLOCATIONS_DATABASE_COL_METHOD = "method";
    public static final String ALLOCATIONS_DATABASE_COL_YR = "yr";
    public static final String ALLOCATIONS_DATABASE_COL_ACCOUNT = "account";
    public static final String ALLOCATIONS_DATABASE_COL_TIMESTAMP = "timeStamp";
    public static final String ALLOCATIONS_DATABASE_COL_NOTES = "notes";
    public static final String ALLOCATIONS_DATABASE_COL_BKR_ID = "bkr_id";
 
    // Immutable list of trades table column names to display in Elle_GUI
    public static final List<String> TRADES_COL_NAMES = Collections.unmodifiableList(Arrays.asList(
        TRADES_COL_PK_ID,
        TRADES_COL_TRADE_TIME,
        TRADES_COL_OC,
        TRADES_COL_LS,
        TRADES_COL_SYMBOL,
        TRADES_COL_Q,
        TRADES_COL_PRICE,
        TRADES_COL_COMM,
        TRADES_COL_PROCEEDS,
        TRADES_COL_BASIS,
        TRADES_COL_ADJ_PROCEEDS,
        TRADES_COL_PROCESSED,
        TRADES_COL_REALIZED_PL,
        TRADES_COL_CODES,
        TRADES_COL_KSFLAG,
        TRADES_COL_NOTES,
        TRADES_COL_ACCOUNT,
        TRADES_COL_YR,
        TRADES_COL_FILE_CODE,
        TRADES_COL_INPUT_LINE,
        TRADES_COL_LOCKED,
        TRADES_COL_MFLAG,
        TRADES_COL_SEC_TYPE,
        TRADES_COL_MULTI,
        TRADES_COL_UNDERLYING,
        TRADES_COL_EXPIRY,
        TRADES_COL_STRIKE,
        TRADES_COL_O_TYPE,
        TRADES_COL_BKRGROUP,
        TRADES_COL_XCHANGE,
        TRADES_COL_ORDER,
        TRADES_COL_FILLS,
        TRADES_COL_TOTAL_Q,
        TRADES_COL_TIMESTAMP
    ));
    
     // Immutable list of trades table column names to display in Elle_GUI
    public static final List<String> TRADES_DATABASE_COL_NAMES = Collections.unmodifiableList(Arrays.asList(
        TRADES_DATABASE_COL_PK_ID,
        TRADES_DATABASE_COL_TRADE_TIME,
        TRADES_DATABASE_COL_OC,
        TRADES_DATABASE_COL_LS,
        TRADES_DATABASE_COL_SYMBOL,
        TRADES_DATABASE_COL_Q,
        TRADES_DATABASE_COL_PRICE,
        TRADES_DATABASE_COL_COMM,
        TRADES_DATABASE_COL_PROCEEDS,
        TRADES_DATABASE_COL_BASIS,
        TRADES_DATABASE_COL_ADJ_PROCEEDS,
        TRADES_DATABASE_COL_PROCESSED,
        TRADES_DATABASE_COL_REALIZED_PL,
        TRADES_DATABASE_COL_CODES,
        TRADES_DATABASE_COL_KSFLAG,
        TRADES_DATABASE_COL_NOTES,
        TRADES_DATABASE_COL_ACCOUNT,
        TRADES_DATABASE_COL_YR,
        TRADES_DATABASE_COL_FILE_CODE,
        TRADES_DATABASE_COL_INPUT_LINE,
        TRADES_DATABASE_COL_LOCKED,
        TRADES_DATABASE_COL_MFLAG,
        TRADES_DATABASE_COL_SEC_TYPE,
        TRADES_DATABASE_COL_MULTI,
        TRADES_DATABASE_COL_UNDERLYING,
        TRADES_DATABASE_COL_EXPIRY,
        TRADES_DATABASE_COL_STRIKE,
        TRADES_DATABASE_COL_O_TYPE,
        TRADES_DATABASE_COL_BKRGROUP,
        TRADES_DATABASE_COL_XCHANGE,
        TRADES_DATABASE_COL_ORDER,
        TRADES_DATABASE_COL_FILLS,
        TRADES_DATABASE_COL_TOTAL_Q,
        TRADES_DATABASE_COL_TIMESTAMP
    ));
    
    // Array of Positions table column names
    public static final List <String> POSITIONS_DATABASE_COL_NAMES = Collections.unmodifiableList(Arrays.asList(
        POSITIONS_DATABASE_COL_PK_ID,
        POSITIONS_DATABASE_COL_SYMBOL,
        POSITIONS_DATABASE_COL_LOT_TIME,
        POSITIONS_DATABASE_COL_Q,
        POSITIONS_DATABASE_COL_LINE,
        POSITIONS_DATABASE_COL_OCE,
        POSITIONS_DATABASE_COL_OCE_TIME,
        POSITIONS_DATABASE_COL_LS,
        POSITIONS_DATABASE_COL_PRICE_ADJ,
        POSITIONS_DATABASE_COL_BASIS_ADJ,
        POSITIONS_DATABASE_COL_PRICE,
        POSITIONS_DATABASE_COL_BASIS,
        POSITIONS_DATABASE_COL_HOW,
        POSITIONS_DATABASE_COL_T_ID,
        POSITIONS_DATABASE_COL_WASH,
        POSITIONS_DATABASE_COL_KSFLAG,
        POSITIONS_DATABASE_COL_CODES,
        POSITIONS_DATABASE_COL_ACCOUNT,
        POSITIONS_DATABASE_COL_YR,
        POSITIONS_DATABASE_COL_SEC_TYPE,
        POSITIONS_DATABASE_COL_MULTI,
        POSITIONS_DATABASE_COL_UNDERLYING,
        POSITIONS_DATABASE_COL_EXPIRY,
        POSITIONS_DATABASE_COL_STRIKE,
        POSITIONS_DATABASE_COL_O_TYPE,
        POSITIONS_DATABASE_COL_NOTES,
        POSITIONS_DATABASE_COL_FILECODE,
        POSITIONS_DATABASE_COL_INPUTLINE,
        POSITIONS_DATABASE_COL_GRP,
        POSITIONS_DATABASE_COL_TIMESTAMP
    ));

    
    // Immutable list of positions table column names to display in Elle_GUI
     public static final List <String> POSITIONS_COL_NAMES =Collections.unmodifiableList(Arrays.asList(
        POSITIONS_COL_PK_ID,
        POSITIONS_COL_SYMBOL,
        POSITIONS_COL_LOT_TIME,
        POSITIONS_COL_Q,
        POSITIONS_COL_LINE,
        POSITIONS_COL_OCE,
        POSITIONS_COL_OCE_TIME,
        POSITIONS_COL_LS,
        POSITIONS_COL_PRICE_ADJ,
        POSITIONS_COL_BASIS_ADJ,
        POSITIONS_COL_PRICE,
        POSITIONS_COL_BASIS,
        POSITIONS_COL_HOW,
        POSITIONS_COL_T_ID,
        POSITIONS_COL_WASH,
        POSITIONS_COL_KSFLAG,
        POSITIONS_COL_CODES,
        POSITIONS_COL_ACCOUNT,
        POSITIONS_COL_YR,
        POSITIONS_COL_SEC_TYPE,
        POSITIONS_COL_MULTI,
        POSITIONS_COL_UNDERLYING,
        POSITIONS_COL_EXPIRY,
        POSITIONS_COL_STRIKE,
        POSITIONS_COL_O_TYPE,
        POSITIONS_COL_NOTES,
        POSITIONS_COL_FILECODE,
        POSITIONS_COL_INPUTLINE,
        POSITIONS_COL_GRP,
        POSITIONS_COL_TIMESTAMP
     ));
     
     //immutable list of allocations table column names to display in Elle_GUI
    public static final List<String> ALLOCATIONS_COL_NAMES = Collections.unmodifiableList(Arrays.asList(
    ALLOCATIONS_COL_PK_ID,
    ALLOCATIONS_COL_T_ID ,
    ALLOCATIONS_COL_BKRGROUP,
    ALLOCATIONS_COL_PROCESSED,   
    ALLOCATIONS_COL_SYMBOL,
    ALLOCATIONS_COL_MATH_Q,
    ALLOCATIONS_COL_TRADE_TIME,
    ALLOCATIONS_COL_P_ID,
    ALLOCATIONS_COL_LINE,
    ALLOCATIONS_COL_LOT_TIME,
    ALLOCATIONS_COL_P_SYMBOL,
    ALLOCATIONS_COL_P_Q,
    ALLOCATIONS_COL_P_PRICE_ADJ,
    ALLOCATIONS_COL_P_BASIS_ADJ,
    ALLOCATIONS_COL_P_PRICE,
    ALLOCATIONS_COL_P_BASIS,
    ALLOCATIONS_COL_MATH_PROCEEDS,
    ALLOCATIONS_COL_REALIZED_PL,
    ALLOCATIONS_COL_HOLDING_DAYS,
    ALLOCATIONS_COL_TERM,
    ALLOCATIONS_COL_MATCHING,
    ALLOCATIONS_COL_MFLAG,
    ALLOCATIONS_COL_LOCKED,
    ALLOCATIONS_COL_METHOD,
    ALLOCATIONS_COL_YR ,
    ALLOCATIONS_COL_ACCOUNT,
    ALLOCATIONS_COL_TIMESTAMP,
    ALLOCATIONS_COL_NOTES,
    ALLOCATIONS_COL_BKR_ID
    ));
    
    //Immutable list of allocations table column names in the database
     public static final List <String> ALLOCATIONS_DATABASE_COL_NAMES = Collections.unmodifiableList(Arrays.asList(
    ALLOCATIONS_DATABASE_COL_PK_ID,
    ALLOCATIONS_DATABASE_COL_T_ID ,
    ALLOCATIONS_DATABASE_COL_BKRGROUP,
    ALLOCATIONS_DATABASE_COL_PROCESSED,   
    ALLOCATIONS_DATABASE_COL_SYMBOL,
    ALLOCATIONS_DATABASE_COL_MATH_Q,
    ALLOCATIONS_DATABASE_COL_TRADE_TIME,
    ALLOCATIONS_DATABASE_COL_P_ID,
    ALLOCATIONS_DATABASE_COL_LINE,
    ALLOCATIONS_DATABASE_COL_LOT_TIME,
    ALLOCATIONS_DATABASE_COL_P_SYMBOL,
    ALLOCATIONS_DATABASE_COL_P_Q,
    ALLOCATIONS_DATABASE_COL_P_PRICE_ADJ,
    ALLOCATIONS_DATABASE_COL_P_BASIS_ADJ,
    ALLOCATIONS_DATABASE_COL_P_PRICE,
    ALLOCATIONS_DATABASE_COL_P_BASIS,
    ALLOCATIONS_DATABASE_COL_MATH_PROCEEDS,
    ALLOCATIONS_DATABASE_COL_REALIZED_PL,
    ALLOCATIONS_DATABASE_COL_HOLDING_DAYS,
    ALLOCATIONS_DATABASE_COL_TERM,
    ALLOCATIONS_DATABASE_COL_MATCHING,
    ALLOCATIONS_DATABASE_COL_MFLAG,
    ALLOCATIONS_DATABASE_COL_LOCKED,
    ALLOCATIONS_DATABASE_COL_METHOD,
    ALLOCATIONS_DATABASE_COL_YR ,
    ALLOCATIONS_DATABASE_COL_ACCOUNT,
    ALLOCATIONS_DATABASE_COL_TIMESTAMP,
    ALLOCATIONS_DATABASE_COL_NOTES,
    ALLOCATIONS_DATABASE_COL_BKR_ID
    ));

    //map of the database column names to the column names to display 
    public static Map<String, String>  TRADES_COLUMN_NAME_CONSTANTS = setColumnNameConstants(TRADES_DATABASE_COL_NAMES, TRADES_COL_NAMES);
    public static Map<String, String>  POSITIONS_COLUMN_NAME_CONSTANTS = setColumnNameConstants(POSITIONS_DATABASE_COL_NAMES, POSITIONS_COL_NAMES); 
    public static Map<String, String>  ALLOCATIONS_COLUMN_NAME_CONSTANTS = setColumnNameConstants(ALLOCATIONS_DATABASE_COL_NAMES, ALLOCATIONS_COL_NAMES);
    

// Suppress default constructor for noninstantiability
    private TableConstants(){
        throw new AssertionError();
    }
    
    //returns the list of column name constants corresponding to the table name 
    public static List<String> getColumnConstants(String tableName){
        List <String> columnConstants = null;
        switch(tableName){
            case TRADES_TABLE_NAME:
                columnConstants = TRADES_DATABASE_COL_NAMES;
                break;
            case POSITIONS_TABLE_NAME:
                columnConstants = POSITIONS_DATABASE_COL_NAMES;
                break;
            case ALLOCATIONS_TABLE_NAME:
                columnConstants = ALLOCATIONS_DATABASE_COL_NAMES;
                break;
            default: 
                // this means an invalid table name constant was passed
                // this exception will be handled and thrown here
                // the program will still run and show the stack trace for debugging
                
                try {
                    String errorMessage = "ERROR: unknown table";
                    throw new NoSuchFieldException(errorMessage);
                } catch (NoSuchFieldException ex) {
                    LoggingAspect.afterThrown(ex);
                }

                break;
        }
        
        return columnConstants;
    }
   
    //returns a map of database column names to the column names to display
    //corresponding to the table name 
    public static Map<String, String> getColumnConstantsMap(String tableName){
        Map <String, String> columnConstantsMap = null;
        switch(tableName){
            case TRADES_TABLE_NAME:
                columnConstantsMap = TRADES_COLUMN_NAME_CONSTANTS;
                break;
            case POSITIONS_TABLE_NAME:
                columnConstantsMap = POSITIONS_COLUMN_NAME_CONSTANTS;
                break;
            case ALLOCATIONS_TABLE_NAME:
                columnConstantsMap = ALLOCATIONS_COLUMN_NAME_CONSTANTS;
                break;
            default: 
                // this means an invalid table name constant was passed
                // this exception will be handled and thrown here
                // the program will still run and show the stack trace for debugging
                
                try {
                    String errorMessage = "ERROR: unknown table";
                    throw new NoSuchFieldException(errorMessage);
                } catch (NoSuchFieldException ex) {
                    LoggingAspect.afterThrown(ex);
                }

                break;
        }
        
        return columnConstantsMap;
    }
    
    // creates of a map of the database column name constants to the corresponding column names to display in Elle_GUI
   private static Map setColumnNameConstants(List<String> databaseColNames, List<String> colNames){
        Map<String, String> constants = new HashMap <String, String>();
        for (int col = 0; col < databaseColNames.size(); col++){
            constants.put(databaseColNames.get(col),
                    colNames.get(col));
        }
        return constants;
    }  
}
