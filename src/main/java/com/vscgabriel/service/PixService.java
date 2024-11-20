package com.vscgabriel.service;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface PixService {
    Optional<Transaction> transactionRepprove(String uuid);

    Optional<Transaction> findById(String uuid);

    WritableLine generateWritableLine(final Key key, BigDecimal amount, String mailerCity);

    BufferedInputStream generateQrCode(final String uuid) throws IOException;

    Optional<Transaction> transactionApprove(String uuid);
}
