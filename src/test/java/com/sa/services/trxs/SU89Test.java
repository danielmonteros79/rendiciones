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

class SU89Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU89 actualSu89 = new SU89();
        actualSu89.hardcodear(new HashMap<>());
        actualSu89.mapData(new HashMap<>());
        assertTrue(actualSu89.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx3() throws TransactionException {
        SU89 su89 = new SU89();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su89.executeTrx(client, parametersExecute));
    }
}

