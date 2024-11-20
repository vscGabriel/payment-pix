package com.vscgabriel.repository;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionalRepository {
    void add(final WritableLine writableLine, final BigDecimal amount, final Key key);

    Optional<Transaction> updateStatusTransaction(final String uuid, final StatusPix statusPix);

    Optional<Document> findOne(final String uuid);
}
