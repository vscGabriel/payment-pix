package com.vscgabriel.service;

import com.vscgabriel.model.Key;
import io.smallrye.mutiny.Uni;

public interface DictService {
    Uni<Key> searchKey(String key);
}
