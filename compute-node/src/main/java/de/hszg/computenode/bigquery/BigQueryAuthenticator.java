package de.hszg.computenode.bigquery;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 16.06.2015.
 */
public class BigQueryAuthenticator {
    private List SCOPES = Arrays.asList(BigqueryScopes.BIGQUERY);
    private HttpTransport TRANSPORT = new NetHttpTransport();
    private JsonFactory JSON_FACTORY = new JacksonFactory();

    public final static String PROJECT_ID = "sandbox-kiana-analytics";

    public Bigquery getBigquery() throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId("1036887365938-4n4ufo8j2no8ruh4k3uv02svj8tksu2o@developer.gserviceaccount.com")
                .setServiceAccountScopes(SCOPES)
                .setServiceAccountPrivateKeyFromP12File(new File(getClass().getClassLoader().getResource("f360074528c9ac781f988802a248dcab3cba573e-privatekey.p12").getFile()))
                .build();

        Bigquery bigquery = new Bigquery.Builder(TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("BigQuery-Service-Accounts/0.1")
                .setHttpRequestInitializer(credential).build();

        return bigquery;
    }
}
