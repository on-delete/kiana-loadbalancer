package de.hszg.service;

import de.hszg.model.MultipleAPRequest;
import de.hszg.model.OneAPRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
    public Response getMacCountOneAP(OneAPRequest oneAPRequest){
        return Response.ok().entity("<html><body>Hallo</body></html>").build();

        /*final Future<String> entityFuture = target().path("http://example.com/resource/")
                .request().async().get(new InvocationCallback<String>() {
                    @Override
                    public void completed(String response) {
                        System.out.println("Response entity '" + response + "' received.");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        System.out.println("Invocation failed.");
                        throwable.printStackTrace();
                    }
                });
        System.out.println(entityFuture.get());*/
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
