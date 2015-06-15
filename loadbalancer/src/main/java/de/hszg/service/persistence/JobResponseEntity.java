package de.hszg.service.persistence;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andre on 15.06.2015.
 */
@Entity
@Table(name = "JobResponse")
public class JobResponseEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;

    @Column(name = "GCEName", nullable = false)
    private String GCEName;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "startTime", nullable = false)
    private String startTime;

    @Column(name = "endTime", nullable = false)
    private String endTime;

    public JobResponseEntity() {
        this.ipAddress = null;
        this.GCEName = null;
        this.job = null;
        this.startTime = null;
        this.endTime = null;
    }

    public JobResponseEntity(String ipAddress, String GCEName, String job, String startTime, String endTime) {
        this.ipAddress = ipAddress;
        this.GCEName = GCEName;
        this.job = job;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getGCEName() {
        return GCEName;
    }

    public void setGCEName(String GCEName) {
        this.GCEName = GCEName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
