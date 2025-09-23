package com.sa.services;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    static class DummyTransaction extends Transaction {
        @Override
        public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
            // No-op
        }
        @Override
        protected void mapData(Map<String, Object> parametersExecute) throws Exception {
            throw new Exception("mapData error");
        }
        @Override
        protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
            // No-op
        }
        @Override
        protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute) throws Exception {
            // No-op para evitar dependencias externas y permitir cobertura de ejecutarTransaccion
        }
    }

    @Test
    @DisplayName("Cobertura: ejecutarTransaccion lanza TransactionException si mapData falla")
    void testEjecutarTransaccionThrowsTransactionException() {
        DummyTransaction trx = new DummyTransaction();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        TransactionException thrown = assertThrows(TransactionException.class, () -> {
            trx.ejecutarTransaccion(client, "TRX", params);
        });
        assertTrue(thrown.getCause() instanceof Exception);
        assertEquals("mapData error", thrown.getCause().getMessage());
    }
}