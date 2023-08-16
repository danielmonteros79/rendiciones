package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Element;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.utils.ArrayIterator;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU70Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU70()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU70 su70 = new SU70();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", "42");
        assertThrows(TransactionException.class, () -> su70.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU70 su70 = new SU70();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("001ESTADO 1                                          A103557 NOMBRE USUARIO PROXIMO        01A103558 NOMBRE USUARIO APROBADOR      2018-05-27");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su70.mapData(parametersExecute);
        assertFalse(su70.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando getDataReturnList")
    void getDataReturnList() {
        SU70 su70 = new SU70();
        assertSame(su70.journal, su70.getDataReturnList());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU70 su70 = new SU70();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su70.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(1, ((Collection<String>) getResult).size());
        assertEquals("001ESTADO 1                                          A103557 NOMBRE USUARIO PROXIMO        01A103558"
                + " NOMBRE USUARIO APROBADOR      2018-05-27", ((List<String>) getResult).get(0));
    }
}

