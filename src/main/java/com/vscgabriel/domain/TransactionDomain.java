package com.vscgabriel.domain;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import com.vscgabriel.repository.TransactionPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionDomain {

    @Inject
    TransactionPanacheRepository mongoClientRepository;

    public void addTransaction(final WritableLine writableLine, final BigDecimal amount, final Key key){
        mongoClientRepository.add(writableLine, amount, key);
    }

    public Optional<Transaction> transactionApprove(final String uuid) {
        try {
        return mongoClientRepository.updateStatusTransaction(uuid, StatusPix.APPROVED);
        } finally {
            transactionInProcess(uuid);
        }
    }
    public Optional<Transaction> transactionReprove(final String uuid) {
        return mongoClientRepository.updateStatusTransaction(uuid, StatusPix.REPROVED);
    }
    public Optional<Transaction> transactionInProcess(final String uuid) {
        return mongoClientRepository.updateStatusTransaction(uuid, StatusPix.IN_PROGRESS);
    }
    public Optional<Transaction> findById(final String uuid) {
        return  mongoClientRepository.findOne(uuid);
    }

    public List<Transaction> searchTransaction(final Date initDate, final Date endDate) {
        return mongoClientRepository.searchTransaction(initDate,endDate);
    }

}
