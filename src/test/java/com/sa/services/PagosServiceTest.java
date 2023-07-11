package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.*;
import com.sa.manager.ManagerTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PagosServiceTest {

    @InjectMocks
    PagosService pagosService;

    @Spy
    SAMWebClient samWebClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("getCuponesSource")
    @DisplayName("Testeando get cupones")
    void getCupones(String opcion, String subTrx, String codapli, String user,
			String fechaDesde, String fechaHasta, String idRendicion, String codMotivo, String montoMin, String moneda) throws TransactionException {

        List<Cupones> cupones =  new ArrayList<>();
      	String msg = "mensaje aviso";

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

//    @ParameterizedTest
//    @MethodSource("asignarCuponSource")
//    @DisplayName("Testeando asignar cupon")
//    void asignarCupon(String opcion, String idRendicion, String idGasto, String user, String impCuponTj, String nroTarjeta,
//                      String nroCuponTj, String cuponDeb, String cuponCred, String descCupon, String monedaCupon, String fechaPresentacion, List<Gastos> gastosRendicion) throws TransactionException {
//
//        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
//            doNothing().when(mockM).executeTrx(any(),anyMap());
//            when(mockM.getDataReturnList()).thenReturn(gastosRendicion);
//        })) {
//
//            PagosService pagosService = new PagosService(samWebClient);
//            List<Gastos> result = pagosService.asignarCupon(opcion,idRendicion,idGasto,user,impCuponTj,nroTarjeta,nroCuponTj,cuponDeb,cuponCred,descCupon,monedaCupon,fechaPresentacion);
//
//            assertAll(
//                    () -> assertNotNull(result),
//                    () -> assertEquals(gastosRendicion,result)
//            );
//        }
//
//
//    }

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

//    @ParameterizedTest
//    @MethodSource("bajaGastoSource")
//    @DisplayName("Testeando baja gasto")
//    void bajaGasto(String opcion, String idGasto, String user, String idRendicion, String codMotivo,String msg,Integer idGastoBorrado) throws TransactionException {
//
//            try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
//                doNothing().when(mockM).executeTrx(any(),anyMap());
//                when(mockM.getMensajeAviso()).thenReturn(msg);
//                when(mockM.getDataReturn()).thenReturn(idGastoBorrado);
//            })) {
//
//                PagosService pagosService = new PagosService(samWebClient);
//                Integer result = pagosService.bajaGasto(opcion,idGasto,user,idRendicion,codMotivo);
//
//                assertAll(
//                        () -> assertNotNull(result),
//                        () -> assertEquals(idGastoBorrado,result)
//                );
//            }
//    }

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
        String msg = "msg";
        List<Cupones> cupones = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, subTrx, codapli, user, fechaDesde, fechaHasta, idRendicion, codMotivo, msg, cupones)
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
        String opcion = "opcion";
        String idGasto = "1";
        String user = "user";
        String idRendicion = "1";
        String codMotivo = "1";
        String msg = "msg";
        Integer idGastoBorrado = 1;

        return Stream.of(
                Arguments.of(opcion, idGasto, user, idRendicion, codMotivo, msg, idGastoBorrado)
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
}