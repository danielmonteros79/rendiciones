package com.sa.services.trxs;

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

class SU86Test {

    @Test
    @DisplayName("Testeando constructor")
    void constructor() {
        assertTrue((new SU86()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU86 su86 = new SU86();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_usu", "foo");
        parametersExecute.put((String) "des_mot_usu", "foo");
        parametersExecute.put((String) "fe_hasta", "foo");
        parametersExecute.put((String) "fe_desde", "foo");
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "ma_mot_usu", "foo");
        assertThrows(TransactionException.class, () -> su86.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() throws Exception {
        SU86 su86 = new SU86();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("Motivo_Usuario 9393482                                                             2023-01-01 2023-01-02                   ");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "cod_mot_usu", "foo");
        parametersExecute.put((String) "des_mot_usu", "foo");
        parametersExecute.put((String) "fe_hasta", "foo");
        parametersExecute.put((String) "fe_desde", "foo");
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "ma_mot_usu", "foo");
        su86.mapData(parametersExecute);
        assertFalse(su86.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData donde opcion no sea CONS")
    void mapData2() throws Exception {

        SU86 su86 = new SU86();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "TEST");
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "cod_mot_usu", "foo");
        parametersExecute.put((String) "des_mot_usu", "foo");
        parametersExecute.put((String) "fe_hasta", "2023-01-02");
        parametersExecute.put((String) "fe_desde", "2023-01-01");
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "ma_mot_usu", "foo");
        su86.mapData(parametersExecute);
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void ardcohdear() throws Exception {
        SU86 su86 = new SU86();
        su86.hardcodear(new HashMap<>());
        assertTrue(su86.getDataReturnList().isEmpty());
    }
}

