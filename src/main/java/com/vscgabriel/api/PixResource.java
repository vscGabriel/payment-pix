package com.vscgabriel.api;

import com.vscgabriel.model.Pix;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.io.IOException;
import java.text.ParseException;

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
    @Path("{uuid}/qrcode")
    Response getQrCode(@PathParam("uuid") String uuid) throws IOException;


    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}/approve")
    Response pixApprove(@PathParam("uuid") String uuid);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}/reprove")
    Response pixReprove(String uuid);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{uuid}")
    Response findById(String uuid);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transactions")
    @Parameter(
            name = "initDate",
            in = ParameterIn.QUERY,
            description = "init date format yyyy-MM-dd",
            ref = "2024-11-20"
    )
    @Parameter(
            name = "endDate",
            in = ParameterIn.QUERY,
            description = "end date format yyyy-MM-dd",
            ref = "2024-11-20"
    )
    Response findTransactions(@QueryParam(value = "initDate") String initDate, @QueryParam(value = "endDate") String endDate) throws ParseException;
}
