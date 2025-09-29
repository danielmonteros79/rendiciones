package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboOpcion;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.entities.parametros.Resumen;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResumenServiceTest {

    @InjectMocks
    ResumenService resumenService;

    @Spy
    SAMWebClient samWebClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getConsumosSource")
    @DisplayName("Testeando get consumos")
    void getConsumos(String user,String fechaDesde, String fechaHasta, String codMotivo,String msg, List<Resumen> resumen) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(resumen);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ResumenService resumenService1 = new ResumenService(samWebClient);
            List<Resumen> result = resumenService1.getConsumos(user, fechaDesde, fechaHasta, codMotivo);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(resumen, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getFechasResumenesSource")
    @DisplayName("Testeando get fechas resumenes")
    void getFechasResumenes(String user,String msg,List<ComboOpcion> fechas) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(fechas);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ResumenService resumenService1 = new ResumenService(samWebClient);
            List<ComboOpcion> result = resumenService1.getFechasResumenes(user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(fechas, result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getResumenesSource")
    @DisplayName("Testeando get resumenes")
    void getResumenes(String user, String fecha,String msg, List<Resumen> resumen) throws TransactionException {
        try (MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(), anyMap());
            when(mockM.getDataReturnList()).thenReturn(resumen);
            when(mockM.getMensajeAviso()).thenReturn(msg);
        })) {

            ResumenService resumenService1 = new ResumenService(samWebClient);
            List<Resumen> result = resumenService1.getResumenes(user, fecha);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(resumen, result)
            );
        }
    }

    @Test
    @Disabled("No se puede mockear el metodo getMsg")
    @DisplayName("Testeando get msg")
    void getMsg() {
    }

    // ------ Sources ------

    private static Stream<Arguments> getConsumosSource() {
        String user = "user";
        String fechaDesde = "fechaDesde";
        String fechaHasta = "fechaHasta";
        String codMotivo = "codMotivo";
        String msg = "msg";
        List<Resumen> resumen = new ArrayList<>();

        return Stream.of(
                Arguments.of(user,fechaDesde,fechaHasta,codMotivo, msg, resumen)
        );
    }

    private static Stream<Arguments> getFechasResumenesSource(){
        String user = "user";
        String msg = "msg";
        List<ComboOpcion> fechas = new ArrayList<>();

        return Stream.of(
                Arguments.of(user, msg, fechas)
        );
    }

    private static Stream<Arguments> getResumenesSource(){
        String user = "user";
        String fecha = "fecha";
        String msg  = "msg";
        List<Resumen> resumen = new ArrayList<>();

        return Stream.of(
                Arguments.of(user, fecha, msg, resumen)
        );
    }

}