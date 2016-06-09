
package com.elle.elle_gui.dao;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Trade;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public static final String COL_LOT_TIME = "lot_Time";
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
    public static final String COL_STRATEGY = "strategy";
    public static final String COL_XCHANGE = "Xchange";
    public static final String COL_ORDER = "order";
    public static final String COL_FILLS = "fills";
    public static final String COL_TOTAL_Q = "TotalQ";
    public static final String COL_T_GRP = "t_grp";
    public static final String COL_MATCHING = "matching";
    public static final String COL_METHOD = "method";
    public static final String COL_TIMESTAMP = "timeStamp";
    
    /**
     * 
     * @param accountName
     * @return 
     */
    public ArrayList<Trade> get(String accountName) {
        
        ArrayList<Trade> trades = new ArrayList<>();
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
                Trade trade = new Trade();
                trade.setId(rs.getString(COL_PK_ID));
                trade.setTradeTime(rs.getString(COL_TRADE_TIME));
                trade.setOc(rs.getString(COL_OC));
                trade.setLs(rs.getString(COL_LS));
                trade.setSymbol(rs.getString(COL_SYMBOL));
                trade.setQ(rs.getString(COL_Q));
                trade.setPrice(rs.getString(COL_PRICE));
                trade.setComm(rs.getString(COL_COMM));
                trade.setProceeds(rs.getString(COL_PROCEEDS));
                trade.setBasis(rs.getString(COL_BASIS));
                trade.setAdjProceeds(rs.getString(COL_ADJ_PROCEEDS));
                trade.setProcessed(rs.getString(COL_PROCESSED));
                trade.setLotTime(rs.getString(COL_LOT_TIME));
                trade.setRealizedPl(rs.getString(COL_REALIZED_PL));
                trade.setCodes(rs.getString(COL_CODES));
                trade.setKsflag(rs.getString(COL_KSFLAG));
                trade.setNotes(rs.getString(COL_NOTES));
                trade.setAccount(rs.getString(COL_ACCOUNT));
                trade.setYr(rs.getString(COL_YR));
                trade.setFileCode(rs.getString(COL_FILE_CODE));
                trade.setInputLine(rs.getString(COL_INPUT_LINE));
                trade.setLocked(rs.getString(COL_LOCKED));
                trade.setMflag(rs.getString(COL_MFLAG));
                trade.setSecType(rs.getString(COL_SEC_TYPE));
                trade.setMulti(rs.getString(COL_MULTI));
                trade.setUnderlying(rs.getString(COL_UNDERLYING));
                trade.setExpiry(rs.getString(COL_EXPIRY));
                trade.setStrike(rs.getString(COL_STRIKE));
                trade.setoType(rs.getString(COL_O_TYPE));
                trade.setBkrGroup(rs.getString(COL_BKRGROUP));
                trade.setStategy(rs.getString(COL_STRATEGY));
                trade.setxChange(rs.getString(COL_XCHANGE));
                trade.setOrder(rs.getString(COL_ORDER));
                trade.setFills(rs.getString(COL_FILLS));
                trade.setTotalQ(rs.getString(COL_TOTAL_Q));
                trade.settGrp(rs.getString(COL_T_GRP));
                trade.setMatching(rs.getString(COL_MATCHING));
                trade.setMethod(rs.getString(COL_METHOD));
                trade.setTimeStamp(rs.getString(COL_TIMESTAMP));
                trades.add(trade);
                
            }
            
            LoggingAspect.afterReturn("Loaded table " + DB_TABLE_NAME + " for " + accountName);
        } 
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return trades;
    }
}
