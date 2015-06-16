package de.hszg.service;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.DatasetList;
import de.hszg.bigquery.BigQueryAuthenticator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 15.06.2015.
 */
@Path("/customerProjects")
public class CustomerProjectsService {
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getCustomerProjects() {
        BigQueryAuthenticator bigQueryAuthenticator = new BigQueryAuthenticator();
        Bigquery bigquery = null;

        try {
            bigquery = bigQueryAuthenticator.getBigquery();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        DatasetList datasetList = null;
        try {
            Bigquery.Datasets.List datasetRequest = bigquery.datasets().list(BigQueryAuthenticator.PROJECT_ID);
            datasetRequest.setMaxResults(10000000L);
            datasetList = datasetRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> projects = new ArrayList<>();

        datasetList.getDatasets().stream().forEach((i) -> projects.add(i.getId().split(":")[1]));

        return Response.ok(projects).build();
    }
}
