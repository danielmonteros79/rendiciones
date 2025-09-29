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

class SU58Test {

    @Test
    @DisplayName("Testeando constructor de SU58")
    void testConstructor() throws Exception {
        SU58 actualSu58 = new SU58();
        actualSu58.hardcodear(new HashMap<>());
        actualSu58.mapData(new HashMap<>());
        assertTrue(actualSu58.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU58 su58 = new SU58();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su58.executeTrx(client, parametersExecute));
    }
}

