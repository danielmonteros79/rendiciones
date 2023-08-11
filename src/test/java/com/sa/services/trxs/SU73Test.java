package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU73Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU73 actualSu73 = new SU73();
        actualSu73.hardcodear(new HashMap<>());
        assertTrue(actualSu73.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU73 su73 = new SU73();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        assertThrows(TransactionException.class, () -> su73.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU73 su73 = new SU73();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        su73.mapData(parametersExecute);
        assertTrue(su73.getDataReturnList().isEmpty());
    }
}

