package de.hszg.model;

import java.util.Date;

/**
 * Created by Andre on 23.04.2015.
 */
public class MacCountModel {

    private Date date;
    private int passthru;
    private int visited;
    private int engaged;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPassthru() {
        return passthru;
    }

    public void setPassthru(int passthru) {
        this.passthru = passthru;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getEngaged() {
        return engaged;
    }

    public void setEngaged(int engaged) {
        this.engaged = engaged;
    }
}
