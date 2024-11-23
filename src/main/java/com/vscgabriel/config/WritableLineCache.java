package com.vscgabriel.config;

import com.vscgabriel.model.WritableLine;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

@ApplicationScoped
public class WritableLineCache {
    private final ValueCommands<String, WritableLine> commands;

    public WritableLineCache(RedisDataSource ds) {
        this.commands = ds.value(WritableLine.class);
    }

    public WritableLine get(String key) {
        return commands.get(key);
    }

    public void set(String key, WritableLine writableLine) {
        commands.set(key, writableLine, new SetArgs().ex(Duration.ofMinutes(30)));
    }

    public WritableLine getOrSetIfAbsent(String key, Supplier<WritableLine> cachedObj) {
        var cached = get(key);
        if (Objects.nonNull(cached)) {
            return cached;
        } else {
            var result = cachedObj.get();
            set(key, result);
            return result;
        }

    }

}
