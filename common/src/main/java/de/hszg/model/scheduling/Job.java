package de.hszg.model.scheduling;

import java.util.List;

/**
 * Created by Andre on 03.05.2015.
 *
 * Mocel class for the jobs with a list of all mac addresses that have to be computed.
 */
public class Job {

    private List<String> macsToCompute;

    public List<String> getMacsToCompute() {
        return macsToCompute;
    }
}