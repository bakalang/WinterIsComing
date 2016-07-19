package org.knowm.xdropwizard.jobs;


import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.knowm.xdropwizard.business.SecurityTrade;
import org.knowm.xdropwizard.business.SecurityTradeDAO;
import org.knowm.xdropwizard.business.StockDailyTransaction;
import org.knowm.xdropwizard.business.StockDailyTransactionDAO;
import org.knowm.xdropwizard.constance.commonConstance;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SKYE on 2016/7/15.
 */
public class JobUtils implements commonConstance {

    private Logger log = null;
    private static final DateTime now = new org.joda.time.DateTime();

    public static String getStockMonthTransaction(String url, String stockId) {

        List<String> tmpList = StockDailyTransactionDAO.getStockDailyTransactionList(stockId);
        List<StockDailyTransaction> sdtList = new ArrayList<StockDailyTransaction>();
        StockDailyTransaction sdt = null;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements targetTable = doc.select(".board_trad");
            Iterator<Element> rr = targetTable.select("tr").iterator();
            while (rr.hasNext()) {
                Iterator<Element> dd = rr.next().select("td").iterator();
                StringBuilder sb = new StringBuilder();
                while (dd.hasNext()) {
                    Element dd_e = dd.next();
                    sb.append(Jsoup.parse(dd_e.html()).text()).append(" ");
                }

                //check if regular data
                String[] dataArray = sb.toString().trim().split(" ");
                if(dataArray[0].length() == 9){
                    String commonEraDate = toCommonEra(dataArray[0]);

                    if(!tmpList.contains(commonEraDate)){
                        sdt = new StockDailyTransaction();;
                        sdt.setTransactionDate(YYYYMMDD.parseDateTime(commonEraDate).toDate());
                        sdt.setStockId(stockId);
                        sdt.setTradeVolume(getBigDecimal(dataArray[1], 0));
                        sdt.setTurnover(getBigDecimal(dataArray[2], 0));
                        sdt.setOpen(getBigDecimal(dataArray[3],2));
                        sdt.setDayHigh(getBigDecimal(dataArray[4], 2));
                        sdt.setDayLow(getBigDecimal(dataArray[5], 2));
                        sdt.setClose(getBigDecimal(dataArray[6], 2));
                        sdt.setGrossSpread(getBigDecimal(dataArray[7], 2));
                        sdt.setTransactionCount(getBigDecimal(dataArray[8], 0));
                        sdtList.add(sdt);
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sdtList.size() >0) {
//            System.out.println(">>>" + stockId + " , " + sdtList.size());
//            for (StockDailyTransaction tmp : sdtList) {
//                System.out.println(tmp.toString());
//            }
            StockDailyTransactionDAO.insertBatch(sdtList);
        }
        return null;
    }


    public static String getSecurityCompany(String security, Logger log) {

        String url = SECURITY_MAP.get(security);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select(".t01");

            // get date
            String tmpDate = newsHeadlines.select(".t11").text();
            if( !MMDD.print(now).endsWith(tmpDate.substring(tmpDate.length()-5, tmpDate.length()))){
                System.out.println(tmpDate.substring(tmpDate.length() - 5, tmpDate.length())+", data updated !");
                return null;
            }


            Iterator<Element> rr = newsHeadlines.select("tr").iterator();
            while (rr.hasNext()) {
                Iterator<Element> dd = rr.next().select("td").iterator();
                int i = 0;
                SecurityTrade s = new SecurityTrade();
                while (dd.hasNext()) {
                    String stockId =null;

                    Element dd_e = dd.next();
                    if (dd_e.className().startsWith("t4")) {
                        stockId = getC(Jsoup.parse(dd_e.html()).text());
                        s = new SecurityTrade();
                        s.setTradeDate(now.toDate());
                        s.setSecurityId(security);
                        s.setStockId(stockId);
                        i = 0;

                    }
                    if (dd_e.className().startsWith("t3")) {
                        BigDecimal tmp = getBigDecimal(Jsoup.parse(dd_e.html()).text().toString(), 0);
                        switch (i) {
                            case 0:
                                s.setBuy(tmp);
                            case 1:
                                s.setSell(tmp);
                            case 2:
                                s.setSubtraction((s.getSell().compareTo(s.getBuy()) ==1) ? tmp.negate() : tmp);
                            default:
                        }
                        i++;

                        if (i == 3) {
                            System.out.println("insert =>" + s.toString());
                            SecurityTradeDAO.insertSecurityTrade(s);
                        }
                    }
                    //check stock transaction
                    if(stockId != null)
                        getStockMonthTransaction(getTransationURI(now.getYear(), now.getMonthOfYear(), stockId), stockId);

                    //http://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_2882.tw&d=20160714&json=1
                    //http://www.twse.com.tw/ch/trading/exchange/STOCK_DAY/genpage/Report201607/201607_F3_1_8_2882.php?STK_NO=2882&myear=2016&mmon=07
                    //http://www.twse.com.tw/ch/trading/exchange/STOCK_DAY/genpage/Report201607/201607_F3_1_8_2882.php?STK_NO=2882&myear=2016&mmon=07
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BigDecimal getBigDecimal(String tmp, int scale) {
        tmp = tmp.replace(String.valueOf((char) 160), " ");
        tmp = tmp.replace("%", "");
        tmp = tmp.replace(",", "");
        tmp = tmp.replace("+", "");
        tmp = tmp.replace("X", "");
        BigDecimal b = null;
        try {
            b = BigDecimal.valueOf(Double.valueOf(tmp));
            b.setScale(scale);
        }catch (NumberFormatException nfe){
//            ThreadLocalLogger.getInstance()
        }
        return b;
    }

    private static String getC(String text) {
        String rtn = null;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.toString(text.charAt(i)).matches("[a-zA-Z0-9]"))
                return text.substring(0, i);
        }
        return rtn;
    }

    private static String getTransationURI(int year, int month, String stockId) {
        String formatStr = "%02d";
        String monthString = String.format(formatStr, month);

        StringBuilder sb = new StringBuilder();
        sb.append("http://www.twse.com.tw/ch/trading/exchange/STOCK_DAY/genpage/Report");
        sb.append(year).append(monthString).append("/").append(year).append(monthString);
        sb.append("_F3_1_8_").append(stockId);
        sb.append(".php?STK_NO=").append(stockId);
        sb.append("&myear=").append(year).append("&mmon=").append(monthString);
        return sb.toString();
    }


    private static String toCommonEra(String s) {
        String tmpYear = s.substring(0, 3).trim();
        String cYear = String.valueOf(Integer.valueOf(tmpYear)+1911);
        return cYear+s.substring(3, s.length());
    }


    public static void main(String[] args) {
//        System.out.println(getC("3576新日光"));

        getStockMonthTransaction(getTransationURI(2016, 07, "2882"), "2882");

//        String formatStr = "%02d";
//        System.out.println(String.format(formatStr, 12));

//        getSecurityCompany("http://jsjustweb.jihsun.com.tw/z/zg/zgb/zgb0_1040_1.djhtm");


    }

}
