package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Cupones;
import com.sa.entities.Gastos;

import java.text.ParseException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SU55Test {

    /*@Test
    void testConstructor() throws Exception {
        SU55 actualSu55 = new SU55();
        actualSu55.hardcodear(new HashMap<>());
        assertTrue(actualSu55.listaCupones.isEmpty());
        assertTrue(actualSu55.listaGastos.isEmpty());
        assertTrue(actualSu55.listaGastosRedistribuidos.isEmpty());
    }*/

    @Test
    void testConstructor2() {
        SU55 actualSu55 = new SU55();
        assertTrue(actualSu55.listaGastosRedistribuidos.isEmpty());
        assertTrue(actualSu55.listaGastos.isEmpty());
        assertTrue(actualSu55.listaCupones.isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SU55 su55 = new SU55();
        SAMWebClient client = new SAMWebClient();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "lista2", null);
        parametersExecute.put((String) "id_rendicion", "foo");
        parametersExecute.put((String) "cod_motivo", "foo");
        parametersExecute.put((String) "centro_costo", "foo");
        parametersExecute.put((String) "DERRAME", "42");
        assertThrows(TransactionException.class, () -> su55.executeTrx(client, parametersExecute));
    }


    /*@Test
    @DisplayName("Testeando setInforGasto")
    void setInfoGasto() throws ParseException {

        SU55 su55 = new SU55();
        Gastos gasto = new Gastos();
        su55.setInfoGasto("000000000000234 56789543256799863534525767898579036256256578578  987689765674564345 2023-1-1 6856675432421645469345735737222454236684567978455342154568098765645343134254 2657655637376356563468467835686 1234435654457658967953417897442341312  34532341414367567859694334690 97645647786521346876505345765546546546546097123", gasto, new HashMap<>());

    }*/

    @Test
    @DisplayName("Testeando mapData con Lista2 con datos")
    void testMapDataLista2Llena() {
        SU55 su55 = new SU55();
        List<String> datosLista2 = new ArrayList<>();
        datosLista2.add("2023-1-1  283945678897 2345678564326789532567534974120 34528987653267890");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", new ArrayList<>());
        parametersExecute.put((String) "lista2", datosLista2);
        parametersExecute.put((String) "id_rendicion", "foo");
        parametersExecute.put((String) "cod_motivo", "foo");
        parametersExecute.put((String) "centro_costo", "foo");
        parametersExecute.put((String) "DERRAME", "42");
        su55.mapData(parametersExecute);
        assertEquals(5, parametersExecute.size());
    }

    @Test
    @DisplayName("Testeando mapData con Lista2 siendo null")
    void testMapDataLista2Null() {

        SU55 su55 = new SU55();
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("Mapeo SU55");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", objectList);
        parametersExecute.put((String) "lista2", null);
        parametersExecute.put((String) "id_rendicion", "foo");
        parametersExecute.put((String) "cod_motivo", "foo");
        parametersExecute.put((String) "centro_costo", "foo");
        parametersExecute.put((String) "DERRAME", "42");
        su55.mapData(parametersExecute);
    }

    @Test
    @DisplayName("Testeando getDataReturnList")
    void testGetDataReturnList2() {

        SU55 su55 = new SU55();

        List actualDataReturnList = su55.getDataReturnList();

        assertTrue(actualDataReturnList.isEmpty());
    }

}

