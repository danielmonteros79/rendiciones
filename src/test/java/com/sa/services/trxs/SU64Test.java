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

class SU64Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU64 actualSu64 = new SU64();
        actualSu64.hardcodear(new HashMap<>());
        actualSu64.mapData(new HashMap<>());
        assertTrue(actualSu64.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU64 su64 = new SU64();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        assertThrows(TransactionException.class, () -> su64.executeTrx(client, parametersExecute));
    }
}

