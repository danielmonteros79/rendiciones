package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroGasto;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SU85Test {

    @Spy
    SU85 su85;


    @BeforeEach
    public void setup() {
        su85 = new SU85() {
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
        SU85 actualSu85 = new SU85();
        actualSu85.hardcodear(new HashMap<>());
        ParametroGasto expectedDataReturn = actualSu85.gastoReturn;
        assertSame(expectedDataReturn, actualSu85.getDataReturn());
    }

    @Test
    @DisplayName("Testeando constructor")
    void testConstructor2() {
        SU85 actualSu85 = new SU85();
        assertTrue(actualSu85.getDataReturnList().isEmpty());
        ParametroGasto expectedDataReturn = actualSu85.gastoReturn;
        assertSame(expectedDataReturn, actualSu85.getDataReturn());
    }

    @ParameterizedTest
    @MethodSource("executeTrxSource")
    @DisplayName("Testeando executeTrx")
    void executeTrx(HashMap<String, Object> parametersExecute) throws TransactionException {
        SAMWebClient client = new SAMWebClient();

        su85.executeTrx(client, parametersExecute);
        assertNotNull(parametersExecute);
    }

    @Test
    @DisplayName("Testeando executeTrx Exception")
    void executeTrxException() throws TransactionException {
        SU85 su85 = new SU85();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "modo", (Object) "I");
        parametersExecute.put((String) "opcion", (Object) "ALTA");
        parametersExecute.put((String) "lista", objectList);
        assertThrows(TransactionException.class, () -> su85.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando executeTrx Exception 2")
    void executeTrxException2() throws Exception {
        List gastos = mock(List.class);
        when(gastos.add(any())).thenThrow(new ClassCastException());

        Field gasto = SU85.class.getDeclaredField("gastos");
        gasto.setAccessible(true);
        gasto.set(su85, gastos);

        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "modo", (Object) "I");
        parametersExecute.put((String) "opcion", (Object) "ALTA");
        parametersExecute.put((String) "lista", objectList);
        su85.executeTrx(client, parametersExecute);
        assertNotNull(parametersExecute);
    }


    @Test
    @DisplayName("Testeando mapData")
    void mapData() {
        SU85 su85 = new SU85();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "modo", (Object) "I");
        parametersExecute.put((String) "opcion", (Object) SU85.OPCION_MODIFICAR);
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "antig", "foo");
        parametersExecute.put((String) "bimon", "foo");
        parametersExecute.put((String) "desc_gasto", "foo");
        parametersExecute.put((String) "observ", "foo");
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "cent_costo", "foo");
        parametersExecute.put((String) "compte", "foo");
        parametersExecute.put((String) "plazo_ap", "foo");
        parametersExecute.put((String) "ristra", "foo");
        parametersExecute.put((String) "inc_excl", "foo");
        parametersExecute.put((String) "ni_ing", "foo");
        su85.mapData(parametersExecute);
        Object getResult = parametersExecute.get("oscar");
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getRistra());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getPlazoAprob());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getObserv());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getAntiguedad());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getBimon());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getComprob());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getNivelIngreso());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getDescripcionGasto());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getMaInclExcl());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getCcostos());
        assertSame(getResult, ((ParametroGasto) su85.getDataReturn()).getEstado());
        assertTrue(((ParametroGasto) su85.getDataReturn()).getCentrosCosto().isEmpty());
        OSCAR oscar = ((ParametroGasto) su85.getDataReturn()).getOscar();
        assertEquals(" ", oscar.getA());
        assertEquals(" ", oscar.getS());
        assertEquals(" ", oscar.getC());
        assertEquals(" ", oscar.getO());
        assertEquals(" ", oscar.getR());
    }

    // ------ Sources ------

    private static Stream<Arguments> executeTrxSource() {
        ArrayList<Object> objectList = new ArrayList<>();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        HashMap<String, Object> parametersExecute2 = new HashMap<>();

        objectList.add("42");

        parametersExecute.put((String) "modo", (Object) "I");
        parametersExecute.put((String) "oscar", (Object) "oscar");
        parametersExecute.put((String) "opcion", (Object) "BAJA");
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "vcccost", "vcccostvcccost");

        parametersExecute2.put((String) "modo", (Object) "I");
        parametersExecute2.put((String) "opcion", (Object) "ALTA");
        parametersExecute2.put((String) "lista", objectList);
        parametersExecute2.put((String) "vcccost", "vcccostvcccost");


        return Stream.of(
            Arguments.of(parametersExecute),
            Arguments.of(parametersExecute2)
        );
    }

}
