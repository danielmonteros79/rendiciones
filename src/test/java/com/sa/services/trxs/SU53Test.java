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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.utils.ArrayIterator;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU53Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() {
        assertTrue((new SU53()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU53 su53 = new SU53();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", null);
        parametersExecute.put((String) "aviso", "foo");
        parametersExecute.put((String) "desc_est_rend", "foo");
        parametersExecute.put((String) "nomUsrAprob", "foo");
        parametersExecute.put((String) "codUsrAprob", "foo");
        parametersExecute.put((String) "desc_rechazo", "foo");
        parametersExecute.put((String) "fec_ult_mod", "foo");
        assertThrows(TransactionException.class, () -> su53.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() throws Exception {
        SU53 su53 = new SU53();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("00000000000011070204USO DE VEHICULOS DE GESTION COMERCIAL             a                                                                                                                       OBSER2018-11-262018-11-26           633,00");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "aviso", "foo");
        parametersExecute.put((String) "desc_est_rend", "foo");
        parametersExecute.put((String) "nomUsrAprob", "foo");
        parametersExecute.put((String) "codUsrAprob", "foo");
        parametersExecute.put((String) "desc_rechazo", "foo");
        parametersExecute.put((String) "fec_ult_mod", "foo");
        su53.mapData(parametersExecute);
        assertFalse(su53.getDataReturnList().isEmpty());
    }

    /*@Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU53 su53 = new SU53();
        su53.hardcodear(new HashMap<>());
        assertTrue(su53.getDataReturnList().isEmpty());
    }*/
}

