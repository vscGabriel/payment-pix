package com.vscgabriel.api.impl;

import com.vscgabriel.api.PixResource;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.Pix;
import com.vscgabriel.service.DictService;
import com.vscgabriel.service.PixService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class PixResourceImpl implements PixResource {

    private final DictService dictService;

    private final PixService pixService;

    @Override
    public Response createWritableLine(Pix pix) {
        var key = dictService.searchKey(pix.key());

        if (Objects.nonNull(key)) {
            return Response.ok(pixService.generateWritableLine(key, pix.amount(), pix.mailerCity())).build()   ;
        }

        return null;


    }

    private Response generateWritableLine(Key key, Pix pix) {
        if (Objects.nonNull(key.key())){
            return Response.ok(pixService.generateWritableLine(key, pix.amount(), pix.mailerCity())).build();
        }
        return null;
    }

    @Override
    public Response getQrCode(String uuid) throws IOException {
        // TODO adicinar controle de excessoes.
        return Response.ok(pixService.generateQrCode(uuid)).build();
    }

    @Override
    public Response pixApprove(String uuid) {
        return Response.ok(pixService.transactionApprove(uuid).get()).build();
    }

    @Override
    public Response pixReprove(String uuid) {
        return Response.ok(pixService.transactionRepprove(uuid).get()).build();
    }

    @Override
    public Response findById(String uuid) {
        return Response.ok(pixService.findById(uuid).get()).build();
    }

}
