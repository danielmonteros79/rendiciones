package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroGasto;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SU85Test {

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

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
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

}
