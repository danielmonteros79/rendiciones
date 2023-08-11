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

class SU71Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU71 actualSu71 = new SU71();
        actualSu71.hardcodear(new HashMap<>());
        assertTrue(actualSu71.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void testExecuteTrx() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, parametersExecute));
    }


    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU71 su71 = new SU71();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        su71.mapData(parametersExecute);
        assertTrue(su71.getDataReturnList().isEmpty());
    }
}

