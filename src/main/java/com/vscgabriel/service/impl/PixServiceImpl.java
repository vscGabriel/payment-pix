package com.vscgabriel.service.impl;

import com.vscgabriel.config.WritableLineCache;
import com.vscgabriel.domain.TransactionDomain;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import com.vscgabriel.model.qrcode.DadosEnvio;

import com.vscgabriel.model.qrcode.QrCode;
import com.vscgabriel.service.PixService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PixServiceImpl implements PixService {

    @Inject
    TransactionDomain transactionDomain;

    @Inject
    WritableLineCache writableLineCache;
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
        var qrcode = new QrCode(new DadosEnvio(key, amount,mailerCity));
        var uuid = UUID.randomUUID().toString();
        var imagePath = QRCODE_PATH + uuid + ".png" ;
        qrcode.save(Path.of(imagePath));
        String qrCodeString = qrcode.toString();
        var writableLine = new WritableLine(qrCodeString, uuid);
        saveWritableLine(key, amount, writableLine);
        return writableLine;
    }

    private void saveWritableLine(Key key, BigDecimal amount, WritableLine writableLine) {
        transactionDomain.addTransaction(writableLine, amount, key);
        writableLineCache.set(writableLine.uuid(), writableLine);
    }

    public List<Transaction> searchTransaction(final Date initDate, final Date endDate) {
        return transactionDomain.searchTransaction(initDate,endDate);
    }

}
