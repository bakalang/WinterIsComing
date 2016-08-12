package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by SKYE on 2016/7/15.
 */
public class SecurityTradeDAO {

    public static int[] insertBatch(List<SecurityTrade> stList) {

        Object[][] params = new Object[stList.size()][];

        for (int i = 0; i < stList.size(); i++) {
            SecurityTrade st = stList.get(i);
            params[i] = new Object[] { st.getSecurityId(), st.getStockId(), st.getTradeDate(), st.getBuy(), st.getSell(), st.getClose(), st.getSubtraction() };
        }
        String SQL = "INSERT INTO SECURITY_TRADE (SECURITY_ID, STOCK_ID, TRADE_DATE, BUY, SELL, CLOSE, SUBTRACTION) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        return Yank.execute(SQL, params);
        return Yank.executeBatch(SQL, params);
    }

    public static List<SecurityTrade> selectSecurityTradeByStockIdAndSecurityId(String stockId, String securityId) {

        Object[] params = new Object[] {stockId, securityId};
        String SQL = "SELECT * FROM SECURITY_TRADE where STOCK_ID=? AND SECURITY_ID=? ";
        return Yank.queryBeanList(SQL, SecurityTrade.class, params);
    }

    public static int updateClose(Date date, String stockId, BigDecimal close) {

        Object[] params = new Object[] {close, stockId, date};
        String SQL = "UPDATE SECURITY_TRADE SET CLOSE = ? WHERE STOCK_ID = ? AND TRADE_DATE=?";
        return Yank.execute(SQL, params);
    }

}
