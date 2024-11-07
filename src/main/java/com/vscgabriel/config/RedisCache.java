package com.vscgabriel.config;

import com.vscgabriel.model.Key;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

@ApplicationScoped
public class RedisCache {
    private final ValueCommands<String, Key> commands;

    public RedisCache(RedisDataSource dataSource) {
        this.commands = dataSource.value(Key.class);
    }

    public Key get(String key) {
        return commands.get(key);
    }

    public void set(String key, Key cached){
        commands.set(key,cached, new SetArgs().ex(Duration.ofMinutes(30)));
    }

    public void evict(String key) {
        commands.getdel(key);
    }

    public Key getOrSetIfAbsent(String key, Supplier<Key> cachedObj) {
        var cached = get(key);

        if(Objects.nonNull(cached)) return cached;
        else {
            var result = cachedObj.get();
            set(key,result);
            return result;
        }
    }
}
