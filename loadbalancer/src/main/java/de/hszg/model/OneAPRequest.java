package de.hszg.model;

import java.util.Date;

/**
 * Created by Andre on 23.04.2015.
 */
public class OneAPRequest {

    private AccessPoint accessPoint;
    private Date startDate;
    private Date endDate;

    public AccessPoint getAccessPoint() {
        return accessPoint;
    }

    public void setAccessPoint(AccessPoint accessPoint) {
        this.accessPoint = accessPoint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
