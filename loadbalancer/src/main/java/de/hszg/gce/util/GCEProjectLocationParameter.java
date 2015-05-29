package de.hszg.gce.util;

public class GCEProjectLocationParameter {
    private final String project;
    private final String zone;
    private final String accessToken;

    public GCEProjectLocationParameter(String project, String zone, String accessToken) {
        this.project = project;
        this.zone = zone;
        this.accessToken = accessToken;
    }

    public String getProject() {
        return project;
    }

    public String getZone() {
        return zone;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
