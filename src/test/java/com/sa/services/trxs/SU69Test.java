package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SU69Test {


    @Test
    void testConstructor() throws Exception {
        SU69 actualSu69 = new SU69();
        actualSu69.hardcodear(new HashMap<>());
        assertTrue(actualSu69.getDataReturnList().isEmpty());
    }


    @Test
    void testConstructor2() {
        assertTrue((new SU69()).getDataReturnList().isEmpty());
    }


    @Test
    void testExecuteTrx() throws TransactionException {
        SU69 su69 = new SU69();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su69.executeTrx(client, new HashMap<>()));
    }


    @Test
    void testExecuteTrx2() throws TransactionException {
        SU69 su69 = new SU69();
        assertThrows(TransactionException.class, () -> su69.executeTrx(null, new HashMap<>()));
    }


    @Test
    void testExecuteTrx3() throws TransactionException {
        SU69 su69 = new SU69();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su69.executeTrx(client, parametersExecute));
    }


    @ParameterizedTest
    @MethodSource("mapDataSource")
    @DisplayName("Testeando mapData")
    void testMapData(String subtran,String ch) throws Exception {

        List lista = new ArrayList();

        DynaProperty[] dynaProperties = new DynaProperty[1];
        dynaProperties[0] = new DynaProperty("lista", String.class);

        BasicDynaBean bean = new BasicDynaBean(new BasicDynaClass("", null, dynaProperties));
        bean.set("lista", "01-01-2023" + StringUtils.repeat("1",80)+ch);

        lista.add(bean);

        Map<String, Object> parametersExecute = new HashMap<>();

        parametersExecute.put("lista", lista);
        parametersExecute.put("subtran", subtran);


        SU69 su69 = new SU69();
        su69.mapData(parametersExecute);

        assertTrue(su69.getDataReturnList().size() == 1);
    }

    // ------ Sources ------

    private static Stream<Arguments> mapDataSource() {
        String subtran = "FEC";
        String subtran2 = "subtran";
        String ch = "P";
        String ch2 = "D";
        String ch3 = "R";
        String ch4 = "E";
        String ch5 = "A";

        return Stream.of(
            Arguments.of(subtran,ch),
            Arguments.of(subtran2,ch),
            Arguments.of(subtran2,ch2),
            Arguments.of(subtran2,ch3),
            Arguments.of(subtran2,ch4),
            Arguments.of(subtran2,ch5)
        );
    }
}

