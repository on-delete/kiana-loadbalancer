package de.hszg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Andre on 23.04.2015.
 */
public class MultipleAPRequest implements Serializable{

    private List<AccessPoint> accessPointList;
    private Date startDate;
    private Date endDate;
    private int gceCount;

    public void setAccessPointList(List<AccessPoint> accessPointList) {
        this.accessPointList = accessPointList;
    }

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

    public int getGceCount() {
        return gceCount;
    }

    public void setGceCount(int gceCount) {
        this.gceCount = gceCount;
    }
}
