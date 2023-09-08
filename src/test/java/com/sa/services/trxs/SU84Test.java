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
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        su84.executeTrx(client, parametersExecute);
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
    @DisplayName("Testeando mapData")
    void mapData() {
        SU84 su84 = new SU84();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123 descripcionesGasto                                                                                            test                                                                   test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        su84.mapData(parametersExecute);
        assertFalse(su84.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU84 su84 = new SU84();
        su84.hardcodear(new HashMap<>());
        assertSame(su84.gastos, su84.getDataReturnList());
    }
}

