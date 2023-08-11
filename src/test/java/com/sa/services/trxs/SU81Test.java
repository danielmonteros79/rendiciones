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

class SU81Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        SU81 actualSu81 = new SU81();
        assertTrue(actualSu81.getDataReturnList().isEmpty());
        assertNull(actualSu81.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU81 su81 = new SU81();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "id_reemplazo", "foo");
        parametersExecute.put((String) "nombre", "foo");
        parametersExecute.put((String) "centro_costo", "foo");
        parametersExecute.put((String) "sector", "foo");
        assertThrows(TransactionException.class, () -> su81.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU81 su81 = new SU81();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "id_reemplazo", (Object) "A126661");
        su81.hardcodear(parametersExecute);
        assertEquals(5, parametersExecute.size());
    }

    @Test
    @DisplayName("Testeando hardcodear entra a throw")
    void testHardcodear5() throws Exception {
        SU81 su81 = new SU81();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("id_reemplazo", "42");
        parametersExecute.put("opcion", "CONS");
        assertThrows(Exception.class, () -> su81.hardcodear(parametersExecute));
    }
}

