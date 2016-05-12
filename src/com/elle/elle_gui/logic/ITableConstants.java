package com.elle.elle_gui.logic;

import com.elle.elle_gui.dao.AllocationDAO1;
import com.elle.elle_gui.dao.PositionDAO1;
import com.elle.elle_gui.dao.TradeDAO1;
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
        TradeDAO1.COL_PK_ID,
        TradeDAO1.COL_TRADE_TIME,
        TradeDAO1.COL_OC,
        TradeDAO1.COL_LS,
        TradeDAO1.COL_SYMBOL,
        TradeDAO1.COL_Q,
        TradeDAO1.COL_PRICE,
        TradeDAO1.COL_COMM,
        TradeDAO1.COL_PROCEEDS,
        TradeDAO1.COL_BASIS,
        TradeDAO1.COL_PRICE_ADJ,
        TradeDAO1.COL_PROCESSED,
        TradeDAO1.COL_LOT_TIME,
        TradeDAO1.COL_REALIZED_PL,
        TradeDAO1.COL_CODES,
        TradeDAO1.COL_KSFLAG,
        TradeDAO1.COL_NOTES,
        TradeDAO1.COL_ACCOUNT,
        TradeDAO1.COL_YR,
        TradeDAO1.COL_FILE_CODE,
        TradeDAO1.COL_INPUT_LINE,
        TradeDAO1.COL_LOCKED,
        TradeDAO1.COL_MFLAG,
        TradeDAO1.COL_SEC_TYPE,
        TradeDAO1.COL_MULTI,
        TradeDAO1.COL_UNDERLYING,
        TradeDAO1.COL_EXPIRY,
        TradeDAO1.COL_STRIKE,
        TradeDAO1.COL_O_TYPE,
        TradeDAO1.COL_L_NOTES,
        TradeDAO1.COL_STRATEGY,
        TradeDAO1.COL_XCHANGE,
        TradeDAO1.COL_ORDER,
        TradeDAO1.COL_FILL,
        TradeDAO1.COL_TOTAL_Q,
        TradeDAO1.COL_T_GRP,
        TradeDAO1.COL_MATCHING,
        TradeDAO1.COL_METHOD,
        TradeDAO1.COL_TIMESTAMP
    };
    
    // Positions table column names
    public static final String[] POSITIONS_COL_NAMES = new String[]
    {
        PositionDAO1.COL_PK_ID,
        PositionDAO1.COL_SYMBOL,
        PositionDAO1.COL_LOT_TIME,
        PositionDAO1.COL_Q,
        PositionDAO1.COL_LINE,
        PositionDAO1.COL_OCE,
        PositionDAO1.COL_OCE_TIME,
        PositionDAO1.COL_LS,
        PositionDAO1.COL_QORI,
        PositionDAO1.COL_PRICE_ADJ,
        PositionDAO1.COL_BASIS_ADJ,
        PositionDAO1.COL_PRICE,
        PositionDAO1.COL_BASIS,
        PositionDAO1.COL_HOW,
        PositionDAO1.COL_T_ID,
        PositionDAO1.COL_WASH,
        PositionDAO1.COL_KSFLAG,
        PositionDAO1.COL_CODES,
        PositionDAO1.COL_ACCOUNT,
        PositionDAO1.COL_YR,
        PositionDAO1.COL_L_CODES,
        PositionDAO1.COL_SEC_TYPE,
        PositionDAO1.COL_MULTI,
        PositionDAO1.COL_UNDERLYING,
        PositionDAO1.COL_EXPIRY,
        PositionDAO1.COL_STRIKE,
        PositionDAO1.COL_O_TYPE,
        PositionDAO1.COL_NOTES,
        PositionDAO1.COL_FILECODE,
        PositionDAO1.COL_INPUTLINE,
        PositionDAO1.COL_GRP,
        PositionDAO1.COL_TIMESTAMP
    };
    
    // Allocations table column names
    public static final String[] ALLOCATIONS_COL_NAMES = new String[]
    {
        AllocationDAO1.COL_PK_ID,
        AllocationDAO1.COL_SYMBOL,
        AllocationDAO1.COL_TRADE_TIME,
        AllocationDAO1.COL_TRADE_Q,
        AllocationDAO1.COL_TRADE_PRICE,
        AllocationDAO1.COL_METHOD,
        AllocationDAO1.COL_MATH_Q,
        AllocationDAO1.COL_MATH_PROCEEDS,
        AllocationDAO1.COL_LOT_TIME,
        AllocationDAO1.COL_LINE,
        AllocationDAO1.COL_PRICE_ADJ,
        AllocationDAO1.COL_MATCH_BASIS,
        AllocationDAO1.COL_REALIZED_PL,
        AllocationDAO1.COL_TERM,
        AllocationDAO1.COL_ACCOUNT
    };
    
    /**
     * COLUMN WIDTH PERCENT FOR EACH TABLE AND VIEW
     */
    // POSITIONS DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_POSITIONS_DEFAULT_VIEW
            = new HashMap<String, Integer>() {
                {
                    put(PositionDAO1.COL_PK_ID, 50);
                    put(PositionDAO1.COL_SYMBOL, 155);
                    put(PositionDAO1.COL_LOT_TIME, 160);
                    put(PositionDAO1.COL_Q, 80);
                    put(PositionDAO1.COL_LINE, 50);
                    put(PositionDAO1.COL_OCE, 40);
                    put(PositionDAO1.COL_OCE_TIME, 160);
                    put(PositionDAO1.COL_LS, 25);
                    put(PositionDAO1.COL_QORI, 70);
                    put(PositionDAO1.COL_PRICE_ADJ, 90);
                    put(PositionDAO1.COL_BASIS_ADJ, 75);
                    put(PositionDAO1.COL_PRICE, 45);
                    put(PositionDAO1.COL_BASIS, 45);
                    put(PositionDAO1.COL_HOW, 40);
                    put(PositionDAO1.COL_T_ID, 40);
                    put(PositionDAO1.COL_WASH, 20);
                    put(PositionDAO1.COL_KSFLAG, 55);
                    put(PositionDAO1.COL_CODES, 55);
                    put(PositionDAO1.COL_ACCOUNT, 65);
                    put(PositionDAO1.COL_YR, 65);
                    put(PositionDAO1.COL_L_CODES, 40);
                    put(PositionDAO1.COL_SEC_TYPE, 58);
                    put(PositionDAO1.COL_MULTI, 40);
                    put(PositionDAO1.COL_UNDERLYING, 85);
                    put(PositionDAO1.COL_EXPIRY, 88);
                    put(PositionDAO1.COL_STRIKE, 75);
                    put(PositionDAO1.COL_O_TYPE, 55);
                    put(PositionDAO1.COL_NOTES, 55);
                    put(PositionDAO1.COL_FILECODE, 60);
                    put(PositionDAO1.COL_INPUTLINE, 62);
                    put(PositionDAO1.COL_GRP, 20);
                    put(PositionDAO1.COL_TIMESTAMP, 20);
                }
            };
    
    // POSITIONS ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_POSITIONS_ALL_FIELDS
            = new HashMap<String, Integer>() {
                {
                    put(PositionDAO1.COL_PK_ID, 50);
                    put(PositionDAO1.COL_SYMBOL, 155);
                    put(PositionDAO1.COL_LOT_TIME, 160);
                    put(PositionDAO1.COL_Q, 80);
                    put(PositionDAO1.COL_LINE, 50);
                    put(PositionDAO1.COL_OCE, 40);
                    put(PositionDAO1.COL_OCE_TIME, 160);
                    put(PositionDAO1.COL_LS, 25);
                    put(PositionDAO1.COL_QORI, 70);
                    put(PositionDAO1.COL_PRICE_ADJ, 90);
                    put(PositionDAO1.COL_BASIS_ADJ, 75);
                    put(PositionDAO1.COL_PRICE, 45);
                    put(PositionDAO1.COL_BASIS, 45);
                    put(PositionDAO1.COL_HOW, 40);
                    put(PositionDAO1.COL_T_ID, 40);
                    put(PositionDAO1.COL_WASH, 20);
                    put(PositionDAO1.COL_KSFLAG, 55);
                    put(PositionDAO1.COL_CODES, 55);
                    put(PositionDAO1.COL_ACCOUNT, 65);
                    put(PositionDAO1.COL_YR, 65);
                    put(PositionDAO1.COL_L_CODES, 40);
                    put(PositionDAO1.COL_SEC_TYPE, 58);
                    put(PositionDAO1.COL_MULTI, 40);
                    put(PositionDAO1.COL_UNDERLYING, 85);
                    put(PositionDAO1.COL_EXPIRY, 88);
                    put(PositionDAO1.COL_STRIKE, 75);
                    put(PositionDAO1.COL_O_TYPE, 55);
                    put(PositionDAO1.COL_NOTES, 55);
                    put(PositionDAO1.COL_FILECODE, 60);
                    put(PositionDAO1.COL_INPUTLINE, 62);
                    put(PositionDAO1.COL_GRP, 20);
                    put(PositionDAO1.COL_TIMESTAMP, 20);
                }
            };

    // ALLOCATIONS DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_ALLOCATIONS_DEFAULT_VIEW 
            = new HashMap<String, Integer>() {
        {
            put(AllocationDAO1.COL_PK_ID, 30);
            put(AllocationDAO1.COL_SYMBOL, 55);
            put(AllocationDAO1.COL_TRADE_TIME, 160);
            put(AllocationDAO1.COL_TRADE_Q, 60);
            put(AllocationDAO1.COL_TRADE_PRICE, 85);
            put(AllocationDAO1.COL_METHOD, 80);
            put(AllocationDAO1.COL_MATH_Q, 80);
            put(AllocationDAO1.COL_MATH_PROCEEDS, 90);
            put(AllocationDAO1.COL_LOT_TIME, 160);
            put(AllocationDAO1.COL_LINE, 50);
            put(AllocationDAO1.COL_PRICE_ADJ, 30);
            put(AllocationDAO1.COL_MATCH_BASIS, 75);
            put(AllocationDAO1.COL_REALIZED_PL, 90);
            put(AllocationDAO1.COL_TERM, 55);
            put(AllocationDAO1.COL_ACCOUNT, 65);
        }
    };
    
    // ALLOCATIONS ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_ALLOCATIONS_ALL_FIELDS 
            = new HashMap<String, Integer>() {
        {
            put(AllocationDAO1.COL_PK_ID, 30);
            put(AllocationDAO1.COL_SYMBOL, 55);
            put(AllocationDAO1.COL_TRADE_TIME, 160);
            put(AllocationDAO1.COL_TRADE_Q, 60);
            put(AllocationDAO1.COL_TRADE_PRICE, 85);
            put(AllocationDAO1.COL_METHOD, 80);
            put(AllocationDAO1.COL_MATH_Q, 80);
            put(AllocationDAO1.COL_MATH_PROCEEDS, 90);
            put(AllocationDAO1.COL_LOT_TIME, 160);
            put(AllocationDAO1.COL_LINE, 50);
            put(AllocationDAO1.COL_PRICE_ADJ, 30);
            put(AllocationDAO1.COL_MATCH_BASIS, 75);
            put(AllocationDAO1.COL_REALIZED_PL, 90);
            put(AllocationDAO1.COL_TERM, 55);
            put(AllocationDAO1.COL_ACCOUNT, 65);
        }
    };
    
    // TRADES DEFAULT VIEW
    public static final Map<String, Integer> COL_WIDTH_PER_TRADES_DEFAULT_VIEW  
            = new HashMap<String, Integer>() {
        {
            put(TradeDAO1.COL_PK_ID, 40);
            put(TradeDAO1.COL_TRADE_TIME, 160);
            put(TradeDAO1.COL_OC, 30);
            put(TradeDAO1.COL_LS, 0);
            put(TradeDAO1.COL_SYMBOL, 155);
            put(TradeDAO1.COL_Q, 85);
            put(TradeDAO1.COL_PRICE, 80);
            put(TradeDAO1.COL_COMM, 0);
            put(TradeDAO1.COL_PROCEEDS, 90);
            put(TradeDAO1.COL_BASIS, 75);
            put(TradeDAO1.COL_PRICE_ADJ, 0);
            put(TradeDAO1.COL_PROCESSED, 0);
            put(TradeDAO1.COL_LOT_TIME, 160);
            put(TradeDAO1.COL_REALIZED_PL, 90);
            put(TradeDAO1.COL_CODES, 55);
            put(TradeDAO1.COL_KSFLAG, 0);
            put(TradeDAO1.COL_NOTES, 0);
            put(TradeDAO1.COL_ACCOUNT, 65);
            put(TradeDAO1.COL_YR, 0);
            put(TradeDAO1.COL_FILE_CODE, 0);
            put(TradeDAO1.COL_INPUT_LINE, 0);
            put(TradeDAO1.COL_LOCKED, 0);
            put(TradeDAO1.COL_MFLAG, 0);
            put(TradeDAO1.COL_SEC_TYPE, 0);
            put(TradeDAO1.COL_MULTI, 0);
            put(TradeDAO1.COL_UNDERLYING, 0);
            put(TradeDAO1.COL_EXPIRY, 0);
            put(TradeDAO1.COL_STRIKE, 0);
            put(TradeDAO1.COL_O_TYPE, 0);
            put(TradeDAO1.COL_L_NOTES, 0);
            put(TradeDAO1.COL_STRATEGY, 0);
            put(TradeDAO1.COL_XCHANGE, 0);
            put(TradeDAO1.COL_ORDER, 50);
            put(TradeDAO1.COL_FILL, 30);
            put(TradeDAO1.COL_TOTAL_Q, 0);
            put(TradeDAO1.COL_T_GRP, 0);
            put(TradeDAO1.COL_MATCHING, 0);
            put(TradeDAO1.COL_METHOD, 0);
            put(TradeDAO1.COL_TIMESTAMP, 0);
        }
    };

    // TRADES ALL FIELDS
    public static final Map<String, Integer> COL_WIDTH_PER_TRADES_ALL_FIELDS  
            = new HashMap<String, Integer>() {
        {
            put(TradeDAO1.COL_PK_ID, 40);
            put(TradeDAO1.COL_TRADE_TIME, 160);
            put(TradeDAO1.COL_OC, 30);
            put(TradeDAO1.COL_LS, 80);
            put(TradeDAO1.COL_SYMBOL, 155);
            put(TradeDAO1.COL_Q, 85);
            put(TradeDAO1.COL_PRICE, 80);
            put(TradeDAO1.COL_COMM, 80);
            put(TradeDAO1.COL_PROCEEDS, 90);
            put(TradeDAO1.COL_BASIS, 75);
            put(TradeDAO1.COL_PRICE_ADJ, 90);
            put(TradeDAO1.COL_PROCESSED, 80);
            put(TradeDAO1.COL_LOT_TIME, 160);
            put(TradeDAO1.COL_REALIZED_PL, 90);
            put(TradeDAO1.COL_CODES, 55);
            put(TradeDAO1.COL_KSFLAG, 55);
            put(TradeDAO1.COL_NOTES, 55);
            put(TradeDAO1.COL_ACCOUNT, 65);
            put(TradeDAO1.COL_YR, 65);
            put(TradeDAO1.COL_FILE_CODE, 60);
            put(TradeDAO1.COL_INPUT_LINE, 62);
            put(TradeDAO1.COL_LOCKED, 60);
            put(TradeDAO1.COL_MFLAG, 60);
            put(TradeDAO1.COL_SEC_TYPE, 58);
            put(TradeDAO1.COL_MULTI, 40);
            put(TradeDAO1.COL_UNDERLYING, 85);
            put(TradeDAO1.COL_EXPIRY, 88);
            put(TradeDAO1.COL_STRIKE, 75);
            put(TradeDAO1.COL_O_TYPE, 55);
            put(TradeDAO1.COL_L_NOTES, 55);
            put(TradeDAO1.COL_STRATEGY, 60);
            put(TradeDAO1.COL_XCHANGE, 85);
            put(TradeDAO1.COL_ORDER, 50);
            put(TradeDAO1.COL_FILL, 30);
            put(TradeDAO1.COL_TOTAL_Q, 50);
            put(TradeDAO1.COL_T_GRP, 60);
            put(TradeDAO1.COL_MATCHING, 50);
            put(TradeDAO1.COL_METHOD, 50);
            put(TradeDAO1.COL_TIMESTAMP, 50);
        }
    };
}
