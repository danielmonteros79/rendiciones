package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

//import com.sa.action.RendicionAvisoAction;
import com.sa.entities.Journal;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.ImagenesForm;
import com.sa.form.RendicionAvisoForm;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AprobacionesServiceTest {

    @InjectMocks
    AprobacionesService aprobacionesService;

    @Spy
    SAMWebClient samWebClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getAprobacionesPendientesSource")
    @DisplayName("Testeando get aprobaciones pendientes")
    void getAprobacionesPendientes(String id, String usuarioFiltro, String motivo, String estado, String usuario, List<Rendicion> rendiciones, String dataReturn) throws Exception {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(rendiciones);
            when(mockM.getDataReturn()).thenReturn("msg");
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            List<Rendicion> rendiciones1 = aprobacionesService.getAprobacionesPendientes(id, usuarioFiltro, motivo, estado, usuario);

            assertNotNull(rendiciones1);
        }
    }

    /*@ParameterizedTest
    @MethodSource("cambiarEstadoRendicionesSource")
    @DisplayName("Testeando cambiar estado rendiciones")
    void cambiarEstadoRendiciones(String user,String estado,String motivoRechazo,String glg,String aviso,List<Rendicion> rendicionesSeleccionadas) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturn()).thenReturn(aviso);
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            String result = aprobacionesService.cambiarEstadoRendiciones(user, rendicionesSeleccionadas, estado, motivoRechazo, glg);

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(aviso, result)
            );
        }
    }*/

    /*@ParameterizedTest
    @MethodSource("cambiarEstadoDeUnaRendicionSource")
    @DisplayName("Testeando cambiar estado de una rendicion")
    void cambiarEstadoDeUnaRendicion(String user, Integer rendicionesSeleccionadas,String estado,String motivoRechazo,String glg,String aviso) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturn()).thenReturn(aviso);
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            String result = aprobacionesService.cambiarEstadoDeUnaRendicion(user, rendicionesSeleccionadas, estado, motivoRechazo, glg);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(aviso, result)
            );
        }
    }*/


    /*@ParameterizedTest
    @MethodSource("obtenerIDUSource")
    @DisplayName("Testeando obtener IDU")
    void obtenerIDU(RendicionAvisoForm form, String tipoAdea, String iduAdea, String msg)  {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturn()).thenReturn(iduAdea);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            String result = aprobacionesService.obtenerIDU(form, tipoAdea);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(iduAdea, result)
            );
        }
    }*/

    @ParameterizedTest
    @MethodSource("scanRendicionSource")
    @DisplayName("Testeando scan rendicion")
    void scanRendicion(String idRendicion, String user) throws Exception {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            aprobacionesService.scanRendicion(idRendicion, user);
        }
    }

    /*@ParameterizedTest
    @MethodSource("cambiarEscanRendicionSource")
    @DisplayName("Testeando cambiar escan rendicion")
    void cambiarEscanRendicion(String idRendicion, String user,
                               String idu, String adea) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            aprobacionesService.cambiarEscanRendicion(idRendicion, user, idu, adea);
        }
    }*/

    @ParameterizedTest
    @MethodSource("getJournalSource")
    @DisplayName("Testeando get journal")
    void getJournal(String idRendicion,String msg, List<Journal> data) throws Exception {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturnList()).thenReturn(data);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
            List<Journal> result = aprobacionesService.getJournal(idRendicion);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(data, result)
            );
        }
    }

    @Test
    @Disabled("Desabilitado porque no se puede mockear el valor de msg")
    @DisplayName("Testeando get msg")
    void getMsg() {
        AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
        aprobacionesService.getMsg();
        assertNull(aprobacionesService.getMsg());
    }

    @Test
    @Disabled("Desabilitado porque no se puede mockear el valor de cantRendiciones")
    @DisplayName("Testeando get cant rendiciones")
    void getCantRendiciones() {
        AprobacionesService aprobacionesService = new AprobacionesService(samWebClient);
        aprobacionesService.getCantRendiciones();
        assertNull(aprobacionesService.getCantRendiciones());
    }

    // ----- Sources -----
    private static Stream<Arguments> getAprobacionesPendientesSource() {
        String id = "1";
        String usuarioFiltro = "usuarioFiltro";
        String motivo = "motivo";
        String estado = "estado";
        String usuario = "usuario";
        List<Rendicion> rendiciones = new ArrayList<>();
        String dataReturn = "msg";

        return Stream.of(
                Arguments.of(id, usuarioFiltro, motivo, estado, usuario, rendiciones, dataReturn)
        );
    }

    private static Stream<Arguments> cambiarEstadoRendicionesSource(){
        String user = "user";
        String estado = "RECHA";
        String motivoRechazo = "motivoRechazo";
        String glg = "glg";
        String aviso = "aviso";
        List<Rendicion> rendicionesSeleccionadas = new ArrayList<>();

        for (int i = 0; i < 1450; i++) {
            Rendicion rendicion = new Rendicion();
            rendicion.setId(i);
            rendicionesSeleccionadas.add(rendicion);
        }

        return Stream.of(
                Arguments.of(user, estado, motivoRechazo, glg, aviso, rendicionesSeleccionadas)
        );
    }

    private static Stream<Arguments> cambiarEstadoDeUnaRendicionSource(){
        String user = "user";
        Integer rendicionesSeleccionadas = 1;
        String estado = "RECHA";
        String motivoRechazo = "motivoRechazo";
        String glg = "glg";
        String aviso = "aviso";

        return Stream.of(
                Arguments.of(user, rendicionesSeleccionadas, estado, motivoRechazo, glg, aviso)
        );
    }

    private static Stream<Arguments> obtenerIDUSource(){
        RendicionAvisoForm form = new RendicionAvisoForm();

        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);
        rendicion.setFechaDesde(new Date());
        rendicion.setFechaHasta(new Date());

        Usuario usuario = new Usuario("1", "1", "1", 1, "1", new ArrayList<>());

        form.setRendicion(rendicion);
        form.setUsuario(usuario);


        String tipoAdea = "tipoAdea";
        String iduAdea = "iduAdea";
        String msg = "msg";

        return Stream.of(
                Arguments.of(form, tipoAdea, iduAdea, msg)
        );
    }

    private static Stream<Arguments> scanRendicionSource(){
        String idRendicion = "1";
        String user = "user";

        return Stream.of(
                Arguments.of(idRendicion, user)
        );
    }

    private static Stream<Arguments> cambiarEscanRendicionSource(){
        String idRendicion = "1";
        String user = "user";
        String idu = "idu";
        String adea = "adea";

        return Stream.of(
                Arguments.of(idRendicion, user, idu, adea)
        );
    }

    private static Stream<Arguments> getJournalSource(){
        String idRendicion = "1";
        String msg = "msg";
        List<Journal> data = new ArrayList<>();

        return Stream.of(
                Arguments.of(idRendicion, msg, data)
        );
    }
}