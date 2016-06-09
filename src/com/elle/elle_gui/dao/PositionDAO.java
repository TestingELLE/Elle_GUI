
package com.elle.elle_gui.dao;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Position;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * PositionDAO
 * @author Carlos Igreja
 * @since  May 7, 2016
 */
public class PositionDAO {

    // database table information
    public static final String DB_TABLE_NAME = "positions";
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

    public static ArrayList<Position> get(String accountName) {
        
        ArrayList<Position> positions = new ArrayList<>();
        ResultSet rs = null;
        String sql = "";
        
        if (accountName == "Combined") {
            sql = "SELECT * FROM " + DB_TABLE_NAME
                    + " ORDER BY symbol ASC";
        } else {
            sql = "SELECT * FROM " + DB_TABLE_NAME
                    + " WHERE Account = '" + accountName
                    + "' ORDER BY symbol ASC";
        }
        
        try {

            DBConnection.close();
            DBConnection.open();
            rs = DBConnection.getStatement().executeQuery(sql);
            while(rs.next()){
                Position position = new Position();
                position.setPosId(rs.getString(COL_PK_ID));
                position.setSymbol(rs.getString(COL_SYMBOL));
                position.setLotTime(rs.getString(COL_LOT_TIME));
                position.setQ(rs.getString(COL_Q));
                position.setLine(rs.getString(COL_LINE));
                position.setOce(rs.getString(COL_OCE));
                position.setOceTime(rs.getString(COL_OCE_TIME));
                position.setLs(rs.getString(COL_LS));
                position.setPriceAdj(rs.getString(COL_PRICE_ADJ));
                position.setBasisAdj(rs.getString(COL_BASIS_ADJ));
                position.setPrice(rs.getString(COL_PRICE));
                position.setBasis(rs.getString(COL_BASIS));
                position.setHow(rs.getString(COL_HOW));
                position.settId(rs.getString(COL_T_ID));
                position.setWash(rs.getString(COL_WASH));
                position.setKsflag(rs.getString(COL_KSFLAG));
                position.setCodes(rs.getString(COL_CODES));
                position.setAccount(rs.getString(COL_ACCOUNT));
                position.setYr(rs.getString(COL_YR));
                position.setSecType(rs.getString(COL_SEC_TYPE));
                position.setMulti(rs.getString(COL_MULTI));
                position.setUnderlying(rs.getString(COL_UNDERLYING));
                position.setExpiry(rs.getString(COL_EXPIRY));
                position.setStrike(rs.getString(COL_STRIKE));
                position.setoType(rs.getString(COL_O_TYPE));
                position.setNotes(rs.getString(COL_NOTES));
                position.setFileCode(rs.getString(COL_FILECODE));
                position.setInputLine(rs.getString(COL_INPUTLINE));
                position.setGrp(rs.getString(COL_GRP));
                position.setTimeStamp(rs.getString(COL_TIMESTAMP));
                positions.add(position);
            }
            
            LoggingAspect.afterReturn("Loaded table " + DB_TABLE_NAME + " for " + accountName);
        } 
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return positions;
    }
}
