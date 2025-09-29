package com.sa.form;

import com.sa.entities.ComboMotivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuadroGeneralFormTest {

    private CuadroGeneralForm service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        service = new CuadroGeneralForm();
    }

    @Test
    @DisplayName("Testeando set y get User")
    void getUser() {
        service.setUser("");
        String resultTest = service.getUser();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getUser())
        );
    }

    @Test
    @DisplayName("Testeando set y get Ccostos")
    void getCcostos() {
        service.setCcostos("");
        String resultTest = service.getCcostos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getCcostos())
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void getEstado() {
        service.setEstado("");
        String resultTest = service.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getEstado())
        );
    }

    @Test
    @DisplayName("Testeando set y get CantRen")
    void getCantRen() {
        service.setCantRen("");
        String resultTest = service.getCantRen();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getCantRen())
        );
    }

    @Test
    @DisplayName("Testeando set y get ComboMotivo")
    void getComboMotivo() {
        service.setComboMotivo(new ArrayList<>());
        List<ComboMotivo> resultTest = service.getComboMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getComboMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get MontoTotal")
    void getMontoTotal() {
        service.setMontoTotal(1);
        Integer resultTest = service.getMontoTotal();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getMontoTotal())
        );
    }

    @Test
    @DisplayName("Testeando set y get Glg")
    void getGlg() {
        service.setGlg("");
        String resultTest = service.getGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getGlg())
        );
    }
}