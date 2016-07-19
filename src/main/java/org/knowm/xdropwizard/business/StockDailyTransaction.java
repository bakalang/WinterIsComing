package org.knowm.xdropwizard.business;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SKYE on 2016/7/17.
 */
public class StockDailyTransaction {

    private Date transactionDate;
    private String stockId;
    private BigDecimal tradeVolume;
    private BigDecimal turnover;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal dayHigh;
    private BigDecimal dayLow;
    private BigDecimal grossSpread;
    private BigDecimal transactionCount;


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(BigDecimal tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(BigDecimal dayHigh) {
        this.dayHigh = dayHigh;
    }

    public BigDecimal getDayLow() {
        return dayLow;
    }

    public void setDayLow(BigDecimal dayLow) {
        this.dayLow = dayLow;
    }

    public BigDecimal getGrossSpread() {
        return grossSpread;
    }

    public void setGrossSpread(BigDecimal grossSpread) {
        this.grossSpread = grossSpread;
    }

    public BigDecimal getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(BigDecimal transactionCount) {
        this.transactionCount = transactionCount;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(transactionDate).append("|");
        sb.append(stockId).append("|");
        sb.append(tradeVolume).append("|");
        sb.append(turnover).append("|");
        sb.append(open).append("|");
        sb.append(close).append("|");
        sb.append(dayHigh).append("|");
        sb.append(dayLow).append("|");
        sb.append(grossSpread).append("|");
        sb.append(transactionCount).append("|");
        return  sb.toString();
    }


}
