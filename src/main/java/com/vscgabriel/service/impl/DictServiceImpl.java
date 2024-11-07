package com.vscgabriel.service.impl;

import com.vscgabriel.config.RedisCache;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.KeyType;
import com.vscgabriel.model.PersonType;
import com.vscgabriel.service.DictService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@ApplicationScoped
public class DictServiceImpl implements DictService {

    @ConfigProperty(name = "pix.ispb")
    private String ispb;
    @ConfigProperty(name = "pix.cnpj")
    private String cnpj;
    @ConfigProperty(name = "pix.name")
    private String name;

    @Inject
    RedisCache redisCache;

    @Override
    public Key getDetailsKey(String key) {
        var cachedKey =  getCacheKey(key);

        if (Objects.isNull(cachedKey)) {
            var fakeKey = searchKey(key);
            redisCache.set(key, fakeKey);
            return fakeKey;
        }

        return cachedKey;
    }

    private Key getCacheKey(String key) {
        var cacheKey = redisCache.get(key);
        Log.infof("Key found in cache %s", cacheKey);
        return cacheKey;
    }

    @Override
    public Key searchKey(String key) {
        return new Key(KeyType.EMAIL, key, ispb, PersonType.JURIDICAL, cnpj, name, LocalDateTime.now());
    }
}
