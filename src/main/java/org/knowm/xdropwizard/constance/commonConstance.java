package org.knowm.xdropwizard.constance;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SKYE on 2016/7/15.
 */
public interface commonConstance {
    public static final DateTimeFormatter YYYYMMDD = DateTimeFormat.forPattern("yyyy/MM/dd");
    public static final DateTimeFormatter MMDD = DateTimeFormat.forPattern("MM/dd");

    public static final Map<String, String> SECURITY_MAP = new HashMap<String, String>(){
        {
            put("1040" ,"http://jsjustweb.jihsun.com.tw/z/zg/zgb/zgb0_1040_1.djhtm");
            put("9100" ,"http://jsjustweb.jihsun.com.tw/z/zg/zgb/zgb0_9100_1.djhtm");
            put("9800" ,"http://jsjustweb.jihsun.com.tw/z/zg/zgb/zgb0_9800_1.djhtm");

        }
    };
}
