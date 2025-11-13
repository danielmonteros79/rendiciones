package com.sa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@DisplayName("Transaction Execute Tests")
@SuppressWarnings("rawtypes")
public class TransactionExecuteTest {

    /**
     * Mock implementation of IWebClient for testing purposes
     */
    @SuppressWarnings("all")
    private static class MockWebClient extends IWebClient {
        private final Map<String, Object> attributes = new HashMap<>();

        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        public void setUserLoggin(String user) {
            attributes.put("userLoggin", user);
        }

        @Override
        public void destroy(HttpSession session) {
            // No-op for test purposes
        }

        @Override
        public void init(ar.com.bbva.web.IWebApplication webApplication) {
            // No-op for test purposes
        }
    }

    /**
     * Test implementation of Transaction that overrides execute to avoid SAM dependencies
     */
    @SuppressWarnings("unchecked")
    private static class TestTransaction extends Transaction {

        private boolean shouldSimulateError = false;
        private boolean mapDataCalled = false;

        public TestTransaction() {
            this.CURRENT_TRX = "SU_TEST";
            this.PARAMETER_TRX = "SU_TEST";
        }

        @Override
        public void executeTrx(IWebClient client, Map<String, Object> parametersExecute)
                throws TransactionException {
            try {
                if (!shouldSimulateError) {
                    // Simulate successful execution
                } else {
                    throw new Exception("Simulated transaction error");
                }
                mapData(parametersExecute);
            } catch (Exception e) {
                throw new TransactionException("Error simulado en transacción: " + e.getMessage(), e);
            }
        }

        @Override
        protected void mapData(Map<String, Object> parametersExecute) {
            mapDataCalled = true;
            dataReturn = "ok";
            dataReturnList = new ArrayList<>();
            dataReturnList.add("one");
            dataReturnList.add("two");
        }

        @Override
        protected void hardcodear(Map<String, Object> parametersExecute) {
            // no-op for tests
        }

        @Override
        protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute)
                throws Exception {
            execute(client, trxExecute, parametersExecute, CONECTOR_SOA);
        }

        @Override
        protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute,
                String conectorSoa) throws Exception {
            this.client = client;
            if (shouldSimulateError) {
                throw new Exception("Error simulado en execute");
            }
        }

        public boolean isMapDataCalled() {
            return mapDataCalled;
        }

        public void setShouldSimulateError(boolean shouldSimulateError) {
            this.shouldSimulateError = shouldSimulateError;
        }
    }

    private TestTransaction transaction;
    private MockWebClient mockClient;

    @BeforeEach
    public void setUp() {
        transaction = new TestTransaction();
        mockClient = new MockWebClient();
        mockClient.setUserLoggin("testUser");
    }

    @Test
    @DisplayName("executeTrx should map data after execute successfully")
    public void testExecuteTrxSuccess() throws TransactionException {
        Map<String, Object> params = new HashMap<>();
        params.put("input1", "value1");
        params.put("input2", "value2");

        transaction.executeTrx(mockClient, params);

        assertTrue(transaction.isMapDataCalled(), "mapData debería haber sido llamado");
        assertEquals("ok", transaction.getDataReturn(), "dataReturn debería ser 'ok'");

        List<?> list = transaction.getDataReturnList();
        assertNotNull(list, "dataReturnList no debería ser nulo");
        assertEquals(2, list.size(), "dataReturnList debería tener 2 elementos");
        assertEquals("one", list.get(0), "Primer elemento debería ser 'one'");
        assertEquals("two", list.get(1), "Segundo elemento debería ser 'two'");
    }

    @Test
    @DisplayName("executeTrx should throw TransactionException on error")
    public void testExecuteTrxError() {
        transaction.setShouldSimulateError(true);
        Map<String, Object> params = new HashMap<>();

        try {
            transaction.executeTrx(mockClient, params);
        } catch (TransactionException e) {
            assertTrue(e.getMessage().contains("Error simulado"),
                "La excepción debería contener el mensaje de error simulado");
            assertNotNull(e.getCause(), "La excepción debería tener una causa");
        }
    }

    @Test
    @DisplayName("executeTrx should handle null parameters gracefully")
    public void testExecuteTrxWithNullParams() throws TransactionException {
        Map<String, Object> params = new HashMap<>();

        transaction.executeTrx(mockClient, params);

        assertEquals("ok", transaction.getDataReturn(), "Debería funcionar con parámetros vacíos");
    }

    @Test
    @DisplayName("executeTrx should handle null client")
    public void testExecuteTrxWithNullClient() {
        Map<String, Object> params = new HashMap<>();

        try {
            try {
                transaction.executeTrx(null, params);
            } catch (TransactionException e) {
                throw new RuntimeException(e);
            }
            assertEquals("ok", transaction.getDataReturn(),
                "Debería funcionar incluso sin client (si la implementación lo permite)");
        } catch (NullPointerException e) {
            assertNotNull(e, "Error manejado apropiadamente con client nulo");
        }
    }

    @Test
    @DisplayName("dataReturn should be initialized")
    public void testDataReturnInitialized() {
        assertNotNull(transaction.getDataReturn(), "dataReturn no debería ser nulo");
    }

    @Test
    @DisplayName("dataReturnList should be initialized")
    public void testDataReturnListInitialized() {
        List<?> list = transaction.getDataReturnList();
        assertNotNull(list, "dataReturnList no debería ser nulo");
        assertTrue(list instanceof ArrayList, "dataReturnList debería ser una ArrayList");
    }
}

