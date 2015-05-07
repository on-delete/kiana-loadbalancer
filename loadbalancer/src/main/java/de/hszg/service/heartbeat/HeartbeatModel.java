package de.hszg.service.heartbeat;

import java.util.Date;

/**
 * Created by Andre on 07.05.2015.
 */
public class HeartbeatModel {

    private String ipAddress;
    private double load;
    private int numberJobs;
    private long systemTime;

    public HeartbeatModel(String ipAddress, double load, int numberJobs, long systemTime) {
        this.ipAddress = ipAddress;
        this.load = load;
        this.numberJobs = numberJobs;
        this.systemTime = systemTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public int getNumberJobs() {
        return numberJobs;
    }

    public void setNumberJobs(int numberJobs) {
        this.numberJobs = numberJobs;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    @Override
    public String toString() {
        return  "ipAddress='" + ipAddress + '\'' +
                ", load=" + load +
                ", numberJobs=" + numberJobs +
                ", systemTime=" + systemTime;
    }
}
