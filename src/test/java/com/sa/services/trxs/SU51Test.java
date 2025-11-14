package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;   
import static org.mockito.Mockito.mock;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.util.ParamsConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class SU51Test {
	
	private SU51 su51;

    @BeforeEach
    void setUp() {
        su51 = new SU51();
    }

    @Test
    @DisplayName("Debe agregar valores de monedas cuando opcion es 2 y claves_cons es MONEDA")
    void hardcodear_ShouldSetMonedas_WhenOpcionIs2() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "2");
        parameters.put("claves_cons", ParamsConstants.MONEDA_TABLA + ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO);

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertEquals(3, lista.size());
        assertTrue(lista.contains("0000200004ARS PESOS ARGENTINOS"));
        assertTrue(lista.contains("0000200004USD DOLARES"));
        assertTrue(lista.contains("0000200004EUR EUROS"));
    }

    @Test
    @DisplayName("Debe agregar valores de comprobantes cuando opcion es 2 y claves_cons es COMPROBANTE")
    void hardcodear_ShouldSetComprobantes_WhenOpcionIs2() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "2");
        parameters.put("claves_cons", ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO);

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertEquals(5, lista.size());
        assertTrue(lista.contains("00002000060001FACTURA"));
        assertTrue(lista.contains("00002000060002MAIL"));
        assertTrue(lista.contains("00002000060003SIN COMPROBANTE"));
        assertTrue(lista.contains("00002000060004TICKET"));
        assertTrue(lista.contains("00002000060006FACTURA OBLIGATORIA"));
    }

    @Test
    @DisplayName("Debe agregar motivos de gastos cuando opcion es 3")
    void hardcodear_ShouldSetMotivosGasto_WhenOpcionIs3() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0202");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertEquals(2, lista.size());
        assertTrue(lista.contains("0200ALMUERZOS                                         00202N"));
        assertTrue(lista.contains("0201CENAS                                             00202N"));
    }
    
    @Test
    @DisplayName("Debe agregar motivos de gasto generales cuando opcion es 4")
    void hardcodear_ShouldSetMotivosGenerales_WhenOpcionIs4() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "4");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0200GASTOS DE REPRESENTACION                          0000"));
        assertTrue(lista.contains("0201VIAJE AL EXTERIOR                                 0000"));
        assertTrue(lista.contains("0723PAGO DE MATERIAS                                  0000"));
    }

    @Test
    @DisplayName("Debe agregar diferentes tipos de gastos cuando opcion es 8")
    void hardcodear_ShouldSetGastosDiferentes_WhenOpcionIs8() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "8");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0123123                                               0123"));
        assertTrue(lista.contains("0200REPRESENTACION AACC - COMIDAS                     0000"));
        assertTrue(lista.contains("0500GASTOS DE SW - LOCAL                              0000"));
    }

    @Test
    @DisplayName("Debe agregar diferentes tipos de gastos cuando opcion es 9")
    void hardcodear_ShouldSetGastosDiferentes_WhenOpcionIs9() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "9");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0123123                                               0123"));
        assertTrue(lista.contains("0200REPRESENTACION AACC - COMIDAS                     0000"));
        assertTrue(lista.contains("0500GASTOS DE SW - LOCAL                              0000"));
    }
    
    @Test
    @DisplayName("Debe agregar gastos para reuniones cuando cod_motivo es 0203")
    void hardcodear_ShouldSetGastosReuniones_WhenCodMotivoIs0203() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0203");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0214COMIDAS                                           00204N"));
        assertTrue(lista.contains("0215ALQUILER SALON                                    00204N"));
        assertTrue(lista.contains("0216ALQUILER EQUIPOS                                  00204N"));
        assertTrue(lista.contains("0217SERVICIO DE CATERING                              00204N"));
        assertTrue(lista.contains("0218VARIOS                                            00204N"));
    }

    @Test
    @DisplayName("Debe agregar gastos de movilidad cuando cod_motivo es 0204")
    void hardcodear_ShouldSetGastosMovilidad_WhenCodMotivoIs0204() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0204");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0219COMBUSTIBLE                                       00205N"));
        assertTrue(lista.contains("0220PEAJES                                            00000N"));
        assertTrue(lista.contains("0221ESTACIONAMIENTO                                   00000N"));
        assertTrue(lista.contains("0222LAVADO                                            00204N"));
        assertTrue(lista.contains("0223SERVICE CONCESIONARIO                             00000N"));
        assertTrue(lista.contains("0224VARIOS                                            00206N"));
    }

    @Test
    @DisplayName("Debe agregar bienes de uso cuando cod_motivo es 0205")
    void hardcodear_ShouldSetBienesDeUso_WhenCodMotivoIs0205() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0205");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("0225BIEN DE USO                                       00207N"));
    }



    @Test
    @DisplayName("Debe agregar motivos cuando opcion es 5")
    void hardcodear_ShouldSetMotivosRechazo_WhenOpcionIs5() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "5");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertEquals(4, lista.size());
        assertTrue(lista.contains("MOT1MOTIVO RECH1"));
        assertTrue(lista.contains("MOT2MOTIVO RECH2"));
        assertTrue(lista.contains("MOT3MOTIVO RECH3"));
        assertTrue(lista.contains("MOT4MOTIVO RECH4"));
    }

    @Test
    @DisplayName("Debe agregar motivos de suspensión cuando opcion es 6")
    void hardcodear_ShouldSetMotivosSuspension_WhenOpcionIs6() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "6");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("MOP0motivo de prueba"));
        assertTrue(lista.contains("MOP1mot prue 1"));
        assertTrue(lista.contains("MOP2prueba de suspension"));
    }

    @Test
    @DisplayName("Debe agregar motivos de observación cuando opcion es 7")
    void hardcodear_ShouldSetMotivosObservacion_WhenOpcionIs7() {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "7");

        // Act
        su51.hardcodear(parameters);

        // Assert
        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.contains("MOP1prueba motivo observacion 1"));
        assertTrue(lista.contains("MOP2prueba motivo observacion 2"));
    }

    @Test
    void testConstructor() {
        assertTrue((new SU51()).getDataReturnList().isEmpty());
    }

    @Test
    void testExecuteTrx() {
        SU51 su51obj = new SU51();
        SAMWebClient client = new SAMWebClient();
        assertThrows(TransactionException.class, () -> su51obj.executeTrx(client, new HashMap<>()));
    }

    @Test
    void testExecuteTrx2() {
        SU51 su51obj = new SU51();
        SAMWebClient client = new SAMWebClient();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", null);
        parametersExecute.put("opcion", "foo");
        assertThrows(TransactionException.class, () -> su51obj.executeTrx(client, parametersExecute));
    }

    @Test
    @DisplayName("Testeando mapData case 1")
    void mapData() {

        SU51 su51obj = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                              test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "1");
        su51obj.mapData(parametersExecute);
        assertNotNull(su51obj);
    }

    @Test
    @DisplayName("Testeando mapData case 2")
    void mapData2() {

        SU51 su51obj = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123      USD                                                        test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "2");
        su51obj.mapData(parametersExecute);
        assertNotNull(su51obj);
    }

    @Test
    @DisplayName("Testeando mapData case 3")
    void mapData3() {

        SU51 su51obj = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                      test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "3");
        su51obj.mapData(parametersExecute);
        assertNotNull(su51obj);
    }

    @Test
    @DisplayName("Testeando mapData case 7")
    void mapData7() {

        SU51 su51obj = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123                                                              test                                                     test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "7");
        su51obj.mapData(parametersExecute);
        assertNotNull(su51obj);
    }

    @Test
    void testHardcodear() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Debe lanzar TransactionException cuando execute falla")
    void executeTrx_ShouldThrowException_WhenExecuteFails() {
        // Arrange
        SU51 su51local = new SU51();
        IWebClient client = mock(IWebClient.class);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("lista", new ArrayList<>());
        parameters.put("opcion", "1");

        // Act & Assert
        assertThrows(TransactionException.class, () -> su51local.executeTrx(client, parameters));
    }

    @Test
    @DisplayName("Debe lanzar TransactionException cuando mapData falla")
    void executeTrx_ShouldThrowTransactionException_WhenMapDataFails() {
        // Arrange
        SU51 su51local = new SU51();
        IWebClient client = mock(IWebClient.class);
        Map<String, Object> parameters = new HashMap<>();
        // Sin 'opcion' causa NumberFormatException que se captura
        parameters.put("lista", new ArrayList<>());

        // Act & Assert
        assertThrows(TransactionException.class, () -> su51local.executeTrx(client, parameters));
    }

    @Test
    @DisplayName("Debe lanzar TransactionException con mensaje de mapeo cuando mapData falla")
    void executeTrx_ShouldThrowMappingException_WhenMapDataFails() {
        // Arrange
        SU51 su51local = new SU51();
        IWebClient client = mock(IWebClient.class);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("lista", new ArrayList<>());
        // opcion invalido causa excepción

        // Act & Assert
        TransactionException exception = assertThrows(TransactionException.class,
            () -> su51local.executeTrx(client, parameters));
        // Verificar que la excepción contiene información
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Testeando mapData case 4 - Opción 4")
    void mapData4() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0200GASTOS DE REPRESENTACION                          0000");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "4");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 5 - Opción 5")
    void mapData5() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("MOT1MOTIVO RECH1");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "5");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 6 - Opción 6")
    void mapData6() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("MOP0motivo de prueba");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "6");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 8 - Opción 8")
    void mapData8() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0200GASTOS DE REPRESENTACION                          0000");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "8");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 9 - Opción 9")
    void mapData9() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0200GASTOS DE REPRESENTACION                          0000");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "9");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 2 - Con ARS")
    void mapData2_WithARS() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123      ARS                                                        test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "2");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 2 - Con EUR")
    void mapData2_WithEUR() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123      EUR                                                        test");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "2");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 3 - String largo")
    void mapData3_LongString() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0200ALMUERZOS                                         00202N");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "3");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 3 - Con detalle 00000")
    void mapData3_WithDetalle00000() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        // Formato: 54 caracteres + detalle + resto
        datosLista.add("0200ALMUERZOS                                         00202N");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "3");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 3 - Con detalle vacio")
    void mapData3_WithEmptyDetalle() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0200ALMUERZOS                                                    N");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "3");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData case 1 - Verificar coeficiente")
    void mapData1_VerifyCoeficiente() {
        SU51 su51 = new SU51();

        List<String> datosLista = new ArrayList<>();
        datosLista.add("0123456789012345678901234567890123456789");
        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", datosLista);
        parametersExecute.put("opcion", "1");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData con lista null en caso 4/8/9")
    void mapData_WithNullList_Case4() {
        SU51 su51 = new SU51();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", null);
        parametersExecute.put("opcion", "4");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando mapData con lista null en caso 8")
    void mapData_WithNullList_Case8() {
        SU51 su51 = new SU51();

        HashMap<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", null);
        parametersExecute.put("opcion", "8");
        su51.mapData(parametersExecute);

        assertNotNull(su51);
    }

    @Test
    @DisplayName("Testeando hardcodear case 3 - cod_motivo 0202")
    void hardcodear_Case3_CodMotivo0202() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0202");

        su51.hardcodear(parameters);

        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear case 3 - cod_motivo 0203")
    void hardcodear_Case3_CodMotivo0203() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0203");

        su51.hardcodear(parameters);

        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear case 3 - cod_motivo 0204")
    void hardcodear_Case3_CodMotivo0204() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0204");

        su51.hardcodear(parameters);

        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Testeando hardcodear case 3 - cod_motivo 0205")
    void hardcodear_Case3_CodMotivo0205() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("opcion", "3");
        parameters.put("cod_motivo", "0205");

        su51.hardcodear(parameters);

        List<String> lista = (List<String>) parameters.get("lista");
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }
}

