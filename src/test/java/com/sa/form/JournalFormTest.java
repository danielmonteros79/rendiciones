package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class JournalFormTest {

    private JournalForm service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new JournalForm();
    }

    @Test
    @DisplayName("Testeando set y get NumeroAprob")
    void getNumeroAprob() {
        service.setNumeroAprob("");
        String resultTest = service.getNumeroAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getNumeroAprob())
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
    @DisplayName("Testeando set y get UsuarioProx")
    void getUsuarioProx() {
        service.setUsuarioProx("");
        String resultTest = service.getUsuarioProx();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getUsuarioProx())
        );
    }

    @Test
    @DisplayName("Testeando set y get NombreUsuarioProx")
    void getNombreUsuarioProx() {
        service.setNombreUsuarioProx("");
        String resultTest = service.getNombreUsuarioProx();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getNombreUsuarioProx())
        );
    }

    @Test
    @DisplayName("Testeando set y get Nivel")
    void getNivel() {
        service.setNivel("");
        String resultTest = service.getNivel();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getNivel())
        );
    }

    @Test
    @DisplayName("Testeando set y get UsuarioAprob")
    void getUsuarioAprob() {
        service.setUsuarioAprob("");
        String resultTest = service.getUsuarioAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getUsuarioAprob())
        );
    }

    @Test
    @DisplayName("Testeando set y get NombreUsuarioAprob")
    void getNombreUsuarioAprob() {
        service.setNombreUsuarioAprob("");
        String resultTest = service.getNombreUsuarioAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getNombreUsuarioAprob())
        );
    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void getIdRendicion() {
        service.setIdRendicion("");
        String resultTest = service.getIdRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getIdRendicion())
        );
    }
}