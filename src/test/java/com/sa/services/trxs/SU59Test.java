package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboGenerico;
import com.sa.entities.DatosPantallaDinamica;

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

class SU59Test {

    @Test
    void testConstructor() throws Exception {
        SU59 actualSu59 = new SU59(new ArrayList<>());
        actualSu59.hardcodear(new HashMap<>());
        assertTrue(actualSu59.getDataReturnList().isEmpty());
    }

    @Test
    void testConstructor2() {
        assertTrue((new SU59(new ArrayList<>())).getDataReturnList().isEmpty());
    }

    @Test
    void testConstructor5() {
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.setTipoCampo("TM_WSSOACON");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        assertTrue((new SU59(fieldsScreen)).getDataReturnList().isEmpty());
    }


    @Test
    void testConstructor7() {
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.setTipoCampo("COD1");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        assertTrue((new SU59(fieldsScreen)).getDataReturnList().isEmpty());
    }

    @Test
    void testConstructor8() {
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.setTipoCampo("COD2");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        assertTrue((new SU59(fieldsScreen)).getDataReturnList().isEmpty());
    }

    @Test
    void testConstructor9() {
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.addOpcionCombo(new ComboGenerico("42", "TM_WSSOACON"));
        datosPantallaDinamica.setTipoCampo("COD1");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        assertTrue((new SU59(fieldsScreen)).getDataReturnList().isEmpty());
    }


    @Test
    void testConstructor10() {
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.addOpcionCombo(new ComboGenerico("42", "TM_WSSOACON"));
        datosPantallaDinamica.setTipoCampo("COD2");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        assertTrue((new SU59(fieldsScreen)).getDataReturnList().isEmpty());
    }

    @Test
    void testExecuteTrx() throws TransactionException {
        SU59 su59 = new SU59(new ArrayList<>());
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su59.executeTrx(client, new HashMap<>()));
    }


    @Test
    void testExecuteTrx2() throws TransactionException {
        SU59 su59 = new SU59(new ArrayList<>());
        su59.addColumns(new ArrayList<>(), "TM_WSSOACON");
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su59.executeTrx(client, new HashMap<>()));
    }


    @Test
    void testExecuteTrx3() throws TransactionException {
        SU59 su59 = new SU59(new ArrayList<>());
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su59.executeTrx(client, parametersExecute));
    }


    @Test
    void testAddColumns() {
        List<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.setTipoCampo("COD1");

        DatosPantallaDinamica datosPantallaDinamica2 = new DatosPantallaDinamica();
        datosPantallaDinamica2.setTipoCampo("COD2");

        DatosPantallaDinamica datosPantallaDinamica3 = new DatosPantallaDinamica();
        datosPantallaDinamica3.setTipoCampo("TXT1");

        DatosPantallaDinamica datosPantallaDinamica4 = new DatosPantallaDinamica();
        datosPantallaDinamica4.setTipoCampo("TXT2");

        DatosPantallaDinamica datosPantallaDinamica5 = new DatosPantallaDinamica();
        datosPantallaDinamica5.setTipoCampo("NUM1");

        DatosPantallaDinamica datosPantallaDinamica6 = new DatosPantallaDinamica();
        datosPantallaDinamica6.setTipoCampo("NUM2");

        fieldsScreen.add(datosPantallaDinamica);
        fieldsScreen.add(datosPantallaDinamica2);
        fieldsScreen.add(datosPantallaDinamica3);
        fieldsScreen.add(datosPantallaDinamica4);
        fieldsScreen.add(datosPantallaDinamica5);
        fieldsScreen.add(datosPantallaDinamica6);

        List<String> columnas = new ArrayList<>();
        String string = StringUtils.repeat("1",150);

        SU59 su59 = new SU59(fieldsScreen);
        su59.addColumns(columnas, string);
        assertTrue(su59.getDataReturnList().isEmpty());
    }

    @Test
    void testMapData() {
        List lista = new ArrayList();
        Map<String, Object> parametersExecute = new HashMap<>();

        DynaProperty[] dynaProperties = new DynaProperty[1];
        dynaProperties[0] = new DynaProperty("lista", String.class);

        BasicDynaBean bean = new BasicDynaBean(new BasicDynaClass("", null, dynaProperties));
        bean.set("lista", StringUtils.repeat("12345",60));

        lista.add(bean);

        parametersExecute.put("lista",lista);

        List<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        DatosPantallaDinamica datosPantallaDinamica = new DatosPantallaDinamica();
        datosPantallaDinamica.setTipoCampo("FEC1");

        DatosPantallaDinamica datosPantallaDinamica2 = new DatosPantallaDinamica();
        datosPantallaDinamica2.setTipoCampo("FEC2");

        DatosPantallaDinamica datosPantallaDinamica3 = new DatosPantallaDinamica();
        datosPantallaDinamica3.setTipoCampo("TXT250");

        fieldsScreen.add(datosPantallaDinamica);
        fieldsScreen.add(datosPantallaDinamica2);
        fieldsScreen.add(datosPantallaDinamica3);

        SU59 su59 = new SU59(fieldsScreen);
        su59.mapData(parametersExecute);
        assertFalse(su59.getDataReturnList().isEmpty());
    }


    @Test
    void testMapData2() {
        DatosPantallaDinamica datosPantallaDinamica = mock(DatosPantallaDinamica.class);
        when(datosPantallaDinamica.getTipoCampo()).thenReturn("Tipo Campo");

        ArrayList<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        fieldsScreen.add(datosPantallaDinamica);
        SU59 su59 = new SU59(fieldsScreen);
        su59.mapData(new HashMap<>());
        verify(datosPantallaDinamica, atLeast(1)).getTipoCampo();
    }
}

