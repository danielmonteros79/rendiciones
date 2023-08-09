package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU56Test {

    @Test
    @DisplayName("Testeando Constructor")
    void testConstructor() {
        SU56 actualSu56 = new SU56();
        assertTrue(actualSu56.getDataReturnList().isEmpty());
        assertNull(actualSu56.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx2() throws TransactionException {
        SU56 su56 = new SU56();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "id_gasto", "foo");
        assertThrows(TransactionException.class, () -> su56.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {

        SU56 su56 = new SU56();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "id_gasto", "42");
        su56.mapData(parametersExecute);
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU56 su56 = new SU56();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su56.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
    }
}

