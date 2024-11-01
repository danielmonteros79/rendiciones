package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import com.sa.entities.parametros.ParametroGasto;

import static org.junit.jupiter.api.Assertions.*;

class SU84Test {

    @Spy
    SU84 su84;

    @BeforeEach
    public void setup() {
        su84 = new SU84() {
            @Override
            protected void execute(IWebClient client,
                                   String trxExecute,
                                   Map<String, Object> parametersExecute) throws Exception {
                if(client == null) {
                    throw new Exception();
                }
            }
        };
    }

    @Test
    @DisplayName("Testeando Constructor")
    void testConstructor() {
        assertTrue((new SU84()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
    	SU84 su84 = new SU84();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        assertThrows(TransactionException.class, () -> su84.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando executeTrx Exception")
    void executeTrxException() throws TransactionException {
        SU84 su84 = new SU84();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        assertThrows(TransactionException.class, () -> su84.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando executeTrx Exception 2")
    void executeTrxException2() throws TransactionException {
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", 1);
        assertThrows(TransactionException.class, () -> su84.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU84 su84 = new SU84();
        su84.hardcodear(new HashMap<>());
        assertSame(su84.gastos, su84.getDataReturnList());
    }
    
    @Test
    @DisplayName("Testeando mapData con lista de parámetros y cod_gasto vacío")
    void mapDataWithEmptyCodGasto() {
        SU84 su84 = new SU84();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123 descripcionesGasto                                                                                            test                                                                   test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("cod_gasto", "");

        su84.mapData(parametersExecute);

        assertFalse(su84.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData con cod_gasto y cod_motivo válidos")
    void mapDataWithCodGastoAndCodMotivo() {
        SU84 su84 = new SU84();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_gasto", "0123");
        parametersExecute.put("cod_motivo", "MOT1");
        parametersExecute.put("desc_motivo", "Descripción Motivo");
        parametersExecute.put("desc_gto", "Descripción Gasto");
        parametersExecute.put("estado", "A");
        parametersExecute.put("bimon", "Y");
        parametersExecute.put("ristra", "ristraValue");

        su84.mapData(parametersExecute);

        assertFalse(su84.getDataReturnList().isEmpty());
        ParametroGasto gasto = (ParametroGasto) su84.getDataReturnList().get(0);
        assertEquals("0123", gasto.getGasto());
        assertEquals("MOT1", gasto.getMotivo());
        assertEquals("Descripción Motivo", gasto.getDescripcionMotivo());
        assertEquals("Descripción Gasto", gasto.getDescripcionGasto());
        assertEquals("A", gasto.getEstado());
        assertEquals("Y", gasto.getBimon());
        assertEquals("ristraValue", gasto.getRistra());
    }

    @Test
    @DisplayName("Testeando mapData lanza excepción si cod_motivo o desc_motivo están vacíos")
    void mapDataThrowsExceptionIfCodMotivoOrDescMotivoEmpty() {
        SU84 su84 = new SU84();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_gasto", "0123");
        parametersExecute.put("cod_motivo", "");
        parametersExecute.put("desc_motivo", "Descripción Motivo");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            su84.mapData(parametersExecute);
        });

        assertEquals("El parámetro 'cod_motivo' no puede ser nulo o vacío", exception.getMessage());
    }
    
    @Test
    @DisplayName("Testeando mapData lanza excepción si lista es null o no es una lista")
    void mapDataThrowsExceptionIfListaIsNullOrNotAList() {
        SU84 su84 = new SU84();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_gasto", "");
        parametersExecute.put("lista", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            su84.mapData(parametersExecute);
        });

        assertEquals("El parámetro 'lista' no es una lista válida o es null", exception.getMessage());
    }

    @Test
    @DisplayName("Testeando mapData lanza excepción si str no tiene longitud mínima requerida")
    void mapDataThrowsExceptionIfStrLengthIsInvalid() {
        SU84 su84 = new SU84();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("Cadena corta");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_gasto", "");
        parametersExecute.put("lista", datosLista);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            su84.mapData(parametersExecute);
        });

        assertEquals("Cadena 'str' no tiene la longitud mínima requerida", exception.getMessage());
    }

}

