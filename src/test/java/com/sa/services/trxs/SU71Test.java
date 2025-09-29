package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU71Test {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SU71 actualSu71 = new SU71();
        actualSu71.hardcodear(new HashMap<>());
        assertTrue(actualSu71.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void testExecuteTrx() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, parametersExecute));
    }


    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU71 su71 = new SU71();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("01234 8475643092713485764392810457438297 0123456789 012345678987654 A");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        su71.mapData(parametersExecute);
        assertFalse(su71.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Cobertura de executeTrx: cubre excepción de entorno")
    void testExecuteTrx_coverage() {
        IWebClient client = org.mockito.Mockito.mock(IWebClient.class);
        Map<String, Object> params = new HashMap<>();
        params.put("lista", new ArrayList<>());
        params.put("cod_mot_sel", "motivo");
        params.put("cod_glg_sel", "glg");
        SU71 su71 = new SU71();
        // Verificar que el método está cubierto aunque lance excepción de entorno
        assertThrows(Exception.class, () -> su71.executeTrx(client, params));
    }

    @Test
    @DisplayName("Cobertura del catch en mapData: log de error")
    void testMapData_catchException_coverage() {
        SU71 su71 = new SU71();
        // Forzar excepción: string demasiado corto para substring(0, 5)
        List<String> datosLista = new ArrayList<>();
        datosLista.add(""); // String vacío, lanzará StringIndexOutOfBoundsException
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("cod_mot_sel", "motivo");
        parametersExecute.put("cod_glg_sel", "glg");
        // No debe lanzar excepción hacia afuera, pero debe entrar al catch
        assertDoesNotThrow(() -> su71.mapData(parametersExecute));
        // La lista de retorno debe estar vacía porque no se pudo agregar el objeto
        assertTrue(su71.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Cobertura directa de ejecutarTransaccion en executeTrx usando subclase")
    void testExecuteTrx_llamaEjecutarTransaccion_subclase() {
        IWebClient client = org.mockito.Mockito.mock(IWebClient.class);
        Map<String, Object> params = new HashMap<>();
        params.put("lista", new ArrayList<>());
        params.put("cod_mot_sel", "motivo");
        params.put("cod_glg_sel", "glg");

        // Subclase anónima para interceptar la llamada
        class SU71Testable extends SU71 {
            boolean called = false;
            IWebClient calledClient;
            String calledTrx;
            Map<String, Object> calledParams;

            @Override
            protected void ejecutarTransaccion(IWebClient client, String trx, Map<String, Object> parameters) {
                called = true;
                calledClient = client;
                calledTrx = trx;
                calledParams = parameters;
            }
        }

        SU71Testable su71 = new SU71Testable();
        assertDoesNotThrow(() -> su71.executeTrx(client, params));
        assertTrue(su71.called, "Se debe llamar a ejecutarTransaccion");
        assertEquals(client, su71.calledClient);
        assertEquals("SUM_REPORTERIA_CUADRO_GENERAL", su71.calledTrx);
        assertEquals(params, su71.calledParams);
    }

    @Test
    @DisplayName("Cobertura directa de ejecutarTransaccion en executeTrx (mockeando en subclase)")
    void testExecuteTrx_llamaEjecutarTransaccion_mock() {
        IWebClient client = org.mockito.Mockito.mock(IWebClient.class);
        Map<String, Object> params = new HashMap<>();
        params.put("lista", new ArrayList<>());
        params.put("cod_mot_sel", "motivo");
        params.put("cod_glg_sel", "glg");
        // Subclase anónima para interceptar la llamada y simular mock
        class SU71Testable extends SU71 {
            boolean called = false;
            IWebClient calledClient;
            String calledTrx;
            Map<String, Object> calledParams;
            @Override
            protected void ejecutarTransaccion(IWebClient client, String trx, Map<String, Object> parameters) {
                called = true;
                calledClient = client;
                calledTrx = trx;
                calledParams = parameters;
            }
        }
        SU71Testable su71 = new SU71Testable();
        assertDoesNotThrow(() -> su71.executeTrx(client, params));
        assertTrue(su71.called, "Se debe llamar a ejecutarTransaccion");
        assertEquals(client, su71.calledClient);
        assertEquals("SUM_REPORTERIA_CUADRO_GENERAL", su71.calledTrx);
        assertEquals(params, su71.calledParams);
    }
}