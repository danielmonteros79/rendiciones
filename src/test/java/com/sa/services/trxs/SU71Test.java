package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class SU71Test {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link SU71}
     *   <li>{@link SU71#hardcodear(Map)}
     * </ul>
     */
    @Test
    void testConstructor() throws Exception {
        SU71 actualSu71 = new SU71();
        actualSu71.hardcodear(new HashMap<>());
        assertTrue(actualSu71.getDataReturnList().isEmpty());
    }

    /**
     * Method under test: default or parameterless constructor of {@link SU71}
     */
    @Test
    void testConstructor2() {
        assertTrue((new SU71()).getDataReturnList().isEmpty());
    }

    /**
     * Method under test: {@link SU71#executeTrx(IWebClient, Map)}
     */
    @Test
    void testExecuteTrx() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, new HashMap<>()));
    }

    /**
     * Method under test: {@link SU71#executeTrx(IWebClient, Map)}
     */
    @Test
    void testExecuteTrx2() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, parametersExecute));
    }

    /**
     * Method under test: {@link SU71#executeTrx(IWebClient, Map)}
     */
    @Test
    void testExecuteTrx3() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, parametersExecute));
    }

    /**
     * Method under test: {@link SU71#executeTrx(IWebClient, Map)}
     */
    @Test
    void testExecuteTrx4() throws TransactionException {
        SU71 su71 = new SU71();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        assertThrows(TransactionException.class, () -> su71.executeTrx(client, parametersExecute));
    }

    /**
     * Method under test: {@link SU71#mapData(Map)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMapData() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.sa.services.trxs.SU71.mapData(SU71.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        SU71 su71 = new SU71();
        su71.mapData(new HashMap<>());
    }

    /**
     * Method under test: {@link SU71#mapData(Map)}
     */
    @Test
    void testMapData2() {
        SU71 su71 = new SU71();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "cod_mot_sel", "foo");
        parametersExecute.put((String) "cod_glg_sel", "foo");
        su71.mapData(parametersExecute);
        assertTrue(su71.getDataReturnList().isEmpty());
    }
}

