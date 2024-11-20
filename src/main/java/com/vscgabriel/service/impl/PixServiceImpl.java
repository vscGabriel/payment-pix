package com.vscgabriel.service.impl;

import com.vscgabriel.domain.TransactionDomain;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import com.vscgabriel.model.qrcode.DadosEnvio;

import com.vscgabriel.model.qrcode.QrCode;
import com.vscgabriel.service.PixService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class PixServiceImpl implements PixService {

    TransactionDomain transactionDomain;
    public static final String QRCODE_PATH = "C:\\Users\\a832444\\quarkus3_coffe&it\\payment-pix\\";

    @Override
    public BufferedInputStream generateQrCode(final String uuid) throws IOException {
    // TODO recuperar cache
        var imagePath = QRCODE_PATH + uuid + ".png";
//        try {
          return new BufferedInputStream(new FileInputStream(imagePath));
//        } finally {
//            Files.deleteIfExists(Paths.get(imagePath));
//        }
    }

    @Override
    public Optional<Transaction> transactionApprove(String uuid) {
        return transactionDomain.transactionApprove(uuid);
    }

    @Override
    public Optional<Transaction> transactionRepprove(String uuid) {
        return transactionDomain.transactionReprove(uuid);
    }

    @Override
    public Optional<Transaction> findById(String uuid) {
        return transactionDomain.findById(uuid);
    }

    @Override
    public WritableLine generateWritableLine(final Key key, BigDecimal amount, String mailerCity) {
        var qrcode = new QrCode(new DadosEnvio(key, amount,mailerCity, ""));
        var uuid = UUID.randomUUID().toString();
        var imagePath = QRCODE_PATH + uuid + ".png" ;
        qrcode.save(Path.of(imagePath));
        // TODO IMPLEMENTAR CACHE
       String qrCodeString = qrcode.toString();

        return new WritableLine(qrCodeString, uuid);
    }
}
