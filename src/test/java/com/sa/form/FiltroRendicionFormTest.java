package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FiltroRendicionFormTest {

    private FiltroRendicionForm service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new FiltroRendicionForm();
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void getEstado() {
        service.setEstado("");
        String realTest = service.getEstado();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getEstado())
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaDesde")
    void getFechaDesde() {
        service.setFechaDesde("");
        String realTest = service.getFechaDesde();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getFechaDesde())
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaHasta")
    void getFechaHasta() {
        service.setFechaHasta("");
        String realTest = service.getFechaHasta();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getFechaHasta())
        );
    }

    @Test
    @DisplayName("Testeando set y get Delegado")
    void getDelegado() {
        service.setDelegado("");
        String realTest = service.getDelegado();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getDelegado())
        );
    }

    @Test
    @DisplayName("Testeando set y get Id")
    void getId() {
        service.setId("");
        String realTest = service.getId();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getId())
        );
    }

    @Test
    @DisplayName("Testeando set y get Opciones")
    void getOpciones() {
        service.setOpciones("");
        String realTest = service.getOpciones();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getOpciones())
        );
    }

    @Test
    @DisplayName("Testeando set y get Scan")
    void getScan() {
        service.setScan("");
        String realTest = service.getScan();
        assertAll(
                () -> assertNotNull(realTest),
                () -> assertEquals(realTest, service.getScan())
        );
    }
}
