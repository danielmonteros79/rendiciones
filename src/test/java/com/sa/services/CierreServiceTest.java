package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
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
class CierreServiceTest {

    @InjectMocks
    CierreService cierreService;

    @Spy
    SAMWebClient samWebClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getDatosRendicionSource")
    @DisplayName("Testeando get datos rendicion")
    void getDatosRendicion(String idUser, String idRend, String codMotivo, String usrSel, String feDesde, String feHasta, List<Rendicion> rendiciones,String msg) throws TransactionException, ParseException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(rendiciones);
            when(mockM.getMensajeAviso()).thenReturn("msg");
        })) {

            CierreService cierreService = new CierreService(samWebClient);
            List<Rendicion> rendiciones1 = cierreService.getDatosRendicion(idUser, idRend, codMotivo, usrSel, feDesde, feHasta);

            assertNotNull(rendiciones1);
        }
    }

//    @Disabled("Desabilitado porque se debe adecuar a version actual")
//    @ParameterizedTest
//    @MethodSource("crearOrdenDePagoSource")
//    @DisplayName("Testeando crear orden de pago")
//    void crearOrdenDePago(String estado, List<Rendicion> rendicionesSeleccionadas, String user, String descripcion,
//                          String cmboMotivo) throws TransactionException {
//        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
//            doNothing().when(mockM).executeTrx(any(), anyMap());
//            when(mockM.getMensajeAviso()).thenReturn("msg");
//        })) {
//
//            CierreService cierreService = new CierreService(samWebClient);
//            cierreService.crearOrdenDePago(estado, rendicionesSeleccionadas, user, descripcion, cmboMotivo);
//
//            assertNotNull(cierreService.getMsg());
//        }
//    }

    @ParameterizedTest
    @MethodSource("generarPagoMarcaSource")
    @DisplayName("Testeando generar pago marca")
    void generarPagoMarca(String opcion, String idProceso, String tipoProceso, String feHoy, String estProceso,
                          String numRegistro, String descripcion, String usuario) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getMensajeAviso()).thenReturn("msg");
        })) {

            CierreService cierreService = new CierreService(samWebClient);
            String result = cierreService.GenerarPagoMarca(opcion, idProceso, tipoProceso, feHoy, estProceso, numRegistro, descripcion, usuario);

            assertNull(result);
        }
    }

    @Test
    @DisplayName("Testeando suspensos marca")
    void suspensosMarca() {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
        })) {

            CierreService cierreService = new CierreService(samWebClient);
            TransactionException exception = assertThrows(TransactionException.class, () -> {
                String result = cierreService.SuspensosMarca();
            });

            assertAll(
                    () -> assertEquals("Ya existe una solicitud pendiente para este usuario", exception.getMessage()),
                    () -> assertEquals("Ya existe una solicitud pendiente para este usuario", exception.getLocalizedMessage())
            );
        }

    }

    @Test
    @DisplayName("Testeando cierra tarjeta marca")
    void cierreTarjetaMarca() throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
        })) {

            CierreService cierreService = new CierreService(samWebClient);
            String result = cierreService.CierreTarjetaMarca();


            assertAll(
                    () -> assertNull(result)
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

    // ----- Sources -----

    private static Stream<Arguments> getDatosRendicionSource() {
        String idUser = "1";
        String idRend = "1";
        String codMotivo = "1";
        String usrSel = "1";
        String feDesde = "01/01/2000";
        String feHasta = "01/01/2000";
        String msg = "msg";
        List<Rendicion> rendiciones = new ArrayList<>();

        return Stream.of(
                Arguments.of(idUser, idRend, codMotivo, usrSel, feDesde, feHasta, rendiciones, msg)
        );

    }

    private static Stream<Arguments> crearOrdenDePagoSource(){
        String estado = "SUSPE";
        String user = "1";
        String descripcion = "descripcion";
        String cmboMotivo = "1";
        String msg = "msg";
        List<Rendicion> rendicionesSeleccionadas = new ArrayList<>();
        for (int i = 0; i < 1450; i++) {
            Rendicion rendicion = new Rendicion();
            rendicion.setId(i);
            rendicionesSeleccionadas.add(rendicion);
        }

        return Stream.of(
                Arguments.of(estado, rendicionesSeleccionadas, user, descripcion, cmboMotivo)
        );
    }

    private static Stream<Arguments> generarPagoMarcaSource(){
        String opcion = "1";
        String idProceso = "1";
        String tipoProceso = "1";
        String feHoy = "01/01/2000";
        String estProceso = "1";
        String numRegistro = "1";
        String descripcion = "descripcion";
        String usuario = "1";

        return Stream.of(
                Arguments.of(opcion, idProceso, tipoProceso, feHoy, estProceso, numRegistro, descripcion, usuario)
        );
    }

}