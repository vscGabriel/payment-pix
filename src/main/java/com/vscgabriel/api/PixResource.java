package com.vscgabriel.api;

import com.vscgabriel.model.Pix;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Path("/v1/pix")
public interface PixResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/line")
    Response createWritableLine(final Pix pix);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/qrcode/{uuid}")
    Response getQrCode(@PathParam("uuid") String uuid) throws IOException;


    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}/approve")
    Response pixApprove(@PathParam("uuid") String uuid);

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}/reprove")
    Response pixReprove(String uuid);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}")
    Response findById(String uuid);
}
