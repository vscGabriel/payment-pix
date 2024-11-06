package com.vscgabriel.service;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.WritableLine;

import java.math.BigDecimal;

public interface PixService {
    WritableLine generateWritableLine(final Key key, BigDecimal amount, String mailerCity);
}
