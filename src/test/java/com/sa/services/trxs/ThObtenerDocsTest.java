package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class ThObtenerDocsTest {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        ThObtenerDocs actualThObtenerDocs = new ThObtenerDocs();
        assertNull(actualThObtenerDocs.getAviso());
        assertTrue(actualThObtenerDocs.getDataReturnList().isEmpty());
        assertNull(actualThObtenerDocs.getContexto());
        assertNull(actualThObtenerDocs.getClient());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        ThObtenerDocs thObtenerDocs = new ThObtenerDocs();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        thObtenerDocs.executeTrx(client, parametersExecute);
        assertEquals("", client.getId());
        assertFalse(client.isLoginOk());
        assertTrue(thObtenerDocs.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        ThObtenerDocs thObtenerDocs = new ThObtenerDocs();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("NOMBRE ARCHIVO 1                                  A123456789");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        thObtenerDocs.mapData(parametersExecute);
        assertFalse(thObtenerDocs.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        ThObtenerDocs thObtenerDocs = new ThObtenerDocs();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        thObtenerDocs.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(1, ((Collection<String>) getResult).size());
        assertEquals("NOMBRE ARCHIVO 1                                  A123456789", ((List<String>) getResult).get(0));
    }
}

