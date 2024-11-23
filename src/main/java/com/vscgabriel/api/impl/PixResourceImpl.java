package com.vscgabriel.api.impl;

import com.vscgabriel.api.PixResource;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.Pix;
import com.vscgabriel.service.DictService;
import com.vscgabriel.service.PixService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PixResourceImpl implements PixResource {

    @Inject
    DictService dictService;

    @Inject
    PixService pixService;

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Response createWritableLine(Pix pix) {
        var key = dictService.searchKey(pix.key());

        if (Objects.nonNull(key)) {
            return Response.ok(pixService.generateWritableLine(key, pix.amount(), pix.mailerCity())).build()   ;
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

    @Override
    public Response findTransactions(String initDate, String endDate) throws ParseException {
        return Response.ok(pixService.searchTransaction(DATE_FORMAT.parse(initDate), DATE_FORMAT.parse(endDate))).build();
    }

}
