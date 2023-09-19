package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SUCuadroDetalladoTest {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SUCuadroDetallado actualSuCuadroDetallado = new SUCuadroDetallado();
        actualSuCuadroDetallado.hardcodear(new HashMap<>());
        assertTrue(actualSuCuadroDetallado.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor2() {
        SUCuadroDetallado actualSuCuadroDetallado = new SUCuadroDetallado();
        assertTrue(actualSuCuadroDetallado.getDataReturnList().isEmpty());
        assertNull(actualSuCuadroDetallado.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SUCuadroDetallado suCuadroDetallado = new SUCuadroDetallado();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        suCuadroDetallado.executeTrx(client, parametersExecute);
        assertEquals(4, suCuadroDetallado.getDataReturnList().size());
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() throws Exception {
        SUCuadroDetallado suCuadroDetallado = new SUCuadroDetallado();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        suCuadroDetallado.mapData(parametersExecute);
        assertEquals(4, suCuadroDetallado.getDataReturnList().size());
    }
}

