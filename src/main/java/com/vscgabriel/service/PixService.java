package com.vscgabriel.service;

import com.vscgabriel.model.Key;
import com.vscgabriel.model.WritableLine;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;

public interface PixService {
    WritableLine generateWritableLine(final Key key, BigDecimal amount, String mailerCity);

    BufferedInputStream generateQrCode(final String uuid) throws IOException;
}
