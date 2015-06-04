package de.hszg.model;

import java.io.Serializable;

/**
 * Created by Andre on 23.04.2015.
 */
public class AccessPoint implements Serializable{
    private int apId;
    private String apName;

    public int getApId() {
        return apId;
    }

    public void setApId(int apId) {
        this.apId = apId;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    @Override
    public String toString() {
        return "{\"apId\":"+apId+",\"apName\":\""+apName+"\"}";
    }
}
