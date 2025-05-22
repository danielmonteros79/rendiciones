package com.sa.services.trxs;

import antlr.collections.impl.LList;
import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU60Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU60()).listaRendiciones.isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU60 su60 = new SU60();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su60.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {

        SU60 su60 = new SU60();
        su60.hardcodear(new HashMap<>());

        assertNotNull(su60);
    }

    @Test
    @DisplayName("Testeando mapInputParams")
    void mapInputParams() {
        assertTrue((new SU60()).mapInputParams().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {

        SU60 su60 = new SU60();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0000000000001108000876877896987697698769869889698689685454351234 MOTIVO DE 60 01/01/2023                                     NOMBRE DE 60   12/12/2022 12/12/2022                                     12345678901234,6712345678901234,67        ");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "mensajesRespuesta", datosLista);

        su60.mapData(parametersExecute);


        assertFalse(su60.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData si la fecha esta en formato incorrecto")
    void mapDataErrorFecha() {

        SU60 su60 = new SU60();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0000000000001108000876877896987697698769869889698689685454351234 MOTIVO DE 60 01/01/2023                                            60   12-12-2022 12-12-2022                                     12345678901234,6712345678901234,67        ");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "mensajesRespuesta", datosLista);

        su60.mapData(parametersExecute);


        assertFalse(su60.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando getDataReturnList")
    void getDataReturnList() {
        SU60 su60 = new SU60();
        List actualDataReturnList = su60.getDataReturnList();
        assertSame(su60.listaRendiciones, actualDataReturnList);
        assertTrue(actualDataReturnList.isEmpty());
    }
}

