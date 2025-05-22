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

class SU80Test {


    @Test
    @DisplayName("Testeando executeTrx3")
    void executeTrx() throws TransactionException {
        SU80 su80 = new SU80();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", "42");
        assertThrows(TransactionException.class, () -> su80.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() throws Exception {
        SU80 su80 = new SU80();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("RED DEVIL, ANALIA LAURA                                                    00998568SIA2018-12-262019-04-23A126661 A103555 2018-12-26-15.10.28.785850");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su80.mapData(parametersExecute);
        assertFalse(su80.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando getDataReturnList")
    void getDataReturnList() {
        assertTrue((new SU80()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU80 su80 = new SU80();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_user", (Object) "A103555");
        su80.hardcodear(parametersExecute);
        assertEquals(2, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(1, ((Collection<String>) getResult).size());
        assertEquals("RED DEVIL, ANALIA LAURA                                                    00998568SIA2018-12-262019"
                + "-04-23A126661 A103555 2018-12-26-15.10.28.785850", ((List<String>) getResult).get(0));
    }
}
