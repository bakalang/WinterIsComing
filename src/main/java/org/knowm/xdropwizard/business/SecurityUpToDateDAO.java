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
        String sql = "SELECT SECURITY_CURRENT FROM SECURITY_UP_TO_DATE WHERE SECURITY_ID = ? ";
//        return Yank.queryBean(sql, Date.class, params);
        return Yank.queryColumn(sql, "SECURITY_CURRENT", Date.class, params).get(0);
    }

    public static int insertSecurityCurrentDate(String securityId, Date date) {

        Object[] params = new Object[] {securityId, date};
        String SQL = "INSERT INTO SECURITY_UP_TO_DATE (SECURITY_ID, SECURITY_CURRENT) VALUES (?, ?)";
        return Yank.execute(SQL, params);
    }

    public static int updateSecurityCurrentDate(String securityId, Date date) {

        Object[] params = new Object[] {date, securityId};
        String SQL = "UPDATE SECURITY_UP_TO_DATE SET SECURITY_CURRENT = ? WHERE SECURITY_ID = ?";
        return Yank.execute(SQL, params);
    }
}
