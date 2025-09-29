package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CierreFiltroFormTest {

    private CierreFiltroForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new CierreFiltroForm();
    }

    @Test
    @DisplayName("Testeando set y get User")
    void setUser() {
        form.setUser("");
        String resultTest = form.getUser();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getUser())
        );

    }

    @Test
    @DisplayName("Testeando set y get Motivo")
    void setMotivo() {
        form.setMotivo("");
        String resultTest = form.getMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getMotivo())
        );

    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void setIdRendicion() {
        form.setIdRendicion("");
        String resultTest = form.getIdRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getIdRendicion())
        );

    }

    @Test
    @DisplayName("Testeando set y get FechaDesde")
    void setFechaDesde() {
        form.setFechaDesde("");
        String resultTest = form.getFechaDesde();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getFechaDesde())
        );

    }

    @Test
    @DisplayName("Testeando set y get FechaHasta")
    void setFechaHasta() {
        form.setFechaHasta("");
        String resultTest = form.getFechaHasta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getFechaHasta())
        );

    }
}