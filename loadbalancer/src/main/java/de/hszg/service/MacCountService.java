package de.hszg.service;

import de.hszg.model.MultipleAPRequest;
import de.hszg.model.OneAPRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Andre on 22.04.2015.
 *
 * This class is for the rest services to receive a request to count all mac addresses for one or multiple AccessPoints and forward it to the "start" compute node.
 */
@Path("/MacCountService")
public class MacCountService {

    /**
     * This function is to receive the request to count all mac addresses for one AccessPoint and forward it to the "start" compute node.
     * @param oneAPRequest DTO for the request. Is marshalled automatically.
     * @return
     */
    @POST
    @Path("/getMacCountOneAP")
    @Consumes("application/json")
    public Response getMacCountOneAP(@Context HttpServletRequest request, OneAPRequest oneAPRequest){
        System.out.println(request.getRequestURI());

        /*try {
            return Response.seeOther(new URI("http://www.google.de")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }*/

        return Response.ok().entity("<html><body>Hallo</body></html>").build();
    }

    /**
     * This function is to receive the request to count all mac addresses for multiple AccessPoint and forward it to the "start" compute node.
     * @param multipleAPRequest DTO for the request. Is marshalled automatically.
     * @return
     */
    @POST
    @Path("/getMacCountMultipleAP")
    @Consumes("application/json")
    public Response getMacCountMultipleAP(MultipleAPRequest multipleAPRequest){
        return null;
    }
}
