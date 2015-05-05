package de.hszg.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Andre on 23.04.2015.
 */
public class MultipleAPRequest {

    private List<AccessPoint> accessPointList;
    private Date startDate;
    private Date endDate;

    public List<AccessPoint> getAccessPointList(){
        return accessPointList;
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
