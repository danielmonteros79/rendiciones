package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GastosTest {

    private Gastos entity = new Gastos();
    private List<Cupones> cupones = new ArrayList<Cupones>();

    @BeforeEach
    void setup() {
        entity = new Gastos("", "", "", "", "", cupones);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get IdGasto")
    void getIdGasto() {
        entity.setIdGasto("");
        String resultTest = entity.getIdGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get DescGasto")
    void getDescGasto() {
        entity.setDescGasto("");
        String resultTest = entity.getDescGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get TipoComprobante")
    void getTipoComprobante() {
        entity.setTipoComprobante("");
        String resultTest = entity.getTipoComprobante();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get ObsObligatoria")
    void getObsObligatoria() {
        entity.setObsObligatoria("");
        String resultTest = entity.getObsObligatoria();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Tarjeta")
    void getTarjeta() {
        entity.setTarjeta("");
        String resultTest = entity.getTarjeta();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get NroGasto")
    void getNroGasto() {
        entity.setNroGasto("");
        entity.setNroGastos("");
        String resultTest = entity.getNroGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Monto")
    void getMonto() {
        entity.setMonto("");
        String resultTest = entity.getMonto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Moneda")
    void getMoneda() {
        entity.setMoneda("");
        String resultTest = entity.getMoneda();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Fechagastos")
    void getFechagastos() {
        entity.setFechagastos("");
        String resultTest = entity.getFechagastos();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante")
    void getComprobante() {
        entity.setComprobante("");
        String resultTest = entity.getComprobante();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get ListaCupones")
    void getListaCupones() {
        entity.setListaCupones(cupones);
        List<Cupones> resultTest = entity.getListaCupones();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(cupones, resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Obs")
    void getObs() {
        entity.setObs("");
        String resultTest = entity.getObs();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CuponGasto")
    void getCuponGasto() {
        entity.setCuponGasto("");
        String resultTest = entity.getCuponGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get ObservacionGasto")
    void getObservacionGasto() {
        entity.setObservacionGasto("");
        String resultTest = entity.getObservacionGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CostosDestino")
    void getCostosDestino() {
        entity.setCostosDestino("");
        String resultTest = entity.getCostosDestino();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante1")
    void getComprobante1() {
        entity.setComprobante1("");
        String resultTest = entity.getComprobante1();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante2")
    void getComprobante2() {
        entity.setComprobante2("");
        String resultTest = entity.getComprobante2();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit1")
    void getCuit1() {
        entity.setCuit1("");
        String resultTest = entity.getCuit1();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit2")
    void getCuit2() {
        entity.setCuit2("");
        String resultTest = entity.getCuit2();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit3")
    void getCuit3() {
        entity.setCuit3("");
        String resultTest = entity.getCuit3();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CmbComprobante")
    void getCmbComprobante() {
        entity.setCmbComprobante("");
        String resultTest = entity.getCmbComprobante();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CentroCostoGasto")
    void getCentroCostoGasto() {
        entity.setCentroCostoGasto("");
        String resultTest = entity.getCentroCostoGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get IdGastoOriginal")
    void getIdGastoOriginal() {
        entity.setIdGastoOriginal("");
        String resultTest = entity.getIdGastoOriginal();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals("", resultTest)
        );
    }
}