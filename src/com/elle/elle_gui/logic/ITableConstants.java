package com.elle.elle_gui.logic;

import com.elle.elle_gui.dao.AllocationDAO;
import com.elle.elle_gui.dao.PositionDAO;
import com.elle.elle_gui.dao.TradeDAO;
import java.util.HashMap;
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

    /**
     * TABLE COLUMN NAMES
     */
    
    // Trades table column names
    public static final String[] TRADES_COL_NAMES = new String[]
    {
        TradeDAO.COL_PK_ID,
        TradeDAO.COL_TRADE_TIME,
        TradeDAO.COL_OC,
        TradeDAO.COL_LS,
        TradeDAO.COL_SYMBOL,
        TradeDAO.COL_Q,
        TradeDAO.COL_PRICE,
        TradeDAO.COL_COMM,
        TradeDAO.COL_PROCEEDS,
        TradeDAO.COL_BASIS,
        TradeDAO.COL_ADJ_PROCEEDS,
        TradeDAO.COL_PROCESSED,
        TradeDAO.COL_LOT_TIME,
        TradeDAO.COL_REALIZED_PL,
        TradeDAO.COL_CODES,
        TradeDAO.COL_KSFLAG,
        TradeDAO.COL_NOTES,
        TradeDAO.COL_ACCOUNT,
        TradeDAO.COL_YR,
        TradeDAO.COL_FILE_CODE,
        TradeDAO.COL_INPUT_LINE,
        TradeDAO.COL_LOCKED,
        TradeDAO.COL_MFLAG,
        TradeDAO.COL_SEC_TYPE,
        TradeDAO.COL_MULTI,
        TradeDAO.COL_UNDERLYING,
        TradeDAO.COL_EXPIRY,
        TradeDAO.COL_STRIKE,
        TradeDAO.COL_O_TYPE,
        TradeDAO.COL_BKRGROUP,
        TradeDAO.COL_STRATEGY,
        TradeDAO.COL_XCHANGE,
        TradeDAO.COL_ORDER,
        TradeDAO.COL_FILLS,
        TradeDAO.COL_TOTAL_Q,
        TradeDAO.COL_T_GRP,
        TradeDAO.COL_MATCHING,
        TradeDAO.COL_METHOD,
        TradeDAO.COL_TIMESTAMP
    };
    
    // Positions table column names
    public static final String[] POSITIONS_COL_NAMES = new String[]
    {
        PositionDAO.COL_PK_ID,
        PositionDAO.COL_SYMBOL,
        PositionDAO.COL_LOT_TIME,
        PositionDAO.COL_Q,
        PositionDAO.COL_LINE,
        PositionDAO.COL_OCE,
        PositionDAO.COL_OCE_TIME,
        PositionDAO.COL_LS,
        PositionDAO.COL_PRICE_ADJ,
        PositionDAO.COL_BASIS_ADJ,
        PositionDAO.COL_PRICE,
        PositionDAO.COL_BASIS,
        PositionDAO.COL_HOW,
        PositionDAO.COL_T_ID,
        PositionDAO.COL_WASH,
        PositionDAO.COL_KSFLAG,
        PositionDAO.COL_CODES,
        PositionDAO.COL_ACCOUNT,
        PositionDAO.COL_YR,
        PositionDAO.COL_SEC_TYPE,
        PositionDAO.COL_MULTI,
        PositionDAO.COL_UNDERLYING,
        PositionDAO.COL_EXPIRY,
        PositionDAO.COL_STRIKE,
        PositionDAO.COL_O_TYPE,
        PositionDAO.COL_NOTES,
        PositionDAO.COL_FILECODE,
        PositionDAO.COL_INPUTLINE,
        PositionDAO.COL_GRP,
        PositionDAO.COL_TIMESTAMP
    };
    
    // Allocations table column names
    public static final String[] ALLOCATIONS_COL_NAMES = new String[]
    {
        AllocationDAO.COL_PK_ID,
        AllocationDAO.COL_SYMBOL,
        AllocationDAO.COL_TRADE_TIME,
        AllocationDAO.COL_TRADE_Q,
        AllocationDAO.COL_TRADE_PRICE,
        AllocationDAO.COL_METHOD,
        AllocationDAO.COL_MATH_Q,
        AllocationDAO.COL_MATH_PROCEEDS,
        AllocationDAO.COL_LOT_TIME,
        AllocationDAO.COL_LINE,
        AllocationDAO.COL_PRICE_ADJ,
        AllocationDAO.COL_MATCH_BASIS,
        AllocationDAO.COL_REALIZED_PL,
        AllocationDAO.COL_TERM,
        AllocationDAO.COL_ACCOUNT
    };
    
    /**
     * COLUMN WIDTH PERCENT FOR EACH TABLE AND VIEW
     */
    // POSITIONS DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_POSITIONS_DEFAULT_VIEW
            = new HashMap<String, Integer>() {
                {
                    put(PositionDAO.COL_PK_ID, 50);
                    put(PositionDAO.COL_SYMBOL, 155);
                    put(PositionDAO.COL_LOT_TIME, 160);
                    put(PositionDAO.COL_Q, 80);
                    put(PositionDAO.COL_LINE, 50);
                    put(PositionDAO.COL_OCE, 40);
                    put(PositionDAO.COL_OCE_TIME, 160);
                    put(PositionDAO.COL_LS, 25);
                    put(PositionDAO.COL_PRICE_ADJ, 90);
                    put(PositionDAO.COL_BASIS_ADJ, 75);
                    put(PositionDAO.COL_PRICE, 45);
                    put(PositionDAO.COL_BASIS, 45);
                    put(PositionDAO.COL_HOW, 40);
                    put(PositionDAO.COL_T_ID, 40);
                    put(PositionDAO.COL_WASH, 20);
                    put(PositionDAO.COL_KSFLAG, 55);
                    put(PositionDAO.COL_CODES, 55);
                    put(PositionDAO.COL_ACCOUNT, 65);
                    put(PositionDAO.COL_YR, 65);
                    put(PositionDAO.COL_SEC_TYPE, 58);
                    put(PositionDAO.COL_MULTI, 40);
                    put(PositionDAO.COL_UNDERLYING, 85);
                    put(PositionDAO.COL_EXPIRY, 88);
                    put(PositionDAO.COL_STRIKE, 75);
                    put(PositionDAO.COL_O_TYPE, 55);
                    put(PositionDAO.COL_NOTES, 55);
                    put(PositionDAO.COL_FILECODE, 60);
                    put(PositionDAO.COL_INPUTLINE, 62);
                    put(PositionDAO.COL_GRP, 20);
                    put(PositionDAO.COL_TIMESTAMP, 20);
                }
            };
    
    // POSITIONS ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_POSITIONS_ALL_FIELDS
            = new HashMap<String, Integer>() {
                {
                    put(PositionDAO.COL_PK_ID, 50);
                    put(PositionDAO.COL_SYMBOL, 155);
                    put(PositionDAO.COL_LOT_TIME, 160);
                    put(PositionDAO.COL_Q, 80);
                    put(PositionDAO.COL_LINE, 50);
                    put(PositionDAO.COL_OCE, 40);
                    put(PositionDAO.COL_OCE_TIME, 160);
                    put(PositionDAO.COL_LS, 25);
                    put(PositionDAO.COL_PRICE_ADJ, 90);
                    put(PositionDAO.COL_BASIS_ADJ, 75);
                    put(PositionDAO.COL_PRICE, 45);
                    put(PositionDAO.COL_BASIS, 45);
                    put(PositionDAO.COL_HOW, 40);
                    put(PositionDAO.COL_T_ID, 40);
                    put(PositionDAO.COL_WASH, 20);
                    put(PositionDAO.COL_KSFLAG, 55);
                    put(PositionDAO.COL_CODES, 55);
                    put(PositionDAO.COL_ACCOUNT, 65);
                    put(PositionDAO.COL_YR, 65);
                    put(PositionDAO.COL_SEC_TYPE, 58);
                    put(PositionDAO.COL_MULTI, 40);
                    put(PositionDAO.COL_UNDERLYING, 85);
                    put(PositionDAO.COL_EXPIRY, 88);
                    put(PositionDAO.COL_STRIKE, 75);
                    put(PositionDAO.COL_O_TYPE, 55);
                    put(PositionDAO.COL_NOTES, 55);
                    put(PositionDAO.COL_FILECODE, 60);
                    put(PositionDAO.COL_INPUTLINE, 62);
                    put(PositionDAO.COL_GRP, 20);
                    put(PositionDAO.COL_TIMESTAMP, 20);
                }
            };

    // ALLOCATIONS DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_ALLOCATIONS_DEFAULT_VIEW 
            = new HashMap<String, Integer>() {
        {
            put(AllocationDAO.COL_PK_ID, 30);
            put(AllocationDAO.COL_SYMBOL, 55);
            put(AllocationDAO.COL_TRADE_TIME, 160);
            put(AllocationDAO.COL_TRADE_Q, 60);
            put(AllocationDAO.COL_TRADE_PRICE, 85);
            put(AllocationDAO.COL_METHOD, 80);
            put(AllocationDAO.COL_MATH_Q, 80);
            put(AllocationDAO.COL_MATH_PROCEEDS, 90);
            put(AllocationDAO.COL_LOT_TIME, 160);
            put(AllocationDAO.COL_LINE, 50);
            put(AllocationDAO.COL_PRICE_ADJ, 30);
            put(AllocationDAO.COL_MATCH_BASIS, 75);
            put(AllocationDAO.COL_REALIZED_PL, 90);
            put(AllocationDAO.COL_TERM, 55);
            put(AllocationDAO.COL_ACCOUNT, 65);
        }
    };
    
    // ALLOCATIONS ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_ALLOCATIONS_ALL_FIELDS 
            = new HashMap<String, Integer>() {
        {
            put(AllocationDAO.COL_PK_ID, 30);
            put(AllocationDAO.COL_SYMBOL, 55);
            put(AllocationDAO.COL_TRADE_TIME, 160);
            put(AllocationDAO.COL_TRADE_Q, 60);
            put(AllocationDAO.COL_TRADE_PRICE, 85);
            put(AllocationDAO.COL_METHOD, 80);
            put(AllocationDAO.COL_MATH_Q, 80);
            put(AllocationDAO.COL_MATH_PROCEEDS, 90);
            put(AllocationDAO.COL_LOT_TIME, 160);
            put(AllocationDAO.COL_LINE, 50);
            put(AllocationDAO.COL_PRICE_ADJ, 30);
            put(AllocationDAO.COL_MATCH_BASIS, 75);
            put(AllocationDAO.COL_REALIZED_PL, 90);
            put(AllocationDAO.COL_TERM, 55);
            put(AllocationDAO.COL_ACCOUNT, 65);
        }
    };
    
    // TRADES DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_TRADES_DEFAULT_VIEW  
            = new HashMap<String, Integer>() {
        {
            put(TradeDAO.COL_PK_ID, 40);
            put(TradeDAO.COL_TRADE_TIME, 160);
            put(TradeDAO.COL_OC, 30);
            put(TradeDAO.COL_LS, 0);
            put(TradeDAO.COL_SYMBOL, 155);
            put(TradeDAO.COL_Q, 85);
            put(TradeDAO.COL_PRICE, 80);
            put(TradeDAO.COL_COMM, 0);
            put(TradeDAO.COL_PROCEEDS, 90);
            put(TradeDAO.COL_BASIS, 75);
            put(TradeDAO.COL_ADJ_PROCEEDS, 0);
            put(TradeDAO.COL_PROCESSED, 0);
            put(TradeDAO.COL_LOT_TIME, 160);
            put(TradeDAO.COL_REALIZED_PL, 90);
            put(TradeDAO.COL_CODES, 55);
            put(TradeDAO.COL_KSFLAG, 0);
            put(TradeDAO.COL_NOTES, 0);
            put(TradeDAO.COL_ACCOUNT, 65);
            put(TradeDAO.COL_YR, 0);
            put(TradeDAO.COL_FILE_CODE, 0);
            put(TradeDAO.COL_INPUT_LINE, 0);
            put(TradeDAO.COL_LOCKED, 0);
            put(TradeDAO.COL_MFLAG, 0);
            put(TradeDAO.COL_SEC_TYPE, 0);
            put(TradeDAO.COL_MULTI, 0);
            put(TradeDAO.COL_UNDERLYING, 0);
            put(TradeDAO.COL_EXPIRY, 0);
            put(TradeDAO.COL_STRIKE, 0);
            put(TradeDAO.COL_O_TYPE, 0);
            put(TradeDAO.COL_BKRGROUP, 0);
            put(TradeDAO.COL_STRATEGY, 0);
            put(TradeDAO.COL_XCHANGE, 0);
            put(TradeDAO.COL_ORDER, 50);
            put(TradeDAO.COL_FILLS, 30);
            put(TradeDAO.COL_TOTAL_Q, 0);
            put(TradeDAO.COL_T_GRP, 0);
            put(TradeDAO.COL_MATCHING, 0);
            put(TradeDAO.COL_METHOD, 0);
            put(TradeDAO.COL_TIMESTAMP, 0);
        }
    };

    // TRADES ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_TRADES_ALL_FIELDS  
            = new HashMap<String, Integer>() {
        {
            put(TradeDAO.COL_PK_ID, 40);
            put(TradeDAO.COL_TRADE_TIME, 160);
            put(TradeDAO.COL_OC, 30);
            put(TradeDAO.COL_LS, 80);
            put(TradeDAO.COL_SYMBOL, 155);
            put(TradeDAO.COL_Q, 85);
            put(TradeDAO.COL_PRICE, 80);
            put(TradeDAO.COL_COMM, 80);
            put(TradeDAO.COL_PROCEEDS, 90);
            put(TradeDAO.COL_BASIS, 75);
            put(TradeDAO.COL_ADJ_PROCEEDS, 90);
            put(TradeDAO.COL_PROCESSED, 80);
            put(TradeDAO.COL_LOT_TIME, 160);
            put(TradeDAO.COL_REALIZED_PL, 90);
            put(TradeDAO.COL_CODES, 55);
            put(TradeDAO.COL_KSFLAG, 55);
            put(TradeDAO.COL_NOTES, 55);
            put(TradeDAO.COL_ACCOUNT, 65);
            put(TradeDAO.COL_YR, 65);
            put(TradeDAO.COL_FILE_CODE, 60);
            put(TradeDAO.COL_INPUT_LINE, 62);
            put(TradeDAO.COL_LOCKED, 60);
            put(TradeDAO.COL_MFLAG, 60);
            put(TradeDAO.COL_SEC_TYPE, 58);
            put(TradeDAO.COL_MULTI, 40);
            put(TradeDAO.COL_UNDERLYING, 85);
            put(TradeDAO.COL_EXPIRY, 88);
            put(TradeDAO.COL_STRIKE, 75);
            put(TradeDAO.COL_O_TYPE, 55);
            put(TradeDAO.COL_BKRGROUP, 55);
            put(TradeDAO.COL_STRATEGY, 60);
            put(TradeDAO.COL_XCHANGE, 85);
            put(TradeDAO.COL_ORDER, 50);
            put(TradeDAO.COL_FILLS, 30);
            put(TradeDAO.COL_TOTAL_Q, 50);
            put(TradeDAO.COL_T_GRP, 60);
            put(TradeDAO.COL_MATCHING, 50);
            put(TradeDAO.COL_METHOD, 50);
            put(TradeDAO.COL_TIMESTAMP, 50);
        }
    };
}
