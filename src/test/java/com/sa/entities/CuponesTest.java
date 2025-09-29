package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuponesTest {

    private Cupones entity;

    public static Stream<Arguments> getNrnoTarjetaClienteSource() {
        return Stream.of(
                Arguments.of("XXXX-XXXX-XXXX-XX-X"),
                Arguments.of("")
        );
    }

    @BeforeEach
    void setup(){
        entity = new Cupones();
        MockitoAnnotations.openMocks(this);
    }


    @ParameterizedTest
    @MethodSource("getNrnoTarjetaClienteSource")
    @DisplayName("getNroTarjetaCliente")
    void getNroTarjetaCliente(String nroTarjeta) {
        entity.setNroTarjeta(nroTarjeta);
        String resultTest = entity.getNroTarjetaCliente();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(nroTarjeta,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getIdRendicion")
    void getIdRendicion() {
        entity.setIdRendicion("");
        String resultTest = entity.getIdRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getIdGastoRend")
    void getIdGastoRend() {
        entity.setIdGastoRend("");
        String resultTest = entity.getIdGastoRend();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaCierre")
    void getFechaCierre() {
        entity.setFechaCierre("");
        String resultTest = entity.getFechaCierre();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNroCupon")
    void getNroCupon() {
        entity.setNroCupon("");
        String resultTest = entity.getNroCupon();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getEstablecimiento")
    void getEstablecimiento() {
        entity.setEstablecimiento("");
        String resultTest = entity.getEstablecimiento();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMoneda")
    void getMoneda() {
        entity.setMoneda("");
        String resultTest = entity.getMoneda();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCupones")
    void getCupones() {
        List<Cupones> cupones = new ArrayList<>();
        entity.setCupones(cupones);
        List<Cupones> resultTest = entity.getCupones();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(cupones,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getTipo")
    void getTipo() {
        entity.setTipo("");
        String resultTest = entity.getTipo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCodAdmin")
    void getCodAdmin() {
        entity.setCodAdmin("");
        String resultTest = entity.getCodAdmin();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCuentaCredito")
    void getCuentaCredito() {
        entity.setCuentaCredito("");
        String resultTest = entity.getCuentaCredito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNroCliente")
    void getNroCliente() {
        entity.setNroCliente("");
        String resultTest = entity.getNroCliente();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNroTarjeta")
    void getNroTarjeta() {
        entity.setNroTarjeta("");
        String resultTest = entity.getNroTarjeta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getLiquidacionDebito")
    void getLiquidacionDebito() {
        entity.setLiquidacionDebito("");
        String resultTest = entity.getLiquidacionDebito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getLiquidacionCredito")
    void getLiquidacionCredito() {
        entity.setLiquidacionCredito("");
        String resultTest = entity.getLiquidacionCredito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getLiquidacionNeto")
    void getLiquidacionNeto() {
        entity.setLiquidacionNeto("");
        String resultTest = entity.getLiquidacionNeto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaPresentacion")
    void getFechaPresentacion() {
        entity.setFechaPresentacion("");
        String resultTest = entity.getFechaPresentacion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCodigoAutorizacion")
    void getCodigoAutorizacion() {
        entity.setCodigoAutorizacion("");
        String resultTest = entity.getCodigoAutorizacion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getTipoMovimiento")
    void getTipoMovimiento() {
        entity.setTipoMovimiento("");
        String resultTest = entity.getTipoMovimiento();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getTipoConsumo")
    void getTipoConsumo() {
        entity.setTipoConsumo("");
        String resultTest = entity.getTipoConsumo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMarcaFacturado")
    void getMarcaFacturado() {
        entity.setMarcaFacturado("");
        String resultTest = entity.getMarcaFacturado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNroCuponDebito")
    void getNroCuponDebito() {
        entity.setNroCuponDebito("");
        String resultTest = entity.getNroCuponDebito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNroCuponCredito")
    void getNroCuponCredito() {
        entity.setNroCuponCredito("");
        String resultTest = entity.getNroCuponCredito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMontoUtilizado")
    void getMontoUtilizado() {
        entity.setMontoUtilizado("");
        String resultTest = entity.getMontoUtilizado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getDisponible")
    void getDisponible() {
        entity.setDisponible("");
        String resultTest = entity.getDisponible();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getOpciones")
    void getOpciones() {
        entity.setOpciones("");
        String resultTest = entity.getOpciones();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCuponCheck")
    void getCuponCheck() {
        entity.setCuponCheck("");
        String resultTest = entity.getCuponCheck();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando getFechaPresentacionDate")
    void getFechaPresentacionDate() {
        String date="10-10-2010";
        entity.setFechaPresentacion(date);
        Date resultTest = entity.getFechaPresentacionDate();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertTrue(resultTest.equals(entity.getFechaPresentacionDate()))
        );
    }

    @Test
    @DisplayName("Testeando setAdelanto e isAdelanto")
    void setAdelanto() {
        boolean adelantado = true;
        entity.setAdelanto(adelantado);
        boolean resultTest = entity.isAdelanto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(adelantado,resultTest)
        );
    }

    @Test
    @DisplayName("getFechaPresentacionDate handles parse exception (debug enabled)")
    void getFechaPresentacionDate_parseException_debugEnabled() throws Exception {
        Cupones c = new Cupones();
        c.setFechaPresentacion("invalid-date-format");

        // Mock logger y reemplazo por reflexión
        Logger mockLog = mock(Logger.class);
        when(mockLog.isDebugEnabled()).thenReturn(true);

        Field logField = Cupones.class.getDeclaredField("log");
        logField.setAccessible(true);
        // quitar final
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(logField, logField.getModifiers() & ~Modifier.FINAL);

        Object original = logField.get(null);
        try {
            logField.set(null, mockLog);

            Date res = c.getFechaPresentacionDate();
            assertNull(res, "Al parsear fecha inválida debe retornarse null");

            verify(mockLog).isDebugEnabled();
            verify(mockLog).debug(contains("Error al parsear fechaPresentacion"), any(Exception.class));
            verify(mockLog).error(contains("No se pudo parsear fechaPresentacion"), any(Exception.class));
        } finally {
            // restaurar logger original
            logField.set(null, original);
        }
    }

    @Test
    @DisplayName("getFechaPresentacionDate handles parse exception (debug disabled)")
    void getFechaPresentacionDate_parseException_debugDisabled() throws Exception {
        Cupones c = new Cupones();
        c.setFechaPresentacion("also-invalid");

        Logger mockLog = mock(Logger.class);
        when(mockLog.isDebugEnabled()).thenReturn(false);

        Field logField = Cupones.class.getDeclaredField("log");
        logField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(logField, logField.getModifiers() & ~Modifier.FINAL);

        Object original = logField.get(null);
        try {
            logField.set(null, mockLog);

            Date res = c.getFechaPresentacionDate();
            assertNull(res, "Al parsear fecha inválida debe retornarse null");

            verify(mockLog).isDebugEnabled();
            verify(mockLog, never()).debug(anyString(), any(Throwable.class));
            verify(mockLog).error(contains("No se pudo parsear fechaPresentacion"), any(Exception.class));
        } finally {
            logField.set(null, original);
        }
    }

}