package de.hszg.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by Andre on 23.04.2015.
 */
public class MultipleAPRequest implements Serializable{

    private List<AccessPoint> accessPointList;
    private String startDate = "";
    private String endDate = "";
    private int gceCount;

    public void setAccessPointList(List<AccessPoint> accessPointList) {
        this.accessPointList = accessPointList;
    }

    public List<AccessPoint> getAccessPointList(){
        return accessPointList;
    }

    public String getStartDate() {return startDate;}

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getGceCount() {
        return gceCount;
    }

    public void setGceCount(int gceCount) {
        this.gceCount = gceCount;
    }

    @Override
    public String toString() {
        return "{\"accessPointList\":" + accessPointList +",\"startDate\": "+startDate+",\"endDate\": "+endDate+",\"gceCount\":"+gceCount+"}";
    }
}
