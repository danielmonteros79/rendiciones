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

class WM90Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        WM90 actualWm90 = new WM90();
        actualWm90.hardcodear(new HashMap<>());
        assertEquals("    ", WM90.CENTRO_COSTOS);
        assertTrue(actualWm90.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        WM90 wm90 = new WM90();
        SAMWebClient client = new SAMWebClient();

        HashMap<Object, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((Object) "idu", "foo");
        parametersExecute.put((Object) "etiqueta", "foo");
        assertThrows(TransactionException.class, () -> wm90.executeTrx(client, (Map) parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        WM90 wm90 = new WM90();
        wm90.mapData((Map) new HashMap<>());
        assertEquals("null;null", wm90.getDataReturn());
    }
}

