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
    public Response createWritableLine(final Pix pix) {
        var key = dictService.searchKey(pix.key());
        var resp = generateWritableLine(key, pix);
        return resp;
    }


    private Response generateWritableLine(final Key key,final Pix pix) {
        if (Objects.nonNull(key.key())){
            return Response.ok(pixService.generateWritableLine(key, pix.amount(), pix.mailerCity())).build();
        }
        return null;
    }

}
