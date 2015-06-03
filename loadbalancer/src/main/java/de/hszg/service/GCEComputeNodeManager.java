package de.hszg.service;

import de.hszg.gce.GoogleComputeEngineFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Created by Daniel on 02.06.2015.
 */
@Path("/gce")
public class GCEComputeNodeManager {
    @GET
    @Path("/all")
    @Produces("application/json")
    public Response list() {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();

        return Response.ok(googleComputeEngineFactory.listGCEs()).build();
    }

    @GET
    @Path("/{name}/start")
    public Response startGCE(@PathParam("name") String name) {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();
        if (googleComputeEngineFactory.startGCE(name)) {
            return Response.ok().build();
        } else {
            return Response.status(418).build();
        }
    }

    @GET
    @Path("/{name}/stop")
    public Response stopGCE(@PathParam("name") String name) {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();
        if (googleComputeEngineFactory.stopGCE(name)) {
            return Response.ok().build();
        } else {
            return Response.status(418).build();
        }
    }

    @GET
    @Path("/{name}/reset")
    public Response resetGCE(@PathParam("name") String name) {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();
        if (googleComputeEngineFactory.resetGCE(name)) {
            return Response.ok().build();
        } else {
            return Response.status(418).build();
        }
    }

    @DELETE
    @Path("/{name}")
    public Response deleteGCE(@PathParam("name") String name) {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();
        if (googleComputeEngineFactory.deleteGCE(name)) {
            return Response.ok().build();
        } else {
            return Response.status(418).build();
        }
    }

    @PUT
    @Path("/{name}")
    public Response createGCE(@PathParam("name") String name) {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();
        if (googleComputeEngineFactory.createGCE(name)) {
            return Response.ok().build();
        } else {
            return Response.status(418).build();
        }
    }
}
