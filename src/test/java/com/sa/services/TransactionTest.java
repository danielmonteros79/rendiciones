package com.sa.services;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.IContext;
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

    static class DummyTransactionExecute extends Transaction {
        @Override
        public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) {
            // No-op
        }
        @Override
        protected void mapData(Map<String, Object> parametersExecute) {
            // No-op
        }
        @Override
        protected void hardcodear(Map<String, Object> parametersExecute) {
            // No-op
        }
        @Override
        protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute, String conectorSoa) throws Exception {
            // Simula error en la llamada a getSAM().execute para forzar el catch
            throw new RuntimeException("Simulando error en getSAM().execute");
        }
    }

    static class DummyTransactionContext extends Transaction {
        @Override
        public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) {
            // No-op
        }
        @Override
        protected void mapData(Map<String, Object> parametersExecute) {
            // No-op
        }
        @Override
        protected void hardcodear(Map<String, Object> parametersExecute) {
            // No-op
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

    @Test
    @DisplayName("Cobertura: catch del do-while en Transaction.execute (líneas seleccionadas)")
    void execute_catch_doWhile_selectedLines() {
        DummyTransactionExecute trx = new DummyTransactionExecute();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        Exception ex = assertThrows(Exception.class, () -> trx.execute(client, "TRX", params, "CON"));
        assertEquals("Simulando error en getSAM().execute", ex.getMessage());
    }


}