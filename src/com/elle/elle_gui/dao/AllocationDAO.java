
package com.elle.elle_gui.dao;

import com.elle.elle_gui.database.DBConnection;
import com.elle.elle_gui.entities.Allocation;
import com.elle.elle_gui.logic.LoggingAspect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * AllocationDAO
 * @author Carlos Igreja
 * @since  May 7, 2016
 */
public class AllocationDAO {
    
    // database table information
    public static final String DB_TABLE_NAME = "allocations";
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

    public static ArrayList<Allocation> get(String accountName) {
        ArrayList<Allocation> allocations = new ArrayList<>();
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
                Allocation allocation = new Allocation();
                allocation.setId(rs.getString(COL_PK_ID));
                allocation.setTradeTime(rs.getString(COL_SYMBOL));
                allocation.setTradeTime(rs.getString(COL_TRADE_TIME));
                allocation.setTradeQ(rs.getString(COL_TRADE_Q));
                allocation.setTradePrice(rs.getString(COL_TRADE_PRICE));
                allocation.setMethod(rs.getString(COL_METHOD));
                allocation.setMathQ(rs.getString(COL_MATH_Q));
                allocation.setMathProceeds(rs.getString(COL_MATH_PROCEEDS));
                allocation.setLotTime(rs.getString(COL_LOT_TIME));
                allocation.setLine(rs.getString(COL_LINE));
                allocation.setPriceAdj(rs.getString(COL_PRICE_ADJ));
                allocation.setMatchBasis(rs.getString(COL_MATCH_BASIS));
                allocation.setRealizedPL(rs.getString(COL_REALIZED_PL));
                allocation.setTerm(rs.getString(COL_TERM));
                allocation.setAccount(rs.getString(COL_ACCOUNT));
                allocations.add(allocation);
            }
            
            LoggingAspect.afterReturn("Loaded table " + DB_TABLE_NAME + " for " + accountName);
        } 
        catch (SQLException e) {
            LoggingAspect.afterThrown(e);
        }
        
        return allocations;
    }
}
