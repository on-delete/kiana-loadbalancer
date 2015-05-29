package de.hszg.gce;

import de.hszg.gce.util.GCE;
import de.hszg.gce.util.GCEProjectLocationParameter;
import de.hszg.gce.util.NotAuthenticatedException;

import java.util.List;

/**
 * Created by Daniel on 04.05.2015.
 */
public class GoogleComputeEngineFactory {
    private static final String PROJECT_ID = "test-cand2release";
    private static final String ZONE = "us-central1-a";

    private String accessToken  = "";

    public GoogleComputeEngineFactory() {
        this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
    }

    public boolean startGCE(String name) {
        try {
            return GoogleComputeEngineClient.getInstance().startGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().startGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    public boolean stopGCE(String name) {
        try {
            return GoogleComputeEngineClient.getInstance().stopGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().stopGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    public boolean resetGCE(String name) {
        try {
            return GoogleComputeEngineClient.getInstance().resetGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().resetGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    public boolean createGCE(String name) {
        try {
            return GoogleComputeEngineClient.getInstance().createGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().createGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    public boolean deleteGCE(String name) {
        try {
            return GoogleComputeEngineClient.getInstance().deleteGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().deleteGCE(name, new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    public List<GCE> listGCEs(){
        try {
            return GoogleComputeEngineClient.getInstance().getGCEs(new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
        } catch (NotAuthenticatedException e) {
            this.accessToken = GoogleComputeEngineClient.getInstance().getAuthenticationToken();
            try {
                return GoogleComputeEngineClient.getInstance().getGCEs(new GCEProjectLocationParameter(PROJECT_ID, ZONE, accessToken));
            } catch (NotAuthenticatedException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }
}
