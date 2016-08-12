package org.knowm.xdropwizard.business;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SKYE on 2016/7/15.
 */
public class SecurityTrade {
    private String securityId;
    private String stockId;
    private Date tradeDate;
    private BigDecimal buy;
    private BigDecimal sell;
    private BigDecimal close;
    private BigDecimal subtraction;


    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getSubtraction() {
        return subtraction;
    }

    public void setSubtraction(BigDecimal subtraction) {
        this.subtraction = subtraction;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(securityId).append("|");
        sb.append(stockId).append("|");
        sb.append(tradeDate).append("|");
        sb.append(buy).append("|");
        sb.append(sell).append("|");
        sb.append(close).append("|");
        sb.append(subtraction);
        return sb.toString();
    }
}
