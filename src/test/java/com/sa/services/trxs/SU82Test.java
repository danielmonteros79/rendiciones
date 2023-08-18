package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SU82Test {


    @Test
    void testConstructor() throws Exception {
        SU82 actualSu82 = new SU82();
        actualSu82.hardcodear(new HashMap<>());
        assertTrue(actualSu82.getDataReturnList().isEmpty());
    }


    @Test
    void testConstructor2() {
        assertTrue((new SU82()).getDataReturnList().isEmpty());
    }


    @Test
    void testExecuteTrx() throws TransactionException {
        SU82 su82 = new SU82();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su82.executeTrx(client, new HashMap<>()));
    }


    @Test
    void testExecuteTrx2() throws TransactionException {
        SU82 su82 = new SU82();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", null);
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        assertThrows(TransactionException.class, () -> su82.executeTrx(client, parametersExecute));
    }


    @Test
    void testExecuteTrx3() throws TransactionException {
        SU82 su82 = new SU82();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", null);
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        assertThrows(TransactionException.class, () -> su82.executeTrx(client, parametersExecute));
    }


    @Test
    void testExecuteTrx4() throws TransactionException {
        SU82 su82 = new SU82();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", null);
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        assertThrows(TransactionException.class, () -> su82.executeTrx(client, parametersExecute));
    }



    @Test
    void testMapData() throws Exception {
        SU82 su82 = new SU82();

        List lista = new ArrayList<>();

        DynaProperty[] dynaProperties = new DynaProperty[1];
        dynaProperties[0] = new DynaProperty("lista", String.class);

        BasicDynaBean bean = new BasicDynaBean(new BasicDynaClass("", null, dynaProperties));
        bean.set("lista", StringUtils.repeat("12345",60));

        lista.add(bean);

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", null);
        parametersExecute.put((String) "lista", lista);
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        parametersExecute.put((String) "vcccost", null);
        su82.mapData(parametersExecute);
        assertFalse(su82.getDataReturnList().isEmpty());
    }


    @Test
    void testMapData3() throws Exception {
        SU82 su82 = new SU82();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", "42");
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        parametersExecute.put((String) "vcccost", null);
        su82.mapData(parametersExecute);
        assertEquals(1, su82.getDataReturnList().size());
    }


    @Test
    void testMapData4() throws Exception {
        SU82 su82 = new SU82();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", "42");
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        parametersExecute.put((String) "vcccost", "42");
        su82.mapData(parametersExecute);
        assertEquals(1, su82.getDataReturnList().size());
    }



    @Test
    void testMapData5() throws Exception {
        SU82 su82 = new SU82();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "cod_motivo", "");
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "estado", "foo");
        parametersExecute.put((String) "desc_motivo", "foo");
        parametersExecute.put((String) "idglg", "foo");
        parametersExecute.put((String) "aprglg", "foo");
        parametersExecute.put((String) "ccosto", "foo");
        parametersExecute.put((String) "inclexc", "foo");
        parametersExecute.put((String) "codsup", "foo");
        parametersExecute.put((String) "firma", "foo");
        parametersExecute.put((String) "maviso", "foo");
        parametersExecute.put((String) "oscar", "foo");
        parametersExecute.put((String) "ncarga", "foo");
        parametersExecute.put((String) "nautor", "foo");
        parametersExecute.put((String) "txaviso", "foo");
        parametersExecute.put((String) "oespe", "foo");
        parametersExecute.put((String) "dinterv", (Object) "000000000");
        parametersExecute.put((String) "fdesde", (Object) "");
        parametersExecute.put((String) "fhasta", (Object) "");
        parametersExecute.put((String) "vcccost", null);
        su82.mapData(parametersExecute);
        assertTrue(su82.getDataReturnList().isEmpty());
    }
}

