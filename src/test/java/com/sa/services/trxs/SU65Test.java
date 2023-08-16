package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU65Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU65()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU65 su65 = new SU65();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", "42");
        assertThrows(TransactionException.class, () -> su65.executeTrx(client, parametersExecute));
    }


    @Test
    @DisplayName("Testeando mapData")
    void mapData() throws Exception {
        SU65 su65 = new SU65();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("A2345678000000000012345612345678901234562019-02-21123456789012123456789012123456789012ESTABLECIMIENTO DE 50 CARACTERES                  0000012345678ARS12345678901232019-03-16P2019-03-142018-12-26-15.10.28.785850A23456782019-12-26-15.10.28.785850");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su65.mapData(parametersExecute);
        assertFalse(su65.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando maskCardNumber")
    void maskCardNumber() {
        assertEquals("Mask", SU65.maskCardNumber("42", "Mask"));
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU65 su65 = new SU65();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su65.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(2, ((Collection<String>) getResult).size());
        assertEquals("A2345678000000000012345612345678901234562019-02-21123456789012123456789012123456789012ESTABLECIMIENTO"
                + " DE 50 CARACTERES                  0000012345678ARS12345678901232019-03-16P2019-03-142018-12-26-15.10"
                + ".28.785850A23456782019-12-26-15.10.28.785850", ((List<String>) getResult).get(0));
        assertEquals("A2345678000000000012345712345678901234562019-02-21123456789012123456789012123456789012ESTABLECIMIENTO"
                + " DE 50 CARACTERES                  0000012345678ARS12345678901232019-03-16P2019-03-142018-12-26-15.10"
                + ".28.785850A23456782019-12-26-15.10.28.785850", ((List<String>) getResult).get(1));
    }
}

