package com.vscgabriel.api.impl;

import com.vscgabriel.api.PixResource;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.Pix;
import com.vscgabriel.service.DictService;
import com.vscgabriel.service.PixService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import java.util.Objects;

@AllArgsConstructor
public class PixResourceImpl implements PixResource {

    private final DictService dictService;

    private final PixService pixService;

    @Override
    public Uni<Response> createWritableLine(Pix pix) {
        var resp = dictService.searchKey(pix.key())
                .onItem()
                .transform(key -> generateWritableLine(key,pix));

        return resp;
    }


    private Response generateWritableLine(Key key, Pix pix) {
        if (Objects.nonNull(key.key())){
            return Response.ok(pixService.generateWritableLine(key, pix.amount(), pix.mailerCity())).build();
        }
        return null;
    }

}
