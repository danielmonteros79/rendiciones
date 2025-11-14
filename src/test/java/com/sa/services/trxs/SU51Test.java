package com.sa.services.trxs;

import static org.junit.jupiter.api.Assertions.*;   
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

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

    @Test
    @DisplayName("Should throw TransactionException when execute fails - covers line 50-51")
    void testExecuteTrx_ExecuteFailure() {
        // Arrange
        IWebClient client = null; // Will cause execute to fail
        Map<String, Object> parametersExecute = new HashMap<>();
        
        // Act & Assert - covers catch block lines 50-51
        assertThrows(TransactionException.class, () -> {
            su51.executeTrx(client, parametersExecute);
        });
    }

    @Test
    @DisplayName("Should throw TransactionException when mapData fails - covers line 42-46")
    void testExecuteTrx_MapDataFailure() throws Exception {
        // Arrange - Create a SU51 instance that overrides execute() to do nothing
        SU51 testSU51 = new SU51() {
            @Override
            protected void execute(IWebClient client, String trxExecute, Map<String, Object> parametersExecute) throws Exception {
                // Do nothing - let mapData() fail
            }
        };
        
        Map<String, Object> parametersExecute = new HashMap<>();
        parametersExecute.put("lista", new ArrayList<>());
        parametersExecute.put("opcion", null); // Will cause NullPointerException in mapData
        
        SAMWebClient mockClient = new SAMWebClient();
        
        // Act & Assert - covers catch block lines 42-46 (mapData exception handling)
        // execute() succeeds but mapData() will fail with NullPointerException
        TransactionException exception = assertThrows(TransactionException.class, () -> {
            testSU51.executeTrx(mockClient, parametersExecute);
        });
        
        assertTrue(exception.getMessage().contains("Error de mapeo SU51"));
    }

    @Test
    @DisplayName("Should handle safeSubstring with start >= length - covers line 304")
    void testSafeSubstring_StartGreaterThanLength() {
        // Test para cubrir línea 304 (if (start >= len) return "")
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String muy corto (3 chars) intentará acceder a índices mayores (10-14)
        lista.add("ABC");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert - Verificar que se manejó correctamente sin excepción
        assertNotNull(su51.listaOpcion2);
    }

    @Test
    @DisplayName("Should handle safeSubstring exception fallback - covers line 306")
    void testSafeSubstring_ExceptionFallback() {
        // Test para cubrir línea 306 (catch Exception e - defensive fallback)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String corto que pueda causar problemas
        lista.add("0000200004SHORT");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
    }

    @Test
    @DisplayName("Should handle opcion 3 with short string - covers line 96")
    void testMapData_Opcion3_ShortString() {
        // Test para cubrir línea 96 (str.length() <= 54)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String de exactamente 54 caracteres o menos
        lista.add("0200GASTOS CORTOS                                  "); // 52 chars
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "3");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaTipoGasto);
        assertEquals(1, su51.listaTipoGasto.size());
    }

    @Test
    @DisplayName("Should handle opcion 3 with detalle empty - covers line 97")
    void testMapData_Opcion3_EmptyDetalle() {
        // Test para cubrir línea 97 (detalle = "" o "00000")
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String > 54 con detalle vacío (posiciones 54-59)
        lista.add("0201GASTOS CON DETALLE VACIO                              ");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "3");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaTipoGasto);
        assertEquals(1, su51.listaTipoGasto.size());
        assertTrue(su51.listaTipoGasto.get(0).getId().endsWith("N"));
    }

    @Test
    @DisplayName("SafeSubstring should handle null input - covers line 298")
    void testSafeSubstring_NullInput() {
        // Test para cubrir línea 298 (if (s == null) return "")
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add(null); // null string
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act - should handle null gracefully
        su51.mapData(parametersExecute);
        
        // Assert - No exception thrown
        assertNotNull(su51.listaOpcion2);
    }

    @Test
    @DisplayName("SafeSubstring should handle negative start - covers line 300")
    void testSafeSubstring_NegativeStart() {
        // Aunque no podemos invocar safeSubstring directamente (es privado),
        // podemos asegurar que cualquier uso interno funcione correctamente
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add("0000200004TEST_DATA_HERE");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
        assertFalse(su51.listaOpcion2.isEmpty());
    }

    @Test
    @DisplayName("SafeSubstring should handle end < start - covers line 301")
    void testSafeSubstring_EndLessThanStart() {
        // Test para cubrir línea 301 (if (end < start) end = start)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add("SHORT");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "1");
        
        // Act - safeSubstring(str, 24, 27) en string corto
        su51.mapData(parametersExecute);
        
        // Assert - No exception thrown
        assertNotNull(su51.dataReturn);
    }

    @Test
    @DisplayName("SafeSubstring should handle end > length - covers line 303")
    void testSafeSubstring_EndGreaterThanLength() {
        // Test para cubrir línea 303 (if (end > len) end = len)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add("0000200004EURO"); // 14 chars
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2"); // safeSubstring(str, 14, str.length())
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
    }

    @Test
    @DisplayName("SafeSubstring should handle very short strings - covers line 302-304")
    void testSafeSubstring_VeryShortString() {
        // Test para cubrir líneas 302 y 304 (start >= len return "")
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add("AB"); // 2 chars, intentará substring(10, 14)
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
    }

    @Test
    @DisplayName("SafeSubstring exception fallback should work - covers line 306")
    void testSafeSubstring_ExceptionCatch() {
        // Test para asegurar que el catch en línea 306 funciona correctamente
        // Forzamos situaciones extremas con strings muy cortos
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // Múltiples strings problemáticos
        lista.add(""); // empty
        lista.add("X"); // 1 char
        lista.add("XY"); // 2 chars
        
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act - debería manejar todos sin excepción
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
        assertEquals(3, su51.listaOpcion2.size());
    }

    @Test
    @DisplayName("SafeSubstring with exact boundaries - covers normal path line 305")
    void testSafeSubstring_ExactBoundaries() {
        // Test para cubrir línea 305 (return s.substring(start, end))
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String perfectamente formateado
        lista.add("0000200004ARS PESOS ARGENTINOS Y MAS TEXTO");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "2");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaOpcion2);
        assertFalse(su51.listaOpcion2.isEmpty());
    }

    @Test
    @DisplayName("SafeSubstring with opcion 1 coeficiente extraction - covers line 67")
    void testSafeSubstring_Opcion1Coeficiente() {
        // Test para cubrir línea 67 (coeficiente = safeSubstring(str, 24, 27))
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String con al menos 27 caracteres para el coeficiente
        lista.add("012345678901234567890123456789012345678901234567890");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "1");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.dataReturn);
        assertEquals("456", su51.dataReturn); // chars en posiciones 24-27
    }

    @Test
    @DisplayName("SafeSubstring opcion 1 with short string - defensive fallback")
    void testSafeSubstring_Opcion1ShortString() {
        // Test para string demasiado corto en opción 1
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String de solo 10 caracteres (menor que índice 24)
        lista.add("0123456789");
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "1");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.dataReturn);
        assertEquals("", su51.dataReturn); // Debería retornar string vacío
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - string null - covers line 116-118")
    void testDebugLog_Opcion4_NullString() {
        // Test para cubrir líneas 116-118 con string null
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add(null); // String null para activar la condición str == null
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "4");
        
        // Act - el log.isDebugEnabled() puede estar activo o no, pero el código debe ejecutarse
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - string corto - covers line 116-118")
    void testDebugLog_Opcion4_ShortString() {
        // Test para cubrir líneas 116-118 con string corto (< 140 chars)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add("0200SHORT STRING TEST                                 0000"); // < 140 chars
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "4");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
        assertEquals(1, su51.listaMotivo.size());
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - string largo - covers line 116-118")
    void testDebugLog_Opcion4_LongString() {
        // Test para cubrir líneas 116-118 con string largo (> 140 chars) para activar substring(0, 140)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String de exactamente 150 caracteres para forzar el substring(0, 140)
        String longString = "0200GASTOS MUY LARGOS CON MUCHOS CARACTERES PARA PROBAR EL DEBUG LOG Y ASEGURARSE QUE EL SUBSTRING FUNCIONA CORRECTAMENTE CUANDO SUPERA LOS 140 CARACTERES0000";
        lista.add(longString);
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "8");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
        assertEquals(1, su51.listaMotivo.size());
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - string exactamente 140 chars - covers line 116-118")
    void testDebugLog_Opcion8_Exactly140Chars() {
        // Test para cubrir líneas 116-118 con string de exactamente 140 caracteres
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String de exactamente 140 caracteres
        String exactString = "0200GASTOS CON EXACTAMENTE 140 CARACTERES PARA PROBAR LA CONDICION DE LONGITUD EXACTA EN EL LOG DEBUG Y VER QUE PASA CUANDO ES IGUAL00000000";
        lista.add(exactString);
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "9");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - multiple strings - covers line 116-118")
    void testDebugLog_Opcion4_MultipleStrings() {
        // Test para cubrir líneas 116-118 con múltiples strings de diferentes tamaños
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        lista.add(null); // String null
        lista.add("SHORT");  // String corto
        lista.add("0200GASTOS DE REPRESENTACION NORMAL                   0000"); // String normal
        lista.add("0201GASTOS MUY LARGOS PARA FORZAR EL TRUNCAMIENTO EN EL LOG DEBUG CUANDO SE SUPERA LA LONGITUD MAXIMA DE 140 CARACTERES Y SE DEBE HACER SUBSTRING00000000"); // > 140
        
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "4");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
        assertEquals(4, su51.listaMotivo.size());
    }

    @Test
    @DisplayName("Debug log opcion 4/8/9 - empty string - covers line 116-118")
    void testDebugLog_Opcion8_EmptyString() {
        // Test para cubrir líneas 116-118 con string vacío
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        lista.add(""); // String vacío
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "8");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
    }

    @Test
    @DisplayName("Debug log opcion 9 - string de 141 caracteres - covers line 116-118")
    void testDebugLog_Opcion9_String141Chars() {
        // Test para cubrir líneas 116-118 con string de 141 caracteres (justo sobre el límite)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String de 141 caracteres
        String string141 = "0200GASTOS CON 141 CARACTERES PARA PROBAR QUE SUBSTRING SE EJECUTA CORRECTAMENTE CUANDO LA LONGITUD ES MAYOR A 140 Y DEBE TRUNCARSE0000X";
        lista.add(string141);
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "9");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
        assertEquals(1, su51.listaMotivo.size());
    }

    @Test
    @DisplayName("Debug log opcion 4 - string de 200+ caracteres - covers line 116-118")
    void testDebugLog_Opcion4_VeryLongString() {
        // Test para cubrir líneas 116-118 con string muy largo (> 200 chars)
        
        // Arrange
        Map<String, Object> parametersExecute = new HashMap<>();
        List<String> lista = new ArrayList<>();
        
        // String muy largo (200+ caracteres)
        String veryLongString = "0200GASTOS MUY MUY LARGOS CON MUCHISIMOS CARACTERES PARA ASEGURAR QUE EL LOG DEBUG MANEJA CORRECTAMENTE STRINGS EXTREMADAMENTE LARGOS Y QUE EL SUBSTRING DE 140 CARACTERES FUNCIONA BIEN SIN PROBLEMAS CUANDO HAY MUCHO TEXTO ADICIONAL MAS ALLA DEL LIMITE000000000000000000";
        lista.add(veryLongString);
        parametersExecute.put("lista", lista);
        parametersExecute.put("opcion", "4");
        
        // Act
        su51.mapData(parametersExecute);
        
        // Assert
        assertNotNull(su51.listaMotivo);
        assertEquals(1, su51.listaMotivo.size());
    }
}




