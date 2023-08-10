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

class SU67Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU67 actualSu67 = new SU67();
        actualSu67.hardcodear(new HashMap<>());
        actualSu67.mapData(new HashMap<>());
        assertTrue(actualSu67.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU67 su67 = new SU67();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        assertThrows(TransactionException.class, () -> su67.executeTrx(client, parametersExecute));
    }
}

