package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class SU81Test {

    @Spy
    SU81 su81;

    @BeforeEach
    public void setup() {
        su81 = new SU81() {
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
    @DisplayName("Testeando constructor")
    void testConstructor() {
        SU81 actualSu81 = new SU81();
        assertTrue(actualSu81.getDataReturnList().isEmpty());
        assertNull(actualSu81.getDataReturn());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "id_reemplazo", "1");
        parametersExecute.put((String) "nombre", "1");
        parametersExecute.put((String) "centro_costo", "1");
        parametersExecute.put((String) "sector", "1");
        su81.executeTrx(client, parametersExecute);
        assertNotNull(parametersExecute);
    }

    @Test
    @DisplayName("Testeando executeTrx Exception")
    void executeTrxException() throws TransactionException {
        SU81 su81 = new SU81();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "id_reemplazo", "foo");
        parametersExecute.put((String) "nombre", "foo");
        parametersExecute.put((String) "centro_costo", "foo");
        parametersExecute.put((String) "sector", "foo");
        assertThrows(TransactionException.class, () -> su81.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU81 su81 = new SU81();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "opcion", (Object) "CONS");
        parametersExecute.put((String) "id_reemplazo", (Object) "A126661");
        su81.hardcodear(parametersExecute);
        assertEquals(5, parametersExecute.size());
    }

    @Test
    @DisplayName("Testeando hardcodear entra a throw")
    void testHardcodear5() throws Exception {
        SU81 su81 = new SU81();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("id_reemplazo", "42");
        parametersExecute.put("opcion", "CONS");
        assertThrows(Exception.class, () -> su81.hardcodear(parametersExecute));
    }
}

