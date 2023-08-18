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
    @Disabled("TODO: Complete this test")
    void testMapData() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.sa.services.trxs.SU82.mapDataWithoutCodMotivo(SU82.java:65)
        //       at com.sa.services.trxs.SU82.mapData(SU82.java:51)
        //   See https://diff.blue/R013 to resolve this issue.

        SU82 su82 = new SU82();
        su82.mapData(new HashMap<>());
    }


    @Test
    void testMapData2() throws Exception {
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
    @Disabled("TODO: Complete this test")
    void testMapData5() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.sa.entities.OSCAR.<init>(OSCAR.java:19)
        //       at com.sa.services.trxs.SU82.mapDataWithCodMotivo(SU82.java:95)
        //       at com.sa.services.trxs.SU82.mapData(SU82.java:53)
        //   See https://diff.blue/R013 to resolve this issue.

        SU82 su82 = new SU82();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("cod_motivo", "42");
        su82.mapData(parametersExecute);
    }


    @Test
    void testMapData6() throws Exception {
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

