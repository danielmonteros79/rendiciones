package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FiltrarAprobacionFormTest {

    private FiltrarAprobacionForm service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new FiltrarAprobacionForm();
    }

    @Test
    @DisplayName("Testeando set y get User")
    void setygetUser() {
        service.setUser("");
        String resultTest = service.getUser();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getUser())
        );
    }

    @Test
    @DisplayName("Testeando set y get Motivo")
    void setygetMotivo() {
        service.setMotivo("");
        String resultTest = service.getMotivo();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void setygetIdRendicion() {
        service.setIdRendicion("");
        String resultTest = service.getIdRendicion();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getIdRendicion())
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void setygetEstado() {
        service.setEstado("");
        String resultTest = service.getEstado();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getEstado())
        );
    }
    
    @Test
    @DisplayName("Testeando set y get  TipoAlerta")
    void setygetTipoAlerta() {
        service.setTipoAlerta("");
        String resultTest = service.getTipoAlerta();
        assertAll(
                () -> assertNotNull(resultTest),
                () -> assertEquals(resultTest, service.getTipoAlerta())
        );
    }
    
    
   
}