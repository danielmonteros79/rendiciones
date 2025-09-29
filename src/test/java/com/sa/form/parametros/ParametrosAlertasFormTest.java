package com.sa.form.parametros;

import com.sa.entities.ComboOpcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosAlertasFormTest {

    private ParametrosAlertasForm form;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        form = new ParametrosAlertasForm();
    }

    @Test
    @DisplayName("Testeando Clear")
    void clear() {
        form.setCodMotivo("");
        form.setCodGasto("");
        form.setMontCant("");
        form.setImpCant("");
        form.setRend("");
        form.setPeriodo("");
        form.setNivMin("");
        form.setNivMax("");
        form.setEstado("");
        form.setCriticidad("");
        form.setMapGastoMotivo(new HashMap<String, String>());
        form.setMapMotivoGastos(new HashMap<String, List<ComboOpcion>>());
        form.setCmbGasto(new ArrayList<ComboOpcion>());
        form.setCmbMotivo(new ArrayList<ComboOpcion>());
        form.setTxAviso("");

        form.clear();

        assertAll(
                () -> assertEquals(form.getCodMotivo(), null),
                () -> assertEquals(form.getCodGasto(), null),
                () -> assertEquals(form.getMontCant(), null),
                () -> assertEquals(form.getImpCant(), null),
                () -> assertEquals(form.getRend(), null),
                () -> assertEquals(form.getPeriodo(), null),
                () -> assertEquals(form.getNivMin(), null),
                () -> assertEquals(form.getNivMax(), null),
                () -> assertEquals(form.getEstado(), null),
                () -> assertEquals(form.getCriticidad(), null),
                () -> assertEquals(form.getMapGastoMotivo(), null),
                () -> assertEquals(form.getMapMotivoGastos(), null),
                () -> assertEquals(form.getCmbGasto(), null),
                () -> assertEquals(form.getCmbMotivo(), null),
                () -> assertEquals(form.getTxAviso(), null)
        );
    }

    @Test
    @DisplayName("Testeando set y get Accion")
    void getAccion() {
        form.setAccion("");
        String resultTest = form.getAccion();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getAccion())
        );
    }

    @Test
    @DisplayName("Testeando set y get TimeStamp")
    void getTimeStamp() {
        form.setTimeStamp("");
        String resultTest = form.getTimeStamp();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, form.getTimeStamp())
        );
    }
}