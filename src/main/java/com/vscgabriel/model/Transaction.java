package com.vscgabriel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class Transaction {
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
