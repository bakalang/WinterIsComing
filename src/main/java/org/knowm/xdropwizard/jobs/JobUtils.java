package org.knowm.xdropwizard.jobs;


import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.knowm.xdropwizard.business.SecurityTrade;
import org.knowm.xdropwizard.business.SecurityTradeDAO;
import org.knowm.xdropwizard.constance.commonConstance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by SKYE on 2016/7/15.
 */
public class JobUtils implements commonConstance {

    private static final DateTime now = new org.joda.time.DateTime();

    public static String getStockMonthTransaction(String url) {
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
                    sb.append(Jsoup.parse(dd_e.html()).text()).append("|");
                }
                System.out.println(">> "+sb.toString());

                //check if regular data
                String[] dataArray = sb.toString().split("|");
                if(dataArray[0].length() == 9){
                    //check or insert

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSecurityCompany(String url) {
        String security = "1040";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select(".t01");

            // get date
            String tmpDate = newsHeadlines.select(".t11").text();
            System.out.println(tmpDate.substring(tmpDate.length() - 5, tmpDate.length()));
            MMdd.print(now).endsWith(tmpDate.substring(tmpDate.length()-5, tmpDate.length()));


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
                                s.setSubtraction(tmp);
                            default:
                        }
                        i++;

                        if (i == 3) {
                            SecurityTradeDAO.insertSecurityTrade(s);
                            System.out.println("insert =>" + s.toString());
                        }
                    }
                    //check stock transaction
                    //getStockMonthTransaction(getTransationURI(now.getYear(), now.getMonthOfYear(), stockId));

                    //http://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_2882.tw&d=20160714&json=1
                    //http://www.twse.com.tw/ch/trading/exchange/STOCK_DAY/genpage/Report201607/201607_F3_1_8_2882.php?STK_NO=2882&myear=2016&mmon=07
                    //http://www.twse.com.tw/ch/trading/exchange/STOCK_DAY/genpage/Report201607/201607_F3_1_8_2882.php?STK_NO=2882&myear=2016&mmon=07
                }

            }
            System.out.println("!!!");
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

        BigDecimal b = BigDecimal.valueOf(Double.valueOf(tmp));
        b = b.setScale(scale);
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
    public static void main(String[] args) {
//        System.out.println(getC("3576新日光"));

        //getStockMonthTransaction(getTransationURI(2016, 07, "2882"));

//        String formatStr = "%02d";
//        System.out.println(String.format(formatStr, 12));

        getSecurityCompany("http://jsjustweb.jihsun.com.tw/z/zg/zgb/zgb0_1040_1.djhtm");
    }
}
