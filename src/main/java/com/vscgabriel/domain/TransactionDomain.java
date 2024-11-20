package com.vscgabriel.domain;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import com.vscgabriel.repository.TransactionPixMongoClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDomain {

    TransactionPixMongoClientRepository repository;

    @Transactional
    public void addTransaction(final WritableLine writableLine, final BigDecimal amount, final Key key){
        repository.add(writableLine, amount, key);
    }

    public Optional<Transaction> transactionApprove(final String uuid) {
        try {
        return repository.updateStatusTransaction(uuid, StatusPix.APPROVED);
        } finally {
            transactionInProcess(uuid);
        }
    }
    public Optional<Transaction> transactionReprove(final String uuid) {
        return repository.updateStatusTransaction(uuid, StatusPix.REPROVED);
    }
    public Optional<Transaction> transactionInProcess(final String uuid) {
        return repository.updateStatusTransaction(uuid, StatusPix.IN_PROGRESS);
    }
    public Optional<Transaction> findById(final String uuid) {
        Optional<Document> optionalDocument = repository.findOne(uuid);

        return optionalDocument.map(TransactionConverterApply::apply);
    }

}
