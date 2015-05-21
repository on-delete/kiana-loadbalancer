package de.hszg.gce;

/**
 * Created by Daniel on 04.05.2015.
 */
public class GoogleComputeEngineClient {
    private static GoogleComputeEngineClient ourInstance = new GoogleComputeEngineClient();

    public static GoogleComputeEngineClient getInstance() {
        return ourInstance;
    }

    private GoogleComputeEngineClient() {
    }

    //TODO Apache HTTP-Client capsuling
}
