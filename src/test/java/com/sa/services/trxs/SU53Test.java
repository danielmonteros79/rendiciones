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
import java.util.Collections;
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
    
    @Test
    @DisplayName("Testeando mapData con EXEP y campos opcionales IDU y ADEA")
    void mapDataWithEXEPandOptionalFields() throws Exception {
        SU53 su53 = new SU53();

        StringBuilder sb = new StringBuilder("00000000000011080205COMPRA DE BIENES DE USO                           a                                                                                                                       OBSER2018-11-262018-11-26           633,00");
        sb.append("IDU1234567"); // 10 caracteres
        sb.append("ADEA9876543"); // 11 caracteres
        sb.append("EXEP"); // marca especial al final

        List<String> datosLista = Collections.singletonList(sb.toString());
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("lista", datosLista);
        parameters.put("aviso", "aviso prueba");
        parameters.put("desc_est_rend", "desc estado");
        parameters.put("nomUsrAprob", "aprobador");
        parameters.put("codUsrAprob", "codAprobador");
        parameters.put("desc_rechazo", "rechazo");
        parameters.put("fec_ult_mod", "2021-12-31");

        su53.mapData(parameters);

        Rendicion r = (Rendicion) su53.getDataReturnList().get(0);
        assertEquals("IDU1234567", r.getIdu());
        assertEquals("ADEA9876543", r.getAdea());
        assertEquals("EXEP", r.getExceptuado());
    }
    
    @Test
    @DisplayName("Testeando mapData con aviso vacío")
    void mapDataWithEmptyAviso() throws Exception {
        SU53 su53 = new SU53();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("00000000000011070204USO DE VEHICULOS DE GESTION COMERCIAL             a                                                                                                                       OBSER2018-11-262018-11-26           633,00");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("aviso", "  ");
        parametersExecute.put("desc_est_rend", "estado");
        parametersExecute.put("nomUsrAprob", "nombre");
        parametersExecute.put("codUsrAprob", "codigo");
        parametersExecute.put("desc_rechazo", "rechazo");
        parametersExecute.put("fec_ult_mod", null);

        su53.mapData(parametersExecute);

        Rendicion r = (Rendicion) su53.getDataReturnList().get(0);
        assertEquals("", r.getAviso());
        assertNull(r.getFechaUltimaModificacion());
    }
    
    @Test
    @DisplayName("Testeando hardcodear y su efecto sobre los datos")
    void hardcodear() throws Exception {
        SU53 su53 = new SU53();
        Map<String, Object> params = new HashMap<>();
        params.put("idRendicion", "00000000000011080205");

        su53.hardcodear(params);

        assertTrue(params.containsKey("lista"));
        assertTrue(params.containsKey("aviso"));
        assertTrue(((List<?>) params.get("lista")).isEmpty());
        assertEquals("SE ACTUALIZO LA POLITICA DE GASTOS - NUEVOS TOPES VIGENTES", params.get("aviso"));
    }
    
    @Test
    @DisplayName("Testeando hardcodear sin idRendicion (devuelve lista completa)")
    void hardcodearSinIdRendicion() throws Exception {
        SU53 su53 = new SU53();
        Map<String, Object> params = new HashMap<>();
        params.put("idRendicion", "");

        su53.hardcodear(params);

        assertTrue(params.containsKey("lista"));
        List<?> lista = (List<?>) params.get("lista");
        assertTrue(lista.size() > 10);
    }
}

