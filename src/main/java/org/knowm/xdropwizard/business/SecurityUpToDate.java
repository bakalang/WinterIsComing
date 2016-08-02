package org.knowm.xdropwizard.business;

import java.util.Date;

/**
 * Created by Sky on 2016/8/2.
 */
public class SecurityUpToDate {

    private String securityId;
    private Date securityCurrentDate;

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public Date getSecurityCurrentDate() {
        return securityCurrentDate;
    }

    public void setSecurityCurrentDate(Date securityCurrentDate) {
        this.securityCurrentDate = securityCurrentDate;
    }
}
