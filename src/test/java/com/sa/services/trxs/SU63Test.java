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
import com.sa.entities.Rendicion;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.utils.ArrayIterator;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU63Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        SU63 actualSu63 = new SU63();
        List<Rendicion> expectedDataReturnList = actualSu63.listaRendiciones;
        assertSame(expectedDataReturnList, actualSu63.getDataReturnList());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU63 su63 = new SU63();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", null);
        assertThrows(TransactionException.class, () -> su63.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapDataEmpty() {
        SU63 su63 = new SU63();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        su63.mapData(parametersExecute);
        assertTrue(su63.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData con datos")
    void mapData() {
        SU63 su63 = new SU63();

        List<String> contenidoLista = new ArrayList<>();
        contenidoLista.add("0000000000001108A103555 MOTIVO DE 60 CARACTERES                                     NOMBRE DE 60 CARACTERES                                     12345678901234,6712345678901234,67");
        contenidoLista.add("0000000000001109A103555 MOTIVO DE 60 CARACTERES                                     NOMBRE DE 60 CARACTERES                                     12345678901234,6712345678901234,67");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", contenidoLista);
        su63.mapData(parametersExecute);
        assertEquals(false, su63.getDataReturnList().isEmpty());
    }


    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU63 su63 = new SU63();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su63.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(2, ((Collection<String>) getResult).size());
        assertEquals(
                "0000000000001108A103555 MOTIVO DE 60 CARACTERES                                     NOMBRE DE 60"
                        + " CARACTERES                                     12345678901234,6712345678901234,67",
                ((List<String>) getResult).get(0));
        assertEquals(
                "0000000000001109A103555 MOTIVO DE 60 CARACTERES                                     NOMBRE DE 60"
                        + " CARACTERES                                     12345678901234,6712345678901234,67",
                ((List<String>) getResult).get(1));
    }
}

