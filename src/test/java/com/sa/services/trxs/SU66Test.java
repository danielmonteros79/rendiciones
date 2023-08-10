package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU66Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU66 actualSu66 = new SU66();
        actualSu66.hardcodear(new HashMap<>());
        actualSu66.mapData(new HashMap<>());
        assertTrue(actualSu66.getDataReturnList().isEmpty());
    }

    @Test
    void testExecuteTrx() throws TransactionException {
        SU66 su66 = new SU66();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su66.executeTrx(client, new HashMap<>()));
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU66 su66 = new SU66();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        assertThrows(TransactionException.class, () -> su66.executeTrx(client, parametersExecute));
    }
}

