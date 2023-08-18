package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WM95Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        WM95 actualWm95 = new WM95();
        actualWm95.hardcodear(new HashMap<>());
        assertEquals("    ", WM95.CENTRO_COSTOS);
        assertTrue(actualWm95.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        WM95 wm95 = new WM95();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "idu", "foo");
        parametersExecute.put((String) "etiqueta", "foo");
        assertThrows(TransactionException.class, () -> wm95.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void testMapData() {
        WM95 wm95 = new WM95();
        wm95.mapData(new HashMap<>());
        assertEquals("null;null", wm95.getDataReturn());
    }
}

