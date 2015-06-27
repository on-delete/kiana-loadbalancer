package de.hszg.service;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.DatasetList;
import com.google.api.services.bigquery.model.TableList;
import de.hszg.bigquery.BigQueryAuthenticator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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

        Predicate<DatasetList.Datasets> tableChecker = new Predicate<DatasetList.Datasets>() {
            Bigquery bigquery = null;

            @Override
            public boolean test(DatasetList.Datasets datasets) {
                try {
                    bigquery = bigQueryAuthenticator.getBigquery();
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }

                TableList tables = null;
                try {
                    Bigquery.Tables.List tableRequest = bigquery.tables().list(BigQueryAuthenticator.PROJECT_ID, datasets.getId().split(":")[1]);
                    tables = tableRequest.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (tables != null && tables.getTables() != null) {
                    for (TableList.Tables table : tables.getTables()) {
                        if (Objects.equals(table.getId().split("\\.")[1], "observationperstore")) {
                            return true;
                        }
                    }
                }

                return false;
            }
        };

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

        datasetList.getDatasets().stream().filter(tableChecker).forEach((i) -> projects.add(i.getId().split(":")[1]));

        return Response.ok(projects).build();
    }
}
