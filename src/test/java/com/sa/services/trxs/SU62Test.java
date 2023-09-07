package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class SU62Test {

    @Spy
    SU62 su62;

    @BeforeEach
    public void setup() {
        su62 = new SU62() {
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
    void testConstructor() throws Exception {
        SU62 actualSu62 = new SU62();
        actualSu62.hardcodear(new HashMap<>());
        assertTrue(actualSu62.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void testExecuteTrx() throws TransactionException {
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        su62.executeTrx(client, parametersExecute);
        assertNotNull(parametersExecute);
    }

    @Test
    @DisplayName("Testeando executeTrx Exception")
    void testExecuteTrxException() throws TransactionException {
        SU62 su62 = new SU62();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("Test", "42");
        assertThrows(TransactionException.class, () -> su62.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU62 su62 = new SU62();
        su62.mapData(new HashMap<>());
        assertNull(su62.getDataReturn());
    }
}

