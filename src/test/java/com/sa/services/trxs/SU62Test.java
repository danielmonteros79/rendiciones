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

class SU62Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU62 actualSu62 = new SU62();
        actualSu62.hardcodear(new HashMap<>());
        assertTrue(actualSu62.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void testExecuteTrx3() throws TransactionException {
        SU62 su62 = new SU62();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        assertThrows(TransactionException.class, () -> su62.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU62 su62 = new SU62();
        su62.mapData(new HashMap<>());
        assertNull(su62.getDataReturn());
    }
}

