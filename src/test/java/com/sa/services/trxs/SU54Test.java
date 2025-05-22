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

class SU54Test {


    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        SU54 actualSu54 = new SU54();
        assertTrue(actualSu54.getDataReturnList().isEmpty());
        assertNull(actualSu54.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU54 su54 = new SU54();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "id_rendicion", "test");
        assertThrows(TransactionException.class, () -> su54.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU54 su54 = new SU54();
        su54.mapData(new HashMap<>());
        assertNull(su54.getDataReturn());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU54 su54 = new SU54();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su54.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
    }
}

