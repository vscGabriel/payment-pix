package com.vscgabriel.model;

import java.time.LocalDateTime;

public record Key(KeyType keyType, String key, String ispb, PersonType personType,
                  String cpfCnpj,
                  String name,
                  LocalDateTime createDateTime) {
}
