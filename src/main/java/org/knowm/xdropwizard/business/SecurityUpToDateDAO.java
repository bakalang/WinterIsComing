package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

import java.util.Date;
import java.util.List;

/**
 * Created by Sky on 2016/8/2.
 */
public class SecurityUpToDateDAO {

    public static Date getSecurityCurrentDate(String securityId) {

        Object[] params = new Object[] {securityId};
        String sql = "SELECT SECURITY_CURRENT_DATE FROM SECURITY_UP_TO_DATE WHERE SECURITY_ID = ? ORDER BY SECURITY_CURRENT_DATE DESC LIMIT 1 ";
        return Yank.queryBean(sql, Date.class, params);
    }

    public static int insertSecurityCurrentDate(String securityId, Date date) {

        Object[] params = new Object[] {securityId, date};
        String SQL = "INSERT INTO SECURITY_UP_TO_DATE (SECURITY_ID, SECURITY_CURRENT_DATE) VALUES (?, ?)";
        return Yank.execute(SQL, params);
    }
}
