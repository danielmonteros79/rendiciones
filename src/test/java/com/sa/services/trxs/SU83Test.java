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

class SU83Test {

    @Test
    @DisplayName("Testeando contructor")
    void testConstructor() throws Exception {
        SU83 actualSu83 = new SU83();
        actualSu83.hardcodear(new HashMap<>());
        actualSu83.mapData(new HashMap<>());
        assertTrue(actualSu83.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU83 su83 = new SU83();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su83.executeTrx(client, parametersExecute));
    }
}

