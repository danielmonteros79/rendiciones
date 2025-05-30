package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.*;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU56;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

class PagosServiceTest {

    @InjectMocks
    PagosService pagosService;

    @Spy
    SAMWebClient samWebClient;
    
    @Mock
    private ManagerTransaction mockManager;
    
    @Mock
    HttpServletResponse httpServletResponseMocked;

    @Mock
    private SU56 mockSU56;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);        

        mockManager = mock(ManagerTransaction.class);

        // Simular valores por defecto
        when(mockManager.getMensajeAviso()).thenReturn("OK");
        when(mockManager.getDataReturn()).thenReturn(123);
    }

    @ParameterizedTest
    @MethodSource("getComboGastoSource")
    @DisplayName("Testeando get combo gasto")
    void getComboGasto(String opcion,String user,String codMotivo,String msg,List<ComboGasto> comboTipoGastos) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturnList()).thenReturn(comboTipoGastos);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            List<ComboGasto> result = pagosService.getComboGasto(opcion,user,codMotivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(comboTipoGastos,result)
            );
        }
    }

    @Test
    void altaModifGasto() {
    }

    @ParameterizedTest
    @MethodSource("getCuponesSource")
    @DisplayName("Testeando get cupones")
    void getCupones(String opcion, String subTrx, String codapli, String user,
                    String fechaDesde, String fechaHasta, String idRendicion, String codMotivo, String montoMin,String moneda,String msg, List<Cupones> cupones) throws TransactionException {


        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
    		
            when(mockM.getDataReturnList()).thenReturn(cupones);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            List<Cupones> result = pagosService.getCupones( opcion,  subTrx,  codapli,  user,
        			 fechaDesde,  fechaHasta,  idRendicion,  codMotivo,  montoMin,  moneda);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(cupones,result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("asignarCuponSource")
    @DisplayName("Testeando asignar cupon")
    void asignarCupon(String opcion, String idRendicion, String idGasto, String user, String impCuponTj, String nroTarjeta,
                      String nroCuponTj, String cuponDeb, String cuponCred, String descCupon, String monedaCupon, String fechaPresentacion, List<Gastos> gastosRendicion) throws TransactionException {

        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturnList()).thenReturn(gastosRendicion);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            pagosService.asignarCupon(opcion,idRendicion,idGasto,user,impCuponTj,nroTarjeta,nroCuponTj,cuponDeb,cuponCred,descCupon,monedaCupon,fechaPresentacion);

            assertNotNull(gastosRendicion);
        }
    }

    @ParameterizedTest
    @MethodSource("addDescripcionObligatoriaSource")
    @DisplayName("Testeando add descripcion obligatoria")
    void addDescripcionObligatoria(String idRendicion, String idGasto, String codGasto, String codDetOblig, String campoTexto1,
                                   String campoTexto2, String campoNumerico1, String campoNumerico2, String campoCodigo1, String campoCodigo2,
                                   String campoTexto250, String campoFecha1, String campoFecha2) throws TransactionException {

        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            pagosService.addDescripcionObligatoria(idRendicion,idGasto,codGasto,codDetOblig,campoTexto1,campoTexto2,campoNumerico1,campoNumerico2,campoCodigo1,campoCodigo2,campoTexto250,campoFecha1,campoFecha2);

        }
    }

    @ParameterizedTest
    @MethodSource("consultaDetObligatorioSource")
    @DisplayName("Testeando consulta detalle obligatorio")
    void consultaDetObligatorio(String idRendicion, String idGasto, String codMotivo, String codObserv,String msg, List<DatosPantallaDinamica> datosPantallaDinamicas) throws TransactionException {

        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturnList()).thenReturn(datosPantallaDinamicas);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            List<DatosPantallaDinamica> result = pagosService.consultaDetObligatorio(idRendicion,idGasto,codMotivo,codObserv);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(datosPantallaDinamicas,result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("consultaDetallesGastosSource")
    @DisplayName("Testeando consulta detalles gastos")
    void consultaDetallesGastos(String idRendicion, String idGasto, String idObserv,
                                List<DatosPantallaDinamica> fieldsScreen,List<List<String>> res) throws TransactionException {

        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturnList()).thenReturn(res);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            List<List<String>> result = pagosService.consultaDetallesGastos(idRendicion,idGasto,idObserv,fieldsScreen);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(res,result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("bajaGastoSource")
    @DisplayName("Testeando baja gasto")
    void bajaGasto(String idGasto, String user, String idRendicion,String msg,Integer idGastoBorrado) throws TransactionException {

            try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
                doNothing().when(mockM).executeTrx(any(),anyMap());
                when(mockM.getMensajeAviso()).thenReturn(msg);
                when(mockM.getDataReturn()).thenReturn(idGastoBorrado);
            })) {

                PagosService pagosService = new PagosService(samWebClient);
                Integer result = pagosService.bajaGasto(idGasto,user,idRendicion);

                assertAll(
                        () -> assertNotNull(result),
                        () -> assertEquals(idGastoBorrado,result)
                );
            }
    }

    @ParameterizedTest
    @MethodSource("getCuponUnicoSource")
    @DisplayName("Testeando get cupon unico")
    void getCuponUnico(String idRendicion, String idGasto, String idUser, String codMotivo,String msg, List<Cupones> cupones) throws TransactionException {

            try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
                doNothing().when(mockM).executeTrx(any(),anyMap());
                when(mockM.getDataReturnList()).thenReturn(cupones);
                when(mockM.getMensajeAviso()).thenReturn(msg);
            })) {

                PagosService pagosService = new PagosService(samWebClient);
                List<Cupones> result = pagosService.getCuponUnico(idRendicion,idGasto,idUser,codMotivo);

                assertAll(
                        () -> assertNotNull(result),
                        () -> assertEquals(cupones,result)
                );
            }
    }

    @ParameterizedTest
    @Disabled("Terminar")
    @MethodSource("redistribuirGastosSource")
    @DisplayName("Testeando redistribuir gastos")
    void redistribuirGastos(String accion, String idRendicion,
                            String gastoOriginal, String montoItems, String ccostoItems,
                            String gastoItems, Usuario user, String msg) throws TransactionException {

        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            PagosService pagosService = new PagosService(samWebClient);
            String result = pagosService.redistribuirGastos(accion,idRendicion,gastoOriginal,montoItems,ccostoItems,gastoItems,user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(msg,result)
            );
        }

    }

    @Test
    @Disabled("Desabilitado porque no se puede mockear el valor de msg")
    @DisplayName("Testeando get msg")
    void getMsg() {
        PagosService pagosService1 = new PagosService(samWebClient);
        pagosService1.getMsg();
        assertNull(pagosService1.getMsg());
    }

    // ----- Sources -----

    private static Stream<Arguments> getComboGastoSource() {
        String opcion = "opcion";
        String user = "user";
        String codMotivo = "codMotivo";
        String msg = "msg";
        List<ComboGasto> comboTipoGastos = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, user, codMotivo, msg, comboTipoGastos)
        );
    }

    private static Stream<Arguments> getCuponesSource(){
        String opcion = "opcion";
        String subTrx = "subTrx";
        String codapli = "codapli";
        String user = "user";
        String fechaDesde = "fechaDesde";
        String fechaHasta = "fechaHasta";
        String idRendicion = "1";
        String codMotivo = "codMotivo";
        String montoMin = "montoMin";
        String moneda = "moneda";
        String msg = "msg";
        List<Cupones> cupones = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, subTrx, codapli, user, fechaDesde, fechaHasta, idRendicion, codMotivo, montoMin,moneda,msg,cupones)
        );
    }

    private static Stream<Arguments> asignarCuponSource(){
        String opcion = "opcion";
        String idRendicion = "1";
        String idGasto = "1";
        String user = "user";
        String impCuponTj = "20,10";
        String nroTarjeta = "nroTarjeta";
        String nroCuponTj = "1";
        String cuponDeb = "1";
        String cuponCred = "1";
        String descCupon = "descCupon";
        String monedaCupon = "monedaCupon";
        String fechaPresentacion = "fechaPresentacion";
        List<Gastos> gastosRendicion = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, idRendicion, idGasto, user, impCuponTj, nroTarjeta, nroCuponTj, cuponDeb, cuponCred, descCupon, monedaCupon, fechaPresentacion, gastosRendicion)
        );
    }

    private static Stream<Arguments> addDescripcionObligatoriaSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String codGasto = "1";
        String codDetOblig = "1";
        String campoTexto1 = "1";
        String campoTexto2 = "1";
        String campoNumerico1 = "1";
        String campoNumerico2 = "1";
        String campoCodigo1 = "1";
        String campoCodigo2 = "1";
        String campoTexto250 = "1";
        String campoFecha1 = "1";
        String campoFecha2 = "1";

        return Stream.of(
                Arguments.of(idRendicion, idGasto, codGasto, codDetOblig, campoTexto1, campoTexto2, campoNumerico1, campoNumerico2, campoCodigo1, campoCodigo2, campoTexto250, campoFecha1, campoFecha2)
        );
    }

    private static Stream<Arguments> consultaDetObligatorioSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String codMotivo = "1";
        String codObserv = "1";
        String msg = "msg";
        List< DatosPantallaDinamica > datosPantallaDinamica = new ArrayList<>();

        return Stream.of(
                Arguments.of(idRendicion, idGasto, codMotivo, codObserv, msg, datosPantallaDinamica)
        );
    }

    private static Stream<Arguments> consultaDetallesGastosSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String idObserv = "1";
        List< DatosPantallaDinamica > fieldsScreen = new ArrayList<>();
        List<List<String>> res = new ArrayList<>();
        return Stream.of(
                Arguments.of(idRendicion, idGasto, idObserv, fieldsScreen, res)
        );
    }

    private static Stream<Arguments> bajaGastoSource(){
        String idGasto = "1";
        String user = "user";
        String idRendicion = "1";
        String msg = "msg";
        Integer idGastoBorrado = 1;

        return Stream.of(
                Arguments.of(idGasto, user, idRendicion, msg, idGastoBorrado)
        );
    }

    private static Stream<Arguments> getCuponUnicoSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String idUser = "1";
        String codMotivo = "1";
        String msg = "msg";
        List<Cupones> cupones = new ArrayList<>();

        return Stream.of(
                Arguments.of(idRendicion, idGasto, idUser, codMotivo, msg, cupones)
        );
    }

    private static Stream<Arguments> redistribuirGastosSource(){
        String accion = "accion";
        String idRendicion = "1";
        String gastoOriginal = "1";
        String montoItems = "1.0;2.3";
        String ccostoItems = "1";
        String gastoItems = "1";
        Usuario user = new Usuario("idUser","perfil","nombre",1,"sector",new ArrayList<>());
        String msg = "msg";

        return Stream.of(
                Arguments.of(accion, idRendicion, gastoOriginal, montoItems, ccostoItems, gastoItems, user, msg)
        );
    }
    
    @Test
    void testParametrosNulosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
        	pagosService.altaModifGasto(null, null, null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        });
    }
    
    @Test
    void testAltaSinCupon() throws Exception {
    	
    	PrintWriter printWriter = new PrintWriter(new StringWriter());
  	  	when(httpServletResponseMocked.getWriter()).thenReturn(printWriter);
  	  	
  	  	pagosService.setManagerTransaction(mockManager);

        when(mockManager.getDataReturnList()).thenReturn(Collections.emptyList());
        when(mockManager.getDataReturn()).thenReturn(123);
        when(mockManager.getMensajeAviso()).thenReturn("OK");
  	  	
        Integer result = pagosService.altaModifGasto(
            "A", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202", 
            "0001Descripcion00000", "1234.56", "01/01/2024", "MOT", "12", 
            null, null, null, null, null, null, null
        );

        assertEquals(123, result);
    }
    
    @Test
    void testResolveManagerTransaction_retornaMockSiEstaSeteado() {
        PagosService service = new PagosService(samWebClient);
        ManagerTransaction mockTx = mock(ManagerTransaction.class);
        service.setManagerTransaction(mockTx);

        Transaction dummyHandler = mock(Transaction.class);
        ManagerTransaction result = service.resolveManagerTransaction(dummyHandler);

        assertSame(mockTx, result);
    }
    
    @Test
    void testResolveManagerTransaction_retornaInstanciaNuevaSiNoHayMock() {
        PagosService service = new PagosService();

        Transaction handler = mock(Transaction.class);
        ManagerTransaction result = service.resolveManagerTransaction(handler);

        assertNotNull(result);
        assertTrue(result instanceof ManagerTransaction);
    }


    
    @Test
    void testAltaConCupon() throws Exception {
    	
    	PrintWriter printWriter = new PrintWriter(new StringWriter());
  	  	when(httpServletResponseMocked.getWriter()).thenReturn(printWriter);
  	  	
  	  	pagosService.setManagerTransaction(mockManager);

        when(mockManager.getDataReturnList()).thenReturn(Collections.emptyList());
        when(mockManager.getDataReturn()).thenReturn(123);
        when(mockManager.getMensajeAviso()).thenReturn("OK");
        
        Integer result = pagosService.altaModifGasto(
            "A", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202", 
            "0001Descripcion00000", "1000", "01/01/2024", "MOT", "12", 
            "111", "222", "123456", "Descuento especial", "123.45", "4321432143214321", "Observaciones"
        );

        assertEquals(123, result);
    }
    
    @Test
    void testGetValidacionRendicion() throws Exception {
        List<String> retorno = Arrays.asList("VALIDO");
        Mockito.when(mockManager.getDataReturnList()).thenReturn(retorno);
        Mockito.when(mockManager.getMensajeAviso()).thenReturn("Mensaje OK");

        pagosService.setManagerTransaction(mockManager);

        String resultado = pagosService.getValidacionRendicion("1", "123", "456");

        assertEquals("VALIDO", resultado);
        assertEquals("Mensaje OK", pagosService.getMsg());
    }
    
    @Test
    void testConsultaDatosAdicionales() throws Exception {
        List<DatosPantallaDinamica> mockLista = new ArrayList<>();
        mockLista.add(new DatosPantallaDinamica());

        Mockito.when(mockManager.getDataReturnList()).thenReturn(mockLista);
        Mockito.when(mockManager.getMensajeAviso()).thenReturn("OK");
        pagosService.setManagerTransaction(mockManager);

        List<DatosPantallaDinamica> resultado = pagosService.consultaDatosAdicionales("100", "200", "M01", "OBS");

        assertFalse(resultado.isEmpty());
    }
    
    @Test
    void testAltaModifDatoAdicional_alta() throws Exception {
        pagosService.setManagerTransaction(mockManager);

        assertDoesNotThrow(() -> pagosService.altaModifDatoAdicional("1", "2", "3", "4", "", "txt1",
                "txt2", "100", "200", "001", "002", "text large", "2025-01-01", "2025-02-02"));
    }
    
    @Test
    void testBajaDatoAdicional() throws Exception {
        pagosService.setManagerTransaction(mockManager);

        assertDoesNotThrow(() -> pagosService.bajaDatoAdicional("1", "2", "3", "4", "5"));
    }
    
    @Test
    void testGetCodigosPatagonia() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        List<String> resultado = pagosService.getCodigosPatagonia();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("00212"));
    }
    
    @Test
    void testRedistribuirGastos() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        Usuario mockUser = new Usuario(null, "SS", null, 0, null, null);
        mockUser.setIdUser("USR001");

        Mockito.when(mockManager.getMensajeAviso()).thenReturn("Redistribución exitosa");

        String resultado = pagosService.redistribuirGastos("DER", "123", "456", "100.50", "1001", "8888", mockUser);

        assertEquals("Redistribución exitosa", resultado);
    }
    
    @Test
    void testAltaModifGastoParametrosNulos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            pagosService.altaModifGasto(null, null, null, "ARS", null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null)
        );
        assertEquals("Los parámetros opcion, idGasto e idRendicion no pueden ser nulos.", exception.getMessage());
    }
    
    @Test
    void testAltaModifGastoConFechaInvalida() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(321);
        when(mockManager.getMensajeAviso()).thenReturn("OK");

        Integer result = pagosService.altaModifGasto(
            "M", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202",
            "0001Descripcion", "1000", "32/13/2023", "MOT", "", null, null, null, null, null, null, ""
        );

        assertEquals(321, result);
    }

    @Test
    void testAltaModifGastoCuponConValorNoNumerico() throws Exception {
        pagosService.setManagerTransaction(mockManager);

        when(mockManager.getDataReturn()).thenReturn(999);
        when(mockManager.getMensajeAviso()).thenReturn("Con cupon string");

        Integer result = pagosService.altaModifGasto(
            "A", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202", 
            "0001Descripcion00000", "500.00", "01/01/2024", "MOT", "0012", 
            "0001", "0002", "NO_NUMERICO", "Desc.", "10", "000012341234", "Obs"
        );

        assertEquals(999, result);
    }


    @Test
    void testAltaModifGastoConTipoGastoNull() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(456);
        when(mockManager.getMensajeAviso()).thenReturn("OK con tipoGasto null");

        Integer result = pagosService.altaModifGasto(
            "A", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202",
            null, // tipoGasto es null
            "1000.50", "01/01/2024", "MOT", "12", 
            null, null, null, null, null, null, "Observaciones"
        );

        assertEquals(456, result);
        assertEquals("OK con tipoGasto null", pagosService.getMsg());
    }
    
    @Test
    void testAltaModifGastoConTipoGastoIncompleto() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(789);
        when(mockManager.getMensajeAviso()).thenReturn("OK con tipoGasto incompleto");

        Integer result = pagosService.altaModifGasto(
            "A", "123", "456", "ARS", "F", "A", "001-00000001", "20202020202",
            "0001Desc", // tipoGasto incompleto (menos de 59 caracteres)
            "2500.75", "15/03/2024", "MOT", "25", 
            null, null, null, null, null, null, "Test incompleto"
        );

        assertEquals(789, result);
        assertEquals("OK con tipoGasto incompleto", pagosService.getMsg());
    }
    
    @Test
    void testAltaModifGastoConTipoGastoMuyCorto() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(101);
        when(mockManager.getMensajeAviso()).thenReturn("OK con tipoGasto muy corto");

        Integer result = pagosService.altaModifGasto(
            "M", "555", "777", "USD", "R", "B", "002-00000002", "30303030303",
            "01", // tipoGasto muy corto (menos de 4 caracteres)
            "750.00", "20/06/2024", "XYZ", "8", 
            null, null, null, null, null, null, "Gasto modificado"
        );

        assertEquals(101, result);
        assertEquals("OK con tipoGasto muy corto", pagosService.getMsg());
    }
    
    @Test
    void testAltaModifGastoConTipoGastoVacio() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(202);
        when(mockManager.getMensajeAviso()).thenReturn("OK con tipoGasto vacío");

        Integer result = pagosService.altaModifGasto(
            "A", "888", "999", "EUR", "F", "C", "003-00000003", "40404040404",
            "", // tipoGasto vacío
            "150.25", "10/12/2024", "ABC", "15", 
            null, null, null, null, null, null, null
        );

        assertEquals(202, result);
        assertEquals("OK con tipoGasto vacío", pagosService.getMsg());
    }
    
    @Test
    void testAltaModifGastoConTipoGastoLongitudIntermedia() throws Exception {
        pagosService.setManagerTransaction(mockManager);
        
        when(mockManager.getDataReturn()).thenReturn(303);
        when(mockManager.getMensajeAviso()).thenReturn("OK con longitud intermedia");

        Integer result = pagosService.altaModifGasto(
            "M", "111", "222", "ARS", "T", "A", "004-00000004", "50505050505",
            "0123DescripcionParcialTexto", // tipoGasto con longitud intermedia (entre 4 y 59)
            "3000.00", "05/08/2024", "DEF", "30", 
            null, null, null, null, null, null, "Test intermedio"
        );

        assertEquals(303, result);
        assertEquals("OK con longitud intermedia", pagosService.getMsg());
    }
}