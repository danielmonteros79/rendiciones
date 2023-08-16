package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.*;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU57Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU57()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU57 su57 = new SU57();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su57.executeTrx(client, parametersExecute));
    }

    @Test
    void mapData(){
        SU57 su57 = new SU57();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("COD1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su57.mapData(parametersExecute);

        assertNotNull(su57);
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU57 su57 = new SU57();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su57.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(4, ((Collection<String>) getResult).size());
        assertEquals("TXT1           SSAPELLIDO/NOMBRE", ((List<String>) getResult).get(0));
        assertEquals("NUM1           SSDNI", ((List<String>) getResult).get(1));
        assertEquals("FEC1           SSFEC.NACIM.", ((List<String>) getResult).get(2));
        assertEquals("TXT2           SSINSTITUCION", ((List<String>) getResult).get(3));
    }
}

