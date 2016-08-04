package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

import java.util.Date;
import java.util.List;

/**
 * Created by Sky on 2016/8/2.
 */
public class SecurityUpToDateDAO {

    public static SecurityUpToDate getSecurityCurrentDate(String securityId) {

        Object[] params = new Object[] {securityId};
        String sql = "SELECT * FROM SECURITY_UP_TO_DATE WHERE SECURITY_ID = ? ";
        return Yank.queryBean(sql, SecurityUpToDate.class, params);
    }

    public static int insertSecurityCurrentDate(String securityId, String dateString) {

        Object[] params = new Object[] {securityId, dateString};
        String SQL = "INSERT INTO SECURITY_UP_TO_DATE (SECURITY_ID, SECURITY_CURRENT) VALUES (?, ?)";
        return Yank.execute(SQL, params);
    }

    public static int updateSecurityCurrentDate(String securityId, String dateString) {

        Object[] params = new Object[] {dateString, securityId};
        String SQL = "UPDATE SECURITY_CURRENT SET SECURITY_CURRENT_DATE = ? WHERE SECURITY_ID = ?";
        return Yank.execute(SQL, params);
    }
}
