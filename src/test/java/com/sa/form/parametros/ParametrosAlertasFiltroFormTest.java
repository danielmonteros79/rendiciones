package com.sa.form.parametros;

import com.sa.entities.ComboOpcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosAlertasFiltroFormTest {

    private ParametrosAlertasFiltroForm form;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        form = new ParametrosAlertasFiltroForm();
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void getCodMotivo() {
        form.setCodMotivo("");
        String resultTest = form.getCodMotivo();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getCodMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get CodGasto")
    void getCodGasto() {
        form.setCodGasto("");
        String resultTest = form.getCodGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getCodGasto())
        );
    }

    @Test
    @DisplayName("Testeando set y get MapGastoMotivo")
    void getMapGastoMotivo() {
        form.setMapGastoMotivo(new HashMap<String, String>());
        Map<String, String> resultTest = form.getMapGastoMotivo();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getMapGastoMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get MapMotivoGastos")
    void getMapMotivoGastos() {
        form.setMapMotivoGastos(new HashMap<String, List<ComboOpcion>>());
        Map<String, List<ComboOpcion>> resultTest = form.getMapMotivoGastos();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getMapMotivoGastos())
        );
    }

    @Test
    @DisplayName("Testeando set y get CmbGasto")
    void getCmbGasto() {
        form.setCmbGasto(new ArrayList<ComboOpcion>());
        List<ComboOpcion> resultTest = form.getCmbGasto();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getCmbGasto())
        );
    }

    @Test
    @DisplayName("Testeando set y get CmbMotivo")
    void getCmbMotivo() {
        form.setCmbMotivo(new ArrayList<ComboOpcion>());
        List<ComboOpcion> resultTest = form.getCmbMotivo();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getCmbMotivo())
        );
    }
}