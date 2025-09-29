package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Cupones;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

class TJCPTest {

    @Test
    void testConstructor() throws Exception {
        TJCP actualTjcp = new TJCP();
        actualTjcp.hardcodear(new HashMap<>());
        assertTrue(actualTjcp.listaCupones.isEmpty());
    }


    @Test
    void testConstructor2() {
        TJCP actualTjcp = new TJCP();
        assertTrue(actualTjcp.listaCupones.isEmpty());
        assertTrue(actualTjcp.getDataReturnList().isEmpty());
    }


    @Test
    void testExecuteTrx() throws TransactionException {
        TJCP tjcp = new TJCP();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> tjcp.executeTrx(client, (Map) new HashMap<>()));
    }


    @Test
    void testExecuteTrx2() throws TransactionException {
        TJCP tjcp = new TJCP();
        SAMWebClient client = new SAMWebClient();

        HashMap<Object, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((Object) "lista", null);
        assertThrows(TransactionException.class, () -> tjcp.executeTrx(client, (Map) parametersExecute));
    }


    @Test
    void testExecuteTrx3() throws TransactionException {
        TJCP tjcp = new TJCP();
        SAMWebClient client = new SAMWebClient();

        HashMap<Object, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((Object) "lista", "42");
        assertThrows(TransactionException.class, () -> tjcp.executeTrx(client, (Map) parametersExecute));
    }


    @Test
    void testMapData() {
        TJCP tjcp = new TJCP();
        tjcp.mapData((Map) new HashMap<>());
        List<Cupones> expectedDataReturnList = tjcp.listaCupones;
        assertSame(expectedDataReturnList, tjcp.getDataReturnList());
    }


    @Test
    void testMapData2() {
        TJCP tjcp = new TJCP();

        DynaProperty[] dynaProperties = new DynaProperty[1];
        dynaProperties[0] = new DynaProperty("lista", String.class);

        List lista = new ArrayList();
        BasicDynaBean basicDynaBean = new BasicDynaBean(new BasicDynaClass("", null, dynaProperties));
        basicDynaBean.set("lista", StringUtils.repeat("1234",50));

        lista.add(basicDynaBean);

        HashMap<Object, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((Object) "lista", lista);
        tjcp.mapData((Map) parametersExecute);
        List<Cupones> expectedDataReturnList = tjcp.listaCupones;
        assertSame(expectedDataReturnList, tjcp.getDataReturnList());
    }
}

