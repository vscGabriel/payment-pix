package com.vscgabriel.repository;

import com.vscgabriel.domain.TransactionConverterApply;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionPanacheRepository implements PanacheMongoRepository<Transaction> {

    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    public void add(WritableLine writableLine, BigDecimal amount, Key key) {
        var transaction  = new Transaction();
        transaction.setId(writableLine.uuid());
        transaction.setAmont( amount);
        transaction.setKeyType(key.keyType().toString());
        transaction.setKey(key.key());
        transaction.setStatus(StatusPix.CREATED);
        transaction.setWritableLine(writableLine.line());
        transaction.setDate(LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));;

        transaction.persist();
    }

    public Optional<Transaction> updateStatusTransaction(String uuid, StatusPix statusPix) {
        Optional<Transaction> optionalTransaction = findOne(uuid);

        if (optionalTransaction.isPresent()){
            var transaction = optionalTransaction.get();
            transaction.setStatus(statusPix);
            transaction.update();
            return Optional.of(transaction);
        }
        return Optional.empty();
    }

    public Optional<Transaction> findOne(String uuid) {
        return find(TransactionConverterApply.ID, uuid).stream().findFirst();
    }

    public List<Transaction> searchTransaction(final Date initDate, final Date endDate) {
        return  find("date >= ?1 and  date <= ?2 and status = ?3",initDate, endDate, StatusPix.CREATED)
                .stream()
                .collect(Collectors.toList());
    }
}
