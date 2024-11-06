package com.vscgabriel.service.impl;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.KeyType;
import com.vscgabriel.model.PersonType;
import com.vscgabriel.service.DictService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;

@ApplicationScoped
public class DictServiceImpl implements DictService {

    @ConfigProperty(name = "pix.ispb")
    private String ispb;
    @ConfigProperty(name = "pix.cnpj")
    private String cnpj;
    @ConfigProperty(name = "pix.name")
    private String name;

    @Override
    public Uni<Key> searchKey(String key) {
        return Uni.createFrom()
                .item(new Key(KeyType.EMAIL, key, ispb, PersonType.JURIDICAL, cnpj, name, LocalDateTime.now()));
    }
}
