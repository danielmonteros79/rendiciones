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

class SU61Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        SU61 actualSu61 = new SU61();
        assertTrue(actualSu61.getDataReturnList().isEmpty());
        assertEquals("", actualSu61.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU61 su61 = new SU61();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "glg", "foo");
        parametersExecute.put((String) "cantidad", null);
        assertThrows(TransactionException.class, () -> su61.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU61 su61 = new SU61();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("00000000000011080205COMPRA DE BIENES DE USO                           DESCRIPCION 1                                                                                                           2018-11-262018-11-26          2005,00A103555 NOMBRE USUARIO REND 1                                                      PSUP DESC ESTADO 1                                                           test");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "glg", "foo");
        parametersExecute.put((String) "cantidad", null);
        su61.mapData(parametersExecute);
        assertFalse(su61.getDataReturnList().isEmpty());
        assertEquals("", su61.getDataReturn());
    }

    @Test
    @DisplayName("Testeando getDataReturn")
    void getDataReturn() {
        SU61 su61 = new SU61();
        su61.getDataReturn();
        assertSame(su61.listaRendiciones, su61.getDataReturnList());
    }

    @Test
    @DisplayName("Testeando hardCodear")
    void hardcodear() throws Exception {
        SU61 su61 = new SU61();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "id_rend", (Object) "42");
        su61.hardcodear(parametersExecute);
        assertEquals(3, parametersExecute.size());
    }
}

