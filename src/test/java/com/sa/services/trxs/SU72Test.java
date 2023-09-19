package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Element;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.utils.ArrayIterator;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU72Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU72 actualSu72 = new SU72();
        actualSu72.hardcodear(new HashMap<>());
        assertTrue(actualSu72.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void testExecuteTrx() throws TransactionException {
        SU72 su72 = new SU72();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", null);
        assertThrows(TransactionException.class, () -> su72.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU72 su72 = new SU72();
        Chapter chapter = mock(Chapter.class);
        when(chapter.iterator()).thenReturn(new ArrayIterator<>(new Element[]{new Anchor()}));

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", chapter);
        su72.mapData(parametersExecute);
        verify(chapter).iterator();
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapDataConDatos() {
        SU72 su72 = new SU72();
        List <String> datosLista = new ArrayList<>();
        datosLista.add("000000000000000100020000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400005000000000000000000000000000006000000072017-02-1200000000000000009");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su72.mapData(parametersExecute);

    }

}

