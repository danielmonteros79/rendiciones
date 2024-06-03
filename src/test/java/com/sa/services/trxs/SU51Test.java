package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;   
import static org.mockito.Mockito.mock;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.itextpdf.text.Chapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU51Test {

    @Test
    void testConstructor() {
        assertTrue((new SU51()).getDataReturnList().isEmpty());
    }

    @Test
    void testExecuteTrx() throws TransactionException {
        SU51 su51 = new SU51();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su51.executeTrx(client, new HashMap<>()));
    }

    @Test
    void testExecuteTrx2() throws TransactionException {
        SU51 su51 = new SU51();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "opcion", "foo");
        assertThrows(TransactionException.class, () -> su51.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData case 1")
    void mapData() {

        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                              test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", "1");
        su51.mapData(parametersExecute);
    }

    @Test
    @DisplayName("Testeando mapData case 2")
    void mapData2() {

        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123      USD                                                        test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", "2");
        su51.mapData(parametersExecute);
    }

    @Test
    @DisplayName("Testeando mapData case 3")
    void mapData3() {

        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                      test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", "3");
        su51.mapData(parametersExecute);
    }

    /*@Test
    @DisplayName("Testeando mapData case 9")
    void mapData9() {

        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                         test                  000000000000");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", "9");
        su51.mapData(parametersExecute);
    }*/

    @Test
    @DisplayName("Testeando mapData case 7")
    void mapData7() {

        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                              test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", "7");
        su51.mapData(parametersExecute);
    }

    @Test
    void testHardcodear() {

        SU51 su51 = new SU51();
        //su51.hardcodear(new HashMap<>());
    }
}

