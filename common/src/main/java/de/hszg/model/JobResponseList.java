package de.hszg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 15.06.2015.
 */
public class JobResponseList {

    List<JobResponse> responseList = new ArrayList<>();

    public List<JobResponse> getResponseList(){
        return this.responseList;
    }

    @Override
    public String toString() {
        return "{\"responseList\":"+responseList+"}";
    }
}
