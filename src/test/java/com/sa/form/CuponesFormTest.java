package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CuponesFormTest {

    private CuponesForm service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new CuponesForm();
    }

    @Test
    void getCupones() {
        String resultTest = service.getCupones();
        assertNull(resultTest);
    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void getIdRendicion() {
        service.setIdRendicion("");
        String resultTest = service.getIdRendicion();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getIdRendicion())
        );
    }

    @Test
    @DisplayName("Testeando set y get IdGastoRend")
    void getIdGastoRend() {
        service.setIdGastoRend("");
        String resultTest = service.getIdGastoRend();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getIdGastoRend())
        );
    }

    @Test
    @DisplayName("Testeando set y get NroTarjeta")
    void getNroTarjeta() {
        service.setNroTarjeta("");
        String resultTest = service.getNroTarjeta();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getNroTarjeta())
        );
    }

    @Test
    @DisplayName("Testeando set y get Cupon")
    void getCupon() {
        service.setCupon("");
        String resultTest = service.getCupon();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getCupon())
        );
    }

    @Test
    @DisplayName("Testeando set y get CupCred")
    void getCupCred() {
        service.setCupCred("");
        String resultTest = service.getCupCred();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getCupCred())
        );
    }

    @Test
    @DisplayName("Testeando set y get CupDeb")
    void getCupDeb() {
        service.setCupDeb("");
        String resultTest = service.getCupDeb();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getCupDeb())
        );
    }

    @Test
    @DisplayName("Testeando set y get DescCupon")
    void getDescCupon() {
        service.setDescCupon("");
        String resultTest = service.getDescCupon();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getDescCupon())
        );
    }

    @Test
    @DisplayName("Testeando set y get ImporteCupon")
    void getImporteCupon() {
        service.setImporteCupon("");
        String resultTest = service.getImporteCupon();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getImporteCupon())
        );
    }

    @Test
    @DisplayName("Testeando set y get SubS")
    void getSubS() {
        service.setSubS("");
        String resultTest = service.getSubS();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getSubS())
        );
    }

    @Test
    @DisplayName("Testeando set y get CentroCostos")
    void getCentroCostos() {
        service.setCentroCostos("");
        String resultTest = service.getCentroCostos();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getCentroCostos())
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaD")
    void getFechaD() {
        service.setFechaD("");
        String resultTest = service.getFechaD();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getFechaD())
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaH")
    void getFechaH() {
        service.setFechaH("");
        String resultTest = service.getFechaH();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getFechaH())
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaPresentacion")
    void getFechaPresentacion() {
        service.setFechaPresentacion("");
        String resultTest = service.getFechaPresentacion();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getFechaPresentacion())
        );
    }

    @Test
    @DisplayName("Testeando set y get EstadoRendicion")
    void getEstadoRendicion() {
        service.setEstadoRendicion("");
        String resultTest = service.getEstadoRendicion();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getEstadoRendicion())
        );
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void getCodMotivo() {
        service.setCodMotivo("");
        String resultTest = service.getCodMotivo();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getCodMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get Moneda")
    void getMoneda() {
        service.setMoneda("");
        String resultTest = service.getMoneda();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getMoneda())
        );
    }

    @Test
    @DisplayName("Testeando set y get EsAdelanto")
    void getEsAdelanto() {
        service.setEsAdelanto("");
        String resultTest = service.getEsAdelanto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getEsAdelanto())
        );
    }
}