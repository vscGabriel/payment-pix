package com.vscgabriel.domain;

import com.vscgabriel.model.StatusPix;
import com.vscgabriel.model.Transaction;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

public class TransactionConverterApply {
   public static final String ID = "_id";
   public static final String AMOUNT = "amount";
   public static final String STATUS = "status";
   public static final String WRITABLE_LINE = "writableTline";
   public static final String KEY = "key";
   public static final String KEY_TYPE = "keyType";
   public static final String DATE = "date";
   private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

   public static Transaction apply(Document document) {
      var transaction = new Transaction();

      transaction.setId(document.getString(ID));
      transaction.setAmont(BigDecimal.valueOf(document.get(AMOUNT,Decimal128.class).doubleValue()));
      transaction.setStatus(StatusPix.valueOf(document.getString(STATUS)));
      transaction.setWritableLine(document.getString(WRITABLE_LINE));
      transaction.setKey(document.getString(KEY));
      transaction.setKeyType(document.getString(KEY_TYPE));
      transaction.setDate(document.get(DATE, Date.class).toInstant().atZone(ZoneId.of(AMERICA_SAO_PAULO)).toLocalDateTime());

      return transaction;
   }
}
