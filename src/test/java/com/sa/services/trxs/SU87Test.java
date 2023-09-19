package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU87Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU87 actualSu87 = new SU87();
        actualSu87.hardcodear(new HashMap<>());
        assertTrue(actualSu87.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU87 su87 = new SU87();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "descrip", "foo");
        assertThrows(TransactionException.class, () -> su87.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void testMapData() {
        SU87 su87 = new SU87();
        su87.mapData(new HashMap<>());
        assertTrue(su87.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData")
    void testMapData2() {
        SU87 su87 = new SU87();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "descrip", "foo");
        su87.mapData(parametersExecute);
        Object expectedDataReturn = parametersExecute.get("descrip");
        assertSame(expectedDataReturn, su87.getDataReturn());
    }
}

