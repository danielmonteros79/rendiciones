package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.*;
import com.sa.entities.parametros.Resumen;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RendicionesServiceTest {

    @InjectMocks
    RendicionesService rendicionesService;

    @Spy
    SAMWebClient samWebClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("obtenerListadoRendicionesSource")
    @DisplayName("Testeando obtener listado rendiciones")
    void obtenerListadoRendiciones(String idUser, String idRendicion, String estado, String feDesde, String feHasta, String msg, List<Rendicion> rendiciones) throws TransactionException, ParseException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(rendiciones);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<Rendicion> result = rendicionesService1.obtenerListadoRendiciones(idUser, idRendicion, estado, feDesde, feHasta);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(rendiciones, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getComboOpcion2Source")
    @DisplayName("Testeando get combo opcion 2")
    void getComboOpcion2(String opcion, String tabla, String subTabla, String user,String msg,List<ComboOpcion2> comboEstados) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(comboEstados);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<ComboOpcion2> result = rendicionesService1.getComboOpcion2(opcion, tabla, subTabla, user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(comboEstados, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getComboOpcion2_2Source")
    @DisplayName("Testeando get combo opcion 2")
    void testGetComboOpcion2(String opcion, String tabla, String subTabla, String user, String codGasto,String msg,List<ComboOpcion2> comboEstados) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(comboEstados);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<ComboOpcion2> result = rendicionesService1.getComboOpcion2(opcion, tabla, subTabla, user, codGasto);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(comboEstados, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getMotivoRendicionesSource")
    @DisplayName("Testeando get motivo rendiciones")
    void getMotivoRendiciones(String opcion, String user,String msg,List<ComboMotivo> comboMotivo) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(comboMotivo);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<ComboMotivo> result = rendicionesService1.getMotivoRendiciones(opcion, user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(comboMotivo, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("cambiarEstadoScannSource")
    @DisplayName("Testeando cambiar estado scann")
    void cambiarEstadoScann(String idUser, RendicionForm rf, String string2, String string3, String string4) {
        RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
        rendicionesService1.cambiarEstadoScann(idUser, rf, string2, string3, string4);

        assertAll(
                () -> assertNotNull(rf.getEstado()),
                () -> assertEquals(2, rf.getEstado())
        );
    }

    @ParameterizedTest
    @MethodSource("altaRendicionSource")
    @DisplayName("Testeando alta rendicion")
    void altaRendicion(String idusr, String nombreUsr, String motivo, String feDesde, String feHasta, String descripcion,String idRendicion,String msg) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
            when(mockM.getDataReturn()).thenReturn(idRendicion);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            String result = rendicionesService1.altaRendicion(idusr, nombreUsr, motivo, feDesde, feHasta, descripcion);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(idRendicion, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getGastosSource")
    @DisplayName("Testeando get gastos")
    void getGastos(String idRendicion, String idGasto, String idUser, String codMotivo,String msg,List<Gastos> gastosRendicion) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(gastosRendicion);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<Gastos> result = rendicionesService1.getGastos(idRendicion, idGasto, idUser, codMotivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(gastosRendicion, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getGastosDistribuidosSource")
    @DisplayName("Testeando get gastos distribuidos")
    void getGastosDistribuidos(String idRendicion, String idGasto, String idUser, String codMotivo, List<Gastos> gastosRendicion) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(gastosRendicion);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<Gastos> result = rendicionesService1.getGastosDistribuidos(idRendicion, idGasto, idUser, codMotivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(gastosRendicion, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("bajaRendicionSource")
    @DisplayName("Testeando baja rendicion")
    void bajaRendicion(String opcion, String user, String idRendicion,String idRendicionBorrada,String aviso) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(aviso);
            when(mockM.getDataReturn()).thenReturn(idRendicionBorrada);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            String result = rendicionesService1.bajaRendicion(opcion, user, idRendicion);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(idRendicionBorrada, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getCuadroGeneralSource")
    @DisplayName("Testeando get cuadro general")
    void getCuadroGeneral(String opcion, String idUser, String fechaDesde, String fechaHasta,
                          String monDesde, String monHasta, String codMotivo, String codGlg, String userSel,String msg,List<CuadroGeneral> cuadroGeneral) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(cuadroGeneral);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<CuadroGeneral> result = rendicionesService1.getCuadroGeneral(opcion, idUser, fechaDesde, fechaHasta,
                    monDesde, monHasta, codMotivo, codGlg, userSel);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(cuadroGeneral, result)
            );

        }
    }

    @ParameterizedTest
    @MethodSource("getCuadroDetalladoSource")
    @DisplayName("Testeando get cuadro detallado")
    void getCuadroDetallado(String opcion, String fechaDesde, String fechaHasta, String monDesde, String monHasta,
                            String codEstado, String codMotivo, String idUser, String codGlg, String userSel,String msg,List<CuadroDetallado> cuadroDetallado) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(cuadroDetallado);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<CuadroDetallado> result = rendicionesService1.getCuadroDetallado(opcion, fechaDesde, fechaHasta, monDesde, monHasta,
                    codEstado, codMotivo, idUser, codGlg, userSel);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(cuadroDetallado, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getGlgsUsuarioSource")
    @DisplayName("Testeando get glgs usuario")
    void getGlgsUsuario(String usuario, String perfil,String msg,  List<ComboOpcion> glgs) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(glgs);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            List<ComboOpcion> result = rendicionesService1.getGlgsUsuario(usuario, perfil);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(glgs, result)
            );
        }
    }

    @Test
    @Disabled("No se puede mockear el metodo getMsg")
    @DisplayName("Testeando get msg")
    void getMsg() {
    }

    @ParameterizedTest
    @MethodSource("activaRechazaRendicionSource")
    @DisplayName("Testeando activa rechaza rendicion")
    void activaRechazaRendicion(String estado, String user, String idRendicion,String msg) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            RendicionesService rendicionesService1 = new RendicionesService(samWebClient);
            rendicionesService1.activaRechazaRendicion(estado, user, idRendicion);
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> obtenerListadoRendicionesSource() {
        String idUser = "1";
        String idRendicion = "1";
        String estado = "1";
        String feDesde = "1";
        String feHasta = "1";
        String msg = "1";
        List<Rendicion> rendiciones = new ArrayList<>();

        return Stream.of(
                Arguments.of(idUser, idRendicion, estado, feDesde, feHasta, msg, rendiciones)
        );
    }

    private static Stream<Arguments> getComboOpcion2Source(){
        String opcion = "1";
        String tabla = "1";
        String subTabla = "1";
        String user = "1";
        String msg = "1";
        List<ComboOpcion2> comboEstados = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, tabla, subTabla, user, msg, comboEstados)
        );
    }

    private static Stream<Arguments> getComboOpcion2_2Source(){
        String opcion = "1";
        String tabla = "1";
        String subTabla = "1";
        String user = "1";
        String codGasto = "1";
        String msg = "1";
        List<ComboOpcion2> comboEstados = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, tabla, subTabla, user, codGasto, msg, comboEstados)
        );
    }

    private static Stream<Arguments> getMotivoRendicionesSource(){
        String opcion = "1";
        String user = "1";
        String msg = "1";
        List<ComboMotivo> comboMotivo = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, user, msg, comboMotivo)
        );
    }

    private static Stream<Arguments> cambiarEstadoScannSource(){
        String idUser = "1";
        RendicionForm rf = new RendicionForm();
        String string2 = "1";
        String string3 = "1";
        String string4 = "1";

        return Stream.of(
                Arguments.of(idUser, rf, string2, string3, string4)
        );
    }

    private static Stream<Arguments> altaRendicionSource(){
        String idusr = "1";
        String nombreUsr = "1";
        String motivo = "1";
        String feDesde = "1";
        String feHasta = "1";
        String descripcion = "1";
        String idRendicion = "1";
        String msg = "1";

        return Stream.of(
                Arguments.of(idusr, nombreUsr, motivo, feDesde, feHasta, descripcion, idRendicion, msg)
        );
    }

    private static Stream<Arguments> getGastosSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String idUser = "1";
        String codMotivo = "1";
        String msg = "1";
        List<Gastos> gastosRendicion = new ArrayList<>();

        return Stream.of(
                Arguments.of(idRendicion, idGasto, idUser, codMotivo, msg, gastosRendicion)
        );
    }

    private static Stream<Arguments> getGastosDistribuidosSource(){
        String idRendicion = "1";
        String idGasto = "1";
        String idUser = "1";
        String codMotivo = "1";
        List<Gastos> gastosRendicion = new ArrayList<>();

        return Stream.of(
                Arguments.of(idRendicion, idGasto, idUser, codMotivo, gastosRendicion)
        );
    }

    private static Stream<Arguments> bajaRendicionSource(){
        String opcion = "1";
        String user = "1";
        String idRendicion = "1";
        String idRendicionBorrada = "1";
        String aviso = "BAJA EFECTUADA";
        String aviso2 = null;
        String aviso3 = "";


        return Stream.of(
                Arguments.of(opcion, user, idRendicion, idRendicionBorrada, aviso),
                Arguments.of(opcion, user, idRendicion, idRendicionBorrada, aviso2),
                Arguments.of(opcion, user, idRendicion, idRendicionBorrada, aviso3)
        );
    }

    private static Stream<Arguments> getCuadroGeneralSource(){
        String opcion = "1";
        String idUser = "1";
        String fechaDesde = "1";
        String fechaHasta = "1";
        String monDesde = "10,5";
        String monHasta = "10,5";
        String codMotivo = "1";
        String codGlg = "1";
        String userSel = "1";
        String msg = "1";
        List<CuadroGeneral> cuadroGeneral = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, idUser, fechaDesde, fechaHasta, monDesde, monHasta, codMotivo, codGlg, userSel, msg, cuadroGeneral)
        );
    }

    private static Stream<Arguments> getCuadroDetalladoSource(){
        String opcion = "1";
        String fechaDesde = "1";
        String fechaHasta = "1";
        String monDesde = "10,5";
        String monHasta = "10,5";
        String codEstado = "PGLGE";
        String codMotivo = "1";
        String idUser = "1";
        String codGlg = "1";
        String userSel = "1";
        String msg = "1";
        List<CuadroDetallado> cuadroDetallado = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion, fechaDesde, fechaHasta, monDesde, monHasta, codEstado, codMotivo, idUser, codGlg, userSel, msg, cuadroDetallado)
        );
    }

    private static Stream<Arguments> getGlgsUsuarioSource(){
        String usuario = "1";
        String perfil = "1";
        String msg = "1";
        List<ComboOpcion> glgs = new ArrayList<>();

        return Stream.of(
                Arguments.of(usuario, perfil, msg, glgs)
        );
    }

    private static Stream<Arguments> activaRechazaRendicionSource(){
        String estado = "1";
        String user = "1";
        String idRendicion = "1";
        String msg = "1";

        return Stream.of(
                Arguments.of(estado, user, idRendicion, msg)
        );
    }

}