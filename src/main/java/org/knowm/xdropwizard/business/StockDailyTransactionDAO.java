package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

import java.util.List;

/**
 * Created by SKYE on 2016/7/17.
 */
public class StockDailyTransactionDAO {

    public static List<String> getStockDailyTransactionList(String stockId) {

        Object[] params = new Object[] {stockId};
        String sql = "SELECT TRANSACTION_DATE FROM STOCK_DAILY_TRANSACTION WHERE STOCK_ID = ? ";
        return Yank.queryBeanList(sql, String.class, params);
    }

    public static int[] insertBatch(List<StockDailyTransaction> sdtList) {

        Object[][] params = new Object[sdtList.size()][];

        for (int i = 0; i < sdtList.size(); i++) {
            StockDailyTransaction sdt = sdtList.get(i);
            params[i] = new Object[] { sdt.getTransactionDate(), sdt.getStockId(), sdt.getTradeVolume(), sdt.getTurnover(),
            sdt.getOpen(), sdt.getClose(), sdt.getDayHigh(), sdt.getDayLow(), sdt.getGrossSpread(), sdt.getTransactionCount()
            };
        }

        String SQL = "INSERT INTO STOCK_DAILY_TRANSACTION (TRANSACTION_DATE, STOCK_ID, TRADE_VOLUME, TURNOVER, OPEN, CLOSE, DAY_HIGH, DAY_LOW, GROSS_SPREAD, TRANSACTION_COUNT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return Yank.executeBatch(SQL, params);
    }
}
