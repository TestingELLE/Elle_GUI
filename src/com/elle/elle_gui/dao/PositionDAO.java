
package com.elle.elle_gui.dao;

import com.elle.elle_gui.logic.ITableConstants;
import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Position;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * PositionDAO
 * @author Carlos Igreja
 * @since  May 7, 2016
 */
public class PositionDAO {

    // database table information
    public static final String DB_TABLE_NAME = "positions";
    
    //Column names to display in Elle_GUI
    public static final String COL_PK_ID = "pos_id";
    public static final String COL_SYMBOL = "symbol";
    public static final String COL_LOT_TIME = "lot_Time";
    public static final String COL_Q = "Q";
    public static final String COL_LINE = "line";
    public static final String COL_OCE = "OCE";
    public static final String COL_OCE_TIME = "OCE_Time";
    public static final String COL_LS = "LS";
    public static final String COL_PRICE_ADJ = "price_adj";
    public static final String COL_BASIS_ADJ = "basis_adj";
    public static final String COL_PRICE = "price";
    public static final String COL_BASIS = "basis";
    public static final String COL_HOW = "How";
    public static final String COL_T_ID = "T_id";
    public static final String COL_WASH = "wash";
    public static final String COL_KSFLAG = "ksflag";
    public static final String COL_CODES = "codes";
    public static final String COL_ACCOUNT = "account";
    public static final String COL_YR = "yr";
    public static final String COL_SEC_TYPE = "secType";
    public static final String COL_MULTI = "multi";
    public static final String COL_UNDERLYING = "underlying";
    public static final String COL_EXPIRY = "expiry";
    public static final String COL_STRIKE = "strike";
    public static final String COL_O_TYPE = "O_Type";
    public static final String COL_NOTES = "notes";
    public static final String COL_FILECODE = "filecode";
    public static final String COL_INPUTLINE = "inputLine";
    public static final String COL_GRP = "grp";
    public static final String COL_TIMESTAMP = "timeStamp";
    
    //names of columns in the database
    public static final String DATABASE_COL_PK_ID = "pos_id";
    public static final String DATABASE_COL_SYMBOL = "symbol";
    public static final String DATABASE_COL_LOT_TIME = "lot_Time";
    public static final String DATABASE_COL_Q = "Q";
    public static final String DATABASE_COL_LINE = "line";
    public static final String DATABASE_COL_OCE = "OCE";
    public static final String DATABASE_COL_OCE_TIME = "OCE_Time";
    public static final String DATABASE_COL_LS = "LS";
    public static final String DATABASE_COL_PRICE_ADJ = "price_adj";
    public static final String DATABASE_COL_BASIS_ADJ = "basis_adj";
    public static final String DATABASE_COL_PRICE = "price";
    public static final String DATABASE_COL_BASIS = "basis";
    public static final String DATABASE_COL_HOW = "How";
    public static final String DATABASE_COL_T_ID = "T_id";
    public static final String DATABASE_COL_WASH = "wash";
    public static final String DATABASE_COL_KSFLAG = "ksflag";
    public static final String DATABASE_COL_CODES = "codes";
    public static final String DATABASE_COL_ACCOUNT = "account";
    public static final String DATABASE_COL_YR = "yr";
    public static final String DATABASE_COL_SEC_TYPE = "secType";
    public static final String DATABASE_COL_MULTI = "multi";
    public static final String DATABASE_COL_UNDERLYING = "underlying";
    public static final String DATABASE_COL_EXPIRY = "expiry";
    public static final String DATABASE_COL_STRIKE = "strike";
    public static final String DATABASE_COL_O_TYPE = "O_Type";
    public static final String DATABASE_COL_NOTES = "notes";
    public static final String DATABASE_COL_FILECODE = "filecode";
    public static final String DATABASE_COL_INPUTLINE = "inputLine";
    public static final String DATABASE_COL_GRP = "grp";
    public static final String DATABASE_COL_TIMESTAMP = "timeStamp";

    // Array of Positions table column names
    public static final String[] POSITIONS_DATABASE_COL_NAMES = new String[]
    {
        DATABASE_COL_PK_ID,
        DATABASE_COL_SYMBOL,
        DATABASE_COL_LOT_TIME,
        DATABASE_COL_Q,
        DATABASE_COL_LINE,
        DATABASE_COL_OCE,
        DATABASE_COL_OCE_TIME,
        DATABASE_COL_LS,
        DATABASE_COL_PRICE_ADJ,
        DATABASE_COL_BASIS_ADJ,
        DATABASE_COL_PRICE,
        DATABASE_COL_BASIS,
        DATABASE_COL_HOW,
        DATABASE_COL_T_ID,
        DATABASE_COL_WASH,
        DATABASE_COL_KSFLAG,
        DATABASE_COL_CODES,
        DATABASE_COL_ACCOUNT,
        DATABASE_COL_YR,
        DATABASE_COL_SEC_TYPE,
        DATABASE_COL_MULTI,
        DATABASE_COL_UNDERLYING,
        DATABASE_COL_EXPIRY,
        DATABASE_COL_STRIKE,
        DATABASE_COL_O_TYPE,
        DATABASE_COL_NOTES,
        DATABASE_COL_FILECODE,
        DATABASE_COL_INPUTLINE,
        DATABASE_COL_GRP,
        DATABASE_COL_TIMESTAMP
    };    
    // Array of Positions table column names to display in Elle_GUI
     public static final String[] POSITIONS_COL_NAMES = new String[]
    {
        COL_PK_ID,
        COL_SYMBOL,
        COL_LOT_TIME,
        COL_Q,
        COL_LINE,
        COL_OCE,
        COL_OCE_TIME,
        COL_LS,
        COL_PRICE_ADJ,
        COL_BASIS_ADJ,
        COL_PRICE,
        COL_BASIS,
        COL_HOW,
        COL_T_ID,
        COL_WASH,
        COL_KSFLAG,
        COL_CODES,
        COL_ACCOUNT,
        COL_YR,
        COL_SEC_TYPE,
        COL_MULTI,
        COL_UNDERLYING,
        COL_EXPIRY,
        COL_STRIKE,
        COL_O_TYPE,
        COL_NOTES,
        COL_FILECODE,
        COL_INPUTLINE,
        COL_GRP,
        COL_TIMESTAMP
     };
    //map of the column names in the database to the column names to display 
    public static Map<String, String> columnNameConstants = getColumnNameConstants();
    
    public static Map getColumnNameConstants(){
         Map<String, String> constants = new HashMap <String, String>();
        for (int col = 0; col < POSITIONS_DATABASE_COL_NAMES.length; col++){
            constants.put(POSITIONS_DATABASE_COL_NAMES[col],
                    POSITIONS_COL_NAMES[col]);
        }
        return constants;
    }
}
