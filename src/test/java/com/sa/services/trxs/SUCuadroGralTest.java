package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SUCuadroGralTest {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SUCuadroGral actualSuCuadroGral = new SUCuadroGral();
        actualSuCuadroGral.hardcodear(new HashMap<>());
        assertTrue(actualSuCuadroGral.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        SAMWebClient client = new SAMWebClient();
        suCuadroGral.executeTrx(client, new HashMap<>());
        assertEquals("", client.getId());
        assertFalse(client.isLoginOk());
        assertTrue(suCuadroGral.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData2() throws Exception {
        SUCuadroGral suCuadroGral = new SUCuadroGral();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", "42");
        parametersExecute.put((String) "opcion", (Object) "CONS");
        suCuadroGral.mapData(parametersExecute);
        assertEquals(1, suCuadroGral.getDataReturnList().size());
    }
}
