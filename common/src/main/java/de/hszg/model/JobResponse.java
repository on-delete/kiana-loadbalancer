package de.hszg.model;

/**
 * Created by Andre on 15.06.2015.
 */
public class JobResponse {

    private String ipAddress = "";
    private String job = "";
    private String startTime = "";
    private String endTime = "";
    private String gcename = "";

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGcename() {
        return gcename;
    }

    public void setGcename(String gcename) {
        this.gcename = gcename;
    }

    @Override
    public String toString() {
        return "{\"ipAddress\":\""+ ipAddress + "\", \"gcename\":\""+ gcename + "\", \"job\":\""+job+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
    }
}
