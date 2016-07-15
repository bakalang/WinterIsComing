package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

/**
 * Created by SKYE on 2016/7/15.
 */
public class SecurityTradeDAO {

    public static int insertSecurityTrade(SecurityTrade st) {

        Object[] params = new Object[] { st.getSecurityId(), st.getStockId(), st.getTradeDate(), st.getBuy(), st.getSell(), st.getSubtraction() };
        String SQL = "INSERT INTO SECURITY_TRADE (SECURITY_ID, STOCK_ID, TRADE_DATE, BUY, SELL, SUBTRACTION) VALUES (?, ?, ?, ?, ?, ?)";
        return Yank.execute(SQL, params);
    }
}
