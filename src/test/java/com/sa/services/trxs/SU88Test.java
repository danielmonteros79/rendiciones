package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU88Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU88()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU88 su88 = new SU88();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", null);
        parametersExecute.put((String) "cod_mot", "foo");
        parametersExecute.put((String) "cod_gto", "foo");
        parametersExecute.put((String) "mont_can", "foo");
        parametersExecute.put((String) "cod_rend", "foo");
        parametersExecute.put((String) "me_valor", "foo");
        parametersExecute.put((String) "periodo", "foo");
        parametersExecute.put((String) "critico", "foo");
        parametersExecute.put((String) "ni_min", "foo");
        parametersExecute.put((String) "ni_max", "foo");
        parametersExecute.put((String) "tx_aviso", "foo");
        parametersExecute.put((String) "est_aviso", "foo");
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "opcion", (Object) "FILT");
        assertThrows(TransactionException.class, () -> su88.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData tmstp not null")
    void mapData() {
        SU88 su88 = new SU88();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", "42");
        parametersExecute.put((String) "cod_mot", "foo");
        parametersExecute.put((String) "cod_gto", "foo");
        parametersExecute.put((String) "mont_can", "foo");
        parametersExecute.put((String) "cod_rend", "foo");
        parametersExecute.put((String) "me_valor", "foo");
        parametersExecute.put((String) "periodo", "foo");
        parametersExecute.put((String) "critico", "foo");
        parametersExecute.put((String) "ni_min", "foo");
        parametersExecute.put((String) "ni_max", "foo");
        parametersExecute.put((String) "tx_aviso", "foo");
        parametersExecute.put((String) "est_aviso", "foo");
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "opcion", (Object) "FILT");
        su88.mapData(parametersExecute);
        assertEquals(1, su88.getDataReturnList().size());
    }

    /*@Test
    @DisplayName("Testeando mapData tmstp null opcionFILT")
    void mapDataTmstpNull() {
        SU88 su88 = new SU88();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                            test                                test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", null);
        parametersExecute.put((String) "cod_mot", "foo");
        parametersExecute.put((String) "cod_gto", "foo");
        parametersExecute.put((String) "mont_can", "foo");
        parametersExecute.put((String) "cod_rend", "foo");
        parametersExecute.put((String) "me_valor", "foo");
        parametersExecute.put((String) "periodo", "foo");
        parametersExecute.put((String) "critico", "foo");
        parametersExecute.put((String) "ni_min", "foo");
        parametersExecute.put((String) "ni_max", "foo");
        parametersExecute.put((String) "tx_aviso", "foo");
        parametersExecute.put((String) "est_aviso", "foo");
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", (Object) "FILT");
        su88.mapData(parametersExecute);
        assertEquals(1, su88.getDataReturnList().size());
    }*/

    /*@Test
    @DisplayName("Testeando mapData tmstp null opcionCONS")
    void mapDataTmstpNull2() {
        SU88 su88 = new SU88();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                            test                                                                                                  test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", null);
        parametersExecute.put((String) "cod_mot", "foo");
        parametersExecute.put((String) "cod_gto", "foo");
        parametersExecute.put((String) "mont_can", "foo");
        parametersExecute.put((String) "cod_rend", "foo");
        parametersExecute.put((String) "me_valor", "foo");
        parametersExecute.put((String) "periodo", "foo");
        parametersExecute.put((String) "critico", "foo");
        parametersExecute.put((String) "ni_min", "foo");
        parametersExecute.put((String) "ni_max", "foo");
        parametersExecute.put((String) "tx_aviso", "foo");
        parametersExecute.put((String) "est_aviso", "foo");
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "opcion", (Object) "CONS");
        su88.mapData(parametersExecute);
        assertEquals(1, su88.getDataReturnList().size());
    }*/

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {

        SU88 su88 = new SU88();
        su88.hardcodear(new HashMap<>());

    }
}

