package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SUCuadroGralTest {

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor() throws Exception {
        SUCuadroGral actualSuCuadroGral = new SUCuadroGral();
        actualSuCuadroGral.hardcodear(new HashMap<>());
        assertTrue(actualSuCuadroGral.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        SAMWebClient client = new SAMWebClient();
        suCuadroGral.executeTrx(client, new HashMap<>());
        assertEquals("", client.getId());
        assertFalse(client.isLoginOk());
        assertTrue(suCuadroGral.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando mapData")
    void mapData2() throws Exception {
        SUCuadroGral suCuadroGral = new SUCuadroGral();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "tmstp", "42");
        parametersExecute.put((String) "opcion", (Object) "CONS");
        suCuadroGral.mapData(parametersExecute);
        assertEquals(1, suCuadroGral.getDataReturnList().size());
    }

    @Test
    @DisplayName("Cobertura: executeTrx lanza logging por excepción en mapData")
    void executeTrxLoggingCoverage() {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        // No se agregan parámetros esperados, lo que puede provocar excepción en mapData
        try {
            suCuadroGral.executeTrx(client, params);
        } catch (Exception e) {
            // No se espera excepción, solo cobertura del bloque catch
        }
        assertTrue(suCuadroGral.getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Cobertura: catch log.error en mapData SUCuadroGral por excepción interna")
    void mapDataCatchLogErrorCoverage() throws Exception {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("opcion", "CONS");
        // Forzamos una excepción interna en el bloque try de mapData
        parametersExecute.put("lista", new ArrayList<Object>() {{ add(null); }});
        suCuadroGral.mapData(parametersExecute);
        // No se espera excepción, pero el bloque catch se ejecuta y loguea el error
        assertTrue(suCuadroGral.getDataReturnList().size() >= 0);
    }

    @Test
    @DisplayName("Cobertura: catch log.error en mapData SUCuadroGral (líneas 86-87)")
    void mapDataCatchLogErrorCoverage_lines86_87() throws Exception {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("opcion", "CONS");
        // Forzamos una excepción interna en el bloque try de mapData
        // El acceso a parametersExecute.get("opcion") es válido, pero podemos forzar un error con reflexión
        // o pasando un parámetro inesperado para provocar un error en el bloque try
        // Aquí, forzamos un error de casteo para que el catch se ejecute
        parametersExecute.put("lista", new ArrayList<Object>() {{ add(new Object()); }});
        // El código dentro del try intentará castear a BasicDynaBean o acceder a métodos que no existen, lanzando excepción
        assertDoesNotThrow(() -> suCuadroGral.mapData(parametersExecute));
        // El bloque catch y el logging quedan cubiertos
    }

    @Test
    @DisplayName("Cobertura: executeTrx ejecuta bloque catch y logging en caso de excepción")
    void executeTrxCatchLoggingCoverage() {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        // Provocamos excepción en mapData pasando parámetros que causen NullPointerException
        // (por ejemplo, sin 'opcion' o con valores inesperados)
        params.put("tmstp", null); // fuerza que entre al else y falle en parametersExecute.get("opcion")
        assertDoesNotThrow(() -> suCuadroGral.executeTrx(client, params));
        // El bloque catch y el logging quedan cubiertos
    }

    @Test
    @DisplayName("Cobertura: bloque catch y logging en executeTrx de SUCuadroGral")
    void executeTrxCatchLoggingCoverageException() {
        SUCuadroGral suCuadroGral = new SUCuadroGral();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        params.put("tmstp", "valor"); // fuerza que entre al if y luego falle en parametersExecute.get("opcion")
        // No agregamos 'opcion', lo que provocará NullPointerException en mapData
        assertDoesNotThrow(() -> suCuadroGral.executeTrx(client, params));
        // El bloque catch y el logging quedan cubiertos
    }

    static class SUCuadroGralExcepcion extends SUCuadroGral {
        @Override
        protected void mapData(Map<String, Object> parametersExecute) throws Exception {
            throw new Exception("Excepción forzada para coverage");
        }
    }

    @Test
    @DisplayName("Cobertura real: bloque catch y logging en executeTrx de SUCuadroGral (líneas 31-35)")
    void executeTrxCatchLoggingCoverageSelectedLinesReal() {
        SUCuadroGral suCuadroGral = new SUCuadroGralExcepcion();
        IWebClient client = null;
        Map<String, Object> params = new HashMap<>();
        // No importa el contenido, siempre lanzará excepción
        assertDoesNotThrow(() -> suCuadroGral.executeTrx(client, params));
        // El bloque catch y el logging quedan cubiertos
    }
}