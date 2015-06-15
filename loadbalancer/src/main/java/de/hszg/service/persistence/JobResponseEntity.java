package de.hszg.service.persistence;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andre on 15.06.2015.
 */
@Entity

@NamedQueries({
        @NamedQuery(name="JobResponseEntity.getAllJobResponse",
                query="SELECT j FROM JobResponseEntity j")
})

@Table(name = "JobResponse")
public class JobResponseEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ipaddress", nullable = false)
    private String ipaddress;

    @Column(name = "gcename", nullable = false)
    private String gcename;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "starttime", nullable = false)
    private String starttime;

    @Column(name = "endtime", nullable = false)
    private String endtime;

    public JobResponseEntity() {
        this.ipaddress = null;
        this.gcename = null;
        this.job = null;
        this.starttime = null;
        this.endtime = null;
    }

    public JobResponseEntity(String ipaddress, String gcename, String job, String starttime, String endtime) {
        this.ipaddress = ipaddress;
        this.gcename = gcename;
        this.job = job;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipaddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipaddress = ipAddress;
    }

    public String getgcename() {
        return gcename;
    }

    public void setgcename(String gcename) {
        this.gcename = gcename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEndTime() {
        return endtime;
    }

    public void setEndTime(String endTime) {
        this.endtime = endTime;
    }

    public String getStartTime() {
        return starttime;
    }

    public void setStartTime(String startTime) {
        this.starttime = startTime;
    }
}
