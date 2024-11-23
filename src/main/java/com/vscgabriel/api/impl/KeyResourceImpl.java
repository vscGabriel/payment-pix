package com.vscgabriel.api.impl;

import com.vscgabriel.api.KeyResource;
import com.vscgabriel.service.DictService;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

public class KeyResourceImpl implements KeyResource {

    DictService dictService;
    @Override
    public Response getKey(String key) {
        return Response.ok(dictService.getDetailsKey(key)).build();
    }
}
