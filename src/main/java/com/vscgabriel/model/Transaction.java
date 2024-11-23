package com.vscgabriel.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MongoEntity(collection = "transacao_pix")
@Data
@EqualsAndHashCode
public class Transaction extends PanacheMongoEntity {
    @Schema(description = "Identificador único da transação")
    private String id;
    @Schema(description = "Valor da transação")

    private BigDecimal amont;

    @Schema(description = "Tipo de Chave PIX")

    private String keyType;

    @Schema(description = "Chave PIX")
    private String key;

    @Schema(description = "Linha Digitável PIX")
    private String writableLine;

    @Schema(description = "Status PIX")
    private StatusPix status;

    private LocalDateTime date;
}
