package com.vscgabriel.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.vscgabriel.domain.TransactionConverterApply;
import com.vscgabriel.model.Key;
import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import com.vscgabriel.model.WritableLine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class TransactionPixMongoClientRepository implements TransactionalRepository{
    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @Inject
    MongoClient mongoClient;
    @Override
    public void add(WritableLine writableLine, BigDecimal amount, Key key) {
        var document =  new Document();
        document.append(TransactionConverterApply.ID,writableLine.uuid())
                .append(TransactionConverterApply.AMOUNT, amount)
                .append(TransactionConverterApply.KEY_TYPE, key.keyType())
                .append(TransactionConverterApply.KEY, key.key())
                .append(TransactionConverterApply.STATUS, StatusPix.CREATED)
                .append(TransactionConverterApply.WRITABLE_LINE, writableLine.line())
                .append(TransactionConverterApply.DATE, LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));

        getCollection().insertOne(document);
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("pix").getCollection("transacao_pix");
    }

    @Override
    public Optional<Transaction> updateStatusTransaction(String uuid, StatusPix statusPix) {
        Optional<Document> optionalDocument = findOne(uuid);
        if (optionalDocument.isPresent()) {
           var document = optionalDocument.get();

            var opts = new FindOneAndReplaceOptions().upsert(false).returnDocument(ReturnDocument.AFTER);

            document.merge(TransactionConverterApply.STATUS, statusPix, (a,b) -> b);

            var replace = getCollection().findOneAndReplace(Filters.eq(TransactionConverterApply.ID, uuid),
                    document, opts);

            assert replace != null;
            return Optional.of(TransactionConverterApply.apply(replace));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Document> findOne(String uuid) {
        var filter = Filters.eq(TransactionConverterApply.ID, uuid);
        FindIterable<Document> documents = getCollection().find(filter);
        return StreamSupport.stream(documents.spliterator(), false).findFirst();
    }
}
