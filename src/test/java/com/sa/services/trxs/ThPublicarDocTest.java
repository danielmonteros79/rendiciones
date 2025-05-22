package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThPublicarDocTest {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        ThPublicarDoc actualThPublicarDoc = new ThPublicarDoc();
        actualThPublicarDoc.hardcodear(new HashMap<>());
        assertTrue(actualThPublicarDoc.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor2() {
        ThPublicarDoc actualThPublicarDoc = new ThPublicarDoc();
        assertTrue(actualThPublicarDoc.getDataReturnList().isEmpty());
        assertNull(actualThPublicarDoc.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        ThPublicarDoc thPublicarDoc = new ThPublicarDoc();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "IdThuban", "foo");
        assertThrows(TransactionException.class, () -> thPublicarDoc.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        ThPublicarDoc thPublicarDoc = new ThPublicarDoc();
        thPublicarDoc.mapData(new HashMap<>());
        assertNull(thPublicarDoc.getDataReturn());
    }
}

