package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import java.util.*;
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

class SU57Test {

    @Spy
    SU57 su57;


    @BeforeEach
    public void setup() {
        su57 = new SU57() {
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
    void testConstructor() {
        assertTrue((new SU57()).getDataReturnList().isEmpty());
    }

    @Test
    @DisplayName("Testeando executeTrx")
    void executeTrx() throws TransactionException {
        SAMWebClient client = new SAMWebClient();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("COD1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");


        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        parametersExecute.put((String) "lista", datosLista);
        su57.executeTrx(client, parametersExecute);
        assertNotNull(parametersExecute);
    }

    @Test
    @DisplayName("Testeando executeTrx Exception")
    void executeTrxException() throws TransactionException {
        SU57 su57 = new SU57();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        assertThrows(TransactionException.class, () -> su57.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando executeTrx Exception 2")
    void executeTrxException2() throws TransactionException {
        SAMWebClient client = new SAMWebClient();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("COD1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");


        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("TM_WSSOACON", "42");
        parametersExecute.put((String) "lista", 1);
        assertThrows(TransactionException.class, () -> su57.executeTrx(client, parametersExecute));
    }

    @ParameterizedTest
    @MethodSource("mapDataSource")
    @DisplayName("Testeando mapData")
    void mapData(HashMap<String, Object> parametersExecute){
        SU57 su57 = new SU57();

        su57.mapData(parametersExecute);

        assertNotNull(su57);
    }

    @Test
    @DisplayName("Testeando mapData Exception")
    void mapDataException(){
        SU57 su57 = new SU57();
        List<String> datosLista = new ArrayList<>();
        datosLista.add("COD1           S ");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);

        su57.mapData(parametersExecute);

        assertNotNull(su57);
    }

    @Test
    @DisplayName("Testeando hardcodear")
    void hardcodear() throws Exception {
        SU57 su57 = new SU57();
        HashMap<String, Object> parametersExecute = new HashMap<>();
        su57.hardcodear(parametersExecute);
        assertEquals(1, parametersExecute.size());
        Object getResult = parametersExecute.get("lista");
        assertEquals(4, ((Collection<String>) getResult).size());
        assertEquals("TXT1           SSAPELLIDO/NOMBRE", ((List<String>) getResult).get(0));
        assertEquals("NUM1           SSDNI", ((List<String>) getResult).get(1));
        assertEquals("FEC1           SSFEC.NACIM.", ((List<String>) getResult).get(2));
        assertEquals("TXT2           SSINSTITUCION", ((List<String>) getResult).get(3));
    }

    // ------ Sources ------

    private static Stream<Arguments> mapDataSource() {
        List<String> datosLista = new ArrayList<>();
        datosLista.add("COD1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");
        datosLista.add("COA1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put((String) "lista", datosLista);

        return Stream.of(
            Arguments.of(parametersExecute)
        );
    }
}

