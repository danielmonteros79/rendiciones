package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.OSCAR;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.*;
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU89;
import com.sa.util.ParamsConstants;
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

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosServiceTest {

    @InjectMocks
    ParametrosService parametrosService;

    @Spy
    SAMWebClient samWebClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getMotivosSource")
    @DisplayName("Testeando get motivos")
    void getMotivos(String codMotivo, String user, List<ParametroMotivo> parametroMotivos) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(parametroMotivos);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametroMotivo> result = parametrosService.getMotivos(codMotivo, user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(parametroMotivos, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("altaMotivoSource")
    @DisplayName("Testeando alta motivo")
    void altaMotivo(ParametroMotivo motivo,String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.altaMotivo(motivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("modificacionMotivoSource")
    @DisplayName("Testeando modificacion motivo")
    void modificacionMotivo(ParametroMotivo motivo,String msg, String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.modificacionMotivo(motivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("bajaMotivoSource")
    @DisplayName("Testeando baja motivo")
    void bajaMotivo(String codMotivo, String msg, String res) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.bajaMotivo(codMotivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(res, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getGastosSource")
    @DisplayName("Testeando get gastos")
    void getGastos(String user, String codGasto, String msgAviso, List<ParametroGasto> data) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(data);
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametroGasto> result = parametrosService.getGastos(user, codGasto);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(data, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getRelacionUsuarioDelegadoSource")
    @DisplayName("Testeando get relacion usuario delegado")
    void getRelacionUsuarioDelegado(String usuario,String msg,List<ParametriaUsuarioDelegado> listado) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(listado);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametriaUsuarioDelegado> result = parametrosService.getRelacionUsuarioDelegado(usuario);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(listado, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("abmDelegacionesSource")
    @DisplayName("Testeando abm delegaciones")
    void abmDelegaciones(RelacionUsuarioDelegadoForm formulario, Usuario user,String aviso,String res) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn(aviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.abmDelegaciones(formulario, user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(res, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getGastosCombosSource")
    @DisplayName("Testeando get gastos combos")
    void getGastosCombos(List<String> combos) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(combos);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<String> result = parametrosService.getGastosCombos();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(combos, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getAlertaCombosSource")
    @DisplayName("Testeando get alerta combos")
    void getAlertaCombos(String msgAviso, List<String> combos) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(combos);
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<String> result = parametrosService.getAlertaCombos();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(combos, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("modifRelacionUsuarioDelegadoSource")
    @DisplayName("Testeando get relacion usuario delegado")
    void modifRelacionUsuarioDelegado(String idusr, String delegado, String feDesde, String feHasta, String estInf, String estCarg, String idRendicion) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturn()).thenReturn(idRendicion);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.modifRelacionUsuarioDelegado(idusr, delegado, feDesde, feHasta, estInf, estCarg);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(idRendicion, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("bajaRelacionUsuarioDelegadoSource")
    @DisplayName("Testeando baja relacion usuario delegado")
    void bajaRelacionUsuarioDelegado(String idusr, String delegado, String idRendicion) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturn()).thenReturn(idRendicion);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.bajaRelacionUsuarioDelegado(idusr, delegado);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(idRendicion, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getUsuarioDelegacionSource")
    @DisplayName("Testeando get usuario delegacion")
    void getUsuarioDelegacion(String usuario, String opcion, Usuario usuarioCheck) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturn()).thenReturn(usuarioCheck);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            Usuario result = parametrosService.getUsuarioDelegacion(usuario, opcion);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(usuarioCheck, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getCodigoExceptuadoSource")
    @DisplayName("Testeando get codigo exceptuado")
    void getCodigoExceptuado(String usuario, String codigo, String marca,String codigoExceptuado) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturn()).thenReturn(codigoExceptuado);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.getCodigoExceptuado(usuario, codigo, marca);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(codigoExceptuado, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getAlertasSource")
    @DisplayName("Testeando get alertas")
    void getAlertas(String opcion, String codMotivo, String codGasto,String msgAviso,List<ParametroAlerta> parametroAlerta) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(parametroAlerta);
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametroAlerta> result = parametrosService.getAlertas(opcion, codMotivo, codGasto);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(parametroAlerta, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getAlertaSource")
    @DisplayName("Testeando get alerta")
    void getAlerta(String opcion, String codMotivo, String codGasto, String timeStamp,ParametroAlerta parametroAlerta,List<ParametroAlerta> lista) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(lista);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            ParametroAlerta result = parametrosService.getAlerta(opcion, codMotivo, codGasto, timeStamp);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(parametroAlerta, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("altaParamAlertaSource")
    @DisplayName("Testeando alta param alerta")
    void altaParamAlerta(ParametrosAlertasForm frm,String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.altaParamAlerta(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("modificacionParamAlertaSource")
    @DisplayName("Testeando modificacion param alerta")
    void modificacionParamAlerta(ParametrosAlertasForm frm, String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.modificacionParamAlerta(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("bajaParamAlertaSource")
    @DisplayName("Testeando baja param alerta")
    void bajaParamAlerta(ParametrosAlertasForm frm, String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.bajaParamAlerta(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getExceptuadosSource")
    @DisplayName("Testeando get exceptuados")
    void getExceptuados(String user,String msgAviso,List<ParametroExceptuado> parametroExceptuado) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(parametroExceptuado);
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametroExceptuado> result = parametrosService.getExceptuados(user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(parametroExceptuado, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getExceptuadoSource")
    @DisplayName("Testeando get exceptuado")
    void getExceptuado(String marca, String codMotUs, String user,List<ParametroExceptuado> parametroExceptuado) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getDataReturnList()).thenReturn(parametroExceptuado);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            List<ParametroExceptuado> result = parametrosService.getExceptuado(marca, codMotUs, user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(parametroExceptuado, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("altaExceptuadoSource")
    @DisplayName("Testeando alta exceptuado")
    void altaExceptuado(ParametrosExceptuadosForm frm,String msg, String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.altaExceptuado(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("saveModExceptuadoSource")
    @DisplayName("Testeando save mod exceptuado")
    void saveModExceptuado(ParametrosExceptuadosForm frm,String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.saveModExceptuado(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("deleteExceptuadoSource")
    @DisplayName("Testeando delete exceptuado")
    void deleteExceptuado(ParametrosExceptuadosForm frm,String msg,String ret) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            String result = parametrosService.deleteExceptuado(frm);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(ret, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("altaGastoSource")
    @DisplayName("Testeando alta gasto")
    void altaGasto(ParametrosGastosForm frm, String msgAviso) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            parametrosService.altaGasto(frm);
        }
    }

    @ParameterizedTest
    @MethodSource("modificacionGastoSource")
    @DisplayName("Testeando modificacion gasto")
    void modificacionGasto(ParametrosGastosForm frm, String msgAviso) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            parametrosService.modificacionGasto(frm);
        }
    }

    @ParameterizedTest
    @MethodSource("bajaGastoSource")
    @DisplayName("Testeando baja gasto")
    void bajaGasto(String codGasto, String codMotivo,String msgAviso) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            when(mockM.getMensajeAviso()).thenReturn(msgAviso);
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            parametrosService.bajaGasto(codGasto, codMotivo);
        }
    }

    @ParameterizedTest
    @MethodSource("loadModificacionGastoSource")
    @DisplayName("Testeando load modificacion gasto")
    void loadModificacionGasto(String codGasto, String idUser) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            ManagerTransaction result = parametrosService.loadModificacionGasto(codGasto, idUser);

            assertAll(
                    () -> assertNotNull(result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("loadBajaGastoSource")
    @DisplayName("Testeando load baja gasto")
    void loadBajaGasto(String codGasto, String idUser) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
        })) {

            ParametrosService parametrosService = new ParametrosService(samWebClient);
            ManagerTransaction result = parametrosService.loadBajaGasto(codGasto, idUser);

            assertAll(
                    () -> assertNotNull(result)
            );
        }
    }

    @Test
    @Disabled("No se puede mockear el metodo getMsgAviso")
    @DisplayName("Testeando get msg aviso")
    void getMsgAviso() {
    }

    // ------ Sources ------


    private static Stream<Arguments> getMotivosSource() {
        String codMotivo = "";
        String user = "";
        List<ParametroMotivo> parametroMotivos = new ArrayList<>();
        return Stream.of(
                Arguments.of(codMotivo, user, parametroMotivos)
        );
    }

    private static Stream<Arguments> altaMotivoSource(){
        ParametroMotivo motivo = new ParametroMotivo();
        List<String> centrosCosto = new ArrayList<>();
        centrosCosto.add("20");
        motivo.setCentrosCosto(centrosCosto);
        motivo.setMeDiasInterv("20");
        motivo.setCodigo("20");
        motivo.setEstado("estado");
        motivo.setDescripcion("descripcion");
        motivo.setIdGlg("20");
        motivo.setCodAprobacionGlg("20");
        motivo.setIdCentroCostos("20");
        motivo.setMaInclExcl("20");
        motivo.setCodSup("20");
        motivo.setCodFirma("20");
        motivo.setMeAviso("20");
        motivo.setFechaDesde(new Date());
        motivo.setFechaHasta(new Date());
        motivo.setOscar(new OSCAR());
        motivo.setIdNivCarga("20");
        motivo.setIdNivAutoriz("20");
        motivo.setTxAviso("20");
        motivo.setIdOperEspe("20");

        String msg = "msg";
        return Stream.of(
                Arguments.of(motivo,msg,msg),
                Arguments.of(motivo,null,"")
        );
    }

    private static Stream<Arguments> modificacionMotivoSource(){
        ParametroMotivo motivo = new ParametroMotivo();
        List<String> centrosCosto = new ArrayList<>();
        centrosCosto.add("20");
        motivo.setCentrosCosto(centrosCosto);
        motivo.setMeDiasInterv("20");
        motivo.setCodigo("20");
        motivo.setEstado("estado");
        motivo.setDescripcion("descripcion");
        motivo.setIdGlg("20");
        motivo.setCodAprobacionGlg("20");
        motivo.setIdCentroCostos("20");
        motivo.setMaInclExcl("20");
        motivo.setCodSup("20");
        motivo.setCodFirma("20");
        motivo.setMeAviso("20");
        motivo.setFechaDesde(new Date());
        motivo.setFechaHasta(new Date());
        motivo.setOscar(new OSCAR());
        motivo.setIdNivCarga("20");
        motivo.setIdNivAutoriz("20");
        motivo.setTxAviso("20");
        motivo.setIdOperEspe("20");

        String msg = "msg";
        return Stream.of(
                Arguments.of(motivo,msg,msg),
                Arguments.of(motivo,null,"")
        );
    }

    private static Stream<Arguments> bajaMotivoSource(){
        String codMotivo = "20";
        String msg = "msg";

        return Stream.of(
                Arguments.of(codMotivo,msg,"msg"),
                Arguments.of(codMotivo,null,"")
        );
    }

    private static Stream<Arguments> getGastosSource(){
        String user = "user";
        String codGasto  = "20";
        String msgAviso = "msg";
        List<ParametroGasto> data = new ArrayList<>();

        return Stream.of(
                Arguments.of(user,codGasto,msgAviso,data)
        );
    }

    private static Stream<Arguments> getRelacionUsuarioDelegadoSource(){
        String usuario = "user";
        String msg = "msg";
        List<ParametriaUsuarioDelegado> listado = new ArrayList<>();

        return Stream.of(
                Arguments.of(usuario,msg,listado)
        );
    }

    private static Stream<Arguments> abmDelegacionesSource() {
        RelacionUsuarioDelegadoForm formulario = new RelacionUsuarioDelegadoForm();
        RelacionUsuarioDelegadoForm formulario2 = new RelacionUsuarioDelegadoForm();
        RelacionUsuarioDelegadoForm formulario3 = new RelacionUsuarioDelegadoForm();
        Usuario user = new Usuario("idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        String aviso = "msg";
        String aviso2 = null;

        formulario.setOpcion("");
        formulario.setUsuario("");
        formulario.setDelegadoUser("");
        formulario.setFeDesde("2000/01/01");
        formulario.setFeHasta("2000/01/01");
        formulario.setInforme("");
        formulario.setAccion("");
        formulario.setEstado("");
        formulario.setFechaAlta("2000/01/01");
        formulario.setUserAlta("userAlta");


        formulario2.setOpcion("MODI");
        formulario2.setUsuario("");
        formulario2.setDelegadoUser("");
        formulario2.setFeDesde("2000/01/01");
        formulario2.setFeHasta("2000/01/01");
        formulario2.setInforme("");
        formulario2.setAccion("");
        formulario2.setEstado("");
        formulario2.setFechaAlta("2000/01/01");
        formulario2.setUserAlta("userAlta");
        formulario2.setFeDesdeOld("2000/01/01");
        formulario2.setFeHastaOld("2000/01/01");

        formulario3.setOpcion("BAJA");
        formulario3.setUsuario("");
        formulario3.setDelegadoUser("");
        formulario3.setFeDesde("2000/01/01");
        formulario3.setFeHasta("2000/01/01");
        formulario3.setInforme("");
        formulario3.setAccion("");
        formulario3.setEstado("");
        formulario3.setFechaAlta("2000/01/01");
        formulario3.setUserAlta("userAlta");
        formulario3.setFeDesdeOld("2000/01/01");
        formulario3.setFeHastaOld("2000/01/01");

        return Stream.of(
                Arguments.of(formulario, user, aviso,aviso),
                Arguments.of(formulario2, user, aviso2,""),
                Arguments.of(formulario3, user, aviso2,"")
        );
    }

    private static Stream<Arguments> getGastosCombosSource(){
        return Stream.of(
                Arguments.of(new ArrayList<>())
        );
    }

    private static Stream<Arguments> getAlertaCombosSource(){
        String msgAviso = "msg";
        List<String> combos = new ArrayList<>();

        return Stream.of(
                Arguments.of(msgAviso,combos)
        );
    }

    private static Stream<Arguments> modifRelacionUsuarioDelegadoSource(){
        String idusr = "idusr";
        String delegado = "delegado";
        String feDesde = "2000/01/01";
        String feHasta = "2000/01/01";
        String estInf = "estInf";
        String estCarg = "estCarg";
        String idRendicion = "idRendicion";

        return Stream.of(
                Arguments.of(idusr,delegado,feDesde,feHasta,estInf,estCarg,idRendicion)
        );
    }

    private static Stream<Arguments> bajaRelacionUsuarioDelegadoSource(){
        String idusr = "idusr";
        String delegado = "delegado";
        String idRendicion = "idRendicion";

        return Stream.of(
                Arguments.of(idusr,delegado,idRendicion)
        );
    }

    private static Stream<Arguments> getUsuarioDelegacionSource(){
        String usuario = "usuario";
        String opcion = "opcion";
        Usuario usuarioCheck = new Usuario("idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());

        return Stream.of(
                Arguments.of(usuario,opcion,usuarioCheck)
        );
    }

    private static Stream<Arguments> getCodigoExceptuadoSource(){
        String usuario = "usuario";
        String codigo = "codigo";
        String marca = "marca";
        String codigoExceptuado = "codigoExceptuado";

        return Stream.of(
                Arguments.of(usuario,codigo,marca,codigoExceptuado)
        );
    }

    private static Stream<Arguments> getAlertasSource(){
        String opcion = "opcion";
        String codMotivo = "codMotivo";
        String codGasto = "codGasto";
        String msgAviso = "msgAviso";
        List<ParametroAlerta> parametroAlerta = new ArrayList<>();

        return Stream.of(
                Arguments.of(opcion,codMotivo,codGasto,msgAviso,parametroAlerta)
        );
    }

    private static Stream<Arguments> getAlertaSource(){
        String opcion = "opcion";
        String codMotivo = "codMotivo";
        String codGasto = "codGasto";
        String timeStamp = "timeStamp";
        ParametroAlerta parametroAlerta = new ParametroAlerta();
        List<ParametroAlerta> parametroAlertaList = new ArrayList<>();
        parametroAlertaList.add(parametroAlerta);

        return Stream.of(
                Arguments.of(opcion,codMotivo,codGasto,timeStamp,parametroAlerta,parametroAlertaList)
        );
    }

    private static Stream<Arguments> altaParamAlertaSource(){
        ParametrosAlertasForm frm = new ParametrosAlertasForm();
        ParametrosAlertasForm frm2 = new ParametrosAlertasForm();
        String msg = "msg";

        frm.setCodMotivo("codMotivo");
        frm.setCodGasto("codGasto");
        frm.setEstado("estado");
        frm.setMontCant("montCant");
        frm.setRend("rend");
        frm.setPeriodo("periodo");
        frm.setCriticidad("criticidad");
        frm.setNivMax("nivMax");
        frm.setNivMin("nivMin");
        frm.setTxAviso("txAviso");
        frm.setMontCant("M");
        frm.setImpCant("100");

        frm2.setCodMotivo("codMotivo");
        frm2.setCodGasto("codGasto");
        frm2.setEstado("estado");
        frm2.setMontCant("montCant");
        frm2.setRend("rend");
        frm2.setPeriodo("periodo");
        frm2.setCriticidad("criticidad");
        frm2.setNivMax("nivMax");
        frm2.setNivMin("nivMin");
        frm2.setTxAviso("txAviso");
        frm2.setMontCant("montCant");
        frm2.setImpCant("100");

        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm2,null,"")
        );
    }

    private static Stream<Arguments> modificacionParamAlertaSource(){
        ParametrosAlertasForm frm = new ParametrosAlertasForm();
        ParametrosAlertasForm frm2 = new ParametrosAlertasForm();
        String msg = "msg";

        frm.setCodMotivo("codMotivo");
        frm.setCodGasto("codGasto");
        frm.setEstado("estado");
        frm.setMontCant("montCant");
        frm.setRend("rend");
        frm.setPeriodo("periodo");
        frm.setCriticidad("criticidad");
        frm.setNivMax("nivMax");
        frm.setNivMin("nivMin");
        frm.setTxAviso("txAviso");
        frm.setTimeStamp("timeStamp");
        frm.setMontCant("M");
        frm.setImpCant("100");

        frm2.setCodMotivo("codMotivo");
        frm2.setCodGasto("codGasto");
        frm2.setEstado("estado");
        frm2.setMontCant("montCant");
        frm2.setRend("rend");
        frm2.setPeriodo("periodo");
        frm2.setCriticidad("criticidad");
        frm2.setNivMax("nivMax");
        frm2.setNivMin("nivMin");
        frm2.setTxAviso("txAviso");
        frm2.setTimeStamp("timeStamp");
        frm2.setMontCant("");
        frm2.setImpCant("100");

        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm2,null,"")
        );
    }

    private static Stream<Arguments> bajaParamAlertaSource(){
        ParametrosAlertasForm frm = new ParametrosAlertasForm();
        String msg = "msg";

        frm.setCodMotivo("codMotivo");
        frm.setCodGasto("codGasto");
        frm.setTimeStamp("timeStamp");

        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm,null,"")
        );
    }

    private static Stream<Arguments> getExceptuadosSource(){
        String user = "user";
        String msgAviso = "msgAviso";
        List<ParametroExceptuado> parametroExceptuado = new ArrayList<>();

        return Stream.of(
                Arguments.of(user,msgAviso,parametroExceptuado)
        );
    }

    private static Stream<Arguments> getExceptuadoSource(){
        String marca = "marca";
        String codMotUs = "codMotUs";
        String user = "user";
        List<ParametroExceptuado> parametroExceptuado = new ArrayList<>();

        return Stream.of(
                Arguments.of(marca,codMotUs,user,parametroExceptuado)
        );
    }

    private static Stream<Arguments> altaExceptuadoSource(){
        ParametrosExceptuadosForm frm = new ParametrosExceptuadosForm();
        ParametrosExceptuadosForm frm2 = new ParametrosExceptuadosForm();
        String msg = "msg";

        frm.setMotivoUsuario("motivo");
        frm.setDesMotivo("desMotivo");
        frm.setEstado("estado");
        frm.setDesde("2000/01/01");
        frm.setHasta("2000/01/01");

        frm2.setMotivoUsuario("usuario");
        frm2.setDesMotivo("desMotivo");
        frm2.setEstado("estado");
        frm2.setDesde("2000/01/01");
        frm2.setHasta("2000/01/01");


        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm2,null,"")
        );
    }

    private static Stream<Arguments> saveModExceptuadoSource(){
        ParametrosExceptuadosForm frm = new ParametrosExceptuadosForm();
        ParametrosExceptuadosForm frm2 = new ParametrosExceptuadosForm();
        String msg = "msg";

        frm.setMotivoUsuario("motivo");
        frm.setDesMotivo("desMotivo");
        frm.setEstado("estado");
        frm.setDesde("2000/01/01");
        frm.setHasta("2000/01/01");

        frm2.setMotivoUsuario("usuario");
        frm2.setDesMotivo("desMotivo");
        frm2.setEstado("estado");
        frm2.setDesde("2000/01/01");
        frm2.setHasta("2000/01/01");


        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm2,null,"")
        );
    }

    private static Stream<Arguments> deleteExceptuadoSource(){
        ParametrosExceptuadosForm frm = new ParametrosExceptuadosForm();
        ParametrosExceptuadosForm frm2 = new ParametrosExceptuadosForm();
        String msg = "msg";

        frm.setMotivoUsuario("motivo");
        frm.setDesMotivo("desMotivo");

        frm2.setMotivoUsuario("usuario");
        frm2.setDesMotivo("desMotivo");

        return Stream.of(
                Arguments.of(frm,msg,msg),
                Arguments.of(frm2,null,"")
        );
    }

    private static Stream<Arguments> altaGastoSource(){
        ParametrosGastosForm frm = new ParametrosGastosForm();
        String msgAviso = "msgAviso";
        String ri = "";

        for (int i = 0; i < 18; i++) {
            ri+="iiii";
        }
        List <String> centrosCosto = new ArrayList<>();
        centrosCosto.add("100");

        frm.setCentrosCostoList(centrosCosto);
        frm.setCodigo("10");
        frm.setDescripcionGasto("descripcionGasto");
        frm.setMotivo("motivo");
        frm.setBimon("bimon");
        frm.setIdCentroCostos("100");
        frm.setEstado("estado");
        frm.setRistra(ri);
        frm.setOscar(new OSCAR());
        frm.setMaInclExcl("maInclExcl");
        frm.setComprob("comprob");
        frm.setAntiguedad("antiguedad");
        frm.setObserv("observ");
        frm.setIdNivAutoriz("idNivAutoriz");
        frm.setPlazoAprob("plazoAprob");

        return Stream.of(
                Arguments.of(frm,msgAviso,msgAviso)
        );
    }

    private static Stream<Arguments> modificacionGastoSource(){
        ParametrosGastosForm frm = new ParametrosGastosForm();
        String msgAviso = "msgAviso";
        String ri = "";

        for (int i = 0; i < 18; i++) {
            ri+="iiii";
        }
        List <String> centrosCosto = new ArrayList<>();
        centrosCosto.add("100");

        frm.setCentrosCostoList(centrosCosto);
        frm.setCodigo("10");
        frm.setDescripcionGasto("descripcionGasto");
        frm.setMotivo("motivo");
        frm.setBimon("bimon");
        frm.setIdCentroCostos("100");
        frm.setEstado("estado");
        frm.setRistra(ri);
        frm.setOscar(new OSCAR());
        frm.setMaInclExcl("maInclExcl");
        frm.setComprob("comprob");
        frm.setAntiguedad("antiguedad");
        frm.setObserv("observ");
        frm.setIdNivAutoriz("idNivAutoriz");
        frm.setPlazoAprob("plazoAprob");

        return Stream.of(
                Arguments.of(frm,msgAviso,msgAviso)
        );
    }

    private static Stream<Arguments> bajaGastoSource(){
        String codGasto = "codGasto";
        String codMotivo = "codMotivo";
        String msgAviso = "msgAviso";

        return Stream.of(
                Arguments.of(codGasto,codMotivo,msgAviso)
        );
    }

    private static Stream<Arguments> loadModificacionGastoSource(){
        String codGasto = "codGasto";
        String idUser = "idUser";

        return Stream.of(
                Arguments.of(codGasto,idUser)
        );
    }

    private static Stream<Arguments> loadBajaGastoSource(){
        String codGasto = "codGasto";
        String idUser = "idUser";

        return Stream.of(
                Arguments.of(codGasto,idUser)
        );
    }

}