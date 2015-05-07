package de.hszg.model.heartbeat;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is for the heartbeat request that comes with the ip address of the GCE, the load and number of
 * jobs that are computed at the moment.
 */
public class Heartbeat {
    private String ipAddress;
    private double load;
    private int numberJobs;

    public Heartbeat(){}

    public Heartbeat(String ipAddress, double load, int numberJobs) {
        this.ipAddress = ipAddress;
        this.load = load;
        this.numberJobs = numberJobs;
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
}
